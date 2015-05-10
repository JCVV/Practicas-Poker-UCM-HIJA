/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1hja;

import GUI.VentanaGrafica;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import practica1hja.controlador.CardsGenerator;
import practica1hja.controlador.Parser1;
import practica1hja.controlador.Parser2;
import practica1hja.controlador.Parser3;
import practica1hja.controlador.Parser4;
import practica1hja.modelo.Card;
import practica1hja.modelo.IteradorCombinacion;

/**
 *
 * @author jcarlos
 */
public class Practica1HJA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Parser1 p1 = new Parser1("apartado1.txt", "outputApartado1.txt");
        //Parser2 p2 = new Parser2("apartado2b.txt", "outputApartado2b.txt");
        //Parser3 p3 = new Parser3("apartado3.txt", "outputApartado3.txt");
  
        
        if(args.length>0){
            switch(args[0]){
                case "1":
                    Parser1 parser1 = new Parser1(args[1],args[2]);
                    break;
                case "2":
                    Parser2 parser2 = new Parser2(args[1],args[2]);
                    break;
                case "3":
                    Parser3 parser3 = new Parser3(args[1],args[2]);
                    break;
                case "4":
                    Parser4 parser4 = new Parser4(args[1],args[2]);
                    break;
                default:
                    System.out.println("Los argumentos no son validos.");
            }
        }
        else{
        VentanaGrafica gui = new VentanaGrafica();
        gui.setVisible(true);
        }
       /* 
       CardsGenerator cg = new CardsGenerator("archivo1.txt", 1000, 5);
       long a = System.currentTimeMillis();
       Parser1 parser = new Parser1("archivo1.txt","salida1.txt");
       long b = System.currentTimeMillis();
       System.out.println(b-a);
      // Parser2 p2 = new Parser2("archivo2.txt", "salida2.txt");
      // Parser3 p3 = new Parser3("entrada3.txt", "salida3.txt");
      // Parser4 p4 = new Parser4("archivo4.txt", "salida4.txt");
    */
    }
    
}
