package com.example.eventos.service;

import com.example.eventos.model.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TareaService {

    @Autowired
    private AsyncTareaProcessor asyncTareaProcessor;

    // Almacenamiento en memoria para fines educativos
    private final Map<String, Tarea> tareas = new ConcurrentHashMap<>();

    public Tarea crearTarea(String descripcion) {
        Tarea tarea = new Tarea(descripcion);
        tareas.put(tarea.getId(), tarea);
        
        // Enviar a procesamiento asíncrono (sin RabbitMQ)
        asyncTareaProcessor.procesarTareaAsync(tarea, this);
        
        System.out.println("🚀 Tarea creada y enviada a procesamiento asíncrono: " + tarea.getId());
        return tarea;
    }

    public Tarea obtenerTarea(String id) {
        return tareas.get(id);
    }

    public Map<String, Tarea> obtenerTodasLasTareas() {
        return new HashMap<>(tareas);
    }

    public void actualizarTarea(Tarea tarea) {
        tareas.put(tarea.getId(), tarea);
        System.out.println("📝 Tarea actualizada: " + tarea.getId() + " - Estado: " + tarea.getEstado());
    }
}
