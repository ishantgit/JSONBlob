package com.example.ishant.jsonblob.restApi;

import com.example.ishant.jsonblob.utils.URIConstants;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ishant Rana on 23/06/16.
 */
public class RestApiClient {

    private static RestApiService restApiService;

    private RestApiClient(){

    }

    public static RestApiService getRestApiService(){
        if(restApiService == null){
            Retrofit retrofit = setUpUnauthorisedClient();
            restApiService = retrofit.create(RestApiService.class);
        }
        return restApiService;
    }

    private static Retrofit setUpUnauthorisedClient() {

        // Add the interceptor to OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(URIConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory
                        .create(new GsonBuilder()
                                .serializeNulls()
                                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.STATIC, Modifier.TRANSIENT)
                                .create()))
                .client(client)
                .build();
    }

}
