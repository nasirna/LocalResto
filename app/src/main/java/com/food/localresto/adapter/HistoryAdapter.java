package com.food.localresto.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.R;
import com.food.localresto.entities.OrderObject;
import com.food.localresto.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{

    private static final String TAG = HistoryAdapter.class.getSimpleName();

    private RecyclerViewClickListener onClickListener;

    private Context context;
    private List<OrderObject> historyObjectList;

    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        public TextView deliveryDate;
        public TextView deliveryStatus;
        public TextView orderName;
        public TextView orderPrice;
        //public TextView orderTracking;
        public TextView orderTable;
        public ImageButton Overflow;


        public HistoryViewHolder(View itemView) {
            super(itemView);

            deliveryDate = (TextView)itemView.findViewById(R.id.delivery_date);
            deliveryStatus = (TextView)itemView.findViewById(R.id.delivery_status);
            orderName = (TextView)itemView.findViewById(R.id.order_name);
            orderPrice = (TextView)itemView.findViewById(R.id.order_price);
            //orderTracking = (TextView)itemView.findViewById(R.id.order_tracking);
            orderTable = (TextView)itemView.findViewById(R.id.delivery_table);

            Overflow = (ImageButton) itemView.findViewById(R.id.overflow);
            //deleteProduct = (ImageView)itemView.findViewById(R.id.delete_product);
            //editProduct = (ImageView)itemView.findViewById(R.id.edit_product);

            Overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClick(v, getAdapterPosition());
                    //Toast.makeText((Activity)context, "Delete successfully " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public HistoryAdapter(Context context, List<OrderObject> historyObjectList, RecyclerViewClickListener listener) {
        this.context = context;
        this.historyObjectList = historyObjectList;
        //nb
        this.onClickListener = listener;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_layout, parent, false);
        return new HistoryViewHolder(layoutView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        OrderObject historyObject = historyObjectList.get(position);
        //final int mOrderId = historyObject.getId();
        final String mOrderId = historyObject.getId();

        holder.deliveryDate.setText(Helper.dateFormatting(historyObject.getOrderDate()));
        //holder.deliveryDate.setText("");
        holder.deliveryStatus.setText(historyObject.getOrderStatus());

        if (historyObject.getJml() == 1){
            holder.orderName.setText("#" + String.valueOf(historyObject.getId()).substring(10));
        }else{
            holder.orderName.setText("#" + String.valueOf(historyObject.getId()).substring(10)+" - "+"("+historyObject.getJml()+"x Order"+")");
        }
        //holder.orderName.setText("#" + String.valueOf(historyObject.getId()).substring(10)+" - "+"("+historyObject.getJml()+")");
        //holder.orderPrice.setText("$" + String.valueOf(historyObject.getOrderPrice()) + "0");
        holder.orderPrice.setText("Rp " + String.format("%1$,.0f", Float.valueOf(historyObject.getOrderPrice()) ));

        //nb
        if(historyObject.getTabel() == 0) {
            holder.orderTable.setText("Take Away - ");
        }else{
            holder.orderTable.setText("Table " + String.valueOf(historyObject.getTabel())+" - ");
        }

        if (historyObject.getOrderStatus().equals("Ordered")) {
            holder.orderTable.setTextColor(Color.parseColor("#D81B60"));
            holder.deliveryStatus.setTextColor(Color.parseColor("#D81B60"));
        }else{
            holder.orderTable.setTextColor(Color.parseColor("#a7a4a4"));
            holder.deliveryStatus.setTextColor(Color.parseColor("#a7a4a4"));
        }

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

    public interface RecyclerViewClickListener {

        //void onClick(View v, int position);

        void OnClick(View v, int position);

        //void daysOnClick(View v, int position);
    }

    public void setFilter(List<OrderObject> filterList){
        historyObjectList = new ArrayList<>();
        historyObjectList.addAll(filterList);
        notifyDataSetChanged();
    }
}