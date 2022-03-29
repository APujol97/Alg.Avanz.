/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npiezas;

import datos.Datos;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mascport
 */
public class Juego extends Thread {

    private Datos dat;
    private Notificar prog;
    private int soluciones;
    private boolean continuar = true;

    public Juego(Datos d, Notificar n) {
        dat = d;
        prog = n;
        
    }

    @Override
    public void run() {
        soluciones = 0;
        long tiempo = System.nanoTime();
        if (dat.getSolucion() == null) {
            dat.createSolucion();
            dat.ponPieza(0, 0);
            dat.setValorSolucion(0, 0, 1);
        }
        ponerPieza(1, dat.getxIni(), dat.getyIni());
        if (soluciones == 0) {
            prog.notificar("ponalerta:Solución no encontrada");
        } else {
            tiempo = System.nanoTime() - tiempo;
            tiempo = tiempo / 1000000;
            String aux = "He tardado: " + tiempo + " milisegundos";
            prog.notificar("ponalerta:" + aux);
        }
    }

    private void ponerPieza(int n, int x, int y) {

        if (n == dat.getDimension() * dat.getDimension()) {
            prog.notificar("repintar");
            soluciones++;

            continuar = false;
        }
        for (int i = 0; i < dat.getNumMovs() && continuar; i++) {
            int sh = x + dat.getMovX(i);
            int sv = y + dat.getMovY(i);
            if (sh >= 0 && sv >= 0) {
                if (sh < dat.getDimension() && sv < dat.getDimension()) {
                    if (!dat.hayPieza(sh, sv)) {
                        dat.setValorSolucion(sh, sv, n + 1);
                        dat.ponPieza(sh, sv);
                        ponerPieza(n + 1, sh, sv);
                        dat.quitaPieza(sh, sv);
                        dat.setValorSolucion(sh, sv, -1);
                    }
                }
            }
        }

    }

    private void esperar() {
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDatos(Datos d) {
        dat = d;
    }
}
