package com.solproe.service.manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ManagerConfigFile {

    private final String name;
    private final String path;


    public ManagerConfigFile(String name, String path) {
        this.name = name;
        this.path = path;
    }


    public boolean addApisFile(JsonObject jsonObject) {

        if (Files.exists(Paths.get(this.path + this.name))) {
            System.out.println("exists");
        }
        else {
            System.out.println("not exists");
        }

        try {
            File file = new File(this.path + this.name);
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            Gson gson = new Gson();
            JsonObject jj = gson.fromJson(br, JsonObject.class);
            JsonObject jo = new JsonObject();
            JsonObject newJson = new JsonObject();
            newJson.addProperty("url", jsonObject.get("url").toString().replace("\"", ""));
            jo.add(jsonObject.get("name").toString().replace("\"", ""), newJson);

            OutputStream outputStream = new FileOutputStream(this.path + this.name);

            byte[] bytes = jo.getAsJsonObject().toString().getBytes(StandardCharsets.UTF_8);
            outputStream.write(bytes);

            return true;
        } catch (IOException e) {
            System.out.println("error");
            throw new RuntimeException(e);
        }
    }
}
