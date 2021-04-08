package com.food.localresto.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.R;
import com.food.localresto.entities.CartObject;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutViewHolder>{

    private List<CartObject> checkoutList;
    private Context context;

    public CheckoutAdapter(Context context, List<CartObject> checkoutList) {
        this.context = context;
        this.checkoutList = checkoutList;
    }

    @Override
    public CheckoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_layout, parent, false);
        return new CheckoutViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(CheckoutViewHolder holder, int position) {
        CartObject cartObject = checkoutList.get(position);
        holder.checkoutName.setText(cartObject.getOrderName());
        holder.checkoutQuantity.setText(String.valueOf(cartObject.getQuantity()) + " x");
        //nasir
        //holder.checkoutPrice.setText("$" + String.valueOf(cartObject.getPrice()) + "0");
        //holder.checkoutPrice.setText("Rp " + String.format("%1$,.0f", Float.valueOf(cartObject.getPrice()) ));
        holder.checkoutPrice.setText(String.format("%1$,.0f", Float.valueOf(cartObject.getPrice()) ));
    }

    @Override
    public int getItemCount() {
        return checkoutList.size();
    }
}
