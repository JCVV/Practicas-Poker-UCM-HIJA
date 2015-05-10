/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1hja.controlador;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author jcarlos
 */
public class CardsGenerator {
    
    private String archivo;
    private int cantidad;
    private int cantidadMano;
    
    public CardsGenerator(String archivo, int cantidad,int cantidadMano){
        this.archivo = archivo;
        this.cantidad = cantidad;
        this.cantidadMano = cantidadMano;
        
        genera();
    }

    private void genera() {
        Random ran = new Random();
        
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(fichero);
 
            for (int i = 0; i < this.cantidad; i++){
                
                ArrayList<String> cartas = new ArrayList<>();
                int x = 0;
                while(x < this.cantidadMano){
                    int valor = ran.nextInt(13)+1;
                    String valors;
                    switch(valor){
                        case 1:
                            valors = "A";
                            break;
                        case 10:
                            valors = "T";
                            break;
                        case 11:
                            valors = "J";
                            break;
                        case 12:
                            valors = "Q";
                            break;
                        case 13:
                            valors = "K";
                            break;
                        default:
                            valors = Integer.toString(valor);
                    }

                    valor = ran.nextInt(4);
                    switch(valor){
                        case 0:
                            valors += "s";
                            break;
                        case 1:
                            valors += "h";
                            break;
                        case 2:
                            valors += "c";
                            break;
                        case 3:
                            valors += "d";
                            break;
                    }
                    boolean esta = false;
                    for(String s : cartas){
                        if(valors.compareTo(s) == 0)
                            esta = true;
                    }
                    if(!esta){
                    cartas.add(valors);
                    pw.print(valors);
                    x++;
                    }
                }
                pw.println();
                
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        
    }
    
}
