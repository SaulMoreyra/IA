package test;
/**
 * @author: Saúl Moreyra
 * @date: 05/11/2019
 * @time 10:42 p. m.
 * @proyect: NeuronalNetwork-Java
 * @email drone_cam@outlook.es
 */

import clases.*;

import java.io.IOException;
import java.util.ArrayList;

public class TestCangrejos {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        double [][] patron = {
                {1.42,0.00,0.206,0.144,0.428,0.465,0.196},
                {0.19,0.01,0.133,0.111,0.278,0.323,0.113},
                {1.69,0.00,0.167,0.143,0.323,0.370,0.147},
                {0.56,0.01,0.098,0.089,0.204,0.239,0.088}
        };
        double [][] v_espe = {{0.0},{0.0},{1.0},{1.0}};
        int [] neuronas_por_capa = {5,5,5};

        String nombreArchivo = "NetCangrejos";
        Network n;
        if (FileNetwork.exists(nombreArchivo)){
            n = FileNetwork.load(nombreArchivo);
        }else {
            n = new Network(patron, v_espe, neuronas_por_capa, Neurona.SIGMOIDE, Neurona.SIGMOIDE, 0.05555, Long.MAX_VALUE, 0.0001);
            n.learning();
        }

        for (int i = 0; i < patron.length; i++) {
            ArrayList<Double> s =  n.propagation(i);
            System.out.print("VE: " + v_espe[i][0] + "   VO: ");
            for (int j = 0; j < s.size(); j++) {
                System.out.printf("  %.4f  ",Neurona.fEscalon(s.get(j),0.5));
            }
            System.out.println();
        }

        if(!FileNetwork.exists(nombreArchivo))
            System.out.println(FileNetwork.save(n,nombreArchivo)? "RED GUARDADA": "NO SE GUARDO");
    }
}
