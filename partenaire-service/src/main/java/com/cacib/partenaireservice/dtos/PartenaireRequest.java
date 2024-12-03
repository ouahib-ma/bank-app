package com.cacib.partenaireservice.dtos;

import com.cacib.partenaireservice.enums.Direction;
import com.cacib.partenaireservice.enums.ProcessedFlowType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PartenaireRequest {
    @NotBlank(message = "Alias is required")
    private String alias;
    @NotBlank(message = "Type is required")
    private String type;
    private Direction direction;
    private String application;
    private ProcessedFlowType processedFlowType;
    @NotBlank(message = "Description is required")
    private String description;
}
