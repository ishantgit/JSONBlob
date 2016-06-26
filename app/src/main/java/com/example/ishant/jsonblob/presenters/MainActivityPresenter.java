package com.example.ishant.jsonblob.presenters;

import com.example.ishant.jsonblob.models.responses.ExpenseListResponse;
import com.example.ishant.jsonblob.restApi.NetworkManager;
import com.example.ishant.jsonblob.restApi.ResponseCallback;
import com.example.ishant.jsonblob.restApi.RestApiClient;
import com.example.ishant.jsonblob.utils.Utils;

import retrofit2.Call;

/**
 * Created by Ishant Rana on 23/06/16.
 */
public class MainActivityPresenter implements Presenter {

    private final MainActivityView view;

    public MainActivityPresenter(MainActivityView view) {
        this.view = view;
    }

    @Override
    public void onResume() {
        view.showProgress();
        showAppropriateLayout();
    }

    public void retryRequest(){
        showAppropriateLayout();
    }

    public void taskRequest(){
        if(Utils.isInternetConnected()){
            getExpenseList();
        }else{
            view.showToastMessage("Internet is not working check connection");
        }
    }

    private void showAppropriateLayout() {
        if(Utils.isInternetConnected()){
            getExpenseList();
        } else {
            view.showNoConnectionView();
        }
    }

    // Network Manager is Generic Class to handle Request
    // GET Request
    private void getExpenseList(){
        Call<ExpenseListResponse> call = RestApiClient.getRestApiService().getExpensesList();
        new NetworkManager<>(new ResponseCallback<ExpenseListResponse>() {
            @Override
            public void onSuccess(ExpenseListResponse response) {
                view.hideProgress();
                view.showExpenseList(response);
            }

            @Override
            public void onFailure(String status) {
                view.showToastMessage(status);
            }

        }).execute(call);
    }

    //POST Request
    public void verifyExpenseObject(ExpenseListResponse expenseResponse){
        Call<ExpenseListResponse> call = RestApiClient.getRestApiService().verifyExpenseListObject(expenseResponse);
        new NetworkManager<>(new ResponseCallback<ExpenseListResponse>() {

            @Override
            public void onSuccess(ExpenseListResponse response) {
                view.showExpenseList(response);
            }

            @Override
            public void onFailure(String status) {
                view.showToastMessage(status);
            }
        }).execute(call);

    }

}
