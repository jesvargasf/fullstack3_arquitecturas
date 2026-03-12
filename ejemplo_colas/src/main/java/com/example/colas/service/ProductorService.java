package com.example.colas.service;

import com.example.colas.model.Mensaje;
import com.example.colas.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductorService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Envía un mensaje a la cola simple
     */
    public void enviarMensaje(Mensaje mensaje) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_SIMPLE,
            "routing.key.simple",
            mensaje
        );
        
        System.out.println("📤 PRODUCTOR: Mensaje enviado a la cola -> " + mensaje.getContenido());
    }
}
