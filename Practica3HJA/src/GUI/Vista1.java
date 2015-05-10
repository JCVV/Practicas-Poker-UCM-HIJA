/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static GUI.PokerStove.sTodos;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import practica2hja.Card;
import practica2hja.Janda;
import practica2hja.PairCards;
import practica2hja.Player;
import practica2hja.Position;
import practica2hja.RangoOR;
import practica2hja.RankSklansky;
import practica2hja.WillMa;

/**
 *
 * @author jcarlos
 */
public class Vista1 extends javax.swing.JFrame {

    
    private final JTextField pokerstoveText;
    public String arrayTodos;
    private final int positionPlayer;
    private String parejaCartas;

    /**
     * Creates new form Vista1
     * @param pokerstoveText
     * @param arrayTodos
     * @param i
     * @throws java.io.FileNotFoundException
     */
    public Vista1(JTextField pokerstoveText, String arrayTodos, int i) throws FileNotFoundException {
        this.positionPlayer = i;
        this.pokerstoveText = pokerstoveText;
        this.arrayTodos = arrayTodos;
        this.arrayTodos = "";
        this.parejaCartas = "";
        initComponents();
        
        this.seleccionadas = new ArrayList<>();
        this.jPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
        this.bcards = new JButton[13][13];
        crearBotones();
        this.ranking = 0;
        this.sliderRango.setMaximum(10000);
        this.sliderRango.setMinorTickSpacing(1000);
        this.comboRangoAnalizar.removeAllItems();
        this.comboRangoAnalizar.addItem("Janda");
        this.comboRangoAnalizar.addItem("Will Ma");
        this.rangoPS.removeAllItems();
        this.rangoPS.addItem("Janda");
        this.rangoPS.addItem("Will Ma");
        this.comboPosicionAnalizar.removeAllItems();
        this.comboPosicionAnalizar.addItem("UTG");
        this.comboPosicionAnalizar.addItem("MP");
        this.comboPosicionAnalizar.addItem("CO");
        this.comboPosicionAnalizar.addItem("SB");
        this.comboPosicionAnalizar.addItem("BB");
        this.comboAccionAnalizar.removeAllItems();
        this.comboAccionAnalizar.addItem("OR");
        this.comboAccionAnalizar.addItem("FOLD");
        this.textPlayerName.setText("MN-UCM");
        this.janda = new Janda();
        this.willMa = new WillMa();
        this.nextButton.setEnabled(false);

        this.jPanel1.updateUI();
        this.jPanel1.setVisible(true);
        //Para capturar solo los caracteres que nos interesan en el textField.
        textRangos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ((((c < '0') || (c > '9')) && (c != 'T') && (c != 'J') && (c != 'Q')
                         && (c != 'K') && (c != 'A') && (c != ',') && (c != '+')
                         && (c != '-') && (c != 's') && (c != 'c') && (c != 'd') && (c != 'h') && (c != 'o')) 
                         && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();  // ignore event
                }
            }
        });
        
        this.sklansky = new RankSklansky(this);
        this.sklansky.setModel();
        this.comboRanking.setSelectedIndex(0);
        this.sliderRango.setValue(0);
    }
    
    @Override
    public void dispose(){
        for(JButton[] b : this.bcards){
            
            for(JButton bb : b){
                if(bb.getBackground()==Color.yellow){
                    if(this.arrayTodos.equals(""))
                        this.arrayTodos = bb.getText();
                    else
                        this.arrayTodos = this.arrayTodos + "," + bb.getText();
                }
            }
        }
        System.out.println(this.arrayTodos);
       serializeRanges(this.arrayTodos, this.positionPlayer);
        super.dispose();
    }
    
    private void serializeRanges(String str, int position){
        StringTokenizer token = new StringTokenizer(str, ",");
        sTodos[position] = "";
        while(token.hasMoreElements()){
            String rango = token.nextToken();
            if(rango.length() == 2){
                String value = rango.substring(0, 1);
                if(sTodos[position]!=null && !sTodos[position].equals(""))
                    sTodos[position] += ",";
                else
                    sTodos[position] = "";
                sTodos[position] += value + "s" + value + "h," + value + "s" + value + "d,"
                        + value + "s" + value + "c,"
                        + value + "h" + value + "d," + value + "h" + value + "c,"
                        + value + "d" + value + "c";
            } else if(rango.length() == 3 && rango.substring(2, 3).equals("s")){
                String value1 = rango.substring(0, 1);
                String value2 = rango.substring(1, 2);
                if(sTodos[position]!=null && !sTodos[position].equals(""))
                    sTodos[position] += ",";
                else
                    sTodos[position] = "";
                sTodos[position] += value1 + "s" + value2 + "s," + value1 + "h" + value2 + "h,"
                        + value1 + "c" + value2 + "c,"
                        + value1 + "d" + value2 + "d";
            } else if(rango.length() == 3 && rango.substring(2, 3).equals("o")){
                String value1 = rango.substring(0, 1);
                String value2 = rango.substring(1, 2);
                if(sTodos[position]!=null && !sTodos[position].equals(""))
                    sTodos[position] += ",";
                else
                    sTodos[position] = "";
                sTodos[position] += value1 + "s" + value2 + "h," 
                        + value1 + "s" + value2 + "c,"
                        + value1 + "s" + value2 + "d,"
                        + value1 + "h" + value2 + "s,"
                        + value1 + "h" + value2 + "c," 
                        + value1 + "h" + value2 + "d,"
                        + value1 + "c" + value2 + "s,"
                        + value1 + "c" + value2 + "h,"
                        + value1 + "c" + value2 + "d," 
                        + value1 + "d" + value2 + "s,"
                        + value1 + "d" + value2 + "h,"
                        + value1 + "d" + value2 + "c";
            }
        }
        sTodos[position] += this.parejaCartas;
    }
    
    public void rangoSelected(){
        this.textRangos.removeAll();
        this.rangoRankings.removeAll();
        String cadena = new String();
        boolean anterior = false;
        int cont = 0;
        for(int i = 12; i >=0 ; i--){
            if(this.bcards[i][i].getBackground() == Color.yellow){
                if(!anterior){
                    if(cadena.length()!=0)
                        cadena += ",";
                    cadena+= this.bcards[i][i].getText();
                }else if(anterior && i == 0){
                    cadena += "+";
                }
                cont ++;
                anterior = true;
            }else{
                if(anterior && cont > 1)
                   // if(cadena.length()!=0)
                     //   cadena += ",";
                    cadena += "-" + this.bcards[i+1][i+1].getText();
                anterior = false;
                cont = 0;
            }
        }
        
        /////////////////////////////////////////////////////////////////////
        
        anterior = false;
        cont = 0;
        for(int x = 0; x < 13; x++){        
            for(int i = 12; i >x ; i--){
                if(this.bcards[x][i].getBackground() == Color.yellow){
                    if(!anterior){
                        if(cadena.length()!=0)
                            cadena += ",";
                        cadena+= this.bcards[x][i].getText();
                    }else if(anterior && i == x+1){
                        cadena += "+";
                    }
                    cont ++;
                    anterior = true;
                }else{
                    if(anterior && cont > 1)
                       // if(cadena.length()!=0)
                         //   cadena += ",";
                        cadena += "-" + this.bcards[x][i+1].getText();
                    anterior = false;
                    cont = 0;
                }
            }
            anterior = false;
            cont = 0;
        }
        
        /////////////////////////////////////////////////////////////////////
        
        anterior = false;
        cont = 0;
        for(int x = 0; x < 13; x++){        
            for(int i = 12; i > x ; i--){
                if(this.bcards[i][x].getBackground() == Color.yellow){
                    if(!anterior){
                        if(cadena.length()!=0)
                            cadena += ",";
                        cadena+= this.bcards[i][x].getText();
                    }else if(anterior && i == x+1){
                        cadena += "+";
                    }
                    cont ++;
                    anterior = true;
                }else{
                    if(anterior && cont > 1)
                       // if(cadena.length()!=0)
                         //   cadena += ",";
                        cadena += "-" + this.bcards[i+1][x].getText();
                    anterior = false;
                    cont = 0;
                }
            }
            anterior = false;
            cont = 0;
        }
        
        this.textRangos.setText(cadena);
        this.rangoRankings.setText(cadena);
        this.pokerstoveText.setText(cadena);
        
        
        
    }
        
    public void selectPair(PairCards c){
        int x;
        int y;
        this.seleccionadas.add(c);
        x = c.getC1().getValue();
        y = c.getC2().getValue();
        if(x!=y)
            if(!c.isSuited()){
                int temp = x;
                x = y;
                y = temp;
            }
        this.bcards[x][y].setBackground(Color.yellow);
    }
    
    public boolean isSelected(PairCards c){
        int x;
        int y;
        x = c.getC1().getValue();
        y = c.getC2().getValue();
        if(x!=y)
            if(!c.isSuited()){
                int temp = x;
                x = y;
                y = temp;
            }
        return this.bcards[x][y].getBackground() == Color.yellow;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        textRangos = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        sliderRango = new javax.swing.JSlider();
        comboRanking = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        labelRango = new javax.swing.JLabel();
        rangoRankings = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        comboRangoAnalizar = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        manoAnalizar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        comboPosicionAnalizar = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        comboAccionAnalizar = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        labelResultadoAnalizar = new javax.swing.JLabel();
        botonAnalizar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        botonArchivo = new javax.swing.JButton();
        archivoLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        psResutl = new javax.swing.JLabel();
        psAction = new javax.swing.JLabel();
        psCards = new javax.swing.JLabel();
        psPosition = new javax.swing.JLabel();
        psNPlayers = new javax.swing.JLabel();
        psHandNumber = new javax.swing.JLabel();
        nextButton = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        psName = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        textPlayerName = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        rangoPS = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setText("Calcular");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        textRangos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textRangosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addComponent(jButton1)
                .addContainerGap(196, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(textRangos)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jButton1)
                .addContainerGap(383, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(textRangos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(426, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Selector Rangos", jPanel2);

        sliderRango.setMajorTickSpacing(10);
        sliderRango.setMinorTickSpacing(1);
        sliderRango.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderRangoStateChanged(evt);
            }
        });

        comboRanking.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboRanking.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboRankingItemStateChanged(evt);
            }
        });

        jLabel1.setText("Ranking:");

        labelRango.setText("50");

        rangoRankings.setText(" ");

        jLabel2.setText("Rango:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelRango)
                    .addComponent(sliderRango, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rangoRankings, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboRanking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(298, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboRanking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(27, 27, 27)
                .addComponent(labelRango)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sliderRango, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(rangoRankings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(234, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Rankings", jPanel3);

        jLabel3.setText("Rango:");

        comboRangoAnalizar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Mano:");

        jLabel5.setText("Posición:");

        comboPosicionAnalizar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Acción:");

        comboAccionAnalizar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Resultado:");

        labelResultadoAnalizar.setText("Correcto/Erróneo");

        botonAnalizar.setText("Analizar");
        botonAnalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnalizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelResultadoAnalizar)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboRangoAnalizar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(manoAnalizar)
                            .addComponent(comboPosicionAnalizar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboAccionAnalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addComponent(botonAnalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboRangoAnalizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(manoAnalizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(comboPosicionAnalizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAnalizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(comboAccionAnalizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(labelResultadoAnalizar))
                .addContainerGap(241, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Analizar Manos", jPanel4);

        botonArchivo.setText("Archivo");
        botonArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonArchivoActionPerformed(evt);
            }
        });

        archivoLabel.setText(" ");

        jLabel8.setText("PokerStars Hand:");

        jLabel9.setText("Nº Players:");

        jLabel10.setText("Position:");

        jLabel11.setText("Cards:");

        jLabel12.setText("Action:");

        jLabel13.setText("RESULT:");

        psResutl.setText(" ");

        psAction.setText(" ");

        psCards.setText(" ");

        psPosition.setText(" ");

        psNPlayers.setText(" ");

        psHandNumber.setText(" ");

        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        jLabel14.setText("Name:");

        psName.setText(" ");

        jLabel15.setText("Player name:");

        jLabel16.setText("Rango:");

        rangoPS.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13)
                            .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(psName, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(psHandNumber)
                            .addComponent(psNPlayers)
                            .addComponent(psResutl, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                            .addComponent(psAction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(psCards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(psPosition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(botonArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(archivoLabel))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(textPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rangoPS, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {psAction, psCards, psHandNumber, psNPlayers, psName, psPosition});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonArchivo)
                    .addComponent(archivoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(textPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(rangoPS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(psHandNumber))
                .addGap(5, 5, 5)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(psName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(psNPlayers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(psPosition)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(psCards)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(psAction)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(psResutl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nextButton)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("PokerStars", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textRangosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textRangosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textRangosActionPerformed

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        deseleccionar();
        String cadena = this.textRangos.getText();
        String[] polar = cadena.split(",");
        if(polar.length == 4 && isNumeric(polar[0]) && isNumeric(polar[1]) && 
                isNumeric(polar[2])&& isNumeric(polar[3])){
            
            
            System.out.println(polar[0]);
            int w = Integer.parseInt(polar[0]);
            
            int x = w + Integer.parseInt(polar[1]);
            
            int y = x + Integer.parseInt(polar[2]);
            
            int z = y + Integer.parseInt(polar[3]);
            
            if(z > 100){
                JOptionPane.showMessageDialog(null, "Rango polarizado erróneo");
                return;
            }
            ArrayList<String> alw = this.sklansky.rangoPolarizado(w, this.ranking);
            ArrayList<String> alx = this.sklansky.rangoPolarizado(x, this.ranking);
            ArrayList<String> aly = this.sklansky.rangoPolarizado(y, this.ranking);
            ArrayList<String> alz = this.sklansky.rangoPolarizado(z, this.ranking);
            
            alx.removeAll(alw);
            
            alz.removeAll(aly);
            
            ArrayList<String> polarizado;
            polarizado = new ArrayList<>();
            polarizado.addAll(alx);
            polarizado.addAll(alz);
            
            System.out.println(polarizado);
            
            cadena = "";
            for(String s : polarizado){
                cadena += s + ",";
            }
            if(cadena.length()>0)
            cadena = cadena.substring(0, cadena.length()-1);
        
        }
        StringTokenizer tokens = new StringTokenizer(cadena, ",");
        while(tokens.hasMoreTokens()){
            String tk = tokens.nextToken();
            if(tk.length() == 2)
                selectPair(new PairCards(tk.substring(0, 1), tk.substring(1, 2)));
            else if(tk.length()==3){
                switch (tk.substring(2, 3)) {
                    case "+":
                        {
                            masMenos(tk, true);
                            break;
                        }
                    case "-":
                        {
                            masMenos(tk, false);
                            break;
                        }
                    default:
                        selectPair(new PairCards(tk.substring(0, 1), tk.substring(1,2), tk.substring(2,3)));
                }
                //selectPair(new PairCards(tk.substring(0,1), tk.substring(1,2), tk.substring(2,3)));
            }else if(tk.length()==4){
                switch (tk.substring(3, 4)) {
                    case "+":
                        masMenosOS(tk, true);
                        break;
                    default:
                        if(this.parejaCartas.equals(""))
                            this.parejaCartas+=tk;
                        else
                            this.parejaCartas+=","+tk;
                }
            }else if(tk.length()==5){
                intervalo(tk, 1);
            }else if(tk.length()==6)
                intervalo(tk, 2);
            else if(tk.length()==7)
                intervalo(tk, 3);
        }
        rangoSelected();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void sliderRangoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderRangoStateChanged
        this.labelRango.setText(String.valueOf(this.sliderRango.getValue()/100d)+"%");
        if(this.sklansky!=null){
            this.sklansky.rango(this.sliderRango.getValue()/100d, this.ranking);
            rangoSelected();
        }
        
    }//GEN-LAST:event_sliderRangoStateChanged

    private void comboRankingItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboRankingItemStateChanged
        // TODO add your handling code here:
        if( evt.getStateChange() == java.awt.event.ItemEvent.SELECTED ) {
            this.ranking = this.comboRanking.getSelectedIndex();

            this.sklansky.rango(this.sliderRango.getValue()/100d, this.ranking);
        }
    }//GEN-LAST:event_comboRankingItemStateChanged

    private void botonAnalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnalizarActionPerformed
        // TODO add your handling code here:
        if(!this.manoAnalizar.getText().equals("")){
            String rangos;
            RangoOR rng = null;
            
            
            switch(this.comboRangoAnalizar.getSelectedIndex()){
                case 0:
                    rng = this.janda;
                    break;
                default:
                    rng = this.willMa;
                    break;
            }
            
            switch(this.comboPosicionAnalizar.getSelectedIndex()){
                case 0:rangos = rng.getUTG();
                    break;
                case 1:rangos = rng.getMP();
                    break;
                case 2:rangos = rng.getCO();
                    break;
                case 3:rangos = rng.getBTN();
                    break;
                case 4:rangos = rng.getSB();
                    break;
                default:rangos = rng.getBB();
                    break;
            }
            deseleccionar();
            StringTokenizer st = new StringTokenizer(rangos, ",");
            while(st.hasMoreTokens()){
                String tk = st.nextToken();
                if(tk.length() == 2)
                selectPair(new PairCards(tk.substring(0, 1), tk.substring(1, 2)));
            else if(tk.length()==3){
                switch (tk.substring(2, 3)) {
                    case "+":
                        {
                            masMenos(tk, true);
                            break;
                        }
                    case "-":
                        {
                            masMenos(tk, false);
                            break;
                        }
                    default:
                        selectPair(new PairCards(tk.substring(0, 1), tk.substring(1,2), tk.substring(2,3)));
                }
                //selectPair(new PairCards(tk.substring(0,1), tk.substring(1,2), tk.substring(2,3)));
            }else if(tk.length()==4){
                switch (tk.substring(3, 4)) {
                    case "+":
                        masMenosOS(tk, true);
                        break;
                }
            }else if(tk.length()==5){
                intervalo(tk, 1);
            }else if(tk.length()==6)
                intervalo(tk, 2);
            else if(tk.length()==7)
                intervalo(tk, 3);
                
            }
            String entrada = this.manoAnalizar.getText();
            PairCards pc;
            if(entrada.length()==2)
                pc = new PairCards(entrada.substring(0,1), entrada.substring(1,2));
            else
                pc= new PairCards(entrada.substring(0,1), entrada.substring(1,2), entrada.substring(2,3)); 
            if(isSelected(pc))
                if(this.comboAccionAnalizar.getSelectedIndex()==0)
                    this.labelResultadoAnalizar.setText("<html><font color='green'>CORRECTO</font></html>");
                else
                    this.labelResultadoAnalizar.setText("<html><font color='red'>ERRONEO</font></html>");
            else
                if(this.comboAccionAnalizar.getSelectedIndex()==1)
                    this.labelResultadoAnalizar.setText("<html><font color='green'>CORRECTO</font></html>");
                else
                    this.labelResultadoAnalizar.setText("<html><font color='red'>ERRONEO</font></html>");
            
        }
        rangoSelected();
    }//GEN-LAST:event_botonAnalizarActionPerformed

    private void botonArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonArchivoActionPerformed
        // TODO add your handling code here:
        String aux="";

        JFileChooser fLocal=new JFileChooser();
        fLocal.showOpenDialog(this);
        this.file=fLocal.getSelectedFile();
        if(this.file!=null){
            this.archivoLabel.setText("Archivo cargado con éxito.");
            FileReader archivos;
            try {
                archivos = new FileReader(this.file);
                this.lee=new BufferedReader(archivos);
                this.nextButton.setEnabled(true);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Vista1.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        else
        this.archivoLabel.setText("Fallo al cargar el archivo.");

        try {
            parserPS();
        } catch (IOException ex) {
            Logger.getLogger(Vista1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonArchivoActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        try {
            // TODO add your handling code here:
            if(this.lee!=null)
                parserPS();
        } catch (IOException ex) {
            Logger.getLogger(Vista1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_nextButtonActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel archivoLabel;
    private javax.swing.JButton botonAnalizar;
    private javax.swing.JButton botonArchivo;
    private javax.swing.JComboBox comboAccionAnalizar;
    private javax.swing.JComboBox comboPosicionAnalizar;
    private javax.swing.JComboBox comboRangoAnalizar;
    private javax.swing.JComboBox comboRanking;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelRango;
    private javax.swing.JLabel labelResultadoAnalizar;
    private javax.swing.JTextField manoAnalizar;
    private javax.swing.JButton nextButton;
    private javax.swing.JLabel psAction;
    private javax.swing.JLabel psCards;
    private javax.swing.JLabel psHandNumber;
    private javax.swing.JLabel psNPlayers;
    private javax.swing.JLabel psName;
    private javax.swing.JLabel psPosition;
    private javax.swing.JLabel psResutl;
    private javax.swing.JComboBox rangoPS;
    private javax.swing.JTextField rangoRankings;
    private javax.swing.JSlider sliderRango;
    private javax.swing.JTextField textPlayerName;
    private javax.swing.JTextField textRangos;
    // End of variables declaration//GEN-END:variables
    private JButton[][] bcards = new JButton[13][13];
    private final ArrayList<PairCards> seleccionadas;
    private final RankSklansky sklansky;
    private int ranking;
    private final Janda janda;
    private final WillMa willMa;
    private File file;
    private BufferedReader lee;

    public void deseleccionar() {
        this.seleccionadas.removeAll(seleccionadas);
        int cn1 = 14;
        for (JButton[] b : this.bcards) {
            String s1 = null;
            switch (cn1) {
                case 14:
                    s1="A";
                    break;
                case 13:
                    s1="K";
                    break;
                case 12:
                    s1="Q";
                    break;
                case 11:
                    s1="J";
                    break;
                case 10:
                    s1="T";
                    break;
                default:
                    s1=String.valueOf(cn1);
                    
            }
            int x = 14;
            int ii = 0;
            for (int i = 14; i > 1 ; i--) {
                String salida = null;
                String suited = null;
                Color color = null;
                String s2 = null;
                switch (i) {
                    case 14:
                        s2 = "A";
                        break;
                    case 13:
                        s2= "K";
                        break;
                    case 12:
                        s2= "Q";
                        break;
                    case 11:
                        s2= "J";
                        break;
                    case 10:
                        s2= "T";
                        break;
                    default:
                        s2= String.valueOf(i);

                }
                if(cn1<x){
                    color = new Color(154, 192, 205);
                    suited = "o";
                    salida = s2+s1;
                }else if(cn1>x){
                    color = new Color(255,114,86);
                    suited = "s";
                    salida = s1+s2;
                } else {
                    color = new Color(154,255,154);
                    suited = "";
                    salida = s1+s2;
                }
                
                salida+=suited;

                
                b[ii].setBackground(color);
                x--;
                ii++;
            }
            cn1--;
        }
    }

    private void crearBotones() {
    
        int cn1 = 14;
        for (JButton[] b : this.bcards) {
            String s1;
            s1 = null;
            switch (cn1) {
                case 14:
                    s1="A";
                    break;
                case 13:
                    s1="K";
                    break;
                case 12:
                    s1="Q";
                    break;
                case 11:
                    s1="J";
                    break;
                case 10:
                    s1="T";
                    break;
                default:
                    s1=String.valueOf(cn1);
                    
            }
            int x = 14;
            int ii = 0;
            for (int i = 14; i > 1 ; i--) {
                String salida = null;
                String suited = null;
                Color color = null;
                String s2 = null;
                switch (i) {
                    case 14:
                        s2 = "A";
                        break;
                    case 13:
                        s2= "K";
                        break;
                    case 12:
                        s2= "Q";
                        break;
                    case 11:
                        s2= "J";
                        break;
                    case 10:
                        s2= "T";
                        break;
                    default:
                        s2= String.valueOf(i);

                }
                String posicion;
                if(cn1<x){
                    color = new Color(154, 192, 205);
                    suited = "o";
                    salida = s2+s1;
                    posicion = Integer.toString(14 - x)+ "," + Integer.toString(14 - cn1);
                }else if(cn1>x){
                    color = new Color(255,114,86);
                    suited = "s";
                    salida = s1+s2;
                    posicion = Integer.toString(14 - x)+ "," + Integer.toString(14 - cn1);
                } else {
                    color = new Color(154,255,154);
                    suited = "";
                    salida = s1+s2;
                    posicion = Integer.toString(14 - cn1) + "," + Integer.toString(14 - cn1);
                }
                
                salida+=suited;

                b[ii] = new JButton(salida);
                b[ii].setName(posicion);
                b[ii].addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMatrizActionPerformed(evt);
            }
        });
                
                b[ii].setPreferredSize(new Dimension(35,30));
                b[ii].setMargin(new Insets(0,0,0,0));
                b[ii].setBackground(color);
                this.jPanel1.add(b[ii]);
                x--;
                ii++;
            }
            cn1--;
        }   
    }
    
    private void  jButtonMatrizActionPerformed(java.awt.event.ActionEvent evt){
        JButton jb = (JButton) evt.getSource();
        String name = jb.getName();
        StringTokenizer tk = new StringTokenizer(name, ",");
        
        int x = Integer.parseInt(tk.nextToken());
        int y = Integer.parseInt(tk.nextToken());
        if(jb.getBackground()!=Color.yellow)
            jb.setBackground(Color.yellow);
        else {
            
            if (x == y) {
                jb.setBackground(new Color(154, 255, 154));
            } else if (x < y) {
                jb.setBackground(new Color(154, 192, 205));
            } else if (x > y) {
                jb.setBackground(new Color(255, 114, 86));
            }
        }    
        rangoSelected();
    }

    private void masMenos(String tk, boolean mas) {
        if(!mas){
            switch(tk.substring(0, 1)){
                case "A":
                    selectPair(new PairCards(tk.substring(0, 1),(tk.substring(1, 2))));
                    this.seleccionadas.add(new PairCards(tk.substring(0, 1),(tk.substring(1, 2))));
                case "K":
                    selectPair(new PairCards("K","K"));
                    this.seleccionadas.add(new PairCards("K", "K"));
                case "Q":
                    selectPair(new PairCards("Q","Q"));
                    this.seleccionadas.add(new PairCards("Q", "Q"));
                case "J":
                    selectPair(new PairCards("J","J"));
                    this.seleccionadas.add(new PairCards("J", "J"));
                case "T":
                    selectPair(new PairCards("T","T"));
                    this.seleccionadas.add(new PairCards("T", "T"));
                case "9":
                    selectPair(new PairCards("9","9"));
                    this.seleccionadas.add(new PairCards("9", "9"));
                case "8":
                    selectPair(new PairCards("8","8"));
                    this.seleccionadas.add(new PairCards("8", "8"));
                case "7":
                    selectPair(new PairCards("7","7"));
                    this.seleccionadas.add(new PairCards("7", "7"));
                case "6":
                    selectPair(new PairCards("6","6"));
                    this.seleccionadas.add(new PairCards("6", "6"));
                case "5":
                    selectPair(new PairCards("5","5"));
                    this.seleccionadas.add(new PairCards("5", "5"));
                case "4":
                    selectPair(new PairCards("4","4"));
                    this.seleccionadas.add(new PairCards("4", "4"));
                case "3":
                    selectPair(new PairCards("3","3"));
                    this.seleccionadas.add(new PairCards("3", "3"));
                case "2":
                    selectPair(new PairCards("2","2"));
                    this.seleccionadas.add(new PairCards("2", "2"));
            };
            
        }
        else{
            switch(tk.substring(0, 1)){
                case "2":
                    selectPair(new PairCards("2","2"));
                    this.seleccionadas.add(new PairCards("2", "2"));
                case "3":
                    selectPair(new PairCards("3","3"));
                    this.seleccionadas.add(new PairCards("3", "3"));
                case "4":
                    selectPair(new PairCards("4","4"));
                    this.seleccionadas.add(new PairCards("4", "4"));
                case "5":
                    selectPair(new PairCards("5","5"));
                    this.seleccionadas.add(new PairCards("5", "5"));
                case "6":
                    selectPair(new PairCards("6","6"));
                    this.seleccionadas.add(new PairCards("6", "6"));
                case "7":
                    selectPair(new PairCards("7","7"));
                    this.seleccionadas.add(new PairCards("7", "7"));
                case "8":
                    selectPair(new PairCards("8","8"));
                    this.seleccionadas.add(new PairCards("8", "8"));
                case "9":
                    selectPair(new PairCards("9","9"));
                    this.seleccionadas.add(new PairCards("9", "9"));
                case "T":
                    selectPair(new PairCards("T","T"));
                    this.seleccionadas.add(new PairCards("T", "T"));
                case "J":
                    selectPair(new PairCards("J","J"));
                    this.seleccionadas.add(new PairCards("J", "J"));
                case "Q":
                    selectPair(new PairCards("Q", "Q"));
                    this.seleccionadas.add(new PairCards("Q", "Q"));
                case "K":
                    selectPair(new PairCards("K", "K"));
                    this.seleccionadas.add(new PairCards("K", "K"));
                case "A":
                    selectPair(new PairCards("A", "A"));
                    this.seleccionadas.add(new PairCards("A", "A"));

            };

        }

    }

    private void intervalo(String tk, int i) {
        if (i == 1) {
            PairCards pc1 = new PairCards(tk.substring(0, 1), tk.substring(1, 2));
            PairCards pc2 = new PairCards(tk.substring(3, 4), tk.substring(4, 5));
            selectPair(pc1);
            selectPair(pc2);
            Card c1 = pc1.getC2();
            Card c2 = pc2.getC2();
            while (c1.compareTo(c2) > 0) {
                Card c = c2.next();
                PairCards pcTemp = new PairCards(c.getRepresentation(), c.getRepresentation());
                selectPair(pcTemp);
                c2 = c;
            }
        } else if (i == 2) {
            if (tk.substring(2, 3).equals("-")) {
                PairCards pc1 = new PairCards(tk.substring(0, 1), tk.substring(1, 2));
                PairCards pc2 = new PairCards(tk.substring(3, 4), tk.substring(4, 5), tk.substring(5, 6));
                selectPair(pc1);
                selectPair(pc2);
                Card c1 = pc1.getC2();
                Card c2 = pc2.getC2();
                while (c1.compareTo(c2) > 0) {
                    Card c = c2.next();
                    PairCards pcTemp = new PairCards(pc2.getC1().getRepresentation(), c.getRepresentation(), tk.substring(5, 6));
                    selectPair(pcTemp);
                    c2 = c;
                }
            } else {
                PairCards pc1 = new PairCards(tk.substring(0, 1), tk.substring(1, 2), tk.substring(2, 3));
                PairCards pc2 = new PairCards(tk.substring(4, 5), tk.substring(5, 6));
                selectPair(pc1);
                selectPair(pc2);
                Card c1 = pc1.getC2();
                Card c2 = pc2.getC2();
                while (c1.compareTo(c2) > 0) {
                    Card c = c2.next();
                    PairCards pcTemp = new PairCards(pc2.getC1().getRepresentation(), c.getRepresentation(), tk.substring(2, 3));
                    selectPair(pcTemp);
                    c2 = c;
                }

            }
        } else if (i == 3) {
            PairCards pc1 = new PairCards(tk.substring(0, 1), tk.substring(1, 2), tk.substring(2, 3));
            PairCards pc2 = new PairCards(tk.substring(4, 5), tk.substring(5, 6), tk.substring(6, 7));
            selectPair(pc1);
            selectPair(pc2);
            Card c1 = pc1.getC2();
            Card c2 = pc2.getC2();
            while (c1.compareTo(c2) > 0) {
                Card c = c2.next();
                PairCards pcTemp = new PairCards(pc2.getC1().getRepresentation(), c.getRepresentation(), tk.substring(2, 3));
                selectPair(pcTemp);
                c2 = c;
            }
        }
    }

    private void masMenosOS(String tk, boolean b) {
        if (b) {
            PairCards pc = new PairCards(tk.substring(0, 1), tk.substring(1, 2), tk.substring(2, 3));
            selectPair(pc);
            while (pc.getC1().compareTo(pc.getC2()) != 1) {
                Card c = pc.getC2().next();
                PairCards pcTemp = new PairCards(tk.substring(0, 1), c.getRepresentation(), tk.substring(2, 3));
                selectPair(pcTemp);
                pc.setC2(c);
            }
        } else if (!b) {

        }
    }

    private void parserPS() throws IOException {
        if (this.lee != null) {

            String line = this.lee.readLine();
            if (line != null) {
                StringTokenizer st = new StringTokenizer(line, " ");
                for (int i = 0; i < 2; i++) {
                    st.nextToken();
                }
                line = st.nextToken();
                this.psHandNumber.setText(line.substring(1, line.length() - 1));
                line = this.lee.readLine();
                st = new StringTokenizer(line, "#");
                st.nextToken();
                line = st.nextToken();
                int button = Integer.parseInt(line.substring(0, 1));
                line = this.lee.readLine();
                st = new StringTokenizer(line, ":(");
                String seat = st.nextToken();
                ArrayList<Player> players = new ArrayList<>();
          //System.out.println(seat);

                while (seat.substring(0, 4).equals("Seat")) {
                    String name = st.nextToken();
                    //System.out.println(name);
                    Player p = new Player(Integer.parseInt(seat.substring(seat.length() - 1, seat.length())), name.substring(1, name.length() - 1));
                    players.add(p);
                    line = this.lee.readLine();
                    st = new StringTokenizer(line, ":(");
                    seat = st.nextToken();
                }

                while (players.get(0).getSeat() != button) {
                    players.add(players.remove(0));
                }

                for (int i = 0; i < 3; i++) {
                    players.add(players.remove(0));
                }

                this.psNPlayers.setText(String.valueOf(players.size()));
                //this.psPosition.setText(this.textPlayerName.getText());
                this.psName.setText(this.textPlayerName.getText());
                int position = 5;
                int posPlayer = 0;

                String cartasRango;
                RangoOR rng;

                if (this.rangoPS.getSelectedIndex() == 0) {
                    rng = this.janda;
                } else {
                    rng = this.willMa;
                }
                for (int i = players.size() - 1; i >= 0; i--) {
                    Player player = players.get(i);
                    if (player.getName().equals(this.textPlayerName.getText())) {
                        posPlayer = i;
                    }

                    switch (position) {
                        case 5:
                            player.setPosition(Position.BB);
                            break;
                        case 4:
                            player.setPosition(Position.SB);
                            break;
                        case 3:
                            player.setPosition(Position.BTN);
                            break;
                        case 2:
                            player.setPosition(Position.CO);
                            break;
                        case 1:
                            player.setPosition(Position.MP);
                            break;
                        default:
                            player.setPosition(Position.UTG);
                            break;
                    }
                    position--;
                }

                switch (players.get(posPlayer).getPosition()) {
                    case BB:
                        this.psPosition.setText("BB");
                        cartasRango = rng.getBB();
                        break;
                    case SB:
                        cartasRango = rng.getSB();
                        this.psPosition.setText("SB");
                        break;
                    case BTN:
                        cartasRango = rng.getBTN();
                        this.psPosition.setText("BTN");
                        break;
                    case CO:
                        cartasRango = rng.getCO();
                        this.psPosition.setText("CO");
                        break;
                    case MP:
                        cartasRango = rng.getMP();
                        this.psPosition.setText("MP");
                        break;
                    default:
                        cartasRango = rng.getUTG();
                        this.psPosition.setText("UTG");
                        break;
                }
                while (!line.equals("*** HOLE CARDS ***")) {
                    line = this.lee.readLine();
                }
                //this.lee.readLine();
                line = this.lee.readLine();

                st = new StringTokenizer(line, "[");
                st.nextToken();

                String cards = st.nextToken();
                System.out.println(cards);
                PairCards pc = converttoPC(cards.substring(0, 1),
                        cards.substring(1, 2),
                        cards.substring(3, 4),
                        cards.substring(4, 5));

                this.psCards.setText(pc.toString());
                boolean valido = true;

                

                for (int i = 0; i < posPlayer; i++) {
                    String folds;
                    /*try {
                        line = this.lee.readLine();
                        st = new StringTokenizer(line, ":");
                        st.nextToken();
                        folds = st.nextToken();
                    } catch (java.util.NoSuchElementException e) {
                        boolean leaves = false;
                        while (!leaves) {
                            line = this.lee.readLine();
                            StringTokenizer sttry = new StringTokenizer(line, " ");
                            sttry.nextToken();
                            if (!sttry.nextToken().equals("leaves")) {
                                leaves = true;
                            }
                        }

                    }*/
                    line = this.lee.readLine();
                    st = new StringTokenizer(line, " ");
                    String token = st.nextToken();
                    while(token.equals(players.get(i).getName()+":")){
                        line = this.lee.readLine();
                        st = new StringTokenizer(line, " ");
                        token = st.nextToken();
                    }

                    folds = st.nextToken();

                    if (!folds.equals("folds")) {
                        valido = false;
                    }
                }

                if (valido && players.get(posPlayer).getPosition() != Position.BB) {
                    line = this.lee.readLine();
                    st = new StringTokenizer(line, ":");
                    st.nextToken();
                    try{
                    String folds = st.nextToken();
                    }catch(java.util.NoSuchElementException e){
                        boolean leaves = false;
                        while (!leaves) {
                            line = this.lee.readLine();
                            StringTokenizer sttry = new StringTokenizer(line, " ");
                            sttry.nextToken();
                            if (!sttry.nextToken().equals("leaves")) {
                                leaves = true;
                            }
                        }
                        
                    }
                    st = new StringTokenizer(line, ":");
                    st.nextToken();
                    String folds = st.nextToken();
                    deseleccionar();
                    StringTokenizer str = new StringTokenizer(cartasRango, ",");
                    while (str.hasMoreTokens()) {
                        String tk = str.nextToken();
                        if (tk.length() == 2) {
                            selectPair(new PairCards(tk.substring(0, 1), tk.substring(1, 2)));
                        } else if (tk.length() == 3) {
                            switch (tk.substring(2, 3)) {
                                case "+": {
                                    masMenos(tk, true);
                                    break;
                                }
                                case "-": {
                                    masMenos(tk, false);
                                    break;
                                }
                                default:
                                    selectPair(new PairCards(tk.substring(0, 1), tk.substring(1, 2), tk.substring(2, 3)));
                            }
                            //selectPair(new PairCards(tk.substring(0,1), tk.substring(1,2), tk.substring(2,3)));
                        } else if (tk.length() == 4) {
                            switch (tk.substring(3, 4)) {
                                case "+":
                                    masMenosOS(tk, true);
                                    break;
                            }
                        } else if (tk.length() == 5) {
                            intervalo(tk, 1);
                        } else if (tk.length() == 6) {
                            intervalo(tk, 2);
                        } else if (tk.length() == 7) {
                            intervalo(tk, 3);
                        }

                    }

                    boolean or = false;
                    if (!folds.equals(" folds ")) {
                        or = true;
                        this.psAction.setText("OR");
                    } else {
                        this.psAction.setText("Fold");
                    }
                    if (isSelected(pc)) {
                        if (or) {
                            this.psResutl.setText("<html><font color='green'>CORRECTO</font></html>");
                        } else {
                            this.psResutl.setText("<html><font color='red'>ERRONEO</font></html>");
                        }
                    } else {
                        if (!or) {
                            this.psResutl.setText("<html><font color='green'>CORRECTO</font></html>");
                        } else {
                            this.psResutl.setText("<html><font color='red'>ERRONEO</font></html>");
                        }
                    }

                } else {
                    this.psResutl.setText("No soportado");
                    deseleccionar();
                }

                line = this.lee.readLine();

                while (!line.equals("")) {
                    line = this.lee.readLine();
                }
                for (int i = 0; i < 2; i++) {
                    this.lee.readLine();
                }
            }else{
               JOptionPane.showMessageDialog(null, "No hay más manos que analizar");
               this.lee.close();
               deseleccionar();
               this.psAction.setText("");
               this.psCards.setText("");
               this.psHandNumber.setText("");
               this.psNPlayers.setText("");
               this.psName.setText("");
               this.psPosition.setText("");
               this.psResutl.setText("");
               this.nextButton.setEnabled(false);
               this.archivoLabel.setText("");
            }
        }
    }

    private PairCards converttoPC(String n1, String s1, String n2, String s2) {
        if (n1.equals(n2)) {
            return new PairCards(n1, n1);
        } else if (s1.equals(s2)) {
            return new PairCards(n1, n2, "s");
        } else {
            return new PairCards(n1, n2, "o");
        }
    }

    public void setModelRankings(ArrayList<String> names) {
        this.comboRanking.removeAllItems();
        for (String s : names) {
            this.comboRanking.addItem(s);
        }
        this.comboRanking.setSelectedIndex(0);
    }

}
