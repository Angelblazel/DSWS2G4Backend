package DSWS2Grupo4.service;

import DSWS2Grupo4.DTO.AuthResponse;
import DSWS2Grupo4.DTO.LoginRequest;
import DSWS2Grupo4.Jwt.JwtService;
import DSWS2Grupo4.DTO.RegisterRequest;
import DSWS2Grupo4.model.Empleado;
import DSWS2Grupo4.model.Rol;
import DSWS2Grupo4.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthService {
    private final EmpleadoRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        // Autenticar el usuario
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword_hash()
        ));

        // Buscar el usuario en la base de datos
        Empleado empleado = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Generar el token JWT
        String token = jwtService.getToken(empleado);

        // Construir la respuesta con TODOS los datos del usuario
        return AuthResponse.builder()
                .token(token)
                .idEmpleado(empleado.getIdEmpleado())
                .nombre(empleado.getNombre())
                .rol(empleado.getRole().name())
                .build();
    }
    
    public AuthResponse register(RegisterRequest request){
        Empleado user = Empleado.builder()
                .username(request.getUsername())
                .password_hash(passwordEncoder.encode(request.getPassword_hash()))
                .nombre(request.getNombre())
                .role(  request.getRole() != null ? request.getRole() : Rol.LOGISTICA)
                .build();
        userRepository.save(user);          
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
