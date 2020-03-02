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

public class TestCompuertas {


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        double [][] patron = {
                {0.0,0.0},
                {1.0,0.0},
                {0.0,1.0},
                {1.0,1.0}
        };
        double [][] v_espe = {{0.0},{0,0},{0.0},{1.0}};

        int [] neuronas_por_capa = {5};

        String nombreArchivo = "NetCompuertas";
        Network n;
        if(FileNetwork.exists(nombreArchivo)){
            //SI LA RED EXISTE SE CARGA DE UN ARCHIVO .BIN
            n= FileNetwork.load(nombreArchivo);
        }else {
            //SI LA RED NO EXISTE LA CREA Y ENTRENA
            n = new Network(patron,v_espe,neuronas_por_capa, Neurona.SIGMOIDE,0.5,Long.MAX_VALUE, 0.0001);
            n.learning();
            //n.printSize();
        }

        //SE HACE LA PROPAGACION DE LOS PATRONES
        for (int i = 0; i < patron.length; i++) {
            ArrayList<Double> s =  n.propagation(i);
            System.out.print(patron[i][0] + " AND "+patron[i][1] + "  =  "+v_espe[i][0]);
            for (int j = 0; j < s.size(); j++) {
                System.out.printf("   R=  %.4f \n",s.get(j));
            }
        }

        //SI NO EXISTE LA RED ENTRENADA SE GUARDA EN UN ARCHIVO
        if(!FileNetwork.exists(nombreArchivo))
            System.out.println(FileNetwork.save(n, "NetCompuertas")? "RED GUARDADA": "NO SE GUARDO");

    }


}
