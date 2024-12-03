package com.cacib.messageservice.configs;

import com.cacib.messageservice.entities.Message;
import com.cacib.messageservice.enums.MessageStatus;
import com.cacib.messageservice.repositories.IMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DataInitializerConfig {

    @Bean
    CommandLineRunner initDatabase(IMessageRepository messageRepository) {
        return args -> {
            log.info("Initializing Message Database");

            if (messageRepository.count() == 0) {
                // Message 1 - Processed Payment
                Message message1 = new Message();
                message1.setContent("{\"transactionId\": \"123\", \"amount\": 1000, \"currency\": \"EUR\"}");
                message1.setSource("PAYMENT_APP");
                message1.setQueueName("PAYMENT.QUEUE");
                message1.setStatus(MessageStatus.PROCESSED);
                message1.setReceivedAt(LocalDateTime.now());
                message1.setProcessedAt(LocalDateTime.now());
                messageRepository.save(message1);

                // Message 2 - New Order
                Message message2 = new Message();
                message2.setContent("{\"orderId\": \"A456\", \"customerName\": \"John Doe\", \"total\": 500}");
                message2.setSource("ORDER_APP");
                message2.setQueueName("ORDER.QUEUE");
                message2.setStatus(MessageStatus.NEW);
                message2.setReceivedAt(LocalDateTime.now());
                messageRepository.save(message2);

                // Message 3 - Error Alert
                Message message3 = new Message();
                message3.setContent("{\"alertType\": \"FRAUD\", \"accountId\": \"789\", \"amount\": 5000}");
                message3.setSource("ALERT_APP");
                message3.setQueueName("ALERT.QUEUE");
                message3.setStatus(MessageStatus.ERROR);
                message3.setReceivedAt(LocalDateTime.now());
                messageRepository.save(message3);

                // Message 4 - Processed Transfer
                Message message4 = new Message();
                message4.setContent("{\"transferId\": \"TR789\", \"fromAccount\": \"123\", \"toAccount\": \"456\", \"amount\": 2500}");
                message4.setSource("TRANSFER_APP");
                message4.setQueueName("TRANSFER.QUEUE");
                message4.setStatus(MessageStatus.PROCESSED);
                message4.setReceivedAt(LocalDateTime.now());
                message4.setProcessedAt(LocalDateTime.now());
                messageRepository.save(message4);

                // Message 5 - New Refund
                Message message5 = new Message();
                message5.setContent("{\"type\": \"REFUND\", \"orderId\": \"B789\", \"amount\": 150}");
                message5.setSource("REFUND_APP");
                message5.setQueueName("REFUND.QUEUE");
                message5.setStatus(MessageStatus.NEW);
                message5.setReceivedAt(LocalDateTime.now());
                messageRepository.save(message5);

                log.info("Finished initializing Message Database");
            } else {
                log.info("Database already contains data, skipping initialization");
            }
        };
    }
}