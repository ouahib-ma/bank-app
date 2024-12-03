package com.cacib.messageservice.entities;

import com.cacib.messageservice.enums.MessageStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "messages", indexes = {
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_received_at", columnList = "received_at")
})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "queue_name", nullable = false)
    private String queueName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MessageStatus status;

    @Version
    private Long version;

    @PrePersist
    protected void onCreate() {
        receivedAt = LocalDateTime.now();
    }
}