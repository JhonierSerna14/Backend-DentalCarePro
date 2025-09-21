package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.CitaOdontologica;
import com.example.demo.entity.Odontologo;
import com.example.demo.entity.Paciente;

public interface CitaOdontologicaRepository extends CrudRepository<CitaOdontologica, Integer> {
	Iterable<CitaOdontologica> findByOdontologo(Odontologo odontologo);

	Iterable<CitaOdontologica> findByPaciente(Paciente paciente);
}
