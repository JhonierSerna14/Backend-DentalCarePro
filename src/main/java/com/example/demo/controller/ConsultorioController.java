package com.example.demo.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

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
	public @ResponseBody String eliminar(@RequestParam int id) {
		if (consultorioRepository.existsById(id)) {
			consultorioRepository.deleteById(id);
			return "Consultorio eliminado exitosamente";
		} else {
			return "El consultorio con el ID proporcionado no existe";
		}
	}

	@GetMapping(path = "/agendaConsultorio")
	public @ResponseBody List<CitaOdontologica> getCitasPorFechaYConsultorio(@RequestParam int consultorioId,
			@RequestParam Date fecha, @RequestParam(required = false) String filtro) {

		Consultorio consultorio = consultorioRepository.findById(consultorioId).orElse(null);

		if (consultorio != null) {
			Odontologo odontologo = consultorio.getOdontologo();
			List<CitaOdontologica> citas = odontologo.getCitaOdontologica();

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

	// Metodo para verificar si dos fechas estan en el mismo mes
	private boolean mismoMes(Date fecha1, Date fecha2) {
		LocalDate localDate1 = fecha1.toLocalDate();
		LocalDate localDate2 = fecha2.toLocalDate();
		return localDate1.getMonth() == localDate2.getMonth() && localDate1.getYear() == localDate2.getYear();
	}

	// Metodo para verificar si dos fechas estan en la misma semana
	private boolean mismaSemana(Date fecha1, Date fecha2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(fecha1);
		int week1 = cal1.get(Calendar.WEEK_OF_YEAR);
		int year1 = cal1.get(Calendar.YEAR);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(fecha2);
		int week2 = cal2.get(Calendar.WEEK_OF_YEAR);
		int year2 = cal2.get(Calendar.YEAR);

		return week1 == week2 && year1 == year2;
	}

}
