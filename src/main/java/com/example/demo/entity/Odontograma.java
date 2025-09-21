package com.example.demo.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Odontograma {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
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

    public static boolean esMayorDeEdad(Date fechaNacimiento) {
        long fechaActualMilisegundos = System.currentTimeMillis();
        long edadMilisegundos = fechaActualMilisegundos - fechaNacimiento.getTime();
        long edadAproximada = edadMilisegundos / (1000L * 60 * 60 * 24 * 365);
        System.out.println(edadAproximada);
        return edadAproximada >= 18;
    }

    public List<Diente> getDientes() {
        return this.dientes;
    }

    public void setDientes(List<Diente> dientes) {
        this.dientes = dientes;
    }

}
