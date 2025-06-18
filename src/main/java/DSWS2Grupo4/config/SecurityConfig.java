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
                                
                                // Jefe de Área
                                .requestMatchers(HttpMethod.GET, "/api/v1/asignacion/tecnicos-disponibles").hasAuthority("JEFE_AREA")
                                .requestMatchers(HttpMethod.POST, "/api/v1/asignacion").hasAuthority("JEFE_AREA")
                                .requestMatchers(HttpMethod.GET, "/api/v1/asignacion/no-asignadas").hasAuthority("JEFE_AREA")
                                
                                // Público (sin autenticación obligatoria)
                                .requestMatchers(HttpMethod.GET, "/api/catalogos/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/incidencias/publica").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/incidencias/publica/*").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/incidencias/alerta").permitAll()
                                
                                // Técnico
                                .requestMatchers("/api/historial/**").hasAuthority("TECNICO")
                                .requestMatchers("/api/v1/incidencias/*/soluciones").hasAuthority("TECNICO")
                                .requestMatchers("/api/v1/tecnico/incidencias/**").hasAuthority("TECNICO")
                                .requestMatchers("/api/v1/incidencias/solucion").hasAuthority("TECNICO")
                                
                                
                                // Técnico y Logística
                                .requestMatchers("/api/v1/solicitudes-repuestos/repuestos").hasAnyAuthority("TECNICO", "LOGISTICA")
                                .requestMatchers("/api/v1/solicitudes-repuestos").hasAnyAuthority("TECNICO", "LOGISTICA")
                                
                                // Logística
                                .requestMatchers(HttpMethod.PUT, "/api/v1/solicitudes-repuestos/*").hasAuthority("LOGISTICA")
                                .requestMatchers("/api/v1/repuesto/**").hasAuthority("LOGISTICA")
                                
      
                                /*.requestMatchers("/api/v1/tecnico/**").hasAuthority("TECNICO")
                                .requestMatchers("/api/v1/repuesto/**").hasAuthority("LOGISTICA")
                                .requestMatchers(HttpMethod.GET).permitAll()
                                .requestMatchers(HttpMethod.POST).permitAll()*/
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
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // origen Angular
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // si usas JWT o cookies

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
