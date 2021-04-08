package com.food.localresto.adapter;

import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.R;

public class TableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private RecyclerViewClickListener mListener;

    public Button seat;

    TableViewHolder(View v, RecyclerViewClickListener listener) {
        super(v);
        mListener = listener;
        seat = (Button)itemView.findViewById(R.id.seat);
        //v.setOnClickListener(this);

        seat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mListener.onClick(view, getAdapterPosition());
    }
}
