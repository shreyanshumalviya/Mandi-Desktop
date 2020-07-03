package com.company;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import static java.lang.Integer.parseInt;

public class ViewPurchase extends JFrame {


    HSSFWorkbook workbook1;
    ArrayList<String> words = new ArrayList<>();
    AutoSuggestor autoSuggestor;
    ArrayList<Integer> list_of_bill_no= new ArrayList<>();
    JButton _new;
    int group=0,element_number=0;
    {
        try {
            workbook1 = new HSSFWorkbook(new FileInputStream("party.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    HSSFSheet sheet1 = Objects.requireNonNull(workbook1).getSheet("party");
    HSSFRow row;
    HSSFSheet sheet;
    HSSFWorkbook workbook;
    int row_number = 0;
    int y = 100, i = 0;
    JPanel body = new JPanel(),label_panel=new JPanel(),button_panel=new JPanel();
    JLabel l_select_by;
    JButton b_by_party;
    JButton b_by_date,b_back;
    JButton b_by_bill_no,b_till_date;
    JTextField t_party_name,t_date,t_to_date,t_bill_no;
    JButton[] b_bill_no = new JButton[99];
    JLabel[] l_party_name = new JLabel[99];
    JLabel[] l_date = new JLabel[99];
    JLabel[] l_items = new JLabel[99];
    JLabel[] l_amounts = new JLabel[99];
    JLabel l_status;
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date today = new Date();
    String today_date = formatter.format(today);
    JButton b_next_group,b_previous_group;


    public ViewPurchase() {
        setLayout(null);
        setVisible(true);
        setSize(1080, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(rdm(), rdm(), rdm()));

        //header
        JPanel heading = new JPanel();
        heading.setBounds(0, 0, 1080, 100);
        JLabel head = new JLabel("View Purchases");
        head.setFont(new Font("Monospaced", Font.BOLD, 40));
        head.setSize(100, 50);
        heading.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));
        head.setForeground(new java.awt.Color(rdm(), rdm(), rdm()));
        heading.add(head);

        //Body Panel
        body.setLayout(null);
        body.setBounds(0, 110, 1080, 600);
        body.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));

        label_panel.setLayout(null);
        label_panel.setBackground(body.getBackground());
        label_panel.setBounds(0,0,1080,100);

        button_panel.setLayout(null);
        button_panel.setBackground(body.getBackground());
        button_panel.setBounds(0,450,1080,150);

        l_select_by = new JLabel("Select By -");
        l_select_by.setBounds(50, 50, 100, 50);
        body.add(l_select_by);

        b_by_party = new JButton("By Party");
        b_by_party.setBounds(50, 120, 100, 50);
        b_by_party.addActionListener(f -> by_party());
        body.add(b_by_party);

        t_party_name = new JTextField("");
        t_party_name.setBounds(50, 200, 100, 50);
        body.add(t_party_name);

        b_by_date = new JButton("By Date");
        b_by_date.setBounds(200, 120, 100, 50);
        b_by_date.addActionListener(f -> by_date());
        body.add(b_by_date);

        t_date = new JTextField(today_date);
        t_date.setBounds(200, 200, 100, 50);
        body.add(t_date);

        t_to_date = new JTextField(today_date);
        t_to_date.setBounds(200, 270, 100, 50);
        body.add(t_to_date);

        b_till_date = new JButton("Among Date");
        b_till_date.setBounds(200, 360, 100, 50);
        b_till_date.addActionListener(f -> till_date());
        body.add(b_till_date);

        l_status = new JLabel("");
        l_status.setBounds(0, 20, 200, 20);
        label_panel.add(l_status);

        b_by_bill_no = new JButton("By Bill Number");
        b_by_bill_no.setBounds(350, 120, 100, 50);
        b_by_bill_no.addActionListener(f -> by_bill_no(parseInt(t_bill_no.getText())));
        body.add(b_by_bill_no);

        t_bill_no = new JTextField("");
        t_bill_no.setBounds(350, 200, 100, 50);
        body.add(t_bill_no);

        b_back = new JButton("Back");
        b_back.setBounds(800, 90, 200, 50);
        b_back.addActionListener(f -> back());
        button_panel.add(b_back);

        b_next_group = new JButton("Next");
        b_next_group.setBounds(500, 90, 100, 50);
        b_next_group.addActionListener(f -> next_group());
        b_next_group.setVisible(false);
        button_panel.add(b_next_group);

        b_previous_group = new JButton("Previous");
        b_previous_group.setVisible(false);
        b_previous_group.setBounds(100, 90, 100, 50);
        b_previous_group.addActionListener(f -> prev_group());
        button_panel.add(b_previous_group);

        _new = new JButton("Discard \\ New");
        _new.setBounds(800, 0, 200, 50);
        _new.addActionListener(f -> createNew());
        button_panel.add(_new);

        //add(heading);
        //add(body);

        body.add(button_panel);
        /*JScrollPane scrollPane = new JScrollPane(body);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 110, 1080, 600);
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(1080, 600));
        contentPane.add(scrollPane);

        setContentPane(contentPane);*/
        add(heading);
        add(body);
        //getContentPane().setBackground(new Color(rdm(), rdm(), rdm()));

        int i = 1;
        HSSFRow row;
        //String temp;
        row = sheet1.getRow(i);
        while (i <= sheet1.getLastRowNum()) {
            //temp = row.getCell(1).getStringCellValue();
            if (!words.contains(row.getCell(1).getStringCellValue()) &&
                    (row.getCell(4).getNumericCellValue() == 2 ||
                            row.getCell(4).getNumericCellValue() == 3)) {
                words.add(row.getCell(1).getStringCellValue());
            }
            i++;
            row = sheet1.getRow(i);
        }
        autoSuggestor = new AutoSuggestor(t_party_name, this, words, Color.white, Color.black, Color.black, (float) 1.0);

    }

