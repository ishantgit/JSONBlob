package com.example.ishant.jsonblob.restApi;

import com.example.ishant.jsonblob.models.responses.ExpenseListResponse;
import com.example.ishant.jsonblob.utils.URIConstants;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ishant Rana on 23/06/16.
 */
public interface RestApiService {

    @GET(URIConstants.GET_LIST)
    Call<ExpenseListResponse> getExpensesList();

}
