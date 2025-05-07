/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DSWS2Grupo4.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EstadoEquipo {
    OPERATIVO("operativo"),
    MANTENIMIENTO("mantenimiento"),
    BAJA("baja");

    private final String value;

    EstadoEquipo(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static EstadoEquipo fromValue(String value) {
        for (EstadoEquipo estado : EstadoEquipo.values()) {
            if (estado.value.equalsIgnoreCase(value)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }
}
