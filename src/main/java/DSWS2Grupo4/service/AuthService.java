package DSWS2Grupo4.service;

import DSWS2Grupo4.model.Empleado;
import DSWS2Grupo4.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private EmpleadoRepository empleadoRepo;

    public Optional<Empleado> login(String username, String password) {
        return empleadoRepo.findByUsername(username)
            .filter(emp -> emp.getPasswordHash().equals(password))
            .filter(emp -> {
                String rol = emp.getRol().getNombreRol().toLowerCase();
                return rol.equals("tecnico") || rol.equals("logistica") || rol.equals("jefe");
            });
    }
}
