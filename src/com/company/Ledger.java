package com.company;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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

public class Ledger extends JFrame {

    String st_ledg = "";
    HSSFWorkbook workbook;
    {
        try {
            workbook = new HSSFWorkbook(new FileInputStream("sell.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    HSSFSheet sell_sheet = Objects.requireNonNull(workbook).getSheet("sell");

    HSSFWorkbook workbook1;

    {
        try {
            workbook1 = new HSSFWorkbook(new FileInputStream("party.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    HSSFSheet party_sheet = Objects.requireNonNull(workbook1).getSheet("party");

    HSSFWorkbook workbook2;
    JButton b_print;

    {
        try {
            workbook2 = new HSSFWorkbook(new FileInputStream("vasuli.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    HSSFSheet vasuli_sheet = Objects.requireNonNull(workbook2).getSheet("vasuli");

    String name = "";
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat formatterFile = new SimpleDateFormat("dd-MM-yyyy");
    Date today = new Date();
    String date = formatter.format(today);
    JTextField t_date;
    JTextArea t_ledg;

    public Ledger() {
        setLayout(null);
        setVisible(true);
        setSize(1080, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(rdm(), rdm(), rdm()));

        //header
        JPanel heading = new JPanel();
        heading.setBounds(0, 0, 1080, 100);
        JLabel head = new JLabel("Ledger");
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

        t_date = new JTextField(date);
        t_date.setBounds(800, 300, 100, 30);
        body.add(t_date);

        t_ledg = new JTextArea(".");
        JScrollPane scrollBar = new JScrollPane(t_ledg);
        scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        t_ledg.setBounds(0, 0, 750, 600);
        t_ledg.setAutoscrolls(true);
        t_ledg.setVisible(false);
        t_ledg.setFont(new Font("monospaced", Font.BOLD, 12));
        body.add(t_ledg);

        JButton create = new JButton("Create");
        create.setBounds(800, 340, 100, 50);
        create.addActionListener(f -> save());
        body.add(create);

        b_print = new JButton("Print");
        b_print.setBounds(800, 440, 100, 50);
        b_print.addActionListener(f -> print());
        b_print.setVisible(false);
        body.add(b_print);

        JButton back = new JButton("Back");
        back.setBounds(800, 540, 100, 50);
        back.addActionListener(f -> back());
        body.add(back);


        add(heading);
        add(body);
    }

    private int rdm() {
        Random random = new Random();
        return random.nextInt(255);
    }

    private void save() {
        date = t_date.getText();
        ArrayList<String> names_done = new ArrayList<>();
        String _date;
        try {
            _date = formatterFile.format(formatter.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            _date = "";
        }
        name = _date + "_ledger.txt";
        try {
            File file = new File(name);
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println("                            DUKAAN KA NAAM                         date :- " + date);
            printWriter.println("----------------------------------------------------------------------------------------------------");
            printWriter.println("         Name         |  Back  |                                                   | Today | TOTAL |");
            int row = sell_sheet.getLastRowNum();
            HSSFRow _row = sell_sheet.getRow(row);
            String p_name, temp;
            StringBuilder line = new StringBuilder();
            int cell_no;
            while (row > 0 && !_row.getCell(0).getStringCellValue().equals(date)) {
                row--;
                _row = sell_sheet.getRow(row);
            }
            int today;
            int back;
            int temp_n;
            StringBuilder tem;
            while (_row.getCell(0).getStringCellValue().equals(date) && row > 0) {
                //_row = sell_sheet.getRow(row);
                tem = new StringBuilder();
                line.setLength(0);
                today = 0;
                p_name = _row.getCell(1).getStringCellValue();
                names_done.add(p_name);
                line.append(p_name).append(space(22, p_name.length()));
                back = calculate_back(p_name, date);
                temp = back + "";
                line.append(temp).append(space(8, temp.length()));
                cell_no = 3;
                while (cell_no <= _row.getLastCellNum()) {
                    try {
                        if (_row.getCell(cell_no).getStringCellValue().equals("")) {
                            cell_no += 2;
                        } else {
                            //temp_n = Integer.parseInt(_row.getCell(cell_no).getStringCellValue());
                            temp_n = ((int) _row.getCell(cell_no).getNumericCellValue());
                            today = today + temp_n;
                            tem.append(temp_n).append(",");
                            cell_no = cell_no + 2;
                        }
                    } catch (IllegalStateException i){
                        temp_n = ((int) _row.getCell(cell_no).getNumericCellValue());
                        today = today + temp_n;
                        tem.append(temp_n).append(",");
                        cell_no = cell_no + 2;
                    }
                }
                line.append(tem).append(space(51, tem.length()));
                line.append(today).append(space(7, (today + "").length()));
                line.append(today + back).append(space(7, (back + today + "").length()));
                if (!p_name.equals("delete") && !p_name.equals("")) {
                    printWriter.println(line);
                }
                row--;
                _row = sell_sheet.getRow(row);
            }
//-----------ADDITION STARTS
            int i = 1;
            while (i <= party_sheet.getLastRowNum()) {
                _row = party_sheet.getRow(i);
                p_name = _row.getCell(1).getStringCellValue();
                if (!names_done.contains(p_name) && (_row.getCell(4).getNumericCellValue() == 0 ||
                        _row.getCell(4).getNumericCellValue() == 3 ||
                        _row.getCell(4).getNumericCellValue() == 1)) {
                    back = calculate_back(p_name, date);
                    temp = back + "";
                    if (!p_name.equals("delete") && !p_name.equals("") && !(back == 0)) {
                        printWriter.println(p_name + space(22, p_name.length()) + temp + space(8, temp.length()) + "                                                   |   0   |" + temp + space(7, temp.length()));
                    }

                }
                i++;
            }
//------------ADDITION ENDS
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame("Message"), "UNABLE TO SAVE **\n" +
                    " err - exception while finding file...ledger");

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame("Message"), "UNABLE TO SAVE **\n" +
                    " err - exception while writing file...ledger");
        }
        _show();
    }

    public void back() {
        this.dispose();
        new Add();
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

    private int calculate_back(String p_name, String date) {
        int back, opening_bal = 0, i = 0;
        HSSFRow row;
        row = party_sheet.getRow(i);
        while (i <= party_sheet.getLastRowNum()) {
            if (p_name.equals(row.getCell(1).getStringCellValue())) {
                opening_bal = (int) row.getCell(2).getNumericCellValue();
                break;
            }
            i++;
            row = party_sheet.getRow(i);
        }
        back = opening_bal;
        i = 0;
        int last_row = sell_sheet.getLastRowNum();
        row = sell_sheet.getRow(i);
        int cell_no;
        while (i <= last_row && !row.getCell(0).getStringCellValue().equals(date)) {
            if (row.getCell(1).getStringCellValue().equals(p_name)) {
                cell_no = 3;
                while (cell_no <= row.getLastCellNum()) {
                    //back = back + Integer.parseInt(row.getCell(cell_no).getStringCellValue());
                    back = back + ((int) row.getCell(cell_no).getNumericCellValue());
                    cell_no = cell_no + 2;
                }
            }
            i++;
            row = sell_sheet.getRow(i);
        }

        i = 0;
        row = vasuli_sheet.getRow(i);

        while (/*!row.getCell(1).getStringCellValue().equals(date) &&*/ i <= vasuli_sheet.getLastRowNum()) {
            System.out.println("value of i is "+i+"last row num is"+vasuli_sheet.getLastRowNum());
            if (row.getCell(0).getStringCellValue().equals(p_name)) {
                back = back - ((int) row.getCell(2).getNumericCellValue());
            }
            i++;
            row = vasuli_sheet.getRow(i);
        }
        return back;
    }

    private void _show() {
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
            b_print.setVisible(true);
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        t_ledg.setText(st_ledg);
        t_ledg.setVisible(true);
        //print_s lt = new print_s();
        //lt.printString(st_ledg);
        //lt.printString(t_ledg.getText());
    }

    private void print() {
        try {
            t_ledg.setFont(new Font("monospaced", Font.BOLD, 8));
            t_ledg.print();
            t_ledg.setFont(new Font("monospaced", Font.BOLD, 12));
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }
}

