package com.company;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import static java.lang.Integer.parseInt;

import java.io.*;

public class Purchase extends JFrame {
    String st_ledg = "";
    JTextArea t_ledg;
    HSSFWorkbook workbook1;

    {
        try {
            workbook1 = new HSSFWorkbook(new FileInputStream("party.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    HSSFSheet sheet1 = Objects.requireNonNull(workbook1).getSheet("party");
    ArrayList<String> words = new ArrayList<>();
    int hammali_rate = 5;
    int tax_rate = 1;
    HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("purchase.xls"));
    HSSFSheet sheet = workbook.getSheet("purchase");
    //String receipt;
    int y = 250;
    String name;
    int bill_no = getBill_no();
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date today = new Date();
    String date = formatter.format(today);
    JButton _new;
    JButton view;
    JTextField t_hammali;
    JTextField t_cash;
    JTextField t_bhada;
    JTextField t_comm_rate;
    JTextField t_partyName;
    JTextField t_bhada_rate;
    JTextField t_comm;
    JTextField t_net_amount;
    JTextField t_to_exp;
    JTextField t_Driver;
    JButton calculate = new JButton("Calculate");
    JPanel body = new JPanel();
    JTextField t_bill_total;
    JTextField t_Station_Charge;
    JTextField t_tax;

    int index = 0;
    HSSFRow row;
    JButton[] b_add_another = new JButton[20];
    JTextField[] t_item = new JTextField[20];
    JTextField[] t_bag = new JTextField[20];
    JTextField[] t_quantity = new JTextField[20];
    JTextField[] t_rate = new JTextField[20];
    JTextField[] t_amount = new JTextField[20];
    AutoSuggestor autoSuggestor;

    public Purchase() throws IOException {

        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(rdm(), rdm(), rdm()));

        //header
        JPanel heading = new JPanel();
        heading.setBounds(0, 0, 1080, 100);
        JLabel head = new JLabel("Purchase");
        head.setFont(new Font("Monospaced", Font.BOLD, 40));
        head.setSize(100, 50);
        heading.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));
        head.setForeground(new java.awt.Color(rdm(), rdm(), rdm()));
        heading.add(head);

        //Body Panel
        body.setLayout(null);
        body.setBounds(0, 110, 1080, 600);
        body.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));
//Line 0
        JLabel l_partyName = new JLabel("Party Name :-");
        l_partyName.setBounds(50, 50, 100, 20);
        t_partyName = new JTextField("");
        t_partyName.setBounds(160, 50, 100, 20);
        body.add(l_partyName);
        body.add(t_partyName);

        JButton addParty = new JButton("Add New");
        addParty.setBounds(650, 50, 100, 30);
        addParty.addActionListener(f -> addParty());
        body.add(addParty);

//Line 1
        JLabel l_bill_no = new JLabel("Bill No. -");
        l_bill_no.setBounds(50, 100, 60, 20);
        JLabel t_bill_no = new JLabel(bill_no + "");
        t_bill_no.setBounds(100, 100, 50, 20);
        JLabel l_date = new JLabel("Date -");
        l_date.setBounds(160, 100, 40, 20);
        JLabel t_date = new JLabel(date);
        t_date.setBounds(200, 100, 70, 20);
        JLabel l_bhada = new JLabel("Bhada -");
        l_bhada.setBounds(270, 100, 50, 20);
        t_bhada = new JTextField("00");
        t_bhada.setBounds(320, 100, 30, 20);
        JLabel l_hammali = new JLabel("Hammali -");
        l_hammali.setBounds(360, 100, 60, 20);
        t_hammali = new JTextField("0");
        t_hammali.setBounds(420, 100, 30, 20);
        JLabel l_cash = new JLabel("Cash -");
        l_cash.setBounds(480, 100, 50, 20);
        t_cash = new JTextField("0");
        t_cash.setBounds(530, 100, 30, 20);

        body.add(l_bhada);
        body.add(t_cash);
        body.add(t_hammali);
        body.add(t_bhada);
        body.add(l_cash);
        body.add(l_hammali);
        body.add(t_date);
        body.add(l_date);
        body.add(l_bill_no);
        body.add(t_bill_no);
