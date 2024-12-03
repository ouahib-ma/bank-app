package com.cacib.messageservice.dtos;

import com.cacib.messageservice.enums.MessageStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MessageDTO {
    private Long id;
    private String content;
    private String source;
    private LocalDateTime receivedAt;
    private LocalDateTime processedAt;
    private String queueName;
    private MessageStatus status;
}