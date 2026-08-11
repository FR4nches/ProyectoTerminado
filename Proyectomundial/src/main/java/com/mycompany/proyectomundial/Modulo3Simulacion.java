/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.proyectomundial;
import java.awt.BorderLayout;
import java.awt.Font;
import static java.awt.Font.PLAIN;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author franc
 */
public class Modulo3Simulacion extends javax.swing.JFrame {
    private Pais[][] matrizGrupos;
private Sede[] sedes;
private Arbitro[] arbitros;
private Partido[] calendario;
private int siguientePartido;

private JPanel panelTablas;
private JTextArea areaDetalles;
private JButton btnSimularUno;
private JButton btnSimularTodo;

    /**
     * Creates new form Modulo3Simulacion
     */
    public Modulo3Simulacion(Pais[][] matrizGrupos, Sede[] sedes, Arbitro[] arbitros) {
    initComponents();

    this.matrizGrupos = matrizGrupos;
    this.sedes = sedes;
    this.arbitros = arbitros;
    this.siguientePartido = 0;

    prepararModulo();
}
    private void prepararModulo(){
    setSize(1000, 700);
    setLocationRelativeTo(null);
    
    
    crearCalendario();
    armarComponentesGraficos();
    actualizarTablas();
    
    }
    
    // Metodo de Calendario arma los 6 partidos directos por cada grupo
    private void crearCalendario(){
    if (matrizGrupos == null) return;
    int numGrupos = matrizGrupos.length;
    int totalPartidos = numGrupos * 6;// 6 partidos por cada grupo
    calendario = new Partido[totalPartidos];
    
    int posCalendario = 0;
    int posSede=0;
    int posArbitro=0;
    
        for (int g = 0; g < numGrupos; g++) {
          Pais p1 = matrizGrupos[g][0];
          Pais p2 = matrizGrupos[g][1];
          Pais p3 = matrizGrupos[g][2];
          Pais p4 = matrizGrupos[g][3];
          
          //combinaciones de las 3 jornadas 6 partidos en total)
          
         Pais[] locales = {p1, p3, p1, p2, p4, p2};
         Pais[] visitantes = {p2, p4, p3, p4, p1, p3};
          
        for (int i = 0; i < 6; i++) {
            
            Sede s = null;
            if(sedes !=null && sedes.length > 0){
             s = sedes[posSede % sedes.length];
            }
            
            Arbitro a = null ; 
            if(arbitros != null && arbitros.length> 0){
            a = arbitros[posArbitro % arbitros.length];
            
            }
            
            calendario[posCalendario] = new Partido(locales[i], visitantes[i], s,a);
            
            posCalendario++;
            posSede++;
            posArbitro++;
        }
  
 
        }
   
       
        
    }
   // Metodos para aagregar las cosas visuales sobre el JFrame
    private void armarComponentesGraficos(){
    setLayout(new BorderLayout(15,15));
    
    //parte superior bonotnes de acción
    JPanel panelSuperior = new JPanel();
    panelSuperior.setBorder(BorderFactory.createTitledBorder("Controles de Simulacion"));
    
    btnSimularUno= new JButton("Simular partido a Partido");
    btnSimularUno.addActionListener(new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent e) {
     SimularSiguientePartido();
    }
    });
    
    panelSuperior.add(btnSimularUno);
    
    btnSimularTodo = new JButton("Simular Fase Completa");
    btnSimularTodo.addActionListener(new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent e){
    simularFaseCompleta();
    }
    
    
    });
    panelSuperior.add(btnSimularTodo);
    
    add(panelSuperior,BorderLayout.NORTH);
    
    // Panel Central tablas de los grupos organizadas 
    panelTablas = new JPanel(new GridLayout(0,2,10,10));
    panelTablas.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    JScrollPane scrollTablas= new JScrollPane(panelTablas);
    add(scrollTablas, BorderLayout.CENTER);
   
    //panel derecho se mostraran los goles 
    areaDetalles=new JTextArea(15,25);
    areaDetalles.setEditable(false);
    
    // para que los goles se alineen correctamente
    areaDetalles.setFont(new Font("Monospaced", Font.PLAIN,12));
    JScrollPane scrollDetalles = new JScrollPane(areaDetalles);
    scrollDetalles.setBorder(BorderFactory.createTitledBorder("Resultados"));
    add(scrollDetalles,BorderLayout.EAST);
    
    JButton btnIrModulo5 = new JButton("Pasar a la -Fase Eliminatoria-");

