package com.food.localresto.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.R;

public class UserViewHolder extends RecyclerView.ViewHolder{

    public TextView userName;
    //public ImageView categoryImage;
    public View mItemView;
    public ImageView deleteMember;
    public  ImageView editMember;

    public UserViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        userName = (TextView)itemView.findViewById(R.id.user_name);
        deleteMember = (ImageView)itemView.findViewById(R.id.delete_member);
        editMember = (ImageView)itemView.findViewById(R.id.edit_member);
    }
}
