package com.hqumath.demo.utils;

import android.content.Context;
import android.util.Log;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    // 写 Excel 文件
    public static void writeExcel(Context context) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("人员信息");

        // 表头
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("姓名");
        header.createCell(1).setCellValue("年龄");

        // 数据行1
        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("张三");
        row1.createCell(1).setCellValue(25);

        // 数据行2
        Row row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("李四");
        row2.createCell(1).setCellValue(30);

        // 输出到文件
        File file = new File(context.getExternalFilesDir(null), "test.xlsx");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
            Log.d("Excel", "写入成功: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 读 Excel 文件
    public static void readExcel(Context context) {
        File file = new File(context.getExternalFilesDir(null), "test.xlsx");
        if (!file.exists()) {
            Log.e("Excel", "文件不存在: " + file.getAbsolutePath());
            return;
        }

        try (FileInputStream fis = new FileInputStream(file);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                StringBuilder sb = new StringBuilder();
                for (Cell cell : row) {
                    sb.append(cell.toString()).append(" | ");
                }
                Log.d("Excel", sb.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
