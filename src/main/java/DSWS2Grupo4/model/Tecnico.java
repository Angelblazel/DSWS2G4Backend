/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DSWS2Grupo4.model;

import jakarta.persistence.*;


@Entity
@Table(name = "tecnicos")
public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tecnico") // Cambiar a nombre correcto de columna
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_empleado", nullable = false) // Especificar nombre de columna para la relación
    private Empleado empleado;

    @Column(name = "carga_maxima") // Especificar nombre de columna
    private int cargaMaxima = 6;

    @Column(name = "carga_actual") // Especificar nombre de columna
    private int cargaActual = 0;

    public Empleado getEmpleado() {
        return this.empleado;
    }
}


