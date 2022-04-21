/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Control.Control;
import Modelo.Modelo;
import Vista.Vista;
import Modelo.LT;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Main implements Eventos {

    private Modelo mod;    // Puntero al Modelo
    private Vista vis;    // Puntero a la Vista
    private Control con;  // puntero al Control

    /*
        Construcció de l'esquema MVC
     */
    private void inicio() {
        mod = new Modelo(this);
        con = null;
        vis = new Vista("Entrega_1", this);
        vis.mostrar();
    }

    public static void main(String[] args) {
        String numero1 = "";
        String numero2 = "";
        int opcion = 1;
        long tiempo;

        ArrayList<Integer> num1 = new ArrayList<>();
        ArrayList<Integer> num2 = new ArrayList<>();
        ArrayList<Integer> ejemplo;
        ArrayList<Integer> ejemplo1;
        ArrayList<Integer> ejemplo2;

        LT leer = new LT();
        Random ran = new Random();
        
        if (opcion == 0) {
            System.out.println("Introduzca el primer numero : ");
            numero1 = leer.lecturaLinea();
            System.out.println("Introduzca el segundo numero : ");
            numero2 = leer.lecturaLinea();

            for (int i = 0; i < numero1.length() - 1; i++) {
                num1.add((int) numero1.charAt(i) - 48);
            }
            for (int i = 0; i < numero2.length() - 1; i++) {
                num2.add((int) numero2.charAt(i) - 48);
            }
        } else {
            for (int i = 0; i < 10550; i++) {
                numero1 = numero1 + String.valueOf(ran.nextInt(9));
            }
            for (int i = 0; i < 10550; i++) {
                numero2 = numero2 + String.valueOf(ran.nextInt(9));
            }
            num1 = toArrayList(numero1);
            num2 = toArrayList(numero2);
        }

        Collections.reverse(num1);
        Collections.reverse(num2);

        System.out.println("------------------------------------");

        tiempo = System.currentTimeMillis();
        ejemplo = multiplicaionNormal(num1, num2);
        tiempo = System.currentTimeMillis() - tiempo;
        Collections.reverse(ejemplo);
        System.out.println("Resultado de la multiplicaion normal");
        System.out.println("->" + ejemplo.toString().replace(", ", ""));
        System.out.println("Tiempo en ms " + tiempo);

        tiempo = System.currentTimeMillis();
        ejemplo1 = karatsuba(num1, num2);
        tiempo = System.currentTimeMillis() - tiempo;
        Collections.reverse(ejemplo1);
        System.out.println("Resultado de la multiplicaion de Karatsuba");
        System.out.println("->" + ejemplo1.toString().replace(", ", ""));
        System.out.println("Tiempo en ms " + tiempo);

//        ejemplo2 = resta(ejemplo, ejemplo1);
//        Collections.reverse(ejemplo2);
//        System.out.println("Resta para comprobar que las dos operaciones dan el mismo resultado");
//        System.out.println("Solucion ->" + ejemplo2.toString().replace(", ", ""));

        tiempo = System.currentTimeMillis();
        ejemplo = funcionMitxa(num1, num2);
        tiempo = System.currentTimeMillis() - tiempo;
        Collections.reverse(ejemplo);
        System.out.println("Resultado del algoritmo mitxo");
        System.out.println("->" + ejemplo.toString().replace(", ", ""));
        System.out.println("Tiempo en ms " + tiempo);
        
        System.out.println("------------------------------------");
        //(new Main()).inicio();
    }

    /*
        Función símple de la comunicació por eventos
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
        } else if(s.startsWith("O(nlogn)")){
                con = new Control(this);
                con.notificar(s);
        }
        
    }

    /*
        Método public de retorno de la instancia del modelo de dades
    */
    public Modelo getModel() {
        return mod;
    }
}
