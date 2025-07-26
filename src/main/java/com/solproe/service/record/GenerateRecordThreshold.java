package com.solproe.service.record;

import com.google.gson.JsonObject;
import com.solproe.service.config.ConfigPropertiesGenerator;
import java.nio.file.Path;

public class GenerateRecordThreshold {
    public JsonObject contact;


    public void setContact(JsonObject dataContact) {
        this.contact = dataContact;
    }

    public void addRecord(JsonObject jsonObject) {
        RecordService recordService = new RecordService();
        ConfigPropertiesGenerator generatePath = new ConfigPropertiesGenerator();
        generatePath.setDirName(new String[] {".Sarada"});
        generatePath.setFilename("recordThreshold.json");
        Path path = generatePath.getAppConfigPath();
        recordService.setPath(path);
        try {
            recordService.appendJsonToFile(jsonObject, jsonObject.keySet().iterator().next(), this.contact);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
