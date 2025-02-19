package com.solproe.service.APIs;

import com.google.gson.JsonObject;
import com.solproe.business.gateway.ApiCommandInterface;
import com.solproe.business.gateway.RequestInterface;
import javax.inject.Inject;
import okhttp3.Request;
import okhttp3.Response;

public class GetRequestApi implements ApiCommandInterface, RequestInterface {

    private final Request request;
    private final RequestApi requestApi;
    private final RequestInterface requestInterface;


    @Inject
    public GetRequestApi(String baseUrl, RequestInterface requestInterface) {
        this.request = new Request.Builder()
                .url(baseUrl)
                .build();

        this.requestApi = new RequestApi(this);
        this.requestInterface = requestInterface;
    }

    @Override
    public void execute() {
        try {
            this.requestApi.sendRequest(this.request);
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
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
        this.requestInterface.failedResponse(response);
    }
}
