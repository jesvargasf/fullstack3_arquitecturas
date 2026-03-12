package com.example.eventos.service;

import com.example.eventos.model.Tarea;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AsyncTareaProcessor {

    private final ExecutorService executorService;

    public AsyncTareaProcessor() {
        this.executorService = Executors.newFixedThreadPool(3); // Pool de 3 hilos
    }

    @PostConstruct
    public void init() {
        System.out.println("🔧 AsyncTareaProcessor iniciado con pool de 3 hilos (sin RabbitMQ)");
    }

    public void procesarTareaAsync(Tarea tarea, TareaService tareaService) {
        System.out.println("📤 Enviando tarea a procesamiento asíncrono: " + tarea.getId());
        
        executorService.submit(() -> {
            procesarTarea(tarea, tareaService);
        });
    }

    private void procesarTarea(Tarea tarea, TareaService tareaService) {
        System.out.println("🎧 Hilo procesando tarea: " + tarea.getId() + " - " + tarea.getDescripcion());
        
        try {
            // Actualizar estado a PROCESANDO
            tarea.setEstado("PROCESANDO");
            tarea.setFechaProcesamiento(java.time.LocalDateTime.now());
            tareaService.actualizarTarea(tarea);
            
            // Simular procesamiento asíncrono (5-10 segundos)
            int tiempoProcesamiento = 5000 + (int)(Math.random() * 5000);
            System.out.println("⏳ Procesando tarea " + tarea.getId() + " por " + tiempoProcesamiento + "ms...");
            
            Thread.sleep(tiempoProcesamiento);
            
            // Simular posible error (50% de probabilidad)
            if (Math.random() < 0.5) {
                throw new RuntimeException("Error simulado durante el procesamiento");
            }
            
            // Marcar como COMPLETADA
            tarea.setEstado("COMPLETADA");
            tarea.setResultado("Tarea procesada exitosamente en " + tiempoProcesamiento + "ms");
            tareaService.actualizarTarea(tarea);
            
            System.out.println("✅ Tarea completada: " + tarea.getId());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            tarea.setEstado("ERROR");
            tarea.setErrorMensaje("Procesamiento interrumpido");
            tareaService.actualizarTarea(tarea);
            
        } catch (Exception e) {
            // Marcar como ERROR
            tarea.setEstado("ERROR");
            tarea.setErrorMensaje(e.getMessage());
            tareaService.actualizarTarea(tarea);
            
            System.out.println("❌ Error en tarea " + tarea.getId() + ": " + e.getMessage());
        }
    }
}
