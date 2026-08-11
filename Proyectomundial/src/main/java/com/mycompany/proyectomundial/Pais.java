/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectomundial;



public class Pais {
    public static final int TAMANO_PLANTILLA = 23;  

    private String nombre;
    private String directorTecnico;
    private Jugador[] plantilla;
    private int cantidadJugadoresRegistrados; 

    
    private int puntos;
    private int golesAFavor;
    private int golesEnContra;

    public Pais(String nombre, String directorTecnico) {
        this.nombre = nombre;
        this.directorTecnico = directorTecnico;
        this.plantilla = new Jugador[TAMANO_PLANTILLA];
        this.cantidadJugadoresRegistrados = 0;
        this.puntos = 0;
        this.golesAFavor = 0;
        this.golesEnContra = 0;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDirectorTecnico() { return directorTecnico; }
    public void setDirectorTecnico(String dt) { this.directorTecnico = dt; }

    public Jugador[] getPlantilla() { return plantilla; }

    
    public boolean agregarJugador(Jugador j) {
        if (cantidadJugadoresRegistrados >= TAMANO_PLANTILLA) return false;
        plantilla[cantidadJugadoresRegistrados] = j;
        cantidadJugadoresRegistrados++;
        return true;
    }

   
    public boolean editarJugador(int indice, Jugador nuevo) {
        if (indice < 0 || indice >= cantidadJugadoresRegistrados) return false;
        plantilla[indice] = nuevo;
        return true;
    }

    public int getCantidadJugadoresRegistrados() { return cantidadJugadoresRegistrados; }

    
    public int getPuntos() { return puntos; }
    public int getGolesAFavor() { return golesAFavor; }
    public int getGolesEnContra() { return golesEnContra; }
    public int getDiferenciaGoles() { return golesAFavor - golesEnContra; }

    public void sumarVictoria() { puntos += 3; }
    public void sumarEmpate() { puntos += 1; }
    
    public void registrarGoles(int golesFavor, int golesContra) {
        golesAFavor += golesFavor;
        golesEnContra += golesContra;
    }

    public void reiniciarEstadisticas() {
        puntos = 0;
        golesAFavor = 0;
        golesEnContra = 0;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
