/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega_5;

import Modelo.Datos;
import Modelo.Lectura;
import java.io.IOException;

/**
 *
 * @author pujol
 */
public class Main implements Eventos {

    //private Modelo mod;    // Puntero al Modelo
    //private Vista vis;    // Puntero a la Vista
    private Control con;  // puntero al Control

    /*
        Construcción del esquema MVC
     */
    private void inicio() throws IOException {
        //mod = new Modelo(this);
        con = null;
        //vis = new Vista("Entrega_4", this);
        //vis.mostrar();
    }

    public static void main(String[] args) throws IOException {
        (new Main()).inicio();
        Datos d= new Datos();
//        System.out.println(d.distanciaLevenshtein("saturday".toCharArray(), "sunday".toCharArray()));
//        Lectura lect = new Lectura("ca.dic");
//        lect.openFile();
//        System.out.println(lect.readPalabra());
//        lect.closeFile();
        d.detectarErrores("aquesta és una proba per al catalá ");
        
    }
 

    /*
        Función símple de la comunicación por eventos
     */
    @Override
    public void notificar(String s) {
        if(s.startsWith("Comprimir")) {
           //con = new Control(this);
            con.notificar(s); 
        } else if(s.startsWith("Descomprimir")) {
            //con = new Control(this);
            con.notificar(s); 
        } 
    }

    /*
        Método publico de retorno de la instancia del modelo de datos
    */
//    public Modelo getModel() {
//        return mod;
//    }
    
    /*
        Método publico de retorno de la instancia de la vista
    */
//    public Vista getView() {
//        return vis;
//    }
    
}
