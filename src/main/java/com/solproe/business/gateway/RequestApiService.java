package com.solproe.business.gateway;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RequestApiService {

    @GET()
    Call<JsonObject> doRequest(@Url String url);
}
