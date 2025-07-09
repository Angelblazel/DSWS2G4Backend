package DSWS2Grupo4.config;

import DSWS2Grupo4.Jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf ->
                        csrf
                        .disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                
                                // Auth: público
                                .requestMatchers("/auth/**").permitAll()

                                // Público (sin autenticación obligatoria) - SIN /api/v1 porque ya está en context-path
                                .requestMatchers(HttpMethod.GET, "/catalogos/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/incidencias/publica").permitAll()
                                .requestMatchers(HttpMethod.GET, "/incidencias/publica/*").permitAll()
                                .requestMatchers(HttpMethod.POST, "/incidencias/alerta").permitAll()
                                .requestMatchers(HttpMethod.GET, "/usuarios-solicitantes/buscar-por-correo").permitAll()
                                
                                // Jefe de Área
                                .requestMatchers(HttpMethod.GET, "/asignacion/tecnicos-disponibles").hasAuthority("JEFE_AREA")
                                .requestMatchers(HttpMethod.POST, "/asignacion").hasAuthority("JEFE_AREA")
                                .requestMatchers(HttpMethod.GET, "/asignacion/no-asignadas").hasAuthority("JEFE_AREA")
                                .requestMatchers(HttpMethod.PUT, "/incidencias/*").hasAuthority("JEFE_AREA")
                                .requestMatchers(HttpMethod.DELETE, "/incidencias/*").hasAuthority("JEFE_AREA")
                                .requestMatchers(HttpMethod.GET, "/incidencias").hasAuthority("JEFE_AREA")
                                .requestMatchers(HttpMethod.GET, "/estadisticas/*").hasAuthority("JEFE_AREA")
                                .requestMatchers("/usuarios-solicitantes/**").hasAuthority("JEFE_AREA")
                                
                                // Técnico
                                .requestMatchers("/historial/**").hasAuthority("TECNICO")
                                .requestMatchers("/incidencias/*/soluciones").hasAuthority("TECNICO")
                                .requestMatchers("/tecnico/incidencias/**").hasAuthority("TECNICO")
                                .requestMatchers("/incidencias/solucion").hasAuthority("TECNICO")
                                
                                // Técnico y Logística
                                .requestMatchers("/solicitudes-repuestos/repuestos").hasAnyAuthority("TECNICO", "LOGISTICA")
                                .requestMatchers("/solicitudes-repuestos").hasAnyAuthority("TECNICO", "LOGISTICA")
                                
                                // Logística
                                .requestMatchers(HttpMethod.PUT, "/solicitudes-repuestos/*").hasAuthority("LOGISTICA")
                                .requestMatchers("/repuesto/**").hasAuthority("LOGISTICA")
                                .requestMatchers("/repuesto").hasAuthority("LOGISTICA")
      
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "https://dsws2g4-frontend.vercel.app/")); // origen Angular
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // si usas JWT o cookies

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
