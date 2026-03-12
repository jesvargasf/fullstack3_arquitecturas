package com.example.eventos.controller;

import com.example.eventos.model.Tarea;
import com.example.eventos.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "*") // Permitir peticiones desde React
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @PostMapping
    public ResponseEntity<Map<String, String>> crearTarea(@RequestBody Map<String, String> request) {
        String descripcion = request.get("descripcion");
        
        if (descripcion == null || descripcion.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "La descripción es requerida"));
        }

        // Crear tarea y enviar a cola - respuesta inmediata
        Tarea tarea = tareaService.crearTarea(descripcion);
        
        System.out.println("✅ API POST respondió inmediatamente con OK para tarea: " + tarea.getId());
        
        return ResponseEntity.ok(Map.of(
            "message", "Tarea recibida y en procesamiento",
            "tareaId", tarea.getId(),
            "estado", "PENDIENTE"
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerTarea(@PathVariable String id) {
        Tarea tarea = tareaService.obtenerTarea(id);
        
        if (tarea == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(tarea);
    }

    @GetMapping
    public ResponseEntity<Map<String, Tarea>> obtenerTodasLasTareas() {
        return ResponseEntity.ok(tareaService.obtenerTodasLasTareas());
    }
}
