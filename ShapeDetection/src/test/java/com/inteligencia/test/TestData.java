package com.inteligencia.test;

import com.inteligencia.network.ValuesNetwork;
import com.inteligencia.pojos.ImageProcess;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.connectedcomponent.ConnectedComponentLabeler;
import org.openimaj.image.connectedcomponent.proc.HuMoments;
import org.openimaj.image.pixel.ConnectedComponent;
import org.openimaj.image.processing.threshold.OtsuThreshold;
import org.openimaj.video.capture.VideoCaptureException;

/**
 *
 * @author Saul Moreyra <drone_cam@outlook.es>
 */
public class TestData {
     public static void main(String[] args) throws VideoCaptureException, IOException {
        String rut =".\\src\\main\\resources\\data\\images\\";
        File f = new File(rut);
        for (String figura : f.list()) {        
            FImage fimg = ImageProcess.read("cuadros.png");
            fimg.processInplace(new OtsuThreshold());
            ImageProcess.save(fimg, "Componente-full", "PNG");
            List <ConnectedComponent> lcc =ConnectedComponentLabeler.Algorithm.TWO_PASS.findComponents(fimg, 0,ConnectedComponent.ConnectMode.CONNECT_8);
            int cont = 1;
            System.out.println(figura);
            for (ConnectedComponent cc : lcc) {
                MBFImage nueva = cc.crop(new MBFImage(cc.toFImage()), true);
                if((nueva.getWidth() > 30 && nueva.getHeight() > 30)){
                    HuMoments hm = new HuMoments();
                    hm.process(cc);
                    double [] moments = hm.getFeatureVectorArray();
                    ImageProcess im = new ImageProcess(nueva.flatten());
                    //double [][] patron = {};
                    System.out.println(im.getCompactnessFactor()+"\t"+moments[0]+"\t"+moments[1]+"\t"+moments[2]+"\t"+moments[3]);
                    cont++;
                }
            }     
    
        }        
    }
}
