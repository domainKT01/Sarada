package com.solproe.service.excel.sheets;

import com.solproe.business.domain.SheetDataModel;
import com.solproe.service.excel.ExcelSheetTemplate;
import com.solproe.service.excel.graphics.ExcelGenerateGraphics;
import com.solproe.service.excel.graphics.LineChartGenerator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;

public class GenericSheetTemplate implements ExcelSheetTemplate {
    private ExcelGenerateGraphics excelGenerateGraphics;
    private SheetDataModel dataModel;
    private Sheet sheet;
    private Workbook workbook;

    @Override
    public void createSheet(Workbook workbook, SheetDataModel data) {
        try {
            this.dataModel = data;
            this.workbook = workbook;
            int rowStart = 33;
            CellStyle headerStyle = createHeaderStyle(workbook, (short) 12);
            this.sheet = workbook.createSheet(data.getSheetName());

            int secondRowFooter = 1;

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
                CellStyle styleLocation = createHeaderStyle(workbook, (short) 10);
                locationFields.getCell(0).setCellStyle(styleLocation);

                Row projectName = sheet.createRow(5);
                createCells(0, 8, projectName, workbook, "border");
                projectName.getCell(0).setCellValue("Nombre del Proyecto:");
                sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 1));
                sheet.addMergedRegion(new CellRangeAddress(5, 5, 2, 8));
                projectName.getCell(2).setCellValue(data.getConfigFileThreshold()[0].get("projectName").toString());

                Row locationRow = sheet.createRow(6);
                createCells(0, 8, locationRow, workbook, "border");
                locationRow.getCell(0).setCellValue("MUNICIPIO:");
                locationRow.getCell(2).setCellValue(data.getConfigFileThreshold()[0].get("cityName").getAsString().toUpperCase());
                locationRow.getCell(4).setCellValue("DEPARTAMENTO:");
                locationRow.getCell(5).setCellValue(data.getConfigFileThreshold()[0].get("stateName").getAsString().toUpperCase());
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
            // 🔹 SECTION: CREATE FOOTER
            // ==========================
            {
                if (data.getReportType().equalsIgnoreCase("forestFireDataModel")) {
                    rowStart = graphicFooter(rowStart, "forestFireThresholdOrange");
                } else if (data.getReportType().equalsIgnoreCase("massMovementDataModel")) {
                    rowStart = graphicFooter(rowStart, "precipitationRainPercentOrange");
                    secondRowFooter = graphicFooter(rowStart + 25, "precipitationThresholdOrange");
                    System.out.println("second: " + secondRowFooter);
                } else {
                    graphicFooter(rowStart, "windThresholdOrange");
                }
            }

