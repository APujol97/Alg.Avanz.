/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega_5;

import Modelo.Datos;
import Modelo.Lectura;
import java.io.IOException;

/**
 *
 * @author pujol
 */
public class Main implements Eventos {

    //private Modelo mod;    // Puntero al Modelo
    //private Vista vis;    // Puntero a la Vista
    private Control con;  // puntero al Control

    /*
        Construcción del esquema MVC
     */
    private void inicio() throws IOException {
        //mod = new Modelo(this);
        con = null;
        //vis = new Vista("Entrega_4", this);
        //vis.mostrar();
    }

    public static void main(String[] args) throws IOException {
        (new Main()).inicio();
        Datos d = new Datos();
       // String texto = "En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero, adarga antigua, rocín flaco y galgo corredor. Una olla de algo más vaca que carnero, salpicón las más noches, duelos y quebrantos los sábados, lantejas los viernes, algún palomino de añadidura los domingos, consumían las tres partes de su hacienda. El resto della concluían sayo de velarte, calzas de velludo para las fiestas, con sus pantuflos de lo mesmo, y los días de entresemana se honraba con su vellorí de lo más fino. Tenía en su casa una ama que pasaba de los cuarenta, y una sobrina que no llegaba a los veinte, y un mozo de campo y plaza, que así ensillaba el rocín como tomaba la podadera. Frisaba la edad de nuestro hidalgo con los cincuenta años; era de complexión recia, seco de carnes, enjuto de rostro, gran madrugador y amigo de la caza. Quieren decir que tenía el sobrenombre de Quijada, o Quesada, que en esto hay alguna diferencia en los autores que deste caso escriben; aunque por conjeturas verosímiles se deja entender que se llamaba Quijana. Pero esto importa poco a nuestro cuento: basta que en la narración dél no se salga un punto de la verdad.";
        //String texto = "El Tirant lo Blanc és l'obra més representativa de les lletres valencianes, una novel·la que transcendeix de la simple narració de cavalleries per oferir-nos l'univers i la cultura de l'home medieval. El seu autor, Joanot Martorell, cavaller valencià del segle XV, ens va llegar una obra riquíssima, presentant-nos la figura d'un heroi que aconsegueix el somni de la cavalleria medieval: l'alliberament de Constantinoble de mans dels turcs i la conversió al cristianisme de tota la Mediterrània. Un cavaller model de virtuts, valentia i força, però, a diferència del poder meravellós i exagerat d'altres herois literaris, és també un cavaller humanitzat, capaç de sentir i patir els efectes de l'amor que l'inspira la princesa Carmesina.";
        String texto = "Mr. Jones, of the Manor Farm, had locked the hen-houses for the night, but was too drunk to remember to shut the pop-holes. With the ring of light from his lantern dancing from side toside, he lurched across the yard, kicked off his boots at the back door, drew himself a last beer from the barrel in the scullery, and made his way up to bed, where Mrs. Joneswas already snoring.";
        String idioma;
        
        idioma = d.detectarIdioma(texto);
        System.out.println("El idoma es: " + idioma);
        d.detectarErrores(texto, idioma);

    }


    /*
        Función símple de la comunicación por eventos
     */
    @Override
    public void notificar(String s) {
        if (s.startsWith("Comprimir")) {
            //con = new Control(this);
            con.notificar(s);
        } else if (s.startsWith("Descomprimir")) {
            //con = new Control(this);
            con.notificar(s);
        }
    }

    /*
        Método publico de retorno de la instancia del modelo de datos
     */
//    public Modelo getModel() {
//        return mod;
//    }
    /*
        Método publico de retorno de la instancia de la vista
     */
//    public Vista getView() {
//        return vis;
//    }
}
