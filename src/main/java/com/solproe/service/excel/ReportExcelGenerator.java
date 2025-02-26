package com.solproe.service.excel;

import com.solproe.business.domain.WeatherNode;
import com.solproe.business.dto.CellRangeDTO;
import com.solproe.business.dto.OpenMeteoForecastList;
import com.solproe.service.excel.graphics.ExcelGenerateGraphics;
import com.solproe.service.excel.graphics.LineChartGenerator;
import com.solproe.business.repository.ExcelFileGenerator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.RuntimeMXBean;

public class ReportExcelGenerator implements ExcelFileGenerator {


    @Override
    public void generate(String filePath, OpenMeteoForecastList forecastList) {

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Report");
            // Genera la gráfica
            ExcelGenerateGraphics generateGraphics = new LineChartGenerator();

            CellRangeDTO cellRangeDTO = new CellRangeDTO();
            cellRangeDTO.setStartRow(0);
            cellRangeDTO.setStartCol(0);

            int count = 0;
            Row row = sheet.createRow(count);
            Cell cellTemp = row.createCell(1);
            cellTemp.setCellValue("Temp");
            Cell cellHumidity = row.createCell(2);
            cellHumidity.setCellValue("Humidity");
            Cell cellPrecipitation = row.createCell(3);
            cellPrecipitation.setCellValue("Precipitation");
            Cell cellUmbralTemp = row.createCell(4);
            cellUmbralTemp.setCellValue("Umbral");
            for (WeatherNode node: forecastList.getNodeList()) {
                count++;
                Row rowDate = sheet.createRow(count);
                rowDate.createCell(0).setCellValue(node.getDate());
                Cell cellTempData = rowDate.createCell(1);
                cellTempData.setCellValue(node.getTemp());
                Cell cellHunidityData = rowDate.createCell(2);
                cellHunidityData.setCellValue(node.getHumidity());
                Cell cellPrecipitationData = rowDate.createCell(3);
                cellPrecipitationData.setCellValue(node.getPrecipitation());
                Cell cellUmbralTempdata = rowDate.createCell(4);
                cellUmbralTempdata.setCellValue(35);
                cellRangeDTO.setEndRow(count);
                cellRangeDTO.setEndCol(4);
            }

            // create graphic
            XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0,
                    2,  cellRangeDTO.getEndRow() + 4, cellRangeDTO.getEndCol() + 10,
                    cellRangeDTO.getEndRow() + 24);

            generateGraphics.createChart(sheet, workbook, anchor, drawing, cellRangeDTO);

            // save the file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                System.out.println("successful");
            } catch (IOException e) {
                System.out.println("error to create file: " + e.getMessage());
            }

        }
        catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
