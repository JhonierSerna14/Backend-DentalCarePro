package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;

/**
 * Entidad que representa un horario disponible en el consultorio.
 */
@Entity
@Table(name = "horarios_disponibles")
public class HorarioDisponible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    
    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    // Constructores
    public HorarioDisponible() {}
    
    public HorarioDisponible(LocalDate fecha, LocalTime hora) {
        this.fecha = fecha;
        this.hora = hora;
    }

    // Getters y Setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return this.hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HorarioDisponible)) return false;
        HorarioDisponible that = (HorarioDisponible) o;
        return fecha != null && fecha.equals(that.fecha) && 
               hora != null && hora.equals(that.hora);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
