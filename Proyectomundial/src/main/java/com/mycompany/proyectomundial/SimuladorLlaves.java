/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectomundial;

import java.util.Random;

/**
 *
 * @author Mauricio
 */
public class SimuladorLlaves { 

private final Random generadorAleatorio = new Random();


//-----------------------------------------------------------------------------
public Pais[] extraerEquiposClasificados(Pais[][] matrizFaseGrupos) {
    if (matrizFaseGrupos == null) {
        return new Pais[0];
    }

    int totalGrupos = matrizFaseGrupos.length;

    // 24 equipos = 6 grupos -> 16 clasificados
    // 32 equipos = 8 grupos -> 16 clasificados
    // 48 equipos = 12 grupos -> 32 clasificados
    // 64 equipos = 16 grupos -> 32 clasificados

    int cantidadClasificados;

    if (totalGrupos == 6 || totalGrupos == 8) {
        cantidadClasificados = 16;
    } else if (totalGrupos == 12 || totalGrupos == 16) {
        cantidadClasificados = 32;
    } else {
        return new Pais[0];
    }

    Pais[] listaClasificados = new Pais[cantidadClasificados];
    int indiceInsercion = 0;

    // Primero clasifican los dos primeros de cada grupo
    for (int i = 0; i < totalGrupos; i++) {

        if (matrizFaseGrupos[i][0] != null) {
            listaClasificados[indiceInsercion++] = matrizFaseGrupos[i][0];
        }

        if (matrizFaseGrupos[i][1] != null) {
            listaClasificados[indiceInsercion++] = matrizFaseGrupos[i][1];
        }
    }

    // En 24 equipos entran los 4 mejores terceros
    // En 48 equipos entran los 8 mejores terceros
    if (totalGrupos == 6 || totalGrupos == 12) {

        int cantidadTerceros = (totalGrupos == 6) ? 4 : 8;

        Pais[] terceros = new Pais[totalGrupos];

        for (int i = 0; i < totalGrupos; i++) {
            if (matrizFaseGrupos[i].length > 2 &&
                matrizFaseGrupos[i][2] != null) {

                terceros[i] = matrizFaseGrupos[i][2];
            }
        }

        ordenarTerceros(terceros);

        for (int i = 0; i < cantidadTerceros; i++) {
            listaClasificados[indiceInsercion++] = terceros[i];
        }
    }

    return listaClasificados;
}
private void ordenarTerceros(Pais[] terceros) {

    // Ordenar de mayor a menor:
    // 1. Puntos
    // 2. Diferencia de goles
    // 3. Goles a favor

    for (int i = 0; i < terceros.length - 1; i++) {

        for (int j = 0; j < terceros.length - 1 - i; j++) {

            if (terceros[j] == null) {
                continue;
            }

            if (terceros[j + 1] == null) {
                continue;
            }

            Pais actual = terceros[j];
            Pais siguiente = terceros[j + 1];

            boolean intercambiar = false;

            if (actual.getPuntos() < siguiente.getPuntos()) {
                intercambiar = true;

            } else if (actual.getPuntos() == siguiente.getPuntos()
                    && actual.getDiferenciaGoles()
                    < siguiente.getDiferenciaGoles()) {

                intercambiar = true;

            } else if (actual.getPuntos() == siguiente.getPuntos()
                    && actual.getDiferenciaGoles()
                    == siguiente.getDiferenciaGoles()
                    && actual.getGolesAFavor()
                    < siguiente.getGolesAFavor()) {

                intercambiar = true;
            }

            if (intercambiar) {
                terceros[j] = siguiente;
                terceros[j + 1] = actual;
            }
        }
    }
}
private void registrarGolesJugadores(Pais pais, int cantidadGoles) {

    if (pais == null || cantidadGoles <= 0) {
        return;
    }

    Jugador[] plantilla = pais.getPlantilla();
    int cantidadJugadores = pais.getCantidadJugadoresRegistrados();

    if (plantilla == null || cantidadJugadores == 0) {
        return;
    }

    for (int i = 0; i < cantidadGoles; i++) {

        int indice = generadorAleatorio.nextInt(cantidadJugadores);

        if (plantilla[indice] != null) {
            plantilla[indice].sumarGol();
        }
    }
}


//------------------------------------------------------------------------------------------
    public ResultadoEncuentro simularPartido(Pais equipoLocal, Pais equipoVisitante) {
        int golesLocal = generadorAleatorio.nextInt(4);
        int golesVisitante = generadorAleatorio.nextInt(4);

        equipoLocal.registrarGoles(golesLocal, golesVisitante);
        equipoVisitante.registrarGoles(golesVisitante, golesLocal);
        registrarGolesJugadores(equipoLocal, golesLocal);
        registrarGolesJugadores(equipoVisitante, golesVisitante);

        if (golesLocal > golesVisitante) {
            return new ResultadoEncuentro(equipoLocal, golesLocal, golesVisitante, false, 0, 0);
        } else if (golesVisitante > golesLocal) {
            return new ResultadoEncuentro(equipoVisitante, golesLocal, golesVisitante, false, 0, 0);
        } else {
            boolean victoriaLocalEnPenales = generadorAleatorio.nextBoolean();
            int penalesLocal = 5 + (victoriaLocalEnPenales ? 1 : 0);
            int penalesVisitante = 5 + (!victoriaLocalEnPenales ? 1 : 0);
            
            Pais ganador = victoriaLocalEnPenales ? equipoLocal : equipoVisitante;
            return new ResultadoEncuentro(ganador, golesLocal, golesVisitante, true, penalesLocal, penalesVisitante);
        }
    }

    
    
    
    public static class ResultadoEncuentro {
        public final Pais ganador;
        public final int golesLocal;
        public final int golesVisitante;
        public final boolean huboPenales;
        public final int penalesLocal;
        public final int penalesVisitante;

        public ResultadoEncuentro(Pais ganador, int golesLocal, int golesVisitante, 
                                  boolean huboPenales, int penalesLocal, int penalesVisitante) {
            this.ganador = ganador;
            this.golesLocal = golesLocal;
            this.golesVisitante = golesVisitante;
            this.huboPenales = huboPenales;
            this.penalesLocal = penalesLocal;
            this.penalesVisitante = penalesVisitante;
        }
    }
}
