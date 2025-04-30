package com.solproe.business.dto;

import java.util.ArrayList;
import java.util.List;

public class MonthlyThresholdInputModel {
    private double orangeTemperatureThreshold;
    private double redTemperatureThreshold;
    private double orangePrecipitationThreshold;
    private double redPrecipitationThreshold;
    private double yellowPrecipitationThreshold;
    private List<MonthlyData> monthlyData = new ArrayList<>();

    public double getOrangeTemperatureThreshold() {
        return orangeTemperatureThreshold;
    }

    public void setOrangeTemperatureThreshold(double orangeTemperatureThreshold) {
        this.orangeTemperatureThreshold = orangeTemperatureThreshold;
    }

    public double getRedTemperatureThreshold() {
        return redTemperatureThreshold;
    }

    public void setRedTemperatureThreshold(double redTemperatureThreshold) {
        this.redTemperatureThreshold = redTemperatureThreshold;
    }

    public double getOrangePrecipitationThreshold() {
        return orangePrecipitationThreshold;
    }

    public void setOrangePrecipitationThreshold(double orangePrecipitationThreshold) {
        this.orangePrecipitationThreshold = orangePrecipitationThreshold;
    }

    public double getRedPrecipitationThreshold() {
        return redPrecipitationThreshold;
    }

    public void setRedPrecipitationThreshold(double redPrecipitationThreshold) {
        this.redPrecipitationThreshold = redPrecipitationThreshold;
    }

    public double getYellowPrecipitationThreshold() {
        return yellowPrecipitationThreshold;
    }

    public void setYellowPrecipitationThreshold(double yellowPrecipitationThreshold) {
        this.yellowPrecipitationThreshold = yellowPrecipitationThreshold;
    }

    public List<MonthlyData> getMonthlyData() {
        return monthlyData;
    }

    public void setMonthlyData(List<MonthlyData> monthlyData) {
        this.monthlyData = monthlyData;
    }
}
