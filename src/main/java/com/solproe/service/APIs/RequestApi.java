package com.solproe.service.APIs;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.solproe.business.gateway.RequestInterface;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class RequestApi {

    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final RequestInterface requestInterface;


    public RequestApi(RequestInterface requestInterface) {
        this.requestInterface = requestInterface;
    }


    void sendRequest(Request request) throws IOException {

        try (Response response = this.okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                Gson gson = new Gson();
                assert responseBody != null;
                JsonObject jsonObject = gson.fromJson(responseBody.string(), JsonObject.class);
                this.onResponse(jsonObject);
            }
            else {
                this.failedResponse(response);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IOException();
        }
    }


    void onResponse(JsonObject jsonObject) {
        this.requestInterface.successResponse(jsonObject);
    }


    void failedResponse(Response response) {
        this.requestInterface.failedResponse(response);
    }
}
