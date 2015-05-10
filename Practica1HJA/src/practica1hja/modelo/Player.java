/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1hja.modelo;

import java.util.ArrayList;

/**
 *
 * @author jcarlos
 */
public class Player {
    
    private String name;
    private Hand hand;
    private HandEvaluator he;
    
    public Player(String name, ArrayList<Card> hand){
        this.name = name;
        this.hand = new Hand(hand);
        this.he = new HandEvaluator(this.hand.getHand());
    }
    
    @Override
    public String toString(){
        return this.name + ": " + this.he.getCartasJugada() + " -> " + 
                this.he.getJugada();
    }


}
