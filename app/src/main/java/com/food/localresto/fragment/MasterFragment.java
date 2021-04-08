package com.food.localresto.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.DaftarMenuItemExpandActivity;
import com.food.localresto.DaftarMenuItemExpandActivityNew;
import com.food.localresto.R;
import com.food.localresto.adapter.MasterAdapter;
import com.food.localresto.entities.SongObject;
import com.food.localresto.util.CustomApplication;
import com.google.gson.Gson;


import com.food.localresto.DaftarKategoryActivity;
import com.food.localresto.DaftarMenuItemActivity;
import com.food.localresto.MemberActivity;

import java.util.ArrayList;
import java.util.List;

public class MasterFragment extends Fragment {

    private MasterAdapter mAdapter;

    RecyclerView recyclerview;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    View ChildView ;
    int RecyclerViewItemPosition ;

    public MasterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_master, container, false);
        View view = inflater.inflate(R.layout.fragment_master, container, false);

        getActivity().setTitle("Master");
/*        RecyclerView songRecyclerView = (RecyclerView)view.findViewById(R.id.master_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        songRecyclerView.setLayoutManager(linearLayoutManager);
        songRecyclerView.setHasFixedSize(true);*/

        //MasterAdapter mAdapter = new MasterAdapter(getActivity(), getTestData());

        recyclerview = (RecyclerView) view.findViewById(R.id.master_list);

        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());

        recyclerview.setLayoutManager(RecyclerViewLayoutManager);


        final List<SongObject> recentSongs = new ArrayList<SongObject>();

        recentSongs.add(new SongObject("Kategori Menu", "Daftar Kategori Menu", ""));
        recentSongs.add(new SongObject("Menu", "Daftar Menu", ""));
        recentSongs.add(new SongObject("User", "Daftar User", ""));
        //recentSongs.add(new SongObject("Food", "Daftar Food", ""));
        //recentSongs.add(new SongObject("Makanan", "Daftar Makanan", ""));


/*        mAdapter = new MasterAdapter(getActivity(),recentSongs, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SongObject item = recentSongs.get(position);
                Toast.makeText(getActivity(), "Item  detail" + position + ": level " + item.getSongTitle() + ", text: " + item.getSongAuthor(), Toast.LENGTH_SHORT).show();
            }
        });*/

        MasterAdapter mAdapter = new MasterAdapter(getActivity(), recentSongs);

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

                    if (RecyclerViewItemPosition == 0) {
                        Intent foodIntent = new Intent(getActivity(), DaftarKategoryActivity.class);
                        foodIntent.putExtra("MENU_ITEM1", objectToString);
                        getActivity().startActivity(foodIntent);
                    }/*else if (RecyclerViewItemPosition == 1) {
                        Intent foodIntent = new Intent(getActivity(), DaftarMenuActivity.class);
                        foodIntent.putExtra("MENU_ITEM2", objectToString);
                        getActivity().startActivity(foodIntent);
                    }else if (RecyclerViewItemPosition == 2) {
                        Intent foodIntent = new Intent(getActivity(), DaftarFoodActivity_old.class);
                        foodIntent.putExtra("MENU_ITEM3", objectToString);
                        getActivity().startActivity(foodIntent);
                    }else {
                        Intent foodIntent = new Intent(getActivity(), ListFoodActivity.class);
                        foodIntent.putExtra("MENU_ITEM4", objectToString);
                        getActivity().startActivity(foodIntent);
                    }*/
                    else if (RecyclerViewItemPosition == 1) {
                        Intent foodIntent = new Intent(getActivity(), DaftarMenuItemExpandActivityNew.class);
                        foodIntent.putExtra("MENU_ITEM2", objectToString);
                        getActivity().startActivity(foodIntent);
                    } else {
                        Intent foodIntent = new Intent(getActivity(), MemberActivity.class);
                        foodIntent.putExtra("MENU_ITEM2", objectToString);
                        getActivity().startActivity(foodIntent);
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

    /*public List<SongObject> getTestData() {
        List<SongObject> recentSongs = new ArrayList<SongObject>();
        recentSongs.add(new SongObject("Kategori Menu", "Daftar Kategori Menu", ""));
        recentSongs.add(new SongObject("Menu", "Daftar Menu", ""));
     *//*   recentSongs.add(new SongObject("Adele", "Someone Like You", ""));
        recentSongs.add(new SongObject("Adele", "Someone Like You", ""));
        recentSongs.add(new SongObject("Adele", "Someone Like You", ""));
        recentSongs.add(new SongObject("Adele", "Someone Like You", ""));
    *//*
     return recentSongs;
    }*/

}
