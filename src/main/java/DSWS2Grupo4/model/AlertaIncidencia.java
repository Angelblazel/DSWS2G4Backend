package DSWS2Grupo4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "alertas_incidencias")
@Getter
@Setter
@NoArgsConstructor
public class AlertaIncidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_incidencia", nullable = false)
    private Incidencia incidencia;

    @Column(name = "motivo", nullable = false)
    private String motivo;

    @Column(name = "fecha_alerta", nullable = false)
    private LocalDateTime fechaAlerta;
}

