package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.UsuarioSolicitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioSolicitanteRepository extends JpaRepository<UsuarioSolicitante, Long> {
    Optional<UsuarioSolicitante> findByCorreoNumeroAndEquipo_IdEquipo(String correoNumero, Long idEquipo);
    // Nuevo método para buscar usuarios con mismo correo pero sin equipo asignado
    Optional<UsuarioSolicitante> findByCorreoNumeroAndEquipoIsNull(String correoNumero);
    // Nuevo método para buscar por correo (cualquier usuario con ese correo)
    Optional<UsuarioSolicitante> findByCorreoNumero(String correoNumero);
}