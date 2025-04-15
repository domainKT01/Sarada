package com.solproe.service.excel;

import com.solproe.business.domain.SheetDataModel;
import com.solproe.business.dto.OpenMeteoForecastList;
import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelSheetTemplate {

    void generate(Workbook workbook, SheetDataModel sheetDataModel);
}
