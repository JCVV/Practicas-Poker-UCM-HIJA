/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template entrada, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collections;
import practica1hja.modelo.Card;
import practica1hja.modelo.Hand;
import practica1hja.modelo.HandEvaluator;

/**
 *
 * @author jcarlos
 */
public class GuiParser1 {
    
    private String archivo;
    private VentanaGrafica gui;

    public GuiParser1(String archivo, VentanaGrafica gui) {
        this.archivo = archivo;
        this.gui = gui;
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
            
    
            
            String linea;
            while ((linea = br.readLine()) != null) {
                Hand hand = new Hand();
                for (int i = 0; i < linea.length(); i += 2) {
                    Card card = new Card(linea.substring(i, i + 1),
                            linea.substring(i + 1, i + 2));
                    hand.aniadeCarta(card);
                }
                hand.sort();
                HandEvaluator he = new HandEvaluator(hand.getHand());
                String draws = new String();
                Collections.sort(he.getKicker());
                if(he.getKicker().size()>0 && he.getKicker().get(0).getValue() == 1)
                    he.getKicker().add(he.getKicker().remove(0));
                if(hand.getHand().size()<7)
                    for(String s : he.getDraw()){
                    draws+= "\t-Draw: " + s + "\n";
                    }
                
                pw.print(hand.toString()+"\n\t-Best Hand: "+he.getJugada());
                if(!he.getCartasJugada().isEmpty())
                    pw.print(" with " + he.getCartasJugada().toString());
                if(!he.getKicker().isEmpty()){
                    int kicker = 5 - he.getCartasJugada().size();
                    while(he.getKicker().size()>kicker)
                        he.limpiarKicker();
                    pw.print(" and " + he.getKicker().toString() + " Kicker");
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