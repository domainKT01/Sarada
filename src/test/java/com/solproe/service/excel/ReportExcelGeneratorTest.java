package com.solproe.service.excel;

import com.google.gson.JsonObject;
import com.solproe.business.dto.OpenMeteoForecastList;
import com.solproe.business.repository.ExcelFileGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportExcelGeneratorTest {

    @Test
    public void generate() {
        ExcelService excelService = new ExcelService();
        ReportExcelGenerator reportExcelGenerator = new ReportExcelGenerator(excelService);
        reportExcelGenerator.generate("/home/prueba/Documentos/helloExcel1.xlsx", new OpenMeteoForecastList());
    }

}