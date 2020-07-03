package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.util.Random;

public class Add extends JFrame {
    String version = "1.5.1.0";

    public Add() {

        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(rdm(), rdm(), rdm()));

        //header
        JPanel heading = new JPanel();
        heading.setBounds(0, 0, 1080, 100);
        JLabel head = new JLabel("Main Menu");
        head.setFont(new Font("Monospaced", Font.BOLD, 40));
        head.setSize(200, 50);
        heading.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));
        head.setForeground(new java.awt.Color(rdm(), rdm(), rdm()));
        heading.add(head);

        //Body Panel
        JPanel body = new JPanel();
        body.setLayout(null);
        body.setBounds(0, 110, 1080, 600);
        body.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));

        JButton purchase = new JButton("Purchase");
        purchase.setBounds(50, 50, 200, 100);
        purchase.addActionListener(f -> purchase());
        body.add(purchase);

        JButton sell = new JButton("Sell");
        sell.setBounds(300, 50, 200, 100);
        sell.addActionListener(f -> sell());
        body.add(sell);

        JButton ledger = new JButton("Ledger");
        ledger.setBounds(550, 50, 200, 100);
        ledger.addActionListener(f -> ledger());
        body.add(ledger);

        JButton crate = new JButton("Crate");
        crate.setBounds(800, 50, 200, 100);
        crate.addActionListener(f -> crate());
        body.add(crate);

        JButton vasuli = new JButton("Vyapari Vasuli");
        vasuli.setBounds(50, 200, 200, 100);
        vasuli.addActionListener(f -> vasuli());
        body.add(vasuli);

        JButton addParty = new JButton("Add Party");
        addParty.setBounds(300, 200, 200, 100);
        addParty.addActionListener(f -> addParty());
        body.add(addParty);

        JButton viewParty = new JButton("View Party");
        viewParty.setBounds(550, 200, 200, 100);
        viewParty.addActionListener(f -> viewParty());
        body.add(viewParty);

        JButton yearEnding = new JButton("Year Ending");
        yearEnding.setBounds(50, 350, 200, 100);
        yearEnding.addActionListener(f -> yearEnding());
        body.add(yearEnding);

        /*JButton undoYearEnding = new JButton("Undo Year Ending");
        undoYearEnding.setBounds(300, 350, 200, 100);
        undoYearEnding.addActionListener(f -> undoYear());
        body.add(undoYearEnding);*/

        JButton addItem = new JButton("Connect");
        addItem.setBounds(550, 350, 200, 100);
        addItem.addActionListener(f -> b_connect());
        body.add(addItem);

        JButton setConstants = new JButton("Set Values");
        setConstants.setBounds(50, 550, 100, 50);
        setConstants.addActionListener(f -> addItem());//JOptionPane.showMessageDialog(new JFrame("Reminder"),"Hold Your Breaths...\n Coming Soon !!"));
        body.add(setConstants);

        JLabel t_version = new JLabel("version - " + version);
        t_version.setBounds(700, 550, 100, 40);
        body.add(t_version);

        add(heading);
        add(body);
        setSize(1080, 800);
    }

    private void b_connect() {
        new connect();
    }

    private void yearEnding() {
        int k = JOptionPane.showConfirmDialog(new JFrame("confirmation dialog"), "Are you sure you want to start new year program", "welcome", JOptionPane.YES_NO_OPTION);
        if (k == 0) {
            try {
                new YearEnding();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame("Message"), "Year Ending program not called.");
        }

    }

    private void vasuli() {
        this.dispose();
        new VyapariVasuli();
    }

    @Override
    public float getAlignmentX() {
        return super.getAlignmentX();
    }

    /*private void undoYear() {
        int k = JOptionPane.showConfirmDialog(new JFrame("confirmation dialog"), "Are you sure you want to undo new year program", "welcome", JOptionPane.YES_NO_OPTION);
        if (k == 0) {
            boolean done = true;
            int i = 0;
            File file = new File("purchase.xls");
            while (file.exists()) {
                i++;
                file = new File("purchase_" + i + ".xls");
            }
            i--;
            file = ne4tw File("purchase.xls");
            if (!file.delete()) done = false;
            file = new File("purchase_" + i + ".xls");
            File dest = new File("purchase.xls");
            if (!file.renameTo(dest)) done = false;
            if (!file.delete()) done = false;

            file = new File("sell.xls");
            if (!file.delete()) done = false;

            file = new File("sell_" + i + ".xls");
            dest = new File("sell.xls");
            if (!file.renameTo(dest)) done = false;
            if (!file.delete()) done = false;

            file = new File("vasuli.xls");
            if (!file.delete()) done = false;
            f*ile = new File("vasuli_" + i + ".xls");
            dest = new File("vasuli.xls");
            if (!file.renameTo(dest)) done = false;
            if (!file.delete()) done = false;

            file = new File("party.xls");
            if (file.delete()) System.out.println("party deleted");
            file = new File("party_" + i + ".xls");
            dest = new File("party.xls");
            if (!file.renameTo(dest)) done = false;
            if (!file.delete()) done = false;

            if (done) {
                JOptionPane.showMessageDialog(new JFrame("Message"), "YEAR ENDING UNDONE...\nDon't worry we are here to take care of your mistakes... ");
            } else {
                JOptionPane.showMessageDialog(new JFrame("message"), "Restart application and try again");
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame("Message"), "Undo Year Ending program not called.");
        }

    }*/

    private void viewParty() {
        this.dispose();
        new ViewParty();
    }

    private int rdm() {
        Random random = new Random();
        return random.nextInt(255);
    }

    private void addItem() {
        this.dispose();
        new AddItem();
    }

    private void addParty() {
        this.dispose();
        new AddParty();
    }

    private void sell() {
        this.dispose();
        try {
            new Sell();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void purchase() {
        try {
            new Purchase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dispose();

    }

    private void ledger() {
        this.dispose();
        new Ledger();
    }

    private void crate() {
        new Crate();
        this.dispose();
    }


}
