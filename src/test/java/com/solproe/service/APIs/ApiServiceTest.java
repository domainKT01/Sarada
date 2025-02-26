package com.solproe.service.APIs;

import com.google.gson.JsonObject;
import com.solproe.business.gateway.ApiCommandInterface;
import com.solproe.business.gateway.RequestInterface;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiServiceTest implements RequestInterface {


    @Test
    public void createRequest() {
        ApiCommandInvoker apiCommandInvoker = new ApiCommandInvoker();
        apiCommandInvoker.setRequestInterface(this);
        ApiCommandInterface apiCommandInterface = new GetRequestApi("https://api.open-meteo.com/v1/forecast?latitude=10.4631&longitude=-73.2532&daily=temperature_2m_max,weather_code,wind_speed_10m_max,precipitation_probability_max&forecast_days=14", this);
        ApiService apiService = new ApiService(apiCommandInvoker, this);
        apiService.setApiCommandInterface(apiCommandInterface);
        apiService.doRequest("");
    }

    @Override
    public void doRequest(String baseUrl) {

    }

    @Override
    public void successResponse(JsonObject jsonObject) {

    }

    @Override
    public void failedResponse(Response response) {

    }
}