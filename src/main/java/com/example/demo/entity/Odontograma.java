package com.example.demo.entity;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
public class Odontograma {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "paciente_id")
    @JsonBackReference
    private Paciente paciente;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "odontograma_id")
    @JsonManagedReference
    private List<Diente> dientes;

    public void crearOdontograma() {
        this.dientes = new ArrayList<>();
        if (esMayorDeEdad(paciente.getFechaNacimiento())) {
            crearDientes(16);
        } else {
            crearDientes(10);
        }
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    private void crearDientes(int numeroDeDientesPorArco) {
        for (int arco = 1; arco <= 2; arco++) {
            for (int numeroDiente = 1; numeroDiente <= numeroDeDientesPorArco; numeroDiente++) {
                Diente diente = new Diente();
                diente.setSector(arco);
                diente.setNumero(numeroDiente);
                diente.setEstado("diente");
                diente.setDescripcion("");
                dientes.add(diente);
            }
        }
    }

    public static boolean esMayorDeEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            return true; // Por defecto asumimos adulto si no hay fecha
        }
        Period period = Period.between(fechaNacimiento, LocalDate.now());
        int edad = period.getYears();
        System.out.println("Edad calculada: " + edad);
        return edad >= 18;
    }

    public List<Diente> getDientes() {
        return this.dientes;
    }

    public void setDientes(List<Diente> dientes) {
        this.dientes = dientes;
    }

}
