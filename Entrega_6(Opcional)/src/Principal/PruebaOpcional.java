/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;

/**
 *
 * @author alexm
 */
public class PruebaOpcional extends JFrame {

    /**
     * @param args the command line arguments
     */
    static final int dimension = 4;
    static int posiY = dimension - 1, posiX = dimension - 1;
    static int posiciones[][];
    static BufferedImage imagen;
    static Image img;
    static BufferedImage imagenVacia;
    static Image imgVacia;
    static Image trozos[] = new Image[dimension * dimension];
    static int multX;
    static int multY;

    public PruebaOpcional() {
        super("Ejemplo");

    }

    public void paint(Graphics g) {

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (posiciones[i][j] != -1) {
                    g.drawImage(trozos[posiciones[i][j]], j * multY, i * multX, null);
                } else {
                    g.drawImage(imgVacia, j * multY, i * multX, null);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        PruebaOpcional ok = new PruebaOpcional();

        posiciones = new int[dimension][dimension];

        imagen = ImageIO.read(new File("fondo.jpg"));
        img = ok.createImage(imagen.getSource());
        img = img.getScaledInstance(700, 700, java.awt.Image.SCALE_SMOOTH);

        imagenVacia = ImageIO.read(new File("negro.jpg"));
        imgVacia = ok.createImage(imagenVacia.getSource());

        ok.setSize(img.getWidth(ok), img.getHeight(ok));
        ok.setDefaultCloseOperation(EXIT_ON_CLOSE);

        int ancho = ok.getWidth();
        int largo = ok.getHeight();

        multX = ancho / dimension;
        multY = largo / dimension;

        imgVacia = imgVacia.getScaledInstance(multX, multY, java.awt.Image.SCALE_SMOOTH);

        int indice = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                trozos[indice] = ok.createImage(new FilteredImageSource(img.getSource(), new CropImageFilter(j * multY, i * multX, multX, multY)));
                indice++;
            }
        }

        indice = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                posiciones[i][j] = indice;

                if (i == dimension - 1 && j == dimension - 1) {
                    posiciones[i][j] = -1;
                }
                indice++;
            }
        }

         mezclar(30);
        ok.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                // System.out.println(ke.getKeyChar());
                if (ke.getKeyChar() == 'a') {
                    movIzquierda();
                }
                if (ke.getKeyChar() == 'd') {
                    movDerecha();
                }
                if (ke.getKeyChar() == 'w') {
                    movArriba();
                }
                if (ke.getKeyChar() == 's') {
                    movAbajo();
                }
                ok.repaint();
              //  pintar();
            }
        });
        ok.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                int indiceX, indiceY;
                indiceX = me.getX() / multX;
                indiceY = me.getY() / multY;
                if (indiceX == posiX + 1 && indiceY == posiY) {
                    movDerecha();
                } else if (indiceX == posiX - 1 && indiceY == posiY) {
                    movIzquierda();
                } else if (indiceX == posiX && indiceY == posiY - 1) {
                    movArriba();
                } else if (indiceX == posiX && indiceY == posiY + 1) {
                    movAbajo();
                }
                ok.repaint();
            }

            @Override
            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        });
        ok.setVisible(true);
      //  pintar();

        // System.out.println("--------------------------------");
        // System.out.println("--------------------------------");
        while (!comprobar()) {
            sleep(1);
        }
        JOptionPane.showMessageDialog(ok, "Esta bien");
        //  pintar();
    }

    public static void mezclar(int iteraciones) {
        Random ran = new Random();
        int aux;
        for (int i = 0; i < iteraciones; i++) {
            aux = ran.nextInt(4);
            switch (aux) {
                case 0:
                    movDerecha();
                    break;
                case 1:
                    movAbajo();
                    break;
                case 2:
                    movIzquierda();
                    break;
                case 3:
                    movArriba();
                    break;
            }
           // pintar();
        }
    }

    public static void pintar() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.print(posiciones[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("--------------------------------");

    }

    public static void movDerecha() {

        if (posiX + 1 < dimension) {
            //  System.out.println(" Puedes moverte hacia la derecha");
            int aux = posiciones[posiY][posiX + 1];
            posiciones[posiY][posiX + 1] = posiciones[posiY][posiX];
            posiciones[posiY][posiX] = aux;
            posiX++;
        } else {
            // System.out.println("No puedes moverte hacia la derecha");
        }
    }

    public static void movIzquierda() {

        if (posiX - 1 >= 0) {
            //  System.out.println(" Puedes moverte hacia la izquierda");
            int aux = posiciones[posiY][posiX - 1];
            posiciones[posiY][posiX - 1] = posiciones[posiY][posiX];
            posiciones[posiY][posiX] = aux;
            posiX--;
        } else {
            // System.out.println("No puedes moverte hacia la izquierda");
        }
    }

    public static void movArriba() {
        if (posiY - 1 >= 0) {
            //   System.out.println(" Puedes moverte hacia la arriba");
            int aux = posiciones[posiY - 1][posiX];
            posiciones[posiY - 1][posiX] = posiciones[posiY][posiX];
            posiciones[posiY][posiX] = aux;
            posiY--;
        } else {
            //  System.out.println("No puedes moverte hacia la arriba");
        }
    }

    public static void movAbajo() {
        if (posiY + 1 < dimension) {
            //  System.out.println(" Puedes moverte hacia la abajo");
            int aux = posiciones[posiY + 1][posiX];
            posiciones[posiY + 1][posiX] = posiciones[posiY][posiX];
            posiciones[posiY][posiX] = aux;
            posiY++;
        } else {
            //   System.out.println("No puedes moverte hacia la abajo");
        }
    }

    public static boolean comprobar() {
        int indice = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (i != dimension - 1 || j != dimension - 1) {
                    if (posiciones[i][j] != indice) {
                        return false;
                    }
                }
                indice++;
            }
        }
        return true;
    }

}
