package com.solproe.business.usecase;

import com.google.gson.JsonObject;
import com.solproe.business.gateway.ApiCommandInterface;
import com.solproe.business.gateway.RequestInterface;
import com.solproe.service.APIs.ApiCommandInvoker;
import com.solproe.service.APIs.ApiService;
import com.solproe.service.APIs.GetRequestApi;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenerateReportUseCaseTest implements RequestInterface {


    @Test
    public void report() {
        ApiCommandInvoker apiCommandInvoker = new ApiCommandInvoker();
        GenerateReportUseCase generateReportUseCase = new GenerateReportUseCase();
        ApiService apiService = new ApiService(apiCommandInvoker, generateReportUseCase);
        apiCommandInvoker.setRequestInterface(apiService);
        ApiCommandInterface apiCommandInterface = new GetRequestApi("https://api.open-meteo.com/v1/forecast?latitude=10.4631&longitude=-73.2532&daily=temperature_2m_max,weather_code,wind_speed_10m_max,precipitation_probability_max,relative_humidity_2m_mean&forecast_days=14", apiService);
        apiService.setApiCommandInterface(apiCommandInterface);
        generateReportUseCase.setRequestInterface(apiService);
        generateReportUseCase.generateRequestApi();
    }

    @Override
    public void doRequest(String baseUrl) {

    }

    @Override
    public void successResponse(JsonObject jsonObject) {
        System.out.println("success test generate report use case");
    }

    @Override
    public void failedResponse(Response response) {
        System.out.println("failed test generate report use case");
    }
}