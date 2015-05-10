/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2hja;

/**
 *
 * @author jcarlos
 */
public class Card implements Comparable<Card>{
    
    private String representation;//AH, 3C, 1S...
    private String number;//Numero de la carta "2, A, T..."
    private int value;//Valor de la carta "AH -> 1 - 5C -> 5"
    private final int rank;
    private String path;
    
    public Card(String number){
        this.representation=number;
        this.number=number;
        this.path = "cartasPNG/";
        
        switch(number){
            case "A":
                this.value = 0;
                this.rank = 14;
                this.path += "ace_of_";
                break;
            case "T":
                this.value = 4;
                this.rank = 10;
                this.path += "10_of_";
                break;
            case "J":
                this.value = 3;
                this.rank = 11;
                this.path += "jack_of_";
                break;
            case "Q":
                this.value = 2;
                this.rank = 12;
                this.path += "queen_of_";
                break;
            case "K":
                this.value = 1;
                this.rank = 13;
                this.path += "king_of_";
                break;
            default:
                this.value = 14 - Integer.parseInt(number);
                this.rank = Integer.parseInt(number);
                this.path += number + "_of_";
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
    public Card next(){
        switch(this.representation){
            case "2":
                return new Card("3");
            case "3":
                return new Card("4");
            case "4":
                return new Card("5");
            case "5":
                return new Card("6");
            case "6":
                return new Card("7");
            case "7":
                return new Card("8");
            case "8":
                return new Card("9");
            case "9":
                return new Card("T");
            case "T":
                return new Card("J");
            case "J":
                return new Card("Q");
            case "Q":
                return new Card("K");
            case "K":
                return new Card("A");
            default:
                return new Card("A");
        }
    }

    @Override
    public int compareTo(Card t) {
        return this.rank - t.rank;
    }
}
