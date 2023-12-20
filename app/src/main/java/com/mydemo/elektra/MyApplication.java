package com.mydemo.elektra;


import android.app.Application;
import android.content.Context;

import com.mydemo.elektra.models.SubItem;
import com.mydemo.elektra.models.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private static MyApplication sInstance;

    protected static String PORT = "104.211.90.144:1410";

    protected static ArrayList<SubItem> cartItemsList = new ArrayList<SubItem>(10);
    protected static ArrayList<Integer> quantityList = new ArrayList<>(5);

    public static String getPORT() {
        return PORT;
    }

    public static User user;

    public static MyApplication getInstance(){
        return sInstance;
    }

    public static Context myAppContext(){
        return sInstance.getApplicationContext();
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MyApplication.user = user;
    }

    public static ArrayList<SubItem> getCartItemsList() {
        return cartItemsList;
    }

    public static void setCartItemsList(ArrayList<SubItem> cartItemsList) {
        MyApplication.cartItemsList = cartItemsList;
    }

    public static void setCartItems(SubItem subitem){
        MyApplication.cartItemsList.add(subitem);
    }

    public static ArrayList<Integer> getQuantityList() {
        return quantityList;
    }

    public static void setQuantityList(ArrayList<Integer> quantityList) {
        MyApplication.quantityList = quantityList;
    }

    public static void setQuantity(Integer i){
        MyApplication.quantityList.add(i);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }



}
