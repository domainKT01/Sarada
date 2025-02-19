package com.solproe.business.adapters;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.solproe.business.domain.WeatherNode;
import com.solproe.business.dto.OpenMeteoForecastList;

public class OpenMeteoAdapter {

    private JsonObject jsObject;

    public OpenMeteoAdapter(JsonObject jsObject) {
        this.jsObject = jsObject;
    }


    public OpenMeteoForecastList setWeatherForecast5Dto() {
        OpenMeteoForecastList openMeteoForecastList = new OpenMeteoForecastList();
        JsonObject forecast = jsObject.getAsJsonObject("daily");
        JsonArray temp = forecast.getAsJsonArray("temperature_2m_max");
        JsonArray humidity = forecast.getAsJsonArray("relative_humidity_2m_max");
        JsonArray precipitation = forecast.getAsJsonArray("precipitation_probability_mean");
        JsonArray date = forecast.getAsJsonArray("time");
        //JsonArray windSpeed = forecast.getAsJsonArray("relative_humidity_2m_max");
        int count = 0;
        for (JsonElement jsonElement : forecast.getAsJsonArray("time")) {
            WeatherNode weatherNode = new WeatherNode();
            weatherNode.setHumidity(humidity.get(count).getAsDouble());
            weatherNode.setTemp(temp.get(count).getAsDouble());
            weatherNode.setPrecipitation(precipitation.get(count).getAsDouble());
            weatherNode.setDate(date.get(count).getAsString());

            openMeteoForecastList.addNodeList(weatherNode);
            count++;
        }

        return openMeteoForecastList;
    }
}
