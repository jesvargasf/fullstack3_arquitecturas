package com.example.colas.model;

import java.time.LocalDateTime;

public class Mensaje {
    private String id;
    private String tipo;
    private String contenido;
    private String prioridad; // ALTA, MEDIA, BAJA
    private LocalDateTime timestamp;
    private String origen;

    public Mensaje() {
    }

    public Mensaje(String tipo, String contenido, String prioridad, String origen) {
        this.id = java.util.UUID.randomUUID().toString();
        this.tipo = tipo;
        this.contenido = contenido;
        this.prioridad = prioridad;
        this.timestamp = LocalDateTime.now();
        this.origen = origen;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", prioridad='" + prioridad + '\'' +
                ", timestamp=" + timestamp +
                ", origen='" + origen + '\'' +
                '}';
    }
}
