package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.MedicamentoxTratamiento;
import com.example.demo.entity.Tratamiento;

import java.util.List;

public interface MedicamentoxTratamientoRepository extends CrudRepository<MedicamentoxTratamiento, Integer> {
	List<MedicamentoxTratamiento> findByTratamiento(Tratamiento tratamiento);

}
