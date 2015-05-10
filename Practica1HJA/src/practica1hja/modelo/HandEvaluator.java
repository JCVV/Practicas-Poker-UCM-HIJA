/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1hja.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author jcarlos Siguiendo el diagrama de estados de
 * "http://www.makinolo.com/2006/12/evaluacion-de-manos/"
 */
public class HandEvaluator implements Comparable<HandEvaluator>{

    private ArrayList<Card> cartas;//Cartas mano + mesa
    private String jugada;//Poker, full, pair...
    private ArrayList<String> Draw;//Posibles jugadas
    private ArrayList<Card> cartasJugada;//Poker -> 4 ases
    private ArrayList<Card> kicker;//
    private int rank;
    private String idPlayer;
    private String salida;
    
    public HandEvaluator(ArrayList<Card> cartas) {
        this.cartas = cartas;
        this.jugada = new String();
        this.Draw = new ArrayList<>();
        this.cartasJugada = new ArrayList<>();
        this.kicker = new ArrayList<>();
        this.rank = 0;
        this.idPlayer = new String();
        
        analizar();
        setRank();
    }
    
    public HandEvaluator(ArrayList<Card> cartas, String id) {
        this.cartas = cartas;
        this.jugada = new String();
        this.Draw = new ArrayList<>();
        this.cartasJugada = new ArrayList<>();
        this.kicker = new ArrayList<>();
        this.rank = 0;
        this.idPlayer = id;
        
        analizar();
        setRank();
    }
    
    public String getId(){
        return this.idPlayer;
    }
            
    public ArrayList<Card> getCartas() {
        return cartas;
    }

    public void setCartas(ArrayList<Card> cartas) {
        this.cartas = cartas;
    }

    public String getJugada() {
        return jugada;
    }

    public void setJugada(String jugada) {
        this.jugada = jugada;
    }

    public ArrayList<String> getDraw() {
        return Draw;
    }

    public void setDraw(ArrayList<String> Draw) {
        this.Draw = Draw;
    }

    private void analizar() {

        if (mismoPalo())//seccion con 5 cartas del mismo palo.
            if (escalera(this.cartasJugada)){ 
                if (esReal(this.cartasJugada)) 
                    this.jugada = "RoyalFlush";
                else{
                    this.jugada = "StraightFlush";
                    if(this.cartasJugada.get(0).getValue()==1)
                        this.cartasJugada.add(this.cartasJugada.remove(0));
                }
            }
            else{
                this.jugada = "Flush";
                if(this.cartasJugada.get(0).getValue()==1)
                    this.cartasJugada.add(this.cartasJugada.remove(0));
            }
        else
            if (escalera())
                this.jugada = "Straight";
            else 
                automataPares();
        
        if(this.jugada.equals("Full-House")){
            if(this.cartasJugada.get(4).getValue() != 
                    this.cartasJugada.get(2).getValue()){
                for(int i = 0; i < 3; i++)
                    this.cartasJugada.add(this.cartasJugada.remove(0));
            }
        }
    }

    private boolean mismoPalo() {
        int spades, clubs, hearts, diamonds;
        spades = 0;
        clubs = 0;
        hearts = 0;
        diamonds = 0;
        ArrayList<Card> cartasSpades = new ArrayList<>();
        ArrayList<Card> cartasHearts = new ArrayList<>();
        ArrayList<Card> cartasDiamonds = new ArrayList<>();
        ArrayList<Card> cartasClubs = new ArrayList<>();
        for (Card c : this.cartas) {
            switch (c.getSuit()) {
                case SPADES:
                    cartasSpades.add(c);
                    spades++;
                    break;

                case HEARTS:
                    cartasHearts.add(c);
                    hearts++;
                    break;

                case CLUBS:
                    cartasClubs.add(c);
                    clubs++;
                    break;

                case DIAMONDS:
                    cartasDiamonds.add(c);
                    diamonds++;
                    break;
            }

        }
        if(spades == 4 || hearts == 4 || clubs == 4 || diamonds == 4){
            this.Draw.add("Flush");
            return false;
        }else if(spades > 4)
            this.cartasJugada = cartasSpades;
        else if(hearts > 4)
            this.cartasJugada = cartasHearts;
        else if(clubs > 4)
            this.cartasJugada = cartasClubs;
        else if(diamonds > 4)
            this.cartasJugada = cartasDiamonds;
        
        return (spades > 4) || (hearts > 4) || (clubs > 4) || (diamonds > 4);
    }
    