//Line 2
        JLabel l_comm_rate = new JLabel("Comm rate -");
        l_comm_rate.setBounds(50, 150, 100, 20);
        t_comm_rate = new JTextField("0");
        t_comm_rate.setBounds(150, 150, 30, 20);

        JLabel l_bhada_rate = new JLabel("Bhada Rate -");
        l_bhada_rate.setBounds(250, 150, 100, 20);
        t_bhada_rate = new JTextField("5");
        t_bhada_rate.setBounds(350, 150, 30, 20);

        JLabel l_comm = new JLabel("Comm -");
        l_comm.setBounds(480, 150, 50, 20);
        t_comm = new JTextField("0");
        t_comm.setBounds(530, 150, 30, 20);


        body.add(l_bhada_rate);
        body.add(t_bhada_rate);
        body.add(l_comm);
        body.add(t_comm);
        body.add(l_comm_rate);
        body.add(t_comm_rate);

        JButton back = new JButton("Back");
        back.setBounds(800, 540, 200, 50);
        back.addActionListener(f -> back());
        body.add(back);
//Line 3
        JLabel l_tax = new JLabel("Tax -");
        l_tax.setBounds(50, 200, 100, 20);
        t_tax = new JTextField("50");
        t_tax.setBounds(150, 200, 30, 20);

        JLabel l_Station_Charge = new JLabel("Station Charge -");
        l_Station_Charge.setBounds(250, 200, 100, 20);
        t_Station_Charge = new JTextField("75");
        t_Station_Charge.setBounds(350, 200, 30, 20);

        JLabel l_Driver = new JLabel("Driver -");
        l_Driver.setBounds(480, 200, 50, 20);
        t_Driver = new JTextField("200");
        t_Driver.setBounds(530, 200, 30, 20);


        body.add(l_tax);
        body.add(t_tax);
        body.add(l_Station_Charge);
        body.add(t_Station_Charge);
        body.add(l_Driver);
        body.add(t_Driver);
