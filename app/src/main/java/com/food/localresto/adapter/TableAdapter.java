package com.food.localresto.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.R;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.HistoryObject;

import java.util.ArrayList;
import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableViewHolder>{
    private RecyclerViewClickListener mListener;
    ArrayList tableNames;
    Context context;

    int index = -1;

    //private Query mDatabase;
    private SqliteDatabase mDatabase;

    public TableAdapter(Context context, ArrayList tableNames, RecyclerViewClickListener listener) {
        this.context = context;
        this.tableNames = tableNames;
        mListener = listener;
        //mDatabase = new Query(context);
        mDatabase = new SqliteDatabase(context);
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.table_layout, parent, false);
        return new TableViewHolder(v, mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        if (holder instanceof TableViewHolder) {
            TableViewHolder rowHolder = (TableViewHolder) holder;
            //set values of data here

            holder.seat.setText((CharSequence) tableNames.get(position));
            /*holder.seat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index = position;
                    //notifyDataSetChanged();
                }
            });*/
            //if(index==position){
           /* if(position==5){
                holder.seat.setBackgroundColor(Color.parseColor("#FF4081"));
                holder.seat.setTextColor(Color.parseColor("#FFFFFF"));
            }else{
                holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.country.setTextColor(Color.parseColor("#000000"));
            }*/
            index = position+1;
            List<HistoryObject> orderHistory = mDatabase.listHistorybyAvailTableId();
            //ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(str));

            if (checkAvailability(orderHistory,index)){
                holder.seat.setBackgroundColor(Color.parseColor("#FF4081"));
                holder.seat.setTextColor(Color.parseColor("#FFFFFF"));
            }else{
                // holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                //holder.country.setTextColor(Color.parseColor("#000000"));
            }

        }
    }

    @Override
    public int getItemCount() {
        return tableNames.size();
    }

    public static boolean checkAvailability(List<HistoryObject> stock, int product) {
        for (HistoryObject p : stock) {
            final int text = p.getTabel();
            if (text==product) {
                return true;
            }
        }

        return false;
    }
}