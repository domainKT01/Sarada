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

public class LineChartGenerator implements ExcelGenerateGraphics {
    private Sheet sheet;
    private SheetDataModel sheetDataModel;
    private XSSFDrawing drawing;
    private Workbook workbook;

    @Override
    public void createChart(Sheet sheet, XSSFDrawing drawing, Workbook workbook, SheetDataModel sheetDataModel) {
        this.sheet = sheet;
        this.drawing = drawing;
        this.sheetDataModel = sheetDataModel;
        this.workbook = workbook;

        this.sheet.setColumnWidth(13,4500 );
        this.sheet.setColumnWidth(15,4500 );
        this.sheet.setColumnWidth(16,4500 );

        System.out.println(sheetDataModel.getArrTemperature());

        if (sheetDataModel.getReportType().equals("forestFireDataModel")) {
            String[] parameters = {"forestFireThresholdOrange", "forestFireThresholdRed", "Temp"};
            createValuesTable(11, 13, parameters);
            XSSFClientAnchor anchorTemp = this.drawing.createAnchor(0, 0, 0, 0, 0, 11, 9, 31);
            int[][] parameterSource = {{11, 24, 13, 13}, {11, 24, 14, 14}, {11, 24, 15, 15}, {11, 24, 16, 16}, {11, 24, 17, 17}};
            createGraphic(parameterSource, anchorTemp);
        } else if (sheetDataModel.getReportType().equalsIgnoreCase("massMovementDataModel")) {
            String[] parameters1 = {"precipitationRainPercentOrange", "precipitationRainPercentRed", "Prec (%)"};
            String[] parameters2 = {"precipitationThresholdOrange", "precipitationThresholdRed", "Prec (mm)"};
            createValuesTable(11, 13, parameters1);
            createValuesTable(37, 13, parameters2);
            int[][] parameterSource1 = {{11, 24, 13, 13}, {11, 24, 14, 14}, {11, 24, 15, 15}, {11, 24, 16, 16}, {11, 24, 17, 17}};
            int[][] parameterSource2 = {{37, 51, 13, 13}, {37, 51, 14, 14}, {37, 51, 15, 15}, {37, 51, 16, 16}, {37, 51, 17, 17}};
            XSSFClientAnchor xssfClientAnchor1 = this.drawing.createAnchor(0, 0, 0, 0, 0, 11, 9, 31);
            XSSFClientAnchor xssfClientAnchor2 = this.drawing.createAnchor(0, 0, 0, 0, 0, 36, 9, 56);
            createGraphic(parameterSource1, xssfClientAnchor1);
            createGraphic(parameterSource2, xssfClientAnchor2);
        } else if (sheetDataModel.getReportType().equalsIgnoreCase("rainShowerDataModel")) {
            String[] parameters = {"windThresholdOrange", "windThresholdRed", "Viento"};
            createValuesTable(11, 13, parameters);
            XSSFClientAnchor anchorWind = this.drawing.createAnchor(0, 0, 0, 0, 0, 11, 9, 31);
            int[][] parameterSource = {{11, 24, 13, 13}, {11, 24, 14, 14}, {11, 24, 15, 15}, {11, 24, 16, 16}, {11, 24, 17, 17}};
            createGraphic(parameterSource, anchorWind);
        }
    }

    private CellStyle setStyle(String type) {
        CellStyle cellStyle = this.workbook.createCellStyle();

        if (type.equalsIgnoreCase("date")) {
            cellStyle.setBorderLeft(BorderStyle.MEDIUM);
            cellStyle.setBorderTop(BorderStyle.MEDIUM);
            cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        } else if (type.equalsIgnoreCase("end")) {
            cellStyle.setBorderRight(BorderStyle.MEDIUM);
            cellStyle.setBorderTop(BorderStyle.MEDIUM);
            cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        } else if (type.equalsIgnoreCase("header")) {
            Font font = this.workbook.createFont();
            font.setBold(true);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.MEDIUM);
        } else {
            cellStyle.setBorderTop(BorderStyle.MEDIUM);
            cellStyle.setBorderBottom(BorderStyle.MEDIUM);
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

    private void createGraphic(int[][] parameters, XSSFClientAnchor anchor) {
        // Configurar datos del gráfico
        XSSFChart chart = this.drawing.createChart(anchor);
        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("Dates");
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);

        XDDFDataSource<String> categories = XDDFDataSourcesFactory.fromStringCellRange((XSSFSheet) sheet,
                new CellRangeAddress(parameters[0][0], parameters[0][1], parameters[0][2], parameters[0][3]));

        XDDFNumericalDataSource<Double> value = XDDFDataSourcesFactory.fromNumericCellRange((XSSFSheet) sheet,
                new CellRangeAddress(parameters[1][0], parameters[1][1], parameters[1][2], parameters[1][3]));

        XDDFNumericalDataSource<Double> sourceThresholdOrange = XDDFDataSourcesFactory.fromNumericCellRange((XSSFSheet) sheet,
                new CellRangeAddress(parameters[2][0], parameters[2][1], parameters[2][2], parameters[2][3]));

        XDDFNumericalDataSource<Double> sourceThresholdRed = XDDFDataSourcesFactory.fromNumericCellRange((XSSFSheet) sheet,
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

    private void createValuesTable(int rowTable, int columnTable, String[] parameters) {
        Row rowHeader = this.sheet.createRow(rowTable - 1);
        Cell cellDateHeader = rowHeader.createCell(columnTable);
        cellDateHeader.setCellValue("Fecha");
        cellDateHeader.setCellStyle(setStyle("header"));
        Cell cellValueHeader = rowHeader.createCell(columnTable + 1);
        cellValueHeader.setCellValue(parameters[2]);
        cellValueHeader.setCellStyle(setStyle("header"));
        Cell cellThresholdOrangeHeader = rowHeader.createCell(columnTable +2);
        cellThresholdOrangeHeader.setCellValue("Alerta Naranja");
        cellThresholdOrangeHeader.setCellStyle(setStyle("header"));
        Cell cellThresholdRedHeader = rowHeader.createCell(columnTable + 3);
        cellThresholdRedHeader.setCellValue("Alerta Roja");
        cellThresholdRedHeader.setCellStyle(setStyle("header"));
        System.out.println(parameters);
        for (int i = 0; i < sheetDataModel.getArrDate().size(); i++) {
            Row row = sheet.createRow(rowTable);
            Cell cellDate = row.createCell(columnTable);
            Cell cellValue = row.createCell(columnTable + 1);
            Cell cellThresholdOrange = row.createCell(columnTable + 2);
            Cell cellThresholdRed = row.createCell(columnTable + 3);
            cellDate.setCellValue(sheetDataModel.getArrDate().get(i));
            cellDate.setCellStyle(setStyle("date"));
            cellValue.setCellValue(sheetDataModel.getArrTemperature().get(i));
            cellValue.setCellStyle(setStyle(""));
            cellThresholdOrange.setCellValue(sheetDataModel.getConfigFileThreshold().get(parameters[0]).getAsDouble());
            cellThresholdOrange.setCellStyle(setStyle(""));
            cellThresholdRed.setCellValue(sheetDataModel.getConfigFileThreshold().get(parameters[1]).getAsDouble());
            cellThresholdRed.setCellStyle(setStyle("end"));
            rowTable++;
        }
    }
}
