package DSWS2Grupo4.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidencias")
@Getter @Setter @NoArgsConstructor
public class Incidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidencia")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario_solicitante")
    private UsuarioSolicitante usuarioSolicitante;

    @ManyToOne
    @JoinColumn(name = "id_problema")
    private ProblemaSubcategoria problemaSubcategoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private EstadoIncidencia estado = EstadoIncidencia.pendiente;

    @Enumerated(EnumType.STRING)
    @Column(name = "modalidad_atencion")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private UbicacionAtencion ubicacionAtencion = UbicacionAtencion.remoto;

    @Column(name = "fecha_registro")
    @CreationTimestamp
    private LocalDateTime fecha;

    @OneToOne(mappedBy = "incidencia")
    @JsonManagedReference 
    private AsignacionIncidencia asignacion;

    // Método de conveniencia para obtener el empleado asociado al técnico asignado
    public Empleado getTecnico() {
        return asignacion != null ? asignacion.getTecnico().getEmpleado() : null;
    }

    @OneToOne(mappedBy = "incidencia", cascade = CascadeType.ALL)
    @JsonManagedReference
    private HistorialEquipo historialEquipo;
}