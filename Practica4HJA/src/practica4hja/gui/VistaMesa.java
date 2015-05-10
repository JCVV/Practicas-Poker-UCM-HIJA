/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica4hja.gui;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import practica4hja.modelo.Card;
import practica4hja.modelo.Deck;
import practica4hja.modelo.EstadoPartida;
import practica4hja.modelo.Hand;
import practica4hja.modelo.HandEvaluator;
import practica4hja.modelo.Player;
import practica4hja.modelo.Statistics;

/**
 *
 * @author jcarlos
 */
public class VistaMesa extends javax.swing.JFrame {

    private final Player player;
    private final Player banca;
    private final Deck deck;
    private final ArrayList<JLabel> cardsBoard;
    private final ArrayList<JLabel> cardsPlayer;
    private final ArrayList<JLabel> cardsBanca;
    private EstadoPartida estado;
    private ArrayList<ArrayList<Card>> arrayRandom;
    private final double[] flopRandomEquity;
    public static double MARCA_PREFLOP;
    public static double MARCA_FLOP;
    public static double MARCA_TURN;
    
    
    
    /**
     * Creates new form VistaMesa
     */
    public VistaMesa() {
        automatico = true;
        MARCA_PREFLOP = (double)4/9;
        MARCA_FLOP = (double)2/9;
        MARCA_TURN = (double)1/9;
        this.rnd = new Random(System.nanoTime());
        String frE = "85.20,67.05,66.21,65.40,64.60,62.78,61.94,60.98,59.91,59.92,59.03,58.22,57.38,65.32,82.40,63.40,62.57,61.79,59.99,58.31,57.54,56.64,55.79,54.89,54.06,53.21,64.43,61.46,79.93,60.26,59.47,57.66,56.02,54.30,53.61,52.77,51.86,51.02,50.17,63.56,60.57,58.14,77.47,57.53,55.66,54.02,52.33,50.61,49.99,49.07,48.23,47.38,62.72,59.40,57.30,55.29,75.01,54.03,52.33,50.64,48.94,47.22,46.53,45.69,44.84,60.77,57.81,55.36,53.25,51.53,72.06,50.80,49.12,47.43,45.72,43.86,43.26,42.42,59.87,56.02,53.60,51.49,49.72,48.10,69.16,47.94,46.24,44.55,42.70,40.87,40.27,58.84,55.19,51.77,49.68,47.91,46.30,45.05,66.24,45.37,43.68,41.85,40.04,38.16,57.68,54.22,51.02,47.84,46.10,44.49,43.24,42.32,63.29,43.13,41.33,39.53,37.67,57.70,53.31,50.12,47.18,44.25,42.67,41.43,40.51,39.94,60.33,41.45,39.69,37.85,56.73,52.33,49.13,46.19,43.50,40.67,39.47,38.55,38.01,38.16,57.02,38.64,36.83,55.85,51.43,48.22,45.28,42.60,40.02,37.48,36.60,36.08,36.27,35.15,53.69,35.98,54.93,50.51,47.30,44.35,41.69,39.10,36.83,34.58,34.08,34.29,33.20,32.30,50.33";
        String frESeparados[] = frE.split(",");
        this.flopRandomEquity = stringToDouble(frESeparados);
        this.RANDOM = "AsAh,AsAd,AsAc,AhAd,AhAc,AdAc,AsKs,AhKh,AcKc,"
                + "AdKd,AsQs,AhQh,AcQc,AdQd,AsJs,AhJh,AcJc,AdJd,AsTs,AhTh,AcTc,AdTd,"
                + "As9s,Ah9h,Ac9c,Ad9d,As8s,Ah8h,Ac8c,Ad8d,As7s,Ah7h,Ac7c,Ad7d,As6s,"
                + "Ah6h,Ac6c,Ad6d,As5s,Ah5h,Ac5c,Ad5d,As4s,Ah4h,Ac4c,Ad4d,As3s,Ah3h,"
                + "Ac3c,Ad3d,As2s,Ah2h,Ac2c,Ad2d,AsKh,AsKc,AsKd,AhKs,AhKc,AhKd,AcKs,"
                + "AcKh,AcKd,AdKs,AdKh,AdKc,KsKh,KsKd,KsKc,KhKd,KhKc,KdKc,KsQs,KhQh,"
                + "KcQc,KdQd,KsJs,KhJh,KcJc,KdJd,KsTs,KhTh,KcTc,KdTd,Ks9s,Kh9h,Kc9c,"
                + "Kd9d,Ks8s,Kh8h,Kc8c,Kd8d,Ks7s,Kh7h,Kc7c,Kd7d,Ks6s,Kh6h,Kc6c,Kd6d,"
                + "Ks5s,Kh5h,Kc5c,Kd5d,Ks4s,Kh4h,Kc4c,Kd4d,Ks3s,Kh3h,Kc3c,Kd3d,Ks2s,"
                + "Kh2h,Kc2c,Kd2d,AsQh,AsQc,AsQd,AhQs,AhQc,AhQd,AcQs,AcQh,AcQd,AdQs,"
                + "AdQh,AdQc,KsQh,KsQc,KsQd,KhQs,KhQc,KhQd,KcQs,KcQh,KcQd,KdQs,KdQh,"
                + "KdQc,QsQh,QsQd,QsQc,QhQd,QhQc,QdQc,QsJs,QhJh,QcJc,QdJd,QsTs,QhTh,"
                + "QcTc,QdTd,Qs9s,Qh9h,Qc9c,Qd9d,Qs8s,Qh8h,Qc8c,Qd8d,Qs7s,Qh7h,Qc7c,"
                + "Qd7d,Qs6s,Qh6h,Qc6c,Qd6d,Qs5s,Qh5h,Qc5c,Qd5d,Qs4s,Qh4h,Qc4c,Qd4d,"
                + "Qs3s,Qh3h,Qc3c,Qd3d,Qs2s,Qh2h,Qc2c,Qd2d,AsJh,AsJc,AsJd,AhJs,AhJc,"
                + "AhJd,AcJs,AcJh,AcJd,AdJs,AdJh,AdJc,KsJh,KsJc,KsJd,KhJs,KhJc,KhJd,"
                + "KcJs,KcJh,KcJd,KdJs,KdJh,KdJc,QsJh,QsJc,QsJd,QhJs,QhJc,QhJd,QcJs,"
                + "QcJh,QcJd,QdJs,QdJh,QdJc,JsJh,JsJd,JsJc,JhJd,JhJc,JdJc,JsTs,JhTh,"
                + "JcTc,JdTd,Js9s,Jh9h,Jc9c,Jd9d,Js8s,Jh8h,Jc8c,Jd8d,Js7s,Jh7h,Jc7c,"
                + "Jd7d,Js6s,Jh6h,Jc6c,Jd6d,Js5s,Jh5h,Jc5c,Jd5d,Js4s,Jh4h,Jc4c,Jd4d,"
                + "Js3s,Jh3h,Jc3c,Jd3d,Js2s,Jh2h,Jc2c,Jd2d,AsTh,AsTc,AsTd,AhTs,AhTc,"
                + "AhTd,AcTs,AcTh,AcTd,AdTs,AdTh,AdTc,KsTh,KsTc,KsTd,KhTs,KhTc,KhTd,"
                + "KcTs,KcTh,KcTd,KdTs,KdTh,KdTc,QsTh,QsTc,QsTd,QhTs,QhTc,QhTd,QcTs,"
                + "QcTh,QcTd,QdTs,QdTh,QdTc,JsTh,JsTc,JsTd,JhTs,JhTc,JhTd,JcTs,JcTh,"
                + "JcTd,JdTs,JdTh,JdTc,TsTh,TsTd,TsTc,ThTd,ThTc,TdTc,Ts9s,Th9h,Tc9c,"
                + "Td9d,Ts8s,Th8h,Tc8c,Td8d,Ts7s,Th7h,Tc7c,Td7d,Ts6s,Th6h,Tc6c,Td6d,"
                + "Ts5s,Th5h,Tc5c,Td5d,Ts4s,Th4h,Tc4c,Td4d,Ts3s,Th3h,Tc3c,Td3d,Ts2s,"
                + "Th2h,Tc2c,Td2d,As9h,As9c,As9d,Ah9s,Ah9c,Ah9d,Ac9s,Ac9h,Ac9d,Ad9s,"
                + "Ad9h,Ad9c,Ks9h,Ks9c,Ks9d,Kh9s,Kh9c,Kh9d,Kc9s,Kc9h,Kc9d,Kd9s,Kd9h,"
                + "Kd9c,Qs9h,Qs9c,Qs9d,Qh9s,Qh9c,Qh9d,Qc9s,Qc9h,Qc9d,Qd9s,Qd9h,Qd9c,"
                + "Js9h,Js9c,Js9d,Jh9s,Jh9c,Jh9d,Jc9s,Jc9h,Jc9d,Jd9s,Jd9h,Jd9c,Ts9h,"
                + "Ts9c,Ts9d,Th9s,Th9c,Th9d,Tc9s,Tc9h,Tc9d,Td9s,Td9h,Td9c,9s9h,9s9d,"
                + "9s9c,9h9d,9h9c,9d9c,9s8s,9h8h,9c8c,9d8d,9s7s,9h7h,9c7c,9d7d,9s6s,"
                + "9h6h,9c6c,9d6d,9s5s,9h5h,9c5c,9d5d,9s4s,9h4h,9c4c,9d4d,9s3s,9h3h,"
                + "9c3c,9d3d,9s2s,9h2h,9c2c,9d2d,As8h,As8c,As8d,Ah8s,Ah8c,Ah8d,Ac8s,"
                + "Ac8h,Ac8d,Ad8s,Ad8h,Ad8c,Ks8h,Ks8c,Ks8d,Kh8s,Kh8c,Kh8d,Kc8s,Kc8h,"
                + "Kc8d,Kd8s,Kd8h,Kd8c,Qs8h,Qs8c,Qs8d,Qh8s,Qh8c,Qh8d,Qc8s,Qc8h,Qc8d,"
                + "Qd8s,Qd8h,Qd8c,Js8h,Js8c,Js8d,Jh8s,Jh8c,Jh8d,Jc8s,Jc8h,Jc8d,Jd8s,"
                + "Jd8h,Jd8c,Ts8h,Ts8c,Ts8d,Th8s,Th8c,Th8d,Tc8s,Tc8h,Tc8d,Td8s,Td8h,"
                + "Td8c,9s8h,9s8c,9s8d,9h8s,9h8c,9h8d,9c8s,9c8h,9c8d,9d8s,9d8h,9d8c,"
                + "8s8h,8s8d,8s8c,8h8d,8h8c,8d8c,8s7s,8h7h,8c7c,8d7d,8s6s,8h6h,8c6c,"
                + "8d6d,8s5s,8h5h,8c5c,8d5d,8s4s,8h4h,8c4c,8d4d,8s3s,8h3h,8c3c,8d3d,"
                + "8s2s,8h2h,8c2c,8d2d,As7h,As7c,As7d,Ah7s,Ah7c,Ah7d,Ac7s,Ac7h,Ac7d,"
                + "Ad7s,Ad7h,Ad7c,Ks7h,Ks7c,Ks7d,Kh7s,Kh7c,Kh7d,Kc7s,Kc7h,Kc7d,Kd7s,"
                + "Kd7h,Kd7c,Qs7h,Qs7c,Qs7d,Qh7s,Qh7c,Qh7d,Qc7s,Qc7h,Qc7d,Qd7s,Qd7h,"
                + "Qd7c,Js7h,Js7c,Js7d,Jh7s,Jh7c,Jh7d,Jc7s,Jc7h,Jc7d,Jd7s,Jd7h,Jd7c,"
                + "Ts7h,Ts7c,Ts7d,Th7s,Th7c,Th7d,Tc7s,Tc7h,Tc7d,Td7s,Td7h,Td7c,9s7h,"
                + "9s7c,9s7d,9h7s,9h7c,9h7d,9c7s,9c7h,9c7d,9d7s,9d7h,9d7c,8s7h,8s7c,"
                + "8s7d,8h7s,8h7c,8h7d,8c7s,8c7h,8c7d,8d7s,8d7h,8d7c,7s7h,7s7d,7s7c,"
                + "7h7d,7h7c,7d7c,7s6s,7h6h,7c6c,7d6d,7s5s,7h5h,7c5c,7d5d,7s4s,7h4h,"
                + "7c4c,7d4d,7s3s,7h3h,7c3c,7d3d,7s2s,7h2h,7c2c,7d2d,As6h,As6c,As6d,"
                + "Ah6s,Ah6c,Ah6d,Ac6s,Ac6h,Ac6d,Ad6s,Ad6h,Ad6c,Ks6h,Ks6c,Ks6d,Kh6s,"
                + "Kh6c,Kh6d,Kc6s,Kc6h,Kc6d,Kd6s,Kd6h,Kd6c,Qs6h,Qs6c,Qs6d,Qh6s,Qh6c,"
                + "Qh6d,Qc6s,Qc6h,Qc6d,Qd6s,Qd6h,Qd6c,Js6h,Js6c,Js6d,Jh6s,Jh6c,Jh6d,"
                + "Jc6s,Jc6h,Jc6d,Jd6s,Jd6h,Jd6c,Ts6h,Ts6c,Ts6d,Th6s,Th6c,Th6d,Tc6s,"
                + "Tc6h,Tc6d,Td6s,Td6h,Td6c,9s6h,9s6c,9s6d,9h6s,9h6c,9h6d,9c6s,9c6h,"
                + "9c6d,9d6s,9d6h,9d6c,8s6h,8s6c,8s6d,8h6s,8h6c,8h6d,8c6s,8c6h,8c6d,"
                + "8d6s,8d6h,8d6c,7s6h,7s6c,7s6d,7h6s,7h6c,7h6d,7c6s,7c6h,7c6d,7d6s,"
                + "7d6h,7d6c,6s6h,6s6d,6s6c,6h6d,6h6c,6d6c,6s5s,6h5h,6c5c,6d5d,6s4s,"
                + "6h4h,6c4c,6d4d,6s3s,6h3h,6c3c,6d3d,6s2s,6h2h,6c2c,6d2d,As5h,As5c,"
                + "As5d,Ah5s,Ah5c,Ah5d,Ac5s,Ac5h,Ac5d,Ad5s,Ad5h,Ad5c,Ks5h,Ks5c,Ks5d,"
                + "Kh5s,Kh5c,Kh5d,Kc5s,Kc5h,Kc5d,Kd5s,Kd5h,Kd5c,Qs5h,Qs5c,Qs5d,Qh5s,"
                + "Qh5c,Qh5d,Qc5s,Qc5h,Qc5d,Qd5s,Qd5h,Qd5c,Js5h,Js5c,Js5d,Jh5s,Jh5c,"
                + "Jh5d,Jc5s,Jc5h,Jc5d,Jd5s,Jd5h,Jd5c,Ts5h,Ts5c,Ts5d,Th5s,Th5c,Th5d,"
                + "Tc5s,Tc5h,Tc5d,Td5s,Td5h,Td5c,9s5h,9s5c,9s5d,9h5s,9h5c,9h5d,9c5s,"
                + "9c5h,9c5d,9d5s,9d5h,9d5c,8s5h,8s5c,8s5d,8h5s,8h5c,8h5d,8c5s,8c5h,"
                + "8c5d,8d5s,8d5h,8d5c,7s5h,7s5c,7s5d,7h5s,7h5c,7h5d,7c5s,7c5h,7c5d,"
                + "7d5s,7d5h,7d5c,6s5h,6s5c,6s5d,6h5s,6h5c,6h5d,6c5s,6c5h,6c5d,6d5s,"
                + "6d5h,6d5c,5s5h,5s5d,5s5c,5h5d,5h5c,5d5c,5s4s,5h4h,5c4c,5d4d,5s3s,"
                + "5h3h,5c3c,5d3d,5s2s,5h2h,5c2c,5d2d,As4h,As4c,As4d,Ah4s,Ah4c,Ah4d,"
                + "Ac4s,Ac4h,Ac4d,Ad4s,Ad4h,Ad4c,Ks4h,Ks4c,Ks4d,Kh4s,Kh4c,Kh4d,Kc4s,"
                + "Kc4h,Kc4d,Kd4s,Kd4h,Kd4c,Qs4h,Qs4c,Qs4d,Qh4s,Qh4c,Qh4d,Qc4s,Qc4h,"
                + "Qc4d,Qd4s,Qd4h,Qd4c,Js4h,Js4c,Js4d,Jh4s,Jh4c,Jh4d,Jc4s,Jc4h,Jc4d,"
                + "Jd4s,Jd4h,Jd4c,Ts4h,Ts4c,Ts4d,Th4s,Th4c,Th4d,Tc4s,Tc4h,Tc4d,Td4s,"
                + "Td4h,Td4c,9s4h,9s4c,9s4d,9h4s,9h4c,9h4d,9c4s,9c4h,9c4d,9d4s,9d4h,"
                + "9d4c,8s4h,8s4c,8s4d,8h4s,8h4c,8h4d,8c4s,8c4h,8c4d,8d4s,8d4h,8d4c,"
                + "7s4h,7s4c,7s4d,7h4s,7h4c,7h4d,7c4s,7c4h,7c4d,7d4s,7d4h,7d4c,6s4h,"
                + "6s4c,6s4d,6h4s,6h4c,6h4d,6c4s,6c4h,6c4d,6d4s,6d4h,6d4c,5s4h,5s4c,"
                + "5s4d,5h4s,5h4c,5h4d,5c4s,5c4h,5c4d,5d4s,5d4h,5d4c,4s4h,4s4d,4s4c,"
                + "4h4d,4h4c,4d4c,4s3s,4h3h,4c3c,4d3d,4s2s,4h2h,4c2c,4d2d,As3h,As3c,"
                + "As3d,Ah3s,Ah3c,Ah3d,Ac3s,Ac3h,Ac3d,Ad3s,Ad3h,Ad3c,Ks3h,Ks3c,Ks3d,"
                + "Kh3s,Kh3c,Kh3d,Kc3s,Kc3h,Kc3d,Kd3s,Kd3h,Kd3c,Qs3h,Qs3c,Qs3d,Qh3s,"
                + "Qh3c,Qh3d,Qc3s,Qc3h,Qc3d,Qd3s,Qd3h,Qd3c,Js3h,Js3c,Js3d,Jh3s,Jh3c,"
                + "Jh3d,Jc3s,Jc3h,Jc3d,Jd3s,Jd3h,Jd3c,Ts3h,Ts3c,Ts3d,Th3s,Th3c,Th3d,"
                + "Tc3s,Tc3h,Tc3d,Td3s,Td3h,Td3c,9s3h,9s3c,9s3d,9h3s,9h3c,9h3d,9c3s,"
                + "9c3h,9c3d,9d3s,9d3h,9d3c,8s3h,8s3c,8s3d,8h3s,8h3c,8h3d,8c3s,8c3h,"
                + "8c3d,8d3s,8d3h,8d3c,7s3h,7s3c,7s3d,7h3s,7h3c,7h3d,7c3s,7c3h,7c3d,"
                + "7d3s,7d3h,7d3c,6s3h,6s3c,6s3d,6h3s,6h3c,6h3d,6c3s,6c3h,6c3d,6d3s,"
                + "6d3h,6d3c,5s3h,5s3c,5s3d,5h3s,5h3c,5h3d,5c3s,5c3h,5c3d,5d3s,5d3h,"
                + "5d3c,4s3h,4s3c,4s3d,4h3s,4h3c,4h3d,4c3s,4c3h,4c3d,4d3s,4d3h,4d3c,"
                + "3s3h,3s3d,3s3c,3h3d,3h3c,3d3c,3s2s,3h2h,3c2c,3d2d,As2h,As2c,As2d,"
                + "Ah2s,Ah2c,Ah2d,Ac2s,Ac2h,Ac2d,Ad2s,Ad2h,Ad2c,Ks2h,Ks2c,Ks2d,Kh2s,"
                + "Kh2c,Kh2d,Kc2s,Kc2h,Kc2d,Kd2s,Kd2h,Kd2c,Qs2h,Qs2c,Qs2d,Qh2s,Qh2c,"
                + "Qh2d,Qc2s,Qc2h,Qc2d,Qd2s,Qd2h,Qd2c,Js2h,Js2c,Js2d,Jh2s,Jh2c,Jh2d,"
                + "Jc2s,Jc2h,Jc2d,Jd2s,Jd2h,Jd2c,Ts2h,Ts2c,Ts2d,Th2s,Th2c,Th2d,Tc2s,"
                + "Tc2h,Tc2d,Td2s,Td2h,Td2c,9s2h,9s2c,9s2d,9h2s,9h2c,9h2d,9c2s,9c2h,"
                + "9c2d,9d2s,9d2h,9d2c,8s2h,8s2c,8s2d,8h2s,8h2c,8h2d,8c2s,8c2h,8c2d,"
                + "8d2s,8d2h,8d2c,7s2h,7s2c,7s2d,7h2s,7h2c,7h2d,7c2s,7c2h,7c2d,7d2s,"
                + "7d2h,7d2c,6s2h,6s2c,6s2d,6h2s,6h2c,6h2d,6c2s,6c2h,6c2d,6d2s,6d2h,"
                + "6d2c,5s2h,5s2c,5s2d,5h2s,5h2c,5h2d,5c2s,5c2h,5c2d,5d2s,5d2h,5d2c,"
                + "4s2h,4s2c,4s2d,4h2s,4h2c,4h2d,4c2s,4c2h,4c2d,4d2s,4d2h,4d2c,3s2h,"
                + "3s2c,3s2d,3h2s,3h2c,3h2d,3c2s,3c2h,3c2d,3d2s,3d2h,3d2c,2s2h,2s2d,"
                + "2s2c,2h2d,2h2c,2d2c";
        this.arrayRandom = new ArrayList<>();
        String separados[] = this.RANDOM.split(",");
                int x = 0;
                //this.arrayRandom.add(new ArrayList<Card>());
                for(String s : separados){
                    this.arrayRandom.add(new ArrayList<Card>());
                    this.arrayRandom.get(x).add(new Card(s.substring(0,1), s.substring(1,2)));
                    this.arrayRandom.get(x).add(new Card(s.substring(2,3), s.substring(3,4)));
                    x++;
                }
        
        initComponents();
    
        this.deck = new Deck();
        this.estado = EstadoPartida.FINAL;
        this.player = new Player("Jose", 1000);
        this.banca = new Player();
        this.cardsBoard = new ArrayList<>(5);
        this.cardsBoard.add(cardP3);
        this.cardsBoard.add(cardP4);
        this.cardsBoard.add(cardP5);
        this.cardsBoard.add(cardP6);
        this.cardsBoard.add(cardP7);
        this.cardsPlayer = new ArrayList<>(2);
        this.cardsPlayer.add(cardP1);
        this.cardsPlayer.add(cardP2);
        this.cardsBanca = new ArrayList<>(2);
        this.cardsBanca.add(cardB1);
        this.cardsBanca.add(cardB2);
    
        
        
        this.seguirBoton.setEnabled(false);
        this.retirarseBoton.setEnabled(false);
        actualizarInfo();
        /*
        Statistics stPlayer = new Statistics(1);
        Statistics stBanca = new Statistics(0);
        ArrayList<Statistics> st;
        st = new ArrayList<>();
        st.add(stBanca);
        st.add(stPlayer);
        //this.player.hand(new Card("8","h"), new Card("5","d"));
        this.player.hand(this.deck.deal(),this.deck.deal());
        for(Card c : this.player.getCards())
            Deck.disponible.put(c.getNum52(), false);
        
        System.out.println(this.player.getCards());
        ArrayList<Card> board = new ArrayList<>();
        //board.add(new Card("9","h"));
        for(Card c : board)
            Deck.disponible.put(c.getNum52(), false);
        
        System.out.println(pokerStove(board, st));
        System.out.println(equityHandVsRandom(this.player.getCards()));*/
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        seguirBoton = new javax.swing.JButton();
        retirarseBoton = new javax.swing.JButton();
        nextStepB = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cardB1 = new javax.swing.JLabel();
        cardB2 = new javax.swing.JLabel();
        cardP1 = new javax.swing.JLabel();
        cardP2 = new javax.swing.JLabel();
        panelBoard = new javax.swing.JPanel();
        cardP7 = new javax.swing.JLabel();
        cardP6 = new javax.swing.JLabel();
        cardP5 = new javax.swing.JLabel();
        cardP4 = new javax.swing.JLabel();
        cardP3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textoEstadisticas = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nuevoJuegoBoton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        equityLabel = new javax.swing.JLabel();
        estadoGui = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        simularMenu = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        resetPlayer = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        seguirBoton.setBackground(new java.awt.Color(2, 216, 33));
        seguirBoton.setText("Seguir");
        seguirBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seguirBotonActionPerformed(evt);
            }
        });
        getContentPane().add(seguirBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 146, 106));

        retirarseBoton.setBackground(new java.awt.Color(253, 55, 55));
        retirarseBoton.setText("Retirarse");
        retirarseBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retirarseBotonActionPerformed(evt);
            }
        });
        getContentPane().add(retirarseBoton, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 430, 146, 106));

        nextStepB.setBackground(new java.awt.Color(83, 159, 254));
        nextStepB.setText("Next Step");
        nextStepB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextStepBActionPerformed(evt);
            }
        });
        getContentPane().add(nextStepB, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 146, 62));

        jLabel1.setText("BANCA:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, -1));

        cardB1.setText("      ");
        getContentPane().add(cardB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 81, -1));

        cardB2.setText("      ");
        getContentPane().add(cardB2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 80, -1));

        cardP1.setText("      ");
        getContentPane().add(cardP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 430, 80, -1));

        cardP2.setText("      ");
        getContentPane().add(cardP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 430, 80, -1));

        cardP7.setText("      ");

        cardP6.setText("      ");

        cardP5.setText("      ");

        cardP4.setText("      ");

        cardP3.setText("      ");

        javax.swing.GroupLayout panelBoardLayout = new javax.swing.GroupLayout(panelBoard);
        panelBoard.setLayout(panelBoardLayout);
        panelBoardLayout.setHorizontalGroup(
            panelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
            .addGroup(panelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelBoardLayout.createSequentialGroup()
                    .addGap(52, 52, 52)
                    .addComponent(cardP3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(cardP4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(cardP5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(cardP6, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(cardP7, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(55, Short.MAX_VALUE)))
        );
        panelBoardLayout.setVerticalGroup(
            panelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
            .addGroup(panelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelBoardLayout.createSequentialGroup()
                    .addGap(51, 51, 51)
                    .addGroup(panelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cardP3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cardP4)
                        .addComponent(cardP5)
                        .addComponent(cardP6)
                        .addComponent(cardP7))
                    .addContainerGap(62, Short.MAX_VALUE)))
        );

        panelBoardLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cardP3, cardP4, cardP5, cardP6, cardP7});

        getContentPane().add(panelBoard, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 560, 230));

        textoEstadisticas.setEditable(false);
        textoEstadisticas.setColumns(15);
        textoEstadisticas.setRows(5);
        jScrollPane1.setViewportView(textoEstadisticas);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 40, 230, 370));

        jLabel2.setText("     ");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 540, -1, -1));

        jLabel3.setText("     ");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 450, -1, -1));

        nuevoJuegoBoton1.setText("Nuevo Juego");
        nuevoJuegoBoton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoJuegoBoton1ActionPerformed(evt);
            }
        });
        getContentPane().add(nuevoJuegoBoton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 12, 146, 62));

        jLabel4.setText("Equity:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        equityLabel.setText("  ");
        getContentPane().add(equityLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, -1, -1));

        estadoGui.setText("          ");
        getContentPane().add(estadoGui, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        jMenu1.setText("File");

        jMenuItem1.setText("Ingresar Fondos");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        simularMenu.setText("Simular Partidas");
        simularMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simularMenuActionPerformed(evt);
            }
        });
        jMenu1.add(simularMenu);

        jMenuItem3.setText("Simular Steps");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Configuracion");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        resetPlayer.setText("Reset");
        resetPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetPlayerActionPerformed(evt);
            }
        });
        jMenu1.add(resetPlayer);

        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void seguirBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seguirBotonActionPerformed
        // TODO add your handling code here:
        switch(this.estado){
            case PREFLOP:
                    this.estado = EstadoPartida.FLOP;
                    this.dealCards();
                break;
            case FLOP:
               
                    this.estado = EstadoPartida.TURN;
                    this.dealCards();
                break;
            case TURN:
                
                    this.estado = EstadoPartida.RIVER;
                    this.dealCards();
                break;
            
            case RIVER:
                    for(int i = 0; i < 2; i++){
                        this.drawCard(this.banca.getC(i).getPath(),this.cardsBanca.get(i));
                    }
                    this.estado = EstadoPartida.FINAL;
                    this.retirarseBoton.setEnabled(false);
                break;
            case FINAL:
                this.resultado();
                break;
        }
        actualizarInfo();
    }//GEN-LAST:event_seguirBotonActionPerformed

    private void retirarseBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retirarseBotonActionPerformed
        // TODO add your handling code here:

        switch(this.estado){
            case PREFLOP:
                this.player.retPreFlop();
                break;
            case FLOP:
                this.player.retFlop();
                break;
            case TURN:
                this.player.retTurn();
                break;
                
        }
        this.player.retirado();
        limpiar();
        this.estado = EstadoPartida.FINAL;
        actualizarInfo();

    }//GEN-LAST:event_retirarseBotonActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        try {
        int i = Integer.parseInt(JOptionPane.showInputDialog("Introduzca la cantidad a ingresar"));
        this.player.gana(i);
        this.actualizarInfo();
        } catch(NumberFormatException e){}
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    Semaphore sem1=new Semaphore(0);
    private void simularMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simularMenuActionPerformed
        // TODO add your handling code here:
        //try catch!!!
        try {
        final int i = Integer.parseInt(JOptionPane.showInputDialog("Introduzca la cantidad de simulaciones"));
        this.automatico = true;
        new Thread(new Runnable() {

            @Override
            public void run() {
                simulacion(i);
            }
        }).start();
        } catch(NumberFormatException e){}
        
    }//GEN-LAST:event_simularMenuActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void nuevoJuegoBoton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoJuegoBoton1ActionPerformed
        // TODO add your handling code here:
        this.nuevoJuegoBoton1.setEnabled(false);
        this.nextStepB.setEnabled(false);
        this.seguirBoton.setEnabled(true);
        this.retirarseBoton.setEnabled(true);
        
        if(this.estado == EstadoPartida.FINAL){
            this.estado = EstadoPartida.PREFLOP;
            dealCards();
        }
        
        actualizarInfo();
    }//GEN-LAST:event_nuevoJuegoBoton1ActionPerformed

    private void nextStepBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextStepBActionPerformed
        // TODO add your handling code here:
        if(sem1.getQueueLength()>0)
        sem1.release();
    }//GEN-LAST:event_nextStepBActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:

        final int i = Integer.parseInt(JOptionPane.showInputDialog("Introduzca la cantidad de simulaciones"));
        this.automatico=false;
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                simulacion(i);
            }
        }).start();
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void resetPlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetPlayerActionPerformed
        // TODO add your handling code here:
        this.player.reset();
        actualizarInfo();
    }//GEN-LAST:event_resetPlayerActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        Configuration conf = new Configuration(this.MARCA_PREFLOP, this.MARCA_FLOP, this.MARCA_TURN);
        conf.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaMesa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaMesa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaMesa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaMesa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
