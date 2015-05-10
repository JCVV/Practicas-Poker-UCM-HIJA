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
public class WillMa implements RangoOR{
    
    private static final String UTG = "55+,A8s+,KTs+,QTs+,JTs,A9o+,KJo+";
    private static final String MP = "22+,A2s+,KTs+,QTs+,JTs,T9s,98s,87s,76s,65s,54s,43s,32s,A9o+,KTo+,QTo+,JTo"; 
    private static final String CO = "22+,A2s+,K5s+,Q7s+,J8s+,T8s+,98s,87s,76s,65s,54s,43s,32s,A5o,A7o+,K9o+,Q9o+,J9o+,T9o";
    private static final String BTN = "22+,A2s+,K2s+,Q2s+,J3s+,T5s+,95s+,85s+,75s+,65s,54s,43s,32s,A2o+,K4o+,Q6o+,J7o+,T7o+,97o+,87o";
    private static final String SB = "22+,A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,72s+,62s+,52s+,42s+,32s,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,82o+,72o+,62o+,52o+,42o+,32o";
    private static final String BB = "22+,A2s+,K2s+,Q2s+,J2s+,T2s+,92s+,82s+,72s+,62s+,52s+,42s+,32s,A2o+,K2o+,Q2o+,J2o+,T2o+,92o+,82o+,72o+,62o+,52o+,42o+,32o";
    
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
