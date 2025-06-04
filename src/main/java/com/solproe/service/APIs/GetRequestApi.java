package com.solproe.service.APIs;

import com.google.gson.JsonObject;
import com.solproe.business.gateway.ApiCommandInterface;
import com.solproe.business.gateway.RequestInterface;
import javax.inject.Inject;

import com.solproe.util.logging.ErrorLogger;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ConnectException;

public class GetRequestApi implements ApiCommandInterface, RequestInterface {
    private Request request;
    private final RequestApi requestApi;
    private final RequestInterface requestInterface;
    private String baseUrl;
    private int count = 0;


    @Inject
    public GetRequestApi(RequestInterface requestInterface) {
        this.requestApi = new RequestApi(this);
        this.requestInterface = requestInterface;
    }

    @Override
    public void execute() {
        try {
            this.request = new Request.Builder()
                    .url(baseUrl)
                    .build();
            this.requestApi.sendRequest(this.request);
        }
        catch (IOException e) {
            if (this.count < 3) {
                try {
                    ++this.count;
                    Thread.sleep(2000);
                    System.out.println("try #" + this.count);
                    execute();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                ErrorLogger.log("get request exception: ", e);
            }
        }
    }

    @Override
    public void setAnyParameter(@NotNull Object parameter) {
        this.baseUrl = parameter.toString();
        this.request = new Request.Builder()
                .url(this.baseUrl)
                .build();
    }

    @Override
    public void doRequest(String baseUrl) {
    }

    @Override
    public void successResponse(JsonObject jsonObject) {
        this.requestInterface.successResponse(jsonObject);
    }


    @Override
    public void failedResponse(Response response) {
        System.out.println("failed get request");
        this.requestInterface.failedResponse(response);
    }
}
