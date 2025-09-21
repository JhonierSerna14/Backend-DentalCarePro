package com.example.demo.controller;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Medicamento;
import com.example.demo.entity.MedicamentoxTratamiento;
import com.example.demo.entity.Tratamiento;
import com.example.demo.repository.MedicamentoRepository;
import com.example.demo.repository.MedicamentoxTratamientoRepository;
import com.example.demo.repository.TratamientoRepository;

@Controller
@RequestMapping(path = "/medicamentoxTratamiento")
public class MedicamentoxTratamientoController {

	@Autowired
	private MedicamentoxTratamientoRepository medicamentoxTratamientoRepository;

	@Autowired
	private MedicamentoRepository medicamentoRepository;

	@Autowired
	private TratamientoRepository tratamientoRepository;

	@PostMapping(path = "/new")
	public @ResponseBody String nuevo(@RequestParam Long idMedicamento, @RequestParam Long idtratamiento,
			@RequestParam Integer cantidad, @RequestParam String horario) {

		Optional<Medicamento> medicamentoOptional = medicamentoRepository.findById(idMedicamento);
		Optional<Tratamiento> tratamientoOptional = tratamientoRepository.findById(idtratamiento);

		if (medicamentoOptional.isPresent() && tratamientoOptional.isPresent()) {

			Medicamento medicamento = medicamentoOptional.get();
			Tratamiento tratamiento = tratamientoOptional.get();

			MedicamentoxTratamiento HT = new MedicamentoxTratamiento();
			HT.setMedicamento(medicamento);
			HT.setTratamiento(tratamiento);
			HT.setHorario(horario);
			HT.setCantidad(cantidad);

			medicamentoxTratamientoRepository.save(HT);

			return "Creado con exito";
		} else {
			return "Id's proporcionados invalidos";
		}
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<MedicamentoxTratamiento> listarTodos() {
		return medicamentoxTratamientoRepository.findAll();
	}

	@GetMapping(path = "/medicamentosEnTratamiento")
	public @ResponseBody Iterable<MedicamentoxTratamiento> medicamentosEnTratamiento(
			@RequestParam Long tratamientoId) {
		Optional<Tratamiento> tratamientoOptional = tratamientoRepository.findById(tratamientoId);
		if (tratamientoOptional.isPresent()) {
			Tratamiento t = tratamientoOptional.get();
			return medicamentoxTratamientoRepository.findByTratamiento(t);
		}
		return Collections.emptyList();
	}

	@DeleteMapping(path = "/delete")
	public @ResponseBody String eliminar(@RequestParam Long id) {
		if (medicamentoxTratamientoRepository.existsById(id)) {
			Optional<MedicamentoxTratamiento> t = medicamentoxTratamientoRepository.findById(id);
			Long idTratamiento = t.get().getTratamiento().getId();
			medicamentoxTratamientoRepository.deleteById(id);
			return Long.toString(idTratamiento);
		} else {
			return "El medicamentoxTratamiento con el ID proporcionado no existe";
		}
	}

}