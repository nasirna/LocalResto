package com.food.localresto.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.R;

public class PaidViewHolder extends RecyclerView.ViewHolder {

    public TextView deliveryDate;
    public TextView deliveryStatus;
    public TextView orderName;
    public TextView orderPrice;
    public TextView orderTracking;
    public TextView tableSeat;


    public PaidViewHolder(View itemView) {
        super(itemView);

        deliveryDate = (TextView) itemView.findViewById(R.id.delivery_date);
        deliveryStatus = (TextView) itemView.findViewById(R.id.delivery_status);
        orderName = (TextView) itemView.findViewById(R.id.order_name);
        orderPrice = (TextView) itemView.findViewById(R.id.order_price);
        tableSeat = (TextView) itemView.findViewById(R.id.tableseat);
        //orderTracking = (TextView) itemView.findViewById(R.id.order_tracking);
    }
}
