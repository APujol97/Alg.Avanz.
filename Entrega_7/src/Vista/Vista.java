/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Principal.Eventos;
import Principal.Main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Vista extends JFrame implements ActionListener, Eventos {

    private Main prog;
    private JTextField fileChooser;
    private JPanel panelImgSelect;
    private JPanel panelImgResul;

    public Vista(String s, Main p) {
        super(s);
        prog = p;

        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setBackground(Color.LIGHT_GRAY);
        JPanel panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.X_AXIS));

        JLabel fileLabel = new JLabel("Arrastra un archivo:");
        fileLabel.setMaximumSize(new Dimension(130, 50));

        fileChooser = new JTextField();
        fileChooser.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                e.consume();
            }
        });
        fileChooser.setMaximumSize(new Dimension(300, 25));
        fileChooser.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        fileChooser.setText(file.getPath());

                        //Creamos la imagen desde el fichero
                        File img = new File(file.getPath());
                        BufferedImage image = ImageIO.read(img);

                        //La transformamos en un ImageniCON y la redimensionameos
                        ImageIcon picLabel = new ImageIcon(image);
                        picLabel.setImage(picLabel.getImage().getScaledInstance(panelImgSelect.getWidth(), panelImgSelect.getHeight(), Image.SCALE_SMOOTH));

                        //Creamos la etiqueta para ponerla en el jlabel
                        JLabel etiImg = new JLabel();
                        etiImg.setSize(panelImgSelect.getWidth(), panelImgSelect.getHeight());
                        etiImg.setIcon(picLabel);

                        panelImgSelect.add(etiImg);
                        panelImgSelect.repaint();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton botonEjecutar = new JButton("Ejecutar");
        botonEjecutar.addActionListener(this);

        panelOpciones.setBorder(new EmptyBorder(10, 0, 0, 0));
        panelOpciones.add(fileLabel);
        
        panelOpciones.add(fileChooser);
        panelOpciones.add(Box.createRigidArea(new Dimension(50, 0)));
        panelOpciones.add(botonEjecutar);

        JPanel panelImagenes = new JPanel();
        panelImagenes.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelImagenes.setLayout(new BoxLayout(panelImagenes, BoxLayout.X_AXIS));
        panelImagenes.setMaximumSize(new Dimension(700, 500));

        panelImgSelect = new JPanel();
        panelImgSelect.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        panelImgSelect.setBackground(Color.white);
        
        panelImgResul = new JPanel();
        panelImgResul.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        panelImgResul.setBackground(Color.white);

        panelImagenes.add(panelImgSelect);
        panelImagenes.add(Box.createRigidArea(new Dimension(25, 0)));
        panelImagenes.add(panelImgResul);

        this.add(panelOpciones);
        this.add(panelImagenes);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void mostrar() {
        this.setSize(700, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        try {
            Thread.sleep(500);
        } catch (Exception e) {
            Principal.Error.informaError(e);
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    public void notificar(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("".equals(this.fileChooser.getText())) {
            System.out.println("NO IMAGEN SELECCIONADA");
        } else {
            String comanda = e.toString();
            int a = comanda.indexOf(",cmd=") + 5;
            comanda = comanda.substring(a, comanda.indexOf(",", a));
            prog.notificar(comanda);

        }
    }

}
