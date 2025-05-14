package com.consultacreditos.consulta.messages;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitMQConfig {

    // Nome da fila
    public static final String QUEUE_NAME = "credito.fila";

    // Definição da fila
    @Bean
    public Queue creditosQueue() {
        return new Queue(QUEUE_NAME, true);  // Fila durável
    }

    // Definição da Exchange (nesse caso, uma Direct Exchange)
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("creditos.exchange");
    }

    // Definição do Binding entre a fila e a Exchange
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(creditosQueue()).to(directExchange()).with("creditos.routing.key");
    }

    // Conversor de mensagens
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Configuração do AmqpTemplate
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
    
}
