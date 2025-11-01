package com.solproe.service.excel.sheets;

import com.solproe.business.domain.SheetDataModel;
import com.solproe.service.excel.ExcelSheetTemplate;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ForestFireSheet implements ExcelSheetTemplate {
    private Workbook workbook;
    private Sheet sheet;
    @Override
    public void generate(Workbook workbook, SheetDataModel sheetDataModel) {

    }

    public void generateHeader() {

    }
}
