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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
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
    private Escritura esc;
    private Lectura lec;
    private BufferedImage imagenElegida;
    private HashMap<String, Integer> hashSolucion;
    private ArrayList<Nodo> solucionFinal;

    public Modelo(Main p) {
        prog = p;
        rdm = new Random();
        paleta = new Paleta();

        this.colores = new HashMap<>();
        colores.put("Blanco", 0);
        colores.put("Negro", 0);
        colores.put("Rojo", 0);
        colores.put("Verde", 0);
        colores.put("Azul", 0);
        colores.put("Amarillo", 0);
        colores.put("Naranja", 0);
        paises = new ArrayList<>();
    }

    public Bandera procesarImagenBandera(BufferedImage img, int pixelesMuestreo) {
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
        Color col;

        boolean hasAlpha = img.getAlphaRaster() != null;

        for (int i = pixelesMuestreo; i > 0; i--) {
            pixelX = (int) ((double) (width) * rdm.nextDouble());
            pixelY = (int) ((double) (height) * rdm.nextDouble());
            col = new Color(img.getRGB(pixelX, pixelY), hasAlpha);

            if (col.getAlpha() != 0) { //si no es totalmente transparente
                colorName = paleta.getNombre(paleta.analizarColor(col));
                colores.put(colorName, colores.get(colorName) + 1);
            } else {
                i++;
            }

        }

        int value;
        Bandera bandera = new Bandera();

        for (Map.Entry<String, Integer> entry : colores.entrySet()) {
            value = entry.getValue();
            //ponemos porcentaje
            double aux = (double) value / pixelesMuestreo;
            bandera.putColorValue(entry.getKey(), aux * 100.00);
        }

        colores.entrySet().forEach((entry) -> {
            entry.setValue(0);
        });

        return bandera;
    }

    public ArrayList<String> getNombreBanderaDeImagen(Bandera banderaIMG) {
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

        paises.clear();
        Bandera banderaBD;
        double valueIMG, valueBD;
        int maxPoints = -1;
        int aux = 0;
        for (Map.Entry<String, Bandera> banderaIter : banderasBD.entrySet()) {
            banderaBD = banderaIter.getValue();

            for (Map.Entry<String, Double> colorIter : banderaBD.getPaleta().entrySet()) {
                valueIMG = banderaIMG.getColorValue(colorIter.getKey());
                valueBD = colorIter.getValue();
                if (valueIMG - valueBD >= -3.00 && valueIMG - valueBD <= 3.00) {
                    //sumar punto en banderBD
                    banderaBD.addPoint();
                }
            }
            //calcular maxpoint aquí y cargar países candidatos
            if (banderaBD.getPoints() > maxPoints) {
                paises.clear();
                maxPoints = banderaBD.getPoints();
                paises.add(banderaBD.getNombrePais());
            } else if (banderaBD.getPoints() == maxPoints) {
                paises.add(banderaBD.getNombrePais());
            }
            aux++;
            // System.out.println("Bandera procesada " + aux + "de 196");

        }

        return paises;
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
                colores.put(colorName, colores.get(colorName) + 1);
            }
        }

        int area = width * height;
        int value;

        for (Map.Entry<String, Integer> entry : colores.entrySet()) {
            value = entry.getValue();
            //ponemos porcentaje
            bandera.putColorValue(entry.getKey(), (double) (value) / (double) (area) * 100.00);
        }

        colores.entrySet().forEach((entry) -> {
            entry.setValue(0);
        });

        return bandera;

    }

    public void grabarBD(Bandera bandera) {
        try {
            esc.writeObject(bandera);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void crearBD(String fileBD) {
        // cargamos banderas en banderasBD mientras se crea???
        try {

            String base = "flags/";

            BufferedImage bfImage;
            File dir = new File(base);
            String[] ficheros = dir.list();
            Bandera bandera;
            esc = new Escritura(fileBD);
            banderasBD = new HashMap<>();
            int aux = 0;
            int longuitud = ficheros.length;
            for (String fichero : ficheros) {

                bfImage = ImageIO.read(new File(base + fichero));
                bandera = procesarBD(bfImage, fichero);
                grabarBD(bandera); //escribir bandera en fichero;
                banderasBD.put(bandera.getNombrePais(), bandera); //cargamos en BD a la vez
                aux++;
                System.out.println("bandera creada " + aux + " de " + longuitud);

            }
            //escribir bandera centinela
            esc.writeObject(new Bandera("X"));
            esc.closeFile();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void cargarBD(String fileBD) {

        try {
            lec = new Lectura(fileBD);
            if (banderasBD == null) {
                banderasBD = new HashMap<>();
            } else {
                banderasBD.clear();
            }

            Bandera bandera = (Bandera) lec.readObject();
            while (!bandera.getNombrePais().equals("X")) {
                banderasBD.put(bandera.getNombrePais(), bandera);
                bandera = (Bandera) lec.readObject();
            }
            lec.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

    }

    //función que hay que rotocar seguro pero de momento se queda asi porque me da pereza encontrar otra forma
    //hago esto para que se puedan ver diferente banderas seleccionadas
    public void deteccionBandera() {

        Bandera bandera;
        ArrayList<String> paisesSimilares;
        String nombre = "";
        int valor;
        hashSolucion = new HashMap<>();
        solucionFinal = new ArrayList<>();
        System.out.println("Vamos a hcar el monte carlo");
        for (int i = 0; i < 5; i++) {
            bandera = procesarImagenBandera(imagenElegida, 500);
            paisesSimilares = getNombreBanderaDeImagen(bandera);
            for (int j = 0; j < paisesSimilares.size(); j++) {
                nombre = paisesSimilares.get(j);
                if (hashSolucion.containsKey(nombre)) {
                    valor = hashSolucion.get(nombre) + 1;
                    hashSolucion.put(nombre, valor);
                } else {
                    hashSolucion.put(nombre, 0);
                }

            }
        }
        
        System.out.println("recorremos el hashmap");

        for (Map.Entry<String, Integer> iterador : hashSolucion.entrySet()) {

            Nodo nodo = new Nodo(iterador.getKey(), iterador.getValue());
            solucionFinal.add(nodo);
        }
        solucionFinal.sort((t, t1) -> {
            return t.getValor() - t1.getValor(); //To change body of generated lambdas, choose Tools | Templates.
        });
        prog.getView().notificar("Pintar");

    }

    public String getNombre(int i) {

        return solucionFinal.get(i).getNombre();
    }

    public int getLonguitud() {
        return solucionFinal.size();
    }

    @Override
    public void notificar(String s) {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    public void setImagenElegida(BufferedImage imagenElegida) {
        this.imagenElegida = imagenElegida;
    }

}
