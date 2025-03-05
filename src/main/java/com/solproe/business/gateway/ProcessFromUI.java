package com.solproe.business.gateway;

public interface ProcessFromUI {

    void execute();
    void success(Object res);
    void failed(Object failed);
}
