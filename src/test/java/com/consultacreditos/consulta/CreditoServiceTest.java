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
    public void setup() {
        credito = new Credito();
        credito.setId(1L);
        credito.setNumeroCredito("CR123");
        credito.setNumeroNfse("NFSE123");
        // preencher os campos que precisar
    }

    @Test
    public void testObterTodos_retornaListaDeDTO() {
        List<Credito> lista = List.of(credito);
        when(creditoRepository.findAll()).thenReturn(lista);

        List<CreditoDTO> resultado = creditoService.obterTodos();

        assertFalse(resultado.isEmpty());
        assertEquals("CR123", resultado.get(0).getNumeroCredito());
        verify(creditoRepository).findAll();
    }

    @Test
    public void testListarPorNumeroNfse_retornaLista() {
        List<Credito> lista = List.of(credito);
        when(creditoRepository.findByNumeroNfse("NFSE123")).thenReturn(lista);

        List<Credito> resultado = creditoService.listarPorNumeroNfse("NFSE123");

        assertEquals(1, resultado.size());
        assertEquals("NFSE123", resultado.get(0).getNumeroNfse());
        verify(creditoRepository).findByNumeroNfse("NFSE123");
    }

    @Test
    public void testBuscarPorNumeroCredito_retornaCredito() {
        when(creditoRepository.findByNumeroCredito("CR123")).thenReturn(Optional.of(credito));

        Optional<Credito> resultado = creditoService.buscarPorNumeroCredito("CR123");

        assertTrue(resultado.isPresent());
        assertEquals("CR123", resultado.get().getNumeroCredito());
        verify(creditoRepository).findByNumeroCredito("CR123");
    }

    @Test
    public void testBuscarPorNumeroCredito_naoEncontrado() {
        when(creditoRepository.findByNumeroCredito("CR999")).thenReturn(Optional.empty());

        Optional<Credito> resultado = creditoService.buscarPorNumeroCredito("CR999");

        assertFalse(resultado.isPresent());
        verify(creditoRepository).findByNumeroCredito("CR999");
    }
    
}
