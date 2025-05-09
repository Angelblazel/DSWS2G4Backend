package DSWS2Grupo4.service;

import DSWS2Grupo4.DTO.AuthResponse;
import DSWS2Grupo4.DTO.LoginRequest;
import DSWS2Grupo4.Jwt.JwtService;
import DSWS2Grupo4.DTO.RegisterRequest;
import DSWS2Grupo4.model.Empleado;
import DSWS2Grupo4.model.Rol;
import DSWS2Grupo4.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final EmpleadoRepository userRepository;
    private final JwtService jwtService;
    
    public AuthResponse login(LoginRequest request){
        return null;
    }
    
    public AuthResponse register(RegisterRequest request){
        Empleado user = Empleado.builder()
                .username(request.getUsername())
                .password_hash(request.getPassword_hash())
                .nombre(request.getNombre())
                .role(  Rol.LOGISTICA)
                .build();
        userRepository.save(user);          
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
