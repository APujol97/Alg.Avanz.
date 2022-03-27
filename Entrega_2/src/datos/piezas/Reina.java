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
public class Reina extends Pieza {

    public Reina() {
        afectadimension = true; //se mueve en dimensión tablero
        imagen = "/imagenes/reina.png";
        movx = new int[0];
        movy = new int[0];
        nombre = this.getClass().getName();
    }

    public Reina(int d) {
        afectadimension = true; //se mueve en dimensión tablero
        nombre = this.getClass().getName();
        imagen = "/imagenes/reina.png";
        movx = new int[8];
        movy = new int[8];
        int pos = 0;
        movx[pos] = 0; // vertical
        movy[pos++] = 1; //vertical
        movx[pos] = 0; // vertical
        movy[pos++] = -1; //vertical
        movx[pos] = 1; // horizontal
        movy[pos++] = 0; //horizontal
        movx[pos] = -1; // horizontal
        movy[pos++] = 0; //horizontal
        movx[pos] = 1; //   oblicuo 1
        movy[pos++] = 1; //    oblicuo1
        movx[pos] = -1; //   oblicuo 1
        movy[pos++] = -1; //    oblicuo1
        movx[pos] = -1; //   oblicuo 2
        movy[pos++] = 1; //    oblicuo2
        movx[pos] = 1; //   oblicuo 2
        movy[pos++] = -1; //    oblicuo2
    }
}
