package com.food.localresto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.R;
import com.food.localresto.entities.OrderObject;

import java.util.List;

public class PaidAdapter extends RecyclerView.Adapter<PaidViewHolder>{

    private static final String TAG = HistoryAdapter.class.getSimpleName();

    private Context context;
    private List<OrderObject> historyObjectList;

    public PaidAdapter(Context context, List<OrderObject> historyObjectList) {
        this.context = context;
        this.historyObjectList = historyObjectList;
    }

    @Override
    public PaidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.close_table_layout, parent, false);
        return new PaidViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(PaidViewHolder holder, int position) {
        OrderObject historyObject = historyObjectList.get(position);
        //final int mOrderId = historyObject.getId();
        final String mOrderId = historyObject.getId();
        // holder.deliveryDate.setText(Helper.dateFormatting(historyObject.getOrderDate()));
        //holder.deliveryDate.setText("");
        //  holder.deliveryStatus.setText(historyObject.getOrderStatus());
        //holder.tableSeat.setText("5");
        holder.orderName.setText(String.valueOf(historyObject.getId()));
        //holder.orderPrice.setText("$" + String.valueOf(historyObject.getOrderPrice()) + "0");
        holder.orderPrice.setText(String.format("%1$,.0f", Float.valueOf(historyObject.getOrderPrice()) ));
        /*holder.orderTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historyIntent = new Intent(context, OrderListActivity.class);
                historyIntent.putExtra("ORDER_ID", mOrderId);
                context.startActivity(historyIntent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return historyObjectList.size();
    }


}