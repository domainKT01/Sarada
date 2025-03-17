package com.solproe.business.dto;

import com.google.gson.JsonObject;
import com.solproe.business.domain.WeatherNode;

import java.util.ArrayList;
import java.util.List;

public class OpenMeteoForecastList {
    private final List<WeatherNode> nodeList = new ArrayList<>();
    private JsonObject monthlyForecast = new JsonObject();


    public void addNodeList(WeatherNode weatherNode) {
        this.nodeList.add(weatherNode);
    }

    public List<WeatherNode> getNodeList() {
        return nodeList;
    }

    public void setMonthlyForecast(JsonObject jsonObject) {
        this.monthlyForecast = jsonObject;
    }

    public JsonObject getMonthlyForecast() {
        return this.monthlyForecast;
    }
}
