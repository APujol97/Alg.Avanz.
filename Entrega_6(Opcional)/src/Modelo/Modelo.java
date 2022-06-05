/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Principal.Eventos;
import Principal.Main;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Modelo implements Eventos {

    private Main prog;
    private int dimension;
    private Image trozos[];
    private BufferedImage imagen;

    public Modelo(Main p) {
        prog = p;
        this.dimension = -1;
        this.trozos = null;
        this.imagen = null;
    }
    
    public void setDimension(int d) {
        this.dimension = d;
        this.trozos = new Image[dimension * dimension];
    }
    
    public int getDimension() {
        return this.dimension;
    }
    
    public Image getTrozo(int i) {
        return this.trozos[i];
    }

    @Override
    public void notificar(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}