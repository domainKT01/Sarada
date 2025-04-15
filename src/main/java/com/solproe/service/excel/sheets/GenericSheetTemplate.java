package com.solproe.service.excel.sheets;

import com.solproe.business.domain.SheetDataModel;
import com.solproe.service.excel.ExcelSheetTemplate;
import com.solproe.service.excel.graphics.LineChartGenerator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class GenericSheetTemplate implements ExcelSheetTemplate {
    private Workbook workbook;
    private Sheet sheet;
    private GenerateSectionSheet sectionBuilder;
    private LineChartGenerator chartGenerator;

    @Override
    public void generate(Workbook workbook, SheetDataModel sheetDataModel) {
        this.workbook = workbook;
        this.sheet = workbook.createSheet(sheetDataModel.getSheetName());

        // Inicializar colaboradores con acceso a la plantilla
        this.sectionBuilder = new GenerateSectionSheet(this);
        this.chartGenerator = new LineChartGenerator();

        int currentRow = 0;

        currentRow = sectionBuilder.createHeader(sheet, currentRow, sheetDataModel.getTitle());
//        currentRow = sectionBuilder.createParameterSection(sheet, currentRow, sheetDataModel);
//        currentRow = sectionBuilder.createThresholdTable(sheet, currentRow, sheetDataModel);
//        currentRow = sectionBuilder.createAlertSystem(sheet, currentRow, sheetDataModel);
//
//        currentRow = chartGenerator.insertTemperatureChart(sheet, currentRow, sheetDataModel);
//        currentRow = chartGenerator.insertPrecipitationChart(sheet, currentRow, sheetDataModel);
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }
}
