package com.cacib.messageservice.services.impl;

import com.cacib.messageservice.dtos.MessageDTO;
import com.cacib.messageservice.entities.Message;
import com.cacib.messageservice.enums.MessageStatus;
import com.cacib.messageservice.exceptions.MessageNotFoundException;
import com.cacib.messageservice.exceptions.MessageProcessingException;
import com.cacib.messageservice.mappers.MessageMapper;
import com.cacib.messageservice.repositories.IMessageRepository;
import com.cacib.messageservice.services.IMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements IMessageService {
    private final IMessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final JmsTemplate jmsTemplate;

    @Value("${mq.queue.dead-letter}")
    private String deadLetterQueue;

    @Value("${mq.batch.size:100}")
    private int batchSize;

    @Override
    @Transactional
    public MessageDTO processMessage(String content, String source, String queueName) {
        log.debug("Processing message from source: {} queue: {}", source, queueName);

        Message message = new Message();
        message.setContent(content);
        message.setSource(source);
        message.setQueueName(queueName);
        message.setStatus(MessageStatus.NEW);

        try {
            message = messageRepository.save(message);
            jmsTemplate.convertAndSend(queueName, content);

            message.setStatus(MessageStatus.PROCESSED);
            message.setProcessedAt(LocalDateTime.now());
            return messageMapper.toDto(messageRepository.save(message));
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage(), e);
            message.setStatus(MessageStatus.ERROR);
            messageRepository.save(message);
            jmsTemplate.convertAndSend(deadLetterQueue, content);
            throw new MessageProcessingException("Failed to process message", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MessageDTO> getAllMessages(Pageable pageable) {
        return messageRepository.findAll(pageable)
                .map(messageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public MessageDTO getMessage(Long id) {
        return messageRepository.findById(id)
                .map(messageMapper::toDto)
                .orElseThrow(() -> new MessageNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDTO> getMessagesByStatus(MessageStatus status) {
        return messageRepository.findByStatus(status)
                .stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void batchProcessMessages(List<String> messages, String source, String queueName) {
        List<List<String>> batches = ListUtils.partition(messages, batchSize);

        for (List<String> batch : batches) {
            try {
                processBatch(batch, source, queueName);
            } catch (Exception e) {
                log.error("Error processing batch: {}", e.getMessage(), e);
                batch.forEach(message -> jmsTemplate.convertAndSend(deadLetterQueue, message));
                throw new MessageProcessingException("Failed to process batch", e);
            }
        }
    }

    private void processBatch(List<String> messages, String source, String queueName) {
        List<Message> messageEntities = messages.stream()
                .map(content -> {
                    Message message = new Message();
                    message.setContent(content);
                    message.setSource(source);
                    message.setQueueName(queueName);
                    message.setStatus(MessageStatus.NEW);
                    return message;
                })
                .collect(Collectors.toList());

        messageRepository.saveAll(messageEntities);
    }

    @Scheduled(cron = "${message.cleanup.cron:-}")
    @Transactional
    public void cleanupOldMessages() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(30);
        List<Message> oldMessages = messageRepository
                .findMessagesInTimeRange(LocalDateTime.MIN, threshold);
        messageRepository.deleteAll(oldMessages);
        log.info("Cleaned up {} old messages", oldMessages.size());
    }
}