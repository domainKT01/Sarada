package com.solproe.service.config;


import com.solproe.business.repository.ConfigFileGenerator;

public class ConfigFileGeneratorFactory {
    public static ConfigFileGenerator getGenerator(String fileType) {

        if (fileType.equalsIgnoreCase("json")) {
            return new JsonConfigFileGenerator();
        }
        throw new IllegalArgumentException("Format no supported");
    }
}
