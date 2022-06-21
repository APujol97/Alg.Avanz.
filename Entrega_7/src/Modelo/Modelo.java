/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Colores.Paleta;
import Principal.Eventos;
import Principal.Main;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Modelo implements Eventos {

    private Main prog;
    private Paleta paleta;
    private HashMap<String, Integer> colores;
    //hash para cada bandera en la BD y que contenga los puntos
    private HashMap<String, Bandera> banderasBD;
    private ArrayList<String> paises;
    private Random rdm;

    public Modelo(Main p) {
        prog = p;
        rdm = new Random();
        paleta = new Paleta();
        this.colores = new HashMap <> ();
        colores.put("Blanco",0);
        colores.put("Negro",0);
        colores.put("Rojo",0);
        colores.put("Verde",0);
        colores.put("Azul",0);
        colores.put("Amarillo",0);
        colores.put("Naranja",0);
        paises =new ArrayList<>();
    }
    
    public Bandera procesarImagenBandera(BufferedImage img, int pixelesMuestreo){
        /*
        Procesar la imagen leída para devolver bandera con estimación del
        % de colores a partir de N pixeles, el profe recomendó 500px. 
        Quizás un porcentaje es mejor
        
        Este sería el primer algoritmo probabilístico, que devolvería 
        una bandera con diferente estimación de colores por cada ejecución 
        desde Control.java.
        */
        String colorName;
        int height = img.getHeight();
        int width = img.getWidth();
        int pixelX, pixelY;
        
        for(int i = pixelesMuestreo; i > 0; i--){
            pixelX = (int) ((double) (width)*rdm.nextDouble());
            pixelY = (int) ((double) (height)*rdm.nextDouble());
            colorName = paleta.getNombre(paleta.analizarColor(new Color(img.getRGB(pixelX, pixelY))));
            colores.put(colorName,colores.get(colorName)+1);
        }
        
        int area = width*height;
        int value;
        Bandera bandera = new Bandera();
        
        for(Map.Entry<String, Integer> entry : colores.entrySet()){
            value = entry.getValue();
            //ponemos porcentaje
            bandera.putColorValue(entry.getKey(),(double)(value)/(double)(area)*100.00);
        }
        
        colores.entrySet().forEach((entry) -> {entry.setValue(0);});
        
        return bandera;
    }
    
    public ArrayList<String> getNombreBanderaDeImagen(Bandera banderaIMG){
        /*
        Comparar los colores de la bandera por cada bandera en la BD.
        
        En cada iteración comparamos los colores de banderaIMG con los de
        una bandera de banderasBD. si para ese color la diferencia está entre
        el 3.00%, se suma un punto a banderasBD.put(esta_bandera, value+1), y así
        con todas las banderas de banderaBD. Una vez acabado se coge la o las que
        más puntos tiene y esa es la que se devuelve el país.
        
        Lo del 3.00% de diferencia lo comentó el profe como buena heurística
        
        Este método se llamaría N veces desde Cntrol.java, el profe recomienda 5, 
        pues es el segundo algoritmo probabilístico, y recibe siempre una bandera
        diferente de lo devuelto en procesarImagenBandera().
        */
        
        Bandera banderaBD;
        double valueIMG, valueBD;
        
        for(Map.Entry<String, Bandera> banderaIter : banderasBD.entrySet()){
            banderaBD = banderaIter.getValue();
            for(Map.Entry<String, Double> colorIter : banderaBD.getPaleta().entrySet()){
                
                valueIMG = banderaIMG.getColorValue(colorIter.getKey());
                valueBD = colorIter.getValue();
                
                if(valueIMG-valueBD >= -3.00 && valueIMG-valueBD <= 3.00){
                    //sumar punto en banderBD
                    banderaBD.addPoint();
                }
            }
        }
        
        int maxPoints = 0;
        
        for(Map.Entry<String, Bandera> banderaIter : banderasBD.entrySet()){
            banderaBD = banderaIter.getValue();
            if(banderaBD.getPoints() > maxPoints){
                paises.clear();
                maxPoints = banderaBD.getPoints();
                paises.add(banderaBD.getNombrePais());
            } else if(banderaBD.getPoints() == maxPoints){
                paises.add(banderaBD.getNombrePais());
            }
        }
        
        ArrayList<String> paisesVar = paises;
        paises.clear();
        
        return paisesVar;
    }

    public Bandera procesarBD(BufferedImage img, String fichero) {
        //https://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
        //byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        int width = img.getWidth();
        int height = img.getHeight();
//        int rgb = 0;
//        int red = 0;
//        int green = 0;
//        int blue = 0;
        String colorName;
        
        Bandera bandera = new Bandera(fichero);
        
        //algoritmo tradicional de pixelado
        for (int pixelX = 0; pixelX < width; pixelX++) {
            for (int pixelY = 0; pixelY < height; pixelY++) {
//                rgb = img.getRGB(pixelX, pixelY);
//                red = (rgb & 0xff) >> 16;
//                green = (rgb & 0xff) >> 8;
//                blue = (rgb & 0xff);
                colorName = paleta.getNombre(paleta.analizarColor(new Color(img.getRGB(pixelX, pixelY))));
                colores.put(colorName,colores.get(colorName)+1);
            }
        }
        
        int area = width*height;
        int value;
        
        for(Map.Entry<String, Integer> entry : colores.entrySet()){
            value = entry.getValue();
            //ponemos porcentaje
            bandera.putColorValue(entry.getKey(),(double)(value)/(double)(area)*100.00);
        }
        
        colores.entrySet().forEach((entry) -> {entry.setValue(0);});
        
        return bandera;

    }
    
    public void grabarBandera(Bandera bandera){
        //escribir serializable de bandera, o stringLine???
    }

    public void crearBD(File fileBD) {
        // cargamos banderas en banderasBD mientras se crea???
        try {
            
            String base = System.getProperty(Paths.get("").toAbsolutePath().toString() + "/flags");
            URL bandsIMG = getClass().getResource(base);
            BufferedImage bfImage;
            File dir = new File(bandsIMG.toURI());
            String[] ficheros = dir.list();
            Bandera bandera;
            //open escritor
            for (int i = 0; i < ficheros.length; i++) {
                bfImage = ImageIO.read(new File(base + ficheros[i]));
                bandera = procesarBD(bfImage, ficheros[i]);
                //grabarBD(bandera) escribir bandera en fichero;
            }
            //close escritor
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void cargarBD(File fileBD) {
        //lectura del fichero serializable o linea a linea
        //for-banderasBD.put();
    }

    @Override
    public void notificar(String s) {
        throw new UnsupportedOperationException("Not supported yet.");

    }

}
