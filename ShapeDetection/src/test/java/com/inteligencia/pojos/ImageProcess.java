package com.inteligencia.pojos;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

/**
 *
 * @author Saul Moreyra <drone_cam@outlook.es>
 */
public class ImageProcess {
    private FImage image;
    private int padding;
    public ImageProcess(FImage image) {
        this.image = image;
        this.padding = 5;
        this.addPadding();
        
    }
    
    public void addPadding(){
        FImage out = new FImage(image.getWidth() + ( padding*2), image.getHeight() + (padding*2));
        for(int i=0; i<image.width; i++){
            for(int j=0; j<image.height; j++){
                out.pixels[j+padding][i+padding] = image.pixels[j][i];
            }
        }
        image =  out;
    }

     public void printImage(){
        for(int i=0; i<image.width; i++){
            for(int j=0; j<image.height; j++){
                System.out.print(image.pixels[j][i] +" ");
            }
            System.out.println("");
        }
    }
    
    private int calculateArea(){
        int cont = 0;
        for(int i=0; i<image.width; i++){
            for(int j=0; j<image.height; j++){
                if(image.pixels[j][i] != 0.0) cont++;
            }
        }
        return cont;
    }
    
    private int calculatePerimeter(){
        int cont = 0;
        for(int i=0; i<image.width; i++){
            for(int j=0; j<image.height; j++){
                if(image.pixels[j][i] != 0.0)
                    if(isBorder(j, i)) {
                        cont ++ ;
                    }
            }
        }
        return cont;   
    }
    
    private boolean isBorder(int _j, int _i){
        int black = 0;
        int white = 0;
        for(int i=_i-1; i<=_i+1; i++){
            for(int j=_j-1; j<=_j+1; j++){
                if(image.pixels[j][i] != 0.0) black++;
                else white++;
            }
        }
        return (black > 0 && white > 0);
    }
    
    public double getCompactnessFactor(){
        int perimetro = calculatePerimeter();
        int area = calculateArea();
        double compactness = (double)area  / Math.pow(perimetro,2);
        return compactness;
    }
    
    static final String RUT = "./src/main/resources/data/images/";
    
    public static FImage read(String name) throws IOException{
        MBFImage image = ImageUtilities.readMBF(new File(RUT+name));
        return image.flatten().inverse();
    }
    
    public static void save(FImage img, String name, String type) throws IOException{
         ImageUtilities.write(img, type,  new File(RUT+name+"."+type.toLowerCase()));
    }
    
    public static Image readImage(String name) throws IOException{
        String RUTA = "./src/main/resources/data/shapes/";
        return ImageIO.read(new File(RUTA+name));
    }
    
    public static BufferedImage applyResize(BufferedImage img, int newW, int newH) { 
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }  
}
