/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectomundial;

public class Jugador {
    private String nombre;
    private int dorsal;
    private String posicion; // Ej: "Portero", "Defensa", "Medio", "Delantero"
    private int goles;
    private int tarjetasAmarillas;
    private int tarjetasRojas;

    public Jugador(String nombre, int dorsal, String posicion) {
        this.nombre = nombre;
        this.dorsal = dorsal;
        this.posicion = posicion;
        this.goles = 0;
        this.tarjetasAmarillas = 0;
        this.tarjetasRojas = 0;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getDorsal() { return dorsal; }
    public void setDorsal(int dorsal) { this.dorsal = dorsal; }

    public String getPosicion() { return posicion; }
    public void setPosicion(String posicion) { this.posicion = posicion; }

    public int getGoles() { return goles; }
    public void sumarGol() { this.goles++; }

    public int getTarjetasAmarillas() { return tarjetasAmarillas; }
    public void sumarAmarilla() { this.tarjetasAmarillas++; }

    public int getTarjetasRojas() { return tarjetasRojas; }
    public void sumarRoja() { this.tarjetasRojas++; }

    @Override
    public String toString() {
        return "#" + dorsal + " " + nombre + " (" + posicion + ")";
    }
}