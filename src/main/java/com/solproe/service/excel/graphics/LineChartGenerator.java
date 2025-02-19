package com.solproe.service.excel.graphics;

import com.solproe.business.dto.CellRangeDTO;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.IOException;

public class LineChartGenerator implements ExcelGenerateGraphics {


    @Override
    public void createChart(Sheet sheet, Workbook workbook, XSSFClientAnchor anchor, XSSFDrawing drawing, CellRangeDTO cellRangeDTO) throws IOException {

        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText("Gráfico de Líneas");
        chart.setTitleOverlay(false);

        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);

        // Configurar datos del gráfico
        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("Dates");
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle("Temperature (°C)");

        XDDFChartData lineChartData = chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);

        XDDFDataSource<String> categories = XDDFDataSourcesFactory.fromStringCellRange((XSSFSheet) sheet,
                new CellRangeAddress( cellRangeDTO.getStartRow() + 1, cellRangeDTO.getEndRow() + 1,
                        cellRangeDTO.getStartCol(), cellRangeDTO.getStartCol()));


        XDDFNumericalDataSource<Double> tempValue = XDDFDataSourcesFactory.fromNumericCellRange((XSSFSheet) sheet,
                new CellRangeAddress(cellRangeDTO.getStartRow() + 1, cellRangeDTO.getEndRow(),
                        cellRangeDTO.getStartCol() + 1, cellRangeDTO.getStartCol() + 1));

        // Crear el gráfico de líneas
        XDDFChartData.Series series = lineChartData.addSeries(categories, tempValue);

        series.setTitle("Temp", null);

        XDDFNumericalDataSource<Double> valuesUmbral = XDDFDataSourcesFactory.fromNumericCellRange((XSSFSheet) sheet,
                new CellRangeAddress(cellRangeDTO.getStartRow() + 1, cellRangeDTO.getEndRow(), 4, 4));

        XDDFChartData.Series serieUmbral = lineChartData.addSeries(categories, valuesUmbral);

        serieUmbral.setTitle("Threshold");

        chart.plot(lineChartData);
    }
}
