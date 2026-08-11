/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectomundial;

/**
 *
 * @author Mauricio
 */
public enum EtapaEliminatoria {
    DIECISEISAVOS(32, "Dieciseisavos de Final"),
    OCTAVOS(16, "Octavos de Final"),
    CUARTOS(8, "Cuartos de Final"),
    SEMIFINAL(4, "Semifinales"),
    FINAL(2, "Gran Final"),
    CONCLUIDO(1, "Torneo Finalizado");

    private final int cantidadEquipos;
    private final String nombreEtiqueta;

    EtapaEliminatoria(int cantidadEquipos, String nombreEtiqueta) {
        this.cantidadEquipos = cantidadEquipos;
        this.nombreEtiqueta = nombreEtiqueta;
    }

    public int getCantidadEquipos() {
        return cantidadEquipos;
    }

    public String getNombreEtiqueta() {
        return nombreEtiqueta;
    }

    public static EtapaEliminatoria obtenerPorCantidad(int cantidad) {
        for (EtapaEliminatoria etapa : values()) {
            if (etapa.getCantidadEquipos() == cantidad) {
                return etapa;
            }
        }
        return OCTAVOS;
    }
}