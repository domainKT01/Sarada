package com.solproe.business.service.config;

public class ConfigFileBuilder {
    private String locationName;
    private double latitude;
    private double longitude;
    private double temperatureThreshold;
    private int numberDays;

    public ConfigFileBuilder setLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public ConfigFileBuilder setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public ConfigFileBuilder setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public ConfigFileBuilder setTemperatureThreshold(double threshold) {
        this.temperatureThreshold = threshold;
        return this;
    }

    public int getNumberDays() {
        return numberDays;
    }

    public void setNumberDays(int numberDays) {
        this.numberDays = numberDays;
    }

}

