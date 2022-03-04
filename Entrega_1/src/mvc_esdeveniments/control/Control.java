/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc_esdeveniments.control;

import mvc_esdeveniments.MVC_Esdeveniments;
import mvc_esdeveniments.MeuError;
import mvc_esdeveniments.PerEsdeveniments;

/**
 *
 * @author mascport
 */
public class Control extends Thread implements PerEsdeveniments {

    private MVC_Esdeveniments prog;
    private int tipo;

    public Control(MVC_Esdeveniments p) {
        prog = p;
    }

    public void run() {
        switch (tipo) {
            case 1:
                for (int i = 0; i < 32; i++) {
                    prog.getModel().notificar("O(n)");
                    espera(1000 / 25, 0);
                }
                prog.getModel().resetVariables();
                
                break;
            case 2:
                for (int i = 0; i < 32; i++) {
                    prog.getModel().notificar("O(logn)");
                    espera(1000 / 25, 0);
                }
                prog.getModel().resetVariables();
                break;
            case 3:
                for (int i = 0; i < 32; i++) {
                    prog.getModel().notificar("O(n2)");
                    espera(1000 / 25, 0);
                }
                prog.getModel().resetVariables();
                break;
            default:
                break;
        }

    }

    private void espera(long m, int n) {
        try {
            Thread.sleep(m, n);
        } catch (Exception e) {
            MeuError.informaError(e);
        }
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("O(n)")) {
            tipo = 1;
            this.start();
        } else if (s.startsWith("O(logn)")) {
            tipo = 2;
            this.start();
        } else if (s.startsWith("O(n2)")) {
            tipo = 3;
            this.start();
        }
    }
}
