package com.company;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class YearEnding {
    int p_row_no,opening,curr_bal,v_row_no,s_row_no;
    HSSFWorkbook party_workbook = new HSSFWorkbook(new FileInputStream("party.xls"));
    HSSFWorkbook purchase_workbook = new HSSFWorkbook(new FileInputStream("purchase.xls"));
    HSSFWorkbook vasuli_workbook = new HSSFWorkbook(new FileInputStream("vasuli.xls"));
    HSSFWorkbook sell_workbook = new HSSFWorkbook(new FileInputStream("sell.xls"));
    HSSFSheet sell_sheet,vasuli_sheet,party_sheet,purchase_sheet;

    HSSFWorkbook new_party_workbook = new HSSFWorkbook();
    HSSFWorkbook new_purchase_workbook = new HSSFWorkbook();
    HSSFWorkbook new_vasuli_workbook = new HSSFWorkbook();
    HSSFWorkbook new_sell_workbook = new HSSFWorkbook();
    String p_name;
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date today = new Date();
    String date = formatter.format(today);
    public YearEnding() throws IOException ,ParseException{
        date = JOptionPane.showInputDialog(date);
        sell_sheet=sell_workbook.getSheet("sell");
        party_sheet=party_workbook.getSheet("party");
        vasuli_sheet=vasuli_workbook.getSheet("vasuli");
        purchase_sheet=purchase_workbook.getSheet("purchase");
        new_party_workbook.createSheet("party");
        party_sheet.getRow(10).getCell(2).setCellValue(100);
        int i=0,k=0;
        File file=new File("purchase.xls");
        while (file.exists()){
            i++;
            file=new File("purchase_"+i+".xls");
        }
        party_workbook.write(new FileOutputStream("party_"+i+".xls"));
        HSSFRow row;
        while(k<=party_sheet.getLastRowNum()){
            row=party_sheet.getRow(k);
            p_name=row.getCell(1).getStringCellValue();
            p_row_no=k;
            row.getCell(2).setCellValue(set_data());
            k++;
        }

        HSSFSheet sheet = new_sell_workbook.createSheet("sell");
        row = sheet.createRow(0);
        row.createCell(0).setCellValue("20/12/2019");
        row.createCell(1).setCellValue("honey");

        sheet = new_purchase_workbook.createSheet("purchase");
        row = sheet.createRow(0);
        row.createCell(0).setCellValue(2);

        sheet = new_vasuli_workbook.createSheet("vasuli");
        row = sheet.createRow(0);
        row.createCell(0).setCellValue("0");
        row.createCell(1).setCellValue("0");
        row.createCell(2).setCellValue("0");
        row.createCell(3).setCellValue("0");

        place_data();

        new_sell_workbook.write(new FileOutputStream("sell.xls"));
        new_vasuli_workbook.write(new FileOutputStream("vasuli.xls"));
        new_purchase_workbook.write(new FileOutputStream("purchase.xls"));
        party_workbook.write(new FileOutputStream("party.xls"));

        sell_workbook.write(new FileOutputStream("sell_"+i+".xls"));
        vasuli_workbook.write(new FileOutputStream("vasuli_"+i+".xls"));
        purchase_workbook.write(new FileOutputStream("purchase_"+i+".xls"));
        //party_workbook.write(new FileOutputStream("party_"+i+".xls"));

        JOptionPane.showMessageDialog(new JFrame("Message"),"Year Ended");
        party_workbook.close();
        new_party_workbook.close();
        purchase_workbook.close();
        new_purchase_workbook.close();
        sell_workbook.close();
        new_sell_workbook.close();
        vasuli_workbook.close();
        new_vasuli_workbook.close();
    }

    public int set_data () throws ParseException{

        HSSFRow row = party_sheet.getRow(p_row_no);
        /*while (p_row_no <= party_sheet.getLastRowNum()) {
            if (row.getCell(1).getStringCellValue().equals(p_name)) {
                found = true;
                break;
            }
            p_row_no++;
            row = party_sheet.getRow(p_row_no);
        }*/
        curr_bal = (int) row.getCell(2).getNumericCellValue();
        opening = curr_bal;
        v_row_no = 1;
        row = vasuli_sheet.getRow(v_row_no);
        while (formatter.parse(date).after(
                formatter.parse(row.getCell(1).getStringCellValue()))&&
                v_row_no < vasuli_sheet.getLastRowNum()) {
            System.out.println("vrow no"+v_row_no);
            if (row.getCell(0).getStringCellValue().equals(p_name)) {
                //curr_bal = curr_bal - Integer.parseInt(row.getCell(2).getStringCellValue());
                curr_bal = curr_bal - ((int)row.getCell(2).getNumericCellValue());
            }
            v_row_no++;
            row = vasuli_sheet.getRow(v_row_no);
        }

        s_row_no = 1;
        HSSFCell cell;
        int cell_no;
        row = sell_sheet.getRow(s_row_no);
        while (formatter.parse(date).after(formatter.parse(row.getCell(0).getStringCellValue()))&&s_row_no < sell_sheet.getLastRowNum()) {
            cell_no = 3;
            if (row.getCell(1).getStringCellValue().equals(p_name)) {
                while (row.getLastCellNum() >= cell_no) {
                    cell = row.getCell(cell_no);
                    //curr_bal = curr_bal + Integer.parseInt(cell.getStringCellValue());
                    curr_bal = curr_bal + ((int)cell.getNumericCellValue());
                    cell_no = cell_no + 2;
                }
            }
            s_row_no++;
            row = sell_sheet.getRow(s_row_no);
        }
        return curr_bal;
    }

   /* public void place_data2 () throws ParseException {
        CellCopyPolicy cellCopyPolicy = new CellCopyPolicy();
        int pur_row_number=1;
        HSSFSheet newSheet=new_purchase_workbook.getSheet("purchase");
        HSSFRow row=purchase_sheet.getRow(pur_row_number),new_row=newSheet.createRow(1);
       // new_row.setRowStyle(row.getRowStyle());
        while(formatter.parse(date).after(formatter.parse(row.getCell(2).getStringCellValue()))&&pur_row_number<=purchase_sheet.getLastRowNum()){
            row=purchase_sheet.getRow(pur_row_number);
            pur_row_number++;
        }
        while(pur_row_number<=purchase_sheet.getLastRowNum()){
            //new_row.setRowStyle(row.getRowStyle());
            new_row.setRowStyle(null);
            new_row.copyRowFrom(row,cellCopyPolicy);
            pur_row_number++;
            row=purchase_sheet.getRow(pur_row_number);
            new_row=newSheet.createRow(newSheet.getLastRowNum()+1);
        }
        pur_row_number=1;
        row=sell_sheet.getRow(pur_row_number);
        newSheet = new_sell_workbook.getSheet("sell");
        new_row=newSheet.createRow(1);
        while(!formatter.parse(date).after(formatter.parse(row.getCell(0).getStringCellValue()))){
            pur_row_number++;
            row=sell_sheet.getRow(pur_row_number);
        }
        while(pur_row_number<=sell_sheet.getLastRowNum()){
            new_row.setRowStyle(row.getRowStyle());
            new_row.copyRowFrom(row,cellCopyPolicy);
            pur_row_number++;
            row=sell_sheet.getRow(pur_row_number);
            new_row=newSheet.createRow(newSheet.getLastRowNum()+1);
        }
        pur_row_number=1;
        row=vasuli_sheet.getRow(pur_row_number);
        newSheet = new_vasuli_workbook.getSheet("vasuli");
        new_row=newSheet.createRow(1);
        while(!formatter.parse(date).after(formatter.parse(row.getCell(1).getStringCellValue()))){
            pur_row_number++;
            row=vasuli_sheet.getRow(pur_row_number);
        }
        while(pur_row_number<=sell_sheet.getLastRowNum()){
            new_row.setRowStyle(row.getRowStyle());
            new_row.copyRowFrom(row,cellCopyPolicy);
            pur_row_number++;
            row=vasuli_sheet.getRow(pur_row_number);
            new_row=newSheet.createRow(newSheet.getLastRowNum()+1);
        }
    }*/

    public void place_data () throws ParseException{
        int row_no=1,index,i;
        HSSFSheet newSheet = new_purchase_workbook.getSheet("purchase");
        HSSFRow row = purchase_sheet.getRow(1),newRow;
        while(formatter.parse(date).after(formatter.parse(row.getCell(2).getStringCellValue()))&&row_no<=purchase_sheet.getLastRowNum()){
            row=purchase_sheet.getRow(row_no);
            row_no++;
        }
        row_no--;
        while(row_no<=purchase_sheet.getLastRowNum()){
            newRow = newSheet.createRow(newSheet.getLastRowNum()+1);
            row=purchase_sheet.getRow(row_no);
            newRow.createCell(0).setCellValue(newSheet.getLastRowNum()+100);
            newRow.createCell(1).setCellValue(row.getCell(1).getStringCellValue());
            newRow.createCell(2).setCellValue(row.getCell(2).getStringCellValue());
            newRow.createCell(3).setCellValue((int)row.getCell(3).getNumericCellValue());
            newRow.createCell(4).setCellValue((int)row.getCell(4).getNumericCellValue());
            newRow.createCell(5).setCellValue((int)row.getCell(5).getNumericCellValue());
            newRow.createCell(6).setCellValue((int)row.getCell(6).getNumericCellValue());
            newRow.createCell(7).setCellValue((int)row.getCell(7).getNumericCellValue());
            newRow.createCell(8).setCellValue((int)row.getCell(8).getNumericCellValue());
            newRow.createCell(9).setCellValue((int)row.getCell(9).getNumericCellValue());
            newRow.createCell(10).setCellValue((int)row.getCell(10).getNumericCellValue());
            newRow.createCell(11).setCellValue((int)row.getCell(11).getNumericCellValue());
            newRow.createCell(12).setCellValue((int)row.getCell(12).getNumericCellValue());
            newRow.createCell(13).setCellValue((int)row.getCell(13).getNumericCellValue());
            newRow.createCell(14).setCellValue((int)row.getCell(14).getNumericCellValue());
            i=0;
            index = (row.getLastCellNum() - 9) / 5 - 1;
            while(i<index){
                newRow.createCell(15+5*i).setCellValue(row.getCell(15 + 5 * i).getStringCellValue());
                newRow.createCell(16+5*i).setCellValue((int)row.getCell(16 + 5 * i).getNumericCellValue());
                newRow.createCell(17+5*i).setCellValue((int)row.getCell(17 + 5 * i).getNumericCellValue());
                newRow.createCell(18+5*i).setCellValue((int)row.getCell(18 + 5 * i).getNumericCellValue());
                newRow.createCell(19+5*i).setCellValue((int)row.getCell(19 + 5 * i).getNumericCellValue());
                i++;
            }
            row_no++;
        }

        row_no=1;
        newSheet = new_sell_workbook.getSheet("sell");
        row = sell_sheet.getRow(1);
        while(formatter.parse(date).after(formatter.parse(row.getCell(0).getStringCellValue()))&&row_no<sell_sheet.getLastRowNum()){
            row_no++;
            row=sell_sheet.getRow(row_no);
        }
        row_no--;
        while(row_no<=sell_sheet.getLastRowNum()){
            newRow = newSheet.createRow(newSheet.getLastRowNum()+1);
            row=sell_sheet.getRow(row_no);
            newRow.createCell(0).setCellValue(row.getCell(0).getStringCellValue());
            newRow.createCell(1).setCellValue(row.getCell(1).getStringCellValue());
            i=0;
            index = (row.getLastCellNum() - 1) / 2 ;
            while(i<index){
                newRow.createCell(2+2*i).setCellValue(row.getCell(2 + 2 * i).getStringCellValue());
                //System.out.println(row.getCell(3 + 2 * i).getStringCellValue());
                //newRow.createCell(3+2*i).setCellValue(Integer.parseInt(row.getCell(3 + 2 * i).getStringCellValue()));
                newRow.createCell(3+2*i).setCellValue((int)row.getCell(3 + 2 * i).getNumericCellValue());
                i++;
            }
            row_no++;
        }
    
        row_no=1;
        newSheet = new_vasuli_workbook.getSheet("vasuli");
        row = vasuli_sheet.getRow(1);
        while(formatter.parse(date).after(formatter.parse(row.getCell(1).getStringCellValue()))&&row_no<vasuli_sheet.getLastRowNum()){
            row_no++;
            row=vasuli_sheet.getRow(row_no);
        }
        //row_no--;
        while(row_no<vasuli_sheet.getLastRowNum()){
            newRow = newSheet.createRow(newSheet.getLastRowNum()+1);
            row=vasuli_sheet.getRow(row_no);
            newRow.createCell(0).setCellValue(row.getCell(0).getStringCellValue());
            newRow.createCell(1).setCellValue(row.getCell(1).getStringCellValue());
            //newRow.createCell(2).setCellValue(Integer.parseInt(row.getCell(2).getStringCellValue()));
            newRow.createCell(2).setCellValue((int)row.getCell(2).getNumericCellValue());
            row_no++;
        }
    }
}

