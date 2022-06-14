/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Principal.Eventos;
import Principal.Main;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Modelo implements Eventos {
    
    private Main prog;
    
    public Modelo(Main p) {
        prog = p;
    }
    
    @Override
    public void notificar(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
