package com.consultacreditos.consulta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.consultacreditos.consulta.model.Credito;
import com.consultacreditos.consulta.repository.CreditoRepository;
import com.consultacreditos.consulta.services.CreditoService;
import com.consultacreditos.consulta.shared.CreditoDTO;

@ExtendWith(MockitoExtension.class)
public class CreditoServiceTest {

    @Mock
    private CreditoRepository creditoRepository;

    @InjectMocks
    private CreditoService creditoService;

    private Credito credito;

    @BeforeEach
    void setUp() {
        credito = new Credito();
        credito.setId(1L);
        credito.setNumeroNfse("12345");
        credito.setNumeroCredito("CRED001");
    }

    @Test
    void testBuscarPorNumeroNota_Encontrado() {
        when(creditoRepository.findByNumeroNfse("12345")).thenReturn(List.of(credito));
        List<Credito> resultado = creditoService.listarPorNumeroNfse("12345");
        assertFalse(resultado.isEmpty());
        assertEquals("12345", resultado.get(0).getNumeroNfse());
    }

    @Test
    void testBuscarPorNumeroNota_NaoEncontrado() {
        when(creditoRepository.findByNumeroNfse("00000")).thenReturn(List.of());
        List<Credito> resultado = creditoService.listarPorNumeroNfse("00000");
        assertTrue(resultado.isEmpty());
    }

    @Test
    void testBuscarPorNumeroCredito_Encontrado() {
        when(creditoRepository.findByNumeroCredito("CRED001")).thenReturn(Optional.of(credito));
        Optional<Credito> resultado = creditoService.buscarPorNumeroCredito("CRED001");
        assertTrue(resultado.isPresent());
        assertEquals("CRED001", resultado.get().getNumeroCredito());
    }

    @Test
    void testBuscarPorNumeroCredito_NaoEncontrado() {
        when(creditoRepository.findByNumeroCredito("INVALIDO")).thenReturn(Optional.empty());
        Optional<Credito> resultado = creditoService.buscarPorNumeroCredito("INVALIDO");
        assertTrue(resultado.isEmpty());
    }

    
}
