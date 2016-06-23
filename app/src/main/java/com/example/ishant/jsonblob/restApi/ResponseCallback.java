package com.example.ishant.jsonblob.restApi;

/**
 * Created by Ishant Rana on 23/06/16.
 */
public interface ResponseCallback<T> {
    void onSuccess(T response);
    void onFailure(String status);
}
