package com.solproe.business.gateway;

import com.google.gson.JsonObject;
import okhttp3.Response;

public interface RequestInterface {

    void doRequest(String baseUrl);

    void successResponse(JsonObject jsonObject);

    void failedResponse(Response response);
}
