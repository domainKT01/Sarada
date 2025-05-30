package com.solproe.business.repository;

import com.google.gson.JsonObject;
import com.solproe.business.dto.OpenMeteoForecastList;

import java.nio.file.Path;

public interface ExcelFileGenerator {

    void generate(Path filePath, OpenMeteoForecastList forecastList);

    void setConfigFile(JsonObject... jsonObject);
}
