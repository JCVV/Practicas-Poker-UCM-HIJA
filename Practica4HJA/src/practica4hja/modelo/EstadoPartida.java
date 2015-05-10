/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica4hja.modelo;

/**
 *
 * @author jcarlos
 */
public enum EstadoPartida {
    PREFLOP("PREFLOP"), FLOP("FLOP"), TURN("TURN"), RIVER("RIVER"), FINAL("FINAL");
    
    private String cadena;
    
    private EstadoPartida(String cad){
        this.cadena = cad;
    }
    
    public String toString(){
        return this.cadena;
    }
    
    public EstadoPartida next(){
        switch(this){
            case PREFLOP:
                return FLOP;
            case FLOP:
                return TURN;
            case TURN:
                return RIVER;
            case RIVER:
                return FINAL;
            default:
                return PREFLOP;
        }
    }
}
