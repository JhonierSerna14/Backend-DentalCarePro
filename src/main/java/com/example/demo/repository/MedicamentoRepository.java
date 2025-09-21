package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Medicamento;
import java.util.List;

public interface MedicamentoRepository extends CrudRepository<Medicamento, Integer> {
	List<Medicamento> findByNombre(String nombre);
}
