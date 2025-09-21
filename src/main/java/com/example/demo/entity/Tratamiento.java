package com.example.demo.entity;

import java.sql.Date;
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
	private int id;
	private Date fecha;
	private String Tipo, Resultados, InstruccionesPostoperatorias;
	private LocalDate fechaModificacion;

	@ManyToOne
	@JoinColumn(name = "citaOdontologica_id")
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return Tipo;
	}

	public void setTipo(String tipo) {
		Tipo = tipo;
	}

	public String getResultados() {
		return Resultados;
	}

	public void setResultados(String resultados) {
		Resultados = resultados;
	}

	public String getInstruccionesPostoperatorias() {
		return InstruccionesPostoperatorias;
	}

	public void setInstruccionesPostoperatorias(String instruccionesPostoperatorias) {
		InstruccionesPostoperatorias = instruccionesPostoperatorias;
	}

	public LocalDate getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDate fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

}
