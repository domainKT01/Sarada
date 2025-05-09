package com.solproe.service.excel;

import com.google.gson.JsonObject;
import com.solproe.business.domain.SheetDataModel;
import com.solproe.business.domain.WeatherNode;
import com.solproe.business.dto.OpenMeteoForecastList;
import com.solproe.business.repository.ExcelFileGenerator;
import com.solproe.service.excel.sheets.GenericSheetTemplate;
import org.apache.poi.ss.usermodel.Workbook;
import java.util.ArrayList;
import java.util.List;

public class ReportExcelGenerator implements ExcelFileGenerator {
    private final ExcelService excelService;
    private List<SheetDataModel> dataModelList;
    private String path;
    private JsonObject[] configFileThreshold;


    public ReportExcelGenerator(ExcelService excelService) {
        this.excelService = excelService;
    }

    public void generateExcelFile() {
        Workbook workbook = excelService.createWorkbook();
        ExcelSheetGenerator sheetGenerator = new ExcelSheetGenerator(new GenericSheetTemplate());
        sheetGenerator.generateSheets(workbook, this.dataModelList);
        this.excelService.setPath(this.path);
        this.excelService.saveWorkbook(workbook, "reporte).xlsx");
    }

    @Override
    public void generate(String filePath, OpenMeteoForecastList forecastList) {
        this.path = filePath;
        try {
            SheetDataModel forestFireDataModel = new SheetDataModel(
                    "INCENDIOS FORESTALES",
                    "MONITOREO DE PARÁMETROS HIDROMETEOROLÓGICOS PARA LA ALERTA DE OCURRENCIA DE INCENDIOS FORESTALES ",
                    "TEMPERATURA PROMEDIO DIARIA EN °C"
            );
            forestFireDataModel.setReportType(TypeReportSheet.forestFireDataModel);

            SheetDataModel massMovementDataModel = new SheetDataModel(
                    "MOVIMIENTOS EN MASA",
                    "MONITOREO DE PARÁMETROS HIDROMETEOROLÓGICOS PARA LA ALERTA DE OCURRENCIA DE MOVIMIENTOS EN MASA",
                    "PRECIPITACIÓN PROMEDIO DIARIA EN mm y PROBABILIDAD DE PRECIPITACIÓN EN %"
            );
            massMovementDataModel.setReportType(TypeReportSheet.massMovementDataModel);

            SheetDataModel rainShowerDataModel = new SheetDataModel(
                    "VENDAVALES",
                    "MONITOREO DE PARÁMETROS HIDROMETEOROLÓGICOS PARA LA ALERTA DE OCURRENCIA DE VENDAVALES",
                    "VELOCIDAD DEL VIENTO PROMEDIO DIARIA EN Km/h"
            );
            rainShowerDataModel.setReportType(TypeReportSheet.rainShowerDataModel);

            SheetDataModel ceraunicDataModel = new SheetDataModel(
                    "CERAUNICO",
                    "MONITOREO DE PARÁMETROS HIDROMETEOROLÓGICOS PARA LA ALERTA DE OCURRENCIA DE FENÓMENOS CERÁUNICOS",
                    "ESTADO DEL CLIMA ESPERADO"
            );
            ceraunicDataModel.setReportType(TypeReportSheet.ceraunic);

            forestFireDataModel.setConfigFileThreshold(this.configFileThreshold);
            massMovementDataModel.setConfigFileThreshold(this.configFileThreshold);
            rainShowerDataModel.setConfigFileThreshold(this.configFileThreshold);
            ceraunicDataModel.setConfigFileThreshold(this.configFileThreshold);

            for (WeatherNode weatherNode : forecastList.getNodeList()) {
                forestFireDataModel.addTemperature(weatherNode.getTemp());
                forestFireDataModel.addWindSpeed(weatherNode.getSpeedWind());
                forestFireDataModel.addDate(weatherNode.getDate());
                forestFireDataModel.addHumidityPercent(weatherNode.getHumidity());
                forestFireDataModel.addPrecipitationPercent(weatherNode.getPrecipitation());
                forestFireDataModel.addCode(weatherNode.getCode());
                forestFireDataModel.addPrecipitationMm(weatherNode.getPrecipitationMm());

                rainShowerDataModel.addTemperature(weatherNode.getTemp());
                rainShowerDataModel.addWindSpeed(weatherNode.getSpeedWind());
                rainShowerDataModel.addCode(weatherNode.getCode());
                rainShowerDataModel.addDate(weatherNode.getDate());
                rainShowerDataModel.addPrecipitationPercent(weatherNode.getPrecipitation());
                rainShowerDataModel.addHumidityPercent(weatherNode.getHumidity());
                rainShowerDataModel.addPrecipitationMm(weatherNode.getPrecipitationMm());

                massMovementDataModel.addHumidityPercent(weatherNode.getHumidity());
                massMovementDataModel.addCode(weatherNode.getCode());
                massMovementDataModel.addDate(weatherNode.getDate());
                massMovementDataModel.addPrecipitationPercent(weatherNode.getPrecipitation());
                massMovementDataModel.addWindSpeed(weatherNode.getSpeedWind());
                massMovementDataModel.addTemperature(weatherNode.getTemp());
                massMovementDataModel.addPrecipitationMm(weatherNode.getPrecipitationMm());

                ceraunicDataModel.addHumidityPercent(weatherNode.getHumidity());
                ceraunicDataModel.addCode(weatherNode.getCode());
                ceraunicDataModel.addDate(weatherNode.getDate());
                ceraunicDataModel.addPrecipitationPercent(weatherNode.getPrecipitation());
                ceraunicDataModel.addWindSpeed(weatherNode.getSpeedWind());
                ceraunicDataModel.addTemperature(weatherNode.getTemp());
                ceraunicDataModel.addPrecipitationMm(weatherNode.getPrecipitationMm());
            }

            this.dataModelList = new ArrayList<>();
            this.dataModelList.add(forestFireDataModel);
            this.dataModelList.add(massMovementDataModel);
            this.dataModelList.add(rainShowerDataModel);
            this.dataModelList.add(ceraunicDataModel);
            this.generateExcelFile();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("report generator exception: " + e.getMessage());
        }

    }

    @Override
    public void setConfigFile(JsonObject... jsonObject) {
        this.configFileThreshold = jsonObject;
    }
}
