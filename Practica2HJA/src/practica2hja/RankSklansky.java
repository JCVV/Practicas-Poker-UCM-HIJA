/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2hja;

import GUI.Vista1;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author jcarlos
 */
public class RankSklansky {

    private final ArrayList<String> sklansky;
    private final ArrayList<String> sklansky1326;
    private final ArrayList<String> headsUpEquity;
    private final ArrayList<String> headsUpEquity1326;
    private final ArrayList<String> pokerstove;
    private final ArrayList<String> pokerstove1326;
    private final ArrayList<String> sklanskyMalmut;
    private final ArrayList<String> sklanskyMalmut1326;
    private final ArrayList<String> stox;
    private final ArrayList<String> stox1326;
    private final Vista1 view;
//http://www.sickread.com/blog/article/ahk-script-stove-ranges/
    
    public RankSklansky(Vista1 view) {
        this.view = view;
        //Sklansky-Chubukov Ranking:
        String cadenaSklansky = "AA,KK,AKs,QQ,AKo,JJ,AQs,TT,AQo,99,AJs,88,ATs,AJo,77,66,ATo,A9s,55,A8s,KQs,44,A9o,A7s,KJs,A5s,A8o,A6s,A4s,33,KTs,A7o,A3s,KQo,A2s,A5o,A6o,A4o,KJo,QJs,A3o,22,K9s,A2o,KTo,QTs,K8s,K7s,JTs,K9o,K6s,QJo,Q9s,K5s,K8o,K4s,QTo,K7o,K3s,K2s,Q8s,K6o,J9s,K5o,Q9o,JTo,K4o,Q7s,T9s,Q6s,K3o,J8s,Q5s,K2o,Q8o,Q4s,J9o,Q3s,T8s,J7s,Q7o,Q2s,Q6o,98s,Q5o,J8o,T9o,J6s,T7s,J5s,Q4o,J4s,J7o,Q3o,97s,T8o,J3s,T6s,Q2o,J2s,87s,J6o,98o,T7o,96s,J5o,T5s,T4s,86s,J4o,T6o,97o,T3s,76s,95s,J3o,T2s,87o,85s,96o,T5o,J2o,75s,94s,T4o,65s,86o,93s,84s,95o,T3o,76o,92s,74s,54s,T2o,85o,64s,83s,94o,75o,82s,73s,93o,65o,53s,63s,84o,92o,43s,74o,72s,54o,64o,52s,62s,83o,42s,82o,73o,53o,63o,32s,43o,72o,52o,62o,42o,32o";
        //Heads Up Equity Ranking:
        String cadenaHUE =      "AA,KK,QQ,JJ,TT,99,88,AKs,77,AQs,AKo,AJs,ATs,AQo,AJo,66,KQs,ATo,A9s,KJs,A8s,KTs,KQo,A7s,A9o,KJo,55,A5s,A6s,A8o,QJs,K9s,KTo,QTs,A4s,A7o,A3s,K8s,QJo,K9o,A5o,A6o,K7s,Q9s,A2s,QTo,JTs,44,A4o,K6s,K8o,A3o,Q8s,K5s,J9s,Q9o,K7o,JTo,A2o,K4s,K6o,Q7s,K3s,J8s,T9s,33,Q8o,Q6s,K5o,J9o,K2s,Q5s,K4o,J7s,T8s,Q4s,Q7o,T9o,J8o,K3o,Q6o,Q3s,98s,J6s,K2o,T7s,22,Q5o,Q2s,J5s,T8o,J7o,Q4o,J4s,97s,T6s,Q3o,J3s,98o,T7o,J6o,87s,96s,Q2o,J2s,J5o,T5s,T4s,97o,J4o,86s,T6o,95s,T3s,76s,J3o,87o,T2s,96o,85s,J2o,T5o,94s,75s,T4o,86o,93s,65s,95o,84s,T3o,76o,92s,74s,T2o,54s,85o,64s,83s,94o,75o,82s,93o,65o,73s,53s,63s,84o,92o,43s,74o,54o,72s,64o,52s,62s,83o,42s,82o,73o,53o,63o,32s,43o,72o,52o,62o,42o,32o";
        //Stove 4-Way equity Ranking:
        String cadenaStove =    "AA,KK,QQ,JJ,TT,AKs,99,AQs,AKo,AJs,KQs,88,ATs,AQo,KJs,KTs,QJs,AJo,KQo,QTs,A9s,77,ATo,JTs,KJo,A8s,K9s,QJo,A7s,KTo,Q9s,A5s,66,A6s,QTo,J9s,A9o,T9s,A4s,K8s,JTo,K7s,A8o,A3s,Q8s,K9o,A2s,K6s,J8s,T8s,A7o,55,Q9o,98s,K5s,Q7s,J9o,A5o,T9o,A6o,K4s,K8o,Q6s,J7s,T7s,A4o,97s,K3s,87s,Q5s,K7o,44,Q8o,A3o,K2s,J8o,Q4s,T8o,J6s,K6o,A2o,T6s,98o,76s,86s,96s,Q3s,J5s,K5o,Q7o,Q2s,J4s,33,65s,J7o,T7o,K4o,75s,T5s,Q6o,J3s,95s,87o,85s,97o,T4s,K3o,J2s,54s,Q5o,64s,T3s,22,K2o,74s,76o,T2s,Q4o,J6o,84s,94s,86o,T6o,96o,53s,93s,Q3o,J5o,63s,43s,92s,73s,65o,Q2o,J4o,83s,75o,52s,85o,82s,T5o,95o,J3o,62s,54o,42s,T4o,J2o,72s,64o,T3o,32s,74o,84o,T2o,94o,53o,93o,63o,43o,92o,73o,83o,52o,82o,42o,62o,72o,32o";
        //Sklansky-Malmut Groups Ranking:
        String cadenaSkMalmut = "AA,KK,QQ,JJ,AKs,TT,AQs,AKo,AJs,KQs,99,ATs,AQo,KJs,QJs,JTs,88,AJo,KTs,KQo,QTs,J9s,T9s,98s,77,A9s,A8s,A7s,KJo,A5s,A6s,A4s,A3s,QJo,Q9s,A2s,JTo,T8s,97s,87s,76s,65s,66,ATo,55,K9s,KTo,QTo,J8s,86s,75s,54s,K8s,K7s,44,K6s,Q8s,K5s,K4s,K3s,33,J9o,K2s,T9o,T7s,22,98o,64s,53s,43s,A9o,K9o,Q9o,J7s,J8o,T8o,96s,87o,85s,76o,74s,65o,54o,42s,32s,A8o,A7o,A5o,A6o,A4o,K8o,A3o,K7o,A2o,K6o,Q7s,Q8o,Q6s,K5o,Q5s,K4o,Q4s,Q7o,K3o,Q6o,Q3s,J6s,K2o,Q5o,Q2s,J5s,J7o,Q4o,J4s,T6s,Q3o,J3s,T7o,J6o,Q2o,J2s,J5o,T5s,T4s,97o,J4o,T6o,95s,T3s,J3o,T2s,96o,J2o,T5o,94s,T4o,86o,93s,95o,84s,T3o,92s,T2o,85o,83s,94o,75o,82s,93o,73s,63s,84o,92o,74o,72s,64o,52s,62s,83o,82o,73o,53o,63o,43o,72o,52o,62o,42o,32o";
        //STOX Ranking: http://www.sickread.com/blog/article/creating-custom-preflop-ranking/
        String cadenaStox =     "AA,KK,QQ,AKs,AKo,JJ,TT,AQs,AQo,99,AJs,88,ATs,KQs,AJo,77,A9s,ATo,KJs,KQo,A8s,KTs,66,QJs,QTs,JTs,A7s,A9o,55,K9s,T9s,J9s,98s,44,33,A8o,A6s,A5s,A7o,A4s,KJo,K8s,QJo,Q9s,K7s,22,A6o,A3s,A5o,A2s,KTo,K9o,QTo,K6s,K5s,T8s,87s,J8s,76s,97s,A4o,A3o,Q9o,K4s,Q8s,JTo,K3s,Q7s,T9o,J9o,Q6s,98o,K2s,65s,T8o,J7s,86s,75s,T7s,Q5s,A2o,K8o,K7o,K6o,Q4s,K5o,Q8o,K4o,Q3s,Q7o,Q6o,K3o,K2o,J6s,J8o,96s,Q5o,Q2s,J7o,J5s,J4s,T6s,J6o,T5s,T7o,97o,85s,87o,76o,86o,75o,54s,64s,43s,Q4o,53s,65o,Q3o,J5o,54o,J4o,95s,Q2o,64o,T6o,96o,T5o,J3s,T4s,J2s,63s,T3s,32s,42s,52s,74s,T2s,J3o,84s,85o,94s,93s,J2o,92s,83s,73s,82s,72s,62s,95o,T4o,T3o,74o,84o,T2o,94o,53o,93o,63o,92o,73o,83o,43o,42o,82o,72o,62o,52o,32o";

        
        StringTokenizer token1 = new StringTokenizer(cadenaSklansky, ",");
        StringTokenizer token2 = new StringTokenizer(cadenaHUE, ",");
        StringTokenizer token3 = new StringTokenizer(cadenaStove, ",");
        StringTokenizer token4 = new StringTokenizer(cadenaSkMalmut, ",");
        StringTokenizer token5 = new StringTokenizer(cadenaStox, ",");
        
        this.sklansky = new ArrayList<>();
        this.sklansky1326 = new ArrayList<>();
        this.headsUpEquity = new ArrayList<>();
        this.headsUpEquity1326 = new ArrayList<>();
        this.pokerstove = new ArrayList<>();
        this.pokerstove1326 = new ArrayList<>();
        this.sklanskyMalmut = new ArrayList<>();
        this.sklanskyMalmut1326 = new ArrayList<>();
        this.stox = new ArrayList<>();
        this.stox1326 = new ArrayList<>();
        
        while (token1.hasMoreTokens()) {

            String pair1 = token1.nextToken();
            String pair2 = token2.nextToken();
            String pair3 = token3.nextToken();
            String pair4 = token4.nextToken();
            String pair5 = token5.nextToken();
            
            this.sklansky.add(pair1);
            this.headsUpEquity.add(pair2);
            this.pokerstove.add(pair3);
            this.sklanskyMalmut.add(pair4);
            this.stox.add(pair5);

            if (pair1.length() == 2)//Pareja -> * 6
                for (int i = 0; i < 6; i++) 
                    sklansky1326.add(pair1);
            else if (pair1.substring(2, 3).equals("s"))//Suited -> * 4
                for (int i = 0; i < 4; i++) 
                    sklansky1326.add(pair1);
            else
                for (int i = 0; i < 12; i++)//Offsuited -> * 12
                    sklansky1326.add(pair1);
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            
            if (pair2.length() == 2)//Pareja -> * 6
                for (int i = 0; i < 6; i++) 
                    headsUpEquity1326.add(pair2);
            else if (pair2.substring(2, 3).equals("s"))//Suited -> * 4
                for (int i = 0; i < 4; i++) 
                    headsUpEquity1326.add(pair2);
            else
                for (int i = 0; i < 12; i++)//Offsuited -> * 12
                    headsUpEquity1326.add(pair2);
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            
            if (pair3.length() == 2)//Pareja -> * 6
                for (int i = 0; i < 6; i++) 
                    pokerstove1326.add(pair3);
            else if (pair3.substring(2, 3).equals("s"))//Suited -> * 4
                for (int i = 0; i < 4; i++) 
                    pokerstove1326.add(pair3);
            else
                for (int i = 0; i < 12; i++)//Offsuited -> * 12
                    pokerstove1326.add(pair3);
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            
            if (pair4.length() == 2)//Pareja -> * 6
                for (int i = 0; i < 6; i++) 
                    sklanskyMalmut1326.add(pair4);
            else if (pair4.substring(2, 3).equals("s"))//Suited -> * 4
                for (int i = 0; i < 4; i++) 
                    sklanskyMalmut1326.add(pair4);
            else
                for (int i = 0; i < 12; i++)//Offsuited -> * 12
                    sklanskyMalmut1326.add(pair4);
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            
            if (pair5.length() == 2)//Pareja -> * 6
                for (int i = 0; i < 6; i++) 
                    stox1326.add(pair5);
            else if (pair5.substring(2, 3).equals("s"))//Suited -> * 4
                for (int i = 0; i < 4; i++) 
                    stox1326.add(pair5);
            else
                for (int i = 0; i < 12; i++)//Offsuited -> * 12
                    stox1326.add(pair5);
            ////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////
            
        }
        
    }
    
    public void rango(double x, int i){
        marcar(ultimo(porcentaje(x), i), i);
    }

    private int porcentaje(double x) {
        return (int) (x * 1325 / 100);
    }

    private String ultimo(int x, int i) {
        switch(i){
            case 0:
                return sklansky1326.get(x);
            case 1:
                return headsUpEquity1326.get(x);
            case 2:
                return pokerstove1326.get(x);
            case 3:
                return sklanskyMalmut1326.get(x);
            default:
                return stox1326.get(x);
        }
    }

    private void marcar(String ultimo, int rank) {

        ArrayList<String> array;
        switch(rank){
            case 0:
                array = this.sklansky;
                break;
            case 1:
                array = this.headsUpEquity;
                break;
            case 2:
                array = this.pokerstove;
                break;
            case 3:
                array = this.sklanskyMalmut;
                break;
            default:
                array = this.stox;
                break;
        }
        
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
