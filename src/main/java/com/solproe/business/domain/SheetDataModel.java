package com.solproe.business.domain;

import java.util.ArrayList;

public class SheetDataModel {
    private final String sheetName;
    private final String title;
    private final String parameter;
    private final ArrayList<Double> arrTemperature = new ArrayList<>();
    private final ArrayList<Double> arrWindSpeed = new ArrayList<>();
    private final ArrayList<String> arrDate = new ArrayList<>();

    public SheetDataModel(String sheetName, String title, String parameter) {
        this.sheetName = sheetName;
        this.title = title;
        this.parameter = parameter;
    }

    public String getSheetName() {
        return sheetName;
    }

    public String getTitle() {
        return title;
    }

    public String getParameter() {
        return parameter;
    }

    public void addTemperature(double temperature) {
        this.arrTemperature.add(temperature);
    }

    public ArrayList<Double> getArrTemperature() {
        return this.arrTemperature;
    }

    public ArrayList<Double> getArrWindSpeed() {
        return arrWindSpeed;
    }

    public void addWindSpeed(double windSpeed) {
        this.arrWindSpeed.add(windSpeed);
    }

    public void setArrDate(String date) {
        this.arrDate.add(date);
    }

    public ArrayList<String> getArrDate() {
        return this.arrDate;
    }
}
