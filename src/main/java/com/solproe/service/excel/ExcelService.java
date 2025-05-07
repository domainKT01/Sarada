package com.solproe.service.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExcelService {
    private String path;

    public void setPath(String path) {
        this.path = path;
    }

    public Workbook createWorkbook() {
        Workbook workbook = new XSSFWorkbook();
        return workbook;
    }

    public void saveWorkbook(Workbook workbook, String name) {
        System.out.println("path: " + this.path);
        try (OutputStream outputStream = new FileOutputStream(this.path + name)) {
            workbook.write(outputStream);
            workbook.close();
            System.out.println("generated file");
        }
        catch (IOException e) {
            System.out.println("exc: " + e.getMessage());
        }
    }
}
