package com.solproe.service.excel.sheets;

import com.solproe.business.domain.SheetDataModel;
import com.solproe.service.excel.ExcelSheetTemplate;
import com.solproe.service.excel.graphics.ExcelGenerateGraphics;
import com.solproe.service.excel.graphics.LineChartGenerator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class ForestFireSheet implements ExcelSheetTemplate {
    private Workbook workbook;
    private Sheet sheet;
    private GenerateSectionSheet sectionBuilder;
    private ExcelGenerateGraphics chartGenerator;
    private ExcelStyleFactory styleFactory;
    private SheetDataModel dataModel;
    private GenerateSectionSheet generateSectionSheet = new GenerateSectionSheet();
    private int row = 0;

    @Override
    public void generate(Workbook workbook, SheetDataModel sheetDataModel) {
        this.workbook = workbook;
        this.sheet = workbook.createSheet(sheetDataModel.getSheetName());
        this.sectionBuilder = new GenerateSectionSheet(this, this.workbook);
        this.styleFactory = new ExcelStyleFactory(this.workbook, this.sheet);
        this.chartGenerator = new LineChartGenerator(this.sectionBuilder, this.styleFactory);
        this.dataModel = sheetDataModel;

        //header main
        this.row = this.sectionBuilder.createHeader(this.sheet, this.row, dataModel);
    }

    @Override
    public Workbook getWorkbook() {
        return this.workbook;
    }

    @Override
    public Sheet getSheet() {
        return this.sheet;
    }
}
