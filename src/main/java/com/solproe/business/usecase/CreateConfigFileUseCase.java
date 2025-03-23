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
            try {
                ConfigFileThreshold configFileThreshold = (ConfigFileThreshold) object;
                JsonObject jsonObject = this.createConfigFileThreshold(configFileThreshold);
                String path = Objects.requireNonNull(getClass().getResource("/configFiles/")).getPath() +
                        this.type + ".json";
                this.configFileGenerator.generate(jsonObject, path);
                return true;
            }
            catch (Exception e) {
                System.out.println("use case exception");
                e.printStackTrace();
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


    public boolean createConfigFileMonthly(double[] data) {
        try {
            JsonObject jsonObject = this.createMonthlyConfigFileThreshold(data);
            String path = Objects.requireNonNull(getClass().getResource("/configFiles/")).getPath() +
                    this.type + ".json";
            this.configFileGenerator.generate(jsonObject, path);
            System.out.println("createConfigFileMonthly ###########");
            return true;
        }
        catch (Exception e) {
            System.out.println("use case exception");
            e.printStackTrace();
        }
        return false;
    }

    public JsonObject createMonthlyConfigFileThreshold(double[] data) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("orangeThresholdTemperature", data[0]);
        jsonObject.addProperty("redThresholdTemperature", data[1]);
        jsonObject.addProperty("orangeThresholdPrecipitation", data[2]);
        jsonObject.addProperty("redThresholdPrecipitation", data[3]);
        jsonObject.addProperty("januaryDataGrade", data[4]);
        jsonObject.addProperty("januaryDataPercent", data[5]);
        jsonObject.addProperty("februaryDataGrade", data[6]);
        jsonObject.addProperty("februaryDataPercent", data[7]);
        jsonObject.addProperty("marchDataGrade", data[8]);
        jsonObject.addProperty("marchDataPercent", data[9]);
        jsonObject.addProperty("aprilDataGrade", data[10]);
        jsonObject.addProperty("aprilDataPercent", data[11]);
        jsonObject.addProperty("mayDataGrade", data[12]);
        jsonObject.addProperty("mayDataPercent", data[13]);
        jsonObject.addProperty("juneDataGrade", data[14]);
        jsonObject.addProperty("juneDataPercent", data[15]);
        jsonObject.addProperty("julyDataGrade", data[16]);
        jsonObject.addProperty("julyDataPercent", data[17]);
        jsonObject.addProperty("augustDataGrade", data[18]);
        jsonObject.addProperty("augustDataPercent", data[19]);
        jsonObject.addProperty("septemberDataGrade", data[20]);
        jsonObject.addProperty("septemberDataPercent", data[21]);
        jsonObject.addProperty("octoberDataGrade", data[22]);
        jsonObject.addProperty("octoberDataPercent", data[23]);
        jsonObject.addProperty("novemberDataGrade", data[24]);
        jsonObject.addProperty("novemberDataPercent", data[25]);
        jsonObject.addProperty("decemberDataGrade", data[26]);
        jsonObject.addProperty("decemberDataPercent", data[27]);
        return jsonObject;
    }
}
