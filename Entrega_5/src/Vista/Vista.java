/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Principal.Eventos;
import Principal.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
/**
 *
 * @author Joan Alcover, Alejandro Fluixà, Francisco Muñoz, Antonio Pujol
 */
public class Vista extends JFrame implements ActionListener, Eventos {
    
    private Main prog;
    private JTextPane areaTexto;
    private JTextArea output;

    
    public Vista(String s, Main p) {
        super(s);
        prog = p;
        this.getContentPane().setLayout(new BorderLayout());
        //--
        
        //Panel de botones
        JPanel btnPanel = new JPanel();
        JButton detectar = new JButton("Detectar");
        detectar.addActionListener(this);
        
        JButton corregir = new JButton("Corregir");
        corregir.addActionListener(this);
        
        btnPanel.add(detectar);
        btnPanel.add(corregir);
        
        //Panel de texto     
        JPanel centralPanel = new JPanel();
        centralPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centralPanel.setLayout(new BorderLayout());
        
        areaTexto = new JTextPane();  
        areaTexto.setBorder(BorderFactory.createLineBorder(Color.BLACK));    
        
        JScrollPane sp = new JScrollPane(areaTexto);
        centralPanel.add(sp);
        
        //Panel informativo
        JPanel southPane = new JPanel();
        southPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        southPane.setLayout(new BorderLayout());
        
        output = new JTextArea();
        output.setEditable(false);
        output.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
        southPane.add(output);
        
        this.add(BorderLayout.NORTH, btnPanel);
        this.add(BorderLayout.CENTER, centralPanel);
        this.add(BorderLayout.SOUTH, southPane);
           
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
    
    public String getText() {
        return this.areaTexto.getText();
    }
    
    public void setOutput(String text) {
        this.output.setText(this.output.getText() + text);
    }
    
    public void replace(String pal, String correct, int index) {
        System.out.println(pal+" - "+correct+" - "+index);
        this.areaTexto.select(index - pal.length(), index);
        this.areaTexto.replaceSelection(correct);
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