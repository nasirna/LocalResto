package com.food.localresto.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.food.localresto.BackupRestoreActivity;
import com.food.localresto.PengaturanActivity;
import com.food.localresto.ProfilePershActivity;
import com.food.localresto.ProfileUserActivity;
import com.food.localresto.R;
import com.food.localresto.SettingActivity;
import com.food.localresto.adapter.SettingAdapter;
import com.food.localresto.entities.SongObject;
import com.food.localresto.util.CustomApplication;
import com.food.localresto.util.ItemDividerDecoration;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SettingFragmentNew extends Fragment {

    RecyclerView recyclerview;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    View ChildView ;
    int RecyclerViewItemPosition ;


    public SettingFragmentNew() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_setting_new, container, false);
        View view = inflater.inflate(R.layout.fragment_setting_new, container, false);

        getActivity().setTitle("Setting");

        recyclerview = (RecyclerView) view.findViewById(R.id.setting_list);

        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());

        recyclerview.setLayoutManager(RecyclerViewLayoutManager);
        recyclerview.addItemDecoration(new ItemDividerDecoration(getActivity(), LinearLayoutManager.VERTICAL));


        final List<SongObject> recentSongs = new ArrayList<SongObject>();

            recentSongs.add(new SongObject("Printer", "Printer", ""));
            recentSongs.add(new SongObject("Struk", "Struk", ""));
            recentSongs.add(new SongObject("Backup & Restore", "Backup & Restore", ""));

        SettingAdapter mAdapter = new SettingAdapter(getActivity(), recentSongs);

        recyclerview.setAdapter(mAdapter);

        recyclerview.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {
                    SongObject item = recentSongs.get(Recyclerview.getChildAdapterPosition(ChildView));
                    Gson gson = ((CustomApplication) ((Activity) getContext()).getApplication()).getGsonObject();
                    final String objectToString = gson.toJson(item);
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(ChildView);

                    //Toast.makeText(getActivity(), recentSongs.get(RecyclerViewItemPosition), Toast.LENGTH_LONG).show();
                    //Toast.makeText(getActivity(), "Item  detail" + Recyclerview.getChildAdapterPosition(ChildView) + ": level " + item.getSongTitle() + ", text: " + item.getSongAuthor(), Toast.LENGTH_SHORT).show();

                    /*if (RecyclerViewItemPosition == 0) {
                        Intent foodIntent = new Intent(getActivity(), ProfilePershActivity.class);
                        foodIntent.putExtra("PROFILE_PERSH", objectToString);
                        getActivity().startActivity(foodIntent);
                    } else {
                        Intent foodIntent = new Intent(getActivity(), ProfileUserActivity.class);
                        foodIntent.putExtra("PROFILE_USER", objectToString);
                        getActivity().startActivity(foodIntent);
                    }*/
                    if ( item.getSongTitle().equals("Printer")) {
                        //Intent foodIntent = new Intent(getActivity(), ProfileUserActivity.class);
                        //foodIntent.putExtra("PROFILE_USER", objectToString);
                        //getActivity().startActivity(foodIntent);
                        Intent sampleIntent = new Intent(getActivity(), SettingActivity.class);
                        startActivity(sampleIntent);
                    } else if (item.getSongTitle().equals("Struk")) {
                        //Intent foodIntent = new Intent(getActivity(), ProfilePershActivity.class);
                        //foodIntent.putExtra("PROFILE_PERSH", objectToString);
                        //getActivity().startActivity(foodIntent);
                        Intent sampleIntent = new Intent(getActivity(), PengaturanActivity.class);
                        startActivity(sampleIntent);
                    }else if (item.getSongTitle().equals("Backup & Restore")) {
                        //Intent foodIntent = new Intent(getActivity(), ProfilePershActivity.class);
                        //foodIntent.putExtra("PROFILE_PERSH", objectToString);
                        //getActivity().startActivity(foodIntent);
                        Intent sampleIntent = new Intent(getActivity(), BackupRestoreActivity.class);
                        startActivity(sampleIntent);
                    }
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        return view;
    }

}