package com.southsystem.desafio.back.votos.controller.rabbitmq;

import com.southsystem.desafio.back.votos.entities.Pauta;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    public void send(Pauta pauta) {
        rabbitTemplate.convertAndSend(exchange, routingkey, pauta);
        System.out.println("Send msg = " + pauta);

    }
}
