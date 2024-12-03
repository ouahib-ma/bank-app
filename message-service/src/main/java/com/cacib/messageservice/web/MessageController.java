package com.cacib.messageservice.web;


import com.cacib.messageservice.dtos.MessageDTO;
import com.cacib.messageservice.dtos.MessageRequest;
import com.cacib.messageservice.enums.MessageStatus;
import com.cacib.messageservice.services.IMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@Tag(name = "Message Management", description = "APIs for managing messages")
public class MessageController {
    private final IMessageService messageService;

    @GetMapping
    @Operation(summary = "Retrieve paginated messages")
    public ResponseEntity<Page<MessageDTO>> getAllMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(messageService.getAllMessages(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get message details by ID")
    public ResponseEntity<MessageDTO> getMessage(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.getMessage(id));
    }

    @PostMapping
    public ResponseEntity<MessageDTO> processMessage(@Valid @RequestBody MessageRequest request) {
        return ResponseEntity.ok(messageService.processMessage(
                request.getContent(),
                request.getSource(),
                request.getQueueName()
        ));
    }

    @PostMapping("/batch")
    public ResponseEntity<Void> processBatchMessages(@Valid @RequestBody List<MessageRequest> requests) {
        messageService.batchProcessMessages(
                requests.stream().map(MessageRequest::getContent).collect(Collectors.toList()),
                requests.get(0).getSource(),
                requests.get(0).getQueueName()
        );
        return ResponseEntity.ok().build();
    }
}