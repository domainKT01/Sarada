package com.solproe.service.excel;

import com.solproe.business.domain.SheetDataModel;
import com.solproe.service.excel.graphics.ExcelGenerateGraphics;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.IOException;

public class GenericSheetTemplate implements ExcelSheetTemplate {
    private ExcelGenerateGraphics excelGenerateGraphics;

    @Override
    public void createSheet(Workbook workbook, SheetDataModel data) {
        try {
            CellStyle headerStyle = createHeaderStyle(workbook, (short) 12);

            Sheet sheet = workbook.createSheet(data.getSheetName());
            //combinar celdas
            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(2, 3000);
            sheet.setColumnWidth(3, 3000);
            sheet.setColumnWidth(1, 4000);
            sheet.setColumnWidth(7, 4000);
            sheet.setColumnWidth(4, 9000);
            sheet.setColumnWidth(8, 6000);

            Row titleRow = sheet.createRow(0);
            createCells(0, 8, titleRow, workbook, "");

            titleRow.getCell(0).setCellStyle(headerStyle);
            titleRow.getCell(0).setCellValue(data.getTitle());
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));

            Row parameterRow = sheet.createRow(2);
            createCells(0, 8, parameterRow, workbook, "border");
            sheet.addMergedRegion(new CellRangeAddress(2,2,0,2));
            sheet.addMergedRegion(new CellRangeAddress(2,2,3,8));
            sheet.getRow(2).getCell(0).setCellValue("PARÁMETRO A MONITOREAR: ");
            sheet.getRow(2).getCell(3).setCellValue(data.getParameter());

            Row locationFields = sheet.createRow(4);
            createCells(0, 8, locationFields, workbook, "");
            sheet.addMergedRegion(new CellRangeAddress(4,4,0,8));
            sheet.getRow(4).getCell(0).setCellValue("LOCALIZACIÓN ÁREA DE INFLUENCIA");
            CellStyle styleLocation = createHeaderStyle(workbook, (short) 10    );
            sheet.getRow(4).getCell(0).setCellStyle(styleLocation);

            Row projectName = sheet.createRow(5);
            createCells(0, 8, projectName, workbook, "border");
            sheet.getRow(5).getCell(0).setCellValue("Nombre del Proyecto:");
            CellStyle projectNameStyle = createHeaderStyle(workbook, (short) 10);
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 1));
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 2, 8));
            sheet.getRow(5).getCell(2).setCellValue(data.getConfigFileThreshold().get("projectName").toString());
        }
        catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
        }

    }

    private CellStyle createHeaderStyle(Workbook workbook, short fontSize) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints(fontSize);
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        return style;
    }

    private void createCells(int startCell, int endCell, Row row, Workbook workbook, String type) {
        for (int i = startCell; i <= endCell; i++) {
            Cell cell = row.createCell(i);
            CellStyle borderStyle = createBorderedStyle(workbook);
            if (type.equalsIgnoreCase("border")) {
                cell.setCellStyle(borderStyle);
            }
        }
    }

    private CellStyle createBorderedStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();

        // Establecer bordes
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        // Opcional: Alinear el contenido al centro
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }
}
