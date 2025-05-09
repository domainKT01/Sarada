package com.solproe.service.excel.sheets;

import com.solproe.business.domain.SheetDataModel;
import com.solproe.service.excel.ExcelSheetTemplate;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SupportDatasheetTemplate implements ExcelSheetTemplate {
    private List<SheetDataModel> dataModel;
    private Sheet sheet;
    private Workbook workbook;

    public SupportDatasheetTemplate(List<SheetDataModel> datasets) {
        this.dataModel = datasets;
    }


    @Override
    public void generate(Workbook workbook, SheetDataModel sheetDataModel) {
        try {
            this.sheet = workbook.createSheet("support data");
            this.workbook = workbook;

            //create tables
            String[] parameters = {"forestFireThresholdOrange", "forestFireThresholdRed", "Temp"};
            createValuesTable(2, 1, parameters, this.dataModel.getFirst());
            String[] parameters1 = {"precipitationRainPercentOrange", "precipitationRainPercentRed", "Prec (%)"};
            String[] parameters2 = {"precipitationThresholdOrange", "precipitationThresholdRed", "Prec (mm)"};
            createValuesTable(19, 1, parameters1, this.dataModel.get(1));
            createValuesTable(36, 1, parameters2, this.dataModel.get(1));
            String[] parametersWind = {"windThresholdOrange", "windThresholdRed", "Viento"};
            createValuesTable(53, 1, parametersWind, this.dataModel.get(2));
            String[] parametersMonths = {"orangeThresholdTemperature", "redThresholdTemperature", "Temperatura", "Precipitación"};
            createMonthValues(69, 1, parametersMonths, this.dataModel.get(2));
            String[] parametersCeraunic = {"ceraunicosThresholdRed", "ceraunic"};
            createCodeChart(79, 1, parametersCeraunic, this.dataModel.get(1));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("support data exc: " + e.getMessage());
        }
    }

    private void createValuesTable(int rowTable, int columnTable, String[] parameters, SheetDataModel sheetDataModel) {
        try {
            Row rowHeader = this.sheet.createRow(rowTable - 1);
            Cell cellDateHeader = rowHeader.createCell(columnTable);
            cellDateHeader.setCellValue("Fecha");
            cellDateHeader.setCellStyle(setStyle("header"));
            Cell cellValueHeader = rowHeader.createCell(columnTable + 1);
            cellValueHeader.setCellValue(parameters[2]);
            cellValueHeader.setCellStyle(setStyle("header"));
            Cell cellThresholdOrangeHeader = rowHeader.createCell(columnTable + 2);
            cellThresholdOrangeHeader.setCellValue("Alerta Naranja");
            cellThresholdOrangeHeader.setCellStyle(setStyle("header"));
            Cell cellThresholdRedHeader = rowHeader.createCell(columnTable + 3);
            cellThresholdRedHeader.setCellValue("Alerta Roja");
            cellThresholdRedHeader.setCellStyle(setStyle("header"));
            for (int i = 0; i < sheetDataModel.getArrDate().size(); i++) {
                Row row = sheet.createRow(rowTable);
                Cell cellDate = row.createCell(columnTable);
                Cell cellValue = row.createCell(columnTable + 1);
                Cell cellThresholdOrange = row.createCell(columnTable + 2);
                Cell cellThresholdRed = row.createCell(columnTable + 3);
                cellDate.setCellValue(sheetDataModel.getArrDate().get(i));
                cellDate.setCellStyle(setStyle("date"));
                if (parameters[2].equalsIgnoreCase("temp")) {
                    cellValue.setCellValue(sheetDataModel.getArrTemperature().get(i));
                    if (sheetDataModel.getArrTemperature().get(i) >= sheetDataModel.getConfigFileThreshold()[0]
                            .get(parameters[1]).getAsDouble()) {
                        cellValue.setCellStyle(setStyle("red"));
                    }else if (sheetDataModel.getArrTemperature().get(i) >= sheetDataModel.getConfigFileThreshold()[0]
                            .get(parameters[0]).getAsDouble()) {
                        cellValue.setCellStyle(setStyle("orange"));
                    }
                    else {
                        cellValue.setCellStyle(setStyle(""));
                    }
                } else if (parameters[2].equalsIgnoreCase("Prec (%)")) {
                    cellValue.setCellValue(sheetDataModel.getArrPrecipitationPercent().get(i));
                    if (sheetDataModel.getArrPrecipitationPercent().get(i) >= sheetDataModel.getConfigFileThreshold()[0]
                            .get(parameters[1]).getAsDouble()) {
                        cellValue.setCellStyle(setStyle("red"));
                    }else if (sheetDataModel.getArrPrecipitationPercent().get(i) >= sheetDataModel.getConfigFileThreshold()[0]
                            .get(parameters[0]).getAsDouble()) {
                        cellValue.setCellStyle(setStyle("orange"));
                    }
                    else {
                        cellValue.setCellStyle(setStyle(""));
                    }
                } else if (parameters[2].equalsIgnoreCase("Prec (mm)")) {
                    cellValue.setCellValue(sheetDataModel.getArrPrecipitationMm().get(i));
                    if (sheetDataModel.getArrPrecipitationMm().get(i) >= sheetDataModel.getConfigFileThreshold()[0]
                            .get(parameters[1]).getAsDouble()) {
                        cellValue.setCellStyle(setStyle("red"));
                    }else if (sheetDataModel.getArrPrecipitationMm().get(i) >= sheetDataModel.getConfigFileThreshold()[0]
                            .get(parameters[0]).getAsDouble()) {
                        cellValue.setCellStyle(setStyle("orange"));
                    }
                    else {
                        cellValue.setCellStyle(setStyle(""));
                    }
                } else if (parameters[2].equalsIgnoreCase("Viento")) {
                    cellValue.setCellValue(sheetDataModel.getArrWindSpeed().get(i));
                    if (sheetDataModel.getArrWindSpeed().get(i) >= sheetDataModel.getConfigFileThreshold()[0]
                            .get(parameters[1]).getAsDouble()) {
                        cellValue.setCellStyle(setStyle("red"));
                    }else if (sheetDataModel.getArrWindSpeed().get(i) >= sheetDataModel.getConfigFileThreshold()[0]
                            .get(parameters[0]).getAsDouble()) {
                        cellValue.setCellStyle(setStyle("orange"));
                    }
                    else {
                        cellValue.setCellStyle(setStyle(""));
                    }
                }
                else if (parameters[2].equalsIgnoreCase("month")) {
                    cellValue.setCellValue(sheetDataModel.getConfigFileThreshold()[0].get("redThresholdTemperature").getAsDouble());
                    if (sheetDataModel.getArrWindSpeed().get(i) >= sheetDataModel.getConfigFileThreshold()[0]
                            .get(parameters[1]).getAsDouble()) {
                        cellValue.setCellStyle(setStyle("red"));
                    }else if (sheetDataModel.getArrWindSpeed().get(i) >= sheetDataModel.getConfigFileThreshold()[0]
                            .get(parameters[0]).getAsDouble()) {
                        cellValue.setCellStyle(setStyle("orange"));
                    }
                    else {
                        cellValue.setCellStyle(setStyle(""));
                    }
                }
                cellThresholdOrange.setCellValue(sheetDataModel.getConfigFileThreshold()[0].get(parameters[0]).getAsDouble());
                cellThresholdOrange.setCellStyle(setStyle(""));
                cellThresholdRed.setCellValue(sheetDataModel.getConfigFileThreshold()[0].get(parameters[1]).getAsDouble());
                cellThresholdRed.setCellStyle(setStyle("end"));
                rowTable++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("chart exc: " + e.getMessage());
        }
    }

    private CellStyle setStyle(String type) {
        CellStyle cellStyle = this.workbook.createCellStyle();

        if (type.equalsIgnoreCase("date")) {
            cellStyle.setBorderLeft(BorderStyle.MEDIUM);
            cellStyle.setBorderTop(BorderStyle.MEDIUM);
            cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        } else if (type.equalsIgnoreCase("end")) {
            cellStyle.setBorderRight(BorderStyle.MEDIUM);
            cellStyle.setBorderTop(BorderStyle.MEDIUM);
            cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        } else if (type.equalsIgnoreCase("header")) {
            Font font = this.workbook.createFont();
            font.setBold(true);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.MEDIUM);
        } else if (type.equalsIgnoreCase("red")) {
            cellStyle.setBorderTop(BorderStyle.MEDIUM);
            cellStyle.setBorderBottom(BorderStyle.MEDIUM);
            cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex()); // Color de fondo
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // Patrón de relleno SOLID_FOREGROUND
        } else if (type.equalsIgnoreCase("orange")) {
            cellStyle.setBorderTop(BorderStyle.MEDIUM);
            cellStyle.setBorderBottom(BorderStyle.MEDIUM);
            cellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex()); // Color de fondo
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // Patrón de relleno SOLID_FOREGROUND
        } else {
            cellStyle.setBorderTop(BorderStyle.MEDIUM);
            cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        }
        return cellStyle;
    }

    public void createMonthValues(int rowTable, int columnTable, String[] parameters, SheetDataModel sheetDataModel) {
        try {
            Row rowHeader = this.sheet.createRow(rowTable);
            Cell cellDateHeader = rowHeader.createCell(columnTable);
            cellDateHeader.setCellValue("Fecha");
            cellDateHeader.setCellStyle(setStyle("header"));
            Cell cellValueHeader = rowHeader.createCell(columnTable + 1);
            cellValueHeader.setCellValue(parameters[2]);
            cellValueHeader.setCellStyle(setStyle("header"));
            Cell cellPrecValue = rowHeader.createCell(columnTable + 2);
            cellPrecValue.setCellValue(parameters[3]);
            cellPrecValue.setCellStyle(setStyle("header"));
            Cell cellThresholdOrangeHeaderTemperature = rowHeader.createCell(columnTable + 3);
            cellThresholdOrangeHeaderTemperature.setCellValue("Temperatura Naranja");
            cellThresholdOrangeHeaderTemperature.setCellStyle(setStyle("header"));
            Cell cellThresholdRedHeaderTemperature = rowHeader.createCell(columnTable + 4);
            cellThresholdRedHeaderTemperature.setCellValue("Temperatura Roja");
            cellThresholdRedHeaderTemperature.setCellStyle(setStyle("header"));
            Cell cellThresholdOrangeHeaderPrecipitation = rowHeader.createCell(columnTable + 5);
            cellThresholdOrangeHeaderPrecipitation.setCellValue("Precipitación Naranja");
            cellThresholdOrangeHeaderPrecipitation.setCellStyle(setStyle("header"));
            Cell cellThresholdRedHeaderPrecipitation = rowHeader.createCell(columnTable + 6);
            cellThresholdRedHeaderPrecipitation.setCellValue("Precipitación Roja");
            cellThresholdRedHeaderPrecipitation.setCellStyle(setStyle("header"));

            List<String> keys = new ArrayList<>(sheetDataModel.getThresholdMonthlyJson().asMap().keySet());

            int dataStartIndex = 6; // omitimos los primeros 4 elementos comunes
            for (int i = dataStartIndex; i < keys.size(); i += 2) {
                String tempKey = keys.get(i);
                String precKey = (i + 1 < keys.size()) ? keys.get(i + 1) : null;

                Row rowMonth = sheet.createRow(++rowTable);
                Cell cellMonth = rowMonth.createCell(1);
                cellMonth.setCellStyle(setStyle("date"));

                Cell cellTempValue = rowMonth.createCell(2);
                cellTempValue.setCellStyle(setStyle(""));
                cellTempValue.setCellValue(sheetDataModel.getConfigFileThreshold()[1].get(tempKey).getAsDouble());

                if (precKey != null) {
                    Cell cellValue = rowMonth.createCell(3);
                    cellValue.setCellStyle(setStyle(""));
                    cellValue.setCellValue(sheetDataModel.getConfigFileThreshold()[1].get(precKey).getAsDouble());
                }

                rowMonth.createCell(4).setCellValue(sheetDataModel.getConfigFileThreshold()[1].get("orangeThresholdTemperature").getAsDouble());
                rowMonth.getCell(4).setCellStyle(setStyle(""));
                rowMonth.createCell(5).setCellValue(sheetDataModel.getConfigFileThreshold()[1].get("redThresholdTemperature").getAsDouble());
                rowMonth.getCell(5).setCellStyle(setStyle(""));
                rowMonth.createCell(6).setCellValue(sheetDataModel.getConfigFileThreshold()[1].get("orangeThresholdPrecipitation").getAsDouble());
                rowMonth.getCell(6).setCellStyle(setStyle(""));
                rowMonth.createCell(7).setCellValue(sheetDataModel.getConfigFileThreshold()[1].get("redThresholdPrecipitation").getAsDouble());
                rowMonth.getCell(7).setCellStyle(setStyle("end"));
            }

            String[] arrDate = {
                    "Diciembre", "Noviembre", "Octubre", "Septiembre", "Agosto", "Julio",
                    "Junio", "Mayo", "Abril", "Marzo", "Febrero", "Enero"
            };

            for (int i = 11; i >= 0; i--) {
                Row row = sheet.getRow(rowTable - i + 6);
                if (sheetDataModel.getThresholdMonthlyJson().get("stage").getAsDouble() == 1) {
                    if (row != null) {
                        Cell cell = row.getCell(1);
                        if (cell != null) {
                            cell = row.createCell(1);
                            cell.setCellValue(arrDate[i]);
                            cell.setCellStyle(setStyle("date"));
                        }
                    }
                    if (i == 6) {
                        break;
                    }
                }
                else if (sheetDataModel.getThresholdMonthlyJson().get("stage").getAsDouble() == 2) {
                    if (row != null) {
                        Cell cell = row.getCell(1);
                        if (cell == null) {
                            cell = row.createCell(1);
                        }
                        cell.setCellValue(arrDate[i - 6]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createCodeChart(int rowTable, int columnTable, String[] parameters, SheetDataModel sheetDataModel) {
        try {
            Row rowHeader = this.sheet.createRow(rowTable - 1);
            Cell cellDateHeader = rowHeader.createCell(columnTable);
            cellDateHeader.setCellValue("Fecha");
            cellDateHeader.setCellStyle(setStyle("header"));
            Cell cellValueHeader = rowHeader.createCell(columnTable + 1);
            cellValueHeader.setCellValue(parameters[1]);
            cellValueHeader.setCellStyle(setStyle("header"));
            Cell cellThresholdRedHeader = rowHeader.createCell(columnTable + 2);
            cellThresholdRedHeader.setCellValue("Alerta Roja");
            cellThresholdRedHeader.setCellStyle(setStyle("header"));
            for (int i = 0; i < sheetDataModel.getArrDate().size(); i++) {
                Row row = sheet.createRow(rowTable + i);
                Cell cellDate = row.createCell(columnTable);
                Cell cellValue = row.createCell(columnTable + 1);
                Cell cellThresholdOrange = row.createCell(columnTable + 2);
                cellDate.setCellValue(sheetDataModel.getArrDate().get(i));
                cellDate.setCellStyle(setStyle("date"));
                cellThresholdOrange.setCellValue(sheetDataModel.getThresholdCodeList().get("thunderstorm").getAsDouble());
                cellThresholdOrange.setCellStyle(setStyle("end"));
                if (parameters[1].equalsIgnoreCase("ceraunic")) {
                    cellValue.setCellValue(sheetDataModel.getArrCode().get(i));
                    if (sheetDataModel.getArrTemperature().get(i) >= sheetDataModel.getConfigFileThreshold()[0]
                            .get(parameters[0]).getAsDouble()) {
                        cellValue.setCellStyle(setStyle("red"));
                    } else {
                        cellValue.setCellStyle(setStyle(""));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
