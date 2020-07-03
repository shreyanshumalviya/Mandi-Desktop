package com.company;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File("purchase.xls");
        if (!file.exists()) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("purchase");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0);
            workbook.write(new FileOutputStream("purchase.xls"));
            workbook.close();
        }
        file = new File("sell.xls");
        if (!file.exists()) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("sell");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("20/12/2019");
            row.createCell(1).setCellValue("honey");
            workbook.write(new FileOutputStream("sell.xls"));
            workbook.close();
        }
        file = new File("party.xls");
        if (!file.exists()) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("party");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue(0);
            row.createCell(1).setCellValue("0");
            row.createCell(2).setCellValue(Integer.parseInt("0"));
            row.createCell(3).setCellValue(0);
            workbook.write(new FileOutputStream("party.xls"));
            workbook.close();
        }
        file = new File("crate_party.xls");
        if (!file.exists()) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("party");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("name");
            row.createCell(1).setCellValue(0);
            row.createCell(2).setCellValue(Integer.parseInt("898989898"));
            workbook.write(new FileOutputStream("crate_party.xls"));
            workbook.close();
        }
        file = new File("crate_transactions.xls");
        if (!file.exists()) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("transactions");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue(0);
            row.createCell(1).setCellValue("party name");
            row.createCell(2).setCellValue(Integer.parseInt("0"));
            row.createCell(3).setCellValue(0);
            workbook.write(new FileOutputStream("crate_transactions.xls"));
            workbook.close();
        }
        file = new File("vasuli.xls");
        if (!file.exists()) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("vasuli");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("0");
            row.createCell(1).setCellValue("0");
            row.createCell(2).setCellValue("0");
            row.createCell(3).setCellValue("0");
            workbook.write(new FileOutputStream("vasuli.xls"));
            workbook.close();
        }
        Login login = new Login();
        login.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        while (login.i != 1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        new Add();
    }
}

