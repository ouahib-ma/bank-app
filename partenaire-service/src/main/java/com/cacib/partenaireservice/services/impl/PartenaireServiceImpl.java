package com.cacib.partenaireservice.services.impl;

import com.cacib.partenaireservice.dtos.PartenaireDTO;
import com.cacib.partenaireservice.dtos.PartenaireRequest;
import com.cacib.partenaireservice.entities.Partenaire;
import com.cacib.partenaireservice.enums.Direction;
import com.cacib.partenaireservice.exceptions.PartenaireAlreadyExistsException;
import com.cacib.partenaireservice.exceptions.PartenaireNotFoundException;
import com.cacib.partenaireservice.mappers.PartenaireMapper;
import com.cacib.partenaireservice.repositories.IPartenaireRepository;
import com.cacib.partenaireservice.services.IPartenaireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartenaireServiceImpl implements IPartenaireService {
    private final IPartenaireRepository partenaireRepository;
    private final PartenaireMapper partenaireMapper;
    private static final String PARTENAIRE_NOT_FOUND = "Partenaire not found with id: ";

    @Transactional
    public PartenaireDTO createPartenaire(PartenaireRequest request) {
        if (partenaireRepository.existsByAlias(request.getAlias())) {
            throw new PartenaireAlreadyExistsException("Partenaire with alias " + request.getAlias() + " already exists");
        }

        Partenaire partner = partenaireMapper.toEntity(request);
        Partenaire savedPartner = partenaireRepository.save(partner);
        log.info("Created new partner with alias: {}", savedPartner.getAlias());
        return partenaireMapper.toDTO(savedPartner);
    }

    @Transactional(readOnly = true)
    public PartenaireDTO getPartenaireById(Long id) {
        return partenaireRepository.findById(id)
                .map(partenaireMapper::toDTO)
                .orElseThrow(() -> new PartenaireNotFoundException(PARTENAIRE_NOT_FOUND + id));
    }

    @Transactional(readOnly = true)
    public PartenaireDTO getPartenaireByAlias(String alias) {
        return partenaireRepository.findByAlias(alias)
                .map(partenaireMapper::toDTO)
                .orElseThrow(() -> new PartenaireNotFoundException("Partenaire not found with alias: " + alias));
    }

    @Transactional(readOnly = true)
    public List<PartenaireDTO> getAllPartenaires() {
        return partenaireRepository.findAll().stream()
                .map(partenaireMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional()
    public List<PartenaireDTO> getPartenairesByDirection(Direction direction) {
        return partenaireRepository.findByDirection(direction).stream()
                .map(partenaireMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PartenaireDTO updatePartenaire(Long id, PartenaireRequest request) {
        Partenaire partner = partenaireRepository.findById(id)
                .orElseThrow(() -> new PartenaireNotFoundException(PARTENAIRE_NOT_FOUND + id));

        // Vérifier si le nouvel alias est déjà utilisé par un autre partenaire
        if (!partner.getAlias().equals(request.getAlias()) &&
                partenaireRepository.existsByAlias(request.getAlias())) {
            throw new PartenaireAlreadyExistsException("Partenaire with alias " + request.getAlias() + " already exists");
        }

        partner.setAlias(request.getAlias());
        partner.setType(request.getType());
        partner.setDirection(request.getDirection());
        partner.setApplication(request.getApplication());
        partner.setProcessedFlowType(request.getProcessedFlowType());
        partner.setDescription(request.getDescription());

        Partenaire updatedPartner = partenaireRepository.save(partner);
        log.info("Updated partner with id: {}", id);
        return partenaireMapper.toDTO(updatedPartner);
    }

    @Transactional
    public void deletePartenaire(Long id) {
        if (!partenaireRepository.existsById(id)) {
            throw new PartenaireNotFoundException(PARTENAIRE_NOT_FOUND + id);
        }
        partenaireRepository.deleteById(id);
        log.info("Deleted partner with id: {}", id);
    }
}
