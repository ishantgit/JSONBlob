package com.example.ishant.jsonblob.models.responses;

import com.example.ishant.jsonblob.models.entities.ExpenseModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ishant Rana on 23/06/16.
 */
public class ExpenseListResponse {

    @SerializedName("expenses")
    private List<ExpenseModel> expenseList;

    public List<ExpenseModel> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<ExpenseModel> expenseList) {
        this.expenseList = expenseList;
    }
}
