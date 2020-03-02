package clases;
/**
 * @author: Saúl Moreyra
 * @date: 05/11/2019
 * @time 10:42 p. m.
 * @proyect: NeuronalNetwork-Java
 * @email drone_cam@outlook.es
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Network implements Serializable {
    private double [][] patron;
    private double [][] v_espe;
    private ArrayList< ArrayList< Neurona > > red;
    private int [] capasOcultas;
    private int func;
    private int func_s;
    private double alpha;
    private long iteraciones;
    private double errorMinimo;
    private  double ExE;

    private double ET;

    public Network(double [][] patron,double[][] v_espe, int [] capasOcultas, int funcion, double alpha, long iteraciones, double errorMinimo){
        this.patron = patron;
        this.capasOcultas = capasOcultas;
        this.func = funcion;
        this.alpha = alpha;
        this.iteraciones = iteraciones;
        this.v_espe = v_espe;
        red = new ArrayList<>();
        this.errorMinimo = errorMinimo;
        ET = 0.0;
        ExE = 0.0;
        func_s = func;
        doRed();
        cargarPesos();
    }

    public Network(double [][] patron,double[][] v_espe, int [] capasOcultas, int func_oculta, int func_salida, double alpha, long iteraciones, double errorMinimo){
        this.patron = patron;
        this.capasOcultas = capasOcultas;
        this.func = func_oculta;
        this.func_s = func_salida;
        this.alpha = alpha;
        this.iteraciones = iteraciones;
        this.v_espe = v_espe;
        red = new ArrayList<>();
        this.errorMinimo = errorMinimo;
        ET = 0.0;
        ExE = 0.0;
        doRed();
        cargarPesos();
    }

    private void doRed() {
        //CAPAS OCULTAS
        for (int i = 0; i < capasOcultas.length; i++) {
            ArrayList<Neurona> capa = new ArrayList<>();
            for (int j = 0; j < capasOcultas[i]; j++) {
                capa.add(new Neurona(func,alpha));
            }
            red.add(capa);
        }

        //CAPA DE SALIDA
        ArrayList<Neurona> capa = new ArrayList<>();
        for (int i = 0; i < v_espe[0].length; i++) {
            capa.add(new Neurona(func_s,alpha));
        }
        red.add(capa);
    }

    //METODO PARA GENERAR LOS PESOS RANDOM
    private ArrayList<Double> ws(int t){
        ArrayList <Double> temp = new ArrayList<>();
        for (int i = 0; i < t; i++) {
            temp.add(new Random().nextDouble());
        }
        return temp;
    }

    //SE CARGAN LOS PESOS INICIALES
    private void cargarPesos(){
        for(int k=0; k<red.size(); k++){
            double bias = new Random().nextDouble();
            for(int i=0; i<red.get(k).size(); i++){
                if(k==0){
                    red.get(k).get(i).setW(ws(patron[0].length));
                    red.get(k).get(i).setBIAS(bias);
                }
                else{
                    red.get(k).get(i).setW(ws(red.get(k-1).size()));
                    red.get(k).get(i).setBIAS(bias);
                }
            }
        }
    }

    //SE CARGA EL PATRON A LA RED
    private void cargarPatron(int pos){
        ArrayList <Double> p = new ArrayList<>();
        for(int i=0; i<patron[pos].length; i++)
            p.add(patron[pos][i]);

        for(int i=0; i<capasOcultas[0]; i++)
            red.get(0).get(i).setX(p);

        int cf = red.size()-1;
        for (int j = 0; j < red.get(cf).size(); j++) {
            red.get(cf).get(j).setD(v_espe[pos][j]);
        }
    }


    public ArrayList<Double>  propagation(int pos){
        cargarPatron(pos);
        for(int i=0; i<red.size()-1; i++){
            ArrayList <Double> ent = new ArrayList<>();
            for(int j=0; j < red.get(i).size(); j++){
                red.get(i).get(j).fActivacion();
                ent.add(red.get(i).get(j).getY());
            }
            for(int j=0; j < red.get(i+1).size(); j++){
                red.get(i+1).get(j).setX(ent);
            }
        }
        int f =  red.size()-1;
        for(int i=0; i<red.get(f).size(); i++)
            red.get(f).get(i).fActivacion();

        //SE CALCULA EL ERROR TOTAL DE LA RED
        ET = 0.0;
        for (int i = 0; i < red.get(f).size(); i++) {
            Neurona n = red.get(f).get(i);
            double ep = Math.pow((n.getD() - n.getY()),2)*0.5;
            ET += ep;
        }



        //EN CASO DE NECESITAR LAS SALIDAS SE RETORNAN
        ArrayList <Double> salidas = new ArrayList<>();
        for (int i = 0; i < red.get(f).size(); i++) {
            salidas.add(red.get(f).get(i).getY());
        }
        return salidas;
    }

    private void calcularErrores() {
        //SE CALCULA EL ERROR DE LAS NEURONAS DE SALIDA
        int f =  red.size()-1;
        for (int i = 0; i < red.get(f).size(); i++) {
            Neurona n = red.get(f).get(i);
            double epn = (n.getY() -n.getD())*n.getY()*(1-n.getY());
            red.get(f).get(i).setE(epn);
        }

        //SE CALCULA EL ERROR PARA LAS CAPAS OCULTAS
        for (int capa = red.size()-2; capa >= 0 ; capa--) {
            for (int neu = 0; neu < red.get(capa).size(); neu++) {
                double suma = 0.0;
                for(int k=0; k < red.get(capa + 1).size(); k++){
                    Neurona n = red.get(capa+1).get(k);
                    suma += n.getE() * n.getW().get(neu);
                }
                double y = red.get(capa).get(neu).getY();
                y = y*(1-y);
                red.get(capa).get(neu).setE(suma*y);
            }
        }
    }


    private void ajusteDePesos(){
        for (int i = 0; i < red.size()-1; i++) {
            for (int j = 0; j < red.get(i).size(); j++) {
                for (int k = 0; k < red.get(i).get(j).getW().size(); k++) {
                    double peso = red.get(i).get(j).getW().get(k);
                    double error =  red.get(i).get(j).getE() * red.get(i).get(j).getX().get(k);
                    red.get(i).get(j).getW().set(k,(peso - alpha * error));

                    //EL BIAS PUEDE O NO AJUSTARSE (TARDA UN POCO MENOS SI NO SE AJUSTA)
                    //double new_bias = red.get(i).get(j).getBIAS() - alpha * error;
                    //red.get(i).get(j).setBIAS(new_bias);
                }
            }
        }
        for (int i = 0; i < red.get(red.size()-1).size(); i++) {
            Neurona n = red.get(red.size()-1).get(i);
            for (int j = 0; j <n.getW().size() ; j++) {
                double new_peso = n.getW().get(j) - alpha * (n.getE() * n.getX().get(j));
                red.get(red.size()-1).get(i).getW().set(j,new_peso);

                //EL BIAS PUEDE O NO AJUSTARSE (TARDA UN POCO MENOS SI NO SE AJUSTA)
                //double new_bias = red.get(i).get(j).getBIAS() - alpha * n.getE() ;
                //red.get(i).get(j).setBIAS(new_bias);
            }
        }
    }



    private void backPropagation(){
        calcularErrores();
        ajusteDePesos();
    }

    public void learning(){
        long cont = Long.MIN_VALUE;
        double err_ant = 1.0;
        int sec_cont= 100;
        do {
            ExE = 0.0;
            for (int i = 0; i < patron.length; i++) {
                propagation(i);
                ExE += getErrorTotal();
                backPropagation();
            }
            cont++;
            ExE = ExE / patron.length;
            //System.out.printf("ERROR EPOCA: %.16f \n", ExE);
            if(err_ant > ExE){
                if (sec_cont == 100) {
                    System.out.printf("ERROR EPOCA: %.16f \n", ExE);
                    sec_cont = 0;
                }
                sec_cont++;
                err_ant = ExE;
            }
            if(ExE < errorMinimo) {
                System.out.printf("ERROR EPOCA FINAL: %.16f \n", ExE);
                break;
            }
        }while (iteraciones > cont);
    }

    private void setErrorTotal(double errorTotal){
        //System.out.println("ERROR DE LA RED: " + errorTotal);
        this.ET = errorTotal;
    }

    public double getErrorTotal() {
        return ET;
    }

    public void printSize(){
        System.out.print(patron[0].length + "  ");
        for (int i = 0; i < red.size(); i++) {
            System.out.print(red.get(i).size() + "  ");
        }
        System.out.println();
    }

    public void printRed(){
        for(int i=0; i<red.size(); i++){
            System.out.printf("CAPA %d:\n",i);
            for(int j=0; j<red.get(i).size(); j++){
                System.out.println("   NEURONA "+j);
                System.out.println(red.get(i).get(j).toString());
            }
        }
    }

}
