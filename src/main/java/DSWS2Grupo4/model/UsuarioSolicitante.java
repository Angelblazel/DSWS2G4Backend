package DSWS2Grupo4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios_solicitantes")
@Getter @Setter @NoArgsConstructor
public class UsuarioSolicitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_solicitante")
    private Long id;

    @Column(name = "correo", nullable = false)
    private String correoNumero;

    @ManyToOne
    @JoinColumn(name = "id_equipo", nullable = false)
    private Equipo equipo;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_datos_empleados", nullable = false)
    private DatosEmpleado datosEmpleado;
}
