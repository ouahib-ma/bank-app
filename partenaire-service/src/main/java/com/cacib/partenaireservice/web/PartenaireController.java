package com.cacib.partenaireservice.web;

import com.cacib.partenaireservice.dtos.PartenaireDTO;
import com.cacib.partenaireservice.dtos.PartenaireRequest;
import com.cacib.partenaireservice.enums.Direction;
import com.cacib.partenaireservice.services.IPartenaireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/partenaires")
@RequiredArgsConstructor
@Tag(name = "Partenaires Management", description = "APIs for managing partenaires")
public class PartenaireController {
    private final IPartenaireService partenaireService;

    @PostMapping
    @Operation(summary = "Create a new -")
    public ResponseEntity<PartenaireDTO> createPartenaire(@Valid @RequestBody PartenaireRequest request) {
        return ResponseEntity.ok(partenaireService.createPartenaire(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get partenaire by ID")
    public ResponseEntity<PartenaireDTO> getPartenaire(@PathVariable Long id) {
        return ResponseEntity.ok(partenaireService.getPartenaireById(id));
    }

    @GetMapping("/alias/{alias}")
    @Operation(summary = "Get partenaire by alias")
    public ResponseEntity<PartenaireDTO> getPartenaireByAlias(@PathVariable String alias) {
        return ResponseEntity.ok(partenaireService.getPartenaireByAlias(alias));
    }

    @GetMapping
    @Operation(summary = "Get all partenaires")
    public ResponseEntity<List<PartenaireDTO>> getAllPartenaires() {
        return ResponseEntity.ok(partenaireService.getAllPartenaires());
    }

    @GetMapping("/direction/{direction}")
    @Operation(summary = "Get partenaires by direction")
    public ResponseEntity<List<PartenaireDTO>> getPartenairesByDirection(
            @PathVariable Direction direction) {
        return ResponseEntity.ok(partenaireService.getPartenairesByDirection(direction));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a partenaire")
    public ResponseEntity<PartenaireDTO> updatePartenaire(
            @PathVariable Long id,
            @Valid @RequestBody PartenaireRequest request) {
        return ResponseEntity.ok(partenaireService.updatePartenaire(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a partenaire")
    public ResponseEntity<Void> deletePartenaire(@PathVariable Long id) {
        partenaireService.deletePartenaire(id);
        return ResponseEntity.noContent().build();
    }
}
