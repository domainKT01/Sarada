package com.solproe.service.APIs;

import com.solproe.business.gateway.ApiCommandInterface;
import com.solproe.business.gateway.RequestInterface;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;


@Module
public class CommandModule {


    @Provides
    @IntoMap
    @StringKey("request")
    public ApiCommandInterface provideApiCommandInterface(RequestInterface requestInterface, String commandName) {
        if (commandName.equals("get")) {
            return new GetRequestApi(requestInterface);
        }
        else {
            return new PostRequestApi();
        }
    }


    @Provides
    @IntoMap
    @StringKey("apiCommandInvoker")
    public ApiCommandInvoker provideApiCommandInvoker(String data, String baseUrl) {
        return new ApiCommandInvoker();
    }


    @Provides
    public String provideString() {
        return new String();
    }
}
