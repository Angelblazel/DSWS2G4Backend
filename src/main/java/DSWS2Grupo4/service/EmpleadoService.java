package DSWS2Grupo4.service;

import DSWS2Grupo4.DTO.UsuarioDTO;
import DSWS2Grupo4.DTO.UsuarioRequest;
import DSWS2Grupo4.DTO.UsuarioResponse;
import DSWS2Grupo4.model.Usuario;
import DSWS2Grupo4.model.Rol;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import DSWS2Grupo4.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class EmpleadoService {
    private final UsuarioRepository userRepository; 

    @Transactional
    public UsuarioResponse updateUser(UsuarioRequest userRequest) {
       
        Usuario user = Usuario.builder()
        .idEmpleado(userRequest.getIdEmpleado())
        .role(  Rol.LOGISTICA)
        .build();
        
        return new UsuarioResponse("El usuario se registró satisfactoriamente");
    }

    public UsuarioDTO getUser(Integer id) {
        Usuario user= userRepository.findById(id).orElse(null);
       
        if (user!=null)
        {
            UsuarioDTO userDTO = UsuarioDTO.builder()
            .idEmpleado(user.getIdEmpleado())
            .username(user.getUsername())
            .build();
            return userDTO;
        }
        return null;
    }
}
