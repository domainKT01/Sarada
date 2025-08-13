package com.solproe.business.usecase;

import com.google.gson.JsonObject;
import com.solproe.business.adapters.OpenMeteoAdapterJson;
import com.solproe.business.dto.OpenMeteoForecastList;
import com.solproe.business.gateway.RequestInterface;
import com.solproe.business.gateway.WhatsappService;
import com.solproe.business.repository.ExcelFileGenerator;
import com.solproe.business.repository.ReadConfigFile;
import com.solproe.service.config.ConfigPropertiesGenerator;
import com.solproe.util.logging.ErrorLogger;
import okhttp3.Response;
import java.nio.file.Path;
import java.time.LocalDate;

public class GenerateReportUseCase implements RequestInterface {
    private RequestInterface requestInterface;
    private ExcelFileGenerator excelFileGenerator;
    private ReadConfigFile readConfigFile;
    private final ConfigPropertiesGenerator generatePaths = new ConfigPropertiesGenerator();
    private WhatsappService whatsappService;


    public void setRequestInterface(RequestInterface requestInterface) {
        this.requestInterface = requestInterface;
    }

    public void setExcelFileGenerator(ExcelFileGenerator excelFileGenerator) {
        this.excelFileGenerator = excelFileGenerator;
    }

    public void setReadConfigFile(ReadConfigFile readConfigFile) {
        this.readConfigFile = readConfigFile;
    }

    public void setWhatsappService(WhatsappService whatsappService){
        this.whatsappService = whatsappService;
    }

    public boolean generateRequestApi() {
        try {
            this.requestInterface.doRequest("https://api.open-meteo.com/v1/forecast?latitude=6.7187&longitude=-75.9073&daily=temperature_2m_max,weather_code,wind_speed_10m_max,precipitation_probability_max,relative_humidity_2m_mean,precipitation_sum&forecast_days=14");
            return true;
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doRequest(String baseUrl) {

    }

    @Override
    public void successResponse(JsonObject jsonObject) {
        try {
            //generate linked list openMeteoAdapter
            OpenMeteoAdapterJson openMeteoAdapterJson = new OpenMeteoAdapterJson(jsonObject);
            OpenMeteoForecastList openMeteoForecastList = openMeteoAdapterJson.setWeatherForecastDto();
            ReadConfigFileUseCase readConfigFileUseCase = new ReadConfigFileUseCase();
            readConfigFileUseCase.setReadInterface(this.readConfigFile);
            String[] dirName = {
                    "Sarada"
            };
            JsonObject configFileJson = null;
            JsonObject monthlyConfigFile = null;
            JsonObject listCodeFile = null;
            JsonObject record = null;
            try {
                ConfigPropertiesGenerator configPropertiesGenerator = new ConfigPropertiesGenerator("threshold.json", "Sarada");
                Path path = configPropertiesGenerator.getAppConfigPath();
                System.out.println("threshold path: " + path);
                configFileJson = readConfigFileUseCase.readConfigFile(path);
            } catch (Exception e) {
                System.out.println("threshold.json error: " + e.getMessage());
                throw new RuntimeException(e);
            }

            try {
                monthlyConfigFile = readConfigFileUseCase.readConfigFile(new ConfigPropertiesGenerator("monthlyThreshold.json", dirName)
                        .getAppConfigPath());
            } catch (Exception e) {
                System.out.println("monthlyThreshold.json error");
                throw new RuntimeException(e);
            }

            try {
                listCodeFile = readConfigFileUseCase.readConfigFile(new ConfigPropertiesGenerator("listCode.json", dirName)
                        .getAppConfigPath());
            }
            catch (Exception e) {
                System.out.println("listCode.json error");
                throw new RuntimeException(e);
            }

            try {
                record = readConfigFileUseCase.readConfigFile(new ConfigPropertiesGenerator("recordThreshold.json", dirName)
                        .getAppConfigPath());
            } catch (Exception e) {
                System.out.println("recordThreshold.json error");
                throw new RuntimeException(e);
            }

            this.whatsappService.setJsonResource(configFileJson, record);
            this.whatsappService.sendMessage();

            LocalDate localDate = LocalDate.now();
            int year = localDate.getYear();
            int month = localDate.getMonthValue();
            int day = localDate.getDayOfMonth();
            this.generatePaths.setDirName(dirName);
            this.generatePaths.setFilename("report" + "-" + year + "-" + month + "-" + day + ".xlsx");
            Path path = this.generatePaths.getAppConfigPath();
            this.excelFileGenerator.setConfigFile(configFileJson, monthlyConfigFile, listCodeFile);
            this.excelFileGenerator.generate(path, openMeteoForecastList);
        }
        catch (RuntimeException e) {
            System.out.println("use case exception: " + e.getMessage());
            ErrorLogger.log(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void failedResponse(Response response) {
    }
}
