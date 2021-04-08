package com.food.localresto.util;

import android.app.Application;

import com.food.localresto.entities.CartObject;
import com.food.localresto.entities.LoginObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CustomApplication extends Application {

    private Gson gson;
    private GsonBuilder builder;

    private CustomSharedPreference shared;

    @Override
    public void onCreate() {
        super.onCreate();
        builder = new GsonBuilder();
        gson = builder.create();
        shared = new CustomSharedPreference(getApplicationContext());
    }

    public CustomSharedPreference getShared(){
        return shared;
    }

    public Gson getGsonObject(){
        return gson;
    }

    public LoginObject getLoginUser(){
        Gson mGson = getGsonObject();
        String storedUser = getShared().getUserData();
        return mGson.fromJson(storedUser, LoginObject.class);
    }

    public int cartItemCount(){
        String orderList = getShared().getCartItems();
        CartObject[] allCart = getGsonObject().fromJson(orderList, CartObject[].class);
        if(allCart == null){
            return 0;
        }
        return allCart.length;
    }

    //nasir
    public double getSubtotalAmount(){
        float total = (float) 0;

        String orderList = getShared().getCartItems();
        CartObject[] allCart = getGsonObject().fromJson(orderList, CartObject[].class);
        if(allCart == null){
            return 0;
        }

        for (int i = 0; i < allCart.length; i++){
            int quantity = allCart[i].getQuantity();
            if(quantity == 0){
                quantity = 1;
            }
            float itemSubtotal = quantity * allCart[i].getPrice();
            total += itemSubtotal;
        }

        return total;
    }

    //nasir
    public int getTableSeat(int posnya){
        /*LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.menu_tableresto_layout, null);

        TextView textView = (TextView) view.findViewById(R.id.count);
        String globalVariable = textView.getText().toString().trim();*/

        if (posnya==0) {
            return 0;
        }


        return posnya;
    }
}
