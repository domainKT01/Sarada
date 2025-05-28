package com.solproe.business.usecase;

import com.google.gson.JsonObject;
import com.solproe.business.domain.ConfigFileThreshold;
import com.solproe.business.dto.ListCodeDTO;
import com.solproe.business.dto.MonthlyData;
import com.solproe.business.dto.MonthlyThresholdInputModel;
import com.solproe.business.repository.ConfigFileGenerator;
import com.solproe.business.repository.ConfigPropertiesGeneratorInterface;

import java.lang.reflect.Field;
import java.nio.file.Path;

public class CreateConfigFileUseCase {

    private final ConfigFileGenerator configFileGenerator;

    public CreateConfigFileUseCase(ConfigFileGenerator configFileGenerator) {
        this.configFileGenerator = configFileGenerator;
    }

    public boolean createConfigFile(Object object, ConfigPropertiesGeneratorInterface config) {
        try {
            if (!(object instanceof ConfigFileThreshold configFileThreshold)) {
                throw new IllegalArgumentException("Unsupported object type");
            }

            JsonObject jsonObject = buildConfigFileThreshold(configFileThreshold);
            return generateFile(jsonObject, config.getAppConfigPath());

        } catch (Exception e) {
            System.err.println("CreateConfigFileUseCase#createConfigFile: " + e.getMessage());
            return false;
        }
    }

    public boolean createMonthlyConfigFile(MonthlyThresholdInputModel model, ConfigPropertiesGeneratorInterface config) {
        try {
            JsonObject jsonObject = buildMonthlyConfigFile(model);
            return generateFile(jsonObject, config.getAppConfigPath());

        } catch (Exception e) {
            System.err.println("CreateConfigFileUseCase#createMonthlyConfigFile: " + e.getMessage());
            return false;
        }
    }

    public boolean createCodeListConfig(ListCodeDTO dto, ConfigPropertiesGeneratorInterface config) {
        try {
            JsonObject jsonObject = buildCodeListConfig(dto);
            return generateFile(jsonObject, config.getAppConfigPath());

        } catch (IllegalAccessException e) {
            System.err.println("CreateConfigFileUseCase#createCodeListConfig: " + e.getMessage());
            return false;
        }
    }

    public boolean createPropertiesFile(ConfigPropertiesGeneratorInterface config) {
        return config.createPropertyFile();
    }

    // ========== Métodos privados ==========

    private JsonObject buildConfigFileThreshold(ConfigFileThreshold data) {
        JsonObject json = new JsonObject();
        json.addProperty("forestFireThresholdOrange", data.getForestFireThresholdOrange());
        json.addProperty("forestFireThresholdRed", data.getForestFireThresholdRed());
        json.addProperty("precipitationThresholdOrange", data.getPrecipitationThresholdOrange());
        json.addProperty("precipitationThresholdRed", data.getPrecipitationThresholdRed());
        json.addProperty("windThresholdOrange", data.getWindThresholdOrange());
        json.addProperty("windThresholdRed", data.getWindThresholdRed());
        json.addProperty("precipitationRainPercentOrange", data.getPrecipitationRainPercentOrange());
        json.addProperty("precipitationRainPercentRed", data.getPrecipitationRainPercentRed());
        json.addProperty("ceraunicosThresholdRed", data.getCeraunicosThresholdRed());
        json.addProperty("projectName", data.getIdProject());
        json.addProperty("stateName", data.getStateName());
        json.addProperty("cityName", data.getCityName());
        json.addProperty("idProject", data.getIdProject());
        json.addProperty("sciBoss", data.getSciBoss());
        json.addProperty("sciBossContact", data.getSciBossContact());
        json.addProperty("auxiliarSciBoss", data.getAuxiliarSciBoss());
        json.addProperty("auxiliarSciBossContact", data.getAuxiliarSciBossContact());
        return json;
    }

    private JsonObject buildMonthlyConfigFile(MonthlyThresholdInputModel model) {
        JsonObject json = new JsonObject();
        json.addProperty("orangeThresholdTemperature", model.getOrangeTemperatureThreshold());
        json.addProperty("redThresholdTemperature", model.getRedTemperatureThreshold());
        json.addProperty("orangeThresholdPrecipitation", model.getOrangePrecipitationThreshold());
        json.addProperty("redThresholdPrecipitation", model.getRedPrecipitationThreshold());
        json.addProperty("yellowThresholdPrecipitation", model.getYellowPrecipitationThreshold());

        boolean isFirstSemester = model.getMonthlyData().getFirst().isFirstSemester(); // suposición válida
        json.addProperty("stage", isFirstSemester ? 1 : 2);

        for (MonthlyData data : model.getMonthlyData()) {
            String month = data.getMonth().toLowerCase();
            json.addProperty(month + "DataGrade", data.getGrade());
            json.addProperty(month + "DataPercent", data.getPercent());
        }
        return json;
    }

    private JsonObject buildCodeListConfig(ListCodeDTO dto) throws IllegalAccessException {
        JsonObject json = new JsonObject();
        for (Field field : dto.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            json.addProperty(field.getName(), (Integer) field.get(dto));
        }
        return json;
    }

    private boolean generateFile(JsonObject jsonObject, Path path) {
        this.configFileGenerator.generate(jsonObject, path);
        return true;
    }
}
