/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1hja.modelo;

/**
 *
 * @author jcarlos
 */
public enum Suit {
    SPADES("s"), HEARTS("h"), CLUBS("c"), DIAMONDS("d");
    
    private String representacion;
    
    private Suit(String string){
    
        representacion = string;
    
    }
    
    @Override
    public String toString(){
        return representacion;
    }
    
    
    
    
}
