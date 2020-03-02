package clases;
/**
 * @author: Saúl Moreyra
 * @date: 05/11/2019
 * @time 10:42 p. m.
 * @proyect: NeuronalNetwork-Java
 * @email drone_cam@outlook.es
 */

import java.io.*;

public class FileNetwork {
    public static boolean save(Network net, String name) throws IOException {
        String rut = "./src/files/"+name+".bin";
        File archivo = new File(rut);
        FileOutputStream fos = new FileOutputStream(archivo);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(net);
        oos.close();
        return archivo.exists();
    }

    public static Network load(String name) throws IOException, ClassNotFoundException {
        String rut = "./src/files/"+name+".bin";
        File archivo = new File(rut);
        FileInputStream fis = new FileInputStream(archivo);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Network network = (Network) ois.readObject();
        ois.close();
        return network;
    }

    public static boolean exists(String name){
        File archivo = new File("./src/files/"+name+".bin");
        return archivo.exists();
    }
}
