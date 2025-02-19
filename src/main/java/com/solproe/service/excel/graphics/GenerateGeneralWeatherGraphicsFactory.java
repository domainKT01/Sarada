package com.solproe.service.excel.graphics;

import com.google.gson.JsonObject;
import com.solproe.business.dto.CellRangeDTO;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;

import java.io.IOException;

public class GenerateGeneralWeatherGraphicsFactory implements ExcelGenerateGraphics {

    private JsonObject jsonObject;

    public GenerateGeneralWeatherGraphicsFactory(JsonObject jo) {
        this.jsonObject = jo;
    }

    @Override
    public void createChart(Sheet sheet, Workbook workbook, XSSFClientAnchor anchor, XSSFDrawing drawing, CellRangeDTO cellRangeDTO) throws IOException {

    }
}
