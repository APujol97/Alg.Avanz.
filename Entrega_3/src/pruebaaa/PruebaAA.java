/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaaa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PruebaAA {

    static int numeroCorte = 2;
    static int numeroPruebas = 1000;
    static double constante = 0;

    public static void main(String[] args) throws IOException {

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
    }

    public static ArrayList<Integer> toArrayList(String num) {
        String[] numero = num.split("");
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = numero.length - 1; i >= 0; i--) {

            res.add(Integer.parseInt(numero[i]));
        }
        return res;
    }

    private static ArrayList<Integer> multiplicaionNormal(ArrayList<Integer> numero1, ArrayList<Integer> numero2) {

        int acarreo = 0;
        int indice = -1;
        int aux;
        int auxiliar1;
        int auxiliar2;
        int auxiliar3;
        int posicion;
        ArrayList<Integer> num1;
        ArrayList<Integer> num2;
        ArrayList<Object[]> presuma = new ArrayList<>();
        ArrayList<Integer> multiplicacion = new ArrayList<>();
        ArrayList<Integer> auxiliar = new ArrayList<>();
        ArrayList<Integer> solucion = new ArrayList<>();

        if (numero1.size() < numero2.size()) {//Aqui lo que hago es ver que elemento es mas grande, por si acaso pq no se si se puede dar este caso, para que el algoritmo sea el mismo
            num1 = numero2;
            num2 = numero1;

        } else {
            num1 = numero1;
            num2 = numero2;
        }

        for (int i = 0; i < num2.size(); i++) {
            for (int j = 0; j < num1.size(); j++) {
                aux = num2.get(i) * num1.get(j);
                aux = aux + acarreo;
                acarreo = (int) aux / 10;
                multiplicacion.add(aux % 10);
            }
            if (acarreo != 0) {
                multiplicacion.add(acarreo);
                acarreo = 0;
            }
            presuma.add(multiplicacion.toArray());
            indice++;
            if (presuma.size() == 2) {
                auxiliar1 = presuma.get(0).length;
                auxiliar2 = presuma.get(1).length;
                posicion = auxiliar2 + indice;
                for (int j = 0; j < posicion; j++) {
                    if (indice > j) {
                        auxiliar.add((int) presuma.get(0)[j]);
                    } else {
                        if (j < auxiliar1) {
                            auxiliar3 = (int) presuma.get(0)[j] + (int) presuma.get(1)[j - indice] + acarreo;
                            acarreo = (int) auxiliar3 / 10;
                            auxiliar.add(auxiliar3 % 10);
                        } else {
                            auxiliar3 = (int) presuma.get(1)[j - indice] + acarreo;
                            acarreo = (int) auxiliar3 / 10;
                            auxiliar.add(auxiliar3 % 10);
                        }
                    }
                }
                if (acarreo != 0) {
                    auxiliar.add(acarreo);
                    acarreo = 0;
                }
                presuma.clear();
                presuma.add(auxiliar.toArray());
                auxiliar.clear();
            }
            multiplicacion.clear();
        }
        for (int i = 0; i < presuma.get(0).length; i++) {
            solucion.add((Integer) presuma.get(0)[i]);
        }
        while (solucion.get(solucion.size() - 1) == 0 && solucion.size() != 1) {
            //solucion.remove(solucion.size() - 1);
            solucion = new ArrayList<>(solucion.subList(0, solucion.size() - 1));
        }
        return solucion;
    }

    private static ArrayList<Integer> karatsuba(ArrayList<Integer> i, ArrayList<Integer> j) {

        ArrayList<Integer> auxiliar;
        ArrayList<Integer> a;
        ArrayList<Integer> b;
        ArrayList<Integer> c;
        ArrayList<Integer> d;

        ArrayList<Integer> primero;
        ArrayList<Integer> segundo;
        ArrayList<Integer> tercero;

        ArrayList<Integer> particion1;
        ArrayList<Integer> particion2;
        LT leer = new LT();

        int n;

        if (i.size() < 2 || j.size() < 2) {
            auxiliar = MultiplicacionPequeñaKaratsuba(i, j);
            return auxiliar;
        }

        n = Math.min(i.size(), j.size());
        n = (int) Math.floor(n / 2);

        b = new ArrayList<>(i.subList(0, n));

        d = new ArrayList<>(j.subList(0, n));

        a = new ArrayList<>(i.subList(n, i.size()));

        c = new ArrayList<>(j.subList(n, j.size()));

        primero = karatsuba(a, c);
        segundo = karatsuba(b, d);
        tercero = karatsuba(suma(a, b), suma(c, d));

//        System.out.println("--------------");
//        System.out.println("Parametro 1:" + i + "Parametro 2:" + j);
//
//        System.out.println("ALTO 1:" + a.toString());
//        System.out.println("BAJO 1:" + b.toString());
//        System.out.println("ALTO 2:" + c.toString());
//        System.out.println("BAJO 2:" + d.toString());
//
//        System.out.println("PRIMERO:" + primero);
//        System.out.println("SEGUNDO:" + segundo);
//        System.out.println("TERCERO:" + tercero);
//
//        System.out.println("resta(tercero, primero)");
//        particion1 = resta(tercero, primero);
//        System.out.println(particion1.toString());
//
//        System.out.println("resta(resta(tercero, primero), segundo)");
//        particion1 = resta(resta(tercero, primero), segundo);
//        System.out.println(particion1.toString());
//
//        System.out.println("multiplicaion(resta(resta(tercero, primero), segundo), (int) Math.pow(10, n))---" + (int) Math.pow(10, n));
//        particion1 = multiplicaion(resta(resta(tercero, primero), segundo), (int) Math.pow(10, n));
//        System.out.println(particion1.toString());
//
//        System.out.println("suma(multiplicaion(resta(resta(tercero, primero), segundo), (int) Math.pow(10, n)), segundo)---" + (int) Math.pow(10, n));
        particion1 = suma(multiplicaion(resta(resta(tercero, primero), segundo), n), segundo);
//        System.out.println("Ejemplo=" + particion1.toString());
//
//        System.out.println("multiplicaion(primero, (int) Math.pow(10, n * 2))---" + (int) Math.pow(10, n * 2));
        particion2 = multiplicaion(primero, n * 2);
//        System.out.println("Ejemplo1=" + particion2.toString());
//
//        System.out.println("--------------");
        // leer.lecturaCaracter();
        return suma(particion2, particion1);

    }

    private static ArrayList<Integer> multiplicaion(ArrayList<Integer> i, int j) {//No hace mulyiplicar array ya que nunca se hace, solo multiplicac por 10 elevado a algo, que es lo mismo qie poner ceros en el array 
        boolean aux = false;
        if (i.get(i.size() - 1) == -1) {//Miramos si el primer numero es negativo y le quitamos el signo
            //i.remove(0);
            i = new ArrayList<>(i.subList(0, i.size() - 1));
            aux = true;
        }
        Collections.reverse(i);//Esto es porque cuando haces el add con 2 parametros es mas cosatoso el add con 1 parametro
        for (int k = 0; k < j; k++) {
            i.add(0);
        }
        Collections.reverse(i);
        if (aux) {
            i.add(-1);
        }
        return i;
    }

    private static ArrayList<Integer> suma(ArrayList<Integer> i, ArrayList<Integer> j) {//Suma de dos numeros en un array

        boolean auxiliar2;
        boolean cambio = false;
        ArrayList<Integer> solucion = new ArrayList<>();
        ArrayList<Integer> numero1;
        ArrayList<Integer> numero2;
        boolean negativo1, negativo2;

        if (i.get(i.size() - 1) == -1) {//Miramos si el primer numero es negativo y le quitamos el signo
            negativo1 = true;
            // i.remove(0);
            i = new ArrayList<>(i.subList(0, i.size() - 1));
        } else {
            negativo1 = false;
        }
        if (j.get(j.size() - 1) == -1) {//Miramos si el segundo numero es negativo y le quitamos el signo
            negativo2 = true;
            //j.remove(0);
            j = new ArrayList<>(j.subList(0, j.size() - 1));
        } else {
            negativo2 = false;
        }

        if (i.size() < j.size()) {//Aqui lo que hago es ver que elemento es mas grande, por si acaso pq no se si se puede dar este caso, para que el algoritmo sea el mismo
            numero1 = new ArrayList<>(j);
            numero2 = new ArrayList<>(i);
            cambio = true;

        } else {
            numero1 = new ArrayList<>(i);
            numero2 = new ArrayList<>(j);
        }

        if ((negativo1 && negativo2)) {//Caso en el que los 2 numeros son negativos
            solucion = OperacionSuma(numero1, numero2);
            solucion.add(-1);
        }

        if ((!negativo1 && !negativo2)) {//Caso en el que los 2 numeros son positivos
            solucion = OperacionSuma(numero1, numero2);

        }

        if ((negativo1 && !negativo2)) {//Caso en el que el primer numero es positivo y el segundo es negativo
            solucion = resta(numero1, numero2);
            if (!cambio) {
                solucion.add(-1);
            }
        }

        if ((!negativo1 && negativo2)) {//Caso en el que el primer numero es positivo y el segunddo es negativo
            solucion = resta(numero1, numero2);
            if (cambio) {
                solucion.add(-1);
            }
        }

        return solucion;
    }

    private static ArrayList<Integer> resta(ArrayList<Integer> i, ArrayList<Integer> j) {

        boolean cambio = false;

        ArrayList<Integer> solucion = new ArrayList<>();
        ArrayList<Integer> numero1;
        ArrayList<Integer> numero2;
        boolean negativo1;
        boolean negativo2;
        boolean fin = false;
        boolean numeroigual = true;

        if (i.get(i.size() - 1) == -1) {//Miramos si el primer numero es negativo y le quitamos el signo
            negativo1 = true;
            //i.remove(0);
            i = new ArrayList<>(i.subList(0, i.size() - 1));
        } else {
            negativo1 = false;
        }
        if (j.get(j.size() - 1) == -1) {//Miramos si el segundo numero es negativo y le quitamos el signo
            negativo2 = true;
            //j.remove(0);
            j = new ArrayList<>(j.subList(0, j.size() - 1));
        } else {
            negativo2 = false;
        }

        if (i.size() != j.size()) {//Si los tamaños de los array son diferentes

            if (i.size() < j.size()) {//Ahora miramos que tamaño tienen los array para poder decir cual es numero 1 y cual el nuimero2 

                cambio = true;
                numero1 = new ArrayList<>(j);
                numero2 = new ArrayList<>(i);

            } else {
                numero1 = new ArrayList<>(i);
                numero2 = new ArrayList<>(j);
            }

        } else {

            for (int k = i.size() - 1; k > 0 && !fin; k--) {
                if (i.get(k) > j.get(k)) {
                    fin = true;
                    numeroigual = true;
                }
                if (i.get(k) < j.get(k)) {
                    fin = true;
                    numeroigual = false;
                }
            }

            if (numeroigual) {
                numero1 = new ArrayList<>(i);
                numero2 = new ArrayList<>(j);

            } else {
                numero1 = new ArrayList<>(j);
                numero2 = new ArrayList<>(i);
                cambio = true;

            }
        }

        if ((negativo1 && negativo2)) {//Caso en el que los 2 numeros son negativos
            solucion = OperacionResta(numero1, numero2);
            if (!cambio) {//Si no ha habido cambio de numeros cambiamos el signo
                solucion.add(-1);
            }
        }

        if ((!negativo1 && !negativo2)) {//Caso en el que los 2 numeros son positivos
            solucion = OperacionResta(numero1, numero2);
            if (cambio) {
                solucion.add(-1);
            }
        }

        if ((negativo1 && !negativo2)) {//Caso en el que el primer numero es positivo y el segundo es negativo
            solucion = OperacionSuma(numero1, numero2);
            solucion.add(-1);
        }

        if ((!negativo1 && negativo2)) {//Caso en el que el primer numero es positivo y el segunddo es negativo
            solucion = OperacionSuma(numero1, numero2);
        }

        return solucion;
    }

    private static ArrayList<Integer> OperacionResta(ArrayList<Integer> numero1, ArrayList<Integer> numero2) {

        int auxiliar3;
        int acarreo = 0;
        ArrayList<Integer> solucion = new ArrayList<>();

        for (int x = 0; x < numero1.size(); x++) {
            if (x < numero2.size()) {
                auxiliar3 = numero1.get(x) - acarreo;
                if (auxiliar3 >= numero2.get(x)) {
                    auxiliar3 = auxiliar3 - numero2.get(x);
                    acarreo = 0;
                    solucion.add(auxiliar3);
                } else {
                    acarreo = 1;
                    auxiliar3 = auxiliar3 + 10;
                    auxiliar3 = auxiliar3 - numero2.get(x);
                    solucion.add(auxiliar3);
                }
            } else {
                auxiliar3 = numero1.get(x) - acarreo;
                acarreo = 0;
                solucion.add(auxiliar3);
            }
        }

        while (solucion.get(solucion.size() - 1) == 0 && solucion.size() != 1) {
            //solucion.remove(solucion.size() - 1);
            solucion = new ArrayList<>(solucion.subList(0, solucion.size() - 1));
        }

        return solucion;
    }

    private static ArrayList<Integer> OperacionSuma(ArrayList<Integer> numero1, ArrayList<Integer> numero2) {
        int auxiliar3;
        int acarreo = 0;
        ArrayList<Integer> solucion = new ArrayList<>();

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

        while (solucion.get(solucion.size() - 1) == 0 && solucion.size() != 1) {
            //solucion.remove(solucion.size() - 1);
            solucion = new ArrayList<>(solucion.subList(0, solucion.size() - 1));
        }
        return solucion;
    }

    private static ArrayList<Integer> MultiplicacionPequeñaKaratsuba(ArrayList<Integer> i, ArrayList<Integer> j) {

        ArrayList<Integer> solucion = new ArrayList<>();
        ArrayList<Integer> numero1;
        ArrayList<Integer> numero2;
        int acarreo = 0;
        int auxiliar3 = 0;

        if (i.size() < j.size()) {//Aqui lo que hago es ver que elemento es mas grande, por si acaso pq no se si se puede dar este caso, para que el algoritmo sea el mismo
            numero1 = new ArrayList<>(j);
            numero2 = new ArrayList<>(i);

        } else {
            numero1 = new ArrayList<>(i);
            numero2 = new ArrayList<>(j);
        }

        for (int x = 0; x < numero1.size(); x++) {
            auxiliar3 = numero1.get(x) * numero2.get(0) + acarreo;
            acarreo = (int) auxiliar3 / 10;
            solucion.add(auxiliar3 % 10);
        }
        if (acarreo != 0) {
            solucion.add(acarreo);
            acarreo = 0;
        }
        while (solucion.get(solucion.size() - 1) == 0 && solucion.size() != 1) {
            //solucion.remove(solucion.size() - 1);
            solucion = new ArrayList<>(solucion.subList(0, solucion.size() - 1));
        }
        return solucion;
    }

    private static ArrayList<Integer> funcionMitxa(ArrayList<Integer> i, ArrayList<Integer> j) throws FileNotFoundException, IOException {
        File archivo = new File("PuntoCorte.txt");

        if (!archivo.exists()) {

            FileWriter fw = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(fw);
            calculoCorte();
            bw.write(numeroCorte);

            bw.close();
            fw.close();
            
        } else {
            
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            numeroCorte = br.read();
            br.close();
            fr.close();
            
        }
        return AlgoritmoMitxo(i, j);
    }

    private static ArrayList<Integer> AlgoritmoMitxo(ArrayList<Integer> i, ArrayList<Integer> j) {
        ArrayList<Integer> auxiliar;
        ArrayList<Integer> a;
        ArrayList<Integer> b;
        ArrayList<Integer> c;
        ArrayList<Integer> d;

        ArrayList<Integer> primero;
        ArrayList<Integer> segundo;
        ArrayList<Integer> tercero;

        ArrayList<Integer> particion1;
        ArrayList<Integer> particion2;
        LT leer = new LT();

        int n;

        if (i.size() < numeroCorte || j.size() < numeroCorte) {
            auxiliar = multiplicaionNormal(i, j);
            return auxiliar;
        }

        n = Math.min(i.size(), j.size());
        n = (int) Math.floor(n / 2);

        b = new ArrayList<>(i.subList(0, n));

        d = new ArrayList<>(j.subList(0, n));

        a = new ArrayList<>(i.subList(n, i.size()));

        c = new ArrayList<>(j.subList(n, j.size()));

        primero = karatsuba(a, c);
        segundo = karatsuba(b, d);
        tercero = karatsuba(suma(a, b), suma(c, d));

//        System.out.println("--------------");
//        System.out.println("Parametro 1:" + i + "Parametro 2:" + j);
//
//        System.out.println("ALTO 1:" + a.toString());
//        System.out.println("BAJO 1:" + b.toString());
//        System.out.println("ALTO 2:" + c.toString());
//        System.out.println("BAJO 2:" + d.toString());
//
//        System.out.println("PRIMERO:" + primero);
//        System.out.println("SEGUNDO:" + segundo);
//        System.out.println("TERCERO:" + tercero);
//
//        System.out.println("resta(tercero, primero)");
//        particion1 = resta(tercero, primero);
//        System.out.println(particion1.toString());
//
//        System.out.println("resta(resta(tercero, primero), segundo)");
//        particion1 = resta(resta(tercero, primero), segundo);
//        System.out.println(particion1.toString());
//
//        System.out.println("multiplicaion(resta(resta(tercero, primero), segundo), (int) Math.pow(10, n))---" + (int) Math.pow(10, n));
//        particion1 = multiplicaion(resta(resta(tercero, primero), segundo), (int) Math.pow(10, n));
//        System.out.println(particion1.toString());
//
//        System.out.println("suma(multiplicaion(resta(resta(tercero, primero), segundo), (int) Math.pow(10, n)), segundo)---" + (int) Math.pow(10, n));
        particion1 = suma(multiplicaion(resta(resta(tercero, primero), segundo), n), segundo);
//        System.out.println("Ejemplo=" + particion1.toString());
//
//        System.out.println("multiplicaion(primero, (int) Math.pow(10, n * 2))---" + (int) Math.pow(10, n * 2));
        particion2 = multiplicaion(primero, n * 2);
//        System.out.println("Ejemplo1=" + particion2.toString());
//
//        System.out.println("--------------");
        // leer.lecturaCaracter();
        return suma(particion2, particion1);

    }

    private static void calculoCorte() {

        long tiempo;
        double mediaKaratusba = 0, constanteKaratsuba = 0;
        double mediaNormal = 0, constanteNormal = 0;
        String numero1 = "";
        String numero2 = "";
        ArrayList<Integer> num1;
        ArrayList<Integer> num2;
        Random ran = new Random();

        for (int i = 0; i < numeroPruebas; i++) {
            numero1 = numero1 + String.valueOf(ran.nextInt(9));
        }
        for (int i = 0; i < numeroPruebas; i++) {
            numero2 = numero2 + String.valueOf(ran.nextInt(9));
        }
        num1 = toArrayList(numero1);
        num2 = toArrayList(numero2);

        Collections.reverse(num1);
        Collections.reverse(num2);

        for (int k = 0; k < 5; k++) {
            tiempo = System.currentTimeMillis();
            karatsuba(num2, num1);
            tiempo = System.currentTimeMillis() - tiempo;
            mediaKaratusba += tiempo;
        }

        for (int k = 0; k < 5; k++) {
            tiempo = System.currentTimeMillis();
            multiplicaionNormal(num1, num2);
            tiempo = System.currentTimeMillis() - tiempo;
            mediaNormal += tiempo;
        }

        mediaKaratusba = mediaKaratusba / 5;
        mediaNormal = mediaNormal / 5;

        constanteKaratsuba = mediaKaratusba / Math.pow(numeroPruebas, ((Math.log(3.0) / Math.log(2.0))));
        constanteNormal = mediaNormal / Math.pow(numeroPruebas, 2);

        constante = (constanteKaratsuba / constanteNormal);
        numeroCorte = (int) bolzano(1, 1000000000);
    }

    private static double bolzano(double a, double b) {
        double m = (a + b) / 2;
        double vm = funcio(m);
        double vb = funcio(b);
        if (Math.abs(vm) < 0.0000001) {
            return m;
        } else {
            if (((vb > 0) && (vm > 0)) || ((vb < 0) && (vm < 0))) {
                return bolzano(a, m);
            } else {
                return bolzano(m, b);
            }
        }
    }

    private static double funcio(double n) {
        double res;
        res = (Math.pow(n, 2) / Math.pow(n, ((Math.log(3.0) / Math.log(2.0))))) - constante;
        return res;
    }
}
