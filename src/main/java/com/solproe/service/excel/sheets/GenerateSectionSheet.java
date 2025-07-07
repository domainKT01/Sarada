package com.solproe.service.excel.sheets;

import com.google.gson.JsonObject;
import com.solproe.business.domain.SheetDataModel;
import com.solproe.business.usecase.CreateConfigFileUseCase;
import com.solproe.service.excel.TypeReportSheet;
import com.solproe.util.DateUtil;
import com.solproe.util.JsonObjectToMap;
import com.solproe.util.logging.ErrorLogger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class GenerateSectionSheet {
    private final GenericSheetTemplate template;
    private final ExcelStyleFactory styleFactory;

    public GenerateSectionSheet(GenericSheetTemplate template, Workbook workbook) {
        this.template = template;
        this.styleFactory = new ExcelStyleFactory(template.getWorkbook(), template.getSheet());
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
        sheet.addMergedRegion(new CellRangeAddress(typeRow.getRowNum(), typeRow.getRowNum(), 3, 8));
        sheet.addMergedRegion(new CellRangeAddress(typeRow.getRowNum(), typeRow.getRowNum(), 0, 2));
        this.styleFactory.applyStyleBorder(true, true, 3, typeRow.getCell(0), false, true);
        this.styleFactory.applyStyleBorder(true, true, 6, typeRow.getCell(3), false, true);

        try {
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
            else if (model.getReportType() == TypeReportSheet.ceraunic) {
                typeRow.getCell(3).setCellValue("ESTADO DEL CLIMA ESPERADO");
                maxThreshold = "DENSIDAD DE DESCARGAS A TIERRA (DDT)";
            }

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
            this.styleFactory.applyStyleBorder(true, true, 2, projectRow.getCell(0), true, true);
            sheet.addMergedRegion(new CellRangeAddress(projectRow.getRowNum(), projectRow.getRowNum(), 0, 1));
            sheet.addMergedRegion(new CellRangeAddress(projectRow.getRowNum(), projectRow.getRowNum(), 2, 4));
            projectRow.createCell(2).setCellValue(projectName);
            this.styleFactory.applyStyleBorder(true, true, 3, projectRow.getCell(2), false, true);

            //project id
            projectRow.getCell(5).setCellValue("ID Proyecto:");
            sheet.addMergedRegion(new CellRangeAddress(projectRow.getRowNum(), projectRow.getRowNum(), 5, 6));
            this.styleFactory.applyStyleBorder(true, true, 2, projectRow.getCell(5), true, true);
            projectRow.getCell(7).setCellValue(projectId);
            sheet.addMergedRegion(new CellRangeAddress(projectRow.getRowNum(), projectRow.getRowNum(), 7, 8));
            this.styleFactory.applyStyleBorder(true, true, 2, projectRow.getCell(7), false, true);


            //town
            Row locationRow = sheet.createRow(rowIndex++);
            createCellsRow(sheet, 0, 8, locationRow);
            locationRow.getCell(0).setCellValue("Municipio:");
            this.styleFactory.applyStyleBorder(true, true, 2, locationRow.getCell(0), true, true);
            sheet.addMergedRegion(new CellRangeAddress(locationRow.getRowNum(), locationRow.getRowNum(), 0, 1));
            locationRow.getCell(2).setCellValue(city);
            sheet.addMergedRegion(new CellRangeAddress(locationRow.getRowNum(), locationRow.getRowNum(), 2,4));
            this.styleFactory.applyStyleBorder(true, true, 3, locationRow.getCell(2), false, true);

            //state
            locationRow.getCell(5).setCellValue("Departamento:");
            sheet.addMergedRegion(new CellRangeAddress(locationRow.getRowNum(), locationRow.getRowNum(), 5, 6));
            this.styleFactory.applyStyleBorder(true, true, 2, locationRow.getCell(5), true, true);
            locationRow.createCell(7).setCellValue(state);
            sheet.addMergedRegion(new CellRangeAddress(locationRow.getRowNum(), locationRow.getRowNum(), 7, 8));
            this.styleFactory.applyStyleBorder(true, true, 2, locationRow.getCell(7), false, true);

            //max weather
            Row maxTemp = sheet.createRow(rowIndex++);
            createCellsRow(sheet, 0, 8, maxTemp);
            maxTemp.getCell(0).setCellValue(maxThreshold);
            sheet.addMergedRegion(new CellRangeAddress(maxTemp.getRowNum(), maxTemp.getRowNum(), 0, 3));
            this.styleFactory.applyStyleBorder(true, true, 4, maxTemp.getCell(0), true, true);
            sheet.addMergedRegion(new CellRangeAddress(maxTemp.getRowNum(), maxTemp.getRowNum(), 4, 8));
            this.styleFactory.applyStyleBorder(true, true, 5, maxTemp.getCell(4), false, true);
        } catch (Exception e) {
            ErrorLogger.log(e);
        }
        return rowIndex + 1; // deja una fila vacía después
    }

    public int createAlertSystem(Sheet sheet, int startRow, SheetDataModel model) {
        Workbook workbook = this.template.getWorkbook();

        // Título principal
        Row titleRow = sheet.createRow(startRow);
        createCellsRow(sheet, 0, 8, titleRow);
        titleRow.getCell(0).setCellValue("SISTEMA DE ALERTAS");
        titleRow.getCell(0).setCellStyle(this.styleFactory.createHeaderTitleStyle((short) 16));

        startRow += 1;
        if (model.getReportType() == TypeReportSheet.forestFireDataModel) {
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, 8));
            String[] alerts = {
                    "ACTIVAR EN PREVENTIVO EL PMU Y ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS (EQUIPOS LISTOS PARA REACCIÓN INMEDIATA)",
                    "ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS",
            };
            startRow = createAlertChart(sheet, startRow, model, alerts);
        }
        else if (model.getReportType() == TypeReportSheet.massMovementDataModel) {
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, 8));
            String[] alerts = {
                    "ACTIVAR EN PREVENTIVO EL PMU Y ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS (EQUIPOS LISTOS PARA REACCIÓN INMEDIATA)",
                    "ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS",
                    "PREPARACIÓN PARA LA RESPUESTA, ALISTAMIENTO DE RECURSOS, SUMINISTROS Y SERVICIOS E IDENTIFICACIÓN DE DE LAS RUTAS DE INGRESO Y EGRESO."
            };
            startRow = createAlertChart(sheet, startRow, model, alerts);
        }
        else if (model.getReportType() == TypeReportSheet.rainShowerDataModel) {
            String[] alerts = {
                    "ACTIVAR EN PREVENTIVO EL PMU Y ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS (EQUIPOS LISTOS PARA REACCIÓN INMEDIATA)",
                    "ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS",
            };
            startRow += 5;
            startRow = createAlertChart(sheet, startRow, model, alerts);
        }
        else if (model.getReportType() == TypeReportSheet.ceraunic) {
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, 8));
            String[] alerts = {
                    "ACTIVAR EN PREVENTIVO EL PMU Y ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS (EQUIPOS LISTOS PARA REACCIÓN INMEDIATA)",
            };
            startRow = createAlertChart(sheet, startRow, model, alerts);
        }
        return startRow + 1;
    }

    private int createAlertChart(Sheet sheet, int row, SheetDataModel model, String[] alertsLevel) {
        try {
            Row alertLevel = sheet.createRow(row + 1);
            createCellsRow(sheet, 0, 8, alertLevel);
            sheet.addMergedRegion(new CellRangeAddress(alertLevel.getRowNum(), alertLevel.getRowNum(), 2, 3));
            sheet.addMergedRegion(new CellRangeAddress(alertLevel.getRowNum(), alertLevel.getRowNum(), 4, 6));
            alertLevel.getCell(2).setCellValue("NIVEL DE ALERTA");
            this.styleFactory.applyStyleBorder(true, true, 2, alertLevel.getCell(row + 1), true, true);
            alertLevel.getCell(4).setCellValue("ACCIONES POR NIVEL DE ALERTA");
            this.styleFactory.applyStyleBorder(true, true, 3, alertLevel.getCell(4), true, true);
            row += 1;
            String[] titles;
            String[] params;
            if (model.getReportType() == TypeReportSheet.forestFireDataModel) {
                titles = new String[] {
                        "ROJA",
                        "NARANJA",
                };
                params = new String[] {
                        "UMBRAL DE TEMPERATURA Y NIVEL DE ALERTA MONITOREO DIARIO",
                        "UMBRAL DE TEMPERATURA (°C)",
                        model.getThresholdDailyJson().get("forestFireThresholdRed").getAsString() + " °C",
                        model.getThresholdDailyJson().get("forestFireThresholdOrange").getAsString() + " °C",
                };
                String[] threshold = {
                        "forestFireThresholdRed",
                        "forestFireThresholdOrange",
                };
                row = createThresholdComments(sheet, row, titles, alertsLevel);
                row = createChartThreshold(sheet, row, params);
//                row += 2;
//                row = createChartDateAlerts(sheet, row, model, alertsLevel, threshold, titles);
//                row = createChartMonthlyAlerts(sheet, row, model, titles);
            }
            else if (model.getReportType() == TypeReportSheet.massMovementDataModel) {
                titles = new String[] {
                        "ROJA",
                        "NARANJA",
                        "AMARILLA",
                };
                params = new String[] {
                        "UMBRAL DE PRECIPITACIÓN Y NIVEL DE ALERTA PARA DATOS DE PRECIPITACIÓN - MONITOREO DIARIO   ",
                        "UMBRAL DE PRECIPITACION (mm)",
                        model.getThresholdDailyJson().get("precipitationThresholdRed").getAsString() + " mm",
                        model.getThresholdDailyJson().get("precipitationThresholdOrange").getAsString() + " mm",

                };
                String[] threshold = {
                        "precipitationThresholdRed",
                        "precipitationThresholdOrange",
                };
//                row = createThresholdComments(sheet, row, titles, alertsLevel);
//                row = createChartThreshold(sheet, row, params);
//                row += 2;
//                row = createChartDateAlerts(sheet, row, model, alertsLevel, threshold, titles, "mm");

                row += 2;
                params[0] = "UMBRAL DE PRECIPITACIÓN Y NIVEL DE ALERTA EN FUNCIÓN DE LA PROBABILIDAD DE LLUVIA";
                params[1] = "UMBRAL POR PROBABILIDAD DE LLUVIA";
                params[2] = model.getThresholdDailyJson().get("precipitationRainPercentRed").getAsString() + " %";
                params[3] = model.getThresholdDailyJson().get("precipitationRainPercentOrange").getAsString() + " %";
//                row = createChartThreshold(sheet, row, params);
//                row += 2;
//                threshold[0] = "precipitationRainPercentRed";
//                threshold[1] = "precipitationRainPercentOrange";
//                row = createChartDateAlerts(sheet, row, model, alertsLevel, threshold, titles, "%");
//                row += 2;
//                row = createChartMonthlyAlerts(sheet, row, model, titles);
            }
            else if (model.getReportType() == TypeReportSheet.rainShowerDataModel) {
                titles = new String[] {
                        "ROJA",
                        "NARANJA",
                };
                params = new String[] {
                        "UMBRAL DE VELOCIDAD DEL VIENTO Y NIVEL DE ALERTA - MONITOREO DIARIO",
                        "UMBRAL DE VELOCIDAD DEL VIENTO (Km/h)",
                        model.getThresholdDailyJson().get("windThresholdRed").getAsString() + " km/h",
                        model.getThresholdDailyJson().get("windThresholdOrange").getAsString() + " km/h",
                };
                String[] threshold = {
                        "windThresholdRed",
                        "windThresholdOrange",
                };
//                row = createThresholdComments(sheet, row, titles, alertsLevel);
//                row = createChartThreshold(sheet, row, params);
//                row += 2;
//                row = createChartDateAlerts(sheet, row, model, alertsLevel, threshold, titles);
                //row = createChartMonthlyAlerts(sheet, row, model, titles);
            } else if (model.getReportType() == TypeReportSheet.ceraunic) {
                titles = new String[] {
                        "ROJA",
                };
                params = new String[] {
                        "UMBRAL DE FENÓMENO CERÁUNICO Y NIVEL DE ALERTA",
                        "UMBRAL SEGÚN CLIMA ESPERADO",
                        "Tormenta Eléctrica",
                };
//                row = createThresholdComments(sheet, row, titles, alertsLevel);
//                row = createChartThreshold(sheet, row, params);
//                row += 2;
//                String[] threshold = {
//                        "thunderstorm"
//                };
//                row = createChartDateAlerts(sheet, row, model, alertsLevel, threshold, titles);
            }
        } catch (Exception e) {
            ErrorLogger.log(e);
        }

        return  row;
    }

    public int createThresholdComments(Sheet sheet, int row, String[] titles, String[] alertsLevel) {
        row += 1;
        for (int i = 0; i < titles.length; i++) {
            Row title = sheet.createRow(row + i);
            createCellsRow(sheet, 0, 8, title);
            title.setHeight((short) 1200);
            title.getCell(2).setCellValue(titles[i]);
            title.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, false, titles[i]));
            title.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, false, true, titles[i]));
            sheet.addMergedRegion(new CellRangeAddress(title.getRowNum(), title.getRowNum(), 2, 3));
            title.getCell(4).setCellValue(alertsLevel[i]);
            title.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(false, true, true, true, false, true));
            title.getCell(5).setCellStyle(this.styleFactory.createBorderedStyle(false, true, true, true, false, false));
            title.getCell(6).setCellStyle(this.styleFactory.createBorderedStyle(false, true, true, true, false, true));
            sheet.addMergedRegion(new CellRangeAddress(title.getRowNum(), title.getRowNum(), 4, 6));
        }
        return row + 5;
    }

    private int createChartThreshold(Sheet sheet, int row, String... params) {
        try {
            {
                Row title = sheet.createRow(row);
                createCellsRow(sheet, 0, 8, title);
                title.getCell(0).setCellValue(params[0]);
                title.getCell(0).setCellStyle(this.styleFactory.createHeaderTitleStyle((short) 13));
                sheet.addMergedRegion(new CellRangeAddress(title.getRowNum(), title.getRowNum(), 0, 8));
                row += 2;
            }
            {
                Row head = sheet.createRow(row);
                createCellsRow(sheet, 0, 8, head);
                head.getCell(2).setCellValue("NIVEL DE ALERTA");
                this.styleFactory.applyStyleBorder(true, true, 2, head.getCell(2), true, true);
                sheet.addMergedRegion(new CellRangeAddress(head.getRowNum(), head.getRowNum(), 2, 3));
                head.getCell(4).setCellValue(params[1]);
                this.styleFactory.applyStyleBorder(true, true, 3, head.getCell(4), false, true);
                sheet.addMergedRegion(new CellRangeAddress(head.getRowNum(), head.getRowNum(), 4, 6));
                row += 1;
            }
            {
                Row red = sheet.createRow(row);
                createCellsRow(sheet, 0, 8, red);
                red.getCell(2).setCellValue("ROJA");
                red.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, false, "ROJA"));
                red.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, false, true, "ROJA"));
                sheet.addMergedRegion(new CellRangeAddress(red.getRowNum(), red.getRowNum(), 2, 3));
                red.getCell(4).setCellValue(params[2]);
                this.styleFactory.applyStyleBorder(true, true, 3, red.getCell(4), false, true);
                sheet.addMergedRegion(new CellRangeAddress(red.getRowNum(), red.getRowNum(), 4, 6));
                row += 1;
            }
            {
                if (params.length > 3) {
                    Row orange = sheet.createRow(row);
                    createCellsRow(sheet, 0, 8, orange);
                    orange.getCell(2).setCellValue("NARANJA");
                    orange.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, false, "NARANJA"));
                    orange.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, false, false, "NARANJA"));
                    orange.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, false, true, "NARANJA"));
                    sheet.addMergedRegion(new CellRangeAddress(orange.getRowNum(), orange.getRowNum(), 2, 3));
                    orange.getCell(4).setCellValue(params[3]);
                    this.styleFactory.applyStyleBorder(true, true, 3, orange.getCell(4), false, true);
                    sheet.addMergedRegion(new CellRangeAddress(orange.getRowNum(), orange.getRowNum(), 4, 6));
                    row += 1;
                }
            }
        } catch (Exception e) {
            ErrorLogger.log(e);
            System.out.println("threshold: " + e.getMessage());
        }
        return row;
    }
