package com.solproe.service.APIs;

import com.google.gson.JsonObject;
import com.solproe.business.gateway.ApiCommandInterface;
import com.solproe.business.gateway.RequestInterface;
import javax.inject.Inject;
import okhttp3.Response;

public class ApiCommandInvoker implements RequestInterface {

    private ApiCommandInterface command;
    private RequestInterface requestInterface;


    @Inject
    public ApiCommandInvoker() {
    }


    public void setRequestInterface(RequestInterface requestInterface) {
        this.requestInterface = requestInterface;
    }


    public void setCommand(ApiCommandInterface command) {
        this.command = command;
    }

    public void setAnyParameter(Object parameter){
        this.command.setAnyParameter(parameter);
    }

    @Override
    public void doRequest(String baseUrl) {

    }

    public void executeCommand() {
        System.out.println("execute command");
        this.command.execute();
    }


    @Override
    public void failedResponse(Response response) {
        this.requestInterface.failedResponse(response);
    }

    @Override
    public void successResponse(JsonObject jsonObject) {
        System.out.println(jsonObject);
        this.requestInterface.successResponse(jsonObject);
    }
}
