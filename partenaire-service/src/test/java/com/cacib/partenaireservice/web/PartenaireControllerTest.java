package com.cacib.partenaireservice.web;

import com.cacib.partenaireservice.dtos.PartenaireDTO;
import com.cacib.partenaireservice.dtos.PartenaireRequest;
import com.cacib.partenaireservice.enums.Direction;
import com.cacib.partenaireservice.enums.ProcessedFlowType;
import com.cacib.partenaireservice.services.impl.PartenaireServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Ouahib
 * @Date 01/12/2024
 */
@WebMvcTest(PartenaireController.class)
class PartenaireControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PartenaireServiceImpl partnerService;

    private PartenaireDTO testPartnerDTO;
    private PartenaireRequest testPartnerRequest;

    @BeforeEach
    void setUp() {
        testPartnerDTO = new PartenaireDTO();
        testPartnerDTO.setId(1L);
        testPartnerDTO.setAlias("test-partner");
        testPartnerDTO.setType("TEST");
        testPartnerDTO.setDirection(Direction.INBOUND);

        testPartnerRequest = new PartenaireRequest();
        testPartnerRequest.setAlias("test-partner");
        testPartnerRequest.setType("TEST");
        testPartnerRequest.setDirection(Direction.INBOUND);
        testPartnerRequest.setProcessedFlowType(ProcessedFlowType.MESSAGE);
        testPartnerRequest.setDescription("Test Partenaire");
    }

    @Test
    void createPartenaire_Success() throws Exception {
        when(partnerService.createPartenaire(any(PartenaireRequest.class)))
                .thenReturn(testPartnerDTO);

        mockMvc.perform(post("/partenaires")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPartnerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testPartnerDTO.getId()))
                .andExpect(jsonPath("$.alias").value(testPartnerDTO.getAlias()));
    }

    @Test
    void getPartenaire_Success() throws Exception {
        when(partnerService.getPartenaireById(1L)).thenReturn(testPartnerDTO);

        mockMvc.perform(get("/partenaires/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testPartnerDTO.getId()))
                .andExpect(jsonPath("$.alias").value(testPartnerDTO.getAlias()));
    }

    @Test
    void getAllPartenaires_Success() throws Exception {
        List<PartenaireDTO> partners = Collections.singletonList(testPartnerDTO);
        when(partnerService.getAllPartenaires()).thenReturn(partners);

        mockMvc.perform(get("/partenaires"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testPartnerDTO.getId()))
                .andExpect(jsonPath("$[0].alias").value(testPartnerDTO.getAlias()));
    }

    @Test
    void updatePartenaire_Success() throws Exception {
        when(partnerService.updatePartenaire(eq(1L), any(PartenaireRequest.class)))
                .thenReturn(testPartnerDTO);

        mockMvc.perform(put("/partenaires/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPartnerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testPartnerDTO.getId()))
                .andExpect(jsonPath("$.alias").value(testPartnerDTO.getAlias()));
    }

    @Test
    void deletePartenaire_Success() throws Exception {
        doNothing().when(partnerService).deletePartenaire(1L);

        mockMvc.perform(delete("/partenaires/1"))
                .andExpect(status().isNoContent());

        verify(partnerService).deletePartenaire(1L);
    }

    @Test
    void getPartenaireByAlias_Success() throws Exception {
        // Given
        String alias = "test-partner";
        when(partnerService.getPartenaireByAlias(alias)).thenReturn(testPartnerDTO);

        // When & Then
        mockMvc.perform(get("/partenaires/alias/{alias}", alias))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testPartnerDTO.getId()))
                .andExpect(jsonPath("$.alias").value(testPartnerDTO.getAlias()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(partnerService).getPartenaireByAlias(alias);
    }

    @Test
    void createPartenaire_ValidationFailure() throws Exception {
        PartenaireRequest invalidRequest = new PartenaireRequest();
        // Ne pas définir les champs requis pour provoquer une erreur de validation

        mockMvc.perform(post("/partenaires")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}