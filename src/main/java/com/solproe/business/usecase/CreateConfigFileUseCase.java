package com.solproe.business.usecase;

import com.google.gson.JsonObject;
import com.solproe.business.domain.ConfigFileThreshold;
import com.solproe.business.repository.ConfigFileGenerator;
import java.util.Objects;

public class CreateConfigFileUseCase {
    private final String type;
    private final ConfigFileGenerator configFileGenerator;


    public CreateConfigFileUseCase(String type, ConfigFileGenerator configFileGenerator) {
       this.type = type;
       this.configFileGenerator = configFileGenerator;
    }


    public boolean createFileConfig(Object object) {
        if (this.type.equals("threshold")) {
            System.out.println("threshold::");
            try {
                ConfigFileThreshold configFileThreshold = (ConfigFileThreshold) object;
                System.out.println(configFileThreshold.getAuxiliarSciBoss());
                JsonObject jsonObject = this.createConfigFileThreshold(configFileThreshold);
                String path = Objects.requireNonNull(getClass().getResource("/configFiles/")).getPath() +
                        this.type + ".json";
                System.out.println(path);
                this.configFileGenerator.generate(jsonObject, path);
                return true;
            }
            catch (Exception e) {
                System.out.println("use case exception");
                e.printStackTrace();
                return false;
            }
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


    public JsonObject createConfigFileCodeList(Object data) {
        return null;
    }
}
