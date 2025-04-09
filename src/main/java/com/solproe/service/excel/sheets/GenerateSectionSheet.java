package com.solproe.service.excel.sheets;

import com.google.gson.JsonObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class GenerateSectionSheet {
    private GenericSheetTemplate genericSheetTemplate;
    private Sheet sheet;
    private Workbook workbook;
    private JsonObject jsonObjectMonthly;
    private JsonObject jsonObjectThreshold;


    public GenerateSectionSheet(GenericSheetTemplate genericSheetTemplate, Sheet sheet, Workbook workbook, JsonObject jsonObject) {
        this.genericSheetTemplate = genericSheetTemplate;
        this.sheet = sheet;
        this.workbook = workbook;
        this.jsonObjectMonthly = jsonObject;
    }

    public int createAlertSystemChartsTemperature(int startRow, String orangeAlert, String redAlert,
                                        double orangeThreshold, double redThreshold, String typeOfSheetAlert) {
        int numCol = 11;
        for (int i = 0; i < numCol; i++) {
            Row row = sheet.createRow(startRow + i);
            for (int j = 0; j < 9; j++) {
                row.createCell(j);
            }
        }
        sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 8));
        sheet.getRow(startRow).getCell(0).setCellValue("SISTEMA DE ALERTA");
        CellStyle headerStyle1 = this.genericSheetTemplate.createHeadersTables();
        CellStyle headerStyleOrange = this.genericSheetTemplate.createHeadersTables();
        CellStyle headerStyleRed = this.genericSheetTemplate.createHeadersTables();
        XSSFColor redColor = new XSSFColor(new java.awt.Color(255, 0, 0), null);
        XSSFColor orangeColor = new XSSFColor(new java.awt.Color(255, 165, 0), null);
        headerStyleOrange.setFillForegroundColor(orangeColor);
        headerStyleRed.setFillForegroundColor(redColor);
        XSSFColor whiteColor = new XSSFColor(new java.awt.Color(255, 255, 255), null);
        CellStyle cellStyle = this.genericSheetTemplate.createHeaderStyle(workbook, (short) 13);
        sheet.getRow(startRow).getCell(0).setCellStyle(cellStyle);
        sheet.addMergedRegion(new CellRangeAddress(startRow + 1, startRow + 1, 2, 3));
        sheet.getRow(startRow + 1).getCell(2).setCellValue("NIVEL DE ALERTA");
        CellStyle borderStyle = this.genericSheetTemplate.createBorderedStyle(workbook);
        sheet.getRow(startRow + 1).getCell(2).setCellStyle(borderStyle);

        sheet.getRow(startRow + 1).getCell(2).setCellStyle(headerStyle1);
        sheet.getRow(startRow + 1).getCell(4).setCellStyle(borderStyle);
        sheet.getRow(startRow + 1).getCell(4).setCellStyle(headerStyle1);
        sheet.addMergedRegion(new CellRangeAddress(startRow + 1, startRow + 1, 4, 6));
        sheet.getRow(startRow + 1).getCell(4).setCellValue("ACCIONES POR NIVEL DE ALERTA ");
        CellStyle centerStyle = workbook.createCellStyle();
        // Habilitar el ajuste de texto
        centerStyle.setWrapText(true);
        // Establecer la alineación vertical para ocupar todo el alto
        centerStyle.setVerticalAlignment(VerticalAlignment.TOP);
        centerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyleRed.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyleOrange.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        sheet.getRow(startRow + 2).setHeight((short) 550);
        sheet.addMergedRegion(new CellRangeAddress(startRow + 2, startRow + 2, 2, 3));
        sheet.addMergedRegion(new CellRangeAddress(startRow + 2, startRow + 2, 4, 6));
        sheet.addMergedRegion(new CellRangeAddress(startRow + 3, startRow + 3, 2, 3));
        sheet.addMergedRegion(new CellRangeAddress(startRow + 3, startRow + 3, 4, 6));
        sheet.getRow(startRow + 2).getCell(2).setCellValue("ROJA");
        sheet.getRow(startRow + 2).getCell(2).setCellStyle(headerStyleRed);
        sheet.getRow(startRow + 2).getCell(4).setCellValue(redAlert);
        sheet.getRow(startRow + 2).getCell(4).setCellStyle(centerStyle);
        sheet.getRow(startRow + 2).getCell(4).setCellStyle(borderStyle);
        sheet.getRow(startRow + 3).setHeight((short) 550);
        sheet.getRow(startRow + 3).getCell(2).setCellValue("NARANJA");
        sheet.getRow(startRow + 3).getCell(2).setCellStyle(headerStyleOrange);
        sheet.getRow(startRow + 3).getCell(4).setCellValue(orangeAlert);
        sheet.getRow(startRow + 3).getCell(4).setCellStyle(centerStyle);
        sheet.getRow(startRow + 3).getCell(4).setCellStyle(borderStyle);

        sheet.getRow(startRow + 5).getCell(0).setCellValue("UMBRAL DE TEMPERATURA Y NIVEL DE ALERTA MONITOREO DIARIO");
        sheet.addMergedRegion(new CellRangeAddress(startRow + 5, startRow + 5, 0, 8));
        sheet.getRow(startRow + 5).getCell(0).setCellStyle(cellStyle);
        sheet.addMergedRegion(new CellRangeAddress(startRow + 7, startRow + 7, 2, 3));
        sheet.addMergedRegion(new CellRangeAddress(startRow + 7, startRow + 7, 4, 6));
        sheet.getRow(startRow + 7).getCell(2).setCellValue("NIVEL DE ALERTA");
        sheet.getRow(startRow + 7).getCell(2).setCellStyle(headerStyle1);
        sheet.getRow(startRow + 7).getCell(4).setCellValue(typeOfSheetAlert);
        sheet.getRow(startRow + 7).getCell(4).setCellStyle(headerStyle1);
        sheet.getRow(startRow + 8).getCell(2).setCellValue("Roja");
        sheet.addMergedRegion(new CellRangeAddress(startRow + 8, startRow + 8, 2, 3));
        sheet.getRow(startRow + 8).getCell(2).setCellStyle(headerStyleRed);
        sheet.addMergedRegion(new CellRangeAddress(startRow + 8, startRow + 8, 4, 6));
        sheet.getRow(startRow + 8).getCell(4).setCellValue(redThreshold + " °C");
        sheet.getRow(startRow + 8).getCell(4).setCellStyle(headerStyle1);
        sheet.getRow(startRow + 9).getCell(2).setCellValue("Naranja");
        sheet.addMergedRegion(new CellRangeAddress(startRow + 9, startRow + 9, 2, 3));
        sheet.getRow(startRow + 9).getCell(2).setCellStyle(headerStyleOrange);
        sheet.addMergedRegion(new CellRangeAddress(startRow + 9, startRow + 9, 4, 6));
        sheet.getRow(startRow + 9).getCell(4).setCellValue(orangeThreshold + " °C");
        sheet.getRow(startRow + 9).getCell(4).setCellStyle(headerStyle1);

        return numCol;
    }


    public void createAlertSystemChartsPrecipitation(int startRow, String orangeAlert, String redAlert,
                                                   double orangeThreshold, double redThreshold, String typeOfSheetAlert) {
        int numCol = 11;
        for (int i = 0; i < numCol; i++) {
            Row row = sheet.createRow(startRow + i);
            for (int j = 0; j < 9; j++) {
                row.createCell(j);
            }
        }
        sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 8));
        sheet.getRow(startRow).getCell(0).setCellValue("SISTEMA DE ALERTA");
        CellStyle headerStyle1 = this.genericSheetTemplate.createHeadersTables();
        CellStyle headerStyleOrange = this.genericSheetTemplate.createHeadersTables();
        CellStyle headerStyleRed = this.genericSheetTemplate.createHeadersTables();
        XSSFColor redColor = new XSSFColor(new java.awt.Color(255, 0, 0), null);
        XSSFColor orangeColor = new XSSFColor(new java.awt.Color(255, 165, 0), null);
        headerStyleOrange.setFillForegroundColor(orangeColor);
        headerStyleRed.setFillForegroundColor(redColor);
        XSSFColor whiteColor = new XSSFColor(new java.awt.Color(255, 255, 255), null);
        CellStyle cellStyle = this.genericSheetTemplate.createHeaderStyle(workbook, (short) 13);
        sheet.getRow(startRow).getCell(0).setCellStyle(cellStyle);
        sheet.addMergedRegion(new CellRangeAddress(startRow + 1, startRow + 1, 2, 3));
        sheet.getRow(startRow + 1).getCell(2).setCellValue("NIVEL DE ALERTA");
        CellStyle borderStyle = this.genericSheetTemplate.createBorderedStyle(workbook);
        sheet.getRow(startRow + 1).getCell(2).setCellStyle(borderStyle);

        sheet.getRow(startRow + 1).getCell(2).setCellStyle(headerStyle1);
        sheet.getRow(startRow + 1).getCell(4).setCellStyle(borderStyle);
        sheet.getRow(startRow + 1).getCell(4).setCellStyle(headerStyle1);
        sheet.addMergedRegion(new CellRangeAddress(startRow + 1, startRow + 1, 4, 6));
        sheet.getRow(startRow + 1).getCell(4).setCellValue("ACCIONES POR NIVEL DE ALERTA ");
        CellStyle centerStyle = workbook.createCellStyle();
        // Habilitar el ajuste de texto
        centerStyle.setWrapText(true);
        // Establecer la alineación vertical para ocupar todo el alto
        centerStyle.setVerticalAlignment(VerticalAlignment.TOP);
        centerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyleRed.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyleOrange.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        sheet.getRow(startRow + 2).setHeight((short) 550);
        sheet.addMergedRegion(new CellRangeAddress(startRow + 2, startRow + 2, 2, 3));
        sheet.addMergedRegion(new CellRangeAddress(startRow + 2, startRow + 2, 4, 6));
        sheet.addMergedRegion(new CellRangeAddress(startRow + 3, startRow + 3, 2, 3));
        sheet.addMergedRegion(new CellRangeAddress(startRow + 3, startRow + 3, 4, 6));
        sheet.getRow(startRow + 2).getCell(2).setCellValue("ROJA");
        sheet.getRow(startRow + 2).getCell(2).setCellStyle(headerStyleRed);
        sheet.getRow(startRow + 2).getCell(4).setCellValue(redAlert);
        sheet.getRow(startRow + 2).getCell(4).setCellStyle(centerStyle);
        sheet.getRow(startRow + 2).getCell(4).setCellStyle(borderStyle);
        sheet.getRow(startRow + 3).setHeight((short) 550);
        sheet.getRow(startRow + 3).getCell(2).setCellValue("NARANJA");
        sheet.getRow(startRow + 3).getCell(2).setCellStyle(headerStyleOrange);
        sheet.getRow(startRow + 3).getCell(4).setCellValue(orangeAlert);
        sheet.getRow(startRow + 3).getCell(4).setCellStyle(centerStyle);
        sheet.getRow(startRow + 3).getCell(4).setCellStyle(borderStyle);

        sheet.getRow(startRow + 5).getCell(0).setCellValue("UMBRAL DE TEMPERATURA Y NIVEL DE ALERTA MONITOREO DIARIO");
        sheet.addMergedRegion(new CellRangeAddress(startRow + 5, startRow + 5, 0, 8));
        sheet.getRow(startRow + 5).getCell(0).setCellStyle(cellStyle);
        sheet.addMergedRegion(new CellRangeAddress(startRow + 7, startRow + 7, 2, 3));
        sheet.addMergedRegion(new CellRangeAddress(startRow + 7, startRow + 7, 4, 6));
        sheet.getRow(startRow + 7).getCell(2).setCellValue("NIVEL DE ALERTA");
        sheet.getRow(startRow + 7).getCell(2).setCellStyle(headerStyle1);
        sheet.getRow(startRow + 7).getCell(4).setCellValue(typeOfSheetAlert);
        System.out.println(typeOfSheetAlert);
        sheet.getRow(startRow + 7).getCell(4).setCellStyle(headerStyle1);
        sheet.getRow(startRow + 8).getCell(2).setCellValue("");
    }


    public int generateThresholdMonths(int startRow, double orangeThreshold, double redThreshold, String type) {
        String[] keys = {
                "orangeThresholdTemperature",
                "redThresholdTemperature",
                "orangeThresholdPrecipitation",
                "redThresholdPrecipitation",
                "januaryDataGrade",
                "januaryDataPercent",
                "februaryDataGrade",
                "februaryDataPercent",
                "marchDataGrade",
                "marchDataPercent",
                "aprilDataGrade",
                "aprilDataPercent",
                "mayDataGrade",
                "mayDataPercent",
                "juneDataGrade",
                "juneDataPercent",
                "julyDataGrade",
                "julyDataPercent",
                "augustDataGrade",
                "augustDataPercent",
                "septemberDataGrade",
                "septemberDataPercent",
                "octoberDataGrade",
                "octoberDataPercent",
                "novemberDataGrade",
                "novemberDataPercent",
                "decemberDataGrade",
                "decemberDataPercent"
        };
        Row row = this.sheet.createRow(startRow);
        this.genericSheetTemplate.createCells(0, 8, row, this.workbook, "");
        this.sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 8));
        Cell titleCell = row.createCell(0);
        titleCell.setCellValue("NIVEL DE ALERTA PARA LOS PROXIMOS 6 MESES");
        row.setHeight((short) 500);
        int count = 3;
        for (int i = 4; i < (keys.length - 4) / 2; i++) {
            if (i % 2 == 0 && type.equalsIgnoreCase("t")) {
                if (this.jsonObjectMonthly.get(keys[i]).getAsDouble() >= redThreshold) {
                    System.out.println(this.jsonObjectMonthly.get(keys[i]).getAsDouble());
                    Row row1 = this.sheet.createRow(startRow + count);
                    this.genericSheetTemplate.createCells(0, 8, row1, this.workbook, "border");
                    setStyleColorFill(row1.getCell(4), "red");
                    System.out.println(row1.getCell(2).getStringCellValue());
                    ++count;
                } else if (this.jsonObjectMonthly.get(keys[i]).getAsDouble() >= orangeThreshold) {
                    System.out.println(this.jsonObjectMonthly.get(keys[i]).getAsDouble());
                    Row row1 = this.sheet.createRow(startRow + count);
                    this.genericSheetTemplate.createCells(0, 8, row1, this.workbook, "border");
                    setStyleColorFill(row1.getCell(4), "orange");
                    ++count;
                }
            }
            else {
//                if (this.jsonObjectThreshold.get(keys[i]).getAsDouble() >= redThreshold) {
//                    Row row1 = this.sheet.createRow(startRow + count);
//                    this.genericSheetTemplate.createCells(0, 8, row1, this.workbook, "border");
//                    setStyleColorFill(row1.getCell(4), "red");
//                    ++count;
//                } else if (this.jsonObjectThreshold.get(keys[i]).getAsDouble() >= orangeThreshold) {
//                    Row row1 = this.sheet.createRow(startRow + count);
//                    this.genericSheetTemplate.createCells(0, 8, row1, this.workbook, "border");
//                    setStyleColorFill(row1.getCell(4), "orange");
//                    ++count;
//                }
            }
        }
        return 0;
    }


    public void setStyleColorFill(Cell cell, String color) {
        CellStyle cellStyle = this.workbook.createCellStyle();
        XSSFColor redColor = new XSSFColor(new java.awt.Color(255, 0, 0), null);
        XSSFColor orangeColor = new XSSFColor(new java.awt.Color(255, 165, 0), null);
        if (color.equalsIgnoreCase("red")) {
            cellStyle.setFillForegroundColor(redColor);
        } else if (color.equalsIgnoreCase("orange")) {
            cellStyle.setFillForegroundColor(orangeColor);
        }
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(cellStyle);
    }
}
