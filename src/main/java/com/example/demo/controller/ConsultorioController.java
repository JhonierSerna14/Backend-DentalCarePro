package com.example.demo.controller;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.CitaOdontologica;
import com.example.demo.entity.Consultorio;
import com.example.demo.entity.Odontologo;
import com.example.demo.repository.ConsultorioRepository;

@Controller
@RequestMapping(path = "/consultorio")
public class ConsultorioController {
	@Autowired
	private ConsultorioRepository consultorioRepository;

	@PostMapping(path = "/new")
	public @ResponseBody String nuevo(@RequestParam String sede, @RequestParam String consultorio) {
		Consultorio c = new Consultorio();
		c.setSede(sede);
		c.setConsultorio(consultorio);

		consultorioRepository.save(c);
		return "Creado con exito";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Consultorio> listarTodos() {
		return consultorioRepository.findAll();
	}

	@DeleteMapping(path = "/delete")
	public @ResponseBody String eliminar(@RequestParam Long id) {
		if (consultorioRepository.existsById(id)) {
			consultorioRepository.deleteById(id);
			return "Consultorio eliminado exitosamente";
		} else {
			return "El consultorio con el ID proporcionado no existe";
		}
	}

	@GetMapping(path = "/agendaConsultorio")
	public @ResponseBody List<CitaOdontologica> listarCitasPorFecha(@RequestParam Long idConsultorio,
			@RequestParam LocalDate fecha, @RequestParam(required = false) String filtro) {

		Consultorio consultorio = consultorioRepository.findById(idConsultorio).orElse(null);

		if (consultorio != null) {
			Odontologo odontologo = consultorio.getOdontologo();
			List<CitaOdontologica> citas = odontologo.getCitasOdontologicas();

			// Verificar si se proporciona un filtro
			if (filtro != null && !filtro.isEmpty()) {
				// Procesar el filtro
				switch (filtro) {
				case "mes":
					citas.removeIf(cita -> !mismoMes(cita.getFecha(), fecha));
					break;
				case "semana":
					citas.removeIf(cita -> !mismaSemana(cita.getFecha(), fecha));
					break;
				default:
					citas.removeIf(cita -> !cita.getFecha().equals(fecha));
					break;
				}
			} else {
				citas.removeIf(cita -> !cita.getFecha().equals(fecha));
			}

			return citas;
		} else {
			return List.of();
		}
	}

	// Método para verificar si dos fechas están en el mismo mes
	private boolean mismoMes(LocalDate fecha1, LocalDate fecha2) {
		return fecha1.getMonth() == fecha2.getMonth() && fecha1.getYear() == fecha2.getYear();
	}

	// Método para verificar si dos fechas están en la misma semana
	private boolean mismaSemana(LocalDate fecha1, LocalDate fecha2) {
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		int week1 = fecha1.get(weekFields.weekOfYear());
		int year1 = fecha1.getYear();
		int week2 = fecha2.get(weekFields.weekOfYear());
		int year2 = fecha2.getYear();
		return week1 == week2 && year1 == year2;
	}

}
