package com.solproe.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObjectToMap {

    public static Map<String, Object> convertJsonObjectToMap(JsonObject jsonObject) {
        Map<String, Object> map = new LinkedHashMap<>();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            JsonElement value = entry.getValue();

            if (value.isJsonPrimitive()) {
                map.put(entry.getKey(), value.getAsString());
            } else if (value.isJsonObject()) {
                map.put(entry.getKey(), convertJsonObjectToMap(value.getAsJsonObject())); // Recursivo si es otro objeto
            } else if (value.isJsonArray()) {
                map.put(entry.getKey(), value.getAsJsonArray()); // Puedes también procesar el array si quieres
            } else if (value.isJsonNull()) {
                map.put(entry.getKey(), null);
            }
        }

        return map;
    }
}
