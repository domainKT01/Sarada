package com.solproe.business.usecase;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.solproe.business.repository.ReadConfigFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class ReadConfigFileUseCase  {
    private ReadConfigFile readConfigFile;
    private String type;


    public void setReadInterface(ReadConfigFile configFile) {
        this.readConfigFile = configFile;
    }

    public JsonObject readConfigFile(String type) {
        try {
            this.type = type;
            if (type.equalsIgnoreCase("threshold") || type.equalsIgnoreCase("monthlyThreshold") ||
                    type.equalsIgnoreCase("listCode")) {
                String path = Objects.requireNonNull(getClass().getResource("/configFiles/")).getPath() +
                        this.type + ".json";
                try (FileInputStream fs = new FileInputStream(this.readConfigFile.readFile(path))) {
                    InputStreamReader inputStreamReader = new InputStreamReader(fs);
                    JsonObject jsonObject = JsonParser.parseReader(inputStreamReader).getAsJsonObject();
                    return jsonObject;
                }
                catch (IOException e) {
                    System.out.println("exception read use case:" + e.getMessage());
                    throw new RuntimeException(new IOException());
                }
            }
        }
        catch (NullPointerException e) {
            System.out.println("null read use case");
            throw new RuntimeException(new NullPointerException());
        }
        return null;
    }
}
