/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectomundial;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

/**
 *
 * @author gaboa
 */
public class Modulo2 extends JFrame{
private Pais[] paises;
    private Sede[] sedes;
    private Arbitro[] arbitros;

    // Matriz de grupos: numGrupos x 4 equipos
    private Pais[][] matrizGrupos; 
    private int numGrupos;

    private JPanel panelTablasGrupos;
    private JButton btnRealizarSorteo;
    private JButton btnContinuarModulo3;

    public Modulo2(Pais[] paises, Sede[] sedes, Arbitro[] arbitros) {
        this.paises = paises;
        this.sedes = sedes;
        this.arbitros = arbitros;

        this.numGrupos = paises.length / 4;
        this.matrizGrupos = new Pais[numGrupos][4];

        setTitle("Copa Mundial: Sorteo y Fase de Grupos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        crearInterfaz();
    }

    private void crearInterfaz() {
        FondoMundial fondo = new FondoMundial();
        setContentPane(fondo);
        fondo.setLayout(new BorderLayout(10, 10));

        // Panel Superior: Botones de Acción
        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.setOpaque(false);

        btnRealizarSorteo = new JButton("Realizar sorteo aleatorio");
        btnRealizarSorteo.setFont(new Font("Arial", Font.BOLD, 14));
        btnRealizarSorteo.addActionListener(this::onRealizarSorteo);
        panelSuperior.add(btnRealizarSorteo);

        btnContinuarModulo3 = new JButton("Pasar a la simulación");
        btnContinuarModulo3.setEnabled(false); 
        btnContinuarModulo3.addActionListener(this::onContinuarModulo3);
        panelSuperior.add(btnContinuarModulo3);

        add(panelSuperior, BorderLayout.NORTH);

        // Panel Central: Renderizado de Grupos y Tablas
        panelTablasGrupos = new JPanel();
        panelTablasGrupos.setOpaque(false);
        panelTablasGrupos.setLayout(new GridLayout(0, 2, 15, 15)); 
        
        JScrollPane scrollPane = new JScrollPane(panelTablasGrupos);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Algoritmo de Distribución Aleatoria en Matriz usando SOLO ARREGLOS
    private void onRealizarSorteo(ActionEvent e) {
        // 1. Crear una copia del arreglo de países original en un nuevo arreglo tradicional
        Pais[] copiaPaises = new Pais[paises.length];
        for (int i = 0; i < paises.length; i++) {
            copiaPaises[i] = paises[i];
        }

        // 2. Desordenar el arreglo con el algoritmo Fisher-Yates (sin Collections.shuffle)
        Random rnd = new Random();
        for (int i = copiaPaises.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            // Intercambio de posiciones (swap)
            Pais temp = copiaPaises[i];
            copiaPaises[i] = copiaPaises[j];
            copiaPaises[j] = temp;
        }

        // 3. Llenar la matriz de grupos (numGrupos x 4) desde el arreglo desordenado
        int index = 0;
        for (int g = 0; g < numGrupos; g++) {
            for (int ePos = 0; ePos < 4; ePos++) {
                matrizGrupos[g][ePos] = copiaPaises[index];
                index++;
            }
        }

        // 4. Dibujar visualmente las tablas de posiciones iniciales
        dibujarTablasGrupos();

        // 5. Habilitar el botón del Módulo 3
        btnContinuarModulo3.setEnabled(true);
        btnRealizarSorteo.setText("Volver a sortear");
        
        JOptionPane.showMessageDialog(this, "Sorteo completado. Se han conformado " + numGrupos + " grupos.");
    }

    // Dibujar en pantalla la representación gráfica de las tablas de cada grupo
    private void dibujarTablasGrupos() {
        panelTablasGrupos.removeAll();

        char letraGrupo = 'A';

        for (int g = 0; g < numGrupos; g++) {
            JPanel panelGrupo = new JPanel(new BorderLayout());
            panelGrupo.setBorder(BorderFactory.createTitledBorder("GRUPO " + (char)(letraGrupo + g)));

            // Columnas para la tabla
            String[] columnas = {"Equipo", "Puntos", "GF", "GC", "DG"};
            DefaultTableModel model = new DefaultTableModel(columnas, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Hacer la tabla de solo lectura
                }
            };

            // Estadísticas iniciales en Cero
            for (int ePos = 0; ePos < 4; ePos++) {
                Pais p = matrizGrupos[g][ePos];
                Object[] fila = {
                    p.getNombre(), 
                    0, // Puntos
                    0, // Goles a Favor
                    0, // Goles en Contra
                    0  // Diferencia de Goles
                };
                model.addRow(fila);
            }

            JTable tabla = new JTable(model);
            tabla.setFillsViewportHeight(true);
            
            panelGrupo.add(new JScrollPane(tabla), BorderLayout.CENTER);
            panelTablasGrupos.add(panelGrupo);
        }

        panelTablasGrupos.revalidate();
        panelTablasGrupos.repaint();
    }

    private void onContinuarModulo3(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Pasando al Módulo 3: Simulación de Partidos...");
        // TODO: Instanciar Módulo 3 pasando matrizGrupos, sedes, arbitros
        // new Modulo3Simulacion(matrizGrupos, sedes, arbitros).setVisible(true);
        // this.dispose();
        
        
        Modulo3Simulacion modulo3 = new Modulo3Simulacion(matrizGrupos, sedes, arbitros);
        modulo3.setVisible(true);
        
        // cierra la ventana actual
        this.dispose();
        
    }
}