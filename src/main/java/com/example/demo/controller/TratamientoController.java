package com.example.demo.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.CitaOdontologica;
import com.example.demo.entity.Tratamiento;
import com.example.demo.repository.CitaOdontologicaRepository;
import com.example.demo.repository.TratamientoRepository;

@Controller
@RequestMapping(path = "/tratamiento")
public class TratamientoController {

	@Autowired
	private TratamientoRepository tratamientoRepository;

	@Autowired
	private CitaOdontologicaRepository citaOdontologicaRepository;

	@PostMapping(path = "/new")
	public @ResponseBody String nuevo(@RequestParam String Tipo, @RequestParam String Recomendaciones,
			@RequestParam Date Fecha, @RequestParam Integer Idcita, @RequestParam String Resultados) {

		Optional<CitaOdontologica> citaOdontologicaOptional = citaOdontologicaRepository.findById(Idcita);

		if (citaOdontologicaOptional.isPresent()) {
			CitaOdontologica citaOdontologica = citaOdontologicaOptional.get();

			Tratamiento t = new Tratamiento();

			t.setInstruccionesPostoperatorias(Recomendaciones);
			t.setFecha(Fecha);
			t.setTipo(Tipo);
			t.setCitaOdontologica(citaOdontologica);
			LocalDate fechaActual = LocalDate.now();
			t.setFechaModificacion(fechaActual);
			t.setResultados(Resultados);
			tratamientoRepository.save(t);

			citaOdontologica.getTratamiento().add(t);
			citaOdontologicaRepository.save(citaOdontologica);

			return "Tratamiento creado";
		} else {
			return "Id de cita inválido";
		}

	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Tratamiento> listarTodos() {
		return tratamientoRepository.findAll();
	}

	@GetMapping(path = "/tratamientoEnCita")
	public @ResponseBody Iterable<Tratamiento> listarEnCita(Integer citaId) {
		Optional<CitaOdontologica> citaOptional = citaOdontologicaRepository.findById(citaId);
		if (citaOptional.isPresent()) {
			CitaOdontologica c = citaOptional.get();
			return c.getTratamiento();
		} else {
			return List.of();
		}

	}

	@DeleteMapping(path = "/delete")
	public @ResponseBody String eliminar(@RequestParam int id) {
		if (tratamientoRepository.existsById(id)) {
			Optional<Tratamiento> t = tratamientoRepository.findById(id);
			tratamientoRepository.deleteById(id);
			int idCita = t.get().getCitaOdontologica().getId();
			return Integer.toString(idCita);
		} else {
			return "El tratamiento con el ID proporcionado no existe";
		}
	}

	@PostMapping(path = "/resultados")
	public @ResponseBody String resultados(@RequestParam Integer idTratamiento, @RequestParam String Resultados) {
		Optional<Tratamiento> tratamientoOptional = tratamientoRepository.findById(idTratamiento);
		if (tratamientoOptional.isPresent()) {
			Tratamiento tratamiento = tratamientoOptional.get();
			tratamiento.setResultados(Resultados);
			return "Resultados agregados exitosamente";
		}
		return "Id de tratamiento inv�lido";
	}

}
