package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

/**
 * Entidad que representa una cita odontológica en el sistema.
 * 
 * @author DentalCarePro Team
 * @version 1.0
 */
@Entity
@Table(name = "citas_odontologicas")
public class CitaOdontologica {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "fecha", nullable = false)
	private LocalDate fecha;
	
	@Column(name = "hora", nullable = false)
	private LocalTime hora;
	
	@Column(name = "motivo_consulta", columnDefinition = "TEXT")
	private String motivoConsulta;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado")
	private EstadoCita estado = EstadoCita.PROGRAMADA;

	// Relaciones
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paciente_cedula", nullable = false)
	@JsonManagedReference
	private Paciente paciente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "odontologo_id", nullable = false)
	@JsonManagedReference
	private Odontologo odontologo;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "cita_odontologica_id")
	@JsonBackReference
	private List<Tratamiento> tratamientos = new ArrayList<>();
	
	// Enum para estados de cita
	public enum EstadoCita {
		PROGRAMADA, CONFIRMADA, EN_CURSO, COMPLETADA, CANCELADA, NO_ASISTIO
	}
	
	// Constructores
	public CitaOdontologica() {
		this.tratamientos = new ArrayList<>();
		this.estado = EstadoCita.PROGRAMADA;
	}
	
	public CitaOdontologica(LocalDate fecha, LocalTime hora, String motivoConsulta, 
							Paciente paciente, Odontologo odontologo) {
		this();
		this.fecha = fecha;
		this.hora = hora;
		this.motivoConsulta = motivoConsulta;
		this.paciente = paciente;
		this.odontologo = odontologo;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public String getMotivoConsulta() {
		return motivoConsulta;
	}

	public void setMotivoConsulta(String motivoConsulta) {
		this.motivoConsulta = motivoConsulta;
	}

	public EstadoCita getEstado() {
		return estado;
	}

	public void setEstado(EstadoCita estado) {
		this.estado = estado;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Odontologo getOdontologo() {
		return odontologo;
	}

	public void setOdontologo(Odontologo odontologo) {
		this.odontologo = odontologo;
	}

	public List<Tratamiento> getTratamientos() {
		return tratamientos;
	}

	public void setTratamientos(List<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
	}
	
	// Métodos auxiliares para tratamientos
	public void addTratamiento(Tratamiento tratamiento) {
		if (this.tratamientos == null) {
			this.tratamientos = new ArrayList<>();
		}
		this.tratamientos.add(tratamiento);
		tratamiento.setCitaOdontologica(this);
	}
	
	public void removeTratamiento(Tratamiento tratamiento) {
		this.tratamientos.remove(tratamiento);
		tratamiento.setCitaOdontologica(null);
	}
	
	// Métodos de negocio
	public boolean esPasada() {
		return fecha.isBefore(LocalDate.now());
	}
	
	public boolean esHoy() {
		return fecha.equals(LocalDate.now());
	}
	
	public boolean puedeSerCancelada() {
		return estado == EstadoCita.PROGRAMADA || estado == EstadoCita.CONFIRMADA;
	}
	
	@Override
	public String toString() {
		return "CitaOdontologica{" +
				"id=" + id +
				", fecha=" + fecha +
				", hora=" + hora +
				", motivoConsulta='" + motivoConsulta + '\'' +
				", estado=" + estado +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CitaOdontologica)) return false;
		CitaOdontologica that = (CitaOdontologica) o;
		return id != null && id.equals(that.id);
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
