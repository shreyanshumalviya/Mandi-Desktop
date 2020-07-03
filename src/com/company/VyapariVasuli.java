package com.company;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Random;


public class VyapariVasuli extends JFrame {

    int p_row_no, v_row_no, s_row_no, curr_bal;
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date today = new Date();
    String date = formatter.format(today);
    HSSFWorkbook workbook1;
    AutoSuggestor autoSuggestor;

    {
        try {
            workbook1 = new HSSFWorkbook(new FileInputStream("party.xls"));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame("Message"), "UNABLE TO GET PARTY LIST **\n" +
                    " err - exception while accessing party list vasuli");
        }
    }

    HSSFSheet party_sheet = Objects.requireNonNull(workbook1).getSheet("party");
    ArrayList<String> words = new ArrayList<>();
    HSSFWorkbook workbook;

    {
        try {
            workbook = new HSSFWorkbook(new FileInputStream("sell.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    HSSFSheet sell_sheet = Objects.requireNonNull(workbook).getSheet("sell");

    {
        try {
            workbook = new HSSFWorkbook(new FileInputStream("vasuli.xls"));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame("Message"), "UNABLE TO GET PARTY LIST **\n" +
                    " err - exception while accessing vasuli list vasuli");
        }
    }

    String p_name;

    JTextField t_partyName, t_amount, t_remaining;
    HSSFSheet vasuli_sheet = Objects.requireNonNull(workbook).getSheet("vasuli");

    public VyapariVasuli() {
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(rdm(), rdm(), rdm()));

        //header
        JPanel heading = new JPanel();
        heading.setBounds(0, 0, 1080, 100);
        JLabel head = new JLabel("Vyapari Vasuli");
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

        JLabel l_party = new JLabel("Party Name");
        l_party.setBounds(75, 200, 150, 30);
        body.add(l_party);

        t_partyName = new JTextField("");
        t_partyName.setBounds(50, 250, 150, 30);
        body.add(t_partyName);

        JLabel l_amount = new JLabel("Amount");
        l_amount.setBounds(325, 200, 150, 30);
        body.add(l_amount);

        t_amount = new JTextField("");
        t_amount.setBounds(300, 250, 150, 30);
        body.add(t_amount);


        JLabel l_remaining = new JLabel("Remaining");
        l_remaining.setBounds(525, 200, 150, 30);
        body.add(l_remaining);

        t_remaining = new JTextField("");
        t_remaining.setBounds(500, 250, 150, 30);
        body.add(t_remaining);

        JButton addParty = new JButton("Add New");
        addParty.setBounds(650, 50, 100, 30);
        addParty.addActionListener(f -> addParty());
        body.add(addParty);

        JLabel l_date = new JLabel("Date -");
        l_date.setBounds(160, 100, 40, 20);
        JLabel t_date = new JLabel(date);
        t_date.setBounds(200, 100, 70, 20);
        body.add(t_date);
        body.add(l_date);

        JButton _new = new JButton("New");
        _new.setBounds(200, 540, 200, 50);
        _new.addActionListener(f -> add_new());
        body.add(_new);

        JButton save = new JButton("Save");
        save.setBounds(400, 540, 200, 50);
        save.addActionListener(f -> save());
        body.add(save);

        JButton b_remaining = new JButton("View Remaining");
        b_remaining.setBounds(600, 540, 200, 50);
        b_remaining.addActionListener(f -> get_data());
        body.add(b_remaining);

        JButton back = new JButton("Back");
        back.setBounds(800, 540, 100, 50);
        back.addActionListener(f -> back());
        body.add(back);

        add(heading);
        add(body);
        setSize(1080, 800);
        int i = 1;
        HSSFRow row;
        row = party_sheet.getRow(i);
        while (i <= party_sheet.getLastRowNum()) {
            if (!words.contains(row.getCell(1).getStringCellValue()) &&
                    (row.getCell(4).getNumericCellValue() == 0 ||
                            row.getCell(4).getNumericCellValue() == 3 ||
                            row.getCell(4).getNumericCellValue() == 1)) {
                words.add(row.getCell(1).getStringCellValue());
            }
            i++;
            row = party_sheet.getRow(i);
        }
        autoSuggestor = new AutoSuggestor(t_partyName, this, words, Color.white, Color.black, Color.black, (float) 1.0);
    }

    private int rdm() {
        Random random = new Random();
        return random.nextInt(255);
    }

    public void back() {
        this.dispose();
        new Add();
    }

    private void add_new() {
        this.dispose();
        new VyapariVasuli();
    }

    private void save() {

        boolean savable = true;
        if (t_partyName.getText().equals("") || t_amount.getText().equals("")) {savable = false;}
        if (savable) {
            HSSFRow row = vasuli_sheet.createRow(vasuli_sheet.getLastRowNum() + 1);
            row.createCell(0).setCellValue(t_partyName.getText());
            row.createCell(1).setCellValue(date);
            row.createCell(2).setCellValue(Integer.parseInt(t_amount.getText()));
            try {
                workbook.write(new FileOutputStream("vasuli.xls"));
                JOptionPane.showMessageDialog(new JFrame("Message"), "UPDATED SUCCESSFULLY");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame("Message"), "UNABLE TO SAVE **\n" +
                        " err - exception while saving vasuli");
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame("Message"), "UNABLE TO SAVE **\n" +
                    " err - Unsavable");
        }
        get_data();
    }

    private void addParty() {
        AddParty addParty = new AddParty(t_partyName.getText());
        while (addParty.done != 1) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        autoSuggestor.addWordToSuggestions(party_sheet.getRow(party_sheet.getLastRowNum() - 1).getCell(1).getStringCellValue());
    }

    private void get_data() {
        p_row_no = 1;
        p_name = t_partyName.getText();
        boolean found = false;
        HSSFRow row = party_sheet.getRow(p_row_no);
        while (p_row_no <= party_sheet.getLastRowNum()) {
            if (row.getCell(1).getStringCellValue().equals(p_name)) {
                found = true;
                break;
            }
            p_row_no++;
            row = party_sheet.getRow(p_row_no);
        }
        if (found) {
            curr_bal = (int) row.getCell(2).getNumericCellValue();
        } else {
            JOptionPane.showMessageDialog(new JFrame("Message"), "NOT FOUND");
        }
        v_row_no = 1;
        row = vasuli_sheet.getRow(v_row_no);
        while (v_row_no <= vasuli_sheet.getLastRowNum()) {
            if (row.getCell(0).getStringCellValue().equals(p_name) && found) {
                //curr_bal = curr_bal - Integer.parseInt(row.getCell(2).getStringCellValue());
                curr_bal = curr_bal - ((int) row.getCell(2).getNumericCellValue());
            }
            v_row_no++;
            row = vasuli_sheet.getRow(v_row_no);
        }

        s_row_no = 1;
        HSSFCell cell;
        int cell_no;
        row = sell_sheet.getRow(s_row_no);
        while (s_row_no <= sell_sheet.getLastRowNum() && found) {
            cell_no = 3;
            if (row.getCell(1).getStringCellValue().equals(p_name)) {
                while (row.getLastCellNum() >= cell_no) {
                    cell = row.getCell(cell_no);
                    //curr_bal = curr_bal + Integer.parseInt(cell.getStringCellValue());
                    curr_bal = curr_bal + ((int) cell.getNumericCellValue());
                    cell_no = cell_no + 2;
                }
            }
            s_row_no++;
            row = sell_sheet.getRow(s_row_no);
        }
        t_remaining.setText(String.valueOf(curr_bal));
    }
}
