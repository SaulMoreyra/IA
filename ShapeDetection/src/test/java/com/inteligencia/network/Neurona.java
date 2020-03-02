package com.inteligencia.network;

/**
 *
 * @author Saul Moreyra <drone_cam@outlook.es>
 */

import java.io.Serializable;
import java.util.ArrayList;

public class Neurona implements Serializable {

    public static int SIGMOIDE = 0;
    public static int ESCALON = 1;
    public static int ReLu = 2;


    private ArrayList <Double> x;
    private ArrayList <Double> w;
    private double BIAS;
    private double y;
    private double d;
    private double e;
    private int func;
    private double alpha;

    public Neurona(int tipo,double alpha) {
        d = 0.0;
        e = 0.0;
        y = 0.0;
        x = new ArrayList<>();
        w = new ArrayList<>();
        this.alpha = alpha;
        func = tipo;
    }

    public ArrayList<Double> getW() {
        return w;
    }

    public void setW(ArrayList<Double> w) {
        this.w = w;
    }

    public void setW(double [] w) {
        for (int i = 0; i < w.length; i++) {
            this.w.add(w[i]);
        }
    }

    public ArrayList<Double> getX() {
        return x;
    }

    public void setX(ArrayList x) {
        if( x.get(0) instanceof Double)
            this.x = x;
        else{
            for (int i = 0; i < x.size(); i++) {
                Neurona t = ((Neurona)x.get(i));
                t.fActivacion();
                this.x.add(t.getY());
            }
        }
    }

    public double getBIAS() {
        return BIAS;
    }

    public void setBIAS(double BIAS) {
        this.BIAS = BIAS;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public double getE() {
        return e;
    }

    public void setE(double e) {
        this.e = e;
    }

    private double NET(){
        double aux = 0.0;
        for (int i = 0; i < x.size() ; i++) {
            aux += x.get(i) * w.get(i);
        }
        return aux + (BIAS * 1.0);
    }

    public void fActivacion(){
        double aux = 0.0;
        switch (func){
            case 0:
                y = 1.0 / (1.0 + Math.pow(Math.E,(-1)*NET()));
                break;
            case  1:
                aux = NET();
                if(aux >= 0.0) y = 1.0;
                else y = 0.0;
                break;
            case 2:
                aux = NET();
                if(aux >= 0.0) y = aux;
                else y = 0.0;
                break;
        }
    }

    public static double fEscalon(double salida, double umbral){
        return salida >= umbral? 1.0 : 0.0;
    }

    @Override
    public String toString() {
        String xs = "\t\tX: ";
        for (int i = 0; i < x.size(); i++) {
            xs += String.format("%.4f  ",x.get(i) );
        }

        String ws = "\t\tW: ";
        for (int i = 0; i < w.size(); i++) {
            ws += String.format("%.4f  ",w.get(i));
        }
        return xs +"\n"+ws+"\n"+String.format("\t\tNET: %.4f  \tBIAS: %.4f \tY: %.8f  \tVE: %.4f  \tExN: %.8f  ",NET(),BIAS,y,d,e);
    }
}
