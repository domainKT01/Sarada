package com.solproe.service.excel;

import com.solproe.business.domain.SheetDataModel;
import com.solproe.service.excel.sheets.ForestFireSheet;
import com.solproe.service.excel.sheets.SupportDatasheetTemplate;
import com.solproe.util.logging.ErrorLogger;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public class ExcelSheetGenerator {
    private ExcelSheetTemplate sheetTemplate;

    public ExcelSheetGenerator(ExcelSheetTemplate sheetTemplate) {
        this.sheetTemplate = sheetTemplate;
    }

    public void generateSheets(Workbook workbook, List<SheetDataModel> datasets) {
        ExcelSheetTemplate excelSheetTemplate = new SupportDatasheetTemplate(datasets);
        excelSheetTemplate.generate(workbook, null);
        try {
            Thread.sleep(2000);
        }
        catch (Exception e) {
            System.out.println("sleep exception...");
            ErrorLogger.log(e);
        }
//        for (SheetDataModel data : datasets) {
//            this.sheetTemplate.generate(workbook, data);
//        }

        try {
            ExcelSheetTemplate forestFire = new ForestFireSheet();
            forestFire.generate(workbook, datasets.getFirst());
            ExcelSheetTemplate masMovement;
        }
        catch (Exception e) {
            ErrorLogger.log(e);
        }
    }
}
