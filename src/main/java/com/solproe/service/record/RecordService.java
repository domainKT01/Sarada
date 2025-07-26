package com.solproe.service.record;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class RecordService {
    private Path path;
    private final Gson gson = new Gson();


    public void setPath(Path path) {
        this.path = path;
    }

    public void appendJsonToFile(JsonObject jsonObject, String field, JsonObject contact) {
        try {
            // 1. Leer el archivo existente
            JsonObject root;
            try (FileReader reader = new FileReader(path.toFile())) {
                root = gson.fromJson(reader, JsonObject.class);
                if (root == null) {
                    root = new JsonObject(); // Si el archivo está vacío
                }
            }

            // 2. Obtener o crear el array
            JsonArray array;
            if (root.has(field) && root.get(field).isJsonArray()) {
                array = root.getAsJsonArray(field);
            } else {
                array = new JsonArray();
                array.add(contact.get("sciBossContact"));
                root.add(field, array);
            }

            // 3. Agregar el nuevo elemento al array
            array.add(jsonObject.get(field));

            // 4. Sobrescribir el archivo completo con el contenido actualizado
            try (FileWriter writer = new FileWriter(path.toFile())) {
                gson.toJson(root, writer);
                System.out.println("Archivo JSON actualizado correctamente.");
            }

        } catch (IOException e) {
            throw new RuntimeException("Error al editar el archivo JSON", e);
        }
    }
}
