/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectomundial;

public class Sede {
    private String nombreEstadio;
    private String ciudad;
    private int capacidad;
    private int asistenciaTotal;
    private double recaudacionTotal;

    public Sede(String nombreEstadio, String ciudad, int capacidad) {
        this.nombreEstadio = nombreEstadio;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
        this.asistenciaTotal = 0;
        this.recaudacionTotal = 0;
    }

    public String getNombreEstadio() { return nombreEstadio; }
    public void setNombreEstadio(String n) { this.nombreEstadio = n; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String c) { this.ciudad = c; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public int getAsistenciaTotal() {
        return asistenciaTotal;
    }

    public double getRecaudacionTotal() {
        return recaudacionTotal;
    }
    public void sumarAsistencia(int cantidad) {
    asistenciaTotal += cantidad;
}

    public void sumarRecaudacion(double monto) {
    recaudacionTotal += monto;
}
    

    @Override
    public String toString() {
        return nombreEstadio + " - " + ciudad;
        
    }
}
