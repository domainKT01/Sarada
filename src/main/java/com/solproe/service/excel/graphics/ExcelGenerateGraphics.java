package com.solproe.service.excel.graphics;

import com.solproe.business.domain.SheetDataModel;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFDrawing;

import java.io.IOException;

public interface ExcelGenerateGraphics {

    int createChart(Sheet sheet, XSSFDrawing drawing, Workbook workbook, SheetDataModel sheetDataModel) throws IOException;
    int createSecondChart(Sheet sheet, XSSFDrawing drawing, Workbook workbook, SheetDataModel sheetDataModel) throws IOException;
}
