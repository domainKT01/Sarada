package com.solproe.service.excel;

import com.google.gson.JsonObject;
import com.solproe.business.domain.SheetDataModel;
import com.solproe.business.domain.WeatherNode;
import com.solproe.business.dto.OpenMeteoForecastList;
import com.solproe.business.repository.ExcelFileGenerator;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

public class ReportExcelGenerator implements ExcelFileGenerator {
    private final ExcelService excelService;
    private List<SheetDataModel> dataModelList;
    private String path;
    private JsonObject configFileThreshold;


    public ReportExcelGenerator(ExcelService excelService) {
        this.excelService = excelService;
    }

    public void generateExcelFile() {
        Workbook workbook = excelService.createWorkbook();
        ExcelSheetGenerator sheetGenerator = new ExcelSheetGenerator(new GenericSheetTemplate());
        sheetGenerator.generateSheets(workbook, this.dataModelList);
        this.excelService.setPath(this.path);
        this.excelService.saveWorkbook(workbook, "Reporte.xlsx");
    }

    @Override
    public void generate(String filePath, OpenMeteoForecastList forecastList) {
        this.path = filePath;
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

        for (WeatherNode weatherNode : forecastList.getNodeList()) {
            forestFireDataModel.addTemperature(weatherNode.getTemp());
            forestFireDataModel.setConfigFileThreshold(this.configFileThreshold);
            forestFireDataModel.addWindSpeed(weatherNode.getSpeedWind());
            forestFireDataModel.addDate(weatherNode.getDate());
            forestFireDataModel.addHumidityPercent(weatherNode.getHumidity());
            forestFireDataModel.addPrecipitationPercent(weatherNode.getPrecipitation());
            forestFireDataModel.addCode(weatherNode.getCode());

            rainShowerDataModel.addTemperature(weatherNode.getTemp());
            rainShowerDataModel.addWindSpeed(weatherNode.getSpeedWind());
            rainShowerDataModel.setConfigFileThreshold(this.configFileThreshold);
            rainShowerDataModel.addCode(weatherNode.getCode());
            rainShowerDataModel.addDate(weatherNode.getDate());
            rainShowerDataModel.addPrecipitationPercent(weatherNode.getPrecipitation());
            rainShowerDataModel.addHumidityPercent(weatherNode.getHumidity());

            massMovementDataModel.addHumidityPercent(weatherNode.getHumidity());
            massMovementDataModel.addCode(weatherNode.getCode());
            massMovementDataModel.addDate(weatherNode.getDate());
            massMovementDataModel.addPrecipitationPercent(weatherNode.getPrecipitation());
            massMovementDataModel.addWindSpeed(weatherNode.getSpeedWind());
            massMovementDataModel.addTemperature(weatherNode.getTemp());
            massMovementDataModel.setConfigFileThreshold(this.configFileThreshold);
        }

        this.dataModelList = new ArrayList<>();
        this.dataModelList.add(forestFireDataModel);
        this.dataModelList.add(massMovementDataModel);
        this.dataModelList.add(rainShowerDataModel);
        this.generateExcelFile();
    }

    @Override
    public void setConfigFile(JsonObject jsonObject) {
        this.configFileThreshold = jsonObject;
    }
}
