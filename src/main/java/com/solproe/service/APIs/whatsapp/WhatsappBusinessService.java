package com.solproe.service.APIs.whatsapp;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.solproe.business.dto.AlertThreshold;
import com.solproe.business.gateway.WhatsappService;
import com.solproe.util.ValidateDate;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class WhatsappBusinessService implements WhatsappService {

    private String token = "EAARsQH7A6kIBPBPQ0ZBeqIhPLNsYGO7qB7zAouGGvWioXqDgM59KpB9mCPGyxgO6zdFS0bpxqapy4rZCgEkZCUHoGEz8MOfU6fbn2f3srfPTbWKbVO4FfqbBS8ag28MkYutpuVuOE1XxseJkxZBFAxkDNQGeK80jZCFEOHzc9FJ0HLZAZAZCpMzdkZCbVuyqKK3ZBtYAqtpZA64iujpoY8wZAwZDZD";
    private String phoneNumberId = "105793722275227";
    private JsonObject jsonObject;
    private JsonObject record;


    @Override
    public void sendMessage() {
        String contact = "57" + this.jsonObject.get("sciBossContact").getAsString();
        HttpClient client = HttpClient.newHttpClient();
        String header = "";
        String action = "";
        String date = "";

        for (String key : this.record.keySet()) {
            if (new ValidateDate().compareActualDate(key)) {
                date = key;
                for (JsonElement data : this.record.get(key).getAsJsonArray()) {
                    action = switch (data.getAsString()) {
                        case "forestFireThresholdOrange" -> {
                            header = "Naranja Incendio Forestal";
                            yield AlertThreshold.ORANGE_FIRE_FOREST_THRESHOLD.getMessage();
                        }
                        case "forestFireThresholdRed" -> {
                            header = "Roja Incendio Forestal";
                            yield AlertThreshold.RED_FIRE_FOREST_THRESHOLD.getMessage();
                        }
                        case "precipitationRainPercentOrange" -> {
                            header = "Naranja Deslizamiento";
                            yield AlertThreshold.ORANGE_MASS_MOVEMENT_THRESHOLD.getMessage();
                        }
                        case "precipitationRainPercentRed" -> {
                            header = "Roja Deslizamiento";
                            yield AlertThreshold.RED_MASS_MOVEMENT_THRESHOLD.getMessage();
                        }
                        case "windThresholdOrange" -> {
                            header = "Naranja Vendaval";
                            yield AlertThreshold.ORANGE_WIND_SPEED_THRESHOLD.getMessage();
                        }
                        case "windThresholdRed" -> {
                            header = "Roja Vendaval";
                            yield AlertThreshold.RED_WIND_SPEED_THRESHOLD.getMessage();
                        }
                        default -> action;
                    };
                }
            }
        }

        String json = """
        {
          "messaging_product": "whatsapp",
          "to": "%s",
          "type": "template",
          "template": {
            "name": "alert_threshold",
            "language": { "code": "en" },
            "components": [
              {
                "type": "header",
                "parameters": [
                  {
                    "type": "text",
                    "text": "%s"
                  }
                ]
              },
              {
                "type": "body",
                "parameters": [
                  {
                    "type": "text",
                    "text": "%s"
                  },
                  {
                    "type": "text",
                    "text": "%s"
                  }
                ]
              }
            ]
          }
        }
        """.formatted(contact, header, action, date);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://graph.facebook.com/v22.0/" + this.phoneNumberId + "/messages"))
                .header("Authorization", "Bearer " + this.token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200 && response.statusCode() != 201) {
                throw new RuntimeException("Error enviando mensaje: código " + response.statusCode() +
                        ", respuesta: " + response.body());
            }
            else {
                System.out.println("code: " + response.body());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error enviando mensaje a WhatsApp", e);
        }
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void setPhoneNumberId(String numberId) {
        this.phoneNumberId = numberId;
    }

    @Override
    public void setJsonResource(JsonObject... jsonObject) {
        this.jsonObject = jsonObject[0];
        if (!jsonObject[1].isEmpty()) {
            this.record = jsonObject[1];
        }
    }
}
