package com.solproe.business.service;

import com.google.gson.JsonObject;
import com.solproe.business.gateway.RequestInterface;
import okhttp3.Response;

public class InitController implements RequestInterface {

    void getData() {

        String baseUrl = "https://api.open-meteo.com/v1/forecast?latitude=10.46314&longitude=-73.25322&daily=temperature_2m_max&forecast_days=5&daily=precipitation_probability_mean&daily=relative_humidity_2m_max";

    }

    @Override
    public void doRequest(String baseUrl) {

    }

    @Override
    public void successResponse(JsonObject jsonObject) {
        System.out.println("fuck you!!");
    }

    @Override
    public void failedResponse(Response response) {

    }
}
