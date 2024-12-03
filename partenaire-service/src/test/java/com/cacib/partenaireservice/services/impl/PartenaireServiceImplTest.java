package com.cacib.partenaireservice.services.impl;

import com.cacib.partenaireservice.dtos.PartenaireDTO;
import com.cacib.partenaireservice.dtos.PartenaireRequest;
import com.cacib.partenaireservice.entities.Partenaire;
import com.cacib.partenaireservice.enums.Direction;
import com.cacib.partenaireservice.enums.ProcessedFlowType;
import com.cacib.partenaireservice.exceptions.PartenaireAlreadyExistsException;
import com.cacib.partenaireservice.exceptions.PartenaireNotFoundException;
import com.cacib.partenaireservice.mappers.PartenaireMapper;
import com.cacib.partenaireservice.repositories.IPartenaireRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Ouahib
 * @Date 01/12/2024
 */
@ExtendWith(MockitoExtension.class)
class PartenaireServiceImplTest {
    @Mock
    private IPartenaireRepository partnerRepository;

    @Mock
    private PartenaireMapper partnerMapper;

    @InjectMocks
    private PartenaireServiceImpl partnerService;

    private Partenaire testPartner;
    private PartenaireDTO testPartnerDTO;
    private PartenaireRequest testPartnerRequest;

    @BeforeEach
    void setUp() {
        testPartner = new Partenaire();
        testPartner.setId(1L);
        testPartner.setAlias("test-partner");
        testPartner.setType("TEST");
        testPartner.setDirection(Direction.INBOUND);
        testPartner.setProcessedFlowType(ProcessedFlowType.MESSAGE);
        testPartner.setDescription("Test Partner");

        testPartnerDTO = new PartenaireDTO();
        testPartnerDTO.setId(1L);
        testPartnerDTO.setAlias("test-partner");
        testPartnerDTO.setType("TEST");

        testPartnerRequest = new PartenaireRequest();
        testPartnerRequest.setAlias("test-partner");
        testPartnerRequest.setType("TEST");
        testPartnerRequest.setDirection(Direction.INBOUND);
        testPartnerRequest.setProcessedFlowType(ProcessedFlowType.MESSAGE);
        testPartnerRequest.setDescription("Test Partner");
    }

    @Test
    void createPartner_Success() {
        when(partnerRepository.existsByAlias(anyString())).thenReturn(false);
        when(partnerMapper.toEntity(any(PartenaireRequest.class))).thenReturn(testPartner);
        when(partnerRepository.save(any(Partenaire.class))).thenReturn(testPartner);
        when(partnerMapper.toDTO(any(Partenaire.class))).thenReturn(testPartnerDTO);

        PartenaireDTO result = partnerService.createPartenaire(testPartnerRequest);

        assertNotNull(result);
        assertEquals(testPartnerDTO.getAlias(), result.getAlias());
        verify(partnerRepository).save(any(Partenaire.class));
    }

    @Test
    void createPartner_DuplicateAlias_ThrowsException() {
        when(partnerRepository.existsByAlias(anyString())).thenReturn(true);

        assertThrows(PartenaireAlreadyExistsException.class, () ->
                partnerService.createPartenaire(testPartnerRequest)
        );
        verify(partnerRepository, never()).save(any(Partenaire.class));
    }

    @Test
    void getPartenaireById_Success() {
        when(partnerRepository.findById(1L)).thenReturn(Optional.of(testPartner));
        when(partnerMapper.toDTO(testPartner)).thenReturn(testPartnerDTO);

        PartenaireDTO result = partnerService.getPartenaireById(1L);

        assertNotNull(result);
        assertEquals(testPartnerDTO.getAlias(), result.getAlias());
    }

    @Test
    void getPartenaireById_NotFound_ThrowsException() {
        when(partnerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PartenaireNotFoundException.class, () ->
                partnerService.getPartenaireById(1L)
        );
    }

    @Test
    void getAllPartenaires_Success() {
        List<Partenaire> partners = Arrays.asList(testPartner);
        when(partnerRepository.findAll()).thenReturn(partners);
        when(partnerMapper.toDTO(testPartner)).thenReturn(testPartnerDTO);

        List<PartenaireDTO> results = partnerService.getAllPartenaires();

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void updatePartenaire_Success() {
        when(partnerRepository.findById(1L)).thenReturn(Optional.of(testPartner));
        when(partnerRepository.save(any(Partenaire.class))).thenReturn(testPartner);
        when(partnerMapper.toDTO(any(Partenaire.class))).thenReturn(testPartnerDTO);

        PartenaireDTO result = partnerService.updatePartenaire(1L, testPartnerRequest);

        assertNotNull(result);
        assertEquals(testPartnerDTO.getAlias(), result.getAlias());
        verify(partnerRepository).save(any(Partenaire.class));
    }

    @Test
    void deletePartenaire_Success() {
        when(partnerRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> partnerService.deletePartenaire(1L));
        verify(partnerRepository).deleteById(1L);
    }

    @Test
    void deletePartenaire_NotFound_ThrowsException() {
        when(partnerRepository.existsById(1L)).thenReturn(false);

        assertThrows(PartenaireNotFoundException.class, () ->
                partnerService.deletePartenaire(1L)
        );
        verify(partnerRepository, never()).deleteById(anyLong());
    }
    @Test
    void getPartnerByAlias_Success() {
        // Given
        String alias = "test-partner";
        when(partnerRepository.findByAlias(alias)).thenReturn(Optional.of(testPartner));
        when(partnerMapper.toDTO(testPartner)).thenReturn(testPartnerDTO);

        // When
        PartenaireDTO result = partnerService.getPartenaireByAlias(alias);

        // Then
        assertNotNull(result);
        assertEquals(testPartnerDTO.getAlias(), result.getAlias());
        verify(partnerRepository).findByAlias(alias);
    }

    @Test
    void getPartenaireByAlias_NotFound_ThrowsException() {
        // Given
        String alias = "non-existent-partner";
        when(partnerRepository.findByAlias(alias)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(PartenaireNotFoundException.class, () ->
                partnerService.getPartenaireByAlias(alias)
        );
        verify(partnerRepository).findByAlias(alias);
    }
}