package com.inteligencia.test;

import com.inteligencia.network.FileNetwork;
import com.inteligencia.network.Network;
import com.inteligencia.network.Neurona;
import com.inteligencia.network.ValuesNetwork;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Saul Moreyra <drone_cam@outlook.es>
 */
public class TestTraining {
   
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ValuesNetwork vn = new ValuesNetwork();
        double [][] patron = vn.getInputs();
        double [][] v_espe = vn.getOutputs();

        int [] neuronas_por_capa = {5,7,3};

        Network n;
        String nombreArchivo = "NetShapes2";
        if(FileNetwork.exists(nombreArchivo)){
            n = FileNetwork.load(nombreArchivo);
        }else {
            n = new Network(patron, v_espe, neuronas_por_capa , Neurona.SIGMOIDE, 0.03, Long.MAX_VALUE, 0.005, 1000);
            n.learning();
        }

        int cont = 0;
        for (int i = 0; i < patron.length; i++) {
            System.out.print((i+1) + ")\tVE: ");
            ArrayList<Double> s =  n.propagation(i);
            for (int j = 0; j < v_espe[i].length; j++) {
                System.out.printf("%.4f  ",v_espe[i][j]);
            }
            System.out.print("\tVO: ");
            for (int j = 0; j < s.size(); j++) {
                System.out.printf("%.4f  ",Neurona.fEscalon(s.get(j),0.5));
            }
            boolean flag = true;
            for (int j = 0; j < s.size(); j++) {
                if(v_espe[i][j] != Neurona.fEscalon(s.get(j),0.5))
                    flag = false;
            }

            if(flag) {
                System.out.println("ACEPTADO");
                cont++;
            }
            else
                System.out.println("RECHAZADO");
        }
        double porcen = ((cont*100)/patron.length);
        System.out.printf("PORCENTAJE DE ACEPTADOS %.4f  \n",porcen);

        if (!FileNetwork.exists(nombreArchivo))
            System.out.println(FileNetwork.save(n, nombreArchivo)? "RED GUARDADA": "NO SE GUARDO");
    }
}
