package com.inteligencia.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Saul Moreyra <drone_cam@outlook.es>
 */
public class FileNetwork {
    public static String RUT_FILE_NETWORK = "./src/main/resources/data/files/";
    public static boolean save(Network net, String name) throws IOException {
        
        String rut = RUT_FILE_NETWORK+name+".bin";
        File archivo = new File(rut);
        FileOutputStream fos = new FileOutputStream(archivo);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(net);
        }
        return archivo.exists();
    }

    public static Network load(String name) throws IOException, ClassNotFoundException {
        String rut = RUT_FILE_NETWORK+name+".bin";
        File archivo = new File(rut);
        FileInputStream fis = new FileInputStream(archivo);
        Network network;
        try (ObjectInputStream ois = new ObjectInputStream(fis)) {
            network = (Network) ois.readObject();
        }
        return network;
    }

    public static boolean exists(String name){
        File archivo = new File(RUT_FILE_NETWORK+name+".bin");
        return archivo.exists();
    }
}
