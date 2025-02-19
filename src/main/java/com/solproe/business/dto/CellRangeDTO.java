package com.solproe.business.dto;

public class CellRangeDTO {
    private String sheetName; // Nombre de la hoja
    private int startRow;     // Fila inicial
    private int endRow;       // Fila final
    private int startCol;     // Columna inicial
    private int endCol;       // Columna final


    // Getters y Setters
    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }

    public int getEndCol() {
        return endCol;
    }

    public void setEndCol(int endCol) {
        this.endCol = endCol;
    }
}

