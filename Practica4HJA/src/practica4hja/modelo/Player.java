/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica4hja.modelo;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author jcarlos
 */
public class Player {
    
    private String name;
    private int fondos;
    private int ganados;
    private int perdidos;
    private int empatados;
    private int retiradas;
    private int total;

    private int retPreFlop;
    private int retFlop;
    private int retTurn;
    private ArrayList<Card> cartas;

    public Player(String name, int fondos) {
        this.name = name;
        this.fondos = fondos;
        this.ganados = 0;
        this.perdidos = 0;
        this.empatados = 0;
        this.retiradas = 0;
        this.total = 0;
        this.retPreFlop = 0;
        this.retFlop = 0;
        this.retTurn = 0;
        this.cartas = new ArrayList<>(2);
    }

    public Player() {
        this.name = "Banca";
        this.fondos = 0;
        this.ganados = 0;
        this.perdidos = 0;
        this.empatados = 0;
        this.retiradas = 0;
        this.total = 0;
        this.retPreFlop = 0;
        this.retFlop = 0;
        this.retTurn = 0;
        this.cartas = new ArrayList<>(2);
        
    }
    
    
    public void retPreFlop() {
        this.retPreFlop++;
    }

    public void retFlop() {
        this.retFlop++;
    }

    public void retTurn() {
        this.retTurn++;
    }
    
    public int getRetPreFlop() {
        return retPreFlop;
    }

    public int getRetFlop() {
        return retFlop;
    }

    public int getRetTurn() {
        return retTurn;
    }
    
    public ArrayList<Card> getCards(){
        return this.cartas;
    }
    
    public void retirado(){
        this.retiradas ++;
    }
    public void cleanCartas(){
        this.cartas.clear();
    }

    public Card getC1(){
        return this.cartas.get(0);
    }
    public Card getC2(){
        return this.cartas.get(1);
    }
    
    public void hand(Card c1, Card c2){
        this.cartas.add(c1);
        this.cartas.add(c2);
    }
    
    public void gana(){
        this.ganados++;
    }
    
    public void pierde(){
        this.perdidos++;
    }
    
    public void empata(){
        this.empatados++;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFondos() {
        return fondos;
    }

    public void setFondos(int fondos) {
        this.fondos = fondos;
    }

    public int getGanados() {
        return ganados;
    }

    public void setGanados(int ganados) {
        this.ganados = ganados;
    }

    public int getPerdidos() {
        return perdidos;
    }

    public void setPerdidos(int perdidos) {
        this.perdidos = perdidos;
    }

    public int getEmpatados() {
        return empatados;
    }

    public void setEmpatados(int empatados) {
        this.empatados = empatados;
    }

    public void apuesta(EstadoPartida ep) {
        if(ep == EstadoPartida.FLOP)
            this.fondos -=2;
        else
            this.fondos--;
        if(this.fondos < 0){
            int i = Integer.parseInt(JOptionPane.showInputDialog("Player sin dinero. Ingrese fichas"));
            this.gana(i);
        }
            
    }

    public void hand(Card c1) {
                this.cartas.add(c1);
    }

    public int getRetiradas() {
        return this.retiradas;
    }

    public Card getC(int i) {
        return this.cartas.get(i);
    }

    public void gana10() {
        this.fondos += 10;
    }
    public void gana9() {
        this.fondos += 9;
    }
    
    public void empata5(){
        this.fondos += 5;
    }

    public void gana(int i) {
        this.fondos += i;
    }

    public int getTotal() {
        return this.total;
    }

    public void totalMas() {
        this.total++;
    }

    public void reset() {
        this.fondos = 1000;
        this.ganados = 0;
         this.perdidos = 0;
         this.empatados = 0;
         this.retiradas = 0;
         this.total = 0;
         this.retPreFlop = 0;
        this.retFlop = 0;
        this.retTurn = 0;
    }
    
}
