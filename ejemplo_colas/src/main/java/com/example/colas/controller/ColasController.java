package com.example.colas.controller;

import com.example.colas.model.Mensaje;
import com.example.colas.service.ProductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ColasController {

    @Autowired
    private ProductorService productorService;

    /**
     * Enviar un mensaje a la cola
     */
    @PostMapping("/enviar")
    public ResponseEntity<Map<String, String>> enviarMensaje(@RequestBody Map<String, String> request) {
        String contenido = request.get("contenido");
        
        if (contenido == null || contenido.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "El contenido es requerido"));
        }

        Mensaje mensaje = new Mensaje("MENSAJE", contenido, "NORMAL", "API_CLIENT");
        productorService.enviarMensaje(mensaje);
        
        System.out.println("✅ API: Mensaje enviado a la cola: " + contenido);
        
        return ResponseEntity.ok(Map.of(
            "message", "Mensaje enviado a la cola correctamente",
            "contenido", contenido,
            "estado", "EN_COLA"
        ));
    }

    /**
     * Ver información del sistema
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getInfo() {
        return ResponseEntity.ok(Map.of(
            "servicio", "Demo Simple de Colas",
            "concepto", "Productor - Cola - Consumidor",
            "tecnologia", "RabbitMQ + Spring Boot",
            "cola", "mensajes.simple",
            "endpoint_envio", "POST /api/enviar"
        ));
    }

    /**
     * Demostración simple
     */
    @PostMapping("/demo")
    public ResponseEntity<Map<String, String>> demoSimple() {
        // Enviar 3 mensajes de ejemplo
        productorService.enviarMensaje(new Mensaje("DEMO", "Hola mundo desde la cola", "NORMAL", "DEMO"));
        productorService.enviarMensaje(new Mensaje("DEMO", "Este es un segundo mensaje", "NORMAL", "DEMO"));
        productorService.enviarMensaje(new Mensaje("DEMO", "Y este es el tercer mensaje", "NORMAL", "DEMO"));
        
        System.out.println("🎯 API: Demo simple ejecutada - 3 mensajes enviados");
        
        return ResponseEntity.ok(Map.of(
            "message", "Demo simple ejecutada",
            "mensajes_enviados", "3",
            "instruccion", "Revisa los logs del consumidor para ver el procesamiento"
        ));
    }
}
