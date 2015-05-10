/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica4hja.modelo;

/**
 *
 * @author jcarlos
 */
public class Statistics implements Comparable<Statistics>{
   
    public static long total = 0;
    private int player;
    private float win;
    private double tie;
    
    public Statistics(int player){
        this.player = player;
        this.win = 0;
        this.tie = 0;
    }
    
    public float getWins(){//System.out.printf("Value: %.2f", value);
        return ((win / total) * 100);
    }
    
    public double getTies(){
        return ((tie / total) * 100);
    }
    
    public void wins(){
        this.win++;
    }
    
    public void ties(){
        this.tie++;
    }

    public static long getTotal() {
        return total;
    }

    public static void setTotal(long total) {
        Statistics.total = total;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }


    public float getWin() {
        return win;
    }

    public void setWin(float win) {
        this.win = win;
    }

    public double getTie() {
        return tie;
    }

    public void setTie(double tie) {
        this.tie = tie;
    }
    
    public void clear(){
        this.win = 0;
        this.tie = 0;
        Statistics.total=0;
    }
    
    @Override
    public int compareTo(Statistics t) {
        return (int) (this.win - t.getWin());
    }

    public void ties(double eq) {
        this.tie += eq;
    }
   
    
}