package com.solproe.business.dto;

import javafx.scene.control.TextField;

public class MonthlyData {
    private final String month;
    private final TextField gradeField;
    private final TextField percentField;

    public MonthlyData(String month, TextField gradeField, TextField percentField) {
        this.month = month;
        this.gradeField = gradeField;
        this.percentField = percentField;
    }

    public double getGrade() {
        return Double.parseDouble(gradeField.getText());
    }

    public double getPercent() {
        return Double.parseDouble(percentField.getText());
    }

    public String getMonth() {
        return this.month;
    }

    public boolean isFirstSemester() {
        return switch (month.toLowerCase()) {
            case "enero", "febrero", "marzo", "abril", "may0", "junio" -> true;
            default -> false;
        };
    }
}

