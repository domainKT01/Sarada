package com.solproe.business.usecase;

import com.google.gson.JsonObject;
import com.solproe.business.adapters.OpenMeteoAdapter;
import com.solproe.business.dto.OpenMeteoForecastList;
import com.solproe.business.gateway.RequestInterface;
import com.solproe.business.repository.ExcelFileGenerator;
import okhttp3.Response;

import java.io.IOException;

public class GenerateReportUseCase implements RequestInterface {
    private RequestInterface requestInterface;
    private ExcelFileGenerator excelFileGenerator;



    public void setRequestInterface(RequestInterface requestInterface) {
        this.requestInterface = requestInterface;
    }

    public void setExcelFileGenerator(ExcelFileGenerator excelFileGenerator) {
        this.excelFileGenerator = excelFileGenerator;
    }


    public void generateRequestApi() {
        this.requestInterface.doRequest("https://api.open-meteo.com/v1/forecast?latitude=10.4631&longitude=-73.2532&daily=temperature_2m_max,weather_code,wind_speed_10m_max,precipitation_probability_max,relative_humidity_2m&forecast_days=14");
    }

    @Override
    public void doRequest(String baseUrl) {

    }

    @Override
    public void successResponse(JsonObject jsonObject) {
        try {
            //generate linked list openMeteoAdapter
            OpenMeteoAdapter openMeteoAdapter = new OpenMeteoAdapter(jsonObject);
            OpenMeteoForecastList openMeteoForecastList = openMeteoAdapter.setWeatherForecastDto();
            System.out.println("success use case");
        }
        catch (Exception e) {
            System.out.println("use case exception: " + e.getMessage());
        }
    }

    @Override
    public void failedResponse(Response response) {

    }
}
