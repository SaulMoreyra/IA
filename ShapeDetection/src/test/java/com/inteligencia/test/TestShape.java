package com.inteligencia.test;

import com.inteligencia.network.FileNetwork;
import com.inteligencia.network.Network;
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

/**
 *
 * @author Saul Moreyra <drone_cam@outlook.es>
 */
public class TestShape {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Network net = FileNetwork.load("NetShapes");
        FImage fimg = ImageProcess.read("prueba4.jpeg");
        fimg.processInplace(new OtsuThreshold());
        ImageProcess.save(fimg, "Componente-full", "PNG");
        List <ConnectedComponent> lcc =ConnectedComponentLabeler.Algorithm.TWO_PASS.findComponents(fimg, 0,ConnectedComponent.ConnectMode.CONNECT_8);
        int cont = 1;
        for (ConnectedComponent cc : lcc) {
            MBFImage nueva = cc.crop(new MBFImage(cc.toFImage()), true);
            if((nueva.getWidth() > 30 && nueva.getHeight() > 30)){
                HuMoments hm = new HuMoments();
                hm.process(cc);
                double [] moments = hm.getFeatureVectorArray();
                ImageProcess im = new ImageProcess(nueva.flatten());
                double [][] patron = {{im.getCompactnessFactor(),moments[0],moments[1],moments[2],moments[3]}};
                net.setPatron(patron);
                ArrayList <Double> d = net.propagation(0);
                int fig = ValuesNetwork.getShape(d);
                ImageProcess.save(nueva.flatten(), "Componente-"+fig+cont, "PNG");
                cont++;
            }
        }     
    }
}
