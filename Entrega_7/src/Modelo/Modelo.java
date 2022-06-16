/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Principal.Eventos;
import Principal.Main;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Modelo implements Eventos {

    private Main prog;
    private ArrayList<Bandera> DB;

    public Modelo(Main p) {
        prog = p;
    }
    
    public void procesarBD(BufferedImage img, String fichero){
        //https://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
        //algoritmo tradicional de detectar colores pixel por pixel
        byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        int width = img.getWidth();
        int height = img.getHeight();
        boolean hasAlpha = img.getAlphaRaster() != null;
        int pixelLength = 0;
        
        if(hasAlpha){
            pixelLength = 4;
            
        } else {
            pixelLength = 3;
        }
        
    }

    public void crearBD(File fileBD) {
        
        try {
            String base = System.getProperty(Paths.get("").toAbsolutePath().toString() + "/flags");
            URL bandsIMG = getClass().getResource(base);
            BufferedImage bfImage;
            File dir = new File(bandsIMG.toURI());
            String[] ficheros = dir.list();
            for (int i = 0; i < ficheros.length; i++) {
                bfImage = ImageIO.read(new File(base + ficheros[i]));
                procesarBD(bfImage, ficheros[i]);
                //grabarBD();
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void cargarBD(File fileBD){
        //lectura del fichero serializable o linea a linea
    }

    @Override
    public void notificar(String s) {
        throw new UnsupportedOperationException("Not supported yet.");

    }
    
}
