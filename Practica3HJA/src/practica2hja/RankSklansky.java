/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2hja;

import GUI.Vista1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author jcarlos
 */
public class RankSklansky {

    private final ArrayList<ArrayList<String>> rankings;
    private final ArrayList<ArrayList<String>> rankings1326;
    private final ArrayList<String> names;
    private final Vista1 view;
//http://www.sickread.com/blog/article/ahk-script-stove-ranges/

    public RankSklansky(Vista1 view) throws FileNotFoundException{
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        this.names = new ArrayList<>();
        this.rankings = new ArrayList<>();
        this.rankings1326 = new ArrayList<>();
        
        this.view = view;
        try {
         // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("rankings.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            int cont = 0;
            while ((linea = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(linea, " ");
                while(st.hasMoreElements()){
                    String name = st.nextToken();
                    this.names.add(name);
                    String ranking = st.nextToken();
                    StringTokenizer token = new StringTokenizer(ranking, ",");
                    ArrayList<String> vacio = new ArrayList<>();
                    this.rankings.add(vacio);
                    ArrayList<String> vacio1326 = new ArrayList<>();
                    this.rankings1326.add(vacio1326);
                    while(token.hasMoreTokens()){
                        String pair = token.nextToken();
                        this.rankings.get(cont).add(pair);
                        

                        if (pair.length() == 2)//Pareja -> * 6
                        {
                            for (int i = 0; i < 6; i++) {
                                this.rankings1326.get(cont).add(pair);
                            }
                        } else if (pair.substring(2, 3).equals("s"))//Suited -> * 4
                        {
                            for (int i = 0; i < 4; i++) {
                                this.rankings1326.get(cont).add(pair);
                            }
                        } else {
                            for (int i = 0; i < 12; i++)//Offsuited -> * 12
                            {
                                this.rankings1326.get(cont).add(pair);
                            }
                        }
                    }

                }
                cont++;
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
         // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
            }
        }

    }
    
    public void rango(double x, int i){
        marcar(ultimo(porcentaje(x), i), i);
    }
    
    public ArrayList<String> rangoPolarizado(double x, int i){
        return arrayRango(ultimo(porcentaje(x), i), i);
    }

    private int porcentaje(double x) {
        return (int) (x * 1325 / 100);
    }

    public void setModel(){
        this.view.setModelRankings(this.names);
    }
    
    private String ultimo(int x, int i) {
        
        return this.rankings1326.get(i).get(x);
    }

    private ArrayList<String> arrayRango(String ultimo, int rank) {

        ArrayList<String> array;
       
        array = this.rankings.get(rank);
        
       // this.view.deseleccionar();
        int i = 0;
        String actual = array.get(0);
        ArrayList<String> seleccionados;
        seleccionados = new ArrayList<>();


        while (!actual.equals(ultimo)) {
            actual = array.get(i);
            
            seleccionados.add(actual);
            
            i++;
        }
        return seleccionados;
    }
    
    private void marcar(String ultimo, int rank) {

        ArrayList<String> array;
      
        array = this.rankings.get(rank);
        
        this.view.deseleccionar();
        int i = 0;
        String actual = array.get(0);

        while (!actual.equals(ultimo)) {
            
            actual = array.get(i);
            
            if(actual.length()==2)
                view.selectPair(new PairCards(actual.substring(0, 1), actual.substring(1, 2)));
            else
                view.selectPair(new PairCards(actual.substring(0, 1), actual.substring(1, 2), actual.substring(2, 3)));
            i++;
            

        }

    }
}
