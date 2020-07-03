package com.company;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

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

public class Crate extends JFrame {
    JButton b_lend_crate, b_collect_crate;
    Color selected_tab_color = new Color(rdm(), rdm(), rdm());
    ArrayList<String> words = new ArrayList<>();
    AutoSuggestor autoSuggestor, autoSuggester2;
    HSSFWorkbook workbook1, workbook2;
    JPanel collect_panel, p_view;
    boolean collectCrate = true;
    int type = 0;
    JLabel l_type1, l_type2, l_type3;

    {
        try {
            workbook1 = new HSSFWorkbook(new FileInputStream("crate_party.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    HSSFSheet party_sheet = workbook1.getSheet("party");

    {
        try {
            workbook2 = new HSSFWorkbook(new FileInputStream("crate_transactions.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    JButton b_save, b_add, b_addParty;
    HSSFSheet transaction_sheet = workbook2.getSheet("transactions");
    JRadioButton rb_type1, rb_type2, rb_type3;
    JTextField t_party_name, t_amount;
    JLabel l_anType1, l_anType2, l_anType3, l_newParty_name;
    JTextField t_type1, t_type2, t_type3, t_newParty_name;

    public Crate() {
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

        JPanel tabs = new JPanel();
        tabs.setBounds(0, 110, 1080, 30);
        tabs.setBackground(new java.awt.Color(156, 156, 156));

        b_collect_crate = new JButton("Collect");
        b_collect_crate.setBackground(selected_tab_color);
        b_collect_crate.addActionListener(f -> collect_crate());
        tabs.add(b_collect_crate);

        b_lend_crate = new JButton("Lend");
        b_lend_crate.setBackground(new Color(156, 156, 156));
        b_lend_crate.addActionListener(f -> lend_crate());
        tabs.add(b_lend_crate);

        //Body Panel
        collect_panel = new JPanel();
        collect_panel.setLayout(null);
        collect_panel.setBounds(0, 140, 1080, 600);
        collect_panel.setBackground(selected_tab_color);

        JLabel l_party_name = new JLabel("Party Name :- ");
        l_party_name.setBounds(50, 50, 80, 30);
        collect_panel.add(l_party_name);

        t_party_name = new JTextField("");
        t_party_name.setBounds(130, 50, 70, 30);
        collect_panel.add(t_party_name);
        t_party_name.requestFocus();

        JButton b_viewStatus = new JButton("status");
        b_viewStatus.setBounds(250, 50, 70, 30);
        b_viewStatus.addActionListener(f -> view());
        collect_panel.add(b_viewStatus);

        ButtonGroup buttonGroup = new ButtonGroup();
        rb_type1 = new JRadioButton("Type 1");
        rb_type1.setBounds(50, 100, 100, 30);
        buttonGroup.add(rb_type1);
        collect_panel.add(rb_type1);

        rb_type2 = new JRadioButton("Type 2");
        rb_type2.setBounds(150, 100, 100, 30);
        buttonGroup.add(rb_type2);
        collect_panel.add(rb_type2);

        rb_type3 = new JRadioButton("Type 3");
        rb_type3.setBounds(250, 100, 100, 30);
        buttonGroup.add(rb_type3);
        collect_panel.add(rb_type3);

        t_amount = new JTextField("");
        t_amount.setBounds(50, 150, 100, 30);
        collect_panel.add(t_amount);

        b_save = new JButton("Collect");
        b_save.setBounds(50, 200, 100, 30);
        b_save.addActionListener(f -> save());
        collect_panel.add(b_save);

        JButton back = new JButton("Back");
        back.setBounds(800, 540, 100, 50);
        back.addActionListener(f -> back());
        collect_panel.add(back);

        b_addParty = new JButton("Add New");
        b_addParty.setBounds(800, 50, 70, 30);
        b_addParty.addActionListener(f -> show_addNew());
        collect_panel.add(b_addParty);

        l_newParty_name = new JLabel("Party Name");
        l_newParty_name.setBounds(800, 100, 70, 30);
        collect_panel.add(l_newParty_name);

        t_newParty_name = new JTextField("");
        t_newParty_name.setBounds(900, 100, 70, 30);
        collect_panel.add(t_newParty_name);


        l_anType1 = new JLabel("Type 1");
        l_anType1.setBounds(800, 150, 50, 30);
        l_anType1.setBackground(Color.getColor("#BBBBBB"));
        collect_panel.add(l_anType1);


        l_anType2 = new JLabel("Type 2");
        l_anType2.setBounds(850, 150, 50, 30);
        l_anType2.setBackground(Color.getColor("#BBBBBB"));
        collect_panel.add(l_anType2);

        l_anType3 = new JLabel("Type 3");
        l_anType3.setBounds(900, 150, 50, 30);
        l_anType2.setBackground(Color.getColor("#BBBBBB"));
        collect_panel.add(l_anType3);

        t_type1 = new JTextField("");
        t_type1.setBounds(800, 180, 50, 30);
        collect_panel.add(t_type1);

        t_type2 = new JTextField("");
        t_type2.setBounds(850, 180, 50, 30);
        collect_panel.add(t_type2);

        t_type3 = new JTextField("");
        t_type3.setBounds(900, 180, 50, 30);
        collect_panel.add(t_type3);

        b_add = new JButton("ADD");
        b_add.setBounds(850, 250, 70, 50);
        b_add.addActionListener(f -> add_newParty());
        collect_panel.add(b_add);

        l_anType1.setVisible(false);
        l_anType2.setVisible(false);
        l_anType3.setVisible(false);

        l_newParty_name.setVisible(false);

        t_type1.setVisible(false);
        t_type2.setVisible(false);
        t_type3.setVisible(false);

        t_newParty_name.setVisible(false);
        b_add.setVisible(false);
        add(tabs);
        add(heading);
        add(collect_panel);
        setSize(1080, 800);
        int i = 1;
        while (i <= party_sheet.getLastRowNum()) {
            if (!words.contains(party_sheet.getRow(i).getCell(0).getStringCellValue())) {
                words.add(party_sheet.getRow(i).getCell(0).getStringCellValue());
            }
            i++;
        }
        autoSuggestor = new AutoSuggestor(t_party_name, this, words, Color.white, Color.black, Color.black, (float) 1.0);
        autoSuggester2 = new AutoSuggestor(t_newParty_name, this, words, Color.white, Color.black, Color.black, (float) 1.0);
    }

    private void show_addNew() {
        l_anType1.setVisible(true);
        l_anType2.setVisible(true);
        l_anType3.setVisible(true);

        l_newParty_name.setVisible(true);

        t_type1.setVisible(true);
        t_type2.setVisible(true);
        t_type3.setVisible(true);

        t_newParty_name.setVisible(true);
        b_add.setVisible(true);
    }

    private void collect_crate() {
        b_collect_crate.setBackground(selected_tab_color);
        b_lend_crate.setBackground(new Color(156, 156, 156));
        collectCrate = true;
        b_save.setText("Collect");
    }

    private void add_newParty() {
        int type1, type2, type3;
        try {
            if (t_type1.getText().equals("")) type1 = 0;
            else type1 = Integer.parseInt(t_type1.getText());
            if (t_type2.getText().equals("")) type2 = 0;
            else type2 = Integer.parseInt(t_type2.getText());
            if (t_type3.getText().equals("")) type3 = 0;
            else type3 = Integer.parseInt(t_type3.getText());

            String p_name = t_newParty_name.getText();
            if (words.contains(p_name)) {
                JOptionPane.showMessageDialog(this, "Party name already exists");
                return;
            } else if (p_name.equals("")) {
                JOptionPane.showMessageDialog(this, "Party name cannot be empty");
                return;
            }
            Row row = party_sheet.createRow(party_sheet.getLastRowNum() + 1);
            row.createCell(0).setCellValue(p_name);
            row.createCell(1).setCellValue(type1);
            row.createCell(2).setCellValue(type2);
            row.createCell(3).setCellValue(type3);
            try {
                workbook1.write(new FileOutputStream("crate_party.xls"));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Cannot SAVE contact 8989806515");
                party_sheet.removeRow(row);
            }
            t_party_name.setText(p_name);
            words.add(p_name);
            autoSuggestor.addWordToSuggestions(p_name);
        } catch (NumberFormatException n) {
            JOptionPane.showMessageDialog(this, "Enter number of opening crates in type blocks...\n Not Saved");
        }
    }

    private void lend_crate() {
        b_lend_crate.setBackground(selected_tab_color);
        b_collect_crate.setBackground(new Color(156, 156, 156));
        collectCrate = false;
        b_save.setText("LEND");
    }

    public void back() {
        this.dispose();
        new Add();
    }

    private int rdm() {
        Random random = new Random();
        return random.nextInt(255);
    }

    private void save() {
        if (rb_type1.isSelected()) type = 1;
        if (rb_type2.isSelected()) type = 2;
        if (rb_type3.isSelected()) type = 3;
        String party_name = t_party_name.getText();
        if (!words.contains(party_name)) JOptionPane.showMessageDialog(this, "Party Name do not match");
        else if (t_amount.getText().equals("")) JOptionPane.showMessageDialog(this, "Please check Amount");
        else {
            Row row = transaction_sheet.createRow(transaction_sheet.getLastRowNum() + 1);
            try {
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date today = new Date();
                String date = formatter.format(today);
                row.createCell(0).setCellValue(date);
                row.createCell(1).setCellValue(t_party_name.getText());
                if (collectCrate) {
                    row.createCell(2).setCellValue(Integer.parseInt(t_amount.getText()));
                    row.createCell(3).setCellValue(0);
                } else {
                    row.createCell(2).setCellValue(0);
                    row.createCell(3).setCellValue(Integer.parseInt(t_amount.getText()));
                    JOptionPane.showMessageDialog(this, "" + "Saved Successfully");
                }
                if (type == 0) {
                    JOptionPane.showMessageDialog(this, "Type not selected");
                    transaction_sheet.removeRow(row);
                } else
                    row.createCell(4).setCellValue(type);
                try {
                    workbook2.write(new FileOutputStream("crate_transactions.xls"));
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Cannot SAVE contact 8989806515");
                    party_sheet.removeRow(row);
                }
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(this, "Cannot read Amount... \nPlease ensure there is no space or letter");
                party_sheet.removeRow(row);
            }
        }
    }

    private void view() {
        String p_name = t_party_name.getText();
        try {
            collect_panel.remove(p_view);
        } catch (NullPointerException n) {//do nothing
        }
        p_view = new JPanel(null);
        p_view.setBounds(25, 240, 520, 350);
        p_view.setBackground(new Color(rdm(), rdm(), rdm()));

        JLabel l_partyName = new JLabel(p_name);
        l_partyName.setBounds(5, 0, 100, 30);
        p_view.add(l_partyName);

        JButton b_change_color = new JButton("↻");
        b_change_color.setBounds(450, 5, 50, 30);
        b_change_color.addActionListener(f -> p_view.setBackground(new Color(rdm(), rdm(), rdm())));
        p_view.add(b_change_color);

        JButton b_history = new JButton("history");
        b_history.setBounds(450, 40, 50, 30);
        b_history.addActionListener(f -> show_transactions());
        p_view.add(b_history);

        l_type1 = new JLabel("type- 1 :- " + get_history(p_name, 1));
        l_type1.setBounds(110, 0, 100, 30);
        p_view.add(l_type1);

        l_type2 = new JLabel("type- 2 :- " + get_history(p_name, 2));
        l_type2.setBounds(220, 0, 100, 30);
        p_view.add(l_type2);

        l_type3 = new JLabel("type- 3 :- " + get_history(p_name, 3));
        l_type3.setBounds(330, 0, 100, 30);
        p_view.add(l_type3);

        collect_panel.add(p_view);

        if (getWidth() == 1080)
            setSize(1079, 800);
        else setSize(1080, 800);
    }

    @SuppressWarnings("unchecked")
    private <Any> Any getPartyValue(int row_num, String column) {
        if (column.equals("p_name"))
            return ((Any) party_sheet.getRow(row_num).getCell(0).getStringCellValue());

        if (column.equals("1"))
            return ((Any) (Integer) (int) party_sheet.getRow(row_num).getCell(1).getNumericCellValue());

        if (column.equals("2"))
            return ((Any) (Integer) (int) party_sheet.getRow(row_num).getCell(2).getNumericCellValue());

        if (column.equals("3"))
            return ((Any) (Integer) (int) party_sheet.getRow(row_num).getCell(3).getNumericCellValue());
        return null;
    }

    @SuppressWarnings("unchecked")
    private <Any> Any getTransactionValue(int row_num, String column) {
        if (column.equals("date"))
            return ((Any) transaction_sheet.getRow(row_num).getCell(0).getStringCellValue());

        if (column.equals("p_name"))
            return ((Any) transaction_sheet.getRow(row_num).getCell(1).getStringCellValue());

        if (column.equals("collect"))
            return ((Any) (Integer) (int) transaction_sheet.getRow(row_num).getCell(2).getNumericCellValue());

        if (column.equals("lend"))
            return ((Any) (Integer) (int) transaction_sheet.getRow(row_num).getCell(3).getNumericCellValue());

        if (column.equals("type"))
            return ((Any) (Integer) (int) transaction_sheet.getRow(row_num).getCell(4).getNumericCellValue());

        return null;
    }

    @SuppressWarnings("ConstantConditions")
    private int get_history(String p_name, int type) {
        int i = 1;
        while (i < party_sheet.getLastRowNum()) {
            if (p_name.equals(getPartyValue(i, "p_name"))) {
                break;
            } else {
                i++;
            }
        }
        int no = getPartyValue(i, "" + type);
        i = 1;
        while (i <= transaction_sheet.getLastRowNum()) {
            if (p_name.equals(getTransactionValue(i, "p_name")) && type == (int) getTransactionValue(i, "type")) {
                no = no + (int) getTransactionValue(i, "lend");
                no = no - (int) getTransactionValue(i, "collect");
            }
            i++;
        }
        return no;
    }

    int type_1, type_2, type_3, current_row;
    MyPanel p_history;

    private void show_transactions() {
        type_1 = Integer.parseInt(l_type1.getText().split(":- ")[1]);
        type_2 = Integer.parseInt(l_type2.getText().split(":- ")[1]);
        type_3 = Integer.parseInt(l_type3.getText().split(":- ")[1]);
        current_row = transaction_sheet.getLastRowNum();
        if (p_history != null)
            p_history.removeAll();
        else {
            p_history = new MyPanel();
            p_history.setLayout(null);
            p_history.setBounds(0, 0, 480, 30);
        }
        show_ten();
        JScrollPane scrollPane = new JScrollPane(p_history);
        scrollPane.setBackground(p_view.getBackground());
        p_view.add(scrollPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(10, 80, 500, 250);
        if (getWidth() == 1080)
            setSize(1079, 800);
        else setSize(1080, 800);
    }

    private void show_ten() {
        String p_name = t_party_name.getText();
        JLabel l_date,
                l_this_type1, l_net_type1,
                l_this_type2, l_net_type2,
                l_this_type3, l_net_type3;
        int height = 10;
        l_date = new JLabel("Date");
        l_date.setBounds(5, height, 100, 20);
        l_date.setHorizontalAlignment(SwingConstants.CENTER);
        p_history.add(l_date);
        l_this_type1 = new JLabel("This 1");
        l_this_type1.setBounds(105, height, 65, 20);
        p_history.add(l_this_type1);
        l_net_type1 = new JLabel("Net 1");
        l_net_type1.setBounds(170, height, 65, 20);
        p_history.add(l_net_type1);
        l_this_type2 = new JLabel("This 2");
        l_this_type2.setBounds(235, height, 65, 20);
        p_history.add(l_this_type2);
        l_net_type2 = new JLabel("Net 2");
        l_net_type2.setBounds(300, height, 65, 20);
        p_history.add(l_net_type2);
        l_this_type3 = new JLabel("this 3");
        l_this_type3.setBounds(365, height, 65, 20);
        p_history.add(l_this_type3);
        l_net_type3 = new JLabel("Net 3");
        l_net_type3.setBounds(430, height, 65, 20);
        p_history.add(l_net_type3);
        height += 20;

        l_this_type1.setHorizontalAlignment(SwingConstants.CENTER);
        l_this_type2.setHorizontalAlignment(SwingConstants.CENTER);
        l_this_type3.setHorizontalAlignment(SwingConstants.CENTER);
        l_net_type1.setHorizontalAlignment(SwingConstants.CENTER);
        l_net_type2.setHorizontalAlignment(SwingConstants.CENTER);
        l_net_type3.setHorizontalAlignment(SwingConstants.CENTER);
        while (current_row >= 0) {
            Row row = transaction_sheet.getRow(current_row);
            current_row--;
            if (row.getCell(1).getStringCellValue().equals(p_name)) {
                l_date = new JLabel(row.getCell(0).getStringCellValue());
                l_date.setBounds(5, height, 100, 20);
                l_date.setHorizontalAlignment(SwingConstants.CENTER);
                p_history.add(l_date);
                int this_type = (int) row.getCell(4).getNumericCellValue();
                int this_type1 = 0, this_type2 = 0, this_type3 = 0;
                if (this_type == 1) {
                    this_type1 = (int) row.getCell(3).getNumericCellValue();
                    if (this_type1 == 0) {
                        this_type1 = -(int) row.getCell(2).getNumericCellValue();
                    }
                }

                if (this_type == 2) {
                    this_type2 = (int) row.getCell(3).getNumericCellValue();
                    if (this_type2 == 0) {
                        this_type2 = -(int) row.getCell(2).getNumericCellValue();
                    }
                }

                if (this_type == 3) {
                    this_type3 = (int) row.getCell(3).getNumericCellValue();
                    if (this_type3 == 0) {
                        this_type3 = -(int) row.getCell(2).getNumericCellValue();
                    }
                }
                l_this_type1 = new JLabel(this_type1 + "");
                l_this_type1.setBounds(105, height, 65, 20);
                p_history.add(l_this_type1);
                l_net_type1 = new JLabel(type_1 + "");
                l_net_type1.setBounds(170, height, 65, 20);
                p_history.add(l_net_type1);
                l_this_type2 = new JLabel(this_type2 + "");
                l_this_type2.setBounds(235, height, 65, 20);
                p_history.add(l_this_type2);
                l_net_type2 = new JLabel(type_2 + "");
                l_net_type2.setBounds(300, height, 65, 20);
                p_history.add(l_net_type2);
                l_this_type3 = new JLabel(this_type3 + "");
                l_this_type3.setBounds(365, height, 65, 20);
                p_history.add(l_this_type3);
                l_net_type3 = new JLabel(type_3 + "");
                l_net_type3.setBounds(430, height, 65, 20);
                p_history.add(l_net_type3);
                type_1 = type_1 - this_type1;
                type_2 = type_2 - this_type2;
                type_3 = type_3 - this_type3;
                height += 20;

                l_this_type1.setHorizontalAlignment(SwingConstants.CENTER);
                l_this_type2.setHorizontalAlignment(SwingConstants.CENTER);
                l_this_type3.setHorizontalAlignment(SwingConstants.CENTER);
                l_net_type1.setHorizontalAlignment(SwingConstants.CENTER);
                l_net_type2.setHorizontalAlignment(SwingConstants.CENTER);
                l_net_type3.setHorizontalAlignment(SwingConstants.CENTER);
                //p_history.setSize(480, panel_height += 30);

                //   System.out.println(" height and index " + p_history.getHeight()+"    "+ current_row);
            }
        }
        p_history.height = height;
    }
}

class MyPanel extends JPanel {
    int height;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, height);
    }
}