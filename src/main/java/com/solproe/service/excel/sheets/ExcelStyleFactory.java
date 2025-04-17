package com.solproe.service.excel.sheets;

import org.apache.poi.ss.usermodel.*;

public class ExcelStyleFactory {
    private final Workbook workbook;

    public ExcelStyleFactory(Workbook workbook) {
        this.workbook = workbook;
    }

    public CellStyle createHeaderTitleStyle() {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    public CellStyle createBorderedStyle(boolean bold) {
        CellStyle style = workbook.createCellStyle();

        if (bold) {
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
        }

        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        return style;
    }

    public CellStyle createCenterAlignedStyle() {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    public CellStyle createRightAlignedStyle() {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        return style;
    }

}
