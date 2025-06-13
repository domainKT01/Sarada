package com.solproe.business.repository;

@FunctionalInterface
public interface ErrorCallback {
    void onError(Throwable t);
}
