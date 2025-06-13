package com.solproe.business.repository;

@FunctionalInterface
public interface SuccessCallback<T> {
    void onSuccess(T result);
}
