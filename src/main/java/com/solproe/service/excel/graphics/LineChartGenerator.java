package com.solproe.service.excel.graphics;

import com.solproe.business.domain.SheetDataModel;
import com.solproe.service.excel.ExcelSheetTemplate;
import com.solproe.service.excel.TypeReportSheet;
import com.solproe.service.excel.sheets.ExcelStyleFactory;
import com.solproe.service.excel.sheets.GenerateSectionSheet;
import com.solproe.service.excel.sheets.GenericSheetTemplate;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.PresetColor;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class LineChartGenerator implements ExcelGenerateGraphics {
    private Sheet sheet;
    private XSSFDrawing drawing;
    private Sheet sourceSheet;
    private GenerateSectionSheet generateSectionSheet;
    private ExcelStyleFactory styleFactory;


    public LineChartGenerator(GenerateSectionSheet generateSectionSheet, ExcelStyleFactory styleFactory) {
        this.generateSectionSheet = generateSectionSheet;
        this.styleFactory = styleFactory;
    }

    @Override
    public int createChart(Sheet sheet, XSSFDrawing drawing, Workbook workbook, SheetDataModel sheetDataModel) throws IOException {
        this.sheet = sheet;
        this.drawing = drawing;
        this.sourceSheet = workbook.getSheet("support data");
        this.sheet.setColumnWidth(13,4500 );
        this.sheet.setColumnWidth(15,4500 );
        this.sheet.setColumnWidth(16,4500 );
        int rowFinal = 0;
        int space = 2;
        int height = 25;
        Row titleRow = sheet.createRow(sheetDataModel.getStartRow() + space);
        this.generateSectionSheet.createCellsRow(sheet, 0, 8, titleRow);
        sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, 8));
        if (sheetDataModel.getReportType() == TypeReportSheet.forestFireDataModel) {
            space += 2;
            titleRow.getCell(0).setCellValue("MONITOREO DE TEMPERATURA PARA 14 DÍAS DE PRONÓSTICO ");
            titleRow.getCell(0).setCellStyle(this.styleFactory.createHeaderTitleStyle((short) 13));
            XSSFClientAnchor anchorTemp = this.drawing.createAnchor(0, 0, 0, 0, 0,
                    sheetDataModel.getStartRow() + space, 9, sheetDataModel.getStartRow() + height);
            int[][] parameterSource = {
                    {2, 15, 1, 1},
                    {2, 15, 2, 2},
                    {2, 15, 3, 3},
                    {2, 15, 4, 4}
            };
            createGraphic(parameterSource, anchorTemp);
            rowFinal = sheetDataModel.getStartRow() + height;
            rowFinal = this.generateSectionSheet.createFooterThresholdDaily(sheet, rowFinal, sheetDataModel);
            rowFinal += space;
            Row monthlyTitle = sheet.createRow(rowFinal);
            this.generateSectionSheet.createCellsRow(sheet, 0, 8, monthlyTitle);
            sheet.addMergedRegion(new CellRangeAddress(monthlyTitle.getRowNum(), monthlyTitle.getRowNum(), 0, 8));
            monthlyTitle.getCell(0).setCellValue("MONITOREO DE TEMPERATURA PARA 6 MESES PRÓXIMOS DE PRONÓSTICO ");
            monthlyTitle.getCell(0).setCellStyle(this.styleFactory.createHeaderTitleStyle((short) 13));
            rowFinal += 2;
            sheetDataModel.setStartRow(rowFinal);
            createSecondChart(sheet, drawing, workbook, sheetDataModel);
            rowFinal += height;
        } else if (sheetDataModel.getReportType() == TypeReportSheet.massMovementDataModel) {
            //first graphic
            space += 2;
            titleRow.getCell(0).setCellValue("MONITOREO DE PRECIPITACIÓN % PARA 14 DÍAS DE PRONÓSTICO");
            titleRow.getCell(0).setCellStyle(this.styleFactory.createHeaderTitleStyle((short) 13));
            int[][] parameterSource1 = {{19, 33, 1, 1}, {19, 33, 2, 2}, {19, 33, 3, 3}, {19, 33, 4, 4}};
            XSSFClientAnchor xssfClientAnchor1 = this.drawing.createAnchor(0, 0, 0, 0, 0,
                    sheetDataModel.getStartRow() + space, 9, sheetDataModel.getStartRow() + height);
            rowFinal = sheetDataModel.getStartRow() + height + 2;
            createGraphic(parameterSource1, xssfClientAnchor1);
            rowFinal = this.generateSectionSheet.createFooterThresholdDaily(sheet, rowFinal, sheetDataModel, "%");
            //second graphic
            rowFinal += space;
            Row row = sheet.createRow(rowFinal);
            this.generateSectionSheet.createCellsRow(sheet, 0, 8, row);
            sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 8));
            row.getCell(0).setCellStyle(this.styleFactory.createHeaderTitleStyle((short) 13));
            row.getCell(0).setCellValue("MONITOREO DE PRECIPITACIÓN (mm) PARA 14 DÍAS DE PRONÓSTICO");
            int[][] parameterSource2 = {
                    {36, 49, 1, 1},
                    {36, 49, 2, 2},
                    {36, 49, 3, 3},
                    {36, 49, 4, 4}
            };
            rowFinal += 2;
            XSSFClientAnchor xssfClientAnchor2 = this.drawing.createAnchor(0, 0, 0, 0, 0,
                    rowFinal, 9, rowFinal + height);
            createGraphic(parameterSource2, xssfClientAnchor2);
            rowFinal += height + 3;
            Row monthlyTitle = sheet.createRow(rowFinal);
            this.generateSectionSheet.createCellsRow(sheet, 0, 8, monthlyTitle);
            sheet.addMergedRegion(new CellRangeAddress(monthlyTitle.getRowNum(), monthlyTitle.getRowNum(), 0, 8));
            monthlyTitle.getCell(0).setCellValue("MONITOREO DE PRECIPITACIÓN PARA 6 MESES PRÓXIMOS DE PRONÓSTICO");
            monthlyTitle.getCell(0).setCellStyle(this.styleFactory.createHeaderTitleStyle((short) 13));
            rowFinal += 2;
            sheetDataModel.setStartRow(rowFinal);
            createSecondChart(sheet, drawing, workbook, sheetDataModel);
            rowFinal += height;
        } else if (sheetDataModel.getReportType() == TypeReportSheet.rainShowerDataModel) {
            space += 2;
            titleRow.getCell(0).setCellValue("MONITOREO DE VIENTO Km/h PARA 14 DÍAS DE PRONÓSTICO");
            titleRow.getCell(0).setCellStyle(this.styleFactory.createHeaderTitleStyle((short) 13));
            XSSFClientAnchor anchorWind = this.drawing.createAnchor(0, 0, 0, 0, 0,
                    sheetDataModel.getStartRow() + space, 9, sheetDataModel.getStartRow() + height);
            int[][] parameterSource = {
                    {53, 66, 1, 1},
                    {53, 66, 2, 2},
                    {53, 66, 3, 3},
                    {53, 66, 4, 4}
            };
            createGraphic(parameterSource, anchorWind);
            rowFinal += sheetDataModel.getStartRow() + height;
            rowFinal = this.generateSectionSheet.createFooterThresholdDaily(sheet, rowFinal, sheetDataModel);
        }
        return rowFinal - 3;
    }

    // Método para cambiar el color de una línea
    private static void setLineColor(@NotNull XDDFLineChartData.Series series, PresetColor color) {
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
    public int createSecondChart(Sheet sheet, XSSFDrawing drawing, Workbook workbook, SheetDataModel sheetDataModel) {
        this.sheet = sheet;
        this.drawing = drawing;
        this.sourceSheet = workbook.getSheet("support data");
        this.sheet.setColumnWidth(13,4500 );
        this.sheet.setColumnWidth(15,4500 );
        this.sheet.setColumnWidth(16,4500 );
        XSSFClientAnchor anchorTemp = this.drawing.createAnchor(0, 0, 0, 0, 0, sheetDataModel.getStartRow(), 9, sheetDataModel.getStartRow() +20);
        int[][] parameters = new int[4][4];
        if (sheetDataModel.getReportType() == TypeReportSheet.forestFireDataModel) {
            if (sheetDataModel.getThresholdMonthlyJson().get("stage").getAsDouble() == 1) {
                parameters = new int[][]{
                        {70, 75, 1, 1},
                        {70, 75, 2, 2},
                        {70, 75, 4, 4},
                        {70, 75, 5, 5}
                };
            }
            else {
                parameters = new int[][]{
                        {76, 81, 1, 1},
                        {76, 81, 2, 2},
                        {76, 81, 4, 4},
                        {76, 81, 5, 5}
                };
            }

        } else if (sheetDataModel.getReportType() == TypeReportSheet.massMovementDataModel) {
            parameters = new int[][]{
                    {70, 81, 1, 1},
                    {70, 81, 3, 3},
                    {70, 81, 6, 6},
                    {70, 81, 7, 7}
            };
        }
        createGraphic(parameters, anchorTemp);
        return 0;
    }
}
