package com.solproe.business.domain;

import com.google.gson.JsonObject;
import com.solproe.service.excel.TypeReportSheet;

import java.util.ArrayList;
import java.util.Arrays;

public class SheetDataModel {
    private final String sheetName;
    private final String title;
    private final String parameter;
    private final ArrayList<Double> arrTemperature = new ArrayList<>();
    private final ArrayList<Double> arrWindSpeed = new ArrayList<>();
    private final ArrayList<String> arrDate = new ArrayList<>();
    private final ArrayList<Double> arrPrecipitationPercent = new ArrayList<>();
    private final ArrayList<Double> arrPrecipitationMm = new ArrayList<>();
    private final ArrayList<Double> arrHumidityPercent = new ArrayList<>();
    private final ArrayList<Double> arrCode = new ArrayList<>();
    private JsonObject[] configFileThreshold;
    private TypeReportSheet reportType;
    private int startRow;
    private int[] parameterCol;
    private String maxThreshold;

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

    public void addDate(String date) {
        this.arrDate.add(date);
    }

    public ArrayList<String> getArrDate() {
        return this.arrDate;
    }

    public void setConfigFileThreshold(JsonObject... data) {
        this.configFileThreshold = data;
    }

    public JsonObject[] getConfigFileThreshold() {
        return this.configFileThreshold;
    }

    public void addPrecipitationPercent(double precipitationPercent) {
        this.arrPrecipitationPercent.add(precipitationPercent);
    }

    public ArrayList<Double> getArrPrecipitationPercent() {
        return this.arrPrecipitationPercent;
    }

    public void addHumidityPercent(double humidityPercent) {
        this.arrHumidityPercent.add(humidityPercent);
    }

    public ArrayList<Double> getArrHumidityPercent() {
        return this.arrHumidityPercent;
    }

    public void addCode(double code) {
        this.arrCode.add(code);
    }

    public ArrayList<Double> getArrCode() {
        return this.arrCode;
    }

    public void setReportType(TypeReportSheet reportType) {
        this.reportType = reportType;
    }

    public TypeReportSheet getReportType() {
        return this.reportType;
    }

    public void addPrecipitationMm(double precipitationMm) {
        this.arrPrecipitationMm.add(precipitationMm);
    }

    public ArrayList<Double> getArrPrecipitationMm() {
        return this.arrPrecipitationMm;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int[] getParameterCol() {
        return parameterCol;
    }

    public void setParameterCol(int[] parameterCol) {
        this.parameterCol = parameterCol;
    }

    public JsonObject getThresholdDailyJson() {
        return this.configFileThreshold[0];
    }

    public JsonObject getThresholdMonthlyJson() {
        return this.configFileThreshold[1];
    }

    public JsonObject getThresholdCodeList() {
        return this.configFileThreshold[2];
    }

    public void setMaxThreshold(String maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public String getMaxThreshold() {
        return this.maxThreshold;
    }
}
