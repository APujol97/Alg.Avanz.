/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author pujol
 */
public class Central extends JPanel implements Observer {

    private StyleContext corr;
    private Style correctas;
    private StyleContext erro;
    private Style erroneas;
    private DefaultStyledDocument document;
    private JTextPane panel;
    private final int ancho = 800;
    private final int alto = 600;

    public Central() {
        document = new DefaultStyledDocument();
        panel = new JTextPane(document);
        panel.setPreferredSize(new Dimension(ancho, alto));
        this.add(panel);
        corr = new StyleContext();
        correctas = corr.addStyle("test", null);
        StyleConstants.setForeground(correctas, Color.black);
        StyleConstants.setBold(correctas, true);
        StyleConstants.setFontSize(correctas, 18);
        erro = new StyleContext();
        erroneas = erro.addStyle("test", null);
        StyleConstants.setBold(erroneas, true);
        StyleConstants.setFontSize(erroneas, 18);
        StyleConstants.setForeground(erroneas, Color.red);
        simulaInsercion();
        //ademas las posiciones de los carácteres son independientes del estilo
    }

    private void simulaInsercion() {
        try {
            String txt = "IMPLEMENTACIÓN DE UN CORRECTOR INTERACTIVO\n"
                    + "En un lugar de la mancha ...Esta es una frase correcta "
                    + "kon una palabra errónea.";
            document.insertString(0, txt.substring(0,txt.indexOf("kon")), correctas);
            document.insertString(document.getLength(),
                    txt.substring(txt.indexOf("kon"),txt.indexOf("kon")+ 3), erroneas);
            document.insertString(document.getLength(),
                    txt.substring(txt.indexOf("kon") + 3), correctas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void simulaEdicionCorreccion() {
        try {
            String texto = document.getText(0, document.getLength());
            texto = texto.replace("kon", "con");
            document.replace(0, document.getLength(), texto, correctas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void repaint() {
        if (this.getGraphics() != null) {
            paint(this.getGraphics());
        }
    }

    // *********** antiguo diccionario
/*    public void paint(Graphics g) {
     int ndic = dad.getNDiccio();
     int ntex = dad.getNTexto();
     g.setColor(new Color(220, 220, 220));
     g.fillRect(0,0, this.getWidth(), this.getHeight());
     g.setColor(new Color(0, 0, 255));
     g.drawString("Cantidad de palabras del diccionario: " + ndic, 20, 20);
     g.drawString("Cantidad de palabras del texto: " + ntex, 20, 40);
     g.drawString("Cantidad de palabras analizadas: " + dad.getAnalizadas(), 20, 60);
     }
     */
    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
