package com.solproe.service.excel.graphics;

import com.solproe.business.domain.SheetDataModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.PresetColor;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class LineChartGenerator implements ExcelGenerateGraphics {


    @Override
    public void createChart(Sheet sheet, XSSFClientAnchor anchor, XSSFDrawing drawing, Workbook workbook, SheetDataModel sheetDataModel) {
        XSSFChart chart = drawing.createChart(anchor);
        Double[] thresholdOrange = new Double[14];
        Double[] thresholdRed = new Double[14];

        int columnTable = 13;
        int rowTable = 11;
        if (sheetDataModel.getReportType().equals("forestFireDataModel")) {
            for (int i = 0; i < sheetDataModel.getArrDate().size(); i++) {
                Row row = sheet.createRow(rowTable);
                Cell cellDate = row.createCell(columnTable);
                Cell cellValue = row.createCell(columnTable + 1);
                Cell cellThresholdOrange = row.createCell(columnTable + 2);
                Cell cellThresholdRed = row.createCell(columnTable + 3);
                cellDate.setCellValue(sheetDataModel.getArrDate().get(i));
                cellValue.setCellValue(sheetDataModel.getArrTemperature().get(i));
                cellThresholdOrange.setCellValue(sheetDataModel.getConfigFileThreshold().get("forestFireThresholdOrange").getAsDouble());
                cellThresholdRed.setCellValue(sheetDataModel.getConfigFileThreshold().get("forestFireThresholdRed").getAsDouble());
                rowTable++;
            }
        }

        // Configurar datos del gráfico
        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("Dates");
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);

        sheet.setColumnWidth(13,4000 );

        XDDFDataSource<String> categories = XDDFDataSourcesFactory.fromStringCellRange((XSSFSheet) sheet,
                new CellRangeAddress(12, 26, 13, 13));

        XDDFNumericalDataSource<Double> value = XDDFDataSourcesFactory.fromNumericCellRange((XSSFSheet) sheet,
                new CellRangeAddress(12, 26, 14, 14));

        XDDFNumericalDataSource<Double> sourceThresholdOrange = XDDFDataSourcesFactory.fromNumericCellRange((XSSFSheet) sheet,
                new CellRangeAddress(12, 26, 15, 15));

        XDDFNumericalDataSource<Double> sourceThresholdRed = XDDFDataSourcesFactory.fromNumericCellRange((XSSFSheet) sheet,
                new CellRangeAddress(12, 26, 16, 16));

        // Crear el gráfico de líneas
        XDDFLineChartData chartData = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);

        XDDFLineChartData.Series seriesTemp = (XDDFLineChartData.Series) chartData.addSeries(categories, value);
        seriesTemp.setSmooth(false);
        seriesTemp.setMarkerStyle(MarkerStyle.CIRCLE);
        setLineColor(seriesTemp, PresetColor.BLUE);

        XDDFLineChartData.Series seriesThresholdOrange = (XDDFLineChartData.Series) chartData.addSeries(categories, sourceThresholdOrange);
        seriesThresholdOrange.setSmooth(false);
        seriesThresholdOrange.setMarkerStyle(MarkerStyle.PLUS);
        setLineColor(seriesThresholdOrange, PresetColor.ORANGE);

        XDDFLineChartData.Series seriesThresholdRed = (XDDFLineChartData.Series) chartData.addSeries(categories, sourceThresholdRed) ;
        seriesThresholdRed.setSmooth(false);
        seriesThresholdRed.setMarkerStyle(MarkerStyle.DOT);
        setLineColor(seriesThresholdRed, PresetColor.RED);

        chart.plot(chartData);
    }

    private CellStyle setStyle(Workbook workbook, String type) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.MEDIUM);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        if (type.equalsIgnoreCase("date")) {
            cellStyle.setBorderRight(BorderStyle.MEDIUM);
        }
        else {
            cellStyle.setBorderLeft(BorderStyle.MEDIUM);
        }
        return cellStyle;
    }

    // Método para cambiar el color de una línea
    private static void setLineColor(XDDFLineChartData.Series series, PresetColor color) {
        XDDFSolidFillProperties fill = new XDDFSolidFillProperties(XDDFColor.from(color));
        XDDFLineProperties line = new XDDFLineProperties();
        line.setFillProperties(fill);
        series.setLineProperties(line);
    }
}
