package com.example.demo.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Diente;
import com.example.demo.entity.Odontograma;
import com.example.demo.entity.Paciente;
import com.example.demo.repository.PacienteRepository;

@Controller
@RequestMapping(path = "/paciente")
public class PacienteController {

	@Autowired
	private PacienteRepository pacienteRepository;

	@PostMapping(path = "/new")
	public @ResponseBody String nuevo(@RequestParam String Nombres, @RequestParam String Apellidos,
			@RequestParam String Telefono, @RequestParam String Direccion, @RequestParam String Email,
			@RequestParam String password,
			@RequestParam String Cedula, @RequestParam LocalDate FechaNacimiento, @RequestParam String alergias,
			@RequestParam String condicionesMedicas, @RequestParam String pregunta, @RequestParam String respuesta) {

		// Crear un nuevo paciente
		Paciente p = new Paciente();
		p.setNombres(Nombres);
		p.setApellidos(Apellidos);
		p.setTelefono(Telefono);
		p.setDireccion(Direccion);
		p.setEmail(Email);
		p.setPassword(password);
		p.setCedula(Cedula);
		p.setFechaNacimiento(FechaNacimiento);
		p.setAlergias(alergias);
		p.setCondicionesMedicas(condicionesMedicas);
		pacienteRepository.save(p);
		Odontograma od = new Odontograma();
		od.setPaciente(p);
		od.crearOdontograma();
		p.setOdontograma(od);
		p.setPreguntaSeguridad(pregunta);
		p.setRespuestaSeguridad(respuesta);
		pacienteRepository.save(p);
		return "Creado con Éxito";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Paciente> listarTodos() {
		return pacienteRepository.findAll();
	}

	@DeleteMapping(path = "/delete")
	public @ResponseBody String eliminar(@RequestParam String cedula) {
		if (pacienteRepository.existsById(cedula)) {
			pacienteRepository.deleteById(cedula);
			return "Paciente eliminado exitosamente";
		} else {
			return "El paciente con el ID proporcionado no existe";
		}
	}

	@PatchMapping(path = "/update")
	public @ResponseBody Paciente actualizar(@RequestParam String Cedula,
			@RequestParam(required = false) String telefono, @RequestParam(required = false) String email,
			@RequestParam(required = false) String direccion) {

		Optional<Paciente> optionalPaciente = pacienteRepository.findById(Cedula);

		if (optionalPaciente.isPresent()) {
			Paciente p = optionalPaciente.get();

			if (telefono != null) {
				p.setTelefono(telefono);
			}
			if (email != null) {
				p.setEmail(email);
			}
			if (direccion != null) {
				p.setDireccion(direccion);
			}

			pacienteRepository.save(p);
			return p;
		} else {
			return null;
		}
	}

	@GetMapping(path = "/FiltroxNombre")
	public @ResponseBody Iterable<Paciente> listarPorNombres(@RequestParam("Nombres") String Nombres) {
		return pacienteRepository.findByNombres(Nombres);
	}

	@GetMapping(path = "/Odontograma")
	public @ResponseBody Paciente odontogramaPaciente(@RequestParam String Cedula) {
		Optional<Paciente> optionalPaciente = pacienteRepository.findById(Cedula);

		if (optionalPaciente.isPresent()) {
			Paciente p = optionalPaciente.get();

			return p;
		} else {
			return null;
		}
	}

	@PostMapping(path = "/actualizarOdontograma")
	public @ResponseBody Odontograma actualizarDienteOdontograma(
			@RequestParam String cedula,
			@RequestParam int id,
			@RequestParam String estado,
			@RequestParam String descripcion) {
		Optional<Paciente> optionalPaciente = pacienteRepository.findById(cedula);

		if (optionalPaciente.isPresent()) {
			Paciente p = optionalPaciente.get();
			List<Diente> listaDientes = p.getOdontograma().getDientes();

			for (Diente diente : listaDientes) {
				if (diente.getId() == id) {
					diente.setEstado(estado);
					diente.setDescripcion(descripcion);
					break;
				}
			}

			pacienteRepository.save(p);
			return p.getOdontograma();
		} else {
			return null;
		}
	}

}
