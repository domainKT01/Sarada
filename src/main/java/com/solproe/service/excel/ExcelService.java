package com.solproe.service.excel;

import com.solproe.util.logging.ErrorLogger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.nio.file.Path;

public class ExcelService {
    private Path path;

    public void setPath(Path path) {
        this.path = path;
    }

    public Workbook createWorkbook() {
        Workbook workbook = new XSSFWorkbook();
        return workbook;
    }

    public void saveWorkbook(Workbook workbook, String name) {
        System.out.println("path: " + this.path);
        try {
            File fileWriter = new File(this.path.toUri().getPath() + name);
            OutputStream outputStream = new FileOutputStream(fileWriter);
            workbook.write(outputStream);
            workbook.close();
            System.out.println("generated file");
        }
        catch (IOException e) {
            System.out.println("exc: " + e.getMessage());
            ErrorLogger.log(e);
        }
    }
}
