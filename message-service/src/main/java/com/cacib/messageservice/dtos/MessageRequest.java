package com.cacib.messageservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageRequest {
    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "Source is required")
    private String source;

    @NotBlank(message = "Queue name is required")
    private String queueName;
}