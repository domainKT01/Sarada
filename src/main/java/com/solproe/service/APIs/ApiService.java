package com.solproe.service.APIs;

import com.google.gson.JsonObject;
import com.solproe.business.gateway.ApiCommandInterface;
import com.solproe.business.gateway.RequestInterface;
import javax.inject.Inject;
import okhttp3.Response;

public class ApiService implements RequestInterface {
    private final ApiCommandInvoker invoker;
    private ApiCommandInterface apiCommandInterface;
    private final RequestInterface requestInterface;


    @Inject
    public ApiService(ApiCommandInvoker invoker, RequestInterface requestInterface) {
        this.invoker = invoker;
        this.requestInterface = requestInterface;
    }


    public void setApiCommandInterface(ApiCommandInterface apiCommandInterface) {
        this.apiCommandInterface = apiCommandInterface;
        this.invoker.setCommand(this.apiCommandInterface);
    }

    @Override
    public void failedResponse(Response response) {
        System.out.println("failed...");
    }

    @Override
    public void successResponse(JsonObject jsonObject) {
        this.requestInterface.successResponse(jsonObject);
    }

    @Override
    public void doRequest(String baseUrl) {
        this.invoker.setAnyParameter(baseUrl);
        this.invoker.setRequestInterface(this);
        this.invoker.setCommand(this.apiCommandInterface);
        this.invoker.executeCommand();
    }
}