//line 4
        JLabel l_Item = new JLabel("Item");
        l_Item.setBounds(75, 250, 50, 20);
        body.add(l_Item);
        JLabel l_bag = new JLabel("Bag");
        l_bag.setBounds(175, 250, 50, 20);
        body.add(l_bag);
        JLabel l_quantity = new JLabel("Quantity");
        l_quantity.setBounds(275, 250, 50, 20);
        body.add(l_quantity);
        JLabel l_rate = new JLabel("Rate");
        l_rate.setBounds(375, 250, 50, 20);
        body.add(l_rate);
        JLabel l_amount = new JLabel("Amount");
        l_amount.setBounds(475, 250, 50, 20);
        body.add(l_amount);
        calculate.setBounds(800, 250, 200, 50);
        calculate.addActionListener(f -> calculate());
        body.add(calculate);

        b_add_another[0] = new JButton("Add Another");
        b_add_another[0].setBounds(575, 250, 100, 30);
        b_add_another[0].addActionListener(f -> add_another());
        body.add(b_add_another[0]);

        JLabel l_bill;
        l_bill = new JLabel("Bill total -");
        l_bill.setBounds(325, 550, 100, 20);
        JLabel l_to_exp = new JLabel("To Exp - ");
        l_to_exp.setBounds(50, 550, 100, 20);
        t_to_exp = new JTextField("");
        t_to_exp.setBounds(100, 550, 100, 20);
        t_bill_total = new JTextField("");
        t_bill_total.setBounds(400, 550, 150, 20);
        JLabel l_net_amount = new JLabel("Net Amount -");
        l_net_amount.setBounds(575, 550, 100, 20);
        t_net_amount = new JTextField("0");
        t_net_amount.setBounds(670, 550, 100, 20);
        body.add(l_net_amount);
        body.add(t_net_amount);
        body.add(l_to_exp);
        body.add(l_bill);
        body.add(t_bill_total);
        body.add(t_to_exp);
        JButton print = new JButton("Save & Print");
        print.setBounds(800, 350, 200, 50);
        print.addActionListener(f -> print());
        body.add(print);

        _new = new JButton("Discard \\ New");
        _new.setBounds(800, 450, 200, 50);
        _new.addActionListener(f -> createNew());
        body.add(_new);

        view = new JButton("View");
        view.setBounds(800, 50, 200, 50);
        view.addActionListener(f -> view());
        body.add(view);

        add(heading);
        add(body);

        int i = 1;
        HSSFRow row;
        row = sheet1.getRow(i);
        while (i <= sheet1.getLastRowNum()) {
            if (!words.contains(row.getCell(1).getStringCellValue()) &&
                    (row.getCell(4).getNumericCellValue() == 2 ||
                            row.getCell(4).getNumericCellValue() == 3)) {
                words.add(row.getCell(1).getStringCellValue());
            }
            i++;
            row = sheet1.getRow(i);
        }
        autoSuggestor = new AutoSuggestor(t_partyName, this, words, Color.white, Color.black, Color.black, (float) 1.0);
        t_ledg = new JTextArea("");
        JScrollPane scrollBar = new JScrollPane(t_ledg);
        scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        //t_ledg.setBounds(1000, 1000, 750, 600);
        t_ledg.setAutoscrolls(true);
        t_ledg.setVisible(false);
        t_ledg.setFont(new Font("monospaced", Font.BOLD, 8));
        body.add(t_ledg);
        setSize(1080, 800);
    }

    public Purchase(Integer _bill_no) throws IOException, NullPointerException {
        bill_no = _bill_no;
        row = sheet.getRow(_bill_no - 99);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(rdm(), rdm(), rdm()));

        //header
        JPanel heading = new JPanel();
        heading.setBounds(0, 0, 1080, 100);
        JLabel head = new JLabel("Purchase");
        head.setFont(new Font("Monospaced", Font.BOLD, 40));
        head.setSize(100, 50);
        heading.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));
        head.setForeground(new java.awt.Color(rdm(), rdm(), rdm()));
        heading.add(head);

        //Body Panel
        body.setLayout(null);
        body.setBounds(0, 110, 1080, 600);
        body.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));
//Line 0
        JLabel l_partyName = new JLabel("Party Name :-");
        l_partyName.setBounds(50, 50, 100, 20);

        t_partyName = new JTextField(row.getCell(1).getStringCellValue());
        t_partyName.setBounds(160, 50, 100, 20);
        body.add(l_partyName);
        body.add(t_partyName);

        JButton addParty = new JButton("Add New");
        addParty.setBounds(650, 50, 100, 30);
        addParty.addActionListener(f -> addParty());
        body.add(addParty);

//Line 1
        JLabel l_bill_no = new JLabel("Bill No. -");
        l_bill_no.setBounds(50, 100, 60, 20);
        JLabel t_bill_no = new JLabel(String.valueOf((int) row.getCell(0).getNumericCellValue()));
        t_bill_no.setBounds(100, 100, 50, 20);
        JLabel l_date = new JLabel("Date -");
        l_date.setBounds(160, 100, 40, 20);
        JLabel t_date = new JLabel(row.getCell(2).getStringCellValue());
        t_date.setBounds(200, 100, 70, 20);
        JLabel l_bhada = new JLabel("Bhada -");
        l_bhada.setBounds(270, 100, 50, 20);
        t_bhada = new JTextField(String.valueOf((int) row.getCell(4).getNumericCellValue()));
        t_bhada.setBounds(320, 100, 30, 20);
        JLabel l_hammali = new JLabel("Hammali -");
        l_hammali.setBounds(360, 100, 60, 20);
        t_hammali = new JTextField(String.valueOf((int) row.getCell(5).getNumericCellValue()));
        t_hammali.setBounds(420, 100, 30, 20);
        JLabel l_cash = new JLabel("Cash -");
        l_cash.setBounds(480, 100, 50, 20);
        t_cash = new JTextField(String.valueOf((int) row.getCell(6).getNumericCellValue()));
        t_cash.setBounds(530, 100, 30, 20);

        body.add(l_bhada);
        body.add(t_cash);
        body.add(t_hammali);
        body.add(t_bhada);
        body.add(l_cash);
        body.add(l_hammali);
        body.add(t_date);
        body.add(l_date);
        body.add(l_bill_no);
        body.add(t_bill_no);
