package com.solproe.business.repository;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface ExcelSheetGenerator {
    void generateSheet(XSSFWorkbook workbook);
}
