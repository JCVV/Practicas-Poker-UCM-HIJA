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
public class Card implements Comparable<Card>{
    
    private String representation;//AH, 3C, 1S...
    private Suit suit;//Palo de la carta.
    private String number;//Numero de la carta "2, A, T..."
    private int value;//Valor de la carta "AH -> 1 - 5C -> 5"
    private int rank;
    private String path;
    
    public Card(String number, String suit){
        this.representation=number+suit;
        this.number=number;
        this.path = "cartasPNG/";
        
        switch(number){
            case "A":
                this.value = 1;
                this.rank = 14;
                this.path += "ace_of_";
                break;
            case "T":
                this.value = 10;
                this.rank = 10;
                this.path += "10_of_";
                break;
            case "J":
                this.value = 11;
                this.rank = 11;
                this.path += "jack_of_";
                break;
            case "Q":
                this.value = 12;
                this.rank = 12;
                this.path += "queen_of_";
                break;
            case "K":
                this.value = 13;
                this.rank = 13;
                this.path += "king_of_";
                break;
            default:
                this.value = Integer.parseInt(number);
                this.rank = Integer.parseInt(number);
                this.path += number + "_of_";
        }
        switch(suit){
            case "s":
                this.suit = Suit.SPADES;
                this.path += "spades.png";
                break;
            case "h":
                this.suit = Suit.HEARTS;
                this.path += "hearts.png";
                break;
            case "c":
                this.suit = Suit.CLUBS;
                this.path += "clubs.png";
                break;
            case "d":
                this.suit = Suit.DIAMONDS;
                this.path += "diamonds.png";
                break;
        }
    }
    
    
    public String getPath(){
        return this.path;
    }
    public String getRepresentation() {
        return representation;
    }

    public void setRepresentation(String representation) {
        this.representation = representation;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getValue() {
        return value;
    }

    public int getRank(){
        return this.rank;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    @Override
    public String toString(){
        return this.representation;
    }

    @Override
    public int compareTo(Card t) {
        return this.value - t.value;
    }

}