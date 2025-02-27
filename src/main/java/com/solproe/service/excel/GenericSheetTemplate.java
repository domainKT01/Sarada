package com.solproe.service.excel;

import com.solproe.business.domain.SheetDataModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class GenericSheetTemplate implements ExcelSheetTemplate {


    @Override
    public void createSheet(Workbook workbook, SheetDataModel data) {
        Sheet sheet = workbook.createSheet(data.getSheetName());
        CellStyle titleStyle = createTitleStyle(workbook);
        CellStyle headerStyle = createHeaderStyle(workbook);

        // Crear título y combinar celdas
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(data.getTitle());
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
        sheet.setColumnWidth(4, 7500);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(7, 4000);
        sheet.setColumnWidth(8, 5000);

        String[][] templateData = {
                {data.getTitle()},
                {},
                {"PARÁMETRO A MONITOREAR:", "", "", data.getParameter()},
                {},
                {"LOCALIZACIÓN ÁREA DE INFLUENCIA"},
                {"Nombre del Proyecto:"},
                {"MUNICIPIO:", "", "", "DEPARTAMENTO:"},
                {"TEMPERATURA MAXIMA MENSUAL PROMEDIO  (°C):"}
        };

        for (int i = 0; i < templateData.length; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < templateData[i].length; j++) {
                if (templateData[i][j] != null && !templateData[i][j].isEmpty()) {
                    row.createCell(j).setCellValue(templateData[i][j]);
                }
            }
        }
    }

    private CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        style.setFont(font);
        return style;
    }
}
