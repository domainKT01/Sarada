package com.solproe.service.excel.graphics;

import com.solproe.business.domain.SheetDataModel;
import com.solproe.business.dto.CellRangeDTO;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;

import java.io.IOException;

public interface ExcelGenerateGraphics {

    void createChart(Sheet sheet, XSSFDrawing drawing, Workbook workbook, SheetDataModel sheetDataModel) throws IOException;
    void createSecondChart(Sheet sheet, XSSFDrawing drawing, Workbook workbook, SheetDataModel sheetDataModel) throws IOException;
}
