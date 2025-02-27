package com.solproe.service.excel;

import com.solproe.business.domain.SheetDataModel;
import com.solproe.business.domain.WeatherNode;
import com.solproe.business.dto.CellRangeDTO;
import com.solproe.business.dto.OpenMeteoForecastList;
import com.solproe.service.excel.graphics.ExcelGenerateGraphics;
import com.solproe.service.excel.graphics.LineChartGenerator;
import com.solproe.business.repository.ExcelFileGenerator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;

public class ReportExcelGenerator implements ExcelFileGenerator {
    private final ExcelService excelService;
    private List<SheetDataModel> dataModelList;


    public ReportExcelGenerator(ExcelService excelService) {
        this.excelService = excelService;
    }

    public void generateExcelFile() {
        Workbook workbook = excelService.createWorkbook();
        ExcelSheetGenerator sheetGenerator = new ExcelSheetGenerator(new GenericSheetTemplate());
        sheetGenerator.generateSheets(workbook, this.dataModelList);
        this.excelService.saveWorkbook(workbook, "Reporte.xlsx");
    }

    @Override
    public void generate(String filePath, OpenMeteoForecastList forecastList) {
        SheetDataModel forestFireDataModel = new SheetDataModel(
                "INCENDIOS FORESTALES",
                "MONITOREO DE PARÁMETROS HIDROMETEOROLÓGICOS PARA LA ALERTA DE OCURRENCIA DE INCENDIOS FORESTALES ",
                "TEMPERATURA PROMEDIO DIARIA EN °C"
        );
        SheetDataModel massMovementDataModel = new SheetDataModel(
                "MOVIMIENTOS EN MASA",
                "MONITOREO DE PARÁMETROS HIDROMETEOROLÓGICOS PARA LA ALERTA DE OCURRENCIA DE MOVIMIENTOS EN MASA",
                "PRECIPITACIÓN PROMEDIO DIARIA EN mm y PROBABILIDAD DE PRECIPITACIÓN EN %"
        );
        SheetDataModel rainShowerDataModel = new SheetDataModel(
                "VENDAVALES",
                "MONITOREO DE PARÁMETROS HIDROMETEOROLÓGICOS PARA LA ALERTA DE OCURRENCIA DE VENDAVALES",
                "VELOCIDAD DEL VIENTO PROMEDIO DIARIA EN Km/h"
        );

        this.dataModelList = new ArrayList<>();
        this.dataModelList.add(forestFireDataModel);
        this.dataModelList.add(massMovementDataModel);
        this.dataModelList.add(rainShowerDataModel);
        this.generateExcelFile();
    }
}
