package com.solproe.service.excel.sheets;

import org.apache.poi.ss.usermodel.*;

public class ExcelStyleFactory {
    private final Workbook workbook;

    public ExcelStyleFactory(Workbook workbook) {
        this.workbook = workbook;
    }

    public CellStyle createHeaderTitleStyle(short size) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints(size);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    public CellStyle createBorderedStyle(boolean bold, boolean center, String... colorBackground) {
        CellStyle style = workbook.createCellStyle();
        if (bold) {
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
        }
        if (center) {
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setWrapText(true);
        }
        if (colorBackground.length != 0) {
            switch (colorBackground[0]) {
                case "ROJA" :
                    style.setFillForegroundColor(IndexedColors.RED.getIndex());
                    break;
                case "NARANJA" :
                    style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
                    break;
                case "AMARILLA" :
                    style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            }
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        return style;
    }

    public CellStyle createCenterAlignedStyle() {
        CellStyle style = workbook.createCellStyle();
        return style;
    }

    public CellStyle createRightAlignedStyle() {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        return style;
    }
}
