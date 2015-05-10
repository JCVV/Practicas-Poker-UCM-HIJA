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
        //sort();
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
    
    public final void sort(){
       /*for (int i = this.hand.size() - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (this.hand.get(j).getValue() > this.hand.get(j + 1).getValue()) {
                    Card temp = this.hand.get(j);
                    this.hand.set(j, this.hand.get(j + 1));
                    this.hand.set(j + 1, temp);
                }
            }
        }*/
        Collections.sort(hand);
    }
    
        @Override
    public String toString() {
        String cadena = new String();
        for(Card c : this.hand)
            cadena += c.toString();
        return cadena;
    }

    public boolean disponible() {
        boolean result = true;
        for(Card c : this.getHand()){
            if(!Deck.disponible.get(c.getNum52()))
                result = false;
        }
        return result;
    }
}
