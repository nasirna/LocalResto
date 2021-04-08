package com.food.localresto.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.food.localresto.R;
import com.food.localresto.entities.CountryDataItem;
import com.food.localresto.entities.MenuItemObject;

import java.util.List;

public class DaftarMenuItemExpandAdapter extends RecyclerView.Adapter<DaftarMenuItemExpandAdapter.myViewHolder> {
    Context context;
    List<CountryDataItem> mData;

    public DaftarMenuItemExpandAdapter(Context context, List<CountryDataItem> data) {
        this.context = context;
        this.mData = data;
    }
    @Override
    public DaftarMenuItemExpandAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_layout_menuitem, parent, false);
        return new myViewHolder(view);
    }
    @Override
    public void onBindViewHolder(DaftarMenuItemExpandAdapter.myViewHolder holder, int position) {
        CountryDataItem countryDataItem = mData.get(position);
        holder.country.setText(countryDataItem.getCountryName());
        int noOfChildTextViews = holder.linearLayout_childItems.getChildCount();
        int noOfChild = countryDataItem.getChildDataItems().size();
        if (noOfChild < noOfChildTextViews) {
            for (int index = noOfChild; index < noOfChildTextViews; index++) {
                TextView currentTextView = (TextView) holder.linearLayout_childItems.getChildAt(index);
                currentTextView.setVisibility(View.GONE);
            }
        }
        for (int textViewIndex = 0; textViewIndex < noOfChild; textViewIndex++) {
            TextView currentTextView = (TextView) holder.linearLayout_childItems.getChildAt(textViewIndex);
            currentTextView.setText(countryDataItem.getChildDataItems().get(textViewIndex).getStateName());
        }
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView country;
        private LinearLayout linearLayout_childItems;
        public myViewHolder(View itemView) {
            super(itemView);
            country = (TextView) itemView.findViewById(R.id.country);
            linearLayout_childItems = itemView.findViewById(R.id.ll_child_items);
            linearLayout_childItems.setVisibility(View.GONE);
            int intMaxNoOfChild = 0;
            for (int index = 0; index < mData.size(); index++) {
                int intMaxSizeTemp = mData.get(index).getChildDataItems().size();
                if (intMaxSizeTemp > intMaxNoOfChild) intMaxNoOfChild = intMaxSizeTemp;
            }
            for (int indexView = 0; indexView < intMaxNoOfChild; indexView++) {
                TextView textView = new TextView(context);
                textView.setId(indexView);
                textView.setPadding(0, 20, 0, 20);
                textView.setGravity(Gravity.CENTER);
                //textView.setBackground(ContextCompat.getDrawable(context, R.drawable.background_sub_module_text));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textView.setOnClickListener(this);
                linearLayout_childItems.addView(textView, layoutParams);
            }
            country.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.country) {
                if (linearLayout_childItems.getVisibility() == View.VISIBLE) {
                    linearLayout_childItems.setVisibility(View.GONE);
                } else {
                    linearLayout_childItems.setVisibility(View.VISIBLE);
                }
            } else {
                TextView textViewClicked = (TextView) view;
                Toast.makeText(context, "" + textViewClicked.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