    private boolean escalera(ArrayList<Card> cartasJugada, boolean color) {
    
        Card c = cartasJugada.get(0);
        boolean gutshotPrimero = false;
        if (c.getValue() == 1) {//Si hay un as, buscamos la secuencia TJQK
            boolean diez = false, jack = false, queen = false, king = false;
            short bajas = 0;
            short altas = 0;
            for (Card card : cartasJugada) {
                switch (card.getValue()) {
                    case 10:
                        if(!diez){
                        altas++;
                        diez = true;}
                        break;
                    case 11:
                        if(!jack){
                        altas++;
                        jack = true;}
                        break;
                    case 12:
                        if(!queen){
                        altas++;
                        queen = true;}
                        break;
                    case 13:
                        if(!king){
                        altas++;
                        king = true;}
                        break;
                    default:
                        break;
                }
            }
           
            if(altas == 3 && !diez)
                if(color)
                    this.Draw.add("Straight Flush Open-Ended");
                else
                    this.Draw.add("Straight Open-Ended");
            else if(altas == 3 && diez)
                if(color){
                    this.Draw.add("Straight Flush GutShot");
                    gutshotPrimero = true;
                } else{
                    this.Draw.add("Straight GutShot");
                    gutshotPrimero = true;
                }
            
            if ((diez && jack && queen && king)) {
                return true;
            }

        }
        int serie = 0;
        int ultimoValor = cartasJugada.get(0).getValue()-1;
        boolean escalera = false;
        boolean openEnded = false;
        int iteraciones = 0;
        int ultimaSerie = 0;
        boolean gutshot = false;
        boolean posibleGutshot = false;
        int serieGutshot = 0;
        for (Card actual : cartasJugada) {
            
            if (actual.getValue() == ultimoValor + 1) {
                serie++;
                if(posibleGutshot){
                    serieGutshot++;
                    if(serieGutshot == 5)
                        gutshot=true;
                }
                if (serie == 4) {
                    escalera = true;
                    serie--;
                    ultimaSerie = iteraciones;
                }else if(serie == 4)
                    openEnded = true;
                ultimoValor = actual.getValue();
            }else if(actual.getValue() == ultimoValor + 2){
                posibleGutshot = true;
                serieGutshot = serie;
            }else{
                serieGutshot = 1;
                posibleGutshot = false;
            }
            if (actual.getValue() != ultimoValor){
                serie = 1;
                ultimoValor = actual.getValue();
            }
            iteraciones ++;
        }
        if(escalera){
            int cantidad = this.cartasJugada.size();
            for(int i = 0; i < cantidad - 5; i++){
                int x = ultimaSerie-5;
                if(x<0)
                    x=cantidad-i-1;
                this.cartasJugada.remove(x);
            }
        }else if(color){//Todas las cartas del mismo palo, Flush con las mayores cartas.
            if(this.cartasJugada.get(0).getValue()==1)
                for(int i = this.cartasJugada.size(); i > 5; i--)
                    this.cartasJugada.remove(i - 5);
            else
                for(int i = 0; i < (this.cartasJugada.size() - 5); i++)
                    this.cartasJugada.remove(i);
        }
        if(gutshot && !gutshotPrimero)
                if(color)
                    this.Draw.add("Straight Flush GutShot");
                else
                    this.Draw.add("Straight GutShot");
        if(!escalera && openEnded)
                if(color)
                    this.Draw.add("Straight Flush Open-Ended");
                else
                    this.Draw.add("Straight Open-Ended");
        return escalera;

        
    }
    
