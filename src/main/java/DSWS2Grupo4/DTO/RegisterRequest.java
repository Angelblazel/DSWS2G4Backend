package DSWS2Grupo4.DTO;

import DSWS2Grupo4.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String nombre;
    private String username;
    private String password_hash;
    private Rol role;
}
