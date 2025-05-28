package com.solproe.service.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.solproe.business.repository.ConfigFileGenerator;

import java.io.*;
import java.nio.file.Path;

public class JsonConfigFileGenerator implements ConfigFileGenerator {


    @Override
    public void generate(JsonObject data, Path path) {
        Gson gson = new Gson();
        JsonObject finalJson;

        File file = new File(path.toUri());
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                JsonObject existing = gson.fromJson(reader, JsonObject.class);
                finalJson = mergeJson(existing, data); // Combina data con lo existente
            } catch (IOException e) {
                throw new RuntimeException("Error leyendo archivo existente: " + e.getMessage(), e);
            }
        } else {
            finalJson = data;
        }

        try (Writer writer = new FileWriter(file)) {
            gson.toJson(finalJson, writer);
            System.out.println("generated...");
        } catch (IOException e) {
            throw new RuntimeException("Error escribiendo archivo JSON: " + e.getMessage(), e);
        }
    }

    private JsonObject mergeJson(JsonObject original, JsonObject updates) {
        for (String key : updates.keySet()) {
            original.add(key, updates.get(key)); // Reemplaza si existe o agrega si no
        }
        return original;
    }
}
