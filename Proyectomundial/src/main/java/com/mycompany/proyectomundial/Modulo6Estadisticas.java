package com.mycompany.proyectomundial;

import javax.swing.*;
import java.awt.*;

public class Modulo6Estadisticas extends JFrame {

    private final Pais[][] matrizGrupos;
    private final Sede[] sedes;
    private final Arbitro[] arbitros;

    private final Pais VariableCampeon;
    private final Pais VariableSubcampeon;
    private Jugador VariableBotaOro;
    private Jugador VariableMejorJugador;
    private Arbitro VariableMejorArbitro;

    private JTextArea areaEstadisticas;

    public Modulo6Estadisticas(
            Pais[][] matrizGrupos,
            Sede[] sedes,
            Arbitro[] arbitros,
            Pais campeon,
            Pais subcampeon) {

        this.matrizGrupos = matrizGrupos;
        this.sedes = sedes;
        this.arbitros = arbitros;

        this.VariableCampeon = campeon;
        this.VariableSubcampeon = subcampeon;

        setTitle("Copa Mundial - Estadísticas Finales");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        crearInterfaz();
        generarEstadisticas();
    }

    private void crearInterfaz() {

        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel(
                "ESTADÍSTICAS FINALES DEL TORNEO",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        add(titulo, BorderLayout.NORTH);

        areaEstadisticas = new JTextArea();
        areaEstadisticas.setEditable(false);
        areaEstadisticas.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(areaEstadisticas);

        add(scroll, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();

        JButton btnCerrar = new JButton("Cerrar");

        btnCerrar.addActionListener(e -> dispose());

        panelInferior.add(btnCerrar);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void generarEstadisticas() {

        StringBuilder resultado = new StringBuilder();

        resultado.append("==============================================\n");
        resultado.append("          RESUMEN FINAL DEL TORNEO\n");
        resultado.append("==============================================\n\n");

        // CAMPEÓN
        resultado.append("CAMPEÓN\n");
        resultado.append("----------------------------------------------\n");

        if (VariableCampeon != null) {
            resultado.append("🏆 ").append(VariableCampeon.getNombre()).append("\n");
        } else {
            resultado.append("No disponible\n");
        }

        resultado.append("\n");

        // SUBCAMPEÓN
        resultado.append("SUBCAMPEÓN\n");
        resultado.append("----------------------------------------------\n");

        if (VariableSubcampeon != null) {
            resultado.append("🥈 ").append(VariableSubcampeon.getNombre()).append("\n");
        } else {
            resultado.append("No disponible\n");
        }

        resultado.append("\n");

        // TOP 5 GOLEADORES
        resultado.append("TOP 5 GOLEADORES - BOTA DE ORO\n");
        resultado.append("----------------------------------------------\n");

        Jugador[] jugadores = obtenerTodosLosJugadores();

        ordenarJugadoresPorGoles(jugadores);

        int cantidadGoleadores = 0;

        for (int i = 0; i < jugadores.length && cantidadGoleadores < 5; i++) {

            if (jugadores[i] != null) {

                if (jugadores[i].getGoles() > 0) {

                    cantidadGoleadores++;

                    resultado.append(cantidadGoleadores)
                            .append(". ")
                            .append(jugadores[i].getNombre())
                            .append(" - ")
                            .append(jugadores[i].getGoles())
                            .append(" goles\n");

                    if (cantidadGoleadores == 1) {
                        VariableBotaOro = jugadores[i];
                    }
                }
            }
        }

        if (cantidadGoleadores == 0) {
            resultado.append("No se registraron goles de jugadores.\n");
        }

        resultado.append("\n");

        // MEJOR JUGADOR
        resultado.append("MEJOR JUGADOR\n");
        resultado.append("----------------------------------------------\n");

        if (VariableBotaOro != null) {

            VariableMejorJugador = VariableBotaOro;

            resultado.append(VariableMejorJugador.getNombre())
                    .append(" - ")
                    .append(VariableMejorJugador.getGoles())
                    .append(" goles\n");

        } else {
            resultado.append("No disponible\n");
        }

        resultado.append("\n");

        // REPORTE DISCIPLINARIO
        resultado.append("REPORTE DISCIPLINARIO\n");
        resultado.append("----------------------------------------------\n");

        Jugador[] jugadoresDisciplina = obtenerTodosLosJugadores();

        ordenarJugadoresDisciplina(jugadoresDisciplina);

        int mostrados = 0;

        for (int i = 0;
                i < jugadoresDisciplina.length && mostrados < 5;
                i++) {

            if (jugadoresDisciplina[i] != null) {

                int amarillas =
                        jugadoresDisciplina[i].getTarjetasAmarillas();

                int rojas =
                        jugadoresDisciplina[i].getTarjetasRojas();

                if (amarillas > 0 || rojas > 0) {

                    mostrados++;

                    resultado.append(mostrados)
                            .append(". ")
                            .append(jugadoresDisciplina[i].getNombre())
                            .append(" - Amarillas: ")
                            .append(amarillas)
                            .append(" | Rojas: ")
                            .append(rojas)
                            .append("\n");
                }
            }
        }

        if (mostrados == 0) {
            resultado.append("No se registraron tarjetas.\n");
        }

        resultado.append("\n");

        // MEJOR ÁRBITRO
        resultado.append("MEJOR ÁRBITRO\n");
        resultado.append("----------------------------------------------\n");

        VariableMejorArbitro = obtenerMejorArbitro();

        if (VariableMejorArbitro != null) {

            resultado.append(VariableMejorArbitro.getNombre())
                    .append(" (")
                    .append(VariableMejorArbitro.getNacionalidad())
                    .append(")")
                    .append(" - Partidos dirigidos: ")
                    .append(VariableMejorArbitro.getPartidosDirigidos())
                    .append("\n");

        } else {
            resultado.append("No disponible\n");
        }

        resultado.append("\n");

        // ASISTENCIA Y RECAUDACIÓN
        resultado.append("ASISTENCIA Y RECAUDACIÓN\n");
        resultado.append("----------------------------------------------\n");

        int asistenciaTotal = 0;
        double recaudacionTotal = 0;

        if (sedes != null) {

            for (int i = 0; i < sedes.length; i++) {

                if (sedes[i] != null) {

                    asistenciaTotal += sedes[i].getAsistenciaTotal();
                    recaudacionTotal += sedes[i].getRecaudacionTotal();
                }
            }
        }

        resultado.append("Asistencia total: ")
                .append(asistenciaTotal)
                .append(" personas\n");

        resultado.append("Recaudación total: ₡")
                .append(String.format("%.2f", recaudacionTotal))
                .append("\n");

        resultado.append("\n==============================================\n");

        areaEstadisticas.setText(resultado.toString());
    }

    private Jugador[] obtenerTodosLosJugadores() {

        int capacidadMaxima =
                matrizGrupos.length * Pais.TAMANO_PLANTILLA;

        Jugador[] todosLosJugadores =
                new Jugador[capacidadMaxima];

        int indice = 0;

        for (int i = 0; i < matrizGrupos.length; i++) {

            for (int j = 0; j < matrizGrupos[i].length; j++) {

                Pais pais = matrizGrupos[i][j];

                if (pais != null) {

                    Jugador[] plantilla = pais.getPlantilla();

                    int cantidad =
                            pais.getCantidadJugadoresRegistrados();

                    for (int k = 0;
                            k < cantidad;
                            k++) {

                        if (plantilla[k] != null &&
                                indice < todosLosJugadores.length) {

                            todosLosJugadores[indice] =
                                    plantilla[k];

                            indice++;
                        }
                    }
                }
            }
        }

        return todosLosJugadores;
    }

    private void ordenarJugadoresPorGoles(Jugador[] jugadores) {

        for (int i = 0;
                i < jugadores.length - 1;
                i++) {

            for (int j = 0;
                    j < jugadores.length - 1 - i;
                    j++) {

                if (jugadores[j] == null) {
                    continue;
                }

                if (jugadores[j + 1] == null) {
                    continue;
                }

                if (jugadores[j].getGoles()
                        < jugadores[j + 1].getGoles()) {

                    Jugador temporal = jugadores[j];

                    jugadores[j] = jugadores[j + 1];

                    jugadores[j + 1] = temporal;
                }
            }
        }
    }

    private void ordenarJugadoresDisciplina(Jugador[] jugadores) {

        for (int i = 0;
                i < jugadores.length - 1;
                i++) {

            for (int j = 0;
                    j < jugadores.length - 1 - i;
                    j++) {

                if (jugadores[j] == null) {
                    continue;
                }

                if (jugadores[j + 1] == null) {
                    continue;
                }

                int rojasActual =
                        jugadores[j].getTarjetasRojas();

                int rojasSiguiente =
                        jugadores[j + 1].getTarjetasRojas();

                int amarillasActual =
                        jugadores[j].getTarjetasAmarillas();

                int amarillasSiguiente =
                        jugadores[j + 1].getTarjetasAmarillas();

                boolean intercambiar = false;

                if (rojasActual < rojasSiguiente) {

                    intercambiar = true;

                } else if (rojasActual == rojasSiguiente
                        && amarillasActual < amarillasSiguiente) {

                    intercambiar = true;
                }

                if (intercambiar) {

                    Jugador temporal = jugadores[j];

                    jugadores[j] = jugadores[j + 1];

                    jugadores[j + 1] = temporal;
                }
            }
        }
    }

    private Arbitro obtenerMejorArbitro() {

        if (arbitros == null ||
                arbitros.length == 0) {

            return null;
        }

        Arbitro mejor = null;

        for (int i = 0;
                i < arbitros.length;
                i++) {

            if (arbitros[i] != null) {

                if (mejor == null ||
                        arbitros[i].getPartidosDirigidos()
                        > mejor.getPartidosDirigidos()) {

                    mejor = arbitros[i];
                }
            }
        }

        return mejor;
    }
}