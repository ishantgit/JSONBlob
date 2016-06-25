package com.example.ishant.jsonblob.restApi;

import com.example.ishant.jsonblob.models.responses.ExpenseListResponse;
import com.example.ishant.jsonblob.utils.URIConstants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Ishant Rana on 23/06/16.
 */
public interface RestApiService {

    @GET(URIConstants.GET_LIST)
    Call<ExpenseListResponse> getExpensesList();

    @POST(URIConstants.GET_LIST)
    Call<ExpenseListResponse> verifyExpenseListObject(@Body ExpenseListResponse expenseListResponse);

}
