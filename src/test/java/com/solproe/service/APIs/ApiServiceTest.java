package com.solproe.service.APIs;

import com.google.gson.JsonObject;
import com.solproe.business.gateway.ApiCommandInterface;
import com.solproe.business.gateway.RequestInterface;
import okhttp3.Response;

import static org.junit.jupiter.api.Assertions.*;

class ApiServiceTest implements RequestInterface {


    public void createRequest() {
        ApiCommandInvoker apiCommandInvoker = new ApiCommandInvoker();
        ApiCommandInterface apiCommandInterface = new GetRequestApi("", this);
        apiCommandInvoker.setRequestInterface(this);
        apiCommandInvoker.setCommand(apiCommandInterface);
        apiCommandInvoker.executeCommand();
    }

    @Override
    public void doRequest(String baseUrl) {

    }

    @Override
    public void successResponse(JsonObject jsonObject) {

    }

    @Override
    public void failedResponse(Response response) {

    }
}