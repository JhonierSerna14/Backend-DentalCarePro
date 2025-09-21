package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.CitaOdontologica;
import com.example.demo.entity.Odontologo;
import com.example.demo.entity.Paciente;
import com.example.demo.entity.Tratamiento;
import com.example.demo.repository.CitaOdontologicaRepository;
import com.example.demo.repository.OdontologoRepository;
import com.example.demo.repository.PacienteRepository;

@Controller
@RequestMapping(path = "citaOdontologica")
public class CitaOdontologicaController {
	@Autowired
	private CitaOdontologicaRepository citaOdontologicaRepository;
	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private OdontologoRepository odontologoRepository;

	@PostMapping(path = "/new")
	public ResponseEntity<String> nuevo(@RequestParam LocalDate fecha, @RequestParam String hora,
			@RequestParam String motivoConsulta,
			@RequestParam Long idOdontologo, @RequestParam String idPaciente) {

		Optional<Odontologo> odontologoOptional = odontologoRepository.findById(idOdontologo);
		Optional<Paciente> pacienteOptional = pacienteRepository.findById(idPaciente);

		if (odontologoOptional.isPresent() && pacienteOptional.isPresent()) {
			Odontologo odontologo = odontologoOptional.get();
			Paciente paciente = pacienteOptional.get();

			// Verificar disponibilidad del horario
			boolean horarioDisponible = true; // consultorio.verificarDisponibilidad(fecha, hora);
			
			if (horarioDisponible) {
				// Crear una nueva cita
				CitaOdontologica co = new CitaOdontologica();
				co.setFecha(fecha);
				co.setMotivoConsulta(motivoConsulta);
				co.setOdontologo(odontologo);
				co.setPaciente(paciente);
				co.setHora(LocalTime.parse(hora));
				citaOdontologicaRepository.save(co);

				paciente.addCitaOdontologica(co);
				pacienteRepository.save(paciente);

				odontologo.addCitaOdontologica(co);
				odontologoRepository.save(odontologo);
				
				return ResponseEntity.ok("Cita creada con éxito");
			} else {
				return ResponseEntity.badRequest().body("Horario no disponible");
			}
		} else {
			return ResponseEntity.badRequest().body("Paciente o Odontólogo no encontrado");
		}
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<CitaOdontologica> listarTodos() {
		return citaOdontologicaRepository.findAll();
	}

	@PatchMapping(path = "/update")
	public @ResponseBody CitaOdontologica actualizar(@RequestParam Long idCita,
			@RequestParam(required = false) LocalDate fecha) {

		Optional<CitaOdontologica> optionalCitaOdontologica = citaOdontologicaRepository.findById(idCita);

		if (optionalCitaOdontologica.isPresent()) {
			CitaOdontologica co = optionalCitaOdontologica.get();

			co.setFecha(fecha);
			citaOdontologicaRepository.save(co);
			return co;
		} else {
			return null;
		}
	}

	@DeleteMapping(path = "/delete")
	public @ResponseBody String eliminar(@RequestParam Long idCita) {
		if (citaOdontologicaRepository.existsById(idCita)) {
			Optional<CitaOdontologica> optionalco = citaOdontologicaRepository.findById(idCita);
			CitaOdontologica co = optionalco.get();
			co.getOdontologo().getConsultorio().volverDisponible(co.getFecha(), co.getHora());
			citaOdontologicaRepository.deleteById(idCita);
			return "Cita Odontologica eliminado exitosamente";
		} else {
			return "La Cita Odontologica con el ID proporcionado no existe";
		}
	}

	@GetMapping(path = "/tratamientosEnPaciente")
	public @ResponseBody Iterable<Tratamiento> tratamientosEnPaciente(@RequestParam String pacienteId) {
		Optional<Paciente> optionalPaciente = pacienteRepository.findById(pacienteId);
		List<Tratamiento> t = new ArrayList<>();
		if (optionalPaciente.isPresent()) {
			Paciente p = optionalPaciente.get();
			Iterable<CitaOdontologica> citas = citaOdontologicaRepository.findByPaciente(p);
			for (CitaOdontologica cita : citas) {
				t.addAll(cita.getTratamientos());
			}
		}
		return t;

	}

}
