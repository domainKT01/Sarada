package com.solproe.business.domain;

public class WeatherNode {

    private Double temp;
    private Double speedWind;
    private String typeGrades;
    private Double humidity;
    private Double precipitationProbability;
    private String date;
    private Double code;

    private WeatherNode nextNode = null;


    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getSpeedWind() {
        return speedWind;
    }

    public void setSpeedWind(Double speedWind) {
        this.speedWind = speedWind;
    }

    public String getTypeGrades() {
        return typeGrades;
    }

    public void setTypeGrades(String typeGrades) {
        this.typeGrades = typeGrades;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getPrecipitation() {
        return precipitationProbability;
    }

    public void setPrecipitation(Double precipitation) {
        this.precipitationProbability = precipitation;
    }

    public void setNextNode(WeatherNode nextNode) {
        this.nextNode = nextNode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getCode() {
        return code;
    }

    public void setCode(Double code) {
        this.code = code;
    }
}
