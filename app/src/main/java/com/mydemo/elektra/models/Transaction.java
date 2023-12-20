package com.mydemo.elektra.models;

public class Transaction {

    private String id;
    private String time;
    private String date;
    private String details;
    private String amount;
    private boolean credit;


    public Transaction(){

    }

    public Transaction(String id, String time, String date, String details, String amount, boolean credit) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.details = details;
        this.amount = amount;
        this.credit = credit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isCredit() {
        return credit;
    }

    public void setCredit(boolean credit) {
        this.credit = credit;
    }
}
