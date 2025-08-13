package com.solproe.service.record;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RecordService {
    private Path path;
    private final Gson gson = new Gson();


    public void setPath(Path path) {
        this.path = path;
    }

    public void appendJsonToFile(JsonObject jsonObject, String field, JsonObject contact) {
        // 👇 Validación antes de leer
        try {
            ensureFileExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
                System.out.println("exist record: " + field);
                array = root.getAsJsonArray(field);
                System.out.println("arr: "  + field + " -> " + array);
                // 3. Agregar el nuevo elemento al array
                boolean contains = false;
                for (int i = 0; i < array.size(); i++) {
                    if (array.get(i).getAsString().equalsIgnoreCase(jsonObject.get(field).getAsString())) {
                        contains = true;
                    }
                }

                if (!contains) {
                    array.add(jsonObject.get(field));
                    System.out.println("fake");
                }
                root.remove(field);
                root.add(field, array);
            } else {
                array = new JsonArray();
                array.add(contact.get("sciBossContact"));
                array.add(jsonObject.get(field));
                root.add(field, array);
            }

            // 4. Sobrescribir el archivo completo con el contenido actualizado
            try {
                write(root);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage() + " error-----");
            throw new RuntimeException("Error al editar el archivo JSON", e);
        }
    }

    private void write(JsonObject jsonObject) {
        // 4. Sobrescribir el archivo completo con el contenido actualizado
        try (FileWriter writer = new FileWriter(path.toFile())) {
            gson.toJson(jsonObject, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void ensureFileExists(Path path) throws IOException {
        File file = path.toFile();

        // Asegurar que el directorio exista
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            Files.createDirectories(parentDir.toPath());
        }

        // Si el archivo no existe, crearlo vacío con contenido {}
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("{}");
            }
        }
    }
}
