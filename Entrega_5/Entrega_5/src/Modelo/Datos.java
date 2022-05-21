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
    
    String text_cast = "esta es una prueba para el castellano";
    String text_cat = "aquesta es una porva per al catala";
    String text_eng = "this is a test for the english language";
    
    String texto;
    ArrayList<Nodo> palabras_erroneas;
    ArrayList<String> palabras_propuestas;
    String palabra;
    Lectura lect;
    Nodo nodo;
    
    public Datos(){
        this.texto = text_cast;
        palabra = "";
        palabras_propuestas = new ArrayList<>();
        palabras_erroneas = new ArrayList<>();
    }
    
    public String detectarIdioma(){
        return null;
    }
    
    public String getCorrecion(){
        return null;
    }
    
    public String getPropuesta(){
        return null;
    }
    
    public void analizarPalabra(Lectura lect, String pal){
        String word;
        int coste = -1;
        int min = 99;
        while(lect.isNotEOF()){
            word = lect.readPalabra();
            coste = distanciaLevenshtein(pal.toCharArray(), word.toCharArray());
            if(coste == 0){
                break;
            } else if(coste < min){
                min = coste;
                palabras_propuestas.clear();
                palabras_propuestas.add(word);
            } else if(coste == min){
                palabras_propuestas.add(word);
            }
        }
        if(coste != 0){
            nodo = new Nodo(pal);
            nodo.añadir_Lista(palabras_propuestas);
            palabras_erroneas.add(nodo);
        }
        palabras_propuestas.clear();
    }
    
    public void detectarErrores(String text){
        text = text.toLowerCase(); // o se hace antes;
        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) != ' '){ //VIGILA!!!
                palabra +=  text.charAt(i);
            } else {
                lect = new Lectura("ca.dic");
                lect.openFile();
                analizarPalabra(lect, palabra);
                lect.closeFile();
                System.out.println(palabra);
                palabra = "";
            }
        }
        System.out.println(palabras_erroneas.toString());
    }
    
    public void corregirErrores(char[] text){
        
    }
    
    public int distanciaLevenshtein(char[] palabra, char[] palabra_leida){
        int coste = 0;
        int aux_min = 0;
        int[][] distancia = new int[palabra.length+1][palabra_leida.length+1];
        
        for(int i = 0; i < palabra.length+1; i++){
            distancia[i][0] = i;
        }
        
        for(int j = 0; j < palabra_leida.length+1; j++){
            distancia[0][j] = j;
        }
        
        for(int i = 1; i < palabra.length+1; i++){
            for(int j = 1; j < palabra_leida.length+1; j++){
                if(palabra[i-1] == palabra_leida[j-1]){
                    coste = 0;
                } else {
                    coste = 1;
                }
                aux_min = Math.min(distancia[i-1][j]+1, distancia[i][j-1]+1);
                distancia[i][j] = Math.min(aux_min, distancia[i-1][j-1]+coste);
            }
        }
        return distancia[palabra.length][palabra_leida.length];
    }
    
}
