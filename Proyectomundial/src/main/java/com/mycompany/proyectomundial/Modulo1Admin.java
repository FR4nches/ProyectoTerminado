/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectomundial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class Modulo1Admin extends JFrame {

    private Pais[] paises;
    private Sede[] sedes;
    private Arbitro[] arbitros;

    private int cantidadEquipos; 
    private int cantidadRegistrados = 0;

    
    private JComboBox<Integer> comboTamano;
    private JButton btnConfirmarTamano;
    private JButton btnGenerarDemo;

    // Variables de los paises
    private JTextField txtNombrePais;
    private JTextField txtDirectorTecnico;
    private JList<String> listaPaises;
    private DefaultListModel<String> modeloListaPaises;

    // Variables de los jugadores
    private JComboBox<Pais> comboPaisesJugador;
    private JTextField txtNombreJugador;
    private JSpinner spinDorsal;
    private JComboBox<String> comboPosicion;
    private JList<String> listaJugadores;
    private DefaultListModel<String> modeloListaJugadores;
    private JButton btnAgregarJugador;
    private JButton btnEditarJugador;
    
    // Variables de los estadios
    private JTextField txtNombreEstadio;
    private JTextField txtCiudadEstadio;
    private JSpinner capacidadEstadio;
    private JButton btnAgregarSede;
    private JButton btnEditarSede;
    private JList<String> listaSedes;
    private DefaultListModel<String> modeloListaSedes;
    private int cantidadRegistradasSedes = 0;
    
    // Variables de los arbitros
    private JTextField NombreArbitro;
    private JTextField NacionalidadArbitro;
    private JButton btnAgregarArbitro;
    private JButton btnEditarArbitro;
    private JList<String> ListaArbitro;
    private DefaultListModel<String> modeloListaArbitro;
    private int cantidadRegistradosArbitros = 0;

    public Modulo1Admin() {
        setTitle("Copa Mundial: Administración");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);

        
        JTabbedPane pestañas = new JTabbedPane();

        
        pestañas.addTab("1. Países", crearPanelPaises());
        pestañas.addTab("2. Jugadores", crearPanelJugadores());
        pestañas.addTab("3. Sedes/Estadios", crearPanelEstadios());
        pestañas.addTab("4. Arbitros", crearPanelArbitros());

        
        initComponentesConfiguracion();

        add(pestañas, BorderLayout.CENTER);

        setVisible(true);
    }

    private void initComponentesConfiguracion() {
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Cantidad de países participantes:"));

        comboTamano = new JComboBox<>(new Integer[]{24, 32, 48, 64});
        panelSuperior.add(comboTamano);

        btnConfirmarTamano = new JButton("Confirmar Tamaño");
        btnConfirmarTamano.addActionListener(this::onConfirmarTamano);
        panelSuperior.add(btnConfirmarTamano);

        btnGenerarDemo = new JButton("Generar Datos de Demostración");
        btnGenerarDemo.setEnabled(false); 
        btnGenerarDemo.addActionListener(this::onGenerarDemo);
        panelSuperior.add(btnGenerarDemo);
        
        JButton btnIrModulo2 = new JButton("Pasar al sorteo");
        btnIrModulo2.addActionListener(this::onContinuarModulo2);
        panelSuperior.add(btnIrModulo2);

        add(panelSuperior, BorderLayout.NORTH);
    }

    // Registro y modificación de países
    private JPanel crearPanelPaises() {
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Registrar / Editar País"));

        panelForm.add(new JLabel("Nombre del país:"));
        txtNombrePais = new JTextField();
        panelForm.add(txtNombrePais);

        panelForm.add(new JLabel("Director Técnico:"));
        txtDirectorTecnico = new JTextField();
        panelForm.add(txtDirectorTecnico);

        JButton btnAgregarPais = new JButton("Agregar País");
        btnAgregarPais.addActionListener(this::onAgregarPais);
        panelForm.add(btnAgregarPais);

        JButton btnEditarPais = new JButton("Editar País Seleccionado");
        btnEditarPais.addActionListener(this::onEditarPais);
        panelForm.add(btnEditarPais);

        panelCentral.add(panelForm, BorderLayout.NORTH);

        modeloListaPaises = new DefaultListModel<>();
        listaPaises = new JList<>(modeloListaPaises);
        listaPaises.addListSelectionListener(e -> cargarPaisSeleccionadoEnFormulario());
        panelCentral.add(new JScrollPane(listaPaises), BorderLayout.CENTER);

        return panelCentral;
    }

    // Registro y modificación de jugadores
    private JPanel crearPanelJugadores() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Gestión de Jugadores por País"));

        panelForm.add(new JLabel("Seleccionar País:"));
        comboPaisesJugador = new JComboBox<>();
        comboPaisesJugador.addActionListener(e -> actualizarListaJugadores());
        panelForm.add(comboPaisesJugador);

        panelForm.add(new JLabel("Nombre Jugador:"));
        txtNombreJugador = new JTextField();
        panelForm.add(txtNombreJugador);

        panelForm.add(new JLabel("Dorsal (Número):"));
        spinDorsal = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        panelForm.add(spinDorsal);

        panelForm.add(new JLabel("Posición:"));
        comboPosicion = new JComboBox<>(new String[]{"Portero", "Defensa", "Mediocampista", "Delantero"});
        panelForm.add(comboPosicion);

        btnAgregarJugador = new JButton("Agregar Jugador");
        btnAgregarJugador.addActionListener(this::onAgregarJugador);
        panelForm.add(btnAgregarJugador);

        btnEditarJugador = new JButton("Editar Jugador Seleccionado");
        btnEditarJugador.addActionListener(this::onEditarJugador);
        panelForm.add(btnEditarJugador);

        panel.add(panelForm, BorderLayout.NORTH);

        
        modeloListaJugadores = new DefaultListModel<>();
        listaJugadores = new JList<>(modeloListaJugadores);
        listaJugadores.addListSelectionListener(e -> cargarJugadorSeleccionadoEnFormulario());
        panel.add(new JScrollPane(listaJugadores), BorderLayout.CENTER);

        return panel;
    }
    
    // Registro y modificación de arbitros
    private JPanel crearPanelArbitros() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Gestión de árbitros"));

        panelForm.add(new JLabel("Nombre del árbitro:"));
        NombreArbitro = new JTextField();
        panelForm.add(NombreArbitro);

        panelForm.add(new JLabel("Nacionalidad:"));
        NacionalidadArbitro = new JTextField();
        panelForm.add(NacionalidadArbitro);

        btnAgregarArbitro = new JButton("Agregar árbitro");
        btnAgregarArbitro.addActionListener(this::onAgregarArbitro);
        panelForm.add(btnAgregarArbitro);

        btnEditarArbitro = new JButton("Editar arbitro");
        btnEditarArbitro.addActionListener(this::onEditarArbitro);
        panelForm.add(btnEditarArbitro);

        panel.add(panelForm, BorderLayout.NORTH);

        
        modeloListaArbitro = new DefaultListModel<>();
        ListaArbitro = new JList<>(modeloListaArbitro);
        ListaArbitro.addListSelectionListener(e -> arbitroSeleccionadoFormulario());
        panel.add(new JScrollPane(ListaArbitro), BorderLayout.CENTER);

        return panel;
    }
    
    // Registro y modificación de estadios
    private JPanel crearPanelEstadios() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Gestión de sedes/estadios"));

        panelForm.add(new JLabel("Nombre del estadio:"));
        txtNombreEstadio = new JTextField();
        panelForm.add(txtNombreEstadio);

        panelForm.add(new JLabel("Ciudad:"));
        txtCiudadEstadio = new JTextField();
        panelForm.add(txtCiudadEstadio);

        panelForm.add(new JLabel("Capacidad:"));
        capacidadEstadio = new JSpinner(new SpinnerNumberModel(25000, 6000,110000, 1000));
        panelForm.add(capacidadEstadio);

        btnAgregarSede = new JButton("Agregar estadio");
        btnAgregarSede.addActionListener(this::onAgregarSede);
        panelForm.add(btnAgregarSede);

        btnEditarSede = new JButton("Editar estadio");
        btnEditarSede.addActionListener(this::onEditarSede);
        panelForm.add(btnEditarSede);

        panel.add(panelForm, BorderLayout.NORTH);

        
        modeloListaSedes = new DefaultListModel<>();
        listaSedes = new JList<>(modeloListaSedes);
        listaSedes.addListSelectionListener(e -> cargarSedeSeleccionadaEnFormulario());
        panel.add(new JScrollPane(listaSedes), BorderLayout.CENTER);

        return panel;
    }    

    

    private void onConfirmarTamano(ActionEvent e) {
        cantidadEquipos = (Integer) comboTamano.getSelectedItem();

        paises = new Pais[cantidadEquipos];
        sedes = new Sede[cantidadEquipos / 2];   
        arbitros = new Arbitro[cantidadEquipos / 4]; 

        cantidadRegistradosArbitros = 0;
        cantidadRegistradasSedes = 0;
        cantidadRegistrados = 0;
        modeloListaPaises.clear();
        modeloListaSedes.clear();
        modeloListaArbitro.clear();
        comboPaisesJugador.removeAllItems();

        btnGenerarDemo.setEnabled(true);
        comboTamano.setEnabled(false);
        btnConfirmarTamano.setEnabled(false);

        JOptionPane.showMessageDialog(this,
                "Torneo configurado para " + cantidadEquipos + " equipos.\n" +
                "Ya puede registrar países manualmente o generar datos de demostración.");
    }

    private void onAgregarPais(ActionEvent e) {
        if (paises == null) {
            JOptionPane.showMessageDialog(this, "Primero confirme el tamaño del torneo.");
            return;
        }
        if (cantidadRegistrados >= paises.length) {
            JOptionPane.showMessageDialog(this, "Ya se registraron todos los países (" + paises.length + ").");
            return;
        }
        String nombre = txtNombrePais.getText().trim();
        String dt = txtDirectorTecnico.getText().trim();
        if (nombre.isEmpty() || dt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete nombre y director técnico.");
            return;
        }

        Pais nuevo = new Pais(nombre, dt);
        paises[cantidadRegistrados] = nuevo;
        cantidadRegistrados++;

        modeloListaPaises.addElement(nombre + " (DT: " + dt + ")");
        refrescarComboPaisesJugador();

        txtNombrePais.setText("");
        txtDirectorTecnico.setText("");
    }

    private void onEditarPais(ActionEvent e) {
        int indice = listaPaises.getSelectedIndex();
        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un país de la lista para editar.");
            return;
        }
        String nombre = txtNombrePais.getText().trim();
        String dt = txtDirectorTecnico.getText().trim();
        if (nombre.isEmpty() || dt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete nombre y director técnico.");
            return;
        }

        paises[indice].setNombre(nombre);
        paises[indice].setDirectorTecnico(dt);
        modeloListaPaises.set(indice, nombre + " (DT: " + dt + ")");
        
        txtNombrePais.setText("");
        txtDirectorTecnico.setText("");
        listaPaises.clearSelection();
        
        refrescarComboPaisesJugador();
        comboPaisesJugador.repaint();
    }
    
    private void refrescarComboPaisesJugador() {
        Pais seleccionado = (Pais) comboPaisesJugador.getSelectedItem();
        comboPaisesJugador.removeAllItems();
        if (paises != null) {
            for (int i = 0; i < cantidadRegistrados; i++) {
                if (paises[i] != null) {
                    comboPaisesJugador.addItem(paises[i]);
                }
            }
        }
        if (seleccionado != null) {
            comboPaisesJugador.setSelectedItem(seleccionado);
        }
    }    

    private void cargarPaisSeleccionadoEnFormulario() {
        int indice = listaPaises.getSelectedIndex();
        if (indice < 0 || paises == null || paises[indice] == null) return;
        txtNombrePais.setText(paises[indice].getNombre());
        txtDirectorTecnico.setText(paises[indice].getDirectorTecnico());
    }

    

    private void actualizarListaJugadores() {
        modeloListaJugadores.clear();
        Pais seleccionado = (Pais) comboPaisesJugador.getSelectedItem();
        if (seleccionado == null) return;

        Jugador[] plantilla = seleccionado.getPlantilla();
        for (int i = 0; i < seleccionado.getCantidadJugadoresRegistrados(); i++) {
            if (plantilla[i] != null) {
                modeloListaJugadores.addElement(plantilla[i].toString());
            }
        }
    }

    private void onAgregarJugador(ActionEvent e) {
        Pais paisSeleccionado = (Pais) comboPaisesJugador.getSelectedItem();
        if (paisSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un país primero.");
            return;
        }

        String nombre = txtNombreJugador.getText().trim();
        int dorsal = (Integer) spinDorsal.getValue();
        String posicion = (String) comboPosicion.getSelectedItem();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del jugador.");
            return;
        }

        Jugador j = new Jugador(nombre, dorsal, posicion);
        boolean exito = paisSeleccionado.agregarJugador(j);

        if (exito) {
            actualizarListaJugadores();
            txtNombreJugador.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "El país ya tiene la plantilla llena (23 jugadores).");
        }
    }

    private void onEditarJugador(ActionEvent e) {
        Pais paisSeleccionado = (Pais) comboPaisesJugador.getSelectedItem();
        int indiceJugador = listaJugadores.getSelectedIndex();

        if (paisSeleccionado == null || indiceJugador < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un jugador de la lista para editar.");
            return;
        }

        String nombre = txtNombreJugador.getText().trim();
        int dorsal = (Integer) spinDorsal.getValue();
        String posicion = (String) comboPosicion.getSelectedItem();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del jugador.");
            return;
        }

        Jugador j = new Jugador(nombre, dorsal, posicion);
        paisSeleccionado.editarJugador(indiceJugador, j);
        actualizarListaJugadores();
    }

    private void cargarJugadorSeleccionadoEnFormulario() {
        Pais paisSeleccionado = (Pais) comboPaisesJugador.getSelectedItem();
        int indiceJugador = listaJugadores.getSelectedIndex();

        if (paisSeleccionado == null || indiceJugador < 0) return;

        Jugador j = paisSeleccionado.getPlantilla()[indiceJugador];
        if (j != null) {
            txtNombreJugador.setText(j.getNombre());
            spinDorsal.setValue(j.getDorsal());
            comboPosicion.setSelectedItem(j.getPosicion());
        }
    }
    private void onAgregarSede(ActionEvent e) {
        if (sedes == null) {
            JOptionPane.showMessageDialog(this, "Confirme el tamaño del torneo.");
            return;
        }
        if (cantidadRegistradasSedes >= sedes.length) {
            JOptionPane.showMessageDialog(this, "Ya se registraron todas las sedes necesarias (" + sedes.length + ").");
            return;
        }

        String estadio = txtNombreEstadio.getText().trim();
        String ciudad = txtCiudadEstadio.getText().trim();
        int capacidad = (Integer) capacidadEstadio.getValue();

        if (estadio.isEmpty() || ciudad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos de la sede.");
            return;
        }

        // Guardamos la sede en la posición disponible del arreglo
        Sede s = new Sede(estadio, ciudad, capacidad);
        sedes[cantidadRegistradasSedes] = s;
        cantidadRegistradasSedes++;

        // La mostramos en la lista gráfica
        modeloListaSedes.addElement(s.toString() + " | Capacidad: " + capacidad);

        // Limpiamos los campos de texto
        txtNombreEstadio.setText("");
        txtCiudadEstadio.setText("");
    }
    
    private void cargarSedeSeleccionadaEnFormulario() {
        int indice = listaSedes.getSelectedIndex();
        
        // Verificamos que se haya seleccionado un elemento válido
        if (indice < 0 || sedes == null || sedes[indice] == null) return;

        // Leemos del arreglo 'sedes' y colocamos el texto en los componentes
        txtNombreEstadio.setText(sedes[indice].getNombreEstadio());
        txtCiudadEstadio.setText(sedes[indice].getCiudad());
        capacidadEstadio.setValue(sedes[indice].getCapacidad());
    }
    
    private void onEditarSede(ActionEvent e) {
        int indice = listaSedes.getSelectedIndex();

        // Validamos que el usuario realmente haya seleccionado una sede de la lista
        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una sede de la lista para editar.");
            return;
        }

        String estadio = txtNombreEstadio.getText().trim();
        String ciudad = txtCiudadEstadio.getText().trim();
        int capacidad = (Integer) capacidadEstadio.getValue();

        if (estadio.isEmpty() || ciudad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos del estadio.");
            return;
        }

        // Actualizamos los atributos del objeto Sede dentro del arreglo
        sedes[indice].setNombreEstadio(estadio);
        sedes[indice].setCiudad(ciudad);
        sedes[indice].setCapacidad(capacidad);

        // Actualizamos el texto del elemento en la lista gráfica
        modeloListaSedes.set(indice, sedes[indice].toString() + " | Capacidad: " + capacidad);
        
        JOptionPane.showMessageDialog(this, "Sede actualizada con éxito.");
        
        txtNombreEstadio.setText("");
        txtCiudadEstadio.setText("");
        capacidadEstadio.setValue(25000);
        listaSedes.clearSelection();
    }
    
    private void onAgregarArbitro(ActionEvent e){
        if (arbitros == null){
            JOptionPane.showMessageDialog(this, "Seleccione el tamaño del torneo");
            return;
        }
        if (cantidadRegistradosArbitros >= arbitros.length){
            JOptionPane.showMessageDialog(this, "Ya se registraron todos los árbitros");
            return;
        }
        String nombre = NombreArbitro.getText().trim();
        String nacionalidad = NacionalidadArbitro.getText().trim();
        
        if (nombre.isEmpty() || nacionalidad.isEmpty()){
            JOptionPane.showMessageDialog(this, "Complete todos los campos necesarios del árbitro");
            return;
        }
        
        Arbitro a = new Arbitro(nombre, nacionalidad);
        arbitros[cantidadRegistradosArbitros] = a;
        cantidadRegistradosArbitros++;
        
        modeloListaArbitro.addElement(a.toString());
        
        NombreArbitro.setText("");
        NacionalidadArbitro.setText("");
    }
    
    private void arbitroSeleccionadoFormulario(){
        int indice = ListaArbitro.getSelectedIndex();
        if (indice < 0 || arbitros == null || arbitros[indice] == null) 
            return;
        NombreArbitro.setText(arbitros[indice].getNombre());
        NacionalidadArbitro.setText(arbitros[indice].getNacionalidad());
    }
    
    private void onEditarArbitro(ActionEvent e){
        int indice = ListaArbitro.getSelectedIndex();
        if (indice < 0){
            JOptionPane.showMessageDialog(this, "Seleccione el árbitro que desee editar");
            return;
        }
        String nombre = NombreArbitro.getText().trim();
        String nacionalidad = NacionalidadArbitro.getText().trim();
        
        if (nombre.isEmpty() || nacionalidad.isEmpty()){
            JOptionPane.showMessageDialog(this, "Complete todos los campos necesarios");
            return;
        }
        
        arbitros[indice].setNombre(nombre);
        arbitros[indice].setNacionalidad(nacionalidad);
        
        modeloListaArbitro.set(indice, arbitros[indice].toString());
        JOptionPane.showMessageDialog(this, "Árbitro editado correctamente");
        
        NombreArbitro.setText("");
        NacionalidadArbitro.setText("");
        ListaArbitro.clearSelection();
    }

    // Demo

    private void onGenerarDemo(ActionEvent e) {
        if (paises == null) return;
        Random rnd = new Random();

        comboPaisesJugador.removeAllItems();

        for (int i = 0; i < paises.length; i++) {
            Pais p = new Pais("País Demo " + (i + 1), "DT Demo " + (i + 1));
            
            // Generamos 11 jugadores por país para probar bien
            for (int j = 0; j < 11; j++) {
                String pos = (j == 0) ? "Portero" : (j < 5) ? "Defensa" : (j < 9) ? "Mediocampista" : "Delantero";
                p.agregarJugador(new Jugador("Jugador " + (j + 1) + " (" + p.getNombre() + ")", j + 1, pos));
            }
            paises[i] = p;
            comboPaisesJugador.addItem(p);
        }

        for (int i = 0; i < sedes.length; i++) {
            sedes[i] = new Sede("Estadio Demo " + (i + 1), "Ciudad Demo " + (i + 1),
                    30000 + rnd.nextInt(50000));
        }
        modeloListaSedes.clear();
        cantidadRegistradasSedes = sedes.length;
        for (Sede s : sedes){
            modeloListaSedes.addElement(s.toString() + "| Capacidad: " + s.getCapacidad());
        }

        for (int i = 0; i < arbitros.length; i++) {
            arbitros[i] = new Arbitro("Árbitro Demo " + (i + 1), "Nacionalidad Demo");
        }
        modeloListaArbitro.clear();
        cantidadRegistradosArbitros = arbitros.length;
        for (Arbitro a : arbitros){
            modeloListaArbitro.addElement(a.toString());
        }

        modeloListaPaises.clear();
        cantidadRegistrados = paises.length;
        for (Pais p : paises) {
            modeloListaPaises.addElement(p.getNombre() + " (DT: " + p.getDirectorTecnico() + ")");
        }
        
        refrescarComboPaisesJugador();
        actualizarListaJugadores();
        JOptionPane.showMessageDialog(this, "Datos de demostración generados con éxito.");
    }

    public Pais[] getPaises() { return paises; }
    public Sede[] getSedes() { return sedes; }
    public Arbitro[] getArbitros() { return arbitros; }
    
    
    private void onContinuarModulo2(ActionEvent e) {
        if (paises == null) {
            JOptionPane.showMessageDialog(this, "Primero confirme el tamaño del torneo.");
            return;
        }

        if (cantidadRegistrados < paises.length) {
            JOptionPane.showMessageDialog(this, "Debe registrar todos los países (" + 
                    cantidadRegistrados + "/" + paises.length + ") antes de continuar.");
            return;
        }

        if (cantidadRegistradasSedes < sedes.length) {
            JOptionPane.showMessageDialog(this, "Debe registrar todas las sedes necesarias (" + 
                    cantidadRegistradasSedes + "/" + sedes.length + ") antes de continuar.");
            return;
        }

        if (cantidadRegistradosArbitros < arbitros.length) {
            JOptionPane.showMessageDialog(this, "Debe registrar todos los árbitros necesarios (" + 
                    cantidadRegistradosArbitros + "/" + arbitros.length + ") antes de continuar.");
            return;
        }

        for (Pais p : paises) {
            if (p == null || p.getCantidadJugadoresRegistrados() == 0) {
                JOptionPane.showMessageDialog(this, "El país '" + (p != null ? p.getNombre() : "desconocido") + 
                        "' no tiene jugadores registrados en su plantilla.");
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Pasando al sorteo de grupos");

        
        Modulo2 modulo2 = new Modulo2(paises, sedes, arbitros);
        modulo2.setVisible(true);
        this.dispose(); // Cierra el Módulo 1


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Modulo1Admin::new);
        
        
    }
}