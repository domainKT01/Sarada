package com.solproe.service.excel.sheets;

import com.solproe.business.domain.SheetDataModel;
import com.solproe.service.excel.TypeReportSheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class GenerateSectionSheet {
    private final GenericSheetTemplate template;
    private final ExcelStyleFactory styleFactory;

    public GenerateSectionSheet(GenericSheetTemplate template, Workbook workbook) {
        this.template = template;
        this.styleFactory = new ExcelStyleFactory(template.getWorkbook());
    }

    public int createHeader(Sheet sheet, int rowIndex, SheetDataModel model) {
        Workbook workbook = template.getWorkbook();

        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 10000);
        sheet.setColumnWidth(7, 5000);
        sheet.setColumnWidth(8, 5000);

        String maxThreshold = "", projectName, projectId, state, city;
        projectName = model.getThresholdDailyJson().get("projectName").getAsString();
        projectId = model.getThresholdDailyJson().get("idProject").getAsString();
        city = model.getThresholdDailyJson().get("cityName").getAsString();
        state = model.getThresholdDailyJson().get("stateName").getAsString();

        CellStyle boldStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldStyle.setFont(boldFont);

        // Fila 1: Título centrado
        Row titleRow = sheet.createRow(rowIndex++);
        createCellsRow(sheet, 0, 8, titleRow);
        titleRow.getCell(0).setCellValue(model.getTitle());
        sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, 8));
        titleRow.getCell(0).setCellStyle(this.styleFactory.createHeaderTitleStyle((short) 14));

        // Fila 2: Nombre del evento (tipo de reporte)
        rowIndex += 1;
        Row typeRow = sheet.createRow(rowIndex++);
        createCellsRow(sheet, 0, 8, typeRow);
        typeRow.getCell(0).setCellValue("PARÁMETRO A MONITOREAR: ");
        typeRow.getCell(0).setCellStyle(this.styleFactory.createBorderedStyle(false, true));
        sheet.addMergedRegion(new CellRangeAddress(typeRow.getRowNum(), typeRow.getRowNum(), 3, 8));
        sheet.addMergedRegion(new CellRangeAddress(typeRow.getRowNum(), typeRow.getRowNum(), 0, 2));

        if (model.getReportType() == TypeReportSheet.forestFireDataModel) {
            typeRow.getCell(3).setCellValue("TEMPERATURA PROMEDIO DIARIA EN °C");
            maxThreshold = "TEMPERATURA MAXIMA MENSUAL PROMEDIO  (°C):";
        } else if (model.getReportType() == TypeReportSheet.massMovementDataModel) {
            typeRow.getCell(3).setCellValue("PRECIPITACIÓN PROMEDIO DIARIA EN mm y PROBABILIDAD DE PRECIPITACIÓN EN %");
            maxThreshold = "PRECIPITACIÓN MAXIMA MENSUAL PROMEDIO  (mm):";
        }
        else if (model.getReportType() == TypeReportSheet.rainShowerDataModel) {
            typeRow.getCell(3).setCellValue("VELOCIDAD DEL VIENTO PROMEDIO DIARIA EN Km/h");
            maxThreshold = "VELOCIDAD DEL VIENTO MAXIMA MENSUAL PROMEDIO  (Km/h):";
        }
        typeRow.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(false, true));

        // Fila 3: Proyecto / ID del proyecto
        rowIndex++;
        Row emptyRow = sheet.createRow(rowIndex++);
        createCellsRow(sheet, 0, 8, emptyRow);
        emptyRow.getCell(0).setCellValue("LOCALIZACIÓN ÁREA DE INFLUENCIA");
        emptyRow.getCell(0).setCellStyle(this.styleFactory.createHeaderTitleStyle((short) 11));
        sheet.addMergedRegion(new CellRangeAddress(emptyRow.getRowNum(), emptyRow.getRowNum(), 0, 8));
        Row projectRow = sheet.createRow(rowIndex++);
        createCellsRow(sheet, 0, 8, projectRow);
        //project name
        projectRow.getCell(0).setCellValue("Nombre del proyecto:");
        projectRow.getCell(0).setCellStyle(this.styleFactory.createBorderedStyle(true, true));
        sheet.addMergedRegion(new CellRangeAddress(projectRow.getRowNum(), projectRow.getRowNum(), 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(projectRow.getRowNum(), projectRow.getRowNum(), 2, 4));
        projectRow.createCell(2).setCellValue(projectName);
        projectRow.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(false, true));

        //project id
        projectRow.getCell(5).setCellValue("ID Proyecto:");
        sheet.addMergedRegion(new CellRangeAddress(projectRow.getRowNum(), projectRow.getRowNum(), 5, 6));
        projectRow.getCell(5).setCellStyle(this.styleFactory.createBorderedStyle(true, true));
        projectRow.getCell(7).setCellValue(projectId);
        sheet.addMergedRegion(new CellRangeAddress(projectRow.getRowNum(), projectRow.getRowNum(), 7, 8));
        projectRow.getCell(7).setCellStyle(this.styleFactory.createBorderedStyle(false, true));


        //town
        Row locationRow = sheet.createRow(rowIndex++);
        createCellsRow(sheet, 0, 8, locationRow);
        locationRow.getCell(0).setCellValue("Municipio:");
        locationRow.getCell(0).setCellStyle(this.styleFactory.createBorderedStyle(true, true));
        sheet.addMergedRegion(new CellRangeAddress(locationRow.getRowNum(), locationRow.getRowNum(), 0, 1));
        locationRow.getCell(2).setCellValue(city);
        sheet.addMergedRegion(new CellRangeAddress(locationRow.getRowNum(), locationRow.getRowNum(), 2,4));
        locationRow.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(false, true));

        //state
        locationRow.getCell(5).setCellValue("Departamento:");
        sheet.addMergedRegion(new CellRangeAddress(locationRow.getRowNum(), locationRow.getRowNum(), 5, 6));
        locationRow.getCell(5).setCellStyle(this.styleFactory.createBorderedStyle(true, true));
        locationRow.createCell(7).setCellValue(state);
        sheet.addMergedRegion(new CellRangeAddress(locationRow.getRowNum(), locationRow.getRowNum(), 7, 8));
        locationRow.getCell(7).setCellStyle(this.styleFactory.createBorderedStyle(false, true));

        //max weather
        Row maxTemp = sheet.createRow(rowIndex++);
        createCellsRow(sheet, 0, 8, maxTemp);
        maxTemp.getCell(0).setCellValue(maxThreshold);
        sheet.addMergedRegion(new CellRangeAddress(maxTemp.getRowNum(), maxTemp.getRowNum(), 0, 3));
        maxTemp.getCell(0).setCellStyle(this.styleFactory.createBorderedStyle(true, true));
        sheet.addMergedRegion(new CellRangeAddress(maxTemp.getRowNum(), maxTemp.getRowNum(), 4, 8));
        maxTemp.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(false, true));

        return rowIndex + 1; // deja una fila vacía después
    }

    public int createThresholdTable(Sheet sheet, int startRow, SheetDataModel model) {
        Workbook workbook = template.getWorkbook();

        // Estilo para encabezados
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        // Título de la tabla
        Row titleRow = sheet.createRow(startRow++);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Tabla de Umbrales");
        titleCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, 3));

        // Encabezados
        Row headerRow = sheet.createRow(startRow++);
        headerRow.createCell(0).setCellValue("Variable");
        headerRow.createCell(1).setCellValue("Umbral Naranja");
        headerRow.createCell(2).setCellValue("Umbral Rojo");

        for (int i = 0; i <= 2; i++) {
            headerRow.getCell(i).setCellStyle(headerStyle);
        }

        // Contenido
        String[][] variables = {
                {"Temperatura", String.valueOf(model.getThresholdDailyJson().get("forestFireThresholdOrange")),
                        String.valueOf(model.getThresholdDailyJson().get("forestFireThresholdRed"))},
                {"Precipitación", String.valueOf(model.getThresholdDailyJson().get("precipitationThresholdOrange")),
                        String.valueOf(model.getThresholdDailyJson().get("precipitationThresholdRed"))},
                {"Precipitación", String.valueOf(model.getThresholdDailyJson().get("precipitationRainPercentOrange")),
                        String.valueOf(model.getThresholdDailyJson().get("precipitationRainPercentRed"))},
                {"Ceraunicos (solo rojo)", "-", String.valueOf(model.getThresholdDailyJson().get("ceraunicosThresholdRed"))}
        };

        for (String[] var : variables) {
            Row row = sheet.createRow(startRow++);
            row.createCell(0).setCellValue(var[0]);
            row.createCell(1).setCellValue(var[1]);
            row.createCell(2).setCellValue(var[2]);
        }

        return startRow + 1; // deja una fila vacía después
    }

    public int createAlertSystem(Sheet sheet, int startRow, SheetDataModel model) {
        Workbook workbook = template.getWorkbook();

        // Título principal
        Row titleRow = sheet.createRow(startRow);
        createCellsRow(sheet, 0, 8, titleRow);
        titleRow.getCell(0).setCellValue("SISTEMA DE ALERTAS");
        titleRow.getCell(0).setCellStyle(this.styleFactory.createHeaderTitleStyle((short) 16));
        sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, 8));
        startRow += 1;
        if (model.getReportType() == TypeReportSheet.forestFireDataModel) {
            String[] alerts = {
                    "ACTIVAR EN PREVENTIVO EL PMU Y ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS (EQUIPOS LISTOS PARA REACCIÓN INMEDIATA)",
                    "ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS",
            };
            createAlertChart(sheet, startRow, model, alerts);
        }

        if (model.getReportType() == TypeReportSheet.massMovementDataModel) {
            String[] alerts = {
                    "ACTIVAR EN PREVENTIVO EL PMU Y ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS (EQUIPOS LISTOS PARA REACCIÓN INMEDIATA)",
                    "ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS \n",
                    "PREPARACIÓN PARA LA RESPUESTA, ALISTAMIENTO DE RECURSOS, SUMINISTROS Y SERVICIOS E IDENTIFICACIÓN DE DE LAS RUTAS DE INGRESO Y EGRESO."
            };
            createAlertChart(sheet, startRow, model, alerts);
        }
        return startRow + 1;
    }

    private void createAlertChart(Sheet sheet, int row, SheetDataModel model, String[] alertsLevel) {
        Row alertLevel = sheet.createRow(row);
        createCellsRow(sheet, 0, 8, alertLevel);
        sheet.addMergedRegion(new CellRangeAddress(alertLevel.getRowNum(), alertLevel.getRowNum(), 2, 3));
        sheet.addMergedRegion(new CellRangeAddress(alertLevel.getRowNum(), alertLevel.getRowNum(), 4, 6));
        alertLevel.getCell(2).setCellValue("NIVEL DE ALERTA");
        alertLevel.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(true, true));
        alertLevel.getCell(4).setCellValue("ACCIONES POR NIVEL DE ALERTA");
        alertLevel.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(true, true));

        String[] titles = new String[0];
        if (model.getReportType() == TypeReportSheet.forestFireDataModel) {
            titles = new String[]{
                    "ROJA",
                    "NARANJA",
            };
        }
        else if (model.getReportType() == TypeReportSheet.massMovementDataModel) {
            titles = new String[]{
                    "ROJA",
                    "NARANJA",
                    "AMARILLA",
            };
        }
        row += 1;

        for (int i = 0; i < titles.length; i++) {
            Row title = sheet.createRow(row + i);
            createCellsRow(sheet, 0, 8, title);
            title.setHeight((short) 1200);
            title.getCell(2).setCellValue(titles[i]);
            title.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(true, true, titles[i]));
            sheet.addMergedRegion(new CellRangeAddress(title.getRowNum(), title.getRowNum(), 2, 3));
            title.getCell(4).setCellValue(alertsLevel[i]);
            title.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(false, true));
            sheet.addMergedRegion(new CellRangeAddress(title.getRowNum(), title.getRowNum(), 4, 6));
        }
    }

    private int addMovimientoMasaAlertRows(Sheet sheet, int row, SheetDataModel model) {
        Row orange = sheet.createRow(row++);
        orange.createCell(0).setCellValue("Naranja");
        orange.createCell(1).setCellValue("Precipitación acumulada");
        orange.createCell(2).setCellValue(model.getThresholdDailyJson().get("precipitationThresholdOrange").getAsDouble());

        Row red = sheet.createRow(row++);
        red.createCell(0).setCellValue("Roja");
        red.createCell(1).setCellValue("Precipitación");
        red.createCell(2).setCellValue(model.getThresholdDailyJson().get("precipitationThresholdRed").getAsDouble());

        return row;
    }

    private int addVendavalAlertRows(Sheet sheet, int row, SheetDataModel model) {
        Row orange = sheet.createRow(row++);
        orange.createCell(0).setCellValue("Naranja");
        orange.createCell(1).setCellValue("Viento sostenido");
        orange.createCell(2).setCellValue(model.getThresholdDailyJson().get("windThresholdOrange").getAsDouble());

        Row red = sheet.createRow(row++);
        red.createCell(0).setCellValue("Roja");
        red.createCell(1).setCellValue("Viento sostenido");
        red.createCell(2).setCellValue(model.getThresholdDailyJson().get("windThresholdRed").getAsDouble());
        return row;
    }

    public void createCellsRow(Sheet sheet, int startCell, int endCell, Row row) {
        for (int i = startCell; i <= endCell; i++) {
            Cell cell = row.createCell(i);
        }
    }

    public int createFooterThresholdDaily(Sheet sheet, int startRow, SheetDataModel model, String... args) {
        AtomicInteger count = new AtomicInteger();
        count.set(0);

        IntStream.range(0, model.getArrTemperature().size())
                .filter(i ->
                        model.getReportType() == TypeReportSheet.forestFireDataModel &&
                                model.getArrTemperature().get(i) >= model.getThresholdDailyJson().get("forestFireThresholdOrange").getAsDouble())
                .forEach(i -> {
                    int data = (int) (double) model.getArrTemperature().get(i);
                    Row row = sheet.createRow(startRow + count.get());
                    createCellsRow(sheet, 0, 8, row);
                    row.getCell(2).setCellValue(model.getArrDate().get(i));
                    row.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(true, true));
                    sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 2, 8));
                    count.set(count.get() + 1);
                });

        IntStream.range(0, model.getArrPrecipitationPercent().size())
                .filter(i ->
                        model.getReportType() == TypeReportSheet.massMovementDataModel &&
                                model.getArrPrecipitationPercent().get(i) >= model.getThresholdDailyJson().get("precipitationRainPercentOrange").getAsDouble() &&
                                args[0].equalsIgnoreCase("%"))
                .forEach(i -> {
                    int data = (int) (double) model.getArrPrecipitationPercent().get(i);
                    Row row = sheet.createRow(startRow + count.get());
                    createCellsRow(sheet, 0, 8, row);
                    row.getCell(2).setCellValue(model.getArrDate().get(i));
                    row.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(true, true));
                    sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 2, 8));
                    count.set(count.get() + 1);
                });

        IntStream.range(0, model.getArrPrecipitationMm().size())
                .filter(i ->
                        model.getReportType() == TypeReportSheet.massMovementDataModel &&
                                model.getArrPrecipitationMm().get(i) >= model.getThresholdDailyJson().get("precipitationThresholdOrange").getAsDouble() &&
                                args[0].equalsIgnoreCase("mm"))
                .forEach(i -> {
                    int data = (int) (double) model.getArrPrecipitationPercent().get(i);
                    Row row = sheet.createRow(startRow + count.get());
                    createCellsRow(sheet, 0, 8, row);
                    row.getCell(2).setCellValue(model.getArrDate().get(i));
                    row.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(true, true));
                    sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 2, 8));
                    count.set(count.get() + 1);
                });

        IntStream.range(0, model.getArrWindSpeed().size())
                .filter(i ->
                        model.getReportType() == TypeReportSheet.rainShowerDataModel &&
                                model.getArrWindSpeed().get(i) >= model.getThresholdDailyJson().get("windThresholdOrange").getAsDouble())
                .forEach(i -> {
                    int data = (int) (double) model.getArrPrecipitationPercent().get(i);
                    Row row = sheet.createRow(startRow + count.get());
                    createCellsRow(sheet, 0, 8, row);
                    row.getCell(2).setCellValue(model.getArrDate().get(i));
                    row.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(true, true));
                    sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 2, 8));
                    count.set(count.get() + 1);
                });

        if (count.get() != 0) {
            sheet.addMergedRegion(new CellRangeAddress(startRow, startRow + count.get() - 1, 0, 1));
            sheet.getRow(startRow).getCell(0).setCellValue("FECHA DE LECTURA ANÓMALA:");
            sheet.getRow(startRow).getCell(0).setCellStyle(this.styleFactory.createBorderedStyle(true, true));
        }
        return startRow + count.get();
    }
}
