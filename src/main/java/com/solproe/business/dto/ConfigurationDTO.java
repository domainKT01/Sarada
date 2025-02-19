package com.solproe.business.dto;

import com.solproe.business.repository.ConfigFileInterface;

public class ConfigurationDTO implements ConfigFileInterface {
    private String locationName;
    private double latitude;
    private double longitude;
    private double temperatureThreshold;
    private int daysCount;


    //getter and setter
    @Override
    public String getLocationName() {
        return locationName;
    }


    @Override
    public double getLatitude() {
        return latitude;
    }


    @Override
    public double getLongitude() {
        return longitude;
    }


    @Override
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }


    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
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
        return daysCount;
    }

    @Override
    public void setDaysCount(int days) {
        this.daysCount = days;
    }
}