btnIrModulo5.addActionListener(e -> {
    if (calendario == null || siguientePartido < calendario.length) {
        JOptionPane.showMessageDialog(
            this,
            "Debe simular todos los partidos de la Fase de Grupos antes de avanzar a la Fase Eliminatoria.",
            "Aviso",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    Modulo5Eliminatoria modulo5 =
            new Modulo5Eliminatoria(matrizGrupos, sedes, arbitros);

    modulo5.setVisible(true);
    this.dispose();
});

panelSuperior.add(btnIrModulo5);
    
    }
    
    //=====================================================
    //logica del boton 1 simular solo 1 partido pendiente
      //=====================================================
    
    private void SimularSiguientePartido(){
    if (calendario ==  null|| siguientePartido>= calendario.length){
    JOptionPane.showMessageDialog(this,"Todos los partidos ya han sido jugados ");
    return;
    
    }
    // Tomamos el partido los simulamos y le aplicamos los puntos
    Partido p = calendario[siguientePartido];
    p.simularPartido();
    
    imprimirResultadoBitacora(p);
    
    // se actualiza puntos y goles del pais
    actualizarPuntosYGoles(p);
    
    // se avanza el contador para la proxima vez que se presione el boton
    siguientePartido++;
    
    // se refresca la tabla ordenadas
    
    actualizarTablas();
    }

    // logica del boton 2 ejecuta un bucle en el cual simulara de golpe todos los partidos faltantes
    
    private void simularFaseCompleta(){
    if(calendario == null) return;
    
    int simuladosAhora=0;
    
    //recorre desde el partido actual hasta el final del calendario 
    
    while (siguientePartido < calendario.length){
    Partido p = calendario [siguientePartido];
    
    p.simularPartido();
    imprimirResultadoBitacora(p);
    actualizarPuntosYGoles(p);
    simuladosAhora++;
    
    siguientePartido++;
    }
    
    // notificacion al usuario que se ejecuto
    if(simuladosAhora > 0){
    
    areaDetalles.append(">>> FASE COMPLETA SIMULADA EXITOSAMENTE("+ simuladosAhora + "partidos jugados)<<<\n\n");
    actualizarTablas();
    JOptionPane.showMessageDialog(this, "Se completaron todos los partidos restantes de forma automática.");
   
    }else{
    
    JOptionPane.showMessageDialog(this, "La Fase de Grupos ya estaba finalizada.", "aviso", JOptionPane.WARNING_MESSAGE);
    
    }
    }
    
    
    
    
    private void imprimirResultadoBitacora(Partido p){
        
    areaDetalles.append("--- PARTIDO"+(siguientePartido+1)+"DE"+calendario.length+"---\n");
    areaDetalles.append(p.getEquipoLocal().getNombre()+" "+ p.getGolesLocal() +"-"+
    p.getGolesVisitante() + " " + p.getEquipoVisitante().getNombre() + "\n");
   
    if(p.getSede() !=null){
    
    areaDetalles.append("sede:"+ p.getSede().getNombreEstadio()+ "\n" );
    
    }
    
    if(p.getDetalleGoles() !=null && !p.getDetalleGoles().isEmpty()){
    
   areaDetalles.append("goles:\n+"+ p.getDetalleGoles()+"\n" );
    
    } else {
        areaDetalles.append("Goles: Sin anotaciones.\n");
    }         
    
    if(p.getDetalleTarjetas() != null && !p.getDetalleTarjetas().isEmpty ()){
     areaDetalles.append("Tarjetas:" + p.getDetalleTarjetas() + "\n");
        
    }else {
        areaDetalles.append("Tarjetas: Sin amonestaciones.\n");
    }
    
    }
    //============================
    //puntos y ordenamiento
    //=============================
    
    /*
    Asigna goles a favor, en contra y define los puntos (+3vistoria, +1 empate)
    */
    
    private void actualizarPuntosYGoles(Partido p){
    
     Pais local= p.getEquipoLocal();
     Pais visitante = p.getEquipoVisitante();
     
     int gl = p.getGolesLocal();
     int gv = p.getGolesVisitante();
     
     //Actualizar estadisticas de goles
     local.registrarGoles(gl,gv);
     visitante.registrarGoles(gv,gl);
     
     //calculodos y distribucion de puntos  
       if (gl > gv) {
       
       local.sumarVictoria(); //suma +3 puntos
       
       }else if (gv> gl){
            visitante.sumarVictoria(); // suma +3 puntos al visitante
       
       } else{
           // empate 1 punto cada  1 
           
           local.sumarEmpate();// +1 punto
           visitante.sumarEmpate();// + 1 punto al visitante
           
    
    }
       
    }
    
    // se actualizan las tablas con los grupos ordenados 
    
    private void actualizarTablas(){
    
     if (matrizGrupos == null || panelTablas == null) return;
    
     panelTablas.removeAll();
     char letrasGrupo = 'A';
     int numGrupos = matrizGrupos.length;
     
     // recorremos grupo por grupo
     
        for (int g = 0; g < numGrupos; g++) {
            
           JPanel panelG = new JPanel(new BorderLayout()); 
           panelG.setBorder(BorderFactory.createTitledBorder("GRUPO" + (char)(letrasGrupo + g)));
           
           String[] columnas = {"Equipo", "PTS", "GF", "GC","DG"};
           DefaultTableModel model = new DefaultTableModel(columnas, 0){
           @Override
           public boolean isCellEditable(int row, int column){
           return false; 
           
           
           }
           
           
           
           };
            
           Pais[] equiposGrupo = new Pais[4];
           for(int e=0; e<4; e++){
           equiposGrupo[e]= matrizGrupos[g][e];
           
           }
           
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3-i; j++) {
                    if(debeIntercambiar(equiposGrupo[j],equiposGrupo[j  + 1])){
                    Pais temp = equiposGrupo[j];
                    equiposGrupo[j]= equiposGrupo[j+1];
                    equiposGrupo[j+1]=temp;
                
                    }
                }
                
            }
                   
        
        
        
        // se llena el modulo visual con los paises ya ordenados 
        for (int e = 0; e < 4; e++) {
            Pais p = equiposGrupo[e];
            int dg = p.getGolesAFavor()-p.getGolesEnContra();
            model.addRow(new Object[]{p.getNombre(), p.getPuntos(), p.getGolesAFavor(),p.getGolesEnContra(),dg});
            
        }
      JTable tabla = new JTable(model);
      panelG.add(new JScrollPane(tabla),BorderLayout.CENTER);
      panelTablas.add(panelG);
        }
      //actualizamos visualmente el JFrame
      
      panelTablas.revalidate();
      panelTablas.repaint();
    }
    
    
    private boolean debeIntercambiar(Pais p1, Pais p2){
    if (p1.getPuntos()< p2.getPuntos()) return true;
    if (p1.getPuntos()> p2.getPuntos()) return false;
    
    int dg1 = p1.getDiferenciaGoles();
    int dg2 = p2.getDiferenciaGoles();
    
    if(dg1< dg2) return true;
    if(dg1> dg2) return false;
    
    return p1.getGolesAFavor() < p2.getGolesAFavor();
    
    
    
    
    
    }
    
    
    

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