*/
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VistaMesa().setVisible(true);
            }
        });
    }

    
    private void drawCard(String path, JLabel label) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        int scale = 8;
        int width = icon.getIconWidth() / scale;
        int height = icon.getIconHeight() / scale;
        g.drawImage(img, 0, 0, width, height, null);
        ImageIcon newIcon = new ImageIcon(bi);
        label.setIcon(newIcon);
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cardB1;
    private javax.swing.JLabel cardB2;
    private javax.swing.JLabel cardP1;
    private javax.swing.JLabel cardP2;
    private javax.swing.JLabel cardP3;
    private javax.swing.JLabel cardP4;
    private javax.swing.JLabel cardP5;
    private javax.swing.JLabel cardP6;
    private javax.swing.JLabel cardP7;
    private javax.swing.JLabel equityLabel;
    private javax.swing.JLabel estadoGui;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton nextStepB;
    private javax.swing.JButton nuevoJuegoBoton1;
    private javax.swing.JPanel panelBoard;
    private javax.swing.JMenuItem resetPlayer;
    private javax.swing.JButton retirarseBoton;
    private javax.swing.JButton seguirBoton;
    private javax.swing.JMenuItem simularMenu;
    private javax.swing.JTextArea textoEstadisticas;
    // End of variables declaration//GEN-END:variables

    private void dealCards() {
        switch(this.estado){
            case PREFLOP:
                this.banca.hand(deck.deal(), deck.deal());
                this.player.hand(deck.deal(), deck.deal());
                this.drawCard(this.player.getC1().getPath(), this.cardsPlayer.get(0));
                this.drawCard(this.player.getC2().getPath(), this.cardsPlayer.get(1));
                break;
            case FLOP:
                for (int i = 0; i < 3; i++) {
                    Card c = deck.deal();
                    this.player.hand(c);
                    this.banca.hand(c);
                    this.drawCard(c.getPath(), this.cardsBoard.get(i));
                }
                break;
            case TURN:
                Card c = deck.deal();
                this.player.hand(c);
                this.banca.hand(c);
                this.drawCard(c.getPath(), this.cardsBoard.get(3));
                break;
            
            case RIVER:
                Card c2 = deck.deal();
                this.player.hand(c2);
                this.banca.hand(c2);
                this.drawCard(c2.getPath(), this.cardsBoard.get(4));
                break;
        }
        this.player.apuesta(this.estado);
            
    }
    
    private void resultado(){
        HandEvaluator hePlayer = new HandEvaluator(this.player.getCards());
        HandEvaluator heBanca = new HandEvaluator(this.banca.getCards());
        
        int result = hePlayer.compareTo(heBanca);
        
        String cadena = "\nBanca: " + heBanca.toString() + "\n" + "Player: " + hePlayer.toString() + "\n";
        
        if(result>0){
            this.player.pierde();
            JOptionPane.showMessageDialog(null, "\t\t                                 PIERDE\n" + cadena);
        }else if(result == 0){
            this.player.empata();
            this.player.empata5();
            JOptionPane.showMessageDialog(null, "\t\t                                 EMPATA\n" + cadena);
        }else{
            this.player.gana();
            if(hePlayer.getRank()<4)
                this.player.gana9();
            else
                this.player.gana10();
            JOptionPane.showMessageDialog(null, "\t\t                                 GANA\n" + cadena);
        }
        limpiar();
        
    }
    
    private void limpiar(){
    
        this.player.cleanCartas();
        this.banca.cleanCartas();
        for(JLabel jl : this.cardsBanca)
            jl.setIcon(null);
        for(JLabel jl : this.cardsBoard)
            jl.setIcon(null);
        for(JLabel jl : this.cardsPlayer)
            jl.setIcon(null);
        
        this.seguirBoton.setEnabled(false);
        this.nuevoJuegoBoton1.setEnabled(true);
        this.nextStepB.setEnabled(true);
        
        this.deck.reset();
    }
    private void actualizarInfo() {
        String cadena = " " + this.player.getName() + "\n -Fondos: " + this.player.getFondos() + 
                "\n -Ganadas: " + this.player.getGanados() +
                "\n -Empatadas: " + this.player.getEmpatados() +
                "\n -Perdidas: " + this.player.getPerdidos() +
                "\n -Retiradas: " + this.player.getRetiradas() +
                "\n    -Preflop: " + this.player.getRetPreFlop() +
                "\n    -Flop: " + this.player.getRetFlop() +
                "\n    -Turn: " + this.player.getRetTurn() +
                "\n -Total: " + this.player.getTotal();
        
        this.textoEstadisticas.setText(cadena);
    }

    private void simulacion(int partidas) {
        int ganadas = 0;
        Statistics stPlayer = new Statistics(1);
        Statistics stBanca = new Statistics(0);
        ArrayList<Statistics> st;
        st = new ArrayList<>();
        st.add(stBanca);
        st.add(stPlayer);
        ArrayList<Card> dosBancaInicio = new ArrayList<>();
        for(int i = 0; i < partidas; i++){

            this.player.totalMas();
            this.deck.todoTrue();
            if(i%50==0)
                System.out.println("Simulacion numero : " + i);
            dosBancaInicio.clear();
            this.estado = EstadoPartida.PREFLOP;
            this.deck.reset();
            this.player.cleanCartas();
            this.banca.cleanCartas();
            
            
            this.player.hand(this.deck.deal(),this.deck.deal());
            
            
            for(Card c : this.player.getCards())
                Deck.disponible.put(c.getNum52(), false);
            
            
            
            this.deck.reset();
            //Apuesta obligatoria para recibir dos cartas.
            this.player.apuesta(this.estado);
            ganadas--;
            
            double primeraComprobacion = this.equityHandVsRandom(this.player.getCards())/100;
                try {
                    //if(true){
                    //QUIETO HASTA
                    if(!automatico) {
                        setEquityLabel(primeraComprobacion, VistaMesa.MARCA_PREFLOP);
                        this.drawCard(this.player.getC1().getPath(), this.cardsPlayer.get(0));
                        this.drawCard(this.player.getC2().getPath(), this.cardsPlayer.get(1));
                        setEstadoGui();
                        actualizarInfo();
                        sem1.acquire();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(VistaMesa.class.getName()).log(Level.SEVERE, null, ex);
                }
            if(primeraComprobacion>VistaMesa.MARCA_PREFLOP){
                
                //Ponemos dos apuestas y vemos el flop
                this.estado = EstadoPartida.FLOP;
                ganadas -= 2;
                this.player.apuesta(this.estado);
                ArrayList<Card> board = new ArrayList<>();
                board.add(deck.deal());
                board.add(deck.deal());
                board.add(deck.deal());
                
                double marca = (double)2/9;
                if(i == 10)
                    System.out.println();
                double segundaComprobacion = pokerStove(board)/100;
                try {
                    //if(true){
                    //QUIETO HASTA
                    if(!automatico) {
                        setEquityLabel(segundaComprobacion, VistaMesa.MARCA_FLOP);
                        setEstadoGui();
                        actualizarInfo();
                        int cont = 0; 
                        for(Card c : board){
                            this.drawCard(c.getPath(), this.cardsBoard.get(cont));
                            cont++;
                        }
                        sem1.acquire();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(VistaMesa.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(segundaComprobacion>VistaMesa.MARCA_FLOP){
                //if(true){
                    //Ponemos una apuesta y vemos el turn.
                    this.estado = EstadoPartida.TURN;
                    ganadas -= 1;
                    this.player.apuesta(this.estado);
                    
                    board.add(deck.deal());
                    double terceraComprobacion = pokerStove(board)/100;
                    try {
                    //if(true){
                    //QUIETO HASTA
                    if(!automatico) {
                        
                        setEquityLabel(terceraComprobacion, VistaMesa.MARCA_TURN);
                        setEstadoGui();
                        actualizarInfo();
                        this.drawCard(board.get(3).getPath(), this.cardsBoard.get(3));
                            
                        sem1.acquire();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(VistaMesa.class.getName()).log(Level.SEVERE, null, ex);
                }
                    if(terceraComprobacion>VistaMesa.MARCA_TURN){
                    //if(true){
                    //Ponemos una apuesta más y vemos el river, vamos al showdown
                        this.estado = EstadoPartida.RIVER;
                        ganadas--;
                        this.player.apuesta(this.estado);
                        board.add(deck.deal());
                        this.player.getCards().addAll(board);
                        dosBancaInicio.add(deck.deal());
                        dosBancaInicio.add(deck.deal());
                        dosBancaInicio.addAll(board);
             
                        try {
                    //if(true){
                            //QUIETO HASTA
                            if (!automatico) {

                               // setEquityLabel(terceraComprobacion, );
                                setEstadoGui();
                                actualizarInfo();
                                this.drawCard(board.get(4).getPath(), this.cardsBoard.get(4));
                                for(int iss = 0; iss < 2; iss++){
                                    this.drawCard(dosBancaInicio.get(iss).getPath(),this.cardsBanca.get(iss));
                    }
                                sem1.acquire();
                                
                                
                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(VistaMesa.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        HandEvaluator hPlayer = new HandEvaluator(this.player.getCards(), "1");
                        HandEvaluator hBanca = new HandEvaluator(dosBancaInicio, "0");

                        
                        int result = hPlayer.compareTo(hBanca);


                        if (result > 0) {
                            this.player.pierde();
                            //JOptionPane.showMessageDialog(null, "pierde");
                        } else if (result == 0) {
                            ganadas+=5;
                            this.player.empata();
                            this.player.empata5();
                        } else {
                            
                            this.player.gana();
                            if (hPlayer.getRank() < 4) {
                                ganadas += 9;
                                this.player.gana9();
                            } else {
                                ganadas += 10;
                                this.player.gana10();
                            }
                        }
                    for (Card c : board) 
                        Deck.disponible.put(c.getNum52(), true);
                    } else {
                        //tiramos la mano y volvemos al principio
                        this.player.retTurn();
                        limpiar();
                        this.player.retirado();
                        this.estado = EstadoPartida.FINAL;
                        for (Card c : board) 
                            Deck.disponible.put(c.getNum52(), true);
                    }
                } else {
                    this.player.retFlop();
                    limpiar();
                    //tiramos la mano y volvemos al principio
                        this.player.retirado();
                    this.estado = EstadoPartida.FINAL;
                    for (Card c : board) 
                        Deck.disponible.put(c.getNum52(), true);
        
                }
            } else {
                this.player.retPreFlop();
                limpiar();
                        this.player.retirado();
                //tiramos la mano y volvemos al principio
                this.estado = EstadoPartida.FINAL;
            }
            
            this.estado = EstadoPartida.FINAL;
        
            for(Card c : this.player.getCards())
                Deck.disponible.put(c.getNum52(), true);
            for(Card c : this.banca.getCards())
                Deck.disponible.put(c.getNum52(), true);
            limpiar();

        actualizarInfo();
        }
        JOptionPane.showMessageDialog(null, "Simulaciones: " + partidas +  "\nFichas ganadas: " + ganadas + "\n");
        System.out.println("Simulaciones: " + partidas +  "\nFichas ganadas: " + ganadas + "\n");
    }
    
   /* private double preflopEquity(Hand h){
        
    }*/
   
    private double pokerStove(ArrayList<Card> boardComun){
        for(Card c: boardComun){
            Deck.disponible.put(c.getNum52(), false);
        }
    
        
        ArrayList<ArrayList<Card>> selected;//HACERLO INMUTABLE
        selected = new ArrayList<>();

        ArrayList<Statistics> st = new ArrayList<>();
        st.add(new Statistics(0));
        st.add(new Statistics(1));
        //CARGA LAS CARTAS DEL RANGO EN UN ARRAYLIST DE MANOS(2 CARTAS)



        /////////////////////////////////////BOARD///////////////////////////////////
        
        
        
        
        /////////////////////////////////////////BUCLE PRINCIPAL EVALUADOR///////////////////////////////
        for(int i = 0; i < 5000; i++){
            Statistics.total++;
            ArrayList<Integer> cartasPlayers;
            cartasPlayers = new ArrayList<>();
            ArrayList<HandEvaluator> he = new ArrayList<>();
            ArrayList<Hand> hs = new ArrayList<>();

            ArrayList<Card> playerCards = new ArrayList(this.player.getCards());

            playerCards.addAll(boardComun);
            
            ArrayList<Card> bancCards = new ArrayList<>();
            
            /*for(int ix = 0; ix < 2; ix++){
                bancCards.add(this.deck.deal());
            }*/
            Hand h = null;
            do {
                int mano = rnd.nextInt(this.arrayRandom.size());
                ArrayList<Card> al = new ArrayList<>(this.arrayRandom.get(mano));
                h = new Hand(al);
            } while (!h.disponible());
            bancCards.addAll(h.getHand());
            for(Card c : bancCards){
                Deck.disponible.put(c.getNum52(), false);
                cartasPlayers.add(c.getNum52());
            }
            
            for(int ix = 0; ix < 5 - boardComun.size(); ix++){
                Card c = this.deck.deal();
                playerCards.add(c);
                bancCards.add(c);
                cartasPlayers.add(c.getNum52());
            }
            
            bancCards.addAll(boardComun);
            
            he.add(new HandEvaluator(playerCards, "1"));
            he.add(new HandEvaluator(bancCards, "0"));
            
            Collections.sort(he);
            
            if(he.get(0).compareTo(he.get(1))==0){
                
                for(int eq = 0; eq < 2; eq++){
                    st.get(eq).ties((double)1/2);
                }
            }else
                st.get(Integer.parseInt(he.get(0).getId())).wins();
            
            for(int is : cartasPlayers)
                Deck.disponible.put(is, true);
          
            
            deck.reset();
        
        }
        double ret = (st.get(1).getWins() + st.get(1).getTies()); 
         
        for(Statistics s : st)
            s.clear();
        
        return ret;
        
    }

    private double equityHandVsRandom(ArrayList<Card> array){
        Card c1 = array.get(0);
        Card c2 = array.get(1);
        if(c1.getRank()<c2.getRank()){
            Card temp = new Card(c1.getRepresentation().substring(0,1),c1.getRepresentation().substring(1,2));
            c1 = c2;
            c2 = temp;
        }
        boolean suited = false;
        if(c1.getSuit() == c2.getSuit()){
            suited = true;
        }
        int position;
        if(suited){
            int a = Math.abs(c1.getRank()-14);
            int b = Math.abs(c2.getRank()-14);
            position = a * 13 + b;
        }else{
            int a = Math.abs(c1.getRank()-14);
            int b = Math.abs(c2.getRank()-14);
            position = b * 13 + a;
        }
        return this.flopRandomEquity[position];
    }
    
    private final Random rnd;
    private final String RANDOM;
        private boolean automatico;

    private double[] stringToDouble(String[] ss) {
        double[] salida = new double[169];
        int i = 0;
        for(String s : ss){
            salida[i]=Double.parseDouble(s);
            i++;
        }return salida;
    }
    private void setEquityLabel(double a, double b){
        //this.equityLabel.setText(a + "%");
        if(a > b)
        this.equityLabel.setText("<html><font color='green'>" +String.format("%.2f",a) + "</font></html>");
        else
        this.equityLabel.setText("<html><font color='red'>" +String.format("%.2f",a) + "</font></html>");
    }
    private void setEstadoGui(){
        this.estadoGui.setText(this.estado.toString());
    }
}
