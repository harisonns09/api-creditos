package com.consultacreditos.consulta;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.consultacreditos.consulta.services.CreditoMessageProducer;
import com.consultacreditos.consulta.shared.CreditoDTO;



@ExtendWith(MockitoExtension.class)
public class CreditoMessageProducerTest {

    @Mock
    private AmqpTemplate amqpTemplate;

    @InjectMocks
    private CreditoMessageProducer producer;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(producer, "exchange", "creditos.exchange");
        ReflectionTestUtils.setField(producer, "routingKey", "creditos.routing.key");
    }

    @Test
    public void testEnviarCreditoParaFila_chamaConvertAndSend() {
        CreditoDTO dto = new CreditoDTO();
        dto.setNumeroCredito("CR123");

        producer.enviarCreditoParaFila(dto);

        verify(amqpTemplate).convertAndSend("creditos.exchange", "creditos.routing.key", dto);
    }
}

