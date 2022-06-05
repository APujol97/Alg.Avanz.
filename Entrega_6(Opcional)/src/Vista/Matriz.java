/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Modelo;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Matriz extends JPanel{

    private Modelo dat;
    private int posiciones[][];
    private Image imgVacia;
    private Image imagen;

    public Matriz(Modelo d) {
        dat = d;
        try {
            imgVacia = (this.createImage(ImageIO.read(new File("negro.jpg")).getSource())).getScaledInstance(75, 75, java.awt.Image.SCALE_SMOOTH);
            imagen = (this.createImage(ImageIO.read(new File("fondo.jpg")).getSource())).getScaledInstance(700, 700, java.awt.Image.SCALE_SMOOTH);;
        } catch (IOException ex) {
            Logger.getLogger(Matriz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < this.dat.getDimension(); i++) {
            for (int j = 0; j < this.dat.getDimension(); j++) {
                if (posiciones[i][j] != -1) {
                    g.drawImage(this.dat.getTrozo(posiciones[i][j]), j * this.getHeight() / this.dat.getDimension(), i * this.getWidth() / this.dat.getDimension(), null);
                } else {
                    g.drawImage(imgVacia, j * this.getHeight() / this.dat.getDimension(), i * this.getWidth() / this.dat.getDimension(), null);
                }
            }
        }
    }
    
    public void setModelo(Modelo d) {
        dat = d;
    }

}