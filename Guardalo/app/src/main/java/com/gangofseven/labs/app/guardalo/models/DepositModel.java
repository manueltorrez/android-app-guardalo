package com.gangofseven.labs.app.guardalo.models;

import java.util.Date;

/**
 * Created by jlou trill on 12/22/2016.
 */

public class DepositModel {
    private float amount;
    private String today;

    public DepositModel() {
    }

    public DepositModel(float amount, String today) {
        this.amount = amount;
        this.today = today;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }
}
