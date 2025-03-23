package com.solproe.service.excel.graphics;

import com.solproe.business.domain.SheetDataModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.PresetColor;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;

public class LineChartGenerator implements ExcelGenerateGraphics {
    private Sheet sheet;
    private XSSFDrawing drawing;
    private Sheet sourceSheet;

    @Override
    public void createChart(Sheet sheet, XSSFDrawing drawing, Workbook workbook, SheetDataModel sheetDataModel) {
        this.sheet = sheet;
        this.drawing = drawing;
        this.sourceSheet = workbook.getSheet("support data");
        this.sheet.setColumnWidth(13,4500 );
        this.sheet.setColumnWidth(15,4500 );
        this.sheet.setColumnWidth(16,4500 );
        if (sheetDataModel.getReportType().equals("forestFireDataModel")) {
            XSSFClientAnchor anchorTemp = this.drawing.createAnchor(0, 0, 0, 0, 0, 11, 9, 31);
            int[][] parameterSource = {{2, 15, 1, 1}, {2, 15, 2, 2}, {2, 15, 3, 3}, {2, 15, 4, 4}};
            createGraphic(parameterSource, anchorTemp);
        } else if (sheetDataModel.getReportType().equalsIgnoreCase("massMovementDataModel")) {
            int[][] parameterSource1 = {{19, 33, 1, 1}, {19, 33, 2, 2}, {19, 33, 3, 3}, {19, 33, 4, 4}};
            int[][] parameterSource2 = {{36, 49, 1, 1}, {36, 49, 2, 2}, {36, 49, 3, 3}, {36, 49, 4, 4}};
            XSSFClientAnchor xssfClientAnchor1 = this.drawing.createAnchor(0, 0, 0, 0, 0, 11, 9, 31);
            XSSFClientAnchor xssfClientAnchor2 = this.drawing.createAnchor(0, 0, 0, 0, 0, sheetDataModel.getStartRow() + 5,
                    9, sheetDataModel.getStartRow() + 23);
            createGraphic(parameterSource1, xssfClientAnchor1);
            createGraphic(parameterSource2, xssfClientAnchor2);
        } else if (sheetDataModel.getReportType().equalsIgnoreCase("rainShowerDataModel")) {
            XSSFClientAnchor anchorWind = this.drawing.createAnchor(0, 0, 0, 0, 0, 11, 9, 31);
            int[][] parameterSource = {{53, 66, 1, 1}, {53, 66, 2, 2}, {53, 66, 3, 3}, {53, 66, 4, 4}};
            createGraphic(parameterSource, anchorWind);
        }
    }

    // Método para cambiar el color de una línea
    private static void setLineColor(XDDFLineChartData.Series series, PresetColor color) {
        XDDFSolidFillProperties fill = new XDDFSolidFillProperties(XDDFColor.from(color));
        XDDFLineProperties line = new XDDFLineProperties();
        line.setFillProperties(fill);
        series.setLineProperties(line);
    }

    private void createGraphic(int[][] parameters, XSSFClientAnchor anchor) {
        // Configurar datos del gráfico
        XSSFChart chart = this.drawing.createChart(anchor);
        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("Dates");
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);

        XDDFDataSource<String> categories = XDDFDataSourcesFactory.fromStringCellRange((XSSFSheet) this.sourceSheet,
                new CellRangeAddress(parameters[0][0], parameters[0][1], parameters[0][2], parameters[0][3]));

        XDDFNumericalDataSource<Double> value = XDDFDataSourcesFactory.fromNumericCellRange((XSSFSheet) this.sourceSheet,
                new CellRangeAddress(parameters[1][0], parameters[1][1], parameters[1][2], parameters[1][3]));

        XDDFNumericalDataSource<Double> sourceThresholdOrange = XDDFDataSourcesFactory.fromNumericCellRange((XSSFSheet) this.sourceSheet,
                new CellRangeAddress(parameters[2][0], parameters[2][1], parameters[2][2], parameters[2][3]));

        XDDFNumericalDataSource<Double> sourceThresholdRed = XDDFDataSourcesFactory.fromNumericCellRange((XSSFSheet) this.sourceSheet,
                new CellRangeAddress(parameters[3][0], parameters[3][1], parameters[3][2], parameters[3][3]));

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

    @Override
    public void createSecondChart(Sheet sheet, XSSFDrawing drawing, Workbook workbook, SheetDataModel sheetDataModel) throws IOException {
        this.sheet = sheet;
        this.drawing = drawing;
        this.sourceSheet = workbook.getSheet("support data");
        this.sheet.setColumnWidth(13,4500 );
        this.sheet.setColumnWidth(15,4500 );
        this.sheet.setColumnWidth(16,4500 );
        XSSFClientAnchor anchorTemp = this.drawing.createAnchor(0, 0, 0, 0, 0, 11, 9, 31);
        int[][] parameterSource = {{2, 15, 1, 1}, {2, 15, 2, 2}, {2, 15, 3, 3}, {2, 15, 4, 4}};
        createGraphic(parameterSource, anchorTemp);

    }
}
