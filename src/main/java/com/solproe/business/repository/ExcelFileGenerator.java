package com.solproe.business.repository;

import com.solproe.business.dto.OpenMeteoForecastList;

public interface ExcelFileGenerator {

    void generate(String filePath, OpenMeteoForecastList forecastList);
}
