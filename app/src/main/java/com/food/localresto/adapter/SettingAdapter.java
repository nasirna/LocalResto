package com.food.localresto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.R;
import com.food.localresto.entities.SongObject;

import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder>{

    private Context context;
    private List<SongObject> allSongs;

    public class SettingViewHolder extends RecyclerView.ViewHolder{

        public TextView songTitle;
        public TextView songAuthor;
        public ImageView songImage;

        public SettingViewHolder(View itemView, TextView songTitle, TextView songAuthor, ImageView songImage) {
            super(itemView);
            this.songTitle = songTitle;
            this.songAuthor = songAuthor;
            this.songImage = songImage;
        }

        public SettingViewHolder(View itemView) {
            super(itemView);

            songTitle = (TextView)itemView.findViewById(R.id.song_title);
            songAuthor = (TextView)itemView.findViewById(R.id.song_author);
            songImage = (ImageView)itemView.findViewById(R.id.song_cover);
        }

    }

    public SettingAdapter(Context context, List<SongObject> allSongs) {
        this.context = context;
        this.allSongs = allSongs;
    }

    @Override
    public SettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.setting_list_layout, parent, false);
        return new SettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SettingViewHolder holder, int position) {
        SongObject songs = allSongs.get(position);
        holder.songTitle.setText(songs.getSongTitle());
        holder.songAuthor.setText(songs.getSongAuthor());
    }

    @Override
    public int getItemCount() {
        return allSongs.size();
    }

}
