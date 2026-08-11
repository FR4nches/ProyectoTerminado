/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectomundial;

/**
 *
 * @author franc
 */
public class Partido {
    
    private Pais equipoLocal;
    private Pais equipoVisitante;
    
    private Sede sede;
    private Arbitro arbitro;
    
    private int golesLocal;
    private int golesVisitante;
    private boolean jugado;
    private int asistencia;
    private double recaudacion;
    
    private String detalleGoles="";
    private String detalleTarjetas="";

    public Partido(Pais equipoLocal, Pais equipoVisitante, Sede sede, Arbitro arbitro) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.sede = sede;
        this.arbitro = arbitro;
        this.golesLocal = 0;
        this.golesVisitante = 0;
        this.jugado = false;
        this.asistencia = 0;
        this.recaudacion = 0;
    }
    
    public void setJugando(boolean jugado){
    
    this.jugado = jugado;   
    }

    // este metodo genera los goles aleatoriamente si el partido no se ha jugado
    
public void simularPartido(){
if(!this.jugado){

// genera un numero de 0.0 a 0.99 al multiplicarlo por 5 genera goles de 0 a 4
 
this.golesLocal = (int)(Math.random()*5);
this.golesVisitante = (int)(Math.random() * 5 );
this.jugado = true; // se marca el partido como jugado


StringBuilder sbGoles = new StringBuilder();
if(golesLocal> 0){

sbGoles.append("   -").append(equipoLocal.getNombre()).append(":");
sbGoles.append(generarDetalleGolesPais(equipoLocal, golesLocal)).append("\n");

}
if (golesVisitante>0 ){
    if(golesLocal >0) sbGoles.append("\n");
sbGoles.append("  -").append(equipoVisitante.getNombre()).append(":");
sbGoles.append(generarDetalleGolesPais(equipoVisitante,golesVisitante)).append("\n");

}
if(golesLocal == 0 && golesVisitante == 0){
    this.detalleGoles="  - Sin anotaciones";
} else {

    this.detalleGoles = sbGoles.toString();

}


//Generacion de tarjetas

int amarillasLocal = (int)(Math.random()*3);
int amarillasVisitante = (int)(Math.random() *3);

int rojasLocal = (int)(Math.random() *2 );
int rojasVisitante = (int)(Math.random()*2 );

StringBuilder sbTarjetas =new StringBuilder();

if(amarillasLocal > 0){

sbTarjetas.append(equipoLocal.getNombre()).append(":\n");
sbTarjetas.append(generarDetalleTarjetasPais(equipoLocal,amarillasLocal,"Amarilla"));
sbTarjetas.append("\n\n");

}

if(rojasLocal > 0){
sbTarjetas.append(equipoLocal.getNombre()).append(":\n");
sbTarjetas.append(generarDetalleTarjetasPais(equipoLocal,rojasLocal,"Rojo" ));
sbTarjetas.append("\n\n");

}

if(amarillasVisitante > 0){

sbTarjetas.append(equipoVisitante.getNombre()).append(":\n");
sbTarjetas.append(generarDetalleTarjetasPais(equipoVisitante,amarillasVisitante,"Amarilla"));
sbTarjetas.append("\n\n");

}

if(rojasVisitante > 0){

sbTarjetas.append(equipoVisitante.getNombre()).append(":\n"); 
sbTarjetas.append(generarDetalleTarjetasPais(equipoVisitante, rojasVisitante, "Roja"));
sbTarjetas.append("\n\n");

}

if(sbTarjetas.length()==0){

detalleTarjetas = "Sin amonestaciones";
} else{


detalleTarjetas = sbTarjetas.toString();
}
// Generar asistencia y recaudación
if (sede != null) {
    int asistencia = (int) (Math.random() * (sede.getCapacidad() + 1));
    double precioEntrada = 10 + (Math.random() * 41);
    double recaudacion = asistencia * precioEntrada;

    sede.sumarAsistencia(asistencia);
    sede.sumarRecaudacion(recaudacion);
}

// Registrar partido dirigido por el árbitro
if (arbitro != null) {
    arbitro.sumarPartidoDirigido();
}
}

}
private String generarDetalleGolesPais(Pais pais, int totalGoles){

    Jugador [] plantilla = pais.getPlantilla();
  
    StringBuilder detalle = new StringBuilder();
    
    if (plantilla == null || pais.getCantidadJugadoresRegistrados()==0){
    
    for (int i =0; i < totalGoles; i++){
    
    detalle.append("jugador").append(i+1);
    
    if(i< totalGoles-1){
        detalle.append(", ");
    
    
    }
    
    }
    
    return detalle.toString();
    }



int cantidad = pais.getCantidadJugadoresRegistrados();

    for (int i = 0; i < totalGoles; i++) {
       int indice = (int)(Math.random() * cantidad );
       Jugador jugador = plantilla[indice];
       if(jugador !=null){
       jugador.sumarGol();
       detalle.append(jugador.getNombre());
       
       } else{
       
       detalle.append("jugador");
       }
        
       if (i < totalGoles - 1){
       detalle.append(".");
       }
       
    }

return detalle.toString();
}


private String generarDetalleTarjetasPais(Pais pais, int cantidadTarjetas,String tipo){

Jugador [] plantilla = pais.getPlantilla();
StringBuilder detalle = new StringBuilder();

if(plantilla == null || pais.getCantidadJugadoresRegistrados()==0){
return"";
}

int cantidadJugadores = pais.getCantidadJugadoresRegistrados();

    for (int i = 0; i < cantidadTarjetas; i++) {
        int indice = (int)(Math.random() * cantidadJugadores);
        Jugador jugador = plantilla[indice];
        
        if(jugador != null){
        
        if(tipo.equals("Amarilla")){
            jugador.sumarAmarilla();
        } else {
        
            jugador.sumarRoja();
        }
        
        detalle.append(jugador.getNombre())
                .append("-")
                .append(tipo);
        
        
        if(i< cantidadTarjetas -1 ){
        
            detalle.append("\n");
        
             }
        
        
        }
        
    }


return detalle.toString();
}


//otorga los detalles de los goles locales y visitante

    public Pais getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Pais equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Pais getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Pais equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Arbitro getArbitro() {
        return arbitro;
    }

    public void setArbitro(Arbitro arbitro) {
        this.arbitro = arbitro;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    public boolean isJugado() {
        return jugado;
    }

    public void setJugado(boolean jugado) {
        this.jugado = jugado;
    }

    public String getDetalleGoles() {
        return detalleGoles;
    }

    public void setDetalleGoles(String detalleGoles) {
        this.detalleGoles = detalleGoles;
    }

    public String getDetalleTarjetas() {
        return detalleTarjetas;
    }

    public void setDetalleTarjetas(String detalleTarjetas) {
        this.detalleTarjetas = detalleTarjetas;
    }

    public int getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(int asistencia) {
        this.asistencia = asistencia;
    }

    public double getRecaudacion() {
        return recaudacion;
    }

    public void setRecaudacion(double recaudacion) {
        this.recaudacion = recaudacion;
    }
    



}


