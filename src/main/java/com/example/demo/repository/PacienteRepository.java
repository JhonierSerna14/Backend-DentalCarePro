package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Paciente;

public interface PacienteRepository extends CrudRepository<Paciente, String> {
	Iterable<Paciente> findByNombres(String Nombres);

	Paciente findByEmail(String email);

}
