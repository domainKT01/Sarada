package com.solproe.service.excel.sheets;

import com.solproe.business.domain.SheetDataModel;
import com.solproe.service.excel.ExcelSheetTemplate;
import com.solproe.service.excel.TypeReportSheet;
import com.solproe.service.excel.graphics.ExcelGenerateGraphics;
import com.solproe.service.excel.graphics.LineChartGenerator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFDrawing;

public class GenericSheetTemplate implements ExcelSheetTemplate {
    private Workbook workbook;
    private Sheet sheet;
    private GenerateSectionSheet sectionBuilder;
    private ExcelGenerateGraphics chartGenerator;
    private ExcelStyleFactory styleFactory;

    @Override
    public void generate(Workbook workbook, SheetDataModel model) {
        this.workbook = workbook;
        this.sheet = workbook.createSheet(model.getSheetName());

        if (model.getReportType() != TypeReportSheet.ceraunic) {
            this.sectionBuilder = new GenerateSectionSheet(this, this.workbook);
            this.styleFactory = new ExcelStyleFactory(workbook);
            this.chartGenerator = new LineChartGenerator(this.sectionBuilder, this.styleFactory);
            int row = 0;

            //=============
            //* HEADER
            //=============
            {
                try {
                    row = this.sectionBuilder.createHeader(sheet, row, model);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }

            //===========
            //* GRAPHIC
            //===========
            {
                try {
                    XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
                    model.setStartRow(row);
                    row = this.chartGenerator.createChart(sheet, drawing, workbook, model);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            //===============
            //* Alerts Charts
            //===============
            {
                try{
                    row = this.sectionBuilder.createAlertSystem(sheet, row, model);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }

            //===============
            //* Notifications
            //===============
            {
                row += 3;
                try{
                    row = this.sectionBuilder.createNotificationChart(sheet, row, model);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
            System.out.println("final num: " + row);
        }
        else  {
            this.sectionBuilder = new GenerateSectionSheet(this, this.workbook);
            this.styleFactory = new ExcelStyleFactory(workbook);
            this.chartGenerator = new LineChartGenerator(this.sectionBuilder, this.styleFactory);
            int row = 1;

            //=============
            //* HEADER
            //=============
            {
                try {
                    row = this.sectionBuilder.createHeader(sheet, row, model);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }

            //===========
            //* GRAPHIC
            //===========
            {
                try {
                    XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
                    model.setStartRow(row);
                    row = this.chartGenerator.createChart(sheet, drawing, workbook, model);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }




    public Workbook getWorkbook() {
        return workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }
}
