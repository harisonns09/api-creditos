package com.consultacreditos.consulta.services;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.consultacreditos.consulta.shared.CreditoDTO;

@Service
public class CreditoMessageProducer {
    
    private final AmqpTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingKey;

    public CreditoMessageProducer(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarCreditoParaFila(CreditoDTO creditoDTO) {
        System.out.println("Enviando para a fila: " + creditoDTO.getNumeroCredito());
        rabbitTemplate.convertAndSend(exchange, routingKey, creditoDTO);
    }

}
