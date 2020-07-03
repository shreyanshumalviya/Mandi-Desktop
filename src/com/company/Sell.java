package com.company;

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
import java.util.Random;

public class Sell extends JFrame {

    JButton b_calculate;
    HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("sell.xls"));
    HSSFSheet sheet = workbook.getSheet("sell");

    HSSFWorkbook workbook1 = new HSSFWorkbook(new FileInputStream("party.xls"));
    HSSFSheet sheet1 = workbook1.getSheet("party");

    AddParty c_add_Party;
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date today = new Date();
    String date = formatter.format(today);
    JTextField t_item;
    int y = 100;
    int index = 0;
    JPanel body;
    JButton[] b_add_another = new JButton[20];
    JTextField[] t_party = new JTextField[20];
    JTextField[] t_amount = new JTextField[20];
    int uid = 100;
    ArrayList<String> words = new ArrayList<>();
    AutoSuggestor autoSuggestor;


    public Sell() throws IOException {
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(rdm(), rdm(), rdm()));

        //header
        JPanel heading = new JPanel();
        heading.setBounds(0, 0, 1080, 100);
        JLabel head = new JLabel("Sell");
        head.setFont(new Font("Monospaced", Font.BOLD, 40));
        head.setSize(100, 50);
        heading.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));
        head.setForeground(new java.awt.Color(rdm(), rdm(), rdm()));
        heading.add(head);

        //Body Panel
        body = new JPanel();
        body.setLayout(null);
        body.setBounds(0, 110, 1080, 600);
        body.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));
//line 0
        JLabel l_select_item = new JLabel("Select Item - ");
        l_select_item.setBounds(50, 50, 100, 30);
        body.add(l_select_item);

        t_item = new JTextField("");
        t_item.setBounds(150, 50, 100, 30);
        body.add(t_item);

        JLabel l_date = new JLabel(date);
        l_date.setBounds(300, 50, 100, 30);
        body.add(l_date);

        JLabel l_uid = new JLabel("UID - ");
        l_uid.setBounds(500, 50, 50, 30);
        body.add(l_uid);

        JLabel t_uid = new JLabel(uid + "");
        t_uid.setBounds(550, 50, 50, 30);
        body.add(t_uid);

        JButton addParty = new JButton("Add New");
        addParty.setBounds(650, 50, 100, 30);
        addParty.addActionListener(f -> addParty());
        body.add(addParty);
