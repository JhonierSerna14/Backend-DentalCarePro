package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Odontologo;
import com.example.demo.entity.Paciente;
import com.example.demo.repository.OdontologoRepository;
import com.example.demo.repository.PacienteRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private OdontologoRepository odontologoRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpSession session) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            Object userObject;
            String role;
            if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PACIENTE"))) {
                Paciente paciente = pacienteRepository.findByEmail(userDetails.getUsername());
                userObject = paciente;
                role = "paciente";
            } else {
                Odontologo odontologo = odontologoRepository.findByEmail(authRequest.getEmail());
                userObject = odontologo;
                role = "odontologo";
            }

            Map<String, Object> response = new HashMap<>();
            response.put("user", userObject);
            response.put("rol", role);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: Invalid email or password");
        }
    }

    @GetMapping("/protected-endpoint")
    public ResponseEntity<?> protectedEndpoint() {
        return ResponseEntity.ok("This is a protected endpoint");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/pregunta-seguridad")
    public @ResponseBody String getPreguntaSeguridad(@RequestParam String email) {
        String respuesta = "No hay registros";
        Paciente paciente = pacienteRepository.findByEmail(email);
        Odontologo odontologo = odontologoRepository.findByEmail(email);
        if (paciente != null) {
            respuesta = paciente.getPregunta();
        } else if (odontologo != null) {
            respuesta = odontologo.getPregunta();
        }
        return respuesta;
    }

    @PostMapping("/verificarRespuestaSeguridad")
    public ResponseEntity<?> getVerificarRespuesta(@RequestParam String email,
            @RequestParam String respuestaSeguridad) {
        Paciente paciente = pacienteRepository.findByEmail(email);
        Odontologo odontologo = odontologoRepository.findByEmail(email);
        if (paciente != null) {
            if (paciente.getRespuesta().equalsIgnoreCase(respuestaSeguridad)) {
                return ResponseEntity.ok("La clave de ingreso es: " + paciente.getPassword());
            }
        } else if (odontologo != null) {
            if (odontologo.getRespuesta().equalsIgnoreCase(respuestaSeguridad)) {
                return ResponseEntity.ok("La clave de ingreso es: " + odontologo.getPassword());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Respuesta incorrecta");
    }

}

class AuthRequest {
    private String email;
    private String password;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
