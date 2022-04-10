/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mult_karatsuba;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author pujol
 */
public class Karatsuba {
    
    ArrayList<Integer> a,b;
    
    public Karatsuba(String a, String b){
        this.a = new ArrayList<>();
        this.b = new ArrayList<>();
        for(int i = 0; i < a.length(); i++){
            this.a.add(Integer.parseInt(a.substring(i, i+1)));
        }
        for(int i = 0; i < b.length(); i++){
            this.b.add(Integer.parseInt(b.substring(i, i+1)));
        }
    }
    
    private ArrayList<Integer> multiply(ArrayList<Integer> a, ArrayList<Integer> b){
        ArrayList<Integer> num1 = a;
        ArrayList<Integer> num2 = b;
        ArrayList<Integer> solucion;
        if(num1.size() < 3 || num2.size() < 3){
            int elem1 = Integer.parseInt(num1.toString().replace("[","").replace("]", "").replace(", ",""));
            int elem2 = Integer.parseInt(num2.toString().replace("[","").replace("]", "").replace(", ",""));
            return new ArrayList<>(Arrays.asList(elem1*elem2));
//              solucion = multiplicacion_nums(num1,num2);
//              return solucion;
        }
        //miramos el mínimo de los dígitos y calculamos índice de separación
        int digits = Math.min(num1.size(), num2.size());
        int separator = (int) Math.ceil((double)digits/2);
        //parte superior de los números
        ArrayList<Integer> high1 = new ArrayList<>();
        high1.addAll(num1.subList(0, separator));
        ArrayList<Integer> high2 = new ArrayList<>();
        high2.addAll(num2.subList(0, separator));
        //parte inferior de los números
        ArrayList<Integer> low1 = new ArrayList<>();
        low1.addAll(num1.subList(separator, num1.size()));
        ArrayList<Integer> low2 = new ArrayList<>();
        low2.addAll(num2.subList(separator, num2.size()));
        //parte intermedia de los números
        ArrayList<Integer> z0 = multiply(low1,low2);
        ArrayList<Integer> z1 = multiply(suma(low1,high1), suma(low2, high2));
        ArrayList<Integer> z2 = multiply(high1,high2);
        //devolvemos resultado de la multiplicación completo para iteración
        
        Collections.reverse(z0);
        Collections.reverse(z1);
        Collections.reverse(z2);
        solucion = suma(suma(multiplicacion_a_diez(z2,10^(separator*2)),z0), multiplicacion_a_diez(resta(z1, resta(z2,z0)), 10 ^ separator));
        
        Collections.reverse(solucion);
        return solucion; //devuelve arrayList inverso
    }
    
    private ArrayList<Integer> suma(ArrayList<Integer> i, ArrayList<Integer> j) {//Suma de dos numeros en un arraylist de little endian

        int auxiliar3;
        int acarreo = 0;
        ArrayList<Integer> solucion = new ArrayList<>();
        ArrayList<Integer> numero1;
        ArrayList<Integer> numero2;

        if (i.size() < j.size()) {//Aqui lo que hago es ver que elemento es mas grande, por si acaso pq no se si se puede dar este caso, para que el algoritmo sea el mismo
            numero1 = j;
            numero2 = i;
        } else {
            numero1 = i;
            numero2 = j;
        }

        for (int x = 0; x < numero1.size(); x++) {

            if (x < numero2.size()) {
                auxiliar3 = numero1.get(x) + numero2.get(x) + acarreo;
                acarreo = (int) auxiliar3 / 10;
                solucion.add(auxiliar3 % 10);
            } else {

                auxiliar3 = numero1.get(x) + acarreo;
                acarreo = (int) auxiliar3 / 10;
                solucion.add(auxiliar3 % 10);
            }

        }
        if (acarreo != 0) {
            solucion.add(acarreo);
            acarreo = 0;
        }
        
        //cambiamos el orden al big endian
        //Collections.reverse(solucion);

        return solucion;
    }
    
