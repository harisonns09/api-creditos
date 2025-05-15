package com.consultacreditos.consulta;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.consultacreditos.consulta.services.CreditoMessageConsumer;
import com.consultacreditos.consulta.shared.CreditoDTO;

@ExtendWith(MockitoExtension.class)
public class CreditoMessageConsumerTest {

    private CreditoMessageConsumer consumer;

    @BeforeEach
    public void setup() {
        consumer = new CreditoMessageConsumer();
    }

    @Test
    public void testProcessarCredito_imprimeMensagem() {
        CreditoDTO dto = new CreditoDTO();
        dto.setNumeroCredito("CR123");

        // Captura output do System.out para verificar, se quiser
        consumer.processarCredito(dto);

        // Como só imprime no console, garantimos que o método roda sem exceção
    }
}
