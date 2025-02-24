package com.solproe.service.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.solproe.business.repository.ConfigFileGenerator;
import org.jetbrains.annotations.Nullable;

import java.io.FileWriter;
import java.io.IOException;

public class JsonConfigFileGenerator implements ConfigFileGenerator {


    @Override
    public void generate(JsonObject jsonObject, @Nullable String filePath) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(jsonObject);
            assert filePath != null;
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(jsonString);
                System.out.println("file was written");
            }
        }
        catch (IOException e) {
            System.out.println("error to write file");
            e.printStackTrace();
        }
    }
}
