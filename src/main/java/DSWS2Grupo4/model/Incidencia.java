package DSWS2Grupo4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
    @JoinColumn(name = "id_usuario_solicitante", nullable = false)
    private UsuarioSolicitante usuarioSolicitante;

    @Column(nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoIncidencia estado = EstadoIncidencia.pendiente;

    @Enumerated(EnumType.STRING)
    @Column(name = "ubicacion_atencion", nullable = false)
    private UbicacionAtencion ubicacion = UbicacionAtencion.remoto;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime fecha;
}
