package com.solproe.business.usecase;

import com.google.gson.JsonObject;
import com.solproe.business.repository.ReadConfigFile;
import com.solproe.service.config.ConfigPropertiesGenerator;
import com.solproe.service.config.ReadJsonConfigFile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadConfigFileUseCaseTest {


    @Test
    public void read() {
        ReadConfigFileUseCase readConfigFileUseCase = new ReadConfigFileUseCase();
        ReadConfigFile readConfigFile = new ReadJsonConfigFile();
        readConfigFileUseCase.setReadInterface(readConfigFile);
        String[] dirName = {
                "Sarada"
        };
        JsonObject jsonObject = readConfigFileUseCase.readConfigFile(new ConfigPropertiesGenerator("threshold.json", dirName)
                .getAppConfigPath());
        System.out.println("test: " + jsonObject);
    }
}