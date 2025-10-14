package com.solproe.business.usecase;

import com.google.gson.JsonObject;
import com.solproe.business.adapters.OpenMeteoAdapterJson;
import com.solproe.business.dto.OpenMeteoForecastList;
import com.solproe.business.dto.PathDto;
import com.solproe.business.gateway.RequestInterface;
import com.solproe.business.gateway.WhatsappService;
import com.solproe.business.repository.ExcelFileGenerator;
import com.solproe.business.repository.ReadConfigFile;
import com.solproe.service.config.ConfigPropertiesGenerator;
import com.solproe.util.logging.ErrorLogger;
import okhttp3.Response;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class GenerateReportUseCase implements RequestInterface {
    private RequestInterface requestInterface;
    private ExcelFileGenerator excelFileGenerator;
    private ReadConfigFile readConfigFile;
    private final ConfigPropertiesGenerator generatePaths = new ConfigPropertiesGenerator();
    private WhatsappService whatsappService;
    private JsonObject confProject;
    private JsonObject monthlyConfigFile;
    private JsonObject codeListFile;
    private JsonObject record;


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
        float lat = 0;
        float lon = 0;
        String[] dirName = null;
        try {
            ReadConfigFileUseCase readConfigFileUseCase = new ReadConfigFileUseCase();
            readConfigFileUseCase.setReadInterface(this.readConfigFile);

            dirName = new String[] {
                    "Sarada"
            };

            ConfigPropertiesGenerator configPropertiesGenerator = new ConfigPropertiesGenerator();
            configPropertiesGenerator.setDirName(dirName);

            //threshold daily
            configPropertiesGenerator.setFilename("threshold.json");
            this.confProject = readConfigFileUseCase.readConfigFile(configPropertiesGenerator.getAppConfigPath());

            //threshold monthly
            configPropertiesGenerator.setFilename("monthlyThreshold.json");
            this.monthlyConfigFile = readConfigFileUseCase.readConfigFile(configPropertiesGenerator.getAppConfigPath());

            //code list
            configPropertiesGenerator.setFilename("listCode.json");
            codeListFile = readConfigFileUseCase.readConfigFile(configPropertiesGenerator.getAppConfigPath());

            //records
            configPropertiesGenerator.setFilename("recordThreshold.json");
            record = readConfigFileUseCase.readConfigFile(configPropertiesGenerator.getAppConfigPath());
            lat = this.confProject.get("location").getAsJsonObject().get("lat").getAsFloat();
            lon = this.confProject.get("location").getAsJsonObject().get("lng").getAsFloat();

            //dashboard
            configPropertiesGenerator.setFilename("dashboard.json");
            JsonObject dashboard = readConfigFileUseCase.readConfigFile(configPropertiesGenerator.getAppConfigPath());
            this.whatsappService.setToken(dashboard.get("tokenWhatsapp").getAsString());

            this.requestInterface.doRequest("https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&daily=temperature_2m_max,weather_code,wind_speed_10m_max,precipitation_probability_max,relative_humidity_2m_mean,precipitation_sum&forecast_days=14");
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

        OpenMeteoAdapterJson openMeteoAdapterJson = new OpenMeteoAdapterJson(jsonObject);
        //generate linked list openMeteoAdapter
        OpenMeteoForecastList openMeteoForecastList = openMeteoAdapterJson.setWeatherForecastDto();
        Path path = null;

        try {
            LocalDate localDate = LocalDate.now();
            int year = localDate.getYear();
            int month = localDate.getMonthValue();
            int day = localDate.getDayOfMonth();
            path = Paths.get(this.confProject.get("path").getAsString());
            System.out.println("point before resolve path, actual path: " + path);
            path = path.resolve("report" + "-" + year + "-" + month + "-" + day + ".xlsx");
            System.out.println("######" + " new path: " + path);
            try {
                PathDto.init(path);
            }
            catch (Exception e) {
                System.out.println("path was initialized before###");
            }

            this.excelFileGenerator.setConfigFile(this.confProject, monthlyConfigFile, codeListFile);
            this.excelFileGenerator.generate(path, openMeteoForecastList);
        }
        catch (Exception e) {
            System.out.println("use case error set path... new path: " + path);
            ErrorLogger.log(e);
            throw  new RuntimeException(e);
        }

        try {
            this.whatsappService.setJsonResource(this.confProject, record);
            this.whatsappService.sendMessage();
        }
        catch (Exception e) {
            ErrorLogger.log(e);
        }
    }

    @Override
    public void failedResponse(Response response) {
    }
}
