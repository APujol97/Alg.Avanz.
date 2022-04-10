/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mult_karatsuba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pujol
 */
public class Mult_Karatsuba {

    /**
     * @param args the command line arguments
     */
    
    String a,b;
    
    public void init() throws IOException{
        System.out.print("Inserte número 1: ");
        a = (new BufferedReader(new InputStreamReader(System.in))).readLine();
        System.out.print("\nInserte número 2: ");
        b = (new BufferedReader(new InputStreamReader(System.in))).readLine();
        //System.out.println(Integer.parseInt(a));
        System.out.print("\nResultado Karatsuba:"+(new Karatsuba(a,b)).print_result()+"\n");
    }
    
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            (new Mult_Karatsuba()).init();
        } catch (IOException ex) {
            Logger.getLogger(Mult_Karatsuba.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