    private boolean escalera() {

        Card c = this.cartas.get(0);
        boolean gutshotPrimero = false;
        if (c.getValue() == 1) {//Si hay un as, buscamos la secuencia 2345
                                                                    // ó
                                                                    //TJQK
            boolean diez = false, jack = false, queen = false, king = false;
            short bajas = 0;
            short altas = 0;
            for (Card card : this.cartas) {
                switch (card.getValue()) {
                    case 10:
                        if(!diez){
                        altas++;
                        diez = true;}
                        break;
                    case 11:
                        if(!jack){
                        altas++;
                        jack = true;}
                        break;
                    case 12:
                        if(!queen){
                        altas++;
                        queen = true;}
                        break;
                    case 13:
                        if(!king){
                        altas++;
                        king = true;}
                        break;
                    default:
                        break;
                }
            }
            
            if(altas == 3 && !diez)
                this.Draw.add("Straight Open-Ended");
            else if(altas == 3 && diez){
                this.Draw.add("Straight Gutshot");
                gutshotPrimero = true;
            }
            if ((diez && jack && queen && king)) {
                this.cartasJugada.add(this.cartas.get(0));
                int i = 0;
                int indice = 1;
                while(i<4){
                    if(this.cartas.get(indice).getValue() == 10 + i){
                    this.cartasJugada.add(
                            this.cartas.get(indice));
                    indice++;
                    i++;                    
                    }else{
                        indice++;
                    }
                }
                this.cartasJugada.add(this.cartasJugada.remove(0));
                return true;
            }

        }
        int serie = 0;
        int ultimoValor = this.cartas.get(0).getValue()-1;
        boolean escalera = false;
        boolean openEnded = false;
        int iteraciones = 0;
        int ultimaSerie = 0;
        boolean gutshot = false;
        boolean posibleGutshot = false;
        int serieGutshot = 0;
 
        for (Card actual : this.cartas) {
            if (actual.getValue() == ultimoValor + 1) {
                serie++;
                if(posibleGutshot){
                    serieGutshot++;
                    if(serieGutshot == 4)
                        gutshot=true;
                }
                if (serie == 5) {
                    escalera = true;
                    serie--;
                    ultimaSerie = iteraciones;
                }else if(serie == 4)
                    openEnded = true;
                ultimoValor = actual.getValue();
            }else if(actual.getValue() == ultimoValor + 2){
                posibleGutshot = true;
                serieGutshot = serie + 1;
                if(serieGutshot == 4)
                    gutshot=true;
            }else{
                if(actual.getValue() != ultimoValor){
                    serieGutshot = 1;
                    posibleGutshot = false;
                }
            }
            if (actual.getValue() != ultimoValor) {
                serie = 1;
                ultimoValor = actual.getValue();
            }
            iteraciones ++;
        }
        if(escalera){
            int i = 1;
            int indice = ultimaSerie-1;
            this.cartasJugada.add(this.cartas.get(ultimaSerie));
            int ultimaJugadaValue = this.cartas.get(ultimaSerie).getValue();
            while (i < 5) {
                if (this.cartas.get(indice).getValue() == ultimaJugadaValue - i) {
                    this.cartasJugada.add(
                            this.cartas.get(indice));
                    indice--;
                    i++;
                } else {
                    indice--;
                }
            }
            Collections.reverse(this.cartasJugada);
        }
        if(!escalera)
            if(gutshot && !gutshotPrimero)
                this.Draw.add("Straight Gutshot");
        if (!escalera && openEnded)
            this.Draw.add("Straight Open-Ended");
        return escalera;

    }
    
    private boolean escalera(ArrayList<Card> cartasJugada) {
    
        Card c = cartasJugada.get(0);
        if (c.getValue() == 1) {//Si hay un as, buscamos la secuencia TJQK
            boolean diez = false, jack = false, queen = false, king = false;
            short bajas = 0;
            short altas = 0;
            for (Card card : cartasJugada) {
                switch (card.getValue()) {
                    case 10:
                        if(!diez){
                        altas++;
                        diez = true;}
                        break;
                    case 11:
                        if(!jack){
                        altas++;
                        jack = true;}
                        break;
                    case 12:
                        if(!queen){
                        altas++;
                        queen = true;}
                        break;
                    case 13:
                        if(!king){
                        altas++;
                        king = true;}
                        break;
                    default:
                        break;
                }
            }
           
            if(altas == 3 && !diez)
                this.Draw.add("Straight Flush Open-Ended");
            else if(altas == 3 && diez)
                this.Draw.add("Straight Flush Gutshot");
            
            if ((diez && jack && queen && king)) {
                return true;
            }

        }
        int serie = 0;
        int ultimoValor = cartasJugada.get(0).getValue()-1;
        boolean escalera = false;
        boolean openEnded = false;
        int iteraciones = 0;
        int ultimaSerie = 0;
        boolean gutshot = false;
        boolean posibleGutshot = false;
        int serieGutshot = 0;
        for (Card actual : cartasJugada) {
            
            if (actual.getValue() == ultimoValor + 1) {
                serie++;
                if(posibleGutshot){
                    serieGutshot++;
                    if(serieGutshot == 4)
                        gutshot=true;
                }
                if (serie == 5) {
                    escalera = true;
                    serie--;
                    ultimaSerie = iteraciones;
                }else if(serie == 4)
                    openEnded = true;
                ultimoValor = actual.getValue();
            }else if(actual.getValue() == ultimoValor + 2){
                posibleGutshot = true;
                serieGutshot = serie+1;
                if(serieGutshot == 4)
                    gutshot=true;
            }else{
                if(actual.getValue() != ultimoValor){
                    serieGutshot = 1;
                    posibleGutshot = false;
                }
            }
            if (actual.getValue() != ultimoValor){
                serie = 1;
                ultimoValor = actual.getValue();
            }
            iteraciones ++;
        }
        if(escalera){
            int cantidad = this.cartasJugada.size();
            for(int i = 0; i < cantidad - 5; i++){
                int x = ultimaSerie-5;
                if(x<0)
                    x=cantidad-i-1;
                this.cartasJugada.remove(x);
            }
        }else{//Todas las cartas del mismo palo, Flush con las mayores cartas.
            if(this.cartasJugada.get(0).getValue()==1)
                for(int i = this.cartasJugada.size(); i > 5; i--)
                    this.cartasJugada.remove(i - 5);
            else
                for(int i = 0; i < (this.cartasJugada.size() - 5); i++)
                    this.cartasJugada.remove(i);
        }
        if(gutshot)
            this.Draw.add("Straight Flush Gutshot");
        if(!escalera && openEnded)
            this.Draw.add("Straight Flush Open-Ended");
        return escalera;

        
    }
    
