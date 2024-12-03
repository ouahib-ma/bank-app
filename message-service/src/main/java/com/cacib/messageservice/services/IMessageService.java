package com.cacib.messageservice.services;


import com.cacib.messageservice.dtos.MessageDTO;
import com.cacib.messageservice.enums.MessageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMessageService {
    MessageDTO processMessage(String content, String source, String queueName);
    Page<MessageDTO> getAllMessages(Pageable pageable);
    MessageDTO getMessage(Long id);
    List<MessageDTO> getMessagesByStatus(MessageStatus status);
    void batchProcessMessages(List<String> messages, String source, String queueName);
}
