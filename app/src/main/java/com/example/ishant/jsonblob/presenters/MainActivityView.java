package com.example.ishant.jsonblob.presenters;


import com.example.ishant.jsonblob.models.entities.ExpenseModel;
import com.example.ishant.jsonblob.models.responses.ExpenseListResponse;

import java.util.List;

/**
 * Created by Ishant Rana on 23/06/16.
 */
public interface MainActivityView extends View {

    void showExpenseList(ExpenseListResponse expenseListResponse);

    void finish();

}
