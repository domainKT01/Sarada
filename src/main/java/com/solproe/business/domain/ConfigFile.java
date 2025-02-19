package com.solproe.business.domain;

import com.solproe.business.repository.ConfigFileInterface;

public class ConfigFile implements ConfigFileInterface {
    private String locationName;
    private double latitude;
    private double longitude;
    private double temperatureForestFileThresholdOrange;
    private double temperatureForestFileThresholdRed;
    private double precipitationMassMovementThresholdOrange;
    private double precipitationMassMovementThresholdRed;
    private double precipitationOfMassMovementChanceOfRainThresholdOrange;
    private double precipitationOfMassMovementChanceOfRainThresholdRed;
    private double windSpeedStormThresholdOrange;
    private double windSpeedStormThresholdRed;
    private int temperatureThreshold;
    private int countDays;

    // Constructor
    public ConfigFile(
            String locationName,
            double latitude,
            double longitude,

            int numberDays) {
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.countDays = numberDays;
    }

    // Getters y Setters


    @Override
    public String getLocationName() {
        return this.locationName;
    }

    @Override
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public double getLatitude() {
        return this.latitude;
    }

    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public double getLongitude() {
        return this.longitude;
    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public double getTemperatureForestFileThresholdOrange() {
        return 0;
    }

    @Override
    public void setTemperatureForestFileThresholdOrange(double temperatureForestFileThresholdOrange) {

    }

    @Override
    public double getTemperatureForestFileThresholdRed() {
        return 0;
    }

    @Override
    public void setTemperatureForestFileThresholdRed(double temperatureForestFileThresholdRed) {

    }

    @Override
    public double getPrecipitationMassMovementThresholdOrange() {
        return 0;
    }

    @Override
    public void setPrecipitationMassMovementThresholdOrange(double precipitationMassMovementThresholdOrange) {

    }

    @Override
    public double getPrecipitationMassMovementThresholdRed() {
        return 0;
    }

    @Override
    public void setPrecipitationMassMovementThresholdRed(double precipitationMassMovementThresholdRed) {

    }

    @Override
    public double getPrecipitationOfMassMovementChanceOfRainThresholdOrange() {
        return 0;
    }

    @Override
    public void setPrecipitationOfMassMovementChanceOfRainThresholdOrange(double precipitationOfMassMovementChanceOfRainThresholdOrange) {

    }

    @Override
    public double getPrecipitationOfMassMovementChanceOfRainThresholdRed() {
        return 0;
    }

    @Override
    public void setPrecipitationOfMassMovementChanceOfRainThresholdRed(double precipitationOfMassMovementChanceOfRainThresholdRed) {

    }

    @Override
    public double getWindSpeedStormThresholdOrange() {
        return 0;
    }

    @Override
    public void setWindSpeedStormThresholdOrange(double windSpeedStormThresholdOrange) {

    }

    @Override
    public double getWindSpeedStormThresholdRed() {
        return 0;
    }

    @Override
    public void setWindSpeedStormThresholdRed(double windSpeedStormThresholdRed) {

    }

    @Override
    public int getDaysCount() {
        return this.countDays;
    }

    @Override
    public void setDaysCount(int days) {
        this.countDays = days;
    }
}

