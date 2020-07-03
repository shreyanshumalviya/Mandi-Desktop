package com.company;

import javax.naming.Context;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Login extends JFrame {
    JLabel head = new JLabel("Welcome Please log in to continue");
    JLabel label2 = new JLabel("Enter Password");
    JPasswordField passView = new JPasswordField(4);
    JButton button = new JButton("Log In");
    int i = 0;
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date today = new Date();
    String date = formatter.format(today);

    public Login() {
        try {
            if( today.after(formatter.parse("01/12/2020"))){JOptionPane.showMessageDialog(new JFrame("Reminder"),"Expiring soon contact 8989806515");}
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setLayout(null);
        setSize(1080, 600);
        setVisible(true);
        int a = randomCode(), b = randomCode(), c = randomCode();
        getContentPane().setBackground(new Color(a,b,c));

//header
        JPanel heading;
        heading = new JPanel();
        heading.setBounds(0, 0, 1080, 100);
        head.setFont(new Font("Times New Roman", Font.BOLD, 30));
        heading.setBackground(new java.awt.Color(6,43,5));
        head.setForeground(new java.awt.Color(17, 32, 102));
        heading.add(head);

//login panel
        JPanel login = new JPanel();
        login.setSize(3, 350);
        login.setLayout(null);
        login.setBounds(320, 175, 400, 350);
        login.add(label2);
        label2.setFont(new Font("Monospaced", Font.BOLD, 20));
        label2.setHorizontalAlignment(2);
        label2.setBounds(50,50,300,50);
        login.add(passView,1);
        passView.setFont(new Font("",Font.BOLD,40));
        passView.setBackground(Color.BLACK);
        passView.setBounds(120,130,160,50);
        login.add(button);
        login.setBackground(new java.awt.Color(141,138,78));
        button.addActionListener(f -> doFunction());
        button.setBounds(250,230,100,50);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(heading);
        add(login,1);
        passView.requestFocus();
        Login context = this;
        passView.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                if (Arrays.equals(passView.getPassword(),new char[]{'1','2','3','4'})){
                    context.i=1;
                    context.dispose();
                }
            }
        });
    }

    private void doFunction() {
        if (Arrays.equals(passView.getPassword(),new char[]{'1','2','3','4'})){
            this.i=1;
            this.dispose();
        }
    }

    private int randomCode() {
        return new Random().nextInt(255);
    }
}
