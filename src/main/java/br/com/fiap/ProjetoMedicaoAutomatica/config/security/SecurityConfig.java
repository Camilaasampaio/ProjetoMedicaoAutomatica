package br.com.fiap.ProjetoMedicaoAutomatica.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private VerificarToken verificarToken;

    @Bean
    public SecurityFilterChain filtrarCadeiaDeSeguranca(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        // Equipamento
                        .requestMatchers(HttpMethod.POST, "/api/equipamento").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/equipamento").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/equipamento/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/equipamento").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/equipamento/*").hasAnyRole("ADMIN", "USER")
                        // Local
                        .requestMatchers(HttpMethod.POST, "/api/local").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/local").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/local/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/local").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/local/*").hasAnyRole("ADMIN", "USER")
                        // Medição Agua
                        .requestMatchers(HttpMethod.POST, "/api/qualidade/agua").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/qualidade/agua").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/qualidade/agua/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/qualidade/agua").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/qualidade/agua/*").hasAnyRole("ADMIN", "USER")
                        // Medição Ar
                        .requestMatchers(HttpMethod.POST, "/api/qualidade/ar").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/qualidade/ar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/qualidade/ar/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/qualidade/ar").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/api/qualidade/ar/*").hasAnyRole("ADMIN", "USER")
                        // Usuario
                        .requestMatchers(HttpMethod.POST, "/api/usuario").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/usuario").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/usuario/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuario").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuario/*").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        verificarToken,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
