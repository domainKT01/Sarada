package com.solproe.service.config;

import com.solproe.business.repository.ManagerProperties;

import java.io.*;
import java.util.Map;
import java.util.Properties;

public class ConfigPropertiesGenerator implements ManagerProperties {
    private final String path;


    public ConfigPropertiesGenerator(String path) {
        this.path = path;
    }


    @Override
    public boolean createPropertyFile() {
        Properties prop = new Properties();
        try (OutputStream outputStream = new FileOutputStream(this.path)) {
            Reader reader = new FileReader(this.path);
            prop.load(reader);
            reader.close();
            prop.store(outputStream, "created properties file...");
            return true;
        } catch (IOException e) {
            System.out.println("properties exception: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addProperties(String[][] properties) {
        try {
            InputStream input = new FileInputStream(this.path);
            Properties prop = new Properties();
            prop.load(input);
            input.close();
            for (String[] property : properties) {
                if (property[0] != null && property[1] != null) {
                    prop.setProperty(property[0], property[1]);
                }
            }
            OutputStream outputStream = new FileOutputStream(this.path);
            prop.store(outputStream, "added properties successful...");
        } catch (IOException e) {
            System.out.printf("add properties exception: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return false;
    }
}
