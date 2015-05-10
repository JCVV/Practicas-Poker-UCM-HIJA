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
import practica1hja.modelo.Card;
import practica1hja.modelo.Hand;
import practica1hja.modelo.HandEvaluator;

/**
 *
 * @author jcarlos
 */
public class Parser3 {
    
    private String archivo;
    private String salida;

    public Parser3(String archivo, String salida) {
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
    
            ArrayList<Hand> hands = new ArrayList<>();
            ArrayList<HandEvaluator> hes= new ArrayList<>();
            ArrayList<String> ids = new ArrayList<>();   
                int nPlayers = Character.getNumericValue(linea.charAt(0));
                int posicion = 2;
                for(int i = 0; i < nPlayers; i++){
                    Hand hand = new Hand();
                    String id = linea.substring(posicion,posicion + 2);
                    posicion += 2;
                    for (int x = 0; x < 2; x++){
                        Card card = new Card(linea.substring(posicion, posicion + 1).toUpperCase(),
                                linea.substring(posicion + 1, posicion + 2));
                        hand.aniadeCarta(card);
                        posicion += 2;
                    }
                    hands.add(hand);
                    ids.add(id);
                    posicion ++;
                }
                for(int i = 0; i < 5; i++){
                    Card card = new Card(linea.substring(posicion, posicion + 1).toUpperCase(),
                                linea.substring(posicion + 1, posicion + 2));
                    for(Hand h : hands)
                        h.aniadeCarta(card);
                    posicion += 2;
                    
                }
                int idPos = 0;
                for(Hand h : hands){
                    h.sort();
                    hes.add(new HandEvaluator(h.getHand(),ids.get(idPos) ));
                    idPos++;
                }
                
                Collections.sort(hes);
                
                pw.println(linea);
                String orden = new String();
                for(int i = hes.size(); i > 0; i--)
                    orden = orden + hes.get(i-1).toString() + "\n";
                
                pw.println(orden);
                //System.out.println(hes.toString());
                
                        
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
