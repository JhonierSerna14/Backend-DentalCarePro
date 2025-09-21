package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Medicamento;
import com.example.demo.repository.MedicamentoRepository;

@Controller
@RequestMapping(path = "/medicamento")
public class MedicamentoController {

	@Autowired
	private MedicamentoRepository medicamentoRepository;

	@PostMapping(path = "/new")
	public @ResponseBody String nuevo(@RequestParam String Nombre, @RequestParam String Presentacion) {

		Medicamento m = new Medicamento();
		m.setNombre(Nombre);
		m.setPresentacion(Presentacion);
		medicamentoRepository.save(m);
		return "Creado con Exito";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Medicamento> listarTodos() {
		return medicamentoRepository.findAll();
	}

	@DeleteMapping(path = "/delete")
	public @ResponseBody String eliminar(@RequestParam Long id) {
		if (medicamentoRepository.existsById(id)) {
			medicamentoRepository.deleteById(id);
			return "Medicamento eliminado exitosamente";
		} else {
			return "El medicamento con el ID proporcionado no existe";
		}
	}

	@GetMapping(path = "/FiltroxNombre")
	public @ResponseBody Iterable<Medicamento> listarPorNombres(@RequestParam("Nombres") String Nombres) {
		return medicamentoRepository.findByNombre(Nombres);
	}

}
