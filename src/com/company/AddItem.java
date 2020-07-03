package com.company;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class AddItem extends JFrame {

    HSSFWorkbook workbook;
    HSSFSheet sheet;
    JTextField t_row, t_cell;
    JTextField t_set;
    String name;

    public AddItem() {
        setLayout(null);
        setVisible(true);
        setSize(1080, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(rdm(), rdm(), rdm()));

        //header
        JPanel heading = new JPanel();
        heading.setBounds(0, 0, 1080, 100);
        JLabel head = new JLabel("Add Item");
        head.setFont(new Font("Monospaced", Font.BOLD, 40));
        head.setSize(100, 50);
        heading.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));
        head.setForeground(new java.awt.Color(rdm(), rdm(), rdm()));
        heading.add(head);

        //Body Panel
        JPanel body = new JPanel();
        body.setLayout(null);
        body.setBounds(0, 110, 1080, 600);
        body.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));

        JButton b_sell = new JButton("sell");
        b_sell.setBounds(50, 50, 100, 30);
        b_sell.addActionListener(f -> sell());
        body.add(b_sell);

        JButton b_party = new JButton("party");
        b_party.setBounds(150, 50, 100, 30);
        b_party.addActionListener(f -> party());
        body.add(b_party);

        JButton b_vasuli = new JButton("vasuli");
        b_vasuli.setBounds(250, 50, 100, 30);
        b_vasuli.addActionListener(f -> vasuli());
        body.add(b_vasuli);

        JButton b_purchase = new JButton("purchase");
        b_purchase.setBounds(350, 50, 100, 30);
        b_purchase.addActionListener(f -> purchase());
        body.add(b_purchase);

        t_row = new JTextField();
        t_row.setBounds(50, 100, 50, 30);
        body.add(t_row);

        t_cell = new JTextField();
        t_cell.setBounds(120, 100, 50, 30);
        body.add(t_cell);

        t_set = new JTextField();
        t_set.setBounds(120, 200, 50, 30);
        body.add(t_set);

        JButton save = new JButton("SAVE");
        save.setBounds(600, 440, 100, 50);
        save.addActionListener(f -> save());
        body.add(save);

        JButton back = new JButton("Back");
        back.setBounds(800, 540, 100, 50);
        back.addActionListener(f -> back());
        body.add(back);

        add(heading);
        add(body);
    }

    private void save() {
        System.out.println(t_set.getText());
        try {
            sheet.getRow(Integer.parseInt(t_row.getText())).createCell(Integer.parseInt(t_cell.getText())).setCellValue(Integer.parseInt(t_set.getText()));
        } catch (NumberFormatException n){
            sheet.getRow(Integer.parseInt(t_row.getText()))
                    .createCell(Integer.parseInt(t_cell.getText()))
                    .setCellValue(
                            t_set.getText());
        }
        try {
            workbook.write(new FileOutputStream(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int rdm() {
        Random random = new Random();
        return random.nextInt(255);
    }

    public void back() {
        this.dispose();
        new Add();
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void party() {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            workbook = new HSSFWorkbook(new FileInputStream("party.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheet("party");
        name = "party.xls";
    }

    private void sell() {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            workbook = new HSSFWorkbook(new FileInputStream("sell.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheet("sell");
        name = "sell.xls";
    }

    private void vasuli() {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            workbook = new HSSFWorkbook(new FileInputStream("vasuli.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheet("vasuli");
        name = "vasuli.xls";
    }

    private void purchase() {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            workbook = new HSSFWorkbook(new FileInputStream("purchase.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheet("purchase");
        name = "purchase.xls";
    }
}
