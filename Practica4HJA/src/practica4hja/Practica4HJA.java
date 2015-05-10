/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica4hja;

import java.util.ArrayList;
import practica4hja.gui.VistaMesa;
import practica4hja.modelo.Card;
import practica4hja.modelo.HandEvaluator;

/**
 *
 * @author jcarlos
 */
public class Practica4HJA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ArrayList<Card> a1 = new ArrayList<>();
        ArrayList<Card> a2 = new ArrayList<>();
        
        ArrayList<Card> board = new ArrayList<>();
        
        board.add(new Card("T","d"));
        board.add(new Card("5","c"));
        board.add(new Card("2","s"));
        board.add(new Card("8","s"));
        board.add(new Card("T","s"));
        
        a1.add(new Card("K","h"));
        a1.add(new Card("A","s"));
        
        
        a2.add(new Card("Q","c"));
        a2.add(new Card("7","s"));
        
        a1.addAll(board);
        a2.addAll(board);
        
        HandEvaluator h1 = new HandEvaluator(a1);
        HandEvaluator h2 = new HandEvaluator(a2);
        
        int i = h1.compareTo(h2);
// TODO code application logic here
        VistaMesa vm = new VistaMesa();
        vm.setVisible(true);
    }
    
}
