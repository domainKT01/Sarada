package com.solproe.service.APIs;

import com.google.gson.JsonObject;
import com.solproe.business.gateway.ApiCommandInterface;
import com.solproe.business.gateway.RequestInterface;
import javax.inject.Inject;
import okhttp3.Request;
import okhttp3.Response;

public class GetRequestApi implements ApiCommandInterface, RequestInterface {
    private Request request;
    private final RequestApi requestApi;
    private final RequestInterface requestInterface;
    private String baseUrl;


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
        catch (Exception e) {
            System.out.println(e.getMessage() + " get request exception");
            throw new RuntimeException();
        }
    }

    @Override
    public void setAnyParameter(Object parameter) {
        this.baseUrl = parameter.toString();
        System.out.println(this.baseUrl);
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
