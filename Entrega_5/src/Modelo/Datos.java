/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author pujol
 */
public class Datos {

    ArrayList<Nodo> palabras_erroneas;
    ArrayList<String> palabras_propuestas;
    String palabra;
    Lectura lect;
    Nodo nodo;

    public Datos() {

        palabra = "";
        palabras_propuestas = new ArrayList<>();
        palabras_erroneas = new ArrayList<>();
    }

    public String detectarIdioma(String text) {
        text = text.toLowerCase(); // o se hace antes;
        double costeCastellano = 0, costeingles = 0, costecatala = 0;
       // double aux1 = 0, aux2 = 0, aux3 = 0;
        for (int i = 0, indice = 0; i < text.length() && indice < 10; i++) {
            if (text.charAt(i) != ' ') { //VIGILA!!!
                palabra += text.charAt(i);
            } else {
                lect = new Lectura("catalan.dic");
                lect.openFile();
                costecatala += /*aux1 =*/ analizarPalabra2(lect, palabra);
                lect.closeFile();

                lect = new Lectura("castellano.dic");
                lect.openFile();
                costeCastellano += /*aux2 =*/ analizarPalabra2(lect, palabra);
                lect.closeFile();

                lect = new Lectura("ingles.dic");
                lect.openFile();
                costeingles += /*aux3 =*/ analizarPalabra2(lect, palabra);
                lect.closeFile();

                indice++;

              /*  System.out.println("Palabra: " + palabra);
                System.out.println("---> Catalan: " + aux1);
                System.out.println("---> Castellano: " + aux2);
                System.out.println("---> Ingles: " + aux3);*/
                palabra = "";
            }
        }
        if (costeCastellano < costecatala) {
            if (costeCastellano < costeingles) {
                return "castellano.dic";
            } else {
                return "ingles.dic";
            }
        } else {
            if (costecatala < costeingles) {
                return "catalan.dic";
            } else {
                return "ingles.dic";
            }
        }
    }

    public double analizarPalabra2(Lectura lect, String pal) {
        String word;
        int coste = -1;
        int min = 99;
        int costeTotal = 0;
        int palabrasComprobadas = 0;
        while (lect.isNotEOF()) {
            word = lect.readPalabra();
            coste = distanciaLevenshtein(pal.toCharArray(), word.toCharArray());
            if (coste == 0) {
                break;
            }
            costeTotal += coste;
            palabrasComprobadas++;
        }
        if (coste == 0) {
            return 0;
        }
        return costeTotal / palabrasComprobadas;
    }

    public String getCorrecion() {
        return null;
    }

    public String getPropuesta() {
        return null;
    }

    public void analizarPalabra(Lectura lect, String pal) {
        String word;
        int coste = -1;
        int min = 99;
        while (lect.isNotEOF()) {
            word = lect.readPalabra();
            coste = distanciaLevenshtein(pal.toCharArray(), word.toCharArray());
            if (coste == 0) {
                break;
            } else if (coste < min) {
                min = coste;
                palabras_propuestas.clear();
                palabras_propuestas.add(word);
            } else if (coste == min) {
                palabras_propuestas.add(word);
            }
        }
        if (coste != 0) {
            nodo = new Nodo(pal);
            nodo.añadir_Lista(palabras_propuestas);
            palabras_erroneas.add(nodo);
        }
        palabras_propuestas.clear();
    }

    public void detectarErrores(String text, String idioma) {
        text = text.toLowerCase(); // o se hace antes;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ' ') { //VIGILA!!!
                palabra += text.charAt(i);
            } else {
                lect = new Lectura(idioma);
                lect.openFile();
                analizarPalabra(lect, palabra);
                lect.closeFile();
                palabra = "";
            }
        }
        System.out.println(palabras_erroneas.toString());
    }

    public void corregirErrores(char[] text) {

    }

    public int distanciaLevenshtein(char[] palabra, char[] palabra_leida) {
        int coste = 0;
        int aux_min = 0;
        int[][] distancia = new int[palabra.length + 1][palabra_leida.length + 1];

        for (int i = 0; i < palabra.length + 1; i++) {
            distancia[i][0] = i;
        }

        for (int j = 0; j < palabra_leida.length + 1; j++) {
            distancia[0][j] = j;
        }

        for (int i = 1; i < palabra.length + 1; i++) {
            for (int j = 1; j < palabra_leida.length + 1; j++) {
                if (palabra[i - 1] == palabra_leida[j - 1]) {
                    coste = 0;
                } else {
                    coste = 1;
                }
                aux_min = Math.min(distancia[i - 1][j] + 1, distancia[i][j - 1] + 1);
                distancia[i][j] = Math.min(aux_min, distancia[i - 1][j - 1] + coste);
            }
        }
        return distancia[palabra.length][palabra_leida.length];
    }

}
