package DSWS2Grupo4.repository;

import DSWS2Grupo4.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findByUsername(String username);
    
   @Modifying
   @Query("UPDATE Empleado u SET u.nombre = :nombre WHERE u.idEmpleado = :idEmpleado")
   void updateUser(@Param("idEmpleado") Integer idEmpleado, @Param("nombre") String nombre);

}
