package com.southsystem.desafio.back.votos.controller.rabbitmq;

import com.southsystem.desafio.back.votos.entities.Pauta;
import com.southsystem.desafio.back.votos.repository.PautaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQReceiver {

    @Autowired
    PautaRepository pautaRepository;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receive(final Pauta pauta) {
        pautaRepository.save(pauta);
        System.out.println("Received the following message from the queue= " + pauta);
    }
}
