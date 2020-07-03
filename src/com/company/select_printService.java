package com.company;

import javax.print.PrintService;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class select_printService extends JFrame {
    JButton[] buttons=new JButton[20];
    int k;
    JLabel[] labels =new JLabel[20];

    public select_printService(PrintService[] printService){
        setLayout(null);
        setVisible(true);
        setSize(1080, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(rdm(), rdm(), rdm()));

        //header
        JPanel heading = new JPanel();
        heading.setBounds(0, 0, 540, 50);
        JLabel head = new JLabel("Printing");
        head.setFont(new Font("Monospaced", Font.BOLD, 30));
        head.setSize(100, 50);
        heading.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));
        head.setForeground(new java.awt.Color(rdm(), rdm(), rdm()));
        heading.add(head);

        //Body Panel
        JPanel body = new JPanel();
        body.setLayout(null);
        body.setBounds(0, 110, 540, 300);
        body.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));

        int i=0;
        for (PrintService service : printService){
            buttons[i]=new JButton(service.getName());
            buttons[i].setBounds(50,100+i*100,100,30);
            labels[i]=new JLabel(String.valueOf(i));
            int finalI = i;
            buttons[i].addActionListener(f->p(finalI));
            body.add(buttons[i]);
            i++;
        }
        JButton back = new JButton("Back");
        back.setBounds(440, 540, 100, 50);
        back.addActionListener(f -> back());
        body.add(back);

        add(heading);
        add(body);
    }
    private void p(int i){
        k=i+1;
    }

    private int rdm() {
        Random random = new Random();
        return random.nextInt(255);
    }

    public void back() {
        this.dispose();
        new Add();
    }
}