//
//    public int createChartDateAlerts(Sheet sheet, int row, SheetDataModel model, String[] alertsLevel,
//                                     String[] threshold, String[] titles, String... args) {
//        {
//            Row header = sheet.createRow(row);
//            header.setHeight((short) 700);
//            createCellsRow(sheet, 0, 8, header);
//            header.getCell(1).setCellValue("FECHA DE LECTURA ANOMALA");
//            header.getCell(1).setCellStyle(this.styleFactory.createBorderedStyle(true, true));
//            sheet.addMergedRegion(new CellRangeAddress(header.getRowNum(), header.getRowNum(), 1, 2));
//            header.getCell(3).setCellValue("NIVEL DE ALERTA");
//            header.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(true, true));
//            header.getCell(4).setCellValue("ACCIONES POR NIVEL DE ALERTA");
//            header.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(true, true));
//            sheet.addMergedRegion(new CellRangeAddress(header.getRowNum(), header.getRowNum(), 4, 7));
//        }
//        {
//            ArrayList<Double> arrData = new ArrayList<>();
//            row += 1;
//            for (int i = 0; i < model.getArrDate().size(); i++) {
//                if (model.getReportType() == TypeReportSheet.forestFireDataModel) {
//                    arrData = model.getArrTemperature();
//                }
//                else if (model.getReportType() == TypeReportSheet.massMovementDataModel) {
//                    if (args[0].equalsIgnoreCase("mm")) {
//                        arrData = model.getArrPrecipitationMm();
//                    }
//                    else {
//                        arrData = model.getArrPrecipitationPercent();
//                    }
//                } else if (model.getReportType() == TypeReportSheet.rainShowerDataModel) {
//                    arrData = model.getArrWindSpeed();
//                } else if (model.getReportType() == TypeReportSheet.ceraunic) {
//                    arrData = model.getArrCode();
//                }
//                JsonObject jsonObjectThreshold;
//                if (model.getReportType() == TypeReportSheet.ceraunic) {
//                    jsonObjectThreshold = model.getThresholdCodeList();
//                }
//                else {
//                    jsonObjectThreshold = model.getThresholdDailyJson();
//                }
//                if (arrData.get(i) >= jsonObjectThreshold.get(threshold[0]).getAsDouble()) {
//                    Row data = sheet.createRow(row);
//                    createCellsRow(sheet, 0, 8, data);
//                    data.getCell(1).setCellValue(model.getArrDate().get(i));
//                    data.getCell(1).setCellStyle(this.styleFactory.createBorderedStyle(true, true));
//                    sheet.addMergedRegion(new CellRangeAddress(data.getRowNum(), data.getRowNum(), 1, 2));
//                    data.getCell(3).setCellValue(titles[0]);
//                    data.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(false, true));
//                    data.getCell(4).setCellValue(alertsLevel[0]);
//                    data.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(false, true));
//                    sheet.addMergedRegion(new CellRangeAddress(data.getRowNum(), data.getRowNum(), 4, 7));
//                    row += 1;
//                } else if (model.getReportType() != TypeReportSheet.ceraunic && arrData.get(i) >= model.getThresholdDailyJson().get(threshold[1]).getAsDouble()) {
//                    Row data = sheet.createRow(row);
//                    createCellsRow(sheet, 0, 8, data);
//                    data.getCell(1).setCellValue(model.getArrDate().get(i));
//                    data.getCell(1).setCellStyle(this.styleFactory.createBorderedStyle(true, true));
//                    sheet.addMergedRegion(new CellRangeAddress(data.getRowNum(), data.getRowNum(), 1, 2));
//                    data.getCell(3).setCellValue(titles[1]);
//                    data.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(false, true));
//                    data.getCell(4).setCellValue(alertsLevel[1]);
//                    data.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(false, true));
//                    sheet.addMergedRegion(new CellRangeAddress(data.getRowNum(), data.getRowNum(), 4, 7));
//                    row += 1;
//                }
//            }
//        }
//
//        return row + 1;
//    }
//
    public int createChartMonthlyAlerts(Sheet sheet, int row, SheetDataModel model, String[] titles) {
        {
            Row title = sheet.createRow(row);
            createCellsRow(sheet, 0, 8, title);
            title.getCell(0).setCellValue("NIVEL DE ALERTA PARA LOS PROXIMOS 6 MESES");
            title.getCell(0).setCellStyle(this.styleFactory.createHeaderTitleStyle((short) 16));
            sheet.addMergedRegion(new CellRangeAddress(title.getRowNum(), title.getRowNum(), 0, 8));
            row += 2;
        }
        {
            try {
                Row header = sheet.createRow(row);
                header.setHeight((short) 700);
                createCellsRow(sheet, 0, 8, header);
                header.getCell(3).setCellValue("MES");
                header.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
                header.getCell(4).setCellValue("NIVEL DE ALERTA");
                this.styleFactory.applyStyleBorder(true, true, 3, header.getCell(4), true, true);
                sheet.addMergedRegion(new CellRangeAddress(header.getRowNum(), header.getRowNum(), 4, 6));
                Map<String, Object> map = JsonObjectToMap.convertJsonObjectToMap(model.getThresholdMonthlyJson());
                int count = 0;
                int outRange = 0;
                row += 1;
                for (String data : map.keySet()) {
                    if (count < 5) {
                        count += 1;
                        continue;
                    }
                    if (model.getReportType() == TypeReportSheet.forestFireDataModel && count % 2 == 0) {
                        if (model.getThresholdMonthlyJson().get(data).getAsDouble() >= model.getThresholdMonthlyJson().get("redThresholdTemperature").getAsDouble()) {
                            if (model.getThresholdMonthlyJson().get("stage").getAsDouble() == 1 && count <= 16 && data.contains("DataGrade")) {
                                Row row1 = sheet.createRow(row + outRange);
                                createCellsRow(sheet, 0, 8, row1);
                                int index = data.indexOf("DataGrade");
                                String month = data.substring(0, index);
                                row1.getCell(3).setCellValue(month);
                                row1.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
                                row1.getCell(4).setCellValue(titles[0]);
                                row1.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true, "ROJA"));
                                sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum(), 4, 6));
                                outRange += 1;
                            }
                            else if (model.getThresholdMonthlyJson().get("stage").getAsDouble() == 2 && count > 16 && data.contains("DataGrade")) {
                                Row row1 = sheet.createRow(row + outRange);
                                createCellsRow(sheet, 0, 8, row1);
                                int index = data.indexOf("DataGrade");
                                String month = data.substring(0, index).trim();
                                row1.getCell(3).setCellValue(month);
                                row1.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
                                row1.getCell(4).setCellValue(titles[0]);
                                row1.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true, "ROJA"));
                                sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum(), 4, 6));
                                outRange += 1;
                            }
                        }
                        else if (model.getThresholdMonthlyJson().get(data).getAsDouble() >= model.getThresholdMonthlyJson().get("orangeThresholdTemperature").getAsDouble()) {
                            if (model.getThresholdMonthlyJson().get("stage").getAsDouble() == 1 && count <= 16 && data.contains("DataGrade")) {
                                Row row1 = sheet.createRow(row + outRange);
                                createCellsRow(sheet, 0, 8, row1);
                                int index = data.indexOf("DataGrade");
                                String month = data.substring(0, index).trim();
                                row1.getCell(3).setCellValue(month);
                                row1.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
                                row1.getCell(4).setCellValue(titles[1]);
                                row1.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true, "NARANJA"));
                                sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum(), 4, 6));
                                outRange += 1;
                            }
                            else if (model.getThresholdMonthlyJson().get("stage").getAsDouble() == 2 && count > 16 && data.contains("DataGrade")) {
                                Row row1 = sheet.createRow(row + outRange);
                                createCellsRow(sheet, 0, 8, row1);
                                int index = data.indexOf("DataGrade");
                                String month = data.substring(0, index).trim();
                                row1.getCell(3).setCellValue(month);
                                row1.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
                                row1.getCell(4).setCellValue(titles[1]);
                                row1.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true, "NARANJA"));
                                sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum(), 4, 6));
                                outRange += 1;
                            }
                        }
                    }
                    else if (model.getReportType() == TypeReportSheet.massMovementDataModel && count % 2 == 1) {
                        if (model.getThresholdMonthlyJson().get(data).getAsDouble() >= model.getThresholdMonthlyJson().get("redThresholdPrecipitation").getAsDouble()) {
                            if (model.getThresholdMonthlyJson().get("stage").getAsDouble() == 1 && count <= 16 && data.contains("DataPercent")) {
                                Row row1 = sheet.createRow(row + outRange);
                                createCellsRow(sheet, 0, 8, row1);
                                int index = data.indexOf("DataPercent");
                                String month = data.substring(0, index).trim();
                                row1.getCell(3).setCellValue(month);
                                row1.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
                                row1.getCell(4).setCellValue(titles[0]);
                                row1.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true, "ROJA"));
                                sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum(), 4, 6));
                                outRange += 1;
                            }
                            else if (model.getThresholdMonthlyJson().get("stage").getAsDouble() == 2 && count > 16 && data.contains("DataPercent")) {
                                Row row1 = sheet.createRow(row + outRange);

                                createCellsRow(sheet, 0, 8, row1);
                                int index = data.indexOf("DataPercent");
                                String month = data.substring(0, index).trim();
                                row1.getCell(3).setCellValue(month);
                                row1.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
                                row1.getCell(4).setCellValue(titles[0]);
                                row1.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true, "ROJA"));
                                sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum(), 4, 6));
                                outRange += 1;
                            }
                        }
                        else if (model.getThresholdMonthlyJson().get(data).getAsDouble() >= model.getThresholdMonthlyJson().get("orangeThresholdPrecipitation").getAsDouble()) {
                            if (model.getThresholdMonthlyJson().get("stage").getAsDouble() == 1 && count <= 16 && data.contains("DataPercent"))  {
                                Row row1 = sheet.createRow(row + outRange);
                                createCellsRow(sheet, 0, 8, row1);
                                int index = data.indexOf("DataPercent");
                                String month = data.substring(0, index).trim();
                                row1.getCell(3).setCellValue(month);
                                row1.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
                                row1.getCell(4).setCellValue(titles[1]);
                                row1.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true, "NARANJA"));
                                sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum(), 4, 6));
                                outRange += 1;
                            }
                            else if (model.getThresholdMonthlyJson().get("stage").getAsDouble() == 2 && count > 16 && data.contains("DataPercent")) {
                                Row row1 = sheet.createRow(row + outRange);
                                createCellsRow(sheet, 0, 8, row1);
                                int index = data.indexOf("DataPercent");
                                String month = data.substring(0, index).trim();
                                row1.getCell(3).setCellValue(month);
                                row1.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
                                row1.getCell(4).setCellValue(titles[1]);
                                row1.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true, "NARANJA"));
                                sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum(), 4, 6));
                                outRange += 1;
                            }
                        }
                        else if (model.getThresholdMonthlyJson().get(data).getAsDouble() >= model.getThresholdMonthlyJson().get("yellowThresholdPrecipitation").getAsDouble()) {
                            if (model.getThresholdMonthlyJson().get("stage").getAsDouble() == 1 && count <= 16 && data.contains("DataPercent"))  {
                                Row row1 = sheet.createRow(row + outRange);
                                createCellsRow(sheet, 0, 8, row1);
                                int index = data.indexOf("DataPercent");
                                String month = data.substring(0, index).trim();
                                row1.getCell(3).setCellValue(month);
                                row1.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
                                row1.getCell(4).setCellValue(titles[2]);
                                row1.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true, "AMARILLA"));
                                sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum(), 4, 6));
                                outRange += 1;
                            }
                            else if (model.getThresholdMonthlyJson().get("stage").getAsDouble() == 2 && count > 16 && data.contains("DataPercent")) {
                                Row row1 = sheet.createRow(row + outRange);
                                createCellsRow(sheet, 0, 8, row1);
                                int index = data.indexOf("DataPercent");
                                String month = data.substring(0, index).trim();
                                row1.getCell(3).setCellValue(month);
                                row1.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
                                row1.getCell(4).setCellValue(titles[2]);
                                row1.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true, "NARANJA"));
                                sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum(), 4, 6));
                                outRange += 1;
                            }
                        }
                    }
                    count += 1;
                }
                row += outRange;
            } catch (Exception e) {
                ErrorLogger.log(e);
                throw new RuntimeException(e);
            }
        }
        return row;
    }

    public void createCellsRow(Sheet sheet, int startCell, int endCell, Row row) {
        try {
            for (int i = startCell; i <= endCell; i++) {
                Cell cell = row.createCell(i);
            }
        }
        catch (Exception e) {
            System.out.println("create cells row");
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
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
                    Row row = sheet.createRow(startRow + count.get());
                    createCellsRow(sheet, 0, 8, row);
                    row.getCell(2).setCellValue(model.getArrDate().get(i));
                    row.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
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
                    row.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
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
                    row.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
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
                    row.getCell(2).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
                    sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 2, 8));
                    count.set(count.get() + 1);
                });

        if (count.get() != 0) {
            sheet.addMergedRegion(new CellRangeAddress(startRow, startRow + count.get() - 1, 0, 1));
            sheet.getRow(startRow).getCell(0).setCellValue("FECHA DE LECTURA ANÓMALA:");
            sheet.getRow(startRow).getCell(0).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
        }
        return startRow + count.get();
    }

    public int createNotificationChart(Sheet sheet, int row, SheetDataModel model) {
        Row title = sheet.createRow(row);
        createCellsRow(sheet, 0, 8, title);
        title.getCell(0).setCellValue("MECANISMOS  DE NOTIFICACIÓN Y AVISO PREVIO ");
        title.getCell(0).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
        sheet.addMergedRegion(new CellRangeAddress(title.getRowNum(), title.getRowNum(), 0, 8));
        row += 2;
        for (int i = 0; i<= 16; i++) {
            Row row1 = sheet.createRow(row + i);
            createCellsRow(sheet, 0, 8, row1);
        }
        {
            Row header = sheet.getRow(row);
            header.getCell(0).setCellValue("TIPO DE ALERTA");
            header.getCell(0).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
            sheet.addMergedRegion(new CellRangeAddress(header.getRowNum(), header.getRowNum(), 0, 2));
            header.setHeight((short) 1600);
            header.getCell(3).setCellValue("FECHA DE NOTIFICACIÓN (Un día antes y el día esperado de Ocurrencia)\n");
            header.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
            header.getCell(4).setCellValue("NOTIFICACIÓN Y CADENA DE LLAMADO");
            header.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
            header.getCell(5).setCellValue("CONTACTO RESPONSABLE");
            header.getCell(5).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
            sheet.addMergedRegion(new CellRangeAddress(header.getRowNum(), header.getRowNum(), 5, 6));
            header.getCell(7).setCellValue("MENSAJE");
            header.getCell(7).setCellStyle(this.styleFactory.createBorderedStyle(true, true, true, true, true, true));
            sheet.addMergedRegion(new CellRangeAddress(header.getRowNum(), header.getRowNum(), 7, 8));
            row += 1;
        }
        String[] notification = new String[0];
        String[] message = new String[0];
        String[] alert = new String[0];
        if (model.getReportType() == TypeReportSheet.forestFireDataModel) {
            notification = new String[] {
                    "Comunicar de manera inmediata al jefe del sistema de comando de incidentes y al Jefe de Área y/o " +
                            "Auxiliar del Comandante de Incidentes al número telefónico indicando nivel de alerta Naranja",
                    "Comunicar de manera inmediata al jefe del sistema de comando de incidentes y al Jefe de Área y/o " +
                            "Auxiliar del Comandante de Incidentes al número telefónico indicando nivel de alerta Roja" +
                            " y activación de mecanismos de alarma",
                    "Comunicar de manera inmediata al jefe del sistema de comando de incidentes y al Jefe de Área y/o " +
                            "Auxiliar del Comandante de Incidentes al número telefónico:",
                    "Comunicar de manera inmediata al jefe del sistema de comando de incidentes y al Jefe de Área" +
                            " y/o Auxiliar del Comandante de Incidentes al número telefónico:",
            };

            message = new String[] {
                    "ACTIVACIÓN DE BRIGADAS DE EMERGENCIA DE ALERTA POR INCENDIO FORESTAL",
                    "ACTIVAR EN PREVENTIVO EL PMU Y ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS (EQUIPOS LISTOS" +
                            " PARA REACCIÓN INMEDIATA) ALERTA ROJA POR INCENDIO FORESTAL",
                    "ACTIVACIÓN DE BRIGADAS DE EMERGENCIA POR ALERTA NARANJA FRENTE A INCENDIOS FORESTALES",
                    "ACTIVAR EN PREVENTIVO EL PMU Y ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS (EQUIPOS LISTOS PARA " +
                            "REACCIÓN INMEDIATA) ALERTA ROJA POR INCENDIO FORESTAL",
            };

            alert = new String[] {
                    "ALERTA POR POR IDENTIFICACION DE LECTURAS ANÓMALAS EN MONITOREO DIARIO PARA NIVEL DE ALERTA NARANJA \n",
                    "ALERTA POR IDENTIFICACIÓN DE LECTURAS ANÓMALAS EN MONITOREO DIARIO PARA NIVEL DE ALERTA ROJA\n",
                    "ALERTA NARANJA POR IDENTIFICACION DE MES PREVISTO DE AUMENTO ANÓMALO EN EL VALOR PROMEDIO DE LAS LECTURAS DE TEMPERATURA EN MONITOREO MENSUAL\n",
                    "ALERTA ROJA POR IDENTIFICACION DE MES PREVISTOS DE AUMENTO ANÓMALO EN EL VALOR PROMEDIO DE LAS LECTURAS DE TEMPERATURA EN MONITOREO MENSUAL\n",
            };
        }
        else if (model.getReportType() == TypeReportSheet.massMovementDataModel) {
            notification = new String[] {
                    "Comunicar de manera inmediata al jefe del sistema de comando de incidentes y al Jefe de Área y/o " +
                            "Auxiliar del Comandante de Incidentes al número telefónico indicando nivel de alerta Naranja",
                    "Comunicar de manera inmediata al jefe del sistema de comando de incidentes y al Jefe de Área y/o " +
                            "Auxiliar del Comandante de Incidentes al número telefónico indicando nivel de alerta" +
                            " Roja y activación de mecanismos de alarma",
                    "Comunicar de manera inmediata al jefe del sistema de comando de incidentes y al Jefe de Área y/o " +
                            "Auxiliar del Comandante de Incidentes al número telefónico:",
                    "Comunicar de manera inmediata al jefe del sistema de comando de incidentes y al Jefe de Área y/o " +
                            "Auxiliar del Comandante de Incidentes al número telefónico:",
            };

            message = new String[] {
                    "ACTIVACIÓN DE BRIGADAS DE EMERGENCIA DE ALERTA POR MOVIMIENTOS EN MASA",
                    "ACTIVAR EN PREVENTIVO EL PMU Y ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS (EQUIPOS LISTOS PARA " +
                            "REACCIÓN INMEDIATA) ALERTA ROJA POR MOVIMIENTOS EN MASA",
                    "ACTIVACIÓN DE BRIGADAS DE EMERGENCIA POR ALERTA NARANJA FRENTE A MOVIMIENTOS EN MASA",
                    "ACTIVAR EN PREVENTIVO EL PMU Y ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS (EQUIPOS LISTOS PARA " +
                            "REACCIÓN INMEDIATA) ALERTA ROJA POR MOVIMIENTOS EN MASA",
            };

            alert = new String[] {
                    "ALERTA POR POR IDENTIFICACION DE LECTURAS ANÓMALAS EN MONITOREO DIARIO PARA NIVEL DE ALERTA NARANJA \n",
                    "ALERTA POR IDENTIFICACIÓN DE LECTURAS ANÓMALAS EN MONITOREO DIARIO PARA NIVEL DE ALERTA ROJA\n",
                    "ALERTA NARANJA POR IDENTIFICACION DE MES PREVISTO DE AUMENTO ANÓMALO EN EL VALOR PROMEDIO DE LAS LECTURAS DE TEMPERATURA EN MONITOREO MENSUAL\n",
                    "ALERTA ROJA POR IDENTIFICACION DE MES PREVISTOS DE AUMENTO ANÓMALO EN EL VALOR PROMEDIO DE LAS LECTURAS DE TEMPERATURA EN MONITOREO MENSUAL\n",
            };
        } else if (model.getReportType() == TypeReportSheet.rainShowerDataModel) {
            notification = new String[] {
                    "ALERTA POR IDENTIFICACIÓN DE LECTURAS ANÓMALAS EN MONITOREO DIARIO PARA NIVEL DE ALERTA ROJA"
            };

            message = new String[] {
                    "ACTIVAR EN PREVENTIVO EL PMU Y ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS (EQUIPOS LISTOS PARA REACCIÓN INMEDIATA) ALERTA ROJA POR FENÓMENOS CERÁUNICOS"
            };

            alert = new String[] {
                    "ALERTA POR IDENTIFICACIÓN DE LECTURAS ANÓMALAS EN MONITOREO DIARIO PARA NIVEL DE ALERTA ROJA\n"
            };
        } else if (model.getReportType() == TypeReportSheet.ceraunic) {
            notification = new String[] {
                    "Comunicar de manera inmediata al jefe del sistema de comando de incidentes y al Jefe de Área y/o Auxiliar del Comandante de Incidentes al número telefónico indicando nivel de alerta Roja y activación de mecanismos de alarma"
            };

            message = new String[] {
                    "ACTIVAR EN PREVENTIVO EL PMU Y ALISTAMIENTO DE BRIGADAS DE EMERGENCIAS (EQUIPOS LISTOS PARA REACCIÓN INMEDIATA) ALERTA ROJA POR FENÓMENOS CERÁUNICOS"
            };

            alert = new String[] {
                   "ALERTA POR IDENTIFICACIÓN DE LECTURAS ANÓMALAS EN MONITOREO DIARIO PARA NIVEL DE ALERTA ROJA"
            };
        }
        row = generateChartNotification(sheet, row, model, notification, message, alert);
        return  row + 3;
    }

    public int generateChartNotification(Sheet sheet, int row, SheetDataModel model, String[] notification, String[] message, String[] alert) {
        int rowsNum = 3;
        for (int i = 0; i < alert.length; i++) {
            Row row1 = sheet.getRow(row);
            createCellsRow(sheet, 0, 8, row1);
            row1.getCell(0).setCellValue(alert[i]);
            row1.getCell(0).setCellStyle(this.styleFactory.createBorderedStyle(false, true, true, true, true, true));
            sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum() + rowsNum, 0, 2));
            row1.getCell(3).setCellStyle(this.styleFactory.createBorderedStyle(false, true, true, true, true, true));
            sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum() + rowsNum, 3, 3));
            row1.getCell(4).setCellValue(notification[i]);
            row1.getCell(4).setCellStyle(this.styleFactory.createBorderedStyle(false, true, true, true, true, true));
            sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum() + rowsNum, 4, 4));
            row1.getCell(5).setCellValue("Jefe Sci");
            row1.getCell(5).setCellStyle(this.styleFactory.createBorderedStyle(false, true, true, true, true, true));
            sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum(), 5, 6));
            Row bossSciContact = sheet.createRow(row + 1);
            createCellsRow(sheet, 5, 6, bossSciContact);
            bossSciContact.getCell(5).setCellStyle(this.styleFactory.createBorderedStyle(false, true, true, true, true, true));
            bossSciContact.getCell(5).setCellValue(model.getThresholdDailyJson().get("sciBossContact").getAsLong());
            sheet.addMergedRegion(new CellRangeAddress(bossSciContact.getRowNum(), bossSciContact.getRowNum(), 5, 6));
            Row auxiliarBoss = sheet.createRow(row + 2);
            createCellsRow(sheet, 5, 6, auxiliarBoss);
            auxiliarBoss.getCell(5).setCellValue("Auxiliar Jefe SCI");
            auxiliarBoss.getCell(5).setCellStyle(this.styleFactory.createBorderedStyle(false, true, true, true, true, true));
            sheet.addMergedRegion(new CellRangeAddress(auxiliarBoss.getRowNum(), auxiliarBoss.getRowNum(), 5, 6));
            Row auxiliarBossContact = sheet.createRow(row + 3);
            createCellsRow(sheet, 5, 6, auxiliarBossContact);
            auxiliarBossContact.getCell(5).setCellValue(model.getThresholdDailyJson().get("auxiliarSciBossContact").getAsLong());
            auxiliarBossContact.getCell(5).setCellStyle(this.styleFactory.createBorderedStyle(false, true, true, true, true, true));
            sheet.addMergedRegion(new CellRangeAddress(auxiliarBossContact.getRowNum(), auxiliarBossContact.getRowNum(), 5, 6));
            row1.getCell(7).setCellValue(message[i]);
            row1.getCell(7).setCellStyle(this.styleFactory.createBorderedStyle(false, true, true, true, true, true));
            sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row1.getRowNum() + rowsNum, 7, 8));
            List<Double> valuesDaily;
            List<Double> valuesMonthly = new ArrayList<>();
            if (model.getReportType() == TypeReportSheet.forestFireDataModel) {
                valuesDaily = model.getArrTemperature();
                Map<String, Object> map = JsonObjectToMap.convertJsonObjectToMap(model.getThresholdMonthlyJson());
                List<String> months = new ArrayList<>();
                int count = 0;
                for (String key : map.keySet()) {
                    if (count < 5) {
                        count += 1;
                        continue;
                    }
                    if (key.contains("DataGrade")) {
                        valuesMonthly.add(Double.parseDouble(String.valueOf(map.get(key))));
                        int index = key.indexOf("DataGrade");
                        String month = key.substring(0, index).trim();
                        months.add(month);
                    }
                }

                switch (i) {
                    case 0 :
                        Double thresholdOrange = model.getThresholdDailyJson().get("forestFireThresholdOrange").getAsDouble();
                        Double thresholdRed = model.getThresholdDailyJson().get("forestFireThresholdRed").getAsDouble();
                        setFieldThresholdChart(thresholdOrange, thresholdRed, valuesDaily, row1, model.getArrDate(), "days", "orange");
                        break;
                    case 1 :
                        Double threshold2 = model.getThresholdDailyJson().get("forestFireThresholdRed").getAsDouble();
                        setFieldThresholdChart(0.0, threshold2, valuesDaily, row1, model.getArrDate(), "days", "red");
                        break;
                    case 2 :
                        Double thresholdOrange2 = model.getThresholdMonthlyJson().get("orangeThresholdTemperature").getAsDouble();
                        Double thresholdRed2 = model.getThresholdMonthlyJson().get("redThresholdTemperature").getAsDouble();
                        setFieldThresholdChart(thresholdOrange2, thresholdRed2, valuesMonthly, row1, months, "months", "orange");
                        break;
                    case 3 :
                        Double threshold4 = model.getThresholdMonthlyJson().get("redThresholdTemperature").getAsDouble();
                        setFieldThresholdChart(0.0, threshold4, valuesMonthly, row1, months, "months", "red");
                        break;
                }
            }
            else if (model.getReportType() == TypeReportSheet.massMovementDataModel) {
                valuesDaily = model.getArrPrecipitationPercent();
                Map<String, Object> map = JsonObjectToMap.convertJsonObjectToMap(model.getThresholdMonthlyJson());
                List<String> months = new ArrayList<>();
                int count = 0;
                for (String key : map.keySet()) {
                    if (count < 5) {
                        count += 1;
                        continue;
                    }
                    if (key.contains("DataPercent")) {
                        valuesMonthly.add(Double.parseDouble(String.valueOf(map.get(key))));
                        int index = key.indexOf("DataPercent");
                        String month = key.substring(0, index).trim();
                        months.add(month);
                    }
                }

                switch (i) {
                    case 0 :
                        Double thresholdOrange = model.getThresholdDailyJson().get("precipitationRainPercentOrange").getAsDouble();
                        Double thresholdRed = model.getThresholdDailyJson().get("precipitationRainPercentRed").getAsDouble();
                        setFieldThresholdChart(thresholdOrange, thresholdRed, valuesDaily, row1, model.getArrDate(), "days", "orange");
                        break;
                    case 1 :
                        Double threshold2 = model.getThresholdDailyJson().get("precipitationRainPercentRed").getAsDouble();
                        setFieldThresholdChart(0.0, threshold2, valuesDaily, row1, model.getArrDate(), "days", "red");
                        break;
                    case 2 :
                        Double thresholdOrange2 = model.getThresholdMonthlyJson().get("orangeThresholdPrecipitation").getAsDouble();
                        Double thresholdRed2 = model.getThresholdMonthlyJson().get("redThresholdPrecipitation").getAsDouble();
                        setFieldThresholdChart(thresholdOrange2, thresholdRed2, valuesMonthly, row1, months, "months", "orange");
                        break;
                    case 3 :
                        Double threshold4 = model.getThresholdMonthlyJson().get("redThresholdPrecipitation").getAsDouble();
                        setFieldThresholdChart(0.0, threshold4, valuesMonthly, row1, months, "months", "red");
                        break;
                }
            }
            else if (model.getReportType() == TypeReportSheet.rainShowerDataModel) {
                valuesDaily = model.getArrWindSpeed();
                switch (i) {
                    case 0 :
                        Double thresholdOrange = model.getThresholdDailyJson().get("windThresholdOrange").getAsDouble();
                        Double thresholdRed = model.getThresholdDailyJson().get("windThresholdRed").getAsDouble();
                        setFieldThresholdChart(thresholdOrange, thresholdRed, valuesDaily, row1, model.getArrDate(), "days", "orange");
                        break;
                    case 1 :
                        Double threshold2 = model.getThresholdDailyJson().get("windThresholdRed").getAsDouble();
                        setFieldThresholdChart(0.0, threshold2, valuesDaily, row1, model.getArrDate(), "days", "red");
                        break;
                }
            }
            row += rowsNum + 1;
        }
        return row;
    }

    public void setFieldThresholdChart(Double thresholdOrange, Double thresholdRed, List<Double> values, Row row, List<String> dates, String... type) {
        StringBuilder date = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (type[1].equalsIgnoreCase("orange") && type[0].equalsIgnoreCase("days")) {
                if (values.get(i) >= thresholdOrange && values.get(i) < thresholdRed) {
                    date.append("(").append(DateUtil.subtractDays(dates.get(i), 1)).append(")");
                }
            } else if (type[1].equalsIgnoreCase("red") && type[0].equalsIgnoreCase("days")) {
                if (values.get(i) >= thresholdRed) {
                    date.append("(").append(DateUtil.subtractDays(dates.get(i), 1)).append(")");
                }
            } else if (type[1].equalsIgnoreCase("orange") && type[0].equalsIgnoreCase("months")) {
                if (values.get(i) >= thresholdOrange && values.get(i) < thresholdRed) {
                    date.append("(").append(dates.get(i)).append(")");
                }
            } else if (type[1].equalsIgnoreCase("red") && type[0].equalsIgnoreCase("months")) {
                if (values.get(i) >= thresholdRed) {
                    date.append("(").append(dates.get(i)).append(")");
                }
            }
        }
        row.getCell(3).setCellValue(date.toString());
    }
}