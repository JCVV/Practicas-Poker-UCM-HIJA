/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica4hja.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author jcarlos
 */
public final class Deck {
    
    private ArrayList<Card> deck;
    private short num_cards;
    private long seed;
    public static HashMap<Integer, Boolean> disponible;
    
    public static final short DECK_SIZE = 51;
    
    public Deck(){
        deck = new ArrayList<>();
        seed = System.nanoTime();
        this.num_cards = DECK_SIZE;
        Deck.disponible = new HashMap<>();
        for (int i = 1; i < 14; i++) {
            String number;
            switch(i){
                case 1:number = "A";
                    break;
                case 10:number = "T";
                    break;
                case 11:number = "J";
                    break;
                case 12:number = "Q";
                    break;
                case 13:number = "K";
                    break;
                default:
                    number = Integer.toString(i);
            }
            
            
            deck.add(new Card(number, Suit.HEARTS.toString()));
            deck.add(new Card(number, Suit.DIAMONDS.toString()));
            deck.add(new Card(number, Suit.CLUBS.toString()));
            deck.add(new Card(number, Suit.SPADES.toString()));
        }
        
        for(int x = 1; x < 53; x++)
                Deck.disponible.put(x, Boolean.TRUE);
        
        shuffle();
    }
    
    public void shuffle(){
        Random rnd = new Random(System.nanoTime());
        Collections.shuffle(deck, rnd);
    }
    
    @Override
    public String toString(){
        return this.deck.toString();
    }
    
    public Card deal(){
        Card c = null;
        if(this.num_cards>0){
            do{
            c = this.deck.get(num_cards);
            this.num_cards--;
            }while(!Deck.disponible.get(c.getNum52()));
            }
        return c;
    }
    
    public void reset(){
        this.num_cards = DECK_SIZE;
        shuffle();
    }

    public void todoTrue() {
        for(int i = 0; i < 52; i++)
            Deck.disponible.put(i, true);
    }
    
}