    private int boolToInt(boolean b){
        return b ? 1 : 0;
    }

    private boolean esReal() {
        boolean j = false, q = false, k = false, a = false;
        for (Card c : this.cartas) {
            switch (c.getValue()) {
                case 11:
                    j = true;
                    break;
                case 12:
                    q = true;
                    break;
                case 13:
                    k = true;
                    break;
                case 1:
                    a = true;
                    break;
            }
        }
        return j && q && k && a;
    }

    private void automataPares() {
        Iterator<Card> it = this.cartas.iterator();
        Card c1 = it.next();
        int estado = 1;
        int estadoAux = 0;
        while (it.hasNext()) {
            Card c2 = it.next();
            if (c1.getValue() == c2.getValue()) {
                switch (estado) {//iguales!
                    case 1:
                        this.cartasJugada.add(c1);
                        estado = 2;
                        break;
                    case 2:
                        this.cartasJugada.add(c1);
                        estado = 3;
                        break;
                    case 3:
                        this.cartasJugada.add(c1);
                        estado = 4;
                        break;
                    case 5:
                        this.cartasJugada.add(c1);
                        estado = 6;
                        break;
                    case 6:
                        this.cartasJugada.add(c1);
                        estado = 9;
                        break;
                    case 7:
                        this.cartasJugada.add(c1);
                        estadoAux = 7;
                        estado = 8;
                        break;
                    case 8:
                        this.cartasJugada.add(c1);
                        estado = 9;
                        break;
                    case 9:
                        this.cartasJugada.add(c1);
                        estado = 4;
                        break;
                    case 10:
                        this.cartasJugada.add(c1);
                        estadoAux = 10;
                        estado = 11;
                        break;
                    case 11:
                        if(estadoAux==10)
                            this.cartasJugada.add(c1);
                        else
                            this.kicker.add(c1);
                        estado = 12;
                        break;
                    case 12:
                        estado = 4;
                        break;
                    case 13:
                        this.cartasJugada.add(c1);
                        this.cartasJugada.remove(0);
                        this.cartasJugada.remove(0);
                        estado = 9;
                    case 14:
                        this.kicker.add(c1);
                        break;
                }
            } else { //NO IGUALES!
                switch (estado) {
                    case 1:
                        this.kicker.add(c1);
                        break;
                    case 2:
                        this.cartasJugada.add(c1);
                        estado = 5;
                        break;
                    case 3:
                        this.cartasJugada.add(c1);
                        estado = 10;
                        break;
                    case 4:
                        this.cartasJugada.add(c1);
                        estado = 14;
                        break;
                    case 5:
                        this.kicker.add(c1);
                        break;
                    case 6:
                        this.cartasJugada.add(c1);
                        estado = 7;
                        break;
                    case 7:
                        this.kicker.add(c1);
                        break;
                    case 8:
                        this.cartasJugada.add(c1);
                        estadoAux = 6;
                        break;
                    case 9:
                        this.cartasJugada.add(c1);
                        estado = 13;
                        break;
                    case 10:
                        this.kicker.add(c1);
                    case 11:
                        if(estadoAux==10)
                            this.cartasJugada.add(c1);
                        else
                            this.kicker.add(c1);
                        estadoAux = 11;
                    case 14:
                        this.kicker.add(c1);
                        break;
                    case 13:
                        this.kicker.add(c1);
                    //default:
                    //    this.kicker.add(c1);
                }
            }
            c1 = c2;
        }
        switch (estado) {//ESTADO FINAL (ULTIMA CARTA)
            case 1:
                this.kicker.add(c1);
                this.jugada = "HighCard";
                int ultimoIndice = this.cartas.size()-1;
                if (this.cartas.get(0).getValue() == 1) {
                    this.cartasJugada.add(this.cartas.get(0));
                    this.kicker.remove(0);
                }else{
                    this.cartasJugada.add(this.cartas.get(ultimoIndice));
                    ultimoIndice--;
                    this.kicker.remove(this.kicker.size()-1);
                }
                break;
            case 2:
                this.cartasJugada.add(c1);
                this.jugada = "Pair";
                break;
            case 3:
                this.cartasJugada.add(c1);
                this.jugada = "ThreeOfAKind";
                break;
            case 4:
                this.cartasJugada.add(c1);
                this.jugada = "Poker";
                while(this.cartasJugada.size()>4)
                    this.cartasJugada.remove(0);
                break;
            case 5:
                this.kicker.add(c1);
                this.jugada = "Pair";
                break;
            case 6:
                this.cartasJugada.add(c1);
                this.jugada = "Two Pair";                
                while(this.cartasJugada.get(0).getValue()==1)
                    this.cartasJugada.add(this.cartasJugada.remove(0));
                while(this.cartasJugada.size()>4)
                    this.kicker.add(this.cartasJugada.remove(0));
                break;
            case 7:
                this.kicker.add(c1);
                this.jugada = "Two Pair";
                while(this.cartasJugada.get(0).getValue()==1)
                    this.cartasJugada.add(this.cartasJugada.remove(0));
                while(this.cartasJugada.size()>4)
                    this.kicker.add(this.cartasJugada.remove(0));
                break;
            case 8:
                if(estadoAux==7)
                    this.cartasJugada.add(c1);
                else
                    this.kicker.add(c1);
                this.jugada = "Two Pair";
                while(this.cartasJugada.get(0).getValue()==1)
                    this.cartasJugada.add(this.cartasJugada.remove(0));
                while(this.cartasJugada.size()>4)
                    this.kicker.add(this.cartasJugada.remove(0));
                break;
            case 9:
                this.cartasJugada.add(c1);
                this.jugada = "Full-House";
                this.kicker.clear();
                break;
            case 10:
                this.kicker.add(c1);
                this.jugada = "ThreeOfAKind";
                break;
            case 11:
                this.jugada = "Full-House";
                if(estadoAux == 10)
                    this.cartasJugada.add(c1);
                else
                    this.kicker.add(c1);
                this.kicker.clear();
                break;
            case 12:
                this.cartasJugada.add(c1);
                this.jugada = "Full-House";
                this.kicker.clear();
                break;
            case 13://auxiliar: 9 distinta -> 13
                this.jugada = "Full-House";
                this.kicker.clear();
                break;
            case 14:
                this.kicker.add(c1);
                this.jugada = "Poker";
                while(this.cartasJugada.size()>4)
                    this.cartasJugada.remove(0);
                break;
            case 15:
                this.kicker.add(c1);
                this.jugada = "Two Pair";                
                while(this.cartasJugada.get(0).getValue()==1)
                    this.cartasJugada.add(this.cartasJugada.remove(0));
                while(this.cartasJugada.size()>4)
                    this.kicker.add(this.cartasJugada.remove(0));
                break;
        }

    }

