package com.solproe.service.excel.sheets;

import com.solproe.business.domain.SheetDataModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class GenerateSectionSheet {
    private final GenericSheetTemplate template;

    public GenerateSectionSheet(GenericSheetTemplate template) {
        this.template = template;
    }

    public int createHeader(Sheet sheet, int rowIndex, String title) {
        Workbook workbook = template.getWorkbook();

        // Crear fila y celda
        Row row = sheet.createRow(rowIndex);
        Cell cell = row.createCell(0);
        cell.setCellValue(title);

        // Crear estilo para el título
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        cell.setCellStyle(style);

        // Combinar celdas (asumiendo columnas 0 a 7 por ejemplo)
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 7));

        return rowIndex + 2; // deja una fila vacía después del título
    }

    public int createParameterSection(Sheet sheet, int startRow, SheetDataModel data) {
        Workbook workbook = template.getWorkbook();

        // Crear estilo para etiquetas
        CellStyle labelStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        labelStyle.setFont(boldFont);

        // Datos como pares etiqueta → valor
        String[][] rows = {
                {"Nombre del proyecto", data.getThresholdDailyJson().get("projectName").getAsString()},
                {"ID del proyecto", data.getThresholdDailyJson().get("idProject").getAsString()},
                {"Ciudad", data.getThresholdDailyJson().get("cityName").getAsString()},
                {"Estado", data.getThresholdDailyJson().get("stateName").getAsString()},
                {"Responsable SCI", data.getThresholdDailyJson().get("sciBoss").getAsString()},
                {"Teléfono responsable", String.valueOf(data.getThresholdDailyJson().get("sciBossContact"))},
                {"Responsable auxiliar", data.getThresholdDailyJson().get("auxiliarSciBoss").getAsString()},
                {"Teléfono auxiliar", String.valueOf(data.getThresholdDailyJson().get("auxiliarSciBossContact"))}
        };

        for (String[] rowInfo : rows) {
            Row row = sheet.createRow(startRow++);
            Cell labelCell = row.createCell(0);
            labelCell.setCellValue(rowInfo[0]);
            labelCell.setCellStyle(labelStyle);

            Cell valueCell = row.createCell(1);
            valueCell.setCellValue(rowInfo[1]);
        }

        return startRow + 1; // deja una fila vacía después
    }

}
