package com.solproe.service.excel;

import com.solproe.business.domain.SheetDataModel;
import com.solproe.service.excel.graphics.ExcelGenerateGraphics;
import com.solproe.service.excel.graphics.LineChartGenerator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;

public class GenericSheetTemplate implements ExcelSheetTemplate {
    private ExcelGenerateGraphics excelGenerateGraphics;

    @Override
    public void createSheet(Workbook workbook, SheetDataModel data) {
        try {
            CellStyle headerStyle = createHeaderStyle(workbook, (short) 12);
            Sheet sheet = workbook.createSheet(data.getSheetName());

            //combinar celdas
            sheet.setColumnWidth(0, 3500);
            sheet.setColumnWidth(2, 3000);
            sheet.setColumnWidth(3, 4000);
            sheet.setColumnWidth(1, 4000);
            sheet.setColumnWidth(7, 4000);
            sheet.setColumnWidth(4, 9000);
            sheet.setColumnWidth(8, 6000);

            // ========================
            // 🔹 SECTION: CREATE TITLE
            // ========================
            {
                Row titleRow = sheet.createRow(0);
                createCells(0, 8, titleRow, workbook, "");

                titleRow.getCell(0).setCellStyle(headerStyle);
                titleRow.getCell(0).setCellValue(data.getTitle());
                sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));
            }

            // ============================
            // 🔹 SECTION: CREATE PARAMETER
            // ============================
            {
                Row parameterRow = sheet.createRow(2);
                createCells(0, 8, parameterRow, workbook, "border");
                sheet.addMergedRegion(new CellRangeAddress(2,2,0,2));
                sheet.addMergedRegion(new CellRangeAddress(2,2,3,8));
                parameterRow.getCell(0).setCellValue("PARÁMETRO A MONITOREAR: ");
                parameterRow.getCell(3).setCellValue(data.getParameter());
            }

            // =================================
            // 🔹 SECTION: LOCATION OF INFLUENCE
            // =================================
            {
                Row locationFields = sheet.createRow(4);
                createCells(0, 8, locationFields, workbook, "");
                sheet.addMergedRegion(new CellRangeAddress(4,4,0,8));
                locationFields.getCell(0).setCellValue("LOCALIZACIÓN ÁREA DE INFLUENCIA");
                CellStyle styleLocation = createHeaderStyle(workbook, (short) 10    );
                locationFields.getCell(0).setCellStyle(styleLocation);

                Row projectName = sheet.createRow(5);
                createCells(0, 8, projectName, workbook, "border");
                projectName.getCell(0).setCellValue("Nombre del Proyecto:");
                sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 1));
                sheet.addMergedRegion(new CellRangeAddress(5, 5, 2, 8));
                projectName.getCell(2).setCellValue(data.getConfigFileThreshold().get("projectName").toString());

                Row locationRow = sheet.createRow(6);
                createCells(0, 8, locationRow, workbook, "border");
                locationRow.getCell(0).setCellValue("MUNICIPIO:");
                locationRow.getCell(2).setCellValue(data.getConfigFileThreshold().get("cityName").getAsString().toUpperCase());
                locationRow.getCell(4).setCellValue("DEPARTAMENTO:");
                locationRow.getCell(5).setCellValue(data.getConfigFileThreshold().get("stateName").getAsString().toUpperCase());
                sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 1));
                sheet.addMergedRegion(new CellRangeAddress(6, 6, 5, 8));
                sheet.addMergedRegion(new CellRangeAddress(6, 6, 2, 3));

                Row valueParameter = sheet.createRow(7);
                createCells(0, 8, valueParameter, workbook, "border");
                switch (data.getReportType()) {
                    case "forestFireDataModel" :
                        valueParameter.getCell(0).setCellValue("TEMPERATURA MAXIMA MENSUAL PROMEDIO  (°C):");
                        break;
                    case "massMovementDataModel" :
                        valueParameter.getCell(0).setCellValue("PRECIPITACIÓN MAXIMA MENSUAL PROMEDIO  (mm):");
                        break;
                    case "rainShowerDataModel" :
                        valueParameter.getCell(0).setCellValue("VELOCIDAD DEL VIENTO MAXIMA MENSUAL PROMEDIO  (Km/h):");
                        break;
                }
                sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 3));
                sheet.addMergedRegion(new CellRangeAddress(7, 7, 4, 8));
            }

            // ==========================
            // 🔹 SECTION: CREATE GRAPHIC
            // ==========================
            {
                Row graphicTitle = sheet.createRow(9);
                createCells(0, 8, graphicTitle, workbook, "");
                sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 8));
                switch (data.getReportType()) {
                    case "forestFireDataModel" :
                        graphicTitle.getCell(0).setCellValue("MONITOREO DE TEMPERATURA PARA 14 DÍAS DE PRONÓSTICO");
                        break;
                    case "massMovementDataModel" :
                        graphicTitle.getCell(0).setCellValue("MONITOREO DE PRECIPITACIÓN PARA 14 DÍAS DE PRONÓSTICO");
                        break;
                    case "rainShowerDataModel" :
                        graphicTitle.getCell(0).setCellValue("MONITOREO DE VIENTO PARA 14 DÍAS DE PRONÓSTICO");
                        break;
                }
                CellStyle styleGraphic = createHeaderStyle(workbook, (short) 12);
                graphicTitle.getCell(0).setCellStyle(styleGraphic);
                XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
                XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 1, 11, 10, 22);
                this.excelGenerateGraphics = new LineChartGenerator();
                this.excelGenerateGraphics.createChart(sheet, anchor, drawing, data);
            }
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
