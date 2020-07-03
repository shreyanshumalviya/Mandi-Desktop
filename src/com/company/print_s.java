package com.company;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Random;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.PageRanges;
import javax.swing.*;

public class print_s extends JFrame implements  Printable {

    private PrintService[] printService;
    private String text;
    JButton[] buttons=new JButton[20];
    JLabel[] labels =new JLabel[20];

    public print_s() {
        //
    }

    /*public static void main(String[] args) {
        DirectPrint lt = new DirectPrint();
        lt.printString("If this text gets printed, it will have worked! ;D");
    }*/

    public void printString(String input) {
        this.printService = PrinterJob.lookupPrintServices();
        System.out.println(printService);
        this.text = input;
        create_layout();

        /*PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(new PageRanges(1, 1));
        aset.add(new Copies(1));

        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);

        try {
            printJob.setPrintService(printService[k-1]);
            printJob.print(aset);
        } catch (PrinterException err) {
            System.err.println(err);
        }*/
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(pf.getImageableX(), pf.getImageableY());
        g.drawString(String.valueOf(this.text), 14, 14);
        return PAGE_EXISTS;
    }

    private void create_layout(){
        setLayout(null);
        setVisible(true);
        setSize(1080, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(rdm(), rdm(), rdm()));

        //header
        JPanel heading = new JPanel();
        heading.setBounds(0, 0, 540, 50);
        JLabel head = new JLabel("Printing");
        head.setFont(new Font("Monospaced", Font.BOLD, 30));
        head.setSize(100, 50);
        heading.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));
        head.setForeground(new java.awt.Color(rdm(), rdm(), rdm()));
        heading.add(head);

        //Body Panel
        JPanel body = new JPanel();
        body.setLayout(null);
        body.setBounds(0, 110, 540, 300);
        body.setBackground(new java.awt.Color(rdm(), rdm(), rdm()));

        int i=0;
        for (PrintService service : printService){
            buttons[i]=new JButton(service.getName());
            buttons[i].setBounds(50,100+i*50,300,30);
            labels[i]=new JLabel(String.valueOf(i));
            int finalI = i;
            buttons[i].addActionListener(f->p(finalI));
            body.add(buttons[i]);
            i++;
        }
        JButton back = new JButton("Back");
        back.setBounds(440, 540, 100, 50);
        back.addActionListener(f -> back());
        body.add(back);

        add(heading);
        add(body);
    }

    private void p(int i){
        int k=i;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(new PageRanges(1, 1));
        aset.add(new Copies(1));

        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);

        try {
            printJob.setPrintService(printService[k]);
            printJob.print(aset);
            this.dispose();
        } catch (PrinterException err) {
            System.err.println(err);
            JOptionPane.showMessageDialog(new JFrame("Message"), "ERROR\nTry selecting another printer");
        }
    }


    private int rdm() {
        Random random = new Random();
        return random.nextInt(255);
    }

    public void back() {
        this.dispose();
        new Add();
    }
}
