package com.food.localresto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.R;
import com.food.localresto.entities.SongObject;

import java.util.List;

public class MasterAdapter extends RecyclerView.Adapter<MasterViewHolder>{

    private Context context;
    private List<SongObject> allSongs;

    public MasterAdapter(Context context, List<SongObject> allSongs) {
        this.context = context;
        this.allSongs = allSongs;
    }

    @Override
    public MasterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.master_list_layout, parent, false);
        return new MasterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MasterViewHolder holder, int position) {
        SongObject songs = allSongs.get(position);
        holder.songTitle.setText(songs.getSongTitle());
        holder.songAuthor.setText(songs.getSongAuthor());
    }

    @Override
    public int getItemCount() {
        return allSongs.size();
    }

}
