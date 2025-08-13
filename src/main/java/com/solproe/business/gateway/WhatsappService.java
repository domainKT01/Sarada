package com.solproe.business.gateway;

import com.google.gson.JsonObject;

public interface WhatsappService {

    void sendMessage();
    void setToken(String token);
    void setPhoneNumberId(String phoneNumberId);
    void setJsonResource(JsonObject... jsonObject);
}
