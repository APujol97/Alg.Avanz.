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
public class Bomba3 extends Pieza {

    public Bomba3() {
        nombre = this.getClass().getName();
        imagen = "/imagenes/bomba3.png";
        movx = new int[48];  // 49 movimientos menos el (0,0)
        movy = new int[48];
        int pos = 0;
        for (int i = -3; i < 4; i++) {
            for (int j = -3; j < 4; j++) {
                if ((i != 0) || (j != 0)) {
                    movx[pos] = i;
                    movy[pos++] = j;
                }
            }
        }
    }
}
