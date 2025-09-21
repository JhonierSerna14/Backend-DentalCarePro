package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

/**
 * Entidad que representa un odontólogo en el sistema de gestión odontológica.
 * 
 * @author DentalCarePro Team
 * @version 1.0
 */
@Entity
@Table(name = "odontologos")
public class Odontologo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nombres", nullable = false, length = 100)
	private String nombres;
	
	@Column(name = "apellidos", nullable = false, length = 100)
	private String apellidos;
	
	@Column(name = "especialidad", length = 100)
	private String especialidad;
	
	@Column(name = "telefono", length = 20)
	private String telefono;
	
	@Column(name = "email", unique = true, nullable = false, length = 150)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "pregunta_seguridad", length = 200)
	private String preguntaSeguridad;
	
	@Column(name = "respuesta_seguridad", length = 200)
	private String respuestaSeguridad;
	
	// Relaciones
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "odontologo_id")
	@JsonBackReference
	private List<CitaOdontologica> citasOdontologicas = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "consultorio_id")
	@JsonManagedReference
	private Consultorio consultorio;
	
	// Constructores
	public Odontologo() {
		this.citasOdontologicas = new ArrayList<>();
	}
	
	public Odontologo(String nombres, String apellidos, String especialidad, String email) {
		this();
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.especialidad = especialidad;
		this.email = email;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPreguntaSeguridad() {
		return preguntaSeguridad;
	}

	public void setPreguntaSeguridad(String preguntaSeguridad) {
		this.preguntaSeguridad = preguntaSeguridad;
	}

	public String getRespuestaSeguridad() {
		return respuestaSeguridad;
	}

	public void setRespuestaSeguridad(String respuestaSeguridad) {
		this.respuestaSeguridad = respuestaSeguridad;
	}

	public List<CitaOdontologica> getCitasOdontologicas() {
		return citasOdontologicas;
	}

	public void setCitasOdontologicas(List<CitaOdontologica> citasOdontologicas) {
		this.citasOdontologicas = citasOdontologicas;
	}
	
	public void addCitaOdontologica(CitaOdontologica cita) {
		if (this.citasOdontologicas == null) {
			this.citasOdontologicas = new ArrayList<>();
		}
		this.citasOdontologicas.add(cita);
		cita.setOdontologo(this);
	}
	
	public void removeCitaOdontologica(CitaOdontologica cita) {
		this.citasOdontologicas.remove(cita);
		cita.setOdontologo(null);
	}

	public Consultorio getConsultorio() {
		return consultorio;
	}

	public void setConsultorio(Consultorio consultorio) {
		this.consultorio = consultorio;
		if (consultorio != null) {
			consultorio.setOdontologo(this);
		}
	}
	
	// Métodos auxiliares
	public String getNombreCompleto() {
		return nombres + " " + apellidos;
	}
	
	@Override
	public String toString() {
		return "Odontologo{" +
				"id=" + id +
				", nombres='" + nombres + '\'' +
				", apellidos='" + apellidos + '\'' +
				", especialidad='" + especialidad + '\'' +
				", email='" + email + '\'' +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Odontologo)) return false;
		Odontologo that = (Odontologo) o;
		return id != null && id.equals(that.id);
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
