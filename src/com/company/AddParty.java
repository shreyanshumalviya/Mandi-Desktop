package com.company;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class AddParty extends JFrame {
    AutoSuggestor autoSuggestor;
    ArrayList<String> words = new ArrayList<>();
    HSSFWorkbook workbook;
    public int done = 1;
    {
        try {
            workbook = new HSSFWorkbook(new FileInputStream("party.xls"));
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    HSSFSheet sheet = Objects.requireNonNull(workbook).getSheet("party");

    private int uid = sheet.getLastRowNum() + 100;
    private JTextField t_party_name;
    private JTextField t_address;
    private JTextField t_phone;
    private JTextField t_comm;
    private JTextField t_balance;
    private JRadioButton r_vyapari;
    private JRadioButton r_kisan;
    private JRadioButton r_spl_vyapari;
    private JRadioButton r_others;

    public AddParty() {
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(rdm(), rdm(), rdm()));

        //header
        JPanel heading = new JPanel();
        heading.setBounds(0, 0, 1080, 100);
        JLabel head = new JLabel("Add Party");
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
//line 0---------------------------------------------------------------------------------------------------------
        r_vyapari = new JRadioButton("Vyapari");
        r_vyapari.setBounds(50, 50, 100, 50);
        body.add(r_vyapari);

        r_kisan = new JRadioButton("Kisaan");
        r_kisan.setBounds(150, 50, 100, 50);
        body.add(r_kisan);

        r_spl_vyapari = new JRadioButton("Spl Vyapari");
        r_spl_vyapari.setBounds(50, 100, 100, 50);
        body.add(r_spl_vyapari);

        r_others = new JRadioButton("Others");
        r_others.setBounds(150, 100, 100, 50);
        body.add(r_others);

        ButtonGroup vyapari_type = new ButtonGroup();
        vyapari_type.add(r_kisan);
        vyapari_type.add(r_vyapari);
        vyapari_type.add(r_spl_vyapari);
        vyapari_type.add(r_others);

        JLabel l_party_name = new JLabel("Name - ");
        l_party_name.setBounds(350, 50, 100, 30);
        body.add(l_party_name);

        t_party_name = new JTextField();
        t_party_name.setBounds(475, 50, 150, 30);
        body.add(t_party_name);

        JLabel l_uid = new JLabel("U I D - ");
        l_uid.setBounds(350, 100, 100, 30);
        body.add(l_uid);

        JLabel t_uid = new JLabel(uid + "");
        t_uid.setBounds(475, 100, 100, 30);
        body.add(t_uid);

//line 1-------------------------------------------------------------------------------------------------------
        JLabel l_address = new JLabel("Address - ");
        l_address.setBounds(50, 160, 100, 30);
        body.add(l_address);

        t_address = new JTextField();
        t_address.setBounds(175, 160, 200, 30);
        body.add(t_address);

        JLabel l_phone = new JLabel("Phone - ");
        l_phone.setBounds(475, 160, 70, 30);
        body.add(l_phone);

        t_phone = new JTextField();
        t_phone.setBounds(575, 160, 200, 30);
        body.add(t_phone);

//line 2-------------------------------------------------------------------------------------------------------
        JLabel l_comm = new JLabel("Commission - ");
        l_comm.setBounds(50, 220, 100, 30);
        body.add(l_comm);

        t_comm = new JTextField();
        t_comm.setBounds(175, 220, 200, 30);
        body.add(t_comm);

        JLabel l_balance = new JLabel("Balance - ");
        l_balance.setBounds(475, 220, 70, 30);
        body.add(l_balance);

        t_balance = new JTextField();
        t_balance.setBounds(575, 220, 200, 30);
        body.add(t_balance);

//---------------------------------------------------------------------------------------------------------------


        JButton _new = new JButton("New");
        _new.setBounds(200, 540, 200, 50);
        _new.addActionListener(f -> add_new());
        body.add(_new);

        JButton save = new JButton("Save");
        save.setBounds(500, 540, 200, 50);
        save.addActionListener(f -> save());
        body.add(save);

        JButton back = new JButton("Back");
        back.setBounds(800, 540, 200, 50);
        back.addActionListener(f -> back());
        body.add(back);

        add(heading);
        add(body);
        setSize(1080, 800);
        int i = 1;
        HSSFRow row;
        row=sheet.getRow(i);
        while (i <= sheet.getLastRowNum()) {
            if(!words.contains(row.getCell(1).getStringCellValue())){
                words.add(row.getCell(1).getStringCellValue());}
            i++;
            row=sheet.getRow(i);
        }
        autoSuggestor = new AutoSuggestor(t_party_name, this, words, Color.white, Color.black, Color.black, (float) 1.0);
    }

    public AddParty(String name) {

        setLayout(null);
        setVisible(true);
        setSize(1080, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(rdm(), rdm(), rdm()));

        //header
        JPanel heading = new JPanel();
        heading.setBounds(0, 0, 1080, 100);
        JLabel head = new JLabel("Add Party");
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
//line 0---------------------------------------------------------------------------------------------------------
        r_vyapari = new JRadioButton("Vyapari");
        r_vyapari.setBounds(50, 50, 100, 50);
        body.add(r_vyapari);

        r_kisan = new JRadioButton("Kisaan");
        r_kisan.setBounds(150, 50, 100, 50);
        body.add(r_kisan);

        r_spl_vyapari = new JRadioButton("Spl Vyapari");
        r_spl_vyapari.setBounds(50, 100, 100, 50);
        body.add(r_spl_vyapari);

        r_others = new JRadioButton("Others");
        r_others.setBounds(150, 100, 100, 50);
        body.add(r_others);

        ButtonGroup vyapari_type = new ButtonGroup();
        vyapari_type.add(r_kisan);
        vyapari_type.add(r_vyapari);
        vyapari_type.add(r_spl_vyapari);
        vyapari_type.add(r_others);

        JLabel l_party_name = new JLabel("Name - ");
        l_party_name.setBounds(350, 50, 100, 30);
        body.add(l_party_name);

        t_party_name = new JTextField(name);
        t_party_name.setBounds(475, 50, 150, 30);
        body.add(t_party_name);

        JLabel l_uid = new JLabel("U I D - ");
        l_uid.setBounds(350, 100, 100, 30);
        body.add(l_uid);

        JLabel t_uid = new JLabel(uid + "");
        t_uid.setBounds(475, 100, 100, 30);
        body.add(t_uid);

//line 1-------------------------------------------------------------------------------------------------------
        JLabel l_address = new JLabel("Address - ");
        l_address.setBounds(50, 160, 100, 30);
        body.add(l_address);

        t_address = new JTextField();
        t_address.setBounds(175, 160, 200, 30);
        body.add(t_address);

        JLabel l_phone = new JLabel("Phone - ");
        l_phone.setBounds(475, 160, 70, 30);
        body.add(l_phone);

        t_phone = new JTextField();
        t_phone.setBounds(575, 160, 200, 30);
        body.add(t_phone);

//line 2-------------------------------------------------------------------------------------------------------
        JLabel l_comm = new JLabel("Commission - ");
        l_comm.setBounds(50, 220, 100, 30);
        body.add(l_comm);

        t_comm = new JTextField();
        t_comm.setBounds(175, 220, 200, 30);
        body.add(t_comm);

        JLabel l_balance = new JLabel("Balance - ");
        l_balance.setBounds(475, 220, 70, 30);
        body.add(l_balance);

        t_balance = new JTextField();
        t_balance.setBounds(575, 220, 200, 30);
        body.add(t_balance);

//---------------------------------------------------------------------------------------------------------------

        JButton _new = new JButton("New");
        _new.setBounds(200, 540, 200, 50);
        _new.addActionListener(f -> add_new());
        body.add(_new);

        JButton save = new JButton("Save");
        save.setBounds(500, 540, 200, 50);
        save.addActionListener(f -> save());
        body.add(save);

        JButton back = new JButton("Back");
        back.setBounds(800, 540, 200, 50);
        back.addActionListener(f -> go_back());
        body.add(back);

        add(heading);
        add(body);
    }

    private void go_back() {
        this.dispose();
    }

    private void add_new() {
        this.dispose();
        new AddParty();
    }

    private int rdm() {
        Random random = new Random();
        return random.nextInt(255);
    }

    private void save() {
        boolean already_exist = false;
        int i = 0;
        while (i <= sheet.getLastRowNum() && !already_exist) {
            if (t_party_name.getText().equals(sheet.getRow(i).getCell(1).getStringCellValue())) {
                already_exist = true;
            }
            i++;
        }
        if (already_exist) {
            JOptionPane.showMessageDialog(new JFrame("Message"), "NAME ALREADY EXISTS");
        } else {
            HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
            row.createCell(0).setCellValue((int) uid);
            row.createCell(1).setCellValue(t_party_name.getText());
            row.createCell(2).setCellValue(Integer.parseInt(t_balance.getText()));
            row.createCell(3).setCellValue(Integer.parseInt(t_balance.getText()));
            row.createCell(4).setCellValue(find_type());
            row.createCell(5).setCellValue(Integer.parseInt(t_comm.getText()));
            row.createCell(6).setCellValue(t_address.getText());
            row.createCell(7).setCellValue(t_phone.getText());
            try {
                workbook.write(new FileOutputStream("party.xls"));
                JOptionPane.showMessageDialog(new JFrame("Message"), "SAVED SUCCESSFULLY");
                done = 1;
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame("Message"), "UNABLE TO SAVE");
            }
        }
    }

    private int find_type() {
        if (r_vyapari.isSelected()) {
            return 0;
        } else if (r_spl_vyapari.isSelected()) {
            return 1;
        } else if (r_kisan.isSelected()) {
            return 2;
        } else if (r_others.isSelected()) {
            return 3;
        } else return 5;
    }

    private void back() {
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dispose();
        new Add();
    }
}
