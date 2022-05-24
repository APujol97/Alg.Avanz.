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
public class Nodo {
    
    String palabra;
    ArrayList<String> palabras_propuestas;
    
    public Nodo(String palabra){
        this.palabra = palabra;
        this.palabras_propuestas = new ArrayList<>();
    }
    
    public void añadir_Lista(ArrayList<String> palabras){
        palabras_propuestas.addAll(palabras);
    }
    
    public void vaciar_lista(){
        palabras_propuestas.clear();
    }

    @Override
    public String toString() {
        return "Nodo{" + "palabra=" + palabra + ", palabras_propuestas=" + palabras_propuestas + "}\n";
    }

}
