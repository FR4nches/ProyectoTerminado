/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectomundial;

public class Arbitro {
    private String nombre;
    private String nacionalidad;
    private int partidosDirigidos;

    public Arbitro(String nombre, String nacionalidad) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.partidosDirigidos = 0;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    public int getPartidosDirigidos() { return partidosDirigidos; }
    public void sumarPartidoDirigido() { partidosDirigidos++; }

    @Override
    public String toString() {
        return nombre + " (" + nacionalidad + ")";
    }
}

