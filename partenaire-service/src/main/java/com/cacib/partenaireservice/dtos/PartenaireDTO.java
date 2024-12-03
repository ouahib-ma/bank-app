package com.cacib.partenaireservice.dtos;

import com.cacib.partenaireservice.enums.Direction;
import com.cacib.partenaireservice.enums.ProcessedFlowType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PartenaireDTO {
    private Long id;
    private String alias;
    private String type;
    private Direction direction;
    private String application;
    private ProcessedFlowType processedFlowType;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
