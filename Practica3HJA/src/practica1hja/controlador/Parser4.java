/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1hja.controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import practica1hja.modelo.Card;
import practica1hja.modelo.Hand;
import practica1hja.modelo.HandEvaluator;
import practica1hja.modelo.IteradorCombinacion;

/**
 *
 * @author jcarlos
 */
public class Parser4 {
    
    private String archivo;
    private String salida;

    public Parser4(String archivo, String salida) {
        this.archivo = archivo;
        this.salida = salida;

        leer();
    }

    private void leer() {
        File entrada = null;
        FileReader fr = null;
        BufferedReader br = null;
        
        FileWriter fileWriterSalida = null;
        PrintWriter pw = null;
        try {
            entrada = new File(this.archivo);
            fr = new FileReader(entrada);
            br = new BufferedReader(fr);

            fileWriterSalida = new FileWriter(this.salida);
            pw = new PrintWriter(fileWriterSalida);
            
            String linea;

            while ((linea = br.readLine()) != null) {
    
                int nBoard = Character.getNumericValue(linea.charAt(9));
                int posicion = 0; 
                Hand handPlayer = new Hand();
                for(int i = 0; i < 4; i++){
                    Card card = new Card(linea.substring(posicion, posicion + 1).toUpperCase(),
                                linea.substring(posicion + 1, posicion + 2));
                        handPlayer.aniadeCarta(card);
                        posicion += 2;
                }
                posicion = 11;
                Hand Board = new Hand();
                for (int i = 0; i < nBoard; i++) {
                    Card card = new Card(linea.substring(posicion, posicion + 1).toUpperCase(),
                            linea.substring(posicion + 1, posicion + 2));
                    Board.aniadeCarta(card);
                    posicion += 2;

                }

                IteradorCombinacion it = new IteradorCombinacion(handPlayer.getHand(), 2);
                Iterator sPlayer = it.iterator();
                ArrayList PlayerCombinations = new ArrayList();

                while (sPlayer.hasNext()) {

                    List<Object> listaresPlayer = (List<Object>) sPlayer.next();
                    PlayerCombinations.add(listaresPlayer);

                }
                
                IteradorCombinacion itBoard = new IteradorCombinacion(Board.getHand(), 3);
                Iterator sBoard = itBoard.iterator();
                ArrayList BoardCombinations = new ArrayList();

                while (sBoard.hasNext()) {

                    List<Object> listaresBoard = (List<Object>) sBoard.next();
                    BoardCombinations.add(listaresBoard);

                }

                HandEvaluator mejorOmaha = null;
                
                for (Object PlayerCombination : PlayerCombinations) {
                    Hand actualPlayer = new Hand();
                    List<Card> dosPlayer = (LinkedList) PlayerCombination;
                    for(Card c : dosPlayer)
                        actualPlayer.aniadeCarta(c);
                    for (Object BoardCombination : BoardCombinations) {
                        List<Card> tresBoard = (LinkedList) BoardCombination;
                        Hand h = new Hand();
                        for(Card c : actualPlayer.getHand())
                            h.aniadeCarta(c);
                        
                        for(Card c : tresBoard)
                            h.aniadeCarta(c);
                        h.sort();
                        HandEvaluator hes = new HandEvaluator(h.getHand());
                        if(mejorOmaha!=null){
                            if(hes.compareTo(mejorOmaha)>0)
                                mejorOmaha = hes;
                        }else
                            mejorOmaha = hes;
                    }
                }
                
                
                
                String draws = new String();
         
                Collections.sort(mejorOmaha.getKicker());
                if (mejorOmaha.getKicker().size() > 0 && mejorOmaha.getKicker().get(0).getValue() == 1) {
                    mejorOmaha.getKicker().add(mejorOmaha.getKicker().remove(0));
                }
                
                    for(String s : mejorOmaha.getDraw()){
                    draws+= "\t-Draw: " + s + "\n";
                    }
                
                pw.print(linea +"\n\t-Best Hand: "+mejorOmaha.getJugada());
                if(!mejorOmaha.getCartasJugada().isEmpty())
                    pw.print(" with " + mejorOmaha.getCartasJugada().toString());
                if(!mejorOmaha.getKicker().isEmpty()){
                    int kicker = 5 - mejorOmaha.getCartasJugada().size();
                    while(mejorOmaha.getKicker().size()>kicker)
                        mejorOmaha.limpiarKicker();
                    pw.print(" and " + mejorOmaha.getKicker().toString() + " Kicker");
                }
                pw.println("\n" + draws);
                        
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
                try{ 
                    if (null != fileWriterSalida) 
                        fileWriterSalida.close();
                    if (null != fr) 
                        fr.close();
                    
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
    }

}