//Line 2
        JLabel l_comm_rate = new JLabel("Comm rate -");
        l_comm_rate.setBounds(50, 150, 100, 20);
        t_comm_rate = new JTextField(String.valueOf((int) row.getCell(7).getNumericCellValue()));
        t_comm_rate.setBounds(150, 150, 30, 20);

        JLabel l_bhada_rate = new JLabel("Bhada Rate -");
        l_bhada_rate.setBounds(250, 150, 100, 20);
        t_bhada_rate = new JTextField(String.valueOf((int) row.getCell(3).getNumericCellValue()));
        t_bhada_rate.setBounds(350, 150, 30, 20);

        JLabel l_comm = new JLabel("Comm -");
        l_comm.setBounds(480, 150, 50, 20);
        t_comm = new JTextField(String.valueOf((int) row.getCell(8).getNumericCellValue()));
        t_comm.setBounds(530, 150, 30, 20);


        body.add(l_bhada_rate);
        body.add(t_bhada_rate);
        body.add(l_comm);
        body.add(t_comm);
        body.add(l_comm_rate);
        body.add(t_comm_rate);

        JButton back = new JButton("Back");
        back.setBounds(800, 540, 200, 50);
        back.addActionListener(f -> back());
        body.add(back);
//Line 3
        JLabel l_tax = new JLabel("Tax -");
        l_tax.setBounds(50, 200, 100, 20);
        t_tax = new JTextField(String.valueOf((int) row.getCell(10).getNumericCellValue()));
        t_tax.setBounds(150, 200, 30, 20);

        JLabel l_Station_Charge = new JLabel("Station Charge -");
        l_Station_Charge.setBounds(250, 200, 100, 20);
        t_Station_Charge = new JTextField(String.valueOf((int) row.getCell(9).getNumericCellValue()));
        t_Station_Charge.setBounds(350, 200, 30, 20);

        JLabel l_Driver = new JLabel("Driver -");
        l_Driver.setBounds(480, 200, 50, 20);
        t_Driver = new JTextField(String.valueOf((int) row.getCell(11).getNumericCellValue()));
        t_Driver.setBounds(530, 200, 30, 20);

        body.add(l_tax);
        body.add(t_tax);
        body.add(l_Station_Charge);
        body.add(t_Station_Charge);
        body.add(l_Driver);
        body.add(t_Driver);
