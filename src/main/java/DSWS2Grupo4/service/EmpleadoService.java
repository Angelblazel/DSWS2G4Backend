package DSWS2Grupo4.service;

import DSWS2Grupo4.DTO.EmpleadoDTO;
import DSWS2Grupo4.DTO.EmpleadoRequest;
import DSWS2Grupo4.DTO.EmpleadoResponse;
import DSWS2Grupo4.model.Empleado;
import DSWS2Grupo4.model.Rol;
import DSWS2Grupo4.repository.EmpleadoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpleadoService {
    private final EmpleadoRepository userRepository; 

    @Transactional
    public EmpleadoResponse updateUser(EmpleadoRequest userRequest) {
       
        Empleado user = Empleado.builder()
        .idEmpleado(userRequest.getIdEmpleado())
        .nombre(userRequest.getNombre())
        .role(  Rol.LOGISTICA)
        .build();
        
        userRepository.updateUser(user.getIdEmpleado(), user.getNombre());

        return new EmpleadoResponse("El usuario se registró satisfactoriamente");
    }

    public EmpleadoDTO getUser(Integer id) {
        Empleado user= userRepository.findById(id).orElse(null);
       
        if (user!=null)
        {
            EmpleadoDTO userDTO = EmpleadoDTO.builder()
            .idEmpleado(user.getIdEmpleado())
            .username(user.getUsername())
            .nombre(user.getNombre())
            .build();
            return userDTO;
        }
        return null;
    }
}
