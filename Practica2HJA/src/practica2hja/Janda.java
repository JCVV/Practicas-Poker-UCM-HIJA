/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2hja;

/**
 *
 * @author jcarlos
 */
public class Janda implements RangoOR{
    
    private static final String UTG = "AA-33,AKo-AJo,KQo,AKs-ATs,KQs-KTs,QJs-QTs,JTs-J9s,T9s,98s,87s,76s,65s";
    private static final String MP = "22+,ATo+,KQo,A7s+,A5s,KTs+,QTs+,J9s+,T8s+,97s+,86s+,75s+,64s+,54s"; 
    private static final String CO = "AA-22,AKo-ATo,KQo-KJo,QJo,AKs-A2s,KQs-K6s,QJs-Q7s,JTs-J8s,T9s-T8s,98s-97s,87s-86s,76s-75s,65s-64s,54s";
    private static final String BTN = "AA-22,AKo-A2o,KQo-K7o,QJo-Q9o,JTo-J9o,T9o-T8o,98o,87o,AKs-A2s,KQs-K2s,QJs-Q2s,JTs-J5s,T9s-T6s,98s-96s,87s-85s,76s-74s,65s-64s,54s-53s,43s";
    private static final String SB = "AA-22,AKo-A7o,KQo-K9o,QJo-Q9o,JTo-J9o,T9o,98o,AKs-A2s,KQs-K2s,QJs-Q4s,JTs-J7s,T9s-T7s,98s-97s,87s-86s,76s-75s,65s-64s,54s";
    private static final String BB = "22+,A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,72s+,62s+,52s+,42s+,32s,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,82o+,72o+,62o+,52o+,42o+,32o";
    
    public Janda(){

    }

    @Override
    public String getUTG() {
        return UTG;
    }

    @Override
    public String getMP() {
        return MP;
    }

    @Override
    public String getCO() {
        return CO;
    }

    @Override
    public String getBTN() {
        return BTN;
    }

    @Override
    public String getSB() {
        return SB;
    }

    @Override
    public String getBB() {
        return BB;
    }
    
    
    
}
