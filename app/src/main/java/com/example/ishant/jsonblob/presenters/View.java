package com.example.ishant.jsonblob.presenters;

/**
 * Created by Ishant Rana on 23/06/16.
 */
public interface View {
    void showProgress();

    void hideProgress();

    void showNoConnectionView();

    void showToastMessage(String message);

}
