package com.inteligencia.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Saul Moreyra <drone_cam@outlook.es>
 */
public class ValuesNetwork {
    private double max_value;
    private double min_value;
    private String RUTA = "./src/main/resources/data/training/";
    private Scanner scan;
    private double [][] inputs;
    private double [][] outputs;
    public ValuesNetwork() {
        max_value = 0;
        min_value = 0;
        inputs = new double[150][5];
        outputs = new double[150][3];
        readInputs();
        //System.out.println("MIN-VALUE:"+(double)min_value+" MAX-VALUE:"+(double)max_value);
        //normailzeInputs();
        readOutputs();
    }
    
    
    private void normailzeInputs(){
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[0].length; j++) {
                inputs[i][j] = (inputs[i][j] - min_value) / (max_value - min_value);
                //System.out.printf((double)inputs[i][j]+"\t");
            }
            //System.out.println();
        }
    }
    
    public void normailze(double [][] in){
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                in[i][j] = (in[i][j] - min_value) / (max_value - min_value);
            }
        }
    }
    
    private void readInputs(){
        int fil = 0;
        File file = new File(RUTA+"input.txt");
        try {
            scan = new Scanner(file);
            while(scan.hasNext()){
                String [] in = scan.nextLine().split(",");
                if(in.length > 1){
                    for (int col=0; col<5; col++) {
                        inputs[fil][col] = Double.parseDouble(in[col]);
                        if(inputs[fil][col] < min_value) min_value = inputs[fil][col];
                        if(inputs[fil][col] > max_value) max_value = inputs[fil][col];
                    }
                    fil++;
                }
            }
        } catch (FileNotFoundException e1) {
                e1.printStackTrace();
        }
    }
    
    private void readOutputs(){
        int fil = 0;
        File file = new File( "./src/main/resources/data/training/output.txt");
        try {
            scan = new Scanner(file);
            while(scan.hasNext()){
                String [] in = scan.nextLine().split(",");
                for (int col=0; col<in.length; col++) {
                    outputs[fil][col] = Double.parseDouble(in[col]);
                }
                fil++;
            }
        } catch (FileNotFoundException e1) {
                e1.printStackTrace();
        }
    }

    public double[][] getInputs() {
        return inputs;
    }

    public double[][] getOutputs() {
        return outputs;
    }
    
    public static int getShape(ArrayList <Double> in){
        int [] inp = new int[in.size()];
        for (int i=0; i<in.size(); i++) 
            inp [i] = (int)Neurona.fEscalon(in.get(i), 0.9);
                        
        if(inp[0] == 0 && inp[1] == 0 && inp[2] == 1)
            return 1;   //Circulo
        else if(inp[0] == 0 && inp[1] == 1 && inp[2] == 0)
            return 2;   //Cuadro
        else if(inp[0] == 1 && inp[1] == 0 && inp[2] == 0)
            return 3;   //Pentagono
        else if(inp[0] == 0 && inp[1] == 1 && inp[2] == 1)
            return 4;   //Triangulo
        else 
            return 5;   //Trash
    }
}
