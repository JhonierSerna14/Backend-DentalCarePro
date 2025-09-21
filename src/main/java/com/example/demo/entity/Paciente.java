package com.example.demo.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

/**
 * Entidad que representa un paciente en el sistema de gestión odontológica.
 * 
 * @author DentalCarePro Team
 * @version 1.0
 */
@Entity
@Table(name = "pacientes")
public class Paciente {
	
	@Id
	@Column(name = "cedula", unique = true, nullable = false)
	private String cedula;
	
	@Column(name = "nombres", nullable = false, length = 100)
	private String nombres;
	
	@Column(name = "apellidos", nullable = false, length = 100)
	private String apellidos;
	
	@Column(name = "telefono", length = 20)
	private String telefono;
	
	@Column(name = "direccion", length = 200)
	private String direccion;
	
	@Column(name = "email", unique = true, nullable = false, length = 150)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "alergias", columnDefinition = "TEXT")
	private String alergias;
	
	@Column(name = "condiciones_medicas", columnDefinition = "TEXT")
	private String condicionesMedicas;
	
	@Column(name = "fecha_nacimiento")
	private LocalDate fechaNacimiento;
	
	@Column(name = "pregunta_seguridad", length = 200)
	private String preguntaSeguridad;
	
	@Column(name = "respuesta_seguridad", length = 200)
	private String respuestaSeguridad;
	
	// Relaciones
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "paciente_cedula")
	@JsonBackReference
	private List<CitaOdontologica> citasOdontologicas = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "paciente_id")
	@JsonManagedReference
	private Odontograma odontograma;
	
	// Constructores
	public Paciente() {
		this.citasOdontologicas = new ArrayList<>();
	}
	
	public Paciente(String cedula, String nombres, String apellidos, String email) {
		this();
		this.cedula = cedula;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.email = email;
	}

	// Getters y Setters
	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getAlergias() {
		return alergias;
	}

	public void setAlergias(String alergias) {
		this.alergias = alergias;
	}

	public String getCondicionesMedicas() {
		return condicionesMedicas;
	}

	public void setCondicionesMedicas(String condicionesMedicas) {
		this.condicionesMedicas = condicionesMedicas;
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
		cita.setPaciente(this);
	}
	
	public void removeCitaOdontologica(CitaOdontologica cita) {
		this.citasOdontologicas.remove(cita);
		cita.setPaciente(null);
	}

	public Odontograma getOdontograma() {
		return odontograma;
	}

	public void setOdontograma(Odontograma odontograma) {
		this.odontograma = odontograma;
		if (odontograma != null) {
			odontograma.setPaciente(this);
		}
	}
	
	// Métodos auxiliares
	@Override
	public String toString() {
		return "Paciente{" +
				"cedula='" + cedula + '\'' +
				", nombres='" + nombres + '\'' +
				", apellidos='" + apellidos + '\'' +
				", email='" + email + '\'' +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Paciente)) return false;
		Paciente paciente = (Paciente) o;
		return cedula != null && cedula.equals(paciente.cedula);
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
