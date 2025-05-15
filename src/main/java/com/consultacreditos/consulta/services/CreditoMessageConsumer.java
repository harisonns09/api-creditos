package com.consultacreditos.consulta.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.consultacreditos.consulta.shared.CreditoDTO;

@Service
public class CreditoMessageConsumer {


    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void processarCredito(CreditoDTO creditoDTO) {
        System.out.println("Recebido da fila: " + creditoDTO.getNumeroCredito());
    }

}
