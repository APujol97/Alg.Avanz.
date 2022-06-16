/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Colores.Paleta;

/**
 *
 * @author pujol
 */
public class Bandera {
    
    private String nombrePais;
    private Paleta colores;
    
    public Bandera(String pais, Paleta colores){
        this.nombrePais = pais;
        this.colores = colores;
    }
    
}