    private int rdm() {
        Random random = new Random();
        return random.nextInt(255);
    }

    private void by_party() {
        l_select_by.setVisible(false);
        b_by_bill_no.setVisible(false);
        b_by_date.setVisible(false);
        b_by_party.setVisible(false);
        t_bill_no.setVisible(false);
        t_date.setVisible(false);
        t_party_name.setVisible(false);
        t_to_date.setVisible(false);
        b_till_date.setVisible(false);
        String party_name = t_party_name.getText();
        try {
            workbook = new HSSFWorkbook(new FileInputStream("purchase.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (workbook == null) {
            //   System.out.println("No connectivity...");
            System.exit(0);
        }
        sheet = workbook.getSheet("purchase");
        JLabel l_bill_no = new JLabel("Bill no.");
        l_bill_no.setBounds(50, 50, 100, 20);
        label_panel.add(l_bill_no);
        JLabel l_party_name = new JLabel("Party Name");
        l_party_name.setBounds(200, 50, 100, 20);
        label_panel.add(l_party_name);
        JLabel l_date = new JLabel("Date");
        l_date.setBounds(350, 50, 100, 20);
        label_panel.add(l_date);
        JLabel l_item = new JLabel("Items");
        l_item.setBounds(500, 50, 100, 20);
        label_panel.add(l_item);
        JLabel l_amount = new JLabel("Amount");
        l_amount.setBounds(650, 50, 100, 20);
        label_panel.add(l_amount);
        body.setAutoscrolls(true);
        String name;
        l_status.setText("Party - "+party_name);
        while (row_number < sheet.getLastRowNum()) {
            row_number = row_number + 1;
            row = sheet.getRow(row_number);
            if (!row.getCell(1).getStringCellValue().isEmpty()) {
                name = row.getCell(1).getStringCellValue();
                if (name.equalsIgnoreCase(party_name)) {
                    //  System.out.println(name);
                    //append_to_list((int) row.getCell(0).getNumericCellValue());   uncomment this
                    list_of_bill_no.add((int) row.getCell(0).getNumericCellValue());//comment this
                }
            }
        }if(list_of_bill_no.size()>9){
            b_next_group.setVisible(true);
        }
        body.add(label_panel);
        get_the_list();
        setSize(getWidth()-1, 800);
        setSize(getWidth()-1, 800);
    }


    private void append_to_list(Integer bill_number) {
        row=sheet.getRow(bill_number-99);
        b_bill_no[i] = new JButton(bill_number.toString());
        b_bill_no[i].setBounds(50, y, 100, 20);
        b_bill_no[i].addActionListener(f -> by_bill_no(bill_number));
        body.add(b_bill_no[i]);

        l_party_name[i] = new JLabel(row.getCell(1).getStringCellValue());
        l_party_name[i].setBounds(200, y, 100, 20);
        body.add(l_party_name[i]);

        l_date[i] = new JLabel(row.getCell(2).getStringCellValue());
        l_date[i].setBounds(350, y, 100, 20);
        body.add(l_date[i]);
        int no_items = (row.getLastCellNum() - 14) / 5;
        //System.out.println(no_items);
        l_items[i] = new JLabel(no_items + "");
        l_items[i].setBounds(500, y, 100, 20);
        body.add(l_items[i]);

        l_amounts[i] = new JLabel(String.valueOf((int) row.getCell(14).getNumericCellValue()));
        //System.out.println((int) row.getCell(14).getNumericCellValue());
        l_amounts[i].setBounds(650, y, 100, 20);
        body.add(l_amounts[i]);
        y = y + 30;
        i = i + 1;
    }

    private void till_date(){
        l_select_by.setVisible(false);
        b_by_bill_no.setVisible(false);
        b_by_date.setVisible(false);
        b_by_party.setVisible(false);
        t_to_date.setVisible(false);
        b_till_date.setVisible(false);
        t_bill_no.setVisible(false);
        t_date.setVisible(false);
        t_party_name.setVisible(false);
        String from_date = t_date.getText(),to_date=t_to_date.getText();
        try {
            workbook = new HSSFWorkbook(new FileInputStream("purchase.xls"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame("Message"), "Cannot get Purchase list");
            e.printStackTrace();
        }
        if (workbook == null) {
            //    System.out.println("No connectivity...");
            System.exit(0);
        }
        sheet = workbook.getSheet("purchase");
        JLabel l_bill_no = new JLabel("Bill no.");
        l_bill_no.setBounds(50, 50, 100, 20);
        label_panel.add(l_bill_no);
        JLabel l_party_name = new JLabel("Party Name");
        l_party_name.setBounds(200, 50, 100, 20);
        label_panel.add(l_party_name);
        JLabel l_date = new JLabel("Date");
        l_date.setBounds(350, 50, 100, 20);
        label_panel.add(l_date);
        JLabel l_item = new JLabel("Items");
        l_item.setBounds(500, 50, 100, 20);
        label_panel.add(l_item);
        JLabel l_amount = new JLabel("Amount");
        l_amount.setBounds(650, 50, 100, 20);
        label_panel.add(l_amount);
        body.setAutoscrolls(true);
        String name;
        l_status.setText("Date - "+from_date+" - "+to_date);
        while (row_number < sheet.getLastRowNum()) {
            row_number = row_number + 1;
            row = sheet.getRow(row_number);
            if (!row.getCell(2).getStringCellValue().isEmpty()) {
                name = row.getCell(2).getStringCellValue();
                try {
                    if (!formatter.parse(name).before(formatter.parse(from_date))&&!formatter.parse(name).after(formatter.parse(to_date))&&!row.getCell(1).getStringCellValue().equals("delete")) {
                        list_of_bill_no.add((int) row.getCell(0).getNumericCellValue());
                        //append_to_list((int) row.getCell(0).getNumericCellValue());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(new JFrame("message"),"Dates incorrectly entered");
                }
            }
        }
        if(list_of_bill_no.size()>9){
            b_next_group.setVisible(true);
        }
        body.add(label_panel);
        get_the_list();
        setSize(getWidth()-1, 800);
    }

    private void next_group(){
        body.removeAll();
        body.add(button_panel);
        body.add(label_panel);
        group=group+10;
        element_number=0;
        i=0;
        y=100;
        if(group>list_of_bill_no.size()-10){b_next_group.setVisible(false);}
        b_previous_group.setVisible(true);
        get_the_list();
        setSize(getWidth()-1, 800);
    }

    private void prev_group(){
        body.removeAll();
        body.add(button_panel);
        body.add(label_panel);
        group=group-10;
        element_number=0;
        i=0;
        y=100;
        if(group<1){b_previous_group.setVisible(false);}
        b_next_group.setVisible(true);
        get_the_list();
        setSize(getWidth()+1, 800);
    }

    private void get_the_list(){
        int number=group+element_number;
        while (element_number<10&&number<list_of_bill_no.size()){
            append_to_list(list_of_bill_no.get(group+element_number));
            element_number++;
            number++;
        }
    }

    private void by_date() {
        l_select_by.setVisible(false);
        b_by_bill_no.setVisible(false);
        b_by_date.setVisible(false);
        b_by_party.setVisible(false);
        t_to_date.setVisible(false);
        b_till_date.setVisible(false);
        t_bill_no.setVisible(false);
        t_date.setVisible(false);
        t_party_name.setVisible(false);
        String date = t_date.getText();
        try {
            workbook = new HSSFWorkbook(new FileInputStream("purchase.xls"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame("Message"), "Cannot get Purchase list");
            e.printStackTrace();
        }
        if (workbook == null) {
            //    System.out.println("No connectivity...");
            System.exit(0);
        }
        sheet = workbook.getSheet("purchase");
        JLabel l_bill_no = new JLabel("Bill no.");
        l_bill_no.setBounds(50, 50, 100, 20);
        label_panel.add(l_bill_no);
        JLabel l_party_name = new JLabel("Party Name");
        l_party_name.setBounds(200, 50, 100, 20);
        label_panel.add(l_party_name);
        JLabel l_date = new JLabel("Date");
        l_date.setBounds(350, 50, 100, 20);
        label_panel.add(l_date);
        JLabel l_item = new JLabel("Items");
        l_item.setBounds(500, 50, 100, 20);
        label_panel.add(l_item);
        JLabel l_amount = new JLabel("Amount");
        l_amount.setBounds(650, 50, 100, 20);
        label_panel.add(l_amount);
        body.setAutoscrolls(true);
        String name;
        body.add(label_panel);
        l_status.setText("Date - "+date);
        while (row_number < sheet.getLastRowNum()) {
            row_number = row_number + 1;
            row = sheet.getRow(row_number);
            if (!row.getCell(2).getStringCellValue().isEmpty()) {
                name = row.getCell(2).getStringCellValue();
                if (name.equalsIgnoreCase(date)&&!row.getCell(1).getStringCellValue().equals("delete")) {
                    //append_to_list((int) row.getCell(0).getNumericCellValue());
                    list_of_bill_no.add((int) row.getCell(0).getNumericCellValue());
                }
            }
        }
        if(list_of_bill_no.size()>9){
            b_next_group.setVisible(true);
        }
        body.add(label_panel);
        get_the_list();
        setSize(getWidth()-1, 800);
        setSize(getWidth()-1, 800);
    }

    private void createNew(){
        this.dispose();
        new ViewPurchase();
    }

    private void by_bill_no(int bill_no) {
        this.dispose();
        try {
            workbook = new HSSFWorkbook(new FileInputStream("purchase.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (workbook == null) {
            System.exit(0);
        }
        sheet = workbook.getSheet("purchase");

        if (sheet.getRow(bill_no - 99)==null) {
            new ViewPurchase();
            JOptionPane.showMessageDialog(new JFrame("message"), "Bill no : " + bill_no + " not found");
        } else {
            try {
                new Purchase(bill_no);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void back() {
        this.dispose();
        new Add();
    }
}
