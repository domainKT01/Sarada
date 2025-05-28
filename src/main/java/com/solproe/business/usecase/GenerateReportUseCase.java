package com.solproe.business.usecase;

import com.google.gson.JsonObject;
import com.solproe.business.adapters.OpenMeteoAdapterJson;
import com.solproe.business.dto.OpenMeteoForecastList;
import com.solproe.business.gateway.RequestInterface;
import com.solproe.business.repository.ExcelFileGenerator;
import com.solproe.business.repository.ReadConfigFile;
import com.solproe.service.config.ConfigPropertiesGenerator;
import com.solproe.util.OsInfo;
import okhttp3.Response;

import java.io.File;

public class GenerateReportUseCase implements RequestInterface {
    private RequestInterface requestInterface;
    private ExcelFileGenerator excelFileGenerator;
    private JsonObject configFileJson;
    private JsonObject monthlyConfigFile;
    private JsonObject listCodeFile;
    private ReadConfigFile readConfigFile;
    private String path;


    public void setRequestInterface(RequestInterface requestInterface) {
        this.requestInterface = requestInterface;
    }

    public void setExcelFileGenerator(ExcelFileGenerator excelFileGenerator) {
        this.excelFileGenerator = excelFileGenerator;
    }

    public void setReadConfigFile(ReadConfigFile readConfigFile) {
        this.readConfigFile = readConfigFile;
    }


    public void generateRequestApi() {
        this.requestInterface.doRequest("https://api.open-meteo.com/v1/forecast?latitude=6.7187&longitude=-75.9073&daily=temperature_2m_max,weather_code,wind_speed_10m_max,precipitation_probability_max,relative_humidity_2m_mean,precipitation_sum&forecast_days=14");
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
            this.configFileJson = readConfigFileUseCase.readConfigFile(new ConfigPropertiesGenerator("threshold.json",
                    "Sarada").getAppConfigPath());
            this.monthlyConfigFile = readConfigFileUseCase.readConfigFile(new ConfigPropertiesGenerator("monthlyThreshold.json", "Sarada")
                    .getAppConfigPath());

            OsInfo osInfo = new OsInfo();
            if (osInfo.getOsName().equalsIgnoreCase("windows")) {
                this.path = "C:\\Users\\Public\\Documents\\Reports";
            } else if (osInfo.getOsName().equalsIgnoreCase("linux")) {
                this.path = "/home/" + osInfo.getUserName() + "/Reports";
            }
            File folder = new File(this.path);
            if (!folder.exists()) {
                folder.mkdir();
            }
            this.listCodeFile = readConfigFileUseCase.readConfigFile(new ConfigPropertiesGenerator("listCode.json", "Sarada")
                    .getAppConfigPath());
            String path = this.path + openMeteoForecastList.getNodeList().getFirst().getDate() + "(";
            this.excelFileGenerator.setConfigFile(this.configFileJson, this.monthlyConfigFile, this.listCodeFile);
            this.excelFileGenerator.generate(path, openMeteoForecastList);
        }
        catch (Exception e) {
            System.out.println("use case exception: " + e.getMessage());
        }
    }

    @Override
    public void failedResponse(Response response) {
    }
}
