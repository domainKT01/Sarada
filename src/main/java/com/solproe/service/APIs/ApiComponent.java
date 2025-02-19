package com.solproe.service.APIs;


import dagger.Component;

@Component(modules = {CommandModule.class})
public interface ApiComponent {
    ApiCommandInvoker getApiCommandInvoker();
}
