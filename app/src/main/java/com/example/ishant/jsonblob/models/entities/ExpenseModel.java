package com.example.ishant.jsonblob.models.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ishant Rana on 23/06/16.
 */
public class ExpenseModel  {

    @SerializedName("amount")
    private Double amount;

    @SerializedName("category")
    private String category;

    @SerializedName("id")
    private String id;

    @SerializedName("state")
    private String state;

    @SerializedName("time")
    private String time;

    @SerializedName("description")
    private String description;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
