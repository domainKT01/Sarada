package com.solproe.service.excel.sheets;

import com.solproe.business.domain.SheetDataModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class GenerateSectionSheet {
    private final GenericSheetTemplate template;
    private final Workbook workbook;
    private final ExcelStyleFactory styleFactory;

    public GenerateSectionSheet(GenericSheetTemplate template, Workbook workbook) {
        this.template = template;
        this.workbook = workbook;
        this.styleFactory = new ExcelStyleFactory(template.getWorkbook());
    }

    public int createHeader(Sheet sheet, int rowIndex, SheetDataModel model) {
        Workbook workbook = template.getWorkbook();

        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 10000);
        sheet.setColumnWidth(7, 5000);
        sheet.setColumnWidth(8, 5000);

        CellStyle boldStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldStyle.setFont(boldFont);

        // Fila 1: Título centrado
        Row titleRow = sheet.createRow(rowIndex++);
        createCellsRow(sheet, 0, 8, titleRow);
        titleRow.getCell(0).setCellValue(model.getTitle());
        sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, 8));
        titleRow.getCell(0).setCellStyle(this.styleFactory.createHeaderTitleStyle());

        // Fila 2: Nombre del evento (tipo de reporte)
        Row typeRow = sheet.createRow(rowIndex++);
        createCellsRow(sheet, 0, 8, typeRow);
        typeRow.getCell(0).setCellValue("PARÁMETRO A MONITOREAR: :");
        typeRow.getCell(3).setCellStyle(boldStyle);
        typeRow.createCell(1).setCellValue(model.getReportType());
        sheet.addMergedRegion(new CellRangeAddress(typeRow.getRowNum(), typeRow.getRowNum(), 3, 8));
        sheet.addMergedRegion(new CellRangeAddress(typeRow.getRowNum(), typeRow.getRowNum(), 0, 2));

        // Fila 3: Proyecto / ID del proyecto
        Row projectRow = sheet.createRow(rowIndex++);
        createCellsRow(sheet, 0, 8, projectRow);
        projectRow.getCell(0).setCellValue("Proyecto:");
        projectRow.getCell(0).setCellStyle(boldStyle);
        sheet.addMergedRegion(new CellRangeAddress(projectRow.getRowNum(), projectRow.getRowNum(), 0, 1));
        projectRow.createCell(2).setCellValue(model.getThresholdDailyJson().get("projectName").getAsString());

        Cell idLabel = projectRow.createCell(3);
        idLabel.setCellValue("ID Proyecto:");
        idLabel.setCellStyle(boldStyle);
        projectRow.createCell(4).setCellValue(model.getThresholdDailyJson().get("idProject").getAsString());

        // Fila 4: Municipio / Estado
        Row locationRow = sheet.createRow(rowIndex++);
        createCellsRow(sheet, 0, 8, locationRow);
        locationRow.getCell(0).setCellValue("Municipio:");
        locationRow.getCell(0).setCellStyle(boldStyle);
        sheet.addMergedRegion(new CellRangeAddress(locationRow.getRowNum(), locationRow.getRowNum(), 0, 1));
        locationRow.createCell(2).setCellValue(model.getThresholdDailyJson().get("cityName").getAsString());

        Cell stateLabel = locationRow.createCell(3);
        stateLabel.setCellValue("Estado:");
        stateLabel.setCellStyle(boldStyle);
        locationRow.createCell(4).setCellValue(model.getThresholdDailyJson().get("stateName").getAsString());

        //createBorder(sheet, 0, 8, 0, 8);

        return rowIndex + 1; // deja una fila vacía después
    }

    public int createParameterSection(Sheet sheet, int startRow, SheetDataModel data) {
        Workbook workbook = template.getWorkbook();

        // Crear estilo para etiquetas
        CellStyle labelStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        labelStyle.setFont(boldFont);

        // Datos como pares etiqueta → valor
        String[][] rows = {
                {"Nombre del proyecto", data.getThresholdDailyJson().get("projectName").getAsString()},
                {"ID del proyecto", data.getThresholdDailyJson().get("idProject").getAsString()},
                {"Ciudad", data.getThresholdDailyJson().get("cityName").getAsString()},
                {"Estado", data.getThresholdDailyJson().get("stateName").getAsString()},
        };

        for (String[] rowInfo : rows) {
            Row row = sheet.createRow(startRow++);
            Cell labelCell = row.createCell(0);
            labelCell.setCellValue(rowInfo[0]);
            labelCell.setCellStyle(labelStyle);

            Cell valueCell = row.createCell(1);
            valueCell.setCellValue(rowInfo[1]);
        }

        return startRow + 1; // deja una fila vacía después
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
        String type = model.getReportType().toUpperCase();

        // Estilo de título
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        // Estilo para encabezados
        CellStyle headerStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        headerStyle.setFont(boldFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        // Título principal
        Row titleRow = sheet.createRow(startRow++);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Sistema de Alertas");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, 3));

        // Encabezados
        Row headerRow = sheet.createRow(startRow++);
        headerRow.createCell(0).setCellValue("Nivel de Alerta");
        headerRow.createCell(1).setCellValue("Variable");
        headerRow.createCell(2).setCellValue("Valor");
        headerRow.createCell(3).setCellValue("Descripción");

        for (int i = 0; i <= 3; i++) {
            headerRow.getCell(i).setCellStyle(headerStyle);
        }

        if (type.contains("INCENDIO")) {
            startRow = addIncendioAlertRows(sheet, startRow, model);
        } else if (type.contains("MASA")) {
            startRow = addMovimientoMasaAlertRows(sheet, startRow, model);
        } else if (type.contains("VENDAVAL")) {
            startRow = addVendavalAlertRows(sheet, startRow, model);
        }

        return startRow + 1;
    }

    private int addIncendioAlertRows(Sheet sheet, int row, SheetDataModel model) {
        Row orange = sheet.createRow(row++);
        orange.createCell(0).setCellValue("Naranja");
        orange.createCell(1).setCellValue("Temperatura");
        orange.createCell(2).setCellValue(model.getThresholdDailyJson().get("forestFireThresholdOrange").getAsDouble());
        orange.createCell(3).setCellValue("Condiciones cálidas. Monitoreo recomendado.");

        Row red = sheet.createRow(row++);
        red.createCell(0).setCellValue("Roja");
        red.createCell(1).setCellValue("Temperatura / Ceraunicos");
        red.createCell(2).setCellValue(model.getThresholdDailyJson().get("forestFireThresholdRed").getAsDouble());
        red.createCell(3).setCellValue("Condiciones críticas para incendios forestales.");

        return row;
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

    public void createBorder(Sheet sheet, int startRow, int endRow, int startCell, int endCell) {
        CellStyle cellStyle = this.workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.DOTTED);
        cellStyle.setBorderRight(BorderStyle.DOTTED);
        cellStyle.setBorderBottom(BorderStyle.DOTTED);
        cellStyle.setBorderLeft(BorderStyle.DOTTED);
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCell; j <= endCell; j++) {
                sheet.getRow(i).getCell(j).setCellStyle(cellStyle);
            }
        }
    }
}
