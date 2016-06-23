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

    private void showAppropriateLayout() {
        if(Utils.isInternetConnected()){
            getExpenseList();
        } else {
            view.showNoConnectionView();
        }
    }

    private void getExpenseList(){
        Call<ExpenseListResponse> call = RestApiClient.getRestApiService().getExpensesList();
        new NetworkManager<>(new ResponseCallback<ExpenseListResponse>() {
            @Override
            public void onSuccess(ExpenseListResponse response) {
                view.hideProgress();
                view.showExpenseList(response.getExpenseList());
            }

            @Override
            public void onFailure(String status) {
                view.showToastMessage(status);
            }

        }).execute(call);
    }

}
