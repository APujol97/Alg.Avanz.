/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Principal.Eventos;
import Principal.Main;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Control extends Thread implements Eventos {
    
    private Main prog;
    private int type;
    private long tiempo;
    
    public Control(Main p) {
        prog = p;
    }
    
    public void run() {
        try {
            //Solo hay un caso de ejecución contemplado de momento, que es el de analizar la imagen png aportada
            System.out.println("FUNCIÓN NO IMPLEMENTADA");
           /* switch(this.type) { 
                case 1:
                    
                    break;
                    
                case 2: 
                    
                    break;
            }*/
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    @Override
    public void notificar(String s) {
        this.start();
    }
    
}
