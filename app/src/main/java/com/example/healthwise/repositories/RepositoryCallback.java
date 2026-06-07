package com.example.healthwise.repositories;

public interface RepositoryCallback<T> {

    void onSuccess(T result);

    void onError(Exception exception);
}
