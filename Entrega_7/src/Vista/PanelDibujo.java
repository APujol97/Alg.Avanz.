/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class PanelDibujo extends JPanel {

    public String nombreArchivo;

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public PanelDibujo() {
        this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        this.setBackground(Color.white);
        this.setSize(300, 300);
        this.setVisible(true);
    }
    

    @Override
    public void paint(Graphics g) {

        
        
        Image img;
        try {
            
            if (nombreArchivo != null) {
                img = ImageIO.read(new File(nombreArchivo));
                img = img.getScaledInstance(this.getWidth(), this.getWidth(), java.awt.Image.SCALE_SMOOTH);
                g.drawImage(img, 0, 0, null);
            }
        } catch (IOException ex) {
            Logger.getLogger(PanelDibujo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

    }
}
