package com.inteligencia.views;

import com.inteligencia.network.FileNetwork;
import com.inteligencia.network.Network;
import com.inteligencia.network.ValuesNetwork;
import com.inteligencia.pojos.ImageProcess;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.connectedcomponent.ConnectedComponentLabeler;
import org.openimaj.image.connectedcomponent.proc.HuMoments;
import org.openimaj.image.pixel.ConnectedComponent;
import org.openimaj.image.processing.threshold.OtsuThreshold;
import org.openimaj.math.geometry.shape.Polygon;
import org.openimaj.video.VideoDisplay;
import org.openimaj.video.VideoDisplayListener;
import org.openimaj.video.capture.VideoCapture;
import org.openimaj.video.capture.VideoCaptureException;

/**
 *
 * @author Saul Moreyra <drone_cam@outlook.es>
 */
public class Principal extends javax.swing.JFrame {

    private VideoDisplay<MBFImage> videoDisplay;
    private VideoCapture capture;
    private Network net;
    private String [] figuras;
    private int antFig = 0;
    private int figActual = 0;
    private boolean buscando = false;
    
    public Principal() throws VideoCaptureException, IOException, ClassNotFoundException {
        initComponents();
        figuras = new String[]{"circulo","cuadro","pentagono","triangulo","trash"};
        capture = new VideoCapture(320, 240);
        net = FileNetwork.load("NetShapes");
        figRandom();
        this.videoDisplay = VideoDisplay.createVideoDisplay(this.capture, panelCam);
        this.videoDisplay.addVideoListener(new VideoDisplayListener<MBFImage>() {
            @Override
            public void afterUpdate(VideoDisplay<MBFImage> vd) {
            }

            @Override
            public void beforeUpdate(MBFImage t) {
                
                FImage fimg = t.flatten().inverse();
                fimg.processInplace(new OtsuThreshold());
                List <ConnectedComponent> lcc =ConnectedComponentLabeler.Algorithm.TWO_PASS.findComponents(fimg, 0,ConnectedComponent.ConnectMode.CONNECT_8);
                int cont = 1;
                for (ConnectedComponent cc : lcc) {
                    MBFImage nueva = cc.crop(new MBFImage(cc.toFImage()), true);
                    if((nueva.getWidth() > 100 && nueva.getHeight() > 100) && (nueva.getWidth() < 175 && nueva.getHeight() < 175)){

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
                        int tam = 2;
                        //t.drawPoint(poly.calculateCentroid(), RGBColour.PINK, 5);
                        switch(fig){
                            case 1:
                                t.drawPolygon(poly, tam, RGBColour.ORANGE);
                                //t.drawText("Circulo",50, 50, HersheyFont.FUTURA_MEDIUM,40, RGBColour.ORANGE);
                                break;
                            case 2:
                                t.drawPolygon(poly, tam, RGBColour.CYAN);
                                //t.drawText("Cuadro",50, 100, HersheyFont.FUTURA_MEDIUM,40, RGBColour.CYAN);
                                break;
                            case 3:
                                t.drawPolygon(poly, tam, RGBColour.YELLOW);
                                //t.drawText("Pentagono",50, 150, HersheyFont.FUTURA_MEDIUM,40, RGBColour.YELLOW);                            
                                break;
                            case 4:
                                t.drawPolygon(poly, tam, RGBColour.GREEN);
                                //t.drawText("Triangulo",50, 200, HersheyFont.FUTURA_MEDIUM,40, RGBColour.GREEN);    
                                break;
                            case 5:
                                t.drawPolygon(poly, 5, RGBColour.MAGENTA);
                                //t.drawText("Trash",50, 200, HersheyFont.FUTURA_LIGHT,50, RGBColour.MAGENTA); 
                                break;
                            default: break;

                        }
                        if(buscando){
                            //System.out.println("Buscando" + fig +  " == " + figActual);
                            if(fig-1 == figActual){
                                nameImageResul.setText(figuras[fig-1].toUpperCase());
                                BufferedImage bf = ImageProcess.applyResize(ImageUtilities.createBufferedImage(t), 480, 360);
                                ImageIcon imagen = new ImageIcon(bf);
                                labelImageResul.setIcon(imagen);
                                buscando = false;
                                
                            }
                        }
                    }
                } 
            }
        });
    }

   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBackground = new javax.swing.JPanel();
        panelTittle = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        panelCamBack = new javax.swing.JPanel();
        panelCam = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        nameImageResul = new javax.swing.JLabel();
        labelImageResul = new javax.swing.JLabel();
        button3 = new java.awt.Button();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        nameFigRand = new javax.swing.JLabel();
        nextShape = new java.awt.Button();
        labelImageRand = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1080, 740));
        setResizable(false);
        setSize(new java.awt.Dimension(1280, 740));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelBackground.setBackground(new java.awt.Color(251, 251, 251));
        panelBackground.setMinimumSize(new java.awt.Dimension(1080, 740));
        panelBackground.setPreferredSize(new java.awt.Dimension(1080, 740));
        panelBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelTittle.setBackground(new java.awt.Color(255, 102, 0));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Vamos a jugar!");

        javax.swing.GroupLayout panelTittleLayout = new javax.swing.GroupLayout(panelTittle);
        panelTittle.setLayout(panelTittleLayout);
        panelTittleLayout.setHorizontalGroup(
            panelTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelTittleLayout.setVerticalGroup(
            panelTittleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTittleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        panelBackground.add(panelTittle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 70));

        panelCamBack.setBackground(new java.awt.Color(83, 197, 240));
        panelCamBack.setForeground(new java.awt.Color(255, 255, 255));
        panelCamBack.setPreferredSize(new java.awt.Dimension(320, 240));
        panelCamBack.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCam.setPreferredSize(new java.awt.Dimension(320, 240));

        javax.swing.GroupLayout panelCamLayout = new javax.swing.GroupLayout(panelCam);
        panelCam.setLayout(panelCamLayout);
        panelCamLayout.setHorizontalGroup(
            panelCamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        panelCamLayout.setVerticalGroup(
            panelCamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );

        panelCamBack.add(panelCam, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, -1, -1));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Mira a la WebCam!");
        panelCamBack.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 440, -1));

        panelBackground.add(panelCamBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 440, 310));

        jPanel1.setBackground(new java.awt.Color(116, 188, 34));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nameImageResul.setBackground(new java.awt.Color(255, 255, 255));
        nameImageResul.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        nameImageResul.setForeground(new java.awt.Color(255, 255, 255));
        nameImageResul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(nameImageResul, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 640, 35));
        jPanel1.add(labelImageResul, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 480, 360));

        button3.setBackground(new java.awt.Color(155, 204, 5));
        button3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        button3.setForeground(new java.awt.Color(255, 255, 255));
        button3.setLabel("Siguiente Figura!");
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });
        jPanel1.add(button3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 480, 150, 30));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("La figura que encontré es?...");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 640, -1));

        panelBackground.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 640, 520));

        jPanel2.setBackground(new java.awt.Color(229, 55, 134));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nameFigRand.setBackground(new java.awt.Color(255, 255, 255));
        nameFigRand.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        nameFigRand.setForeground(new java.awt.Color(255, 255, 255));
        nameFigRand.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(nameFigRand, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 440, 40));

        nextShape.setBackground(new java.awt.Color(230, 92, 229));
        nextShape.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        nextShape.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        nextShape.setForeground(new java.awt.Color(255, 255, 255));
        nextShape.setLabel("Otra Figura");
        nextShape.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextShapeActionPerformed(evt);
            }
        });
        jPanel2.add(nextShape, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 130, -1));
        jPanel2.add(labelImageRand, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 300, 170));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Dibuja un...");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 440, -1));

        panelBackground.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 440, 340));

        jPanel3.setBackground(new java.awt.Color(80, 239, 220));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Instrucciones");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 640, -1));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("<html>\n<body>\n1. Presta atencion a la figura que se te pide <br>\n2. Dibujala con un marcador de color obscuro <br>\n3. No olvides rellenar por completo la figura <br>\n4. Muestala a la camara <br>\n</body>\n</html>");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 620, -1));

        panelBackground.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 590, 640, 130));

        getContentPane().add(panelBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void figRandom(){
        try {
            figActual = new Random().nextInt(4);
            while(figActual == antFig)
                figActual = new Random().nextInt(4); 
              
            antFig = figActual;
            buscando = true;
            nameFigRand.setText(figuras[figActual].toUpperCase());
            Image img = ImageProcess.readImage(figuras[figActual]+".png");
            ImageIcon imgIcon = new ImageIcon(img);
            labelImageRand.setIcon(imgIcon);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    private void nextShapeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextShapeActionPerformed
        figRandom();
    }//GEN-LAST:event_nextShapeActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        figRandom();
        nameImageResul.setText("");
        labelImageResul.setIcon(null);
    }//GEN-LAST:event_button3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Principal().setVisible(true);
                } catch (VideoCaptureException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelImageRand;
    private javax.swing.JLabel labelImageResul;
    private javax.swing.JLabel nameFigRand;
    private javax.swing.JLabel nameImageResul;
    private java.awt.Button nextShape;
    private javax.swing.JPanel panelBackground;
    private javax.swing.JPanel panelCam;
    private javax.swing.JPanel panelCamBack;
    private javax.swing.JPanel panelTittle;
    // End of variables declaration//GEN-END:variables
}