    private ArrayList<Integer> resta(ArrayList<Integer> i, ArrayList<Integer> j){
        
        int auxiliar3;
        int acarreo = 0;
        boolean cambiar_signo = false;
        ArrayList<Integer> solucion = new ArrayList<>();
        ArrayList<Integer> numero1 = i;
        ArrayList<Integer> numero2 = j;
        ArrayList<Integer> aux;
        
        if(numero1.get(numero1.size()-1) < 0 && numero2.get(numero2.size()-1) < 0){
            //si los dos numeros son negativos
            if(is_greater_than(numero2,numero1)){
                //si el numero 2 es más grande en términos absolutos que el número1, se le cambia el signo y cambiamos el orden de los operandos
                numero2.add(numero2.size()-1, - numero2.get(numero2.size()-1));
                aux = numero1;
                numero1 = numero2;
                numero2 = aux;
            } else {
                //se cambian los signos de los operandos, se restan, y luego se cambia el signo del resultado
                numero1.add(numero1.size()-1, - numero1.get(numero1.size()-1));
                numero2.add(numero2.size()-1, - numero2.get(numero2.size()-1));
                cambiar_signo = true;
            }
        } else if(numero1.get(numero1.size()-1) > 0 && numero2.get(numero2.size()-1) > 0){
            //los dos números son positivos
            if(is_greater_than(numero2,numero1)){
                //si el numero 2 es más grande que el número1, se le cambia el signo y cambiamos el orden de los operandos
                numero1.add(numero1.size()-1, - numero1.get(numero1.size()-1));
                aux = numero1;
                numero1 = numero2;
                numero2 = aux;
                cambiar_signo = true;
            } // sino, es la resta ideal de por sí (num grande positivo menos num pequeño negativo)
        } else if(numero1.get(numero1.size()-1) < 0 && numero2.get(numero2.size()-1) > 0){
            //si el primero es negativo y el segundo es positivo, se cambia de signo el primero, se suman, y el resultado se devuelve negado
            numero1.add(numero1.size()-1, - numero1.get(numero1.size()-1));
            solucion = suma(numero1,numero2);
            solucion.add(solucion.size()-1, - solucion.get(solucion.size()-1));
            return solucion;
        } else if(numero1.get(numero1.size()-1) > 0 && numero2.get(numero2.size()-1) < 0){
            //si el primero es positivo, y el segundo es negativo, se cambia el segundo de signo, se suman, y se devuelve el resultado
            numero2.add(numero2.size()-1, - numero2.get(numero2.size()-1));
            solucion = suma(numero1,numero2);
            return solucion;
        }

        for (int x = 0; x < numero1.size(); x++) {

            if (x < numero2.size()-1) {
                auxiliar3 = numero1.get(x) - numero2.get(x) + acarreo;
                if(auxiliar3 < 0){
                    auxiliar3 = 10 + auxiliar3;
                    acarreo = -1;
                } else if(auxiliar3 == 0){
                    acarreo = 0;
                }
                solucion.add(auxiliar3 % 10);
            } else {
                auxiliar3 = numero1.get(x) + acarreo;
                solucion.add(auxiliar3 % 10);
            }
        }
        if (cambiar_signo) {
            //cambiamos signo
            solucion.add(solucion.size()-1, - solucion.get(solucion.size()-1));
        }
        
        //cambiamos el orden al big endian
        //Collections.reverse(solucion);

        return solucion;
    }
    
    private ArrayList<Integer> multiplicacion_a_diez(ArrayList<Integer> i, int j) {
    //No hace multiplicar array ya que nunca se hace, solo multiplica por 10 elevado a algo, que es lo mismo qie poner ceros en el array 
        for (int k = 1; k < j; k = k * 10) {
            i.add(0, 0);
        }
        //cambiamos el orden al big endian
        //Collections.reverse(i);
        return i;
    }
    
    private boolean is_greater_than(ArrayList<Integer> i, ArrayList<Integer> j){
        //devuelve si i es mayor en términos absoulutos que j
        boolean result = false;
        if(i.size() > j.size()){
            //si i es más grande que j, devolver si i es positivo
            result = i.get(i.size()-1) > 0;
        } else if (i.size() < j.size()){
            //si j es más grande que i, devolver si j es positivo
            result = j.get(j.size()-1) > 0;
        } else if(i.size() == j.size()){
            //si tienen el mismo número de dígitos, mirar cuál tiene uno mayor
            result = Math.abs(i.get(i.size()-1)) > Math.abs(j.get(j.size()-1));
        }
        return result;
    }
    
    public String print_result(){
        return multiply(a,b).toString().replace("[","").replace("]", "").replace(", ","");
    }
    
}
