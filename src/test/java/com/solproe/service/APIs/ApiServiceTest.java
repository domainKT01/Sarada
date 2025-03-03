package com.solproe.service.APIs;

import com.google.gson.JsonObject;
import com.solproe.business.gateway.ApiCommandInterface;
import com.solproe.business.gateway.RequestInterface;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiServiceTest implements RequestInterface {


    @Test
    public void createRequest() {
        ApiCommandInvoker apiCommandInvoker = new ApiCommandInvoker();
        apiCommandInvoker.setRequestInterface(this);
        ApiCommandInterface apiCommandInterface = new GetRequestApi(this);
        ApiService apiService = new ApiService(apiCommandInvoker, this);
        apiService.setApiCommandInterface(apiCommandInterface);
        apiService.doRequest("");
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