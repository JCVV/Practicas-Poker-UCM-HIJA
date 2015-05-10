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
public class PairCards {
    private Card c1;
    private Card c2;
    private boolean suited;    
    private boolean equals;

        public PairCards(String s1, String s2){
        if(s1.substring(1,2).equals(s2.substring(1,2)))
            this.equals = true;
        c1 = new Card(s1.substring(0,1),s1.substring(1,2));
        c2 = new Card(s2.substring(0,1),s2.substring(1,2));
        this.equals = true;
           
    }
    
    public PairCards(String s1, String s2, String suited){
        this.equals = false;
        c1 = new Card(s1.substring(0,1),s1.substring(1,2));
        c2 = new Card(s2.substring(0,1),s2.substring(1,2));
        if(c1.getRank()<c2.getRank()){
            Card temp = new Card(s1.substring(0,1),s1.substring(1,2));
            c1 = c2;
            c2 = temp;
        }
            if(suited != null)
                if(suited.equals("o"))
                    this.suited = false;
                else if(suited.equals("s"))
                    this.suited = true;
           
    }

    
    public boolean isEquals() {
        return equals;
    }
    
    public Card getC1() {
        return c1;
    }

    @Override
    public String toString() {
        if(equals)
            return c1.getNumber() + c2.getNumber();
        else if(suited)
            return c1.getNumber() + c2.getNumber() + "s";
        else
            return c1.getNumber() + c2.getNumber() + "o";
    }

    public void setC1(Card c1) {
        this.c1 = c1;
    }

    public Card getC2() {
        return c2;
    }

    public void setC2(Card c2) {
        this.c2 = c2;
    }

    public boolean isSuited() {
        return suited;
    }

    public void setSuited(boolean suited) {
        this.suited = suited;
    }

    
    
}