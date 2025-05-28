package com.solproe.business.usecase;

import com.google.gson.JsonObject;
import com.solproe.business.domain.ConfigFileThreshold;
import com.solproe.business.dto.ListCodeDTO;
import com.solproe.business.dto.MonthlyData;
import com.solproe.business.dto.MonthlyThresholdInputModel;
import com.solproe.business.repository.ConfigFileGenerator;
import com.solproe.business.repository.ConfigPropertiesGeneratorInterface;
import com.solproe.util.ValidateLoad;
import java.lang.reflect.Field;
import java.nio.file.Path;

public class CreateConfigFileUseCase {
    private final ConfigFileGenerator configFileGenerator;


    public CreateConfigFileUseCase(ConfigFileGenerator configFileGenerator) {
       this.configFileGenerator = configFileGenerator;
    }


    public boolean createFileConfig(Object object, ConfigPropertiesGeneratorInterface config) {
        try {
            ConfigFileThreshold configFileThreshold = (ConfigFileThreshold) object;
            JsonObject jsonObject = this.createConfigFileThreshold(configFileThreshold);
            Path path = config.getAppConfigPath();
            this.configFileGenerator.generate(jsonObject, path);
            return true;
        } catch (Exception e) {
            System.out.println("use case exception: " + e.getMessage());
        }
        return false;
    }


    public JsonObject createConfigFileThreshold(ConfigFileThreshold data) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("forestFireThresholdOrange", data.getForestFireThresholdOrange());
        jsonObject.addProperty("forestFireThresholdRed", data.getForestFireThresholdRed());
        jsonObject.addProperty("precipitationThresholdOrange", data.getPrecipitationThresholdOrange());
        jsonObject.addProperty("precipitationThresholdRed", data.getPrecipitationThresholdRed());
        jsonObject.addProperty("windThresholdOrange", data.getWindThresholdOrange());
        jsonObject.addProperty("windThresholdRed", data.getWindThresholdRed());
        jsonObject.addProperty("precipitationRainPercentOrange", data.getPrecipitationRainPercentOrange());
        jsonObject.addProperty("precipitationRainPercentRed", data.getPrecipitationRainPercentRed());
        jsonObject.addProperty("ceraunicosThresholdRed", data.getCeraunicosThresholdRed());
        jsonObject.addProperty("projectName", data.getIdProject());
        jsonObject.addProperty("stateName", data.getStateName());
        jsonObject.addProperty("cityName", data.getCityName());
        jsonObject.addProperty("idProject", data.getIdProject());
        jsonObject.addProperty("sciBoss", data.getSciBoss());
        jsonObject.addProperty("sciBossContact", data.getSciBossContact());
        jsonObject.addProperty("auxiliarSciBoss", data.getAuxiliarSciBoss());
        jsonObject.addProperty("auxiliarSciBossContact", data.getAuxiliarSciBossContact());
        return jsonObject;
    }


    public boolean createConfigFileMonthly(MonthlyThresholdInputModel model, ConfigPropertiesGeneratorInterface config) {
        try {
            JsonObject jsonObject = this.createMonthlyConfigFileThreshold(model);
            Path path = config.getAppConfigPath();
            this.configFileGenerator.generate(jsonObject, path);
            return true;
        } catch (Exception e) {
            System.out.println("Use case exception:");
        }
        return false;
    }


    public JsonObject createMonthlyConfigFileThreshold(MonthlyThresholdInputModel model) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("orangeThresholdTemperature", model.getOrangeTemperatureThreshold());
        jsonObject.addProperty("redThresholdTemperature", model.getRedTemperatureThreshold());
        jsonObject.addProperty("orangeThresholdPrecipitation", model.getOrangePrecipitationThreshold());
        jsonObject.addProperty("redThresholdPrecipitation", model.getRedPrecipitationThreshold());
        jsonObject.addProperty("yellowThresholdPrecipitation", model.getYellowPrecipitationThreshold());

        boolean isFirstSemester = model.getMonthlyData().getFirst().isFirstSemester(); // suposición
        jsonObject.addProperty("stage", isFirstSemester ? 1 : 2);

        for (MonthlyData data : model.getMonthlyData()) {
            String month = data.getMonth().toLowerCase();
            jsonObject.addProperty(month + "DataGrade", data.getGrade());
            jsonObject.addProperty(month + "DataPercent", data.getPercent());
        }
        return jsonObject;
    }


    public boolean createConfigCodeList(ListCodeDTO listCodeDTO, ConfigPropertiesGeneratorInterface config) {
        try {
            Class<?> dto = listCodeDTO.getClass();
            JsonObject jsonObject = new JsonObject();

            for (Field field : dto.getDeclaredFields()) {
                field.setAccessible(true);
                jsonObject.addProperty(field.getName(), (Integer) field.get(listCodeDTO));
            }
            Path path = config.getAppConfigPath();
            this.configFileGenerator.generate(jsonObject, path);
            return true;
        }
        catch (IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    public boolean createConfigPropertiesFile(ConfigPropertiesGeneratorInterface config) {
        return false;
    }
}
