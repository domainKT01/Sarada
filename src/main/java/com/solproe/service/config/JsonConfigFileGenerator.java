package com.solproe.service.config;

import com.google.gson.JsonObject;
import com.solproe.business.repository.ConfigFileGenerator;
import com.solproe.business.repository.ConfigFileInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class JsonConfigFileGenerator implements ConfigFileGenerator {


    @Override
    public void generate(ConfigFileInterface config, String filePath) {
        try (FileWriter os = new FileWriter(filePath)) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", config.getLocationName());
            jsonObject.addProperty("latitude", config.getLatitude());
            jsonObject.addProperty("longitude", config.getLongitude());
            jsonObject.addProperty("daysCount", config.getDaysCount());

            System.out.println(jsonObject);

            BufferedWriter bufferedWriter = new BufferedWriter(os);
            bufferedWriter.write(jsonObject.toString());
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
