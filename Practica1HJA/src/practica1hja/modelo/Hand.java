/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1hja.modelo;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author jcarlos
 */
public class Hand {
    
    private ArrayList<Card> hand;
    
    public Hand(){
        hand = new ArrayList<>();
    }
    
    public Hand(ArrayList<Card> hand){
        this.hand = hand;
    }
    
    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }
    
    public void aniadeCarta(Card c){
        hand.add(c);
    }
    
    public void sort(){
        Collections.sort(hand);
    }
    
        @Override
    public String toString() {
        String cadena = new String();
        for(Card c : this.hand)
            cadena += c.toString();
        return cadena;
    }
}
