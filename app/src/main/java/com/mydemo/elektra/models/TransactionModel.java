package com.mydemo.elektra.models;

public class TransactionModel {

    private String date;
    private String amount;


    public TransactionModel(){

    }

    public TransactionModel(String date, String amount) {
        this.date = date;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
