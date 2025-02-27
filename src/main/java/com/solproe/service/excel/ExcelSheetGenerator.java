package com.solproe.service.excel;

import com.solproe.business.domain.SheetDataModel;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public class ExcelSheetGenerator {
    private final ExcelSheetTemplate sheetTemplate;

    public ExcelSheetGenerator(ExcelSheetTemplate sheetTemplate) {
        this.sheetTemplate = sheetTemplate;
    }

    public void generateSheets(Workbook workbook, List<SheetDataModel> datasets) {
        for (SheetDataModel data : datasets) {
            sheetTemplate.createSheet(workbook, data);
        }
    }
}
