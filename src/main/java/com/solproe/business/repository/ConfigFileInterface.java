package com.solproe.business.repository;

public interface ConfigFileInterface {

    public String getLocationName();

    public void setLocationName(String locationName);

    public double getLatitude();

    public void setLatitude(double latitude);

    public double getLongitude();

    public void setLongitude(double longitude);

    public double getTemperatureForestFileThresholdOrange();

    public void setTemperatureForestFileThresholdOrange(double temperatureForestFileThresholdOrange);

    public double getTemperatureForestFileThresholdRed();

    public void setTemperatureForestFileThresholdRed(double temperatureForestFileThresholdRed);

    public double getPrecipitationMassMovementThresholdOrange();

    public void setPrecipitationMassMovementThresholdOrange(double precipitationMassMovementThresholdOrange);

    public double getPrecipitationMassMovementThresholdRed();

    public void setPrecipitationMassMovementThresholdRed(double precipitationMassMovementThresholdRed);

    public double getPrecipitationOfMassMovementChanceOfRainThresholdOrange();

    public void setPrecipitationOfMassMovementChanceOfRainThresholdOrange(double precipitationOfMassMovementChanceOfRainThresholdOrange);

    public double getPrecipitationOfMassMovementChanceOfRainThresholdRed();

    public void setPrecipitationOfMassMovementChanceOfRainThresholdRed(double precipitationOfMassMovementChanceOfRainThresholdRed);

    public double getWindSpeedStormThresholdOrange();

    public void setWindSpeedStormThresholdOrange(double windSpeedStormThresholdOrange);

    public double getWindSpeedStormThresholdRed();

    public void setWindSpeedStormThresholdRed(double windSpeedStormThresholdRed);

    public int getDaysCount();

    public void setDaysCount(int days);
}
