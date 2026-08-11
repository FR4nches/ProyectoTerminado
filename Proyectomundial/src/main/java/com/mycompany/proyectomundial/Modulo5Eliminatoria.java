/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectomundial;

import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;

/**
 *
 * @author Mauricio
 */
public class Modulo5Eliminatoria extends JFrame {

    private final Pais[][] matrizGrupos;
    private final Sede[] sedes;
    private final Arbitro[] arbitros;
    private final SimuladorLlaves simulador;
    private Pais campeon;
    private Pais subcampeon;
    private JButton btnEstadisticas;

    private JPanel panelLlaves;
    private JTextArea areaDetalles;

    private JPanel colDieciseisavos;
    private JPanel colOctavos;
    private JPanel colCuartos;
    private JPanel colSemis;
    private JPanel colFinal;

    // Arreglos para almacenar temporalmente las parejas de la siguiente ronda
    private Pais[] parejaOctavos = new Pais[16];
    private Pais[] parejaCuartos = new Pais[8];
    private Pais[] parejaSemis = new Pais[4];
    private Pais[] parejaFinal = new Pais[2];
    private final Pais[] parejaTercerPuesto = new Pais[2];

    private JPanel colTercerPuesto;

    private Pais tercerLugar;

    public Modulo5Eliminatoria(Pais[][] matrizGrupos, Sede[] sedes, Arbitro[] arbitros) {
        this.matrizGrupos = matrizGrupos;
        this.sedes = sedes;
        this.arbitros = arbitros;
        this.simulador = new SimuladorLlaves();

        setTitle("- Fase Eliminatoria -");
        setSize(1400, 850);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarInterfaz();
    }
    
    
//-------------------------------------------------------------------------------------------------
    private void inicializarInterfaz() {
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitulo = new JLabel(" - Fase de eliminación directa -", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        panelLlaves = new JPanel(new GridLayout(1, 4, 10, 0));
        panelLlaves.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane scrollGeneral = new JScrollPane(panelLlaves);
        scrollGeneral.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollGeneral, BorderLayout.CENTER);

        areaDetalles = new JTextArea(5, 50);
        areaDetalles.setEditable(false);
        areaDetalles.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollDetalles = new JScrollPane(areaDetalles);
        scrollDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del Encuentro / Penales / Sede / Arbitro"));
        JPanel panelInferior = new JPanel(new BorderLayout());

panelInferior.add(scrollDetalles, BorderLayout.CENTER);

btnEstadisticas = new JButton("Ver Estadísticas del Torneo");
btnEstadisticas.setEnabled(false);
btnEstadisticas.addActionListener(e -> abrirModulo6());

panelInferior.add(btnEstadisticas, BorderLayout.SOUTH);


add(panelInferior, BorderLayout.SOUTH);
        

        generarEstructuraLlaves();
    }
    
    
//-------------------------------------------------------------------------------------------    
    private void generarEstructuraLlaves() {

    Pais[] clasificados = simulador.extraerEquiposClasificados(matrizGrupos);

    if (clasificados == null || clasificados.length < 16) {
        JOptionPane.showMessageDialog(
                this,
                "No hay suficientes equipos clasificados para armar la fase eliminatoria.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    panelLlaves.removeAll();

    if (clasificados.length == 32) {

        colDieciseisavos = crearColumnaRonda("- Dieciseisavos de Final -");
        colOctavos = crearColumnaRonda("- Octavos de Final -");
        colCuartos = crearColumnaRonda("- Cuartos de Final -");
        colSemis = crearColumnaRonda("- Semifinales -");
        colFinal = crearColumnaRonda("- Gran Final -");
        colTercerPuesto = crearColumnaRonda("- Tercer Lugar -");

        panelLlaves.setLayout(new GridLayout(1, 6, 10, 0));

        panelLlaves.add(colDieciseisavos);
        panelLlaves.add(colOctavos);
        panelLlaves.add(colCuartos);
        panelLlaves.add(colSemis);
        panelLlaves.add(colFinal);
        panelLlaves.add(colTercerPuesto);
        

        for (int i = 0; i < 32; i += 2) {

            Pais local = clasificados[i];
            Pais visitante = clasificados[i + 1];

            int indiceCruce = i / 2;

            JPanel tarjeta = crearTarjetaCruce(
                    local,
                    visitante,
                    "Dieciseisavos",
                    indiceCruce
            );

            colDieciseisavos.add(tarjeta);
        }

    } else {

        colOctavos = crearColumnaRonda("- Octavos de Final -");
        colCuartos = crearColumnaRonda("- Cuartos de Final -");
        colSemis = crearColumnaRonda("- Semifinales -");
        colFinal = crearColumnaRonda("- Gran Final -");
        colTercerPuesto = crearColumnaRonda("- Tercer Lugar -");
        

        panelLlaves.setLayout(new GridLayout(1, 5, 10, 0));

        panelLlaves.add(colOctavos);
        panelLlaves.add(colCuartos);
        panelLlaves.add(colSemis);
        panelLlaves.add(colFinal);
        panelLlaves.add(colTercerPuesto);

        for (int i = 0; i < 16; i += 2) {

            Pais local = clasificados[i];
            Pais visitante = clasificados[i + 1];

            int indiceCruce = i / 2;

            JPanel tarjeta = crearTarjetaCruce(
                    local,
                    visitante,
                    "Octavos",
                    indiceCruce
            );

            colOctavos.add(tarjeta);
        }
    }

    panelLlaves.revalidate();
    panelLlaves.repaint();
}

    private JPanel crearColumnaRonda(String tituloRonda) {
        JPanel columna = new JPanel();
        columna.setLayout(new BoxLayout(columna, BoxLayout.Y_AXIS));
        columna.setBorder(BorderFactory.createTitledBorder(tituloRonda));
        return columna;
    }

//-------------------------------------------------------------------------------------------
    private JPanel crearTarjetaCruce(Pais local, Pais visitante, String ronda, int indiceCruce) {
        JPanel tarjeta = new JPanel(new GridLayout(3, 1, 1, 1));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(3, 3, 3, 3),
                BorderFactory.createLineBorder(Color.GRAY, 1)
        ));
        tarjeta.setMaximumSize(new Dimension(280, 75));

        JLabel lblLocal = new JLabel(" " + local.getNombre());
        JLabel lblVisitante = new JLabel(" " + visitante.getNombre());
        JButton btnSimular = new JButton("Simular Partido");

        btnSimular.addActionListener(e -> {
            Sede sede = (sedes != null && sedes.length > 0) ? sedes[(int) (Math.random() * sedes.length)] : null;
            Arbitro arbitro = (arbitros != null && arbitros.length > 0) ? arbitros[(int) (Math.random() * arbitros.length)] : null;
            if (sede != null) {

    int asistencia = (int) (Math.random() * (sede.getCapacidad() + 1));

    double precioEntrada = 10 + (Math.random() * 41);

    double recaudacion = asistencia * precioEntrada;

    sede.sumarAsistencia(asistencia);
    sede.sumarRecaudacion(recaudacion);
}

            SimuladorLlaves.ResultadoEncuentro res = simulador.simularPartido(local, visitante);

            lblLocal.setText(" " + local.getNombre() + " (" + res.golesLocal + ")");
            lblVisitante.setText(" " + visitante.getNombre() + " (" + res.golesVisitante + ")");
            btnSimular.setEnabled(false);

            StringBuilder detalle = new StringBuilder();
            detalle.append("--- ").append(ronda.toUpperCase()).append(" ---\n");
            detalle.append("Encuentro --> ").append(local.getNombre()).append(" vs ").append(visitante.getNombre()).append("\n");
            if (sede != null) {
                detalle.append("Estadio --> ").append(sede.getNombreEstadio()).append(" (").append(sede.getCiudad()).append(")\n");
            }
           if (arbitro != null) {

    arbitro.sumarPartidoDirigido();

    detalle.append("Árbitro--> ")
            .append(arbitro.getNombre())
            .append("\n");
}
            detalle.append("Resultado --> ").append(local.getNombre()).append(" ").append(res.golesLocal).append(" - ").append(res.golesVisitante).append(" ").append(visitante.getNombre()).append("\n");

            if (res.huboPenales) {
                detalle.append("Definición por Penales --> ")
                        .append(local.getNombre()).append(" ").append(res.penalesLocal)
                        .append(" - ").append(res.penalesVisitante).append(" ").append(visitante.getNombre()).append("\n");
            }
            detalle.append("Ganador y Clasificado --> ").append(res.ganador.getNombre()).append("\n");
            areaDetalles.setText(detalle.toString());

            if (ronda.equals("Semifinales")) {

    Pais perdedor;

    if (res.ganador == local) {
        perdedor = visitante;
    } else {
        perdedor = local;
    }

    parejaTercerPuesto[indiceCruce] = perdedor;

    evaluarYCrearCruce(
            parejaTercerPuesto,
            indiceCruce,
            colTercerPuesto,
            "Tercer Puesto"
    );
}if (ronda.equals("Final")) {
    campeon = res.ganador;

    if (res.ganador == local) {
        subcampeon = visitante;
    } else {
        subcampeon = local;
    }
}if (ronda.equals("Tercer Puesto")) {
    tercerLugar = res.ganador;

    JOptionPane.showMessageDialog(
            this,
            "🥉 El tercer lugar es: "
            + tercerLugar.getNombre(),
            "Tercer Lugar",
            JOptionPane.INFORMATION_MESSAGE
    );
}

avanzarGanadorDirecto(ronda, indiceCruce, res.ganador);
        });

        tarjeta.add(lblLocal);
        tarjeta.add(lblVisitante);
        tarjeta.add(btnSimular);

        return tarjeta;
    }

//-------------------------------------------------------------------------------------------    
   private void avanzarGanadorDirecto(String rondaActual, int indiceCruce, Pais ganador) {

    if (rondaActual.equals("Dieciseisavos")) {

        parejaOctavos[indiceCruce] = ganador;

        evaluarYCrearCruce(
                parejaOctavos,
                indiceCruce,
                colOctavos,
                "Octavos"
        );

    } else if (rondaActual.equals("Octavos")) {

        parejaCuartos[indiceCruce] = ganador;

        evaluarYCrearCruce(
                parejaCuartos,
                indiceCruce,
                colCuartos,
                "Cuartos"
        );

    } else if (rondaActual.equals("Cuartos")) {

        parejaSemis[indiceCruce] = ganador;

        evaluarYCrearCruce(
                parejaSemis,
                indiceCruce,
                colSemis,
                "Semifinales"
        );

    } else if (rondaActual.equals("Semifinales")) {

        parejaFinal[indiceCruce] = ganador;

        evaluarYCrearCruce(
                parejaFinal,
                indiceCruce,
                colFinal,
                "Final"
        );

    } else if (rondaActual.equals("Final")) {

        JOptionPane.showMessageDialog(
                this,
                "¡EL CAMPEÓN DEL MUNDIAL ES... --> "
                + ganador.getNombre().toUpperCase(),
                "- Fin del Mundial 2026 -",
                JOptionPane.INFORMATION_MESSAGE
        );
        btnEstadisticas.setEnabled(true);
    }
}
//-----------------------------------------------------------------------------------------------------------
    private void evaluarYCrearCruce(Pais[] arregloRonda, int indiceCruce, JPanel columnaDestino, String siguienteRonda) {
        int parejaIndex = (indiceCruce % 2 == 0) ? indiceCruce + 1 : indiceCruce - 1;

        if (arregloRonda[parejaIndex] != null) {
            int nuevoIndiceCruce = indiceCruce / 2;
            Pais local = arregloRonda[Math.min(indiceCruce, parejaIndex)];
            Pais visitante = arregloRonda[Math.max(indiceCruce, parejaIndex)];

            JPanel tarjeta = crearTarjetaCruce(local, visitante, siguienteRonda, nuevoIndiceCruce);
            columnaDestino.add(tarjeta);






            columnaDestino.revalidate();
            columnaDestino.repaint();
            panelLlaves.revalidate();
            panelLlaves.repaint();
        }
    }
    public Pais getCampeon() {
    return campeon;
}

public Pais getSubcampeon() {
    return subcampeon;
}
private void abrirModulo6() {

    Modulo6Estadisticas modulo6 =
            new Modulo6Estadisticas(
                    matrizGrupos,
                    sedes,
                    arbitros,
                    campeon,
                    subcampeon
            );

    modulo6.setVisible(true);
    this.dispose();
}
}