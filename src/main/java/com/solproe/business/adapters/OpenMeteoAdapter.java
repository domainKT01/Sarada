package com.solproe.business.adapters;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.solproe.business.domain.WeatherNode;
import com.solproe.business.dto.OpenMeteoForecastList;

public class OpenMeteoAdapter {
    private final JsonObject jsObject;


    public OpenMeteoAdapter(JsonObject jsObject) {
        this.jsObject = jsObject;
    }


    public OpenMeteoForecastList setWeatherForecastDto() {
        OpenMeteoForecastList openMeteoForecastList = new OpenMeteoForecastList();
        JsonObject forecast = jsObject.getAsJsonObject("daily");
        JsonArray temp = forecast.getAsJsonArray("temperature_2m_max");
        JsonArray humidity = forecast.getAsJsonArray("relative_humidity_2m_mean");
        JsonArray precipitation = forecast.getAsJsonArray("precipitation_probability_max");
        JsonArray precipitationMm = forecast.getAsJsonArray("precipitation_sum");
        JsonArray date = forecast.getAsJsonArray("time");
        JsonArray wind = forecast.getAsJsonArray("wind_speed_10m_max");
        JsonArray code = forecast.getAsJsonArray("weather_code");
        int count = 0;
        for (JsonElement jsonElement : forecast.getAsJsonArray("time")) {
            WeatherNode weatherNode = new WeatherNode();
            weatherNode.setHumidity(humidity.get(count).getAsDouble());
            weatherNode.setTemp(temp.get(count).getAsDouble());
            weatherNode.setPrecipitation(precipitation.get(count).getAsDouble());
            weatherNode.setDate(date.get(count).getAsString());
            weatherNode.setSpeedWind(wind.get(count).getAsDouble());
            weatherNode.setCode(code.get(count).getAsDouble());
            weatherNode.setPrecipitationMm(precipitationMm.get(count).getAsDouble());
            openMeteoForecastList.addNodeList(weatherNode);
            count++;
        }

        return openMeteoForecastList;
    }
}
