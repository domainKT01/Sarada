package com.solproe.service.APIs;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.solproe.business.gateway.RequestApiService;
import com.solproe.business.gateway.RequestInterface;
import com.solproe.util.logging.ErrorLogger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.IOException;
import java.net.ConnectException;

public class RequestApi {
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final RequestInterface requestInterface;


    public RequestApi(RequestInterface requestInterface) {
        this.requestInterface = requestInterface;
    }


    void sendRequest(Request request) throws IOException {

        try (Response response = this.okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                ResponseBody responseBody = response.body();
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(responseBody.string(), JsonObject.class);
                this.onResponse(jsonObject);
            }
            else {
                this.failedResponse(response);
            }
        }
        catch (ConnectException e) {
            System.out.println(e.getMessage() + " api request exception");
            ErrorLogger.log("request api exc: ", e);
            throw new IOException();
        }
    }


    void onResponse(JsonObject jsonObject) {
        this.requestInterface.successResponse(jsonObject);
    }


    void failedResponse(Response response) {
        System.out.println("request api exc: " + response.message() + " code: " + response.code());
        this.requestInterface.failedResponse(response);
    }
}
