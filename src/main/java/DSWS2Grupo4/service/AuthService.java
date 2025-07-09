package DSWS2Grupo4.service;

import DSWS2Grupo4.DTO.AuthResponse;
import DSWS2Grupo4.DTO.LoginRequest;
import DSWS2Grupo4.Jwt.JwtService;
import DSWS2Grupo4.DTO.RegisterRequest;
import DSWS2Grupo4.model.EstadoTecnico;
import DSWS2Grupo4.model.JefeArea;
import DSWS2Grupo4.model.Logistica;
import DSWS2Grupo4.model.Usuario;
import DSWS2Grupo4.model.Rol;
import DSWS2Grupo4.model.Tecnico;
import DSWS2Grupo4.repository.JefeAreaRepository;
import DSWS2Grupo4.repository.LogisticaRepository;
import DSWS2Grupo4.repository.TecnicoRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import DSWS2Grupo4.repository.UsuarioRepository;



@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TecnicoRepository tecnicoRepository;
    private final LogisticaRepository logisticaRepository;
    private final JefeAreaRepository jefeAreaRepository;

    public AuthResponse login(LoginRequest request) {
        // Autenticar el usuario
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword_hash()
        ));

        // Buscar el usuario en la base de datos
        Usuario empleado = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Generar el token JWT
        String token = jwtService.getToken(empleado);

        // Construir la respuesta con TODOS los datos del usuario
        return AuthResponse.builder()
                .token(token)
                .idEmpleado(empleado.getIdEmpleado())
                .rol(empleado.getRole().name())
                .build();
    }
    
    public AuthResponse register(RegisterRequest request) {
        // Crear el usuario base
        Usuario user = Usuario.builder()
                .username(request.getUsername())
                .password_hash(passwordEncoder.encode(request.getPassword_hash()))
                .role(request.getRole() != null ? request.getRole() : Rol.LOGISTICA)
                .build();

        userRepository.save(user);

        // Lógica según el rol
        switch (user.getRole()) {
            case TECNICO -> {
                Tecnico tecnico = new Tecnico();
                tecnico.setEmpleado(user);
                tecnico.setCargaMaxima(6);
                tecnico.setCargaActual(0);
                tecnico.setNombre(user.getUsername().split("@")[0]);
                tecnicoRepository.save(tecnico);
            }

            case LOGISTICA -> {
                Logistica logistica = new Logistica();
                logistica.setEmpleado(user);
                logisticaRepository.save(logistica);
            }

            case JEFE_AREA -> {
                JefeArea jefe = new JefeArea();
                jefe.setEmpleado(user);
                jefeAreaRepository.save(jefe);
            }
        }

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

}
