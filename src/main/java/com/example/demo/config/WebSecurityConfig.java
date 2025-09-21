package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.entity.Odontologo;
import com.example.demo.entity.Paciente;
import com.example.demo.repository.OdontologoRepository;
import com.example.demo.repository.PacienteRepository;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private OdontologoRepository odontologoRepository;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/home", "/api/login", "/api/pregunta-seguridad",
                                "/api/verificarRespuestaSeguridad", "/static/**",
                                "/assets/**", "/index.html")
                        .permitAll()
                        // .requestMatchers("/admin", "paciente/all").hasRole("ADMIN")
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .formLogin(login -> login.disable())
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().flush();
                        })
                        .permitAll());

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return email -> {
            Paciente paciente = pacienteRepository.findByEmail(email);
            if (paciente != null) {
                return User.builder()
                        .username(paciente.getEmail())
                        .password(passwordEncoder.encode(paciente.getPassword()))
                        .roles("PACIENTE")
                        .build();
            }

            Odontologo odontologo = odontologoRepository.findByEmail(email);
            if (odontologo != null) {
                return User.builder()
                        .username(odontologo.getEmail())
                        .password(passwordEncoder.encode(odontologo.getPassword()))
                        .roles("ODONTOLOGO")
                        .build();
            }

            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