//line 4
        JLabel l_Item = new JLabel("Item");
        l_Item.setBounds(75, 250, 50, 20);
        body.add(l_Item);
        JLabel l_bag = new JLabel("Bag");
        l_bag.setBounds(175, 250, 50, 20);
        body.add(l_bag);
        JLabel l_quantity = new JLabel("Quantity");
        l_quantity.setBounds(275, 250, 50, 20);
        body.add(l_quantity);
        JLabel l_rate = new JLabel("Rate");
        l_rate.setBounds(375, 250, 50, 20);
        body.add(l_rate);
        JLabel l_amount = new JLabel("Amount");
        l_amount.setBounds(475, 250, 50, 20);
        body.add(l_amount);
        JLabel l_confirm = new JLabel("Yes/No");
        l_confirm.setBounds(575, 250, 50, 20);
        body.add(l_confirm);

        calculate.setBounds(800, 250, 200, 50);
        calculate.addActionListener(f -> calculate());
        body.add(calculate);

        /*b_add_another[0] = new JButton("Add Another");
        b_add_another[0].setBounds(575, 250, 100, 30);
        b_add_another[0].addActionListener(f -> add_another());
        body.add(b_add_another[0]);*/

        JLabel l_bill;
        l_bill = new JLabel("Bill total -");
        l_bill.setBounds(325, 550, 100, 20);
        JLabel l_to_exp = new JLabel("To Exp - ");
        l_to_exp.setBounds(50, 550, 100, 20);
        t_to_exp = new JTextField(String.valueOf((int) row.getCell(13).getNumericCellValue()));
        t_to_exp.setBounds(100, 550, 100, 20);
        t_bill_total = new JTextField(String.valueOf((int) row.getCell(12).getNumericCellValue()));
        t_bill_total.setBounds(400, 550, 150, 20);
        JLabel l_net_amount = new JLabel("Net Amount -");
        l_net_amount.setBounds(575, 550, 100, 20);
        t_net_amount = new JTextField(String.valueOf((int) row.getCell(14).getNumericCellValue()));
        t_net_amount.setBounds(670, 550, 100, 20);
        body.add(l_net_amount);
        body.add(t_net_amount);
        body.add(l_to_exp);
        body.add(l_bill);
        body.add(t_bill_total);
        body.add(t_to_exp);
        //body.add(b_add_another[0]);
        viewingItem();
        JButton print = new JButton("Update & Print");
        print.setBounds(800, 350, 200, 50);
        print.addActionListener(f -> print());
        body.add(print);

        _new = new JButton("Discard \\ New");
        _new.setBounds(800, 450, 200, 50);
        _new.addActionListener(f -> createNew());
        body.add(_new);

        view = new JButton("View Another");
        view.setBounds(800, 50, 200, 50);
        view.addActionListener(f -> view());
        body.add(view);

        add(heading);
        add(body);
        int i = 1;
        HSSFRow row;
        row = sheet1.getRow(i);
        while (i <= sheet1.getLastRowNum()) {
            if (!words.contains(row.getCell(1).getStringCellValue()) &&
                    (row.getCell(4).getNumericCellValue() == 2 ||
                            row.getCell(4).getNumericCellValue() == 3)) {
                words.add(row.getCell(1).getStringCellValue());
            }
            i++;
            row = sheet1.getRow(i);
        }
        autoSuggestor = new AutoSuggestor(t_partyName, this, words, Color.white, Color.gray, Color.black, (float) 0.9);
        t_ledg = new JTextArea("");
        JScrollPane scrollBar = new JScrollPane(t_ledg);
        scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        //t_ledg.setBounds(1000, 1000, 750, 600);
        t_ledg.setAutoscrolls(true);
        t_ledg.setVisible(false);
        t_ledg.setFont(new Font("monospaced", Font.BOLD, 8));
        body.add(t_ledg);
        setSize(1080, 800);
    }

    private int getBill_no() {
        return sheet.getLastRowNum() + 100;
    }

    private int rdm() {
        Random random = new Random();
        return random.nextInt(255);
    }

    private void back() {
        this.dispose();
        new Add();
    }

    private void view() {
        this.dispose();
        new ViewPurchase();
    }

    private void add_another() {
        y = y + 30;
        if (index > 0) {
            t_amount[index - 1].setText(parseInt(t_rate[index - 1].getText()) * parseInt(t_quantity[index - 1].getText()) + "");
        }
        t_item[index] = new JTextField("");
        t_item[index].setBounds(75, y, 50, 20);
        body.add(t_item[index]);
        t_bag[index] = new JTextField("0");
        t_bag[index].setBounds(175, y, 50, 20);
        body.add(t_bag[index]);
        t_quantity[index] = new JTextField("0");
        t_quantity[index].setBounds(275, y, 50, 20);
        body.add(t_quantity[index]);
        t_rate[index] = new JTextField("0");
        t_rate[index].setBounds(375, y, 50, 20);
        body.add(t_rate[index]);
        t_amount[index] = new JTextField("0");
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

    private void calculate() {
        if (t_comm_rate.getText().equals("0")) {
            int row_no = 1;
            while (!t_partyName.getText().equals(sheet1.getRow(row_no).getCell(1).getStringCellValue()) && row_no < sheet1.getLastRowNum()) {
                row_no++;
            }
            System.out.println(row_no);
            t_comm_rate.setText(String.valueOf((int) sheet1.getRow(row_no).getCell(5).getNumericCellValue()));
        }
        int total_amount = 0;
        int bags = 0;
        int i = 0;
        while (i < index) {
            t_amount[i].setText(parseInt(t_rate[i].getText()) * parseInt(t_quantity[i].getText()) + "");
            total_amount = total_amount + parseInt(t_amount[i].getText());
            bags = bags + parseInt(t_bag[i].getText());
            i = i + 1;
        }
        t_hammali.setText(bags * hammali_rate + "");
        t_tax.setText(bags * tax_rate + "");
        t_bhada.setText(bags * parseInt(t_bhada_rate.getText()) + "");
        t_bill_total.setText(total_amount + "");
        t_comm.setText((parseInt(t_comm_rate.getText()) * total_amount) / 100 + "");
        t_to_exp.setText((parseInt(t_bhada.getText()) + parseInt(t_comm.getText()) + parseInt(t_Driver.getText()) + parseInt(t_Station_Charge.getText()) + parseInt(t_hammali.getText()) + parseInt(t_tax.getText()) + parseInt(t_cash.getText())) + "");
        total_amount = total_amount - parseInt(t_to_exp.getText());
        t_net_amount.setText(total_amount + "");
    }

    private void print() {
        name = bill_no + "_purchase.txt";
        try {
            File file = new File(name);
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println("                           DUKAAN KA NAAM ");
            printWriter.println("-------------------------------------------------------------");
            printWriter.println("  Bill no - " + bill_no + "  Name - " + t_partyName.getText() + "        Date - " + date);
            printWriter.println("\n  Hammali - " + t_hammali.getText() + "                  Cash - " + t_cash.getText());
            printWriter.println("  Comm  - " + t_comm.getText() + "                      Tax - " + t_tax.getText());
            printWriter.println("  Bhada - " + t_bhada.getText() + "                  Hammali - " + t_hammali.getText());
            printWriter.println("  Station Charge - " + t_Station_Charge.getText());
            int i = 0;
            printWriter.println("\n\n  Item      | Bag |Rate|Quantity|Amount");
            while (i < index) {
                printWriter.print("  " + t_item[i].getText() + space(10, t_item[i].getText().length()) + t_bag[i].getText() + space(5, t_bag[i].getText().length()) + t_rate[i].getText() + space(4, t_rate[i].getText().length()) + t_quantity[i].getText() + space(8, t_quantity[i].getText().length()) + t_amount[i].getText() + "\n");
                i = i + 1;
            }
            printWriter.println("\n____________________________________________________________________");
            printWriter.println("\n  To Exp.            Bill Total          Net Amount  ");
            printWriter.println("  " + t_to_exp.getText() + "                 " + t_bill_total.getText() + "                " + t_net_amount.getText() + "  ");
            printWriter.close();
            save();
            _print();
            _new.setText("New");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame("Message"), "Cannot save as some fields are empty.\nTry Pressing Calculate button");
        }
    }

    private void createNew() {
        this.dispose();
        try {
            new Purchase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void _print() {
        String strLine;
        try {
            FileInputStream fstream = new FileInputStream(name);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
//Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                //noinspection StringConcatenationInLoop
                st_ledg += strLine + "\n";
            }
//Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        t_ledg.setText(st_ledg);
        t_ledg.setVisible(true);

        //print_s lt = new print_s();
        //lt.printString(st_ledg);
        //lt.printString(t_ledg.getText());
        try {
            t_ledg.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }

    private void save() throws Exception {
        boolean savable = true;
        HSSFRow row = sheet.createRow(bill_no - 99);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(bill_no);
        cell = row.createCell(1);
        cell.setCellValue(t_partyName.getText());
        if (t_partyName.getText().equals("")) {
            savable = false;
            JOptionPane.showMessageDialog(new JFrame("Message"), "Name is empty");
        }
        cell = row.createCell(2);
        cell.setCellValue(date);
        if (date.equals("")) {
            savable = false;
            JOptionPane.showMessageDialog(new JFrame("Message"), "date is empty");
        }
        cell = row.createCell(3);
        cell.setCellValue(parseInt(t_bhada_rate.getText()));
        if (t_bhada_rate.getText().equals("")) {
            savable = false;
            JOptionPane.showMessageDialog(new JFrame("Message"), "Bhada Rate is empty");
        }
        cell = row.createCell(4);
        cell.setCellValue(parseInt(t_bhada.getText()));
        if (t_bhada.getText().equals("")) {
            savable = false;
            JOptionPane.showMessageDialog(new JFrame("Message"), "bhada is empty");
        }
        cell = row.createCell(5);
        cell.setCellValue(parseInt(t_hammali.getText()));
        if (t_hammali.getText().equals("")) {
            savable = false;
            JOptionPane.showMessageDialog(new JFrame("Message"), "hammali is empty");
        }
        cell = row.createCell(6);
        cell.setCellValue(parseInt(t_cash.getText()));
        if (t_cash.getText().equals("")) {
            savable = false;
            JOptionPane.showMessageDialog(new JFrame("Message"), "cash is empty");
        }
        cell = row.createCell(7);
        cell.setCellValue(parseInt(t_comm_rate.getText()));
        if (t_comm_rate.getText().equals("")) {
            savable = false;
            JOptionPane.showMessageDialog(new JFrame("Message"), "comm rate is empty");
        }
        cell = row.createCell(8);
        cell.setCellValue(parseInt(t_comm.getText()));
        if (t_comm.getText().equals("")) {
            savable = false;
            JOptionPane.showMessageDialog(new JFrame("Message"), "Commission is empty");
        }
        cell = row.createCell(9);
        cell.setCellValue(parseInt(t_Station_Charge.getText()));
        if (t_Station_Charge.getText().equals("")) {
            savable = false;
            JOptionPane.showMessageDialog(new JFrame("Message"), "Station Charge is empty");
        }
        cell = row.createCell(10);
        cell.setCellValue(parseInt(t_tax.getText()));
        if (t_tax.getText().equals("")) {
            savable = false;
            JOptionPane.showMessageDialog(new JFrame("Message"), "tax is empty");
        }
        cell = row.createCell(11);
        cell.setCellValue(parseInt(t_Driver.getText()));
        if (t_Driver.getText().equals("")) {
            savable = false;
            JOptionPane.showMessageDialog(new JFrame("Message"), "driver is empty");
        }
        cell = row.createCell(12);
        cell.setCellValue(parseInt(t_bill_total.getText()));
        if (t_bill_total.getText().equals("")) {
            savable = false;
            JOptionPane.showMessageDialog(new JFrame("Message"), "bill total is empty");
        }
        cell = row.createCell(13);
        cell.setCellValue(parseInt(t_to_exp.getText()));
        if (t_to_exp.getText().equals("")) {
            savable = false;
            JOptionPane.showMessageDialog(new JFrame("Message"), "t_to_exp is empty");
        }
        cell = row.createCell(14);
        cell.setCellValue(parseInt(t_net_amount.getText()));
        if (t_net_amount.getText().equals("")) {
            savable = false;
            JOptionPane.showMessageDialog(new JFrame("Message"), "Net Amount is empty");
        }
        int i = 0;
        while (i < index) {
            cell = row.createCell(15 + 5 * i);
            cell.setCellValue(t_item[i].getText());
            if (t_item[i].getText().equals("")) {
                savable = false;
                JOptionPane.showMessageDialog(new JFrame("Message"), "item in row " + i + 1 + " is empty");
            }

            cell = row.createCell(16 + 5 * i);
            cell.setCellValue(parseInt(t_bag[i].getText()));
            if (t_bag[i].getText().equals("")) {
                savable = false;
                JOptionPane.showMessageDialog(new JFrame("Message"), "bag in row " + i + " is empty");
            }

            cell = row.createCell(17 + 5 * i);
            cell.setCellValue(parseInt(t_quantity[i].getText()));
            if (t_quantity[i].getText().equals("")) {
                savable = false;
                JOptionPane.showMessageDialog(new JFrame("Message"), "quantity in row " + i + " is empty");
            }

            cell = row.createCell(18 + 5 * i);
            cell.setCellValue(parseInt(t_rate[i].getText()));
            if (t_rate[i].getText().equals("")) {
                savable = false;
                JOptionPane.showMessageDialog(new JFrame("Message"), "rate in row " + i + " is empty");
            }

            cell = row.createCell(19 + 5 * i);
            cell.setCellValue(parseInt(t_amount[i].getText()));
            if (t_amount[i].getText().equals("")) {
                savable = false;
                JOptionPane.showMessageDialog(new JFrame("Message"), "amount in row " + i + " is empty");
            }
            i = i + 1;
        }
        if (savable) {
            workbook.write(new FileOutputStream("purchase.xls"));
            JOptionPane.showMessageDialog(new JFrame("Message"), "SAVED SUCCESSFULLY");
        } else {
            sheet.removeRow(row);
        }
    }


    private String space(int a, int len) {
        StringBuilder spaces = new StringBuilder();
        int i = 0;
        while (i < a - len) {
            spaces.append(" ");
            i++;
        }
        spaces.append('|');
        return spaces.toString();
    }

    private void addParty() {
        new AddParty(t_partyName.getText());
        try {
            workbook1 = new HSSFWorkbook(new FileInputStream("party.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet1 = workbook1.getSheet("party");
        autoSuggestor.addWordToSuggestions(sheet1.getRow(sheet1.getLastRowNum()).getCell(1).getStringCellValue());
    }

    private void viewingItem() {
        index = (row.getLastCellNum() - 9) / 5 - 1;
        int i = 0;
        while (i < index) {
            y = y + 30;
            /*if (i > 0) {
                t_amount[i - 1].setText(String.valueOf(parseInt(t_rate[i - 1].getText()) * parseInt(t_quantity[i - 1].getText())));
            }*/
            t_item[i] = new JTextField(row.getCell(15 + 5 * i).getStringCellValue());
            t_item[i].setBounds(75, y, 50, 20);
            body.add(t_item[i]);
            t_bag[i] = new JTextField(String.valueOf((int) row.getCell(15 + 5 * i + 1).getNumericCellValue()));
            t_bag[i].setBounds(175, y, 50, 20);
            body.add(t_bag[i]);
            t_quantity[i] = new JTextField(String.valueOf((int) row.getCell(15 + 5 * i + 2).getNumericCellValue()));
            t_quantity[i].setBounds(275, y, 50, 20);
            body.add(t_quantity[i]);
            t_rate[i] = new JTextField(String.valueOf((int) row.getCell(15 + 5 * i + 3).getNumericCellValue()));
            t_rate[i].setBounds(375, y, 50, 20);
            body.add(t_rate[i]);
            t_amount[i] = new JTextField(String.valueOf((int) row.getCell(15 + 5 * i + 4).getNumericCellValue()));
            t_amount[i].setBounds(475, y, 50, 20);
            body.add(t_amount[i]);
            i++;
        }
        b_add_another[i] = new JButton("Add Another");
        b_add_another[i].setBounds(550, y - 5, 100, 30);
        b_add_another[i].addActionListener(f -> add_another());
        body.add(b_add_another[i]);
            /*if (i > 0) {
                b_add_another[i].setVisible(false);
            }*/
    }

   /* private void _print2() {
        String strLine="";
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        while (true) {
            try {
                if (!((strLine = br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            //String concatenation allowed
            receipt += strLine;
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        print_s lt = new print_s();
        lt.printString(receipt);
    }

    private void another_print(){
        FileInputStream textStream = null;
        try {
            textStream = new FileInputStream(name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc mydoc = new SimpleDoc(textStream, flavor, null);

        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(new PageRanges(1, 1));
        aset.add(new Copies(1));
        PrintService[] services = PrintServiceLookup.lookupPrintServices(
                flavor,aset);
        PrintService service = services[0];

        if (service != null)
        {
            DocPrintJob job = service.createPrintJob();
            try {
                job.print(mydoc, aset);
            } catch (PrintException e) {
                e.printStackTrace();
            }
        }

    }

*/
}