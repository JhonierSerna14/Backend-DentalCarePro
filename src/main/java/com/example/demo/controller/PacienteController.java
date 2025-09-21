package com.example.demo.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
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

// OpenAPI/Swagger annotations
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping(path = "/paciente")
@Tag(name = "Pacientes", description = "Gestión de pacientes del sistema odontológico")
public class PacienteController {

	@Autowired
	private PacienteRepository pacienteRepository;

	@PostMapping(path = "/new")
	@Operation(
		summary = "Crear nuevo paciente",
		description = "Registra un nuevo paciente en el sistema y crea automáticamente su odontograma basado en su edad"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Paciente creado exitosamente", 
					content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
		@ApiResponse(responseCode = "400", description = "Datos inválidos")
	})
	public @ResponseBody String nuevo(
		@Parameter(description = "Nombres del paciente", required = true) 
		@RequestParam String Nombres, 
		
		@Parameter(description = "Apellidos del paciente", required = true)
		@RequestParam String Apellidos,
		
		@Parameter(description = "Número de teléfono")
		@RequestParam String Telefono, 
		
		@Parameter(description = "Dirección de residencia")
		@RequestParam String Direccion, 
		
		@Parameter(description = "Correo electrónico único", required = true)
		@RequestParam String Email,
		
		@Parameter(description = "Contraseña del paciente", required = true)
		@RequestParam String password,
		
		@Parameter(description = "Número de cédula (ID único)", required = true)
		@RequestParam String Cedula, 
		
		@Parameter(description = "Fecha de nacimiento (yyyy-MM-dd)", required = true)
		@RequestParam LocalDate FechaNacimiento, 
		
		@Parameter(description = "Alergias conocidas")
		@RequestParam String alergias,
		
		@Parameter(description = "Condiciones médicas preexistentes")
		@RequestParam String condicionesMedicas, 
		
		@Parameter(description = "Pregunta de seguridad")
		@RequestParam String pregunta, 
		
		@Parameter(description = "Respuesta de seguridad")
		@RequestParam String respuesta) {

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
	@Operation(
		summary = "Listar todos los pacientes",
		description = "Obtiene la lista completa de pacientes registrados en el sistema"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lista de pacientes obtenida exitosamente",
					content = @Content(mediaType = "application/json", 
					schema = @Schema(implementation = Paciente.class)))
	})
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
