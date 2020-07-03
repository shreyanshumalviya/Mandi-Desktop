package com.company;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class ViewParty extends JFrame {
    String name;
    int opening;
    JButton b_print;
    JTextField t_p_name, t_curr, t_mob_no, t_addr;
    JTextArea t_history;
    int p_row_no, pur_row_no, v_row_no, s_row_no, curr_bal;
    HSSFWorkbook workbook;
    ArrayList<String> words = new ArrayList<>();
    ArrayList<Integer> row_numbers = new ArrayList<>();
    AutoSuggestor autoSuggestor;
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date today = new Date();
    String date = formatter.format(today);

    {
        try {
            workbook = new HSSFWorkbook(new FileInputStream("sell.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    HSSFSheet sell_sheet = Objects.requireNonNull(workbook).getSheet("sell");
    HSSFWorkbook workbook3;

    {
        try {
            workbook3 = new HSSFWorkbook(new FileInputStream("purchase.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    HSSFSheet purchase_sheet = Objects.requireNonNull(workbook3).getSheet("purchase");
    JScrollPane sp;
    HSSFWorkbook workbook1;
    JButton b_history;

    {
        try {
            workbook1 = new HSSFWorkbook(new FileInputStream("party.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    HSSFSheet party_sheet = Objects.requireNonNull(workbook1).getSheet("party");

    HSSFWorkbook workbook2;

    {
        try {
            workbook2 = new HSSFWorkbook(new FileInputStream("vasuli.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    JButton b_delete;
    HSSFSheet vasuli_sheet = Objects.requireNonNull(workbook2).getSheet("vasuli");
    JTextField t_start_date, t_end_date;

    public ViewParty() {
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(rdm(), rdm(), rdm()));

        //header
        JPanel heading = new JPanel();
        heading.setBounds(0, 0, 1080, 100);
        JLabel head = new JLabel("View Party");
        head.setFont(new Font("Monospaced", Font.BOLD, 40));
        head.setSize(100, 50);
        heading.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));
        head.setForeground(new java.awt.Color(rdm(), rdm(), rdm()));
        heading.add(head);

        //Body Panel
        JPanel body = new JPanel();
        body.setLayout(null);
        body.setBounds(0, 110, 1080, 800);
        body.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));
//line 1
        JLabel l_p_name = new JLabel("Party Name");
        l_p_name.setBounds(50, 50, 100, 30);
        body.add(l_p_name);

        t_p_name = new JTextField();
        t_p_name.setBounds(150, 50, 100, 30);
        body.add(t_p_name);

        JLabel l_curr = new JLabel("CURR :-");
        l_curr.setBounds(400, 50, 100, 30);
        body.add(l_curr);

        t_curr = new JTextField();
        t_curr.setBounds(500, 50, 100, 30);
        body.add(t_curr);
//line 2
        JLabel l_mob_no = new JLabel("Mob no.");
        l_mob_no.setBounds(50, 100, 100, 30);
        body.add(l_mob_no);

        t_mob_no = new JTextField();
        t_mob_no.setBounds(150, 100, 100, 30);
        body.add(t_mob_no);

        JLabel l_addr = new JLabel("Address :-");
        l_addr.setBounds(400, 100, 100, 30);
        body.add(l_addr);

        t_addr = new JTextField();
        t_addr.setBounds(500, 100, 100, 30);
        body.add(t_addr);

        t_start_date = new JTextField("01/01/2020");
        t_start_date.setBounds(250, 150, 100, 30);
        body.add(t_start_date);

        t_end_date = new JTextField(date);
        t_end_date.setBounds(400, 150, 100, 30);
        body.add(t_end_date);

        t_history = new JTextArea("");
        JScrollPane scrollBar = new JScrollPane(t_history);
        scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        t_history.setBounds(0, 0, 750, 450);
        t_history.setAutoscrolls(true);
        t_history.setVisible(false);
        t_history.setFont(new Font("monospaced", Font.BOLD, 14));
        body.add(t_history);

        JButton b_get_data = new JButton("FIND");
        b_get_data.setBounds(800, 140, 100, 50);
        b_get_data.addActionListener(f -> get_data());
        body.add(b_get_data);

        b_history = new JButton("History");
        b_history.setBounds(800, 240, 100, 50);
        b_history.addActionListener(f -> {
            try {
                get_history();
            } catch (NullPointerException n) {
                n.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame("Message"),"Values not found");
            }
        });
        body.add(b_history);

        JButton b_save = new JButton("Save");
        b_save.setBounds(950, 140, 100, 50);
        b_save.addActionListener(f -> save());
        body.add(b_save);

        b_print = new JButton("Print");
        b_print.setVisible(false);
        b_print.setBounds(950, 240, 100, 50);
        b_print.addActionListener(f -> print());
        body.add(b_print);

        b_delete = new JButton("Delete");
        b_delete.setBounds(800, 340, 100, 50);
        b_delete.addActionListener(f -> delete());
        body.add(b_delete);

        JButton b_new = new JButton("new");
        b_new.setBounds(800, 440, 100, 50);
        b_new.addActionListener(f -> _new());
        body.add(b_new);

        JButton back = new JButton("Back");
        back.setBounds(800, 540, 100, 50);
        back.addActionListener(f -> back());
        body.add(back);

        add(heading);
        sp = new JScrollPane(t_history);
        sp.setBounds(50, 200, 700, 450);
        body.add(sp);
        sp.setVisible(false);
        add(body);
        setSize(1080, 800);
        int i = 0;
        while (i <= party_sheet.getLastRowNum()) {
            if (!words.contains(party_sheet.getRow(i).getCell(1).getStringCellValue())) {
                words.add(party_sheet.getRow(i).getCell(1).getStringCellValue());
                row_numbers.add(i);
            }
            i++;
        }
        autoSuggestor = new AutoSuggestor(t_p_name, this, words, Color.white, Color.black, Color.black, (float) 1.0);
    }

    private int rdm() {
        Random random = new Random();
        return random.nextInt(255);
    }

    private void _new() {
        this.dispose();
        new ViewParty();
    }

    private void delete() {
        p_row_no = 1;
        String p_name = t_p_name.getText();
        int m = JOptionPane.showConfirmDialog(new JFrame("confirmation"), "Are you sure you want to delete ", "Welcome", JOptionPane.YES_NO_OPTION);
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
        if (m == 1) found = false;

        if (found) {
            row.getCell(1).setCellValue("delete");
        } else {
            JOptionPane.showMessageDialog(new JFrame("Message"), "NOT FOUND");
        }
        v_row_no = 1;
        row = vasuli_sheet.getRow(v_row_no);
        while (v_row_no <= vasuli_sheet.getLastRowNum() && found) {
            if (row.getCell(0).getStringCellValue().equals(p_name)) {
                row.getCell(0).setCellValue("delete");
            }
            v_row_no++;
            row = vasuli_sheet.getRow(v_row_no);
        }
        s_row_no = 1;
        row = sell_sheet.getRow(s_row_no);
        while (s_row_no <= sell_sheet.getLastRowNum() && found) {
            if (row.getCell(1).getStringCellValue().equals(p_name)) {
                row.getCell(1).setCellValue("delete");
            }
            s_row_no++;
            row = sell_sheet.getRow(s_row_no);
        }

        pur_row_no = 1;
        row = purchase_sheet.getRow(pur_row_no);
        while (pur_row_no <= purchase_sheet.getLastRowNum() && found) {
            if (row.getCell(1).getStringCellValue().equals(p_name)) {
                row.getCell(1).setCellValue("delete");
            }
            pur_row_no++;
            row = purchase_sheet.getRow(pur_row_no);
        }

        try {
            workbook.write(new FileOutputStream("sell.xls"));
            workbook1.write(new FileOutputStream("party.xls"));
            workbook2.write(new FileOutputStream("vasuli.xls"));
            workbook3.write(new FileOutputStream("purchase.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (found) {
            JOptionPane.showMessageDialog(new JFrame("Message"), "Deleted");
        } else {
            JOptionPane.showMessageDialog(new JFrame("Message"), "NOT Deleted");
        }
        this.dispose();
        new ViewParty();
    }

    private void get_history() throws NullPointerException {
        get_data();
        b_print.setVisible(true);
        sp.setVisible(true);
        String p_name = t_p_name.getText();
        name = p_name + "_ledger.txt";
        File file = new File(name);
        if (!file.exists()) {
            Boolean k = null;
            try {
                k = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int sell_row_no = 1, cell_no, vasuli_row_no = 1;
        HSSFRow sell_row = sell_sheet.getRow(sell_row_no), vasuli_row = vasuli_sheet.getRow(vasuli_row_no);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String end_date = t_end_date.getText(), start_date = t_start_date.getText();
        HSSFCell cell;
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        printWriter.println("                            DUKAAN KA NAAM                         ");
        printWriter.println("-------------------------------------------------------------------");
        int total = opening;
        printWriter.println("  Party Name :- " + p_name + "  Opening :- " + opening);
        int curr;
        String temp;
        StringBuilder line = new StringBuilder();
        boolean sell_before = false, sell_after = false, vasuli_before = false, vasuli_after = false;
        try {
            vasuli_before = !sdf.parse(vasuli_row.getCell(1).getStringCellValue()).after(sdf.parse(end_date));
            vasuli_after = !sdf.parse(vasuli_row.getCell(1).getStringCellValue()).before(sdf.parse(start_date));
            sell_before = !sdf.parse(sell_row.getCell(0).getStringCellValue()).after(sdf.parse(end_date));
            sell_after = !sdf.parse(sell_row.getCell(0).getStringCellValue()).before(sdf.parse(start_date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        while (sell_row_no <= sell_sheet.getLastRowNum()) {
            if (sell_row.getCell(1).getStringCellValue().equals(p_name)) {
                if (sell_before && sell_after) {
                    printWriter.println("___________________________________________________");
                    printWriter.println("date :- " + sell_row.getCell(0).getStringCellValue());
                    printWriter.println("       ITEM       Amount DEBIT  CREDIT TOTAL");
                }
                cell_no = 3;
                curr = 0;
                while (sell_row.getLastCellNum() >= cell_no) {
                    line.setLength(0);
                    cell = sell_row.getCell(cell_no - 1);
                    temp = cell.getStringCellValue();
                    line.append("  ").append(temp).append(space(15, temp.length()));
                    cell = sell_row.getCell(cell_no);
                    //temp = cell.getStringCellValue();
                    temp = String.valueOf((int) cell.getNumericCellValue());
                    line.append(temp).append(space(6, temp.length()));
                    curr = curr + Integer.parseInt(temp);
                    line.append("      |      |");
                    if (sell_after && sell_before) {
                        printWriter.println(line);
                    }
                    cell_no = cell_no + 2;
                }
                total = total + curr;
                if (sell_after && sell_before) {
                    printWriter.println("                               |" + curr + space(6, (curr + "").length()) + total);
                }
            }
            sell_row_no++;
            sell_row = sell_sheet.getRow(sell_row_no);
            try {
                if (sell_row != null) {
                    sell_before = !sdf.parse(sell_row.getCell(0).getStringCellValue()).after(sdf.parse(end_date));
                    sell_after = !sdf.parse(sell_row.getCell(0).getStringCellValue()).before(sdf.parse(start_date));
                } else {
                    sell_before = false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            while (true) {
                try {
                    if (sell_row_no <= sell_sheet.getLastRowNum()) {
                        if (!(sdf.parse(sell_row.getCell(0).getStringCellValue())).after(sdf.parse(vasuli_row.getCell(1).getStringCellValue()))) {
                            break;
                        }
                    } else {
                        while (vasuli_row_no <= vasuli_sheet.getLastRowNum()) {
                            if (vasuli_row.getCell(0).getStringCellValue().equals(p_name)) {
                                //temp = vasuli_row.getCell(2).getStringCellValue();
                                temp = String.valueOf((int) vasuli_row.getCell(2).getNumericCellValue());
                                total -= Integer.parseInt(temp);
                                if (vasuli_after && vasuli_before) {
                                    printWriter.println("          RETURN        |" + temp + space(6, temp.length()) + "      |" + total);
                                }
                            }
                            vasuli_row_no++;
                            vasuli_row = vasuli_sheet.getRow(vasuli_row_no);

                            try {
                                if (vasuli_row != null) {
                                    vasuli_before = !sdf.parse(vasuli_row.getCell(1).getStringCellValue()).after(sdf.parse(end_date));
                                    vasuli_after = !sdf.parse(vasuli_row.getCell(1).getStringCellValue()).before(sdf.parse(start_date));
                                } else {
                                    vasuli_before = false;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (vasuli_row_no <= vasuli_sheet.getLastRowNum()) {
                    if (vasuli_row.getCell(0).getStringCellValue().equals(p_name)) {
                        //temp = vasuli_row.getCell(2).getStringCellValue();
                        temp = String.valueOf((int) vasuli_row.getCell(2).getNumericCellValue());
                        total -= Integer.parseInt(temp);
                        if (vasuli_after && vasuli_before) {
                            printWriter.println("          RETURN        |" + temp + space(6, temp.length()) + "      |" + total);
                        }
                    }
                }
                vasuli_row_no++;
                vasuli_row = vasuli_sheet.getRow(vasuli_row_no);
                try {
                    if (sell_row != null) {
                        vasuli_before = !sdf.parse(vasuli_row.getCell(1).getStringCellValue()).after(sdf.parse(end_date));
                        vasuli_after = !sdf.parse(vasuli_row.getCell(1).getStringCellValue()).before(sdf.parse(start_date));
                    } else {
                        vasuli_before = false;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        printWriter.close();
        _show();
    }

    private void print() {
        try {
            t_history.setFont(new Font("monospaced", Font.BOLD, 8));
            t_history.print();
            t_history.setFont(new Font("monospaced", Font.BOLD, 12));
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }

    private void get_data() {

        p_row_no = 1;
        String p_name = t_p_name.getText();
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
            t_addr.setText(row.getCell(6).getStringCellValue());
            t_mob_no.setText(row.getCell(7).getStringCellValue());
            //curr_bal = Integer.parseInt(row.getCell(2).getRawValue());
            curr_bal = (int) row.getCell(2).getNumericCellValue();
            opening = curr_bal;
        } else {
            JOptionPane.showMessageDialog(new JFrame("Message"), "NOT FOUND");
        }
        v_row_no = 1;
        row = vasuli_sheet.getRow(v_row_no);
        while (v_row_no <= vasuli_sheet.getLastRowNum()) {
            if (row.getCell(0).getStringCellValue().equals(p_name) && found) {
                //curr_bal = curr_bal - Integer.parseInt(row.getCell(2).getRawValue());
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
                    //curr_bal = curr_bal + Integer.parseInt(cell.getRawValue());
                    curr_bal = curr_bal + ((int) cell.getNumericCellValue());
                    cell_no = cell_no + 2;
                }
            }
            s_row_no++;
            row = sell_sheet.getRow(s_row_no);
        }
        t_curr.setText(String.valueOf(curr_bal));
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

    private void _show() {
        String strLine;
        String st_ledg = "";
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
            t_history.setVisible(true);
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        t_history.setText(st_ledg);
        t_history.setVisible(true);
        //print_s lt = new print_s();
        //lt.printString(st_ledg);
        //lt.printString(t_ledg.getText());
    }

    public void back() {
        try {
            workbook1.close();
            workbook2.close();
            workbook3.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dispose();
        new Add();
    }

    private void save(){
        // ARe you sure you want to update ...
        String party_name = t_p_name.getText();
        int rowNum = row_numbers.get(words.indexOf(party_name));
        Row row =party_sheet.getRow(rowNum);
        row.createCell(6).setCellValue(t_addr.getText());
        row.createCell(7).setCellValue(t_mob_no.getText());
        try {
            workbook1.write(new FileOutputStream("party.xls"));
            JOptionPane.showMessageDialog(new JFrame("Message"), "SAVED SUCCESSFULLY");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame("Message"), "UNABLE TO SAVE");
        }
    }
}
