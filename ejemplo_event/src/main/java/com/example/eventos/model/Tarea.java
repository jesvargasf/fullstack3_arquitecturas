package com.example.eventos.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Tarea {
    private String id;
    private String descripcion;
    private String estado; // PENDIENTE, PROCESANDO, COMPLETADA, ERROR
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaProcesamiento;
    private String resultado;
    private String errorMensaje;

    public Tarea() {
    }

    public Tarea(String descripcion) {
        this.id = UUID.randomUUID().toString();
        this.descripcion = descripcion;
        this.estado = "PENDIENTE";
        this.fechaCreacion = LocalDateTime.now();
    }

    public Tarea(String id, String descripcion, String estado, LocalDateTime fechaCreacion, LocalDateTime fechaProcesamiento, String resultado, String errorMensaje) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaProcesamiento = fechaProcesamiento;
        this.resultado = resultado;
        this.errorMensaje = errorMensaje;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaProcesamiento() {
        return fechaProcesamiento;
    }

    public void setFechaProcesamiento(LocalDateTime fechaProcesamiento) {
        this.fechaProcesamiento = fechaProcesamiento;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getErrorMensaje() {
        return errorMensaje;
    }

    public void setErrorMensaje(String errorMensaje) {
        this.errorMensaje = errorMensaje;
    }
}
