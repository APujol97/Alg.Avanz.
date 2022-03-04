/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc_esdeveniments;

import mvc_esdeveniments.control.Control;
import mvc_esdeveniments.model.Model;
import mvc_esdeveniments.vista.Vista.Vista;

/**
 *
 * @author mascport
 */
public class MVC_Esdeveniments implements PerEsdeveniments {

    private Model mod;    // Punter al Model del patró
    private Vista vis;    // Punter a la Vista del patró
    private Control con;  // punter al Control del patró

    /*
        Construcció de l'esquema MVC
     */
    private void inicio() {
        mod = new Model(this);
        con = null;
        vis = new Vista("Entrega_1", this);
        vis.mostrar();
    }

    public static void main(String[] args) {
        (new MVC_Esdeveniments()).inicio();
    }

    /*
        Funció símple de la comunicació per Patró d'esdeveniments
     */
    @Override
    public void notificar(String s) {
        
        if(s.startsWith("O(n)")){
                con = new Control(this);
                con.notificar(s); 
        } else if(s.startsWith("O(logn)")){
                con = new Control(this);
                con.notificar(s);
        } else if(s.startsWith("O(n2)")){
                con = new Control(this);
                con.notificar(s);
        }
        
    }

    /*
        Mètode public de retorn de la instància del model de dades
    */
    public Model getModel() {
        return mod;
    }
}
