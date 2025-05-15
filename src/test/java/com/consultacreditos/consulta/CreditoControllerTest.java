package com.consultacreditos.consulta;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.consultacreditos.consulta.model.Credito;
import com.consultacreditos.consulta.services.CreditoMessageProducer;
import com.consultacreditos.consulta.services.CreditoService;
import com.consultacreditos.consulta.shared.CreditoDTO;
import com.consultacreditos.consulta.view.controller.CreditoController;


@WebMvcTest(CreditoController.class)
class CreditoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditoMessageProducer creditoProducer;

    @MockBean
    private CreditoService creditoService;

    @MockBean
    private ModelMapper modelMapper;

    private Credito credito;
    private CreditoDTO dto;

    @BeforeEach
    void setup() {
        credito = new Credito();
        credito.setId(1L);
        credito.setNumeroNfse("12345");
        credito.setNumeroCredito("CRED001");

        dto = new CreditoDTO();
        dto.setNumeroNfse("12345");
        dto.setNumeroCredito("CRED001");
    }

    @Test
    void testBuscarPorNumeroNota_Encontrado() throws Exception {
        when(creditoService.listarPorNumeroNfse("12345")).thenReturn(List.of(credito));
        when(modelMapper.map(any(Credito.class), eq(CreditoDTO.class))).thenReturn(dto);

        mockMvc.perform(get("/api/creditos/12345"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].numeroNfse").value("12345"))
            .andExpect(jsonPath("$[0].numeroCredito").value("CRED001"));
    }

    @Test
    void testBuscarPorNumeroNota_NaoEncontrado() throws Exception {
        when(creditoService.listarPorNumeroNfse("00000")).thenReturn(List.of());

        mockMvc.perform(get("/api/creditos/00000"))
            .andExpect(status().isOk())
            .andExpect(content().string("[]"));
    }

    @Test
    void testBuscarPorNumeroCredito_Encontrado() throws Exception {
        when(creditoService.buscarPorNumeroCredito("CRED001")).thenReturn(Optional.of(credito));
        when(modelMapper.map(any(Credito.class), eq(CreditoDTO.class))).thenReturn(dto);

        mockMvc.perform(get("/api/creditos/credito/CRED001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.numeroNfse").value("12345"))
            .andExpect(jsonPath("$.numeroCredito").value("CRED001"));
    }

    @Test
    void testBuscarPorNumeroCredito_NaoEncontrado() throws Exception {
        when(creditoService.buscarPorNumeroCredito("INVALIDO")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/creditos/credito/INVALIDO"))
            .andExpect(status().isNotFound());
    }
    

}
