/***************************************************************************
FUNCIONALIDAD: funcionalidades para llevar a cabo la lectura de datos desde 
el teclado
 ***************************************************************************/
package pruebaaa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LT {

    private static String secuencia="";
    private static int indice = 0;

    private static char[] lecturaln() {
        String res = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(System.in, "ISO-8859-1"));
            res = br.readLine();
        } catch (IOException e) {
        }
        return res.toCharArray();
    }

    // Lectura secuencia de caracteres finalizada con el caracter '.'
    public static char lecturaCaracterSecuencia() {
        int elemento = 0;
        if (indice == 0) {
            secuencia = lecturaLinea();
        }
        elemento = indice;
        indice = ((indice==(secuencia.length()-1))||
                (secuencia.charAt(indice) == '.'))? 0 : (indice + 1);
        return (secuencia.charAt(elemento));

    }

    // Lectura de número entero.
    public static Integer lecturaEntero() {
        Integer res = 0;
        try {
            char[] pal = lecturaln();
            res = Integer.parseInt(new String(pal));
        } catch (NumberFormatException e) {
            res = null;
        }
        return res;
    }

    // Lectura número real.
    public static Double lecturaReal() {
        Double res = 0.0;
        try {
            char[] pal = lecturaln();
            res = Double.parseDouble(new String(pal));
        } catch (NumberFormatException e) {
            res = null;
        }
        return res;
    }

    // Lectura linea de caracteres.
    public static String lecturaLinea() {
        String res;
        char[] pal = lecturaln();
        res = new String(pal)+'\n';
        return res;
    }

    // Lectura de caracter.
    public static Character lecturaCaracter() {
        Character res;
        char[] pal = lecturaln();
        String s = new String(pal);
        if (s.length() == 0) {
            res = null;
        } else {
            res = new Character(s.charAt(0));
        }
        return res;
    }

}