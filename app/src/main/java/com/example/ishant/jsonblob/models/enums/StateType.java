package com.example.ishant.jsonblob.models.enums;

/**
 * Created by Ishant Rana on 25/06/16.
 */
public enum StateType {

    VERIFIED("VERIFIED"),
    UNVERIFIED("UNVERIFIED"),
    FRAUDULENT("FRAUDULENT");

    private String value;
    StateType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
