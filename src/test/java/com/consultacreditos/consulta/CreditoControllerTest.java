package com.consultacreditos.consulta;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class CreditoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditoService creditoService;

    @MockBean
    private CreditoMessageProducer creditoProducer;

    private Credito credito;

    @BeforeEach
    public void setup() {
        credito = new Credito();
        credito.setId(1L);
        credito.setNumeroCredito("CR123");
        credito.setNumeroNfse("NFSE123");
    }

    @Test
    public void testListarPorNumeroNfse_retornaListaDTO() throws Exception {
        when(creditoService.listarPorNumeroNfse("NFSE123")).thenReturn(List.of(credito));

        mockMvc.perform(get("/api/creditos/NFSE123"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].numeroCredito").value("CR123"))
            .andExpect(jsonPath("$[0].numeroNfse").value("NFSE123"));

        verify(creditoService).listarPorNumeroNfse("NFSE123");
    }

    @Test
    public void testBuscarPorNumeroCredito_retornaCreditoDTO_eEnviaParaFila() throws Exception {
        when(creditoService.buscarPorNumeroCredito("CR123")).thenReturn(Optional.of(credito));

        mockMvc.perform(get("/api/creditos/credito/CR123"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.numeroCredito").value("CR123"));

        verify(creditoService).buscarPorNumeroCredito("CR123");
        verify(creditoProducer).enviarCreditoParaFila(any(CreditoDTO.class));
    }

    @Test
    public void testBuscarPorNumeroCredito_naoEncontrado() throws Exception {
        when(creditoService.buscarPorNumeroCredito("CR999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/creditos/credito/CR999"))
            .andExpect(status().isNotFound());

        verify(creditoService).buscarPorNumeroCredito("CR999");
        verify(creditoProducer, never()).enviarCreditoParaFila(any());
    }
}

