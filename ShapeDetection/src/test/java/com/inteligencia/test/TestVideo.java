package com.inteligencia.test;

import com.inteligencia.network.FileNetwork;
import com.inteligencia.network.Network;
import com.inteligencia.network.ValuesNetwork;
import com.inteligencia.pojos.ImageProcess;
import java.io.IOException;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.connectedcomponent.ConnectedComponentLabeler;
import org.openimaj.image.connectedcomponent.proc.HuMoments;
import org.openimaj.image.pixel.ConnectedComponent;
import org.openimaj.image.processing.threshold.OtsuThreshold;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.math.geometry.shape.Polygon;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

/**
 *
 * @author Saul Moreyra <drone_cam@outlook.es>
 */
public class TestVideo {
    public static void main(String[] args) throws VideoCaptureException, IOException, ClassNotFoundException {
        Network net = FileNetwork.load("NetShapes");
        VideoCapture vc = new VideoCapture(450, 450);
	JFrame frame = DisplayUtilities.displaySimple(vc.getNextFrame(), "capture");
        
        while (true) {  
            MBFImage img = vc.getNextFrame();
            FImage fimg = img.flatten().inverse();
            fimg.processInplace(new OtsuThreshold());
            List <ConnectedComponent> lcc =ConnectedComponentLabeler.Algorithm.TWO_PASS.findComponents(fimg, 0,ConnectedComponent.ConnectMode.CONNECT_8);
            int cont = 1;
            for (ConnectedComponent cc : lcc) {
                MBFImage nueva = cc.crop(new MBFImage(cc.toFImage()), true);
                if((nueva.getWidth() > 50 && nueva.getHeight() > 50) && (nueva.getWidth() < 250 && nueva.getHeight() < 250)){
                    
                    HuMoments hm = new HuMoments();
                    hm.process(cc);
                    double [] moments = hm.getFeatureVectorArray();
                    FImage second = nueva.flatten();
                    ImageProcess im = new ImageProcess(second);
                    double [][] patron = {{im.getCompactnessFactor(),moments[0],moments[1],moments[2],moments[3]}};
                    net.setPatron(patron);
                    ArrayList <Double> d = net.propagation(0);
                    int fig = ValuesNetwork.getShape(d);
                    
                    Polygon poly = cc.toPolygon();
                    
                    img.drawPoint(poly.calculateCentroid(), RGBColour.PINK, 5);
                    switch(fig){
                        case 1:
                            img.drawPolygon(poly, 10, RGBColour.ORANGE);
                            img.drawText("Circulo",50, 50, HersheyFont.FUTURA_MEDIUM,40, RGBColour.ORANGE);
                            
                            //System.out.println("Circulo");
                            break;
                        case 2:
                            img.drawPolygon(poly, 10, RGBColour.CYAN);
                            img.drawText("Cuadro",50, 100, HersheyFont.FUTURA_MEDIUM,40, RGBColour.CYAN);
                            break;
                        case 3:
                            img.drawPolygon(poly, 10, RGBColour.YELLOW);
                            img.drawText("Pentagono",50, 150, HersheyFont.FUTURA_MEDIUM,40, RGBColour.YELLOW);                            
                            break;
                        case 4:
                            img.drawPolygon(poly, 10, RGBColour.GREEN);
                            img.drawText("Triangulo",50, 200, HersheyFont.FUTURA_MEDIUM,40, RGBColour.GREEN);    
                            break;
                        case 5:
                            img.drawPolygon(poly, 2, RGBColour.MAGENTA);
                            img.drawText("Trash",50, 200, HersheyFont.FUTURA_LIGHT,50, RGBColour.MAGENTA); 
                            break;
                        
                    }
                    //ImageProcess.save(nueva.flatten(), "Componente-"+fig+cont, "PNG");
                    //cont++;
                    
                }
            } 
            DisplayUtilities.display(img, frame);
        }
    }
}