    private boolean esReal(ArrayList<Card> cartasJugada) {
        boolean j = false, q = false, k = false, a = false;
        for (Card c : cartasJugada) {
            switch (c.getValue()) {
                case 11:
                    j = true;
                    break;
                case 12:
                    q = true;
                    break;
                case 13:
                    k = true;
                    break;
                case 1:
                    a = true;
                    break;
            }
        }if(j && q && k && a){
            if(cartasJugada.size()>5)
                for(int i = cartasJugada.size()-5; i > 0; i--)
                    cartasJugada.remove(i);
            cartasJugada.add(cartasJugada.remove(0));
            return true;
        }else
            return false;
            
    }

    public ArrayList<Card> getCartasJugada() {
        return cartasJugada;
    }

    public void setCartasJugada(ArrayList<Card> cartasJugada) {
        this.cartasJugada = cartasJugada;
    }

    public ArrayList<Card> getKicker() {
        return kicker;
    }

    public void setKicker(ArrayList<Card> kicker) {
        this.kicker = kicker;
    }
    
    public void limpiarKicker(){
        if(this.kicker.get(0).getValue()==1 && this.kicker.size()>1)
            this.kicker.remove(1);
        else
            this.kicker.remove(0);
    }

    @Override
    public int compareTo(HandEvaluator t) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
        if(this.rank != t.getRank())
            return this.rank - t.getRank();
        else{
            int comparacion;
            switch(this.rank){
                case 1://highCard
                    comparacion = comparaDos(t, 0);
                    if(comparacion != 0)
                        return comparacion;
                    else
                        return compareKicker(t, 4);
                case 2://Pair
                    comparacion = comparaDos(t, 0);
                    if(comparacion != 0)
                        return comparacion;
                    else
                        return compareKicker(t, 3);
                case 3://Two Pair:
                    comparacion = comparaDos(t, 3);
                    if (comparacion != 0) {
                        return comparacion;
                    }
                    comparacion = comparaDos(t, 0);
                    if (comparacion != 0) {
                        return comparacion;
                    } else {
                        return compareKicker(t, 1);
                    }
                case 4://threeOfAKind
                    comparacion = comparaDos(t, 0);
                    if (comparacion != 0) {
                        return comparacion;
                    }
                    else
                        return compareKicker(t, 2);
                    
                case 5://Straight
                    comparacion = comparaDos(t, 4);
                    return comparacion;
                    
                case 6://Flush
                    comparacion = comparaDos(t, 4);
                    return comparacion;
                case 7://Full
                    comparacion = comparaDos(t, 4);
                    if (comparacion != 0) {
                        return comparacion;
                    }
                    comparacion = comparaDos(t, 0);
                    return comparacion;
                case 8://Poker
                     comparacion = comparaDos(t, 0);
                    if (comparacion != 0) {
                        return comparacion;
                    }else
                        return compareKicker(t, 1);
                    
                case 9://StraightFlush
                    comparacion = comparaDos(t, 4);
                    return comparacion;
                    
                default://ROYAL FLUSH
                    return 0;
            }
        }
        
    }
    
    private int comparaDos(HandEvaluator t, int pos){
        return this.cartasJugada.get(pos).getRank()-
                            t.getCartasJugada().get(pos).getRank();
    }    
    
    public int getRank(){
        return this.rank;
    }
            
        @Override
    public String toString() {
        /*String cadena = new String();
        cadena += this.idPlayer + ": ";
        for(Card c : this.cartasJugada)
            cadena += c.toString();
        cadena += " " + this.jugada;
        return cadena;*/
                String output = new String();
                
                Collections.sort(this.getKicker());
                if(this.getKicker().size()>0 && this.getKicker().get(0).getValue() == 1)
                    this.getKicker().add(this.getKicker().remove(0));
                
                output += "-" + this.idPlayer + " ->Best Hand: "+this.getJugada();
                if(!this.getCartasJugada().isEmpty())
                    output += " with " + this.getCartasJugada().toString();
                if(!this.getKicker().isEmpty()){
                    int kickerLocal = 5 - this.getCartasJugada().size();
                    while(this.getKicker().size()>kickerLocal)
                        this.limpiarKicker();
                    output += " and " + this.getKicker().toString() + " Kicker";
                }
                
                return output;
    }

    private void setRank() {
        switch(this.jugada){
            case "HighCard":
                this.rank = 1;
                break;
            case "Pair":
                this.rank = 2;
                break;
                
            case "Two Pair":
                this.rank = 3;
                break;
                
            case "ThreeOfAKind":
                this.rank = 4;
                break;
                
            case "Straight":
                this.rank = 5;
                break;
                
            case "Flush":
                this.rank = 6;
                break;
                
            case "Full-House":
                this.rank = 7;
                break;
                
            case "Poker":
                this.rank = 8;
                break;
                
            case "StraightFlush":
                this.rank = 9;
                break;
                
            case "RoyalFlush":
                this.rank = 10;
                break;
                
                       
        }
    }
    
    private int compareKicker(HandEvaluator t, int kickers) {
        int i = kickers-1;
        boolean diff = false;
        while(i < 0 && !diff){
            if(this.kicker.get(i).getValue()!=t.getKicker().get(i).getValue())
                diff = true;
            else
                i--;
        }
        return this.kicker.get(i).getValue() - t.getKicker().get(i).getValue();
    }

    
}
