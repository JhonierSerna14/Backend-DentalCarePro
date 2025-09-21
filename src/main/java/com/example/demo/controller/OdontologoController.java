package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PatchMapping;

import com.example.demo.entity.CitaOdontologica;
import com.example.demo.entity.Consultorio;
import com.example.demo.entity.Odontologo;
import com.example.demo.repository.CitaOdontologicaRepository;
import com.example.demo.repository.ConsultorioRepository;
import com.example.demo.repository.OdontologoRepository;

@Controller
@RequestMapping(path = "/odontologo")
public class OdontologoController {

	@Autowired
	private OdontologoRepository odontologoRepository;

	@Autowired
	private CitaOdontologicaRepository citaOdontologicaRepository;

	@Autowired
	private ConsultorioRepository consultorioRepository;

	@PostMapping(path = "/new")
	public @ResponseBody String nuevo(@RequestParam String nombres, @RequestParam String apellidos,
			@RequestParam String especialidad, @RequestParam String telefono, @RequestParam String email,
			@RequestParam String password,
			@RequestParam Integer idConsultorio, @RequestParam String pregunta, @RequestParam String respuesta) {
		Optional<Consultorio> consultorioOptional = consultorioRepository.findById(idConsultorio);
		if (consultorioOptional.isPresent()) {
			Consultorio consultorio = consultorioOptional.get();
			Odontologo o = new Odontologo();
			o.setNombres(nombres);
			o.setApellidos(apellidos);
			o.setEspecialidad(especialidad);
			o.setTelefono(telefono);
			o.setEmail(email);
			o.setPassword(password);
			o.setConsultorio(consultorio);
			o.setPregunta(pregunta);
			o.setRespuesta(respuesta);

			odontologoRepository.save(o);

			consultorio.setOdontologo(o);
			consultorioRepository.save(consultorio);

			return "Listo";
		} else {
			return "Id's proporcionados invalidos";
		}
	}

	@GetMapping(path = "/*")
	public String listarTodos_frontend(Model modelo) {
		ArrayList<Odontologo> lista = (ArrayList<Odontologo>) odontologoRepository.findAll();
		modelo.addAttribute("odontologos", lista);
		return "index";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Odontologo> listarTodos() {
		return odontologoRepository.findAll();
	}

	@DeleteMapping(path = "/delete")
	public @ResponseBody String eliminar(@RequestParam int id) {
		if (odontologoRepository.existsById(id)) {
			Odontologo o = odontologoRepository.findById(id).get();
			Iterable<CitaOdontologica> co = citaOdontologicaRepository.findByOdontologo(o);

			for (CitaOdontologica c : co) {
				c.getOdontologo().getConsultorio().volverDisponible(c.getFecha(), c.getHora());
				citaOdontologicaRepository.delete(c);
			}
			o.getConsultorio().setOdontologo(null);
			odontologoRepository.deleteById(id);
			return "Odontologo eliminado exitosamente";
		} else {
			return "El odontologo con el ID proporcionado no existe";
		}
	}

	@PatchMapping(path = "/update")
	public @ResponseBody Odontologo actualizar(@RequestParam int id, @RequestParam(required = false) String nombres,
			@RequestParam(required = false) String apellidos, @RequestParam(required = false) String especialidad,
			@RequestParam(required = false) String telefono, @RequestParam(required = false) String email) {

		Optional<Odontologo> optionalOdontologo = odontologoRepository.findById(id);

		if (optionalOdontologo.isPresent()) {
			Odontologo o = optionalOdontologo.get();

			if (nombres != null && nombres != "") {
				o.setNombres(nombres);
			}
			if (apellidos != null && apellidos != "") {
				o.setApellidos(apellidos);
			}
			if (especialidad != null && especialidad != "") {
				o.setEspecialidad(especialidad);
			}
			if (telefono != null && telefono != "") {
				o.setTelefono(telefono);
			}
			if (email != null && email != "") {
				o.setEmail(email);
			}

			odontologoRepository.save(o);
			return o;
		} else {
			return null;
		}
	}

	@GetMapping(path = "/FiltroxNombre")
	public @ResponseBody Iterable<Odontologo> listarPorNombres(@RequestParam("Nombres") String Nombres) {
		return odontologoRepository.findByNombres(Nombres);
	}

}