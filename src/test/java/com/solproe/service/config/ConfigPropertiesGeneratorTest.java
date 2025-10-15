package com.solproe.service.config;

import com.solproe.business.repository.ConfigPropertiesGeneratorInterface;
import org.junit.jupiter.api.Test;

import java.util.jar.Manifest;

import static org.junit.jupiter.api.Assertions.*;

class ConfigPropertiesGeneratorTest {

    @Test
    public void createPropertyFile() {
        String[] dirName = {
                ".Sarada"
        };
        ConfigPropertiesGeneratorInterface config = new ConfigPropertiesGenerator("config.properties", dirName);
        boolean bool = config.createPropertyFile();
    }
}