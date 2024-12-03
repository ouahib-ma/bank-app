package com.cacib.partenaireservice.entities;
import com.cacib.partenaireservice.enums.Direction;
import com.cacib.partenaireservice.enums.ProcessedFlowType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "partenaires")
public class Partenaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Alias is required")
    @Column(unique = true)
    private String alias;
    @NotBlank(message = "Type is required")
    private String type;
    @Enumerated(EnumType.STRING)
    private Direction direction;
    private String application;
    @Enumerated(EnumType.STRING)
    private ProcessedFlowType processedFlowType;
    @NotBlank(message = "Description is required")
    private String description;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