//line 1
        JLabel l_party = new JLabel("Party");
        l_party.setBounds(75, 100, 50, 20);
        body.add(l_party);
        JLabel l_quantity = new JLabel("Quantity");
        l_quantity.setBounds(275, 100, 50, 20);
        body.add(l_quantity);
        JLabel l_rate = new JLabel("Rate");
        l_rate.setBounds(375, 100, 50, 20);
        body.add(l_rate);
        JLabel l_amount = new JLabel("Amount");
        l_amount.setBounds(475, 100, 50, 20);
        body.add(l_amount);
        b_add_another[0] = new JButton("Add Another");
        b_add_another[0].setBounds(550, 95, 100, 30);
        b_add_another[0].addActionListener(f -> add_another());
        body.add(b_add_another[0]);

        JButton b_new = new JButton("New");
        b_new.setBounds(800, 450, 200, 50);
        b_new.addActionListener(f -> _new());
        body.add(b_new);

        b_calculate = new JButton("Save");
        b_calculate.setBounds(800, 350, 200, 50);
        b_calculate.addActionListener(f -> calculate());
        body.add(b_calculate);

        JButton back = new JButton("Back");
        back.setBounds(800, 540, 200, 50);
        back.addActionListener(f -> back());
        body.add(back);

        add(heading);
        add(body);
        setSize(1080, 800);
    }

    private void _new() {
        this.dispose();
        try {
            new Sell();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addParty() {
        if (index > 0) {
            c_add_Party = new AddParty(t_party[index - 1].getText());
        } else {
            c_add_Party = new AddParty("");
        }
        try {
            workbook1 = new HSSFWorkbook(new FileInputStream("party.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet1 = workbook1.getSheet("party");
        //sheet1.getRow(sheet1.getLastRowNum()).getCell(1).getStringCellValue();
        System.out.println(sheet1.getLastRowNum());
        autoSuggestor.addWordToSuggestions(sheet1.getRow(sheet1.getLastRowNum()).getCell(1).getStringCellValue());
        setSize(getWidth() + 1, 800);
    }


    private void calculate() {
        int i = 0;
        while (i < index) {
            save(t_party[i].getText(), i);
            i++;
        }
        try {
            workbook.write(new FileOutputStream("sell.xls"));
            JOptionPane.showMessageDialog(new JFrame("Message"), "SAVED SUCCESSFULLY");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame("Message"), "UNABLE TO SAVE");
        }
        b_add_another[index].setEnabled(false);
        b_calculate.setEnabled(false);
    }

    private void save(String p_name, int i) {

        int row_num = sheet.getLastRowNum();
        if (p_name.equals("")){
            JOptionPane.showMessageDialog(new JFrame("Message"), "at " + i + " empty value was observed and value was not updated\n All other data was updated");
            p_name="delete";
        }
        HSSFRow row = sheet.getRow(row_num);
        boolean saved = false;
        while (row.getCell(0).getStringCellValue().equals(date) && !saved) {
            if (row.getCell(1).getStringCellValue().equals(p_name)) {
                int cell_num = row.getLastCellNum();
                row.createCell(cell_num).setCellValue(t_item.getText());
                row.createCell(cell_num + 1).setCellValue(Integer.parseInt(t_amount[i].getText()));
                saved = true;
            }
            row_num = row_num - 1;
            row = sheet.getRow(row_num);
        }
        if (!saved) {
            row = sheet.createRow(sheet.getLastRowNum() + 1);
            row.createCell(0).setCellValue(date);
            row.createCell(1).setCellValue(p_name);
            row.createCell(2).setCellValue(t_item.getText());
            try {
                row.createCell(3).setCellValue(Integer.parseInt(t_amount[i].getText()));
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(new JFrame("Message"), "at " + i + " empty value was observed and was replaced by 0");
                t_amount[i].setText(0+"");
                row.createCell(3).setCellValue(0);
            }
        }
    }

    private int rdm() {
        Random random = new Random();
        return random.nextInt(255);
    }

    private void add_another() {
        y = y + 30;
        int i = 1;
        words.clear();
        HSSFRow row;
        row = sheet1.getRow(i);
        while (i <= sheet1.getLastRowNum()) {
            if (!words.contains(row.getCell(1).getStringCellValue()) &&
                    (row.getCell(4).getNumericCellValue() == 0 ||
                            row.getCell(4).getNumericCellValue() == 3 ||
                            row.getCell(4).getNumericCellValue() == 1)) {
                words.add(row.getCell(1).getStringCellValue());
            }
            i++;
            row = sheet1.getRow(i);
        }
        t_party[index] = new JTextField("");
        t_party[index].setBounds(75, y, 50, 20);
        body.add(t_party[index]);
        autoSuggestor = new AutoSuggestor(t_party[index], this, words, Color.white, Color.black, Color.black, (float) 1.0);
        t_amount[index] = new JTextField("");
        t_amount[index].setBounds(475, y, 50, 20);
        body.add(t_amount[index]);
        if (index < 10) {
            b_add_another[index + 1] = new JButton("Add Another");
            b_add_another[index + 1].setBounds(550, y - 5, 100, 30);
            b_add_another[index + 1].addActionListener(f -> add_another());
            body.add(b_add_another[index + 1]);
            b_add_another[index].setVisible(false);
        }
        index = index + 1;
        setSize(getWidth() + 1, 800);
    }

    private void back() {
        this.dispose();
        new Add();
    }
}
