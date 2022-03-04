/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc_esdeveniments.vista.Vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import mvc_esdeveniments.MVC_Esdeveniments;
import mvc_esdeveniments.MeuError;

/**
 *
 * @author mascport
 */
public class Vista extends JFrame implements ActionListener {

    private MVC_Esdeveniments prog;

    public Vista(String s, MVC_Esdeveniments p) {
        super(s);
        prog = p;
        this.getContentPane().setLayout(new BorderLayout());
        JPanel bots = new JPanel();
        //--
        JButton boto_o_n = new JButton("O(n)");
        boto_o_n.addActionListener(this);
        bots.add(boto_o_n);
        
        JButton boto_o_log_n = new JButton("O(logn)");
        boto_o_log_n.addActionListener(this);
        bots.add(boto_o_log_n);
        
        JButton boto_o_n_2 = new JButton("O(n2)");
        boto_o_n_2.addActionListener(this);
        bots.add(boto_o_n_2);
        //--
        this.add(BorderLayout.NORTH, bots);
        PanellDibuix panell = new PanellDibuix(800, 400, prog.getModel(), this);
        this.add(BorderLayout.CENTER, panell);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void mostrar() {
        this.pack();
        this.setVisible(true);
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            MeuError.informaError(e);
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
}
