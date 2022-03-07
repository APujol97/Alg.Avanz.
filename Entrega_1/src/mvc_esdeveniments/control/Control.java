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
    private int iteraciones=32;

    public Control(MVC_Esdeveniments p) {
        prog = p;
    }

    public void run() {
        switch (tipo) {
            case 1:
                prog.getModel().setTipo(1);
                for (int i = 0; i < iteraciones; i++) {
                    prog.getModel().notificar("O(n)");
                    espera(1000 / 25, 0);
                }
                System.out.println("Fin del calculo 0(n) ");
                prog.getModel().resetVariables();
                
                break;
            case 2:
                prog.getModel().setTipo(2);
                for (int i = 0; i < iteraciones; i++) {
                    prog.getModel().notificar("O(logn)");
                    espera(1000 / 25, 0);
                }
                prog.getModel().resetVariables();
                System.out.println("Fin del calculo 0(logn)");
                break;
            case 3:
                prog.getModel().setTipo(3);
                for (int i = 0; i < iteraciones; i++) {
                    prog.getModel().notificar("O(n2)");
                    espera(1000 / 25, 0);
                }
                prog.getModel().resetVariables();
                System.out.println("Fin del calculo 0(n2)");
                break;
            case 4:
                prog.getModel().setTipo(4);
                for (int i = 0; i < iteraciones; i++) {
                    prog.getModel().notificar("O(nlogn)");
                    espera(1000 / 25, 0);
                }
                prog.getModel().resetVariables();
                System.out.println("Fin del calculo 0(nlogn)");
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
        } else if (s.startsWith("O(nlogn)")) {
            tipo = 4;
            this.start();
        }
    }
}
