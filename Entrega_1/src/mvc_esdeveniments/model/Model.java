/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc_esdeveniments.model;

import mvc_esdeveniments.MVC_Esdeveniments;
import mvc_esdeveniments.MeuError;
import mvc_esdeveniments.PerEsdeveniments;

/**
 *
 * @author mascport
 */
public class Model implements PerEsdeveniments {

    private MVC_Esdeveniments prog;
    private int oldX, oldY, x, y;
    private int n;
    private int pixels;
    private int tipo=0;

    public Model(MVC_Esdeveniments p) {
        prog = p;
        oldX = oldY = x = y = 0;
        n = 0;
        pixels = 10;
    }

    public void setPixels(int pixels) {
        this.pixels = pixels;
    }

    public int getTipo() {
        return tipo;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setOldXY(int x, int y) {
        this.oldX = x;
        this.oldY = y;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public int getN() {
        return n;
    }
    
    public void resetVariables(){
        oldX = oldY = x = y = 0;
        n = 0;
    }

    private void calcularOn() {
        oldY = y;
        oldX = x;
        n++;
        tipo=1;
        
        long tiempo = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                MeuError.informaError(ex);
            }
        }
        y = (int)(System.currentTimeMillis()- tiempo);
        
        x = n * pixels;
    }

    private void calcularOlogn() {
        oldY = y;
        oldX = x;
        n++;
        tipo=2;
       
        long tiempo = System.currentTimeMillis();
        for (int j = n; j > 0; j = j / 2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                MeuError.informaError(ex);
            }
        }
        y = (int)(System.currentTimeMillis()- tiempo);

        x = n * pixels;
    }

    private void calcularOn2() {
        oldY = y;
        oldX = x;
        n++;
        tipo=3;
    
        long tiempo = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    MeuError.informaError(ex);
                }
            }
        }
        y = (int)(System.currentTimeMillis()- tiempo);
  
        x = n * pixels;
    }

    
    @Override
    public void notificar(String s) {
        if (s.startsWith("O(n)")) {
            this.calcularOn();
        } else if (s.startsWith("O(logn)")) {
            this.calcularOlogn();
        } else if (s.startsWith("O(n2)")) {
            this.calcularOn2();
        }
    }
}
