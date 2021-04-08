package com.food.localresto.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.R;


public class MasterViewHolder extends RecyclerView.ViewHolder{

    public TextView songTitle;
    public TextView songAuthor;
    public ImageView songImage;

    public MasterViewHolder(View itemView, TextView songTitle, TextView songAuthor, ImageView songImage) {
        super(itemView);
        this.songTitle = songTitle;
        this.songAuthor = songAuthor;
        this.songImage = songImage;
    }

    public MasterViewHolder(View itemView) {
        super(itemView);

        songTitle = (TextView)itemView.findViewById(R.id.song_title);
        songAuthor = (TextView)itemView.findViewById(R.id.song_author);
        songImage = (ImageView)itemView.findViewById(R.id.song_cover);
    }

}
