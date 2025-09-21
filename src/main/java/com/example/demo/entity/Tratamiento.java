package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Tratamiento {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDate fecha;
	private String tipo, resultados, instruccionesPostoperatorias;
	private LocalDate fechaModificacion;

	@ManyToOne
	@JoinColumn(name = "cita_odontologica_id")
	@JsonManagedReference
	private CitaOdontologica citaOdontologica;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "tratamiento_id")
	@JsonBackReference
	private List<MedicamentoxTratamiento> medicamentosxTratamientos;

	public List<MedicamentoxTratamiento> getMedicamentosxTratamientos() {
		return medicamentosxTratamientos;
	}

	public void setMedicamentosxTratamientos(List<MedicamentoxTratamiento> medicamentosxTratamientos) {
		this.medicamentosxTratamientos = medicamentosxTratamientos;
	}

	public CitaOdontologica getCitaOdontologica() {
		return citaOdontologica;
	}

	public void setCitaOdontologica(CitaOdontologica citaOdontologica) {
		this.citaOdontologica = citaOdontologica;
	}

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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getResultados() {
		return resultados;
	}

	public void setResultados(String resultados) {
		this.resultados = resultados;
	}

	public String getInstruccionesPostoperatorias() {
		return instruccionesPostoperatorias;
	}

	public void setInstruccionesPostoperatorias(String instruccionesPostoperatorias) {
		this.instruccionesPostoperatorias = instruccionesPostoperatorias;
	}

	public LocalDate getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDate fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

}
