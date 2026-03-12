package com.example.colas.consumer;

import com.example.colas.model.Mensaje;
import com.example.colas.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumidor {

    /**
     * Consume mensajes de la cola simple
     */
    @RabbitListener(queues = RabbitMQConfig.COLA_MENSAJES)
    public void consumirMensaje(Mensaje mensaje) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📥 CONSUMIDOR: Mensaje recibido de la cola");
        System.out.println("🆔 ID: " + mensaje.getId());
        System.out.println("📝 Contenido: " + mensaje.getContenido());
        System.out.println("🎯 Tipo: " + mensaje.getTipo());
        System.out.println("📍 Origen: " + mensaje.getOrigen());
        System.out.println("⏰ Timestamp: " + mensaje.getTimestamp());
        System.out.println("=".repeat(50));
        
        // Simular procesamiento simple
        procesarMensaje(mensaje);
    }

    private void procesarMensaje(Mensaje mensaje) {
        try {
            // Simular trabajo (2 segundos)
            System.out.println("⏳ CONSUMIDOR: Procesando mensaje... (2 segundos)");
            Thread.sleep(2000);
            
            System.out.println("✅ CONSUMIDOR: Mensaje procesado exitosamente");
            System.out.println("🎉 RESULTADO: '" + mensaje.getContenido() + "' ha sido procesado\n");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("❌ CONSUMIDOR: Procesamiento interrumpido");
        }
    }
}
