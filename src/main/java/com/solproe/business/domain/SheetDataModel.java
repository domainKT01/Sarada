package com.solproe.business.domain;

public class SheetDataModel {
    private final String sheetName;
    private final String title;
    private final String parameter;

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
}
