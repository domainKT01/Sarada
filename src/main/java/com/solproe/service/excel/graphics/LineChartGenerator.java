package com.solproe.service.excel.graphics;

import com.solproe.business.domain.SheetDataModel;
import com.solproe.business.dto.CellRangeDTO;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class LineChartGenerator implements ExcelGenerateGraphics {


    @Override
    public void createChart(Sheet sheet, XSSFClientAnchor anchor, XSSFDrawing drawing, SheetDataModel sheetDataModel) {
        XSSFChart chart = drawing.createChart(anchor);
        String[] properties = sheetDataModel.getArrDate().toArray(new String[14]);
        Double[] thresholdOrange = new Double[14];
        Double[] thresholdRed = new Double[14];
        Double[] values;
        if (sheetDataModel.getReportType().equals("forestFireDataModel")) {
            values = sheetDataModel.getArrTemperature().toArray(new Double[14]);
            double orange = sheetDataModel.getConfigFileThreshold().get("forestFireThresholdOrange").getAsDouble();
            double red = sheetDataModel.getConfigFileThreshold().get("forestFireThresholdRed").getAsDouble();
            for (int i = 0; i < 14; i++) {
                thresholdOrange[i] = orange;
                thresholdRed[i] = red;
            }

            // Configurar datos del gráfico
            XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
            bottomAxis.setTitle("Dates");
            XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);

            XDDFDataSource<String> categories = XDDFDataSourcesFactory.fromArray(properties);

            XDDFNumericalDataSource<Double> value = XDDFDataSourcesFactory.fromArray(values);

            XDDFNumericalDataSource<Double> sourceThresholdOrange = XDDFDataSourcesFactory.fromArray(thresholdOrange);

            XDDFNumericalDataSource<Double> sourceThresholdRed = XDDFDataSourcesFactory.fromArray(thresholdRed);

            // Crear el gráfico de líneas
            XDDFLineChartData chartData = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);
            XDDFLineChartData.Series series = (XDDFLineChartData.Series) chartData.addSeries(categories, value);
            series.setTitle("Ventas", null);
            series.setSmooth(false);
            series.setMarkerStyle(MarkerStyle.CIRCLE);

            chart.plot(chartData);
        }
    }
}
