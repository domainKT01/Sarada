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
    public void generate(Workbook workbook, SheetDataModel model) {
        this.workbook = workbook;
        this.sheet = workbook.createSheet(model.getSheetName());

        this.sectionBuilder = new GenerateSectionSheet(this, this.workbook);
        this.chartGenerator = new LineChartGenerator();

        int row = 0;
        row = sectionBuilder.createHeader(sheet, row, model);
        row = sectionBuilder.createParameterSection(sheet, row, model);
        row = sectionBuilder.createThresholdTable(sheet, row, model);
//        row = sectionBuilder.createAlertSystem(sheet, row, model);
//        row = chartGenerator.insertChart(sheet, row, model);
    }


    public Workbook getWorkbook() {
        return workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }
}
