package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class Consultorio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String sede, consultorio;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "consultorio_id")
	@JsonBackReference
	private Odontologo odontologo;

	@OneToMany(cascade = CascadeType.ALL)
	private List<HorarioDisponible> horariosDisponibles;

	public List<HorarioDisponible> getHorariosDisponibles() {
		return this.horariosDisponibles;
	}

	public void setHorariosDisponibles(List<HorarioDisponible> horariosDisponibles) {
		this.horariosDisponibles = horariosDisponibles;
	}

	public Odontologo getOdontologo() {
		return odontologo;
	}

	public void setOdontologo(Odontologo odontologo) {
		this.odontologo = odontologo;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getConsultorio() {
		return consultorio;
	}

	public void setConsultorio(String consultorio) {
		this.consultorio = consultorio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isHorarioDisponible(LocalDate fecha, LocalTime hora) {
		boolean disponibilidad = true;
		for (HorarioDisponible h : this.horariosDisponibles) {
			if (h.getFecha().equals(fecha) && h.getHora().equals(hora)) {
				disponibilidad = false;
			}
		}
		if (disponibilidad) {
			HorarioDisponible nDisponible = new HorarioDisponible();
			nDisponible.setFecha(fecha);
			nDisponible.setHora(hora);
			this.horariosDisponibles.add(nDisponible);
		}
		return disponibilidad;
	}

	public void volverDisponible(LocalDate fecha, LocalTime hora) {
		for (HorarioDisponible h : this.horariosDisponibles) {
			if (h.getFecha().equals(fecha) && h.getHora().equals(hora)) {
				this.horariosDisponibles.remove(h);
			}
		}
	}

}
