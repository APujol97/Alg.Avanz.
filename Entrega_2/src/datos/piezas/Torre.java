/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos.piezas;

/**
 *
 * @author mascport
 */
public class Torre extends Pieza {

    public Torre() {
        afectadimension = true; //se mueve en dimensión tablero
        nombre = this.getClass().getName();
        imagen = "/imagenes/torre.png";
        movx = new int[0];
        movy = new int[0];
    }

    public Torre(int d) {
        afectadimension = true; //se mueve en dimensión tablero
        nombre = this.getClass().getName();
        imagen = "/imagenes/torre.png";
        movx = new int[4];
        movy = new int[4];
        int pos = 0;
        movx[pos] = 0; // vertical
        movy[pos++] = 1; //vertical
        movx[pos] = 0; // vertical
        movy[pos++] = -1; //vertical
        movx[pos] = 1; // horizontal
        movy[pos++] = 0; //horizontal
        movx[pos] = -1; // horizontal
        movy[pos++] = 0; //horizontal
    }
}
