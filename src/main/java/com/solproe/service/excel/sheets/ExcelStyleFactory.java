package com.solproe.service.excel.sheets;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelStyleFactory {
    private final Workbook workbook;
    private final Sheet sheet;

    public ExcelStyleFactory(Workbook workbook, Sheet sheet) {
        this.workbook = workbook;
        this.sheet = sheet;
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

    public CellStyle createBorderedStyle(boolean bold, boolean center, boolean top, boolean bottom,
                                         boolean left, boolean right, String... colorBackground) {
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
                case "ROJA":
                    style.setFillForegroundColor(IndexedColors.RED.getIndex());
                    break;
                case "NARANJA":
                    style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
                    break;
                case "AMARILLA":
                    style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            }
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        try {
            if (top) {
                style.setBorderTop(BorderStyle.MEDIUM);
            }

            if (bottom) {
                style.setBorderBottom(BorderStyle.MEDIUM);
            }

            if (left) {
                style.setBorderLeft(BorderStyle.MEDIUM);
            }

            if (right) {
                style.setBorderRight(BorderStyle.MEDIUM);
            }
        } catch (Exception e) {
            System.out.println("create bordered style error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return style;
    }

    public void applyStyleBorder(boolean start, boolean end, int numberCells, Cell startCell, boolean bold, boolean center) {
        try {
            if (startCell != null) {
                int countCell = startCell.getColumnIndex();
                Row row = this.sheet.getRow(startCell.getRow().getRowNum());
                for (int i = 0; i < numberCells; i++) {
                    if (i == 0 && start) {
                        try {
                            row.getCell(countCell).setCellStyle(createBorderedStyle(bold, center, true,
                                    true, start, false));
                            countCell++;
                            continue;
                        } catch (Exception e) {
                            System.out.println("start style error: " + e.getMessage());
                            throw new RuntimeException(e);
                        }
                    }

                    if (i == (numberCells - 1) && end) {
                        try {
                            if (row.getCell(countCell) != null) {
                                row.getCell(countCell).setCellStyle(createBorderedStyle(bold, center, true,
                                        true, false, end));
                                countCell++;
                                continue;
                            }
                        } catch (Exception e) {
                            System.out.println("end style error: " + e.getMessage());
                            throw new RuntimeException("error apply style end", e);
                        }
                    }

                    try {
                        if (row.getCell(countCell) != null) {
                            row.getCell(countCell).setCellStyle(createBorderedStyle(false, true, true,
                                    true, true, true));
                            countCell++;
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println("apply style border..");
                        throw new RuntimeException("error apply style middle", e);
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("apply style");
            throw new RuntimeException(e);
        }
    }
}