            // ==========================
            // 🔹 SECTION: CREATE FIRST GRAPHIC
            // ==========================
            {
                Row graphicTitle = sheet.createRow(9);
                createCells(0, 8, graphicTitle, workbook, "");
                sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 8));
                switch (data.getReportType()) {
                    case "forestFireDataModel" :
                        graphicTitle.getCell(0).setCellValue("MONITOREO DE TEMPERATURA PARA 14 DÍAS DE PRONÓSTICO");
                        Row row = sheet.createRow(rowStart + 3);
                        createCells(0, 8, row, workbook, "");
                        sheet.addMergedRegion(new CellRangeAddress(rowStart + 3, rowStart + 3, 0, 8));
                        break;
                    case "massMovementDataModel" :
                        Row rowSecond = sheet.createRow(rowStart + 3);
                        createCells(0, 8, rowSecond, workbook, "");
                        sheet.addMergedRegion(new CellRangeAddress(rowStart + 3, rowStart + 3, 0, 8));
                        graphicTitle.getCell(0).setCellValue("MONITOREO DE PRECIPITACIÓN % PARA 14 DÍAS DE PRONÓSTICO");
                        rowSecond.getCell(0).setCellValue("MONITOREO DE PRECIPITACIÓN mm PARA 14 DÍAS DE PRONÓSTICO");
                        CellStyle style = createHeaderStyle(workbook, (short) 13);
                        rowSecond.getCell(0).setCellStyle(style);
                        break;
                    case "rainShowerDataModel" :
                        graphicTitle.getCell(0).setCellValue("MONITOREO DE VELOCIDAD DE VIENTO PARA 14 DÍAS DE PRONÓSTICO");
                        break;
                }
                this.dataModel.setStartRow(rowStart);
                CellStyle styleGraphic = createHeaderStyle(workbook, (short) 12);
                graphicTitle.getCell(0).setCellStyle(styleGraphic);
                XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
                this.excelGenerateGraphics = new LineChartGenerator();
                this.excelGenerateGraphics.createChart(sheet, drawing, workbook, data);
            }

            // =================================
            // 🔹 SECTION: CREATE SECOND GRAPHIC
            // =================================
            {
                switch (data.getReportType()) {
                    case "forestFireDataModel" :
                        Row rowHeader = sheet.createRow(rowStart + 3);
                        createCells(0, 8, rowHeader, workbook, "");
                        System.out.println("second graphic row: " + rowStart);
                        rowHeader.getCell(0).setCellValue("MONITOREO DE TEMPERATURA °C PARA UN AÑO");
                        CellStyle style = createHeaderStyle(workbook, (short) 13);
                        rowHeader.getCell(0).setCellStyle(style);
                        this.dataModel.setStartRow(rowStart + 5);
                        int[] parameters = {1,2,4,5};
                        this.dataModel.setParameterCol(parameters);
                        XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
                        this.excelGenerateGraphics = new LineChartGenerator();
                        this.excelGenerateGraphics.createSecondChart(sheet, drawing, workbook, dataModel);
                        GenerateSectionSheet generateSectionSheet = new GenerateSectionSheet(this,sheet, workbook, this.dataModel.getConfigFileThreshold()[1]);
                        int out = generateSectionSheet.createAlertSystemChartsTemperature(rowStart + 27, "ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS",
                                "ACTIVAR EN PREVENTIVO EL PMU Y ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS (EQUIPOS LISTOS PARA REACCIÓN INMEDIATA)",
                                dataModel.getConfigFileThreshold()[1].get("orangeThresholdTemperature").getAsDouble(),
                                dataModel.getConfigFileThreshold()[1].get("redThresholdTemperature").getAsDouble(),
                                "UMBRAL DE TEMPERATURA (°C)");
                        System.out.println(out + rowStart + " out size");
                        generateSectionSheet.generateThresholdMonths(rowStart + 41,
                                dataModel.getConfigFileThreshold()[1].get("orangeThresholdTemperature").getAsDouble(),
                                dataModel.getConfigFileThreshold()[1].get("redThresholdTemperature").getAsDouble(), "t");
                        break;
                    case "massMovementDataModel" :
                        Row row = sheet.createRow(rowStart + 3);
                        createCells(0, 8, row, workbook, "");
                        row.getCell(0).setCellValue("MONITOREO DE PPRECIPIYTACION PARA UN AÑO");
                        CellStyle headerStyle1 = createHeaderStyle(workbook, (short) 13);
                        row.getCell(0).setCellStyle(headerStyle1);
                        this.dataModel.setStartRow(secondRowFooter + 5);
                        int[] params = {1,3,6,7};
                        this.dataModel.setParameterCol(params);
                        XSSFDrawing drawing1 = (XSSFDrawing) sheet.createDrawingPatriarch();
                        this.excelGenerateGraphics = new LineChartGenerator();
                        this.excelGenerateGraphics.createSecondChart(sheet, drawing1, workbook, dataModel);
                        break;
                }

                //======================
                // SECTION: ALERT TABLES
                //======================
                {

                }
            }
        }
        catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public CellStyle createHeaderStyle(Workbook workbook, short fontSize) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints(fontSize);
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        return style;
    }

    public void createCells(int startCell, int endCell, Row row, Workbook workbook, String type) {
        for (int i = startCell; i <= endCell; i++) {
            Cell cell = row.createCell(i);
            CellStyle borderStyle = createBorderedStyle(workbook);
            if (type.equalsIgnoreCase("border")) {
                cell.setCellStyle(borderStyle);
            }
        }
    }

    public CellStyle createBorderedStyle(Workbook workbook) {
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

    private int graphicFooter(int rowStart, String param) {
        int count = 0;
        if (param.equalsIgnoreCase("forestFireThresholdOrange")) {
            for (int i = 0; i < this.dataModel.getArrTemperature().size(); i++) {
                if (this.dataModel.getArrTemperature().get(i) >= this.dataModel.getConfigFileThreshold()[0].get(param).getAsDouble()) {
                    Row row = sheet.createRow(rowStart + count);
                    createCells(0, 8, row, workbook, "border");
                    row.getCell(2).setCellValue(this.dataModel.getArrDate().get(i));
                    this.sheet.addMergedRegion(new CellRangeAddress(rowStart + count, rowStart + count, 2, 8));
                    count++;
                }
            }
        } else if (param.equalsIgnoreCase("precipitationRainPercentOrange")) {
            for (int i = 0; i < this.dataModel.getArrPrecipitationPercent().size(); i++) {
                if (this.dataModel.getArrPrecipitationPercent().get(i) >= this.dataModel.getConfigFileThreshold()[0].get(param).getAsDouble()) {
                    Row row = sheet.createRow(rowStart + count);
                    createCells(0, 8, row, workbook, "border");
                    row.getCell(2).setCellValue(this.dataModel.getArrDate().get(i));
                    this.sheet.addMergedRegion(new CellRangeAddress(rowStart + count, rowStart + count, 2, 8));
                    count++;
                }
            }
        }
        else if (param.equalsIgnoreCase("precipitationThresholdOrange")) {
            for (int i = 0; i < this.dataModel.getArrPrecipitationMm().size(); i++) {
                if (this.dataModel.getArrPrecipitationMm().get(i) >= this.dataModel.getConfigFileThreshold()[0].get(param).getAsDouble()) {
                    Row row = sheet.createRow(rowStart + count);
                    createCells(0, 8, row, workbook, "border");
                    row.getCell(2).setCellValue(this.dataModel.getArrDate().get(i));
                    this.sheet.addMergedRegion(new CellRangeAddress(rowStart + count, rowStart + count, 2, 8));
                    count++;
                }
            }
        }
        else if (param.equalsIgnoreCase("windThresholdOrange")) {
            for (int i = 0; i < this.dataModel.getArrWindSpeed().size(); i++) {
                if (this.dataModel.getArrTemperature().get(i) >= this.dataModel.getConfigFileThreshold()[0].get(param).getAsDouble()) {
                    Row row = sheet.createRow(rowStart + count);
                    createCells(0, 8, row, workbook, "border");
                    row.getCell(2).setCellValue(this.dataModel.getArrDate().get(i));
                    this.sheet.addMergedRegion(new CellRangeAddress(rowStart + count, rowStart + count, 2, 8));
                    count++;
                }
            }
        }
        Cell header;
        try {
            header = sheet.getRow(rowStart).getCell(0);
            sheet.addMergedRegion(new CellRangeAddress(rowStart, rowStart + count - 1, 0, 1));
        }
        catch (Exception e) {
            Row row = sheet.createRow(rowStart);
            createCells(0, 8, row, workbook, "border");
            header = row.getCell(0);
            sheet.addMergedRegion(new CellRangeAddress(rowStart, rowStart, 0, 1));
            sheet.addMergedRegion(new CellRangeAddress(rowStart, rowStart, 2, 8));
        }
        header.setCellValue("FECHA DE LECTURA ANÓMALA:");
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        header.setCellStyle(cellStyle);
        return rowStart + count;
    }

    public CellStyle createHeadersTables() {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 13);
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        // Establecer bordes
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }
}
