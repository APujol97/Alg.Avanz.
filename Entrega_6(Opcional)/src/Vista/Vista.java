/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Principal.Eventos;
import Principal.Main;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Vista extends JFrame implements ActionListener, Eventos {

    private Main prog;
    private Matriz matriz;

    public Vista(String s, Main p) {
        super(s);
        prog = p;
        this.getContentPane().setLayout(new BorderLayout());
        //--
        //Panel de botones
        JPanel buttonPane = new JPanel();
        
        JButton setDimension = new JButton("Dimension");
        setDimension.addActionListener(this);
        JButton mezclar = new JButton("Mezclar");
        mezclar.addActionListener(this);
        JButton resolver = new JButton("Resolver");
        resolver.addActionListener(this);
        
        buttonPane.add(setDimension);
        buttonPane.add(mezclar);
        buttonPane.add(resolver);
        
        //Matriz
        matriz = new Matriz(prog.getModel());

        this.add(BorderLayout.NORTH, buttonPane);
        this.add(BorderLayout.CENTER, matriz);
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
    public void actionPerformed(ActionEvent e) {
        String comanda = e.toString();
        int a = comanda.indexOf(",cmd=") + 5;
        comanda = comanda.substring(a, comanda.indexOf(",", a));
        prog.notificar(comanda);
    }

    @Override
    public void notificar(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}