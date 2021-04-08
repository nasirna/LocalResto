package com.food.localresto.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.R;

public class CartViewHolder extends RecyclerView.ViewHolder {

    public TextView orderItemName;
    public TextView orderItemExtra;
    public TextView removeOrder;
    public TextView orderSubtotal;
    public TextView orderQuantity;

    public Button minusButton;
    public Button plusButton;


    public CartViewHolder(View itemView) {
        super(itemView);

        orderItemName = (TextView)itemView.findViewById(R.id.order_item_name);
        orderItemExtra = (TextView)itemView.findViewById(R.id.order_item_exra);
        removeOrder = (TextView)itemView.findViewById(R.id.remove_order);
        orderSubtotal = (TextView)itemView.findViewById(R.id.order_total);
        orderQuantity = (TextView)itemView.findViewById(R.id.order_quantity);

        minusButton = (Button)itemView.findViewById(R.id.minus_button);
        plusButton = (Button)itemView.findViewById(R.id.plus_button);
    }
}
