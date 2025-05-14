package com.consultacreditos.consulta.services;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.consultacreditos.consulta.messages.RabbitMQConfig;
import com.consultacreditos.consulta.shared.CreditoDTO;

@Service
public class CreditoMessageProducer {
    

    private final AmqpTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue}") // Ou define diretamente no código
    private String queue;

    public CreditoMessageProducer(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarCreditoParaFila(CreditoDTO creditoDTO) {
        rabbitTemplate.convertAndSend(queue, creditoDTO);
    }

}
