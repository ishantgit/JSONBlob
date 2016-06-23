package com.example.ishant.jsonblob.restApi;

import com.google.gson.JsonSyntaxException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ishant Rana on 23/06/16.
 */
public class NetworkManager<T> {
    private final ResponseCallback<T> callback;


    public NetworkManager(ResponseCallback<T> callback) {
        this.callback = callback;
    }

    /**
     *  This method is used to get data from the server. Both GET and POST request.
     *  Implement this method in the respective controller
     *
     */
    public void execute(Call<T> call) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if(response.body() != null && response.isSuccessful()) {
                    sendSuccessResponse(response.body());
                }else{
                    sendServerError("Server Error"+ response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                // Log error here since request failed i.e. network connection lost
                sendOtherError(t);
            }
        });
    }

    /**
     * this method will send the successful response returned from the server
     *
     * @param response {@link }
     */
    private void sendSuccessResponse(T response) {
        callback.onSuccess(response);
    }

    /**
     * this method will send server error of any type
     * and this error will be used to show the appropriate error message to the user if required.
     *
     *
     */
    private void sendServerError(String status) {
        callback.onFailure(status);
    }

    /**
     * this method will send any error except server errors e.g JSON parse exception error, network error, etc
     * and this error will be used to show the appropriate error message to the user if required.
     *
     * @param t {@link Throwable}
     */
    public final void sendOtherError(Throwable t) {
        if(t instanceof JsonSyntaxException) {
            callback.onFailure(t.getMessage());
        } else {
            callback.onFailure(t.getMessage());
        }
    }





}
