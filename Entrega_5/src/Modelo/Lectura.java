/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pujol
 */
public class Lectura {
    
    private BufferedReader BR;
    String[] nombreFichero;
    public File fichero;
    
    
    public Lectura(String nombre){
        fichero = new File(nombre);
    }
    
    public void openFile(){
        try {
            BR = new BufferedReader(new FileReader(fichero));
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: EL FICHERO NO EXISTE");
        }
    }
    
    public void closeFile(){
        try {
            BR.close();
        } catch (IOException ex) {
            System.out.println("ERROR: EL FICHERO NO EXISTE");
        }
    }
    
    public String readPalabra(){
        try {
            return BR.readLine();
        } catch (IOException ex) {
            System.out.println("ERROR: EL FICHERO NO EXISTE");
        }
        return null;
    }
    
    public boolean isNotEOF(){
        try {
            return BR.ready();
        } catch (IOException ex) {
            System.out.println("ERROR: EL FICHERO NO EXISTE");
        }
        return true;
    }
}
