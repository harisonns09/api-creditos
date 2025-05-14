package com.consultacreditos.consulta.services;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.consultacreditos.consulta.messages.RabbitMQConfig;
import com.consultacreditos.consulta.shared.CreditoDTO;

@Service
public class CreditoMessageConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)  // Corrigido: Usar apenas queues
    public void processarCredito(CreditoDTO creditoDTO) {
        // Lógica para processar a mensagem
        System.out.println("Processando crédito: " + creditoDTO);
        // Aqui você pode, por exemplo, salvar o crédito no banco de dados ou realizar algum outro processamento
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receberCredito(CreditoDTO creditoDTO) {
        System.out.println("Recebido da fila: " + creditoDTO.getNumeroCredito());
    }

    
}
