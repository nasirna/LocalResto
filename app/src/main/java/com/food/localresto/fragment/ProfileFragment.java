package com.food.localresto.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.R;
import com.food.localresto.adapter.MasterAdapter;
import com.food.localresto.entities.LoginObject;
import com.food.localresto.entities.SongObject;
import com.food.localresto.util.CustomApplication;
import com.google.gson.Gson;


import com.food.localresto.ProfilePershActivity;
import com.food.localresto.ProfileUserActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    private CustomSharedPreference shared;

    public ProfileFragment() {
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.my_profile));
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView profileImage = (ImageView)view.findViewById(R.id.profile_image);
        TextView profileName = (TextView)view.findViewById(R.id.profile_name);
        TextView profileAddress = (TextView)view.findViewById(R.id.profile_address);
        TextView profilePhone = (TextView)view.findViewById(R.id.profile_phone_number);

        //LoginObject loginUser = ((CustomApplication)getActivity().getApplication()).getLoginUser();

        shared = ((CustomApplication)getActivity().getApplication()).getShared();

        try {
            JSONArray kontak = new JSONArray(shared.getUserData());

            for (int i = 0; i < kontak.length(); i++){
                JSONObject c = kontak.getJSONObject(i);
                int idna = c.getInt("id");

                String usernm = c.getString("username");
                String address = c.getString("address");
                String phone = c.getString("phone");

                profileName.setText(usernm);
                profileAddress.setText(address);
                profilePhone.setText(phone);

                //user = new LoginObject(idna,usernm,email,address,phone,loggedIn);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        Button editProfile = (Button)view.findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(editIntent);
            }
        });

        Button editCart = (Button)view.findViewById(R.id.edit_cart);
        editCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hotDealIntent = new Intent(getActivity(), HotDealActvity.class);
                startActivity(hotDealIntent);
            }
        });

        return view;
    }

}
*/

public class ProfileFragment extends Fragment {

    private MasterAdapter mAdapter;

    RecyclerView recyclerview;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    View ChildView ;
    int RecyclerViewItemPosition ;

    private LoginObject user;


    public ProfileFragment() {
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
        View view = inflater.inflate(R.layout.fragment_profile_new, container, false);

        getActivity().setTitle("Profile");

        recyclerview = (RecyclerView) view.findViewById(R.id.profile_list);

        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());

        recyclerview.setLayoutManager(RecyclerViewLayoutManager);


        final List<SongObject> recentSongs = new ArrayList<SongObject>();

        Gson mGson = ((CustomApplication) ((Activity) getContext()).getApplication()).getGsonObject();
        String storedUser = ((CustomApplication) ((Activity) getContext()).getApplication()).getShared().getUserData();
        //LoginObject userObject = mGson.fromJson(storedUser, LoginObject.class);
        //String userObject1 = mGson.toJson(storedUser);
        //LoginObject userObject = mGson.fromJson(userObject1, LoginObject.class);


        try {
            JSONArray kontak = new JSONArray(storedUser);

            for (int i = 0; i < kontak.length(); i++){
                JSONObject c = kontak.getJSONObject(i);
                int idna = c.getInt("id");
                String usernm = c.getString("username");
                String role = c.getString("role");
                String email = c.getString("email");
                String address = c.getString("address");
                String phone = c.getString("phone");
                String loggedIn = c.getString("loggedIn");

                user = new LoginObject(idna,usernm,"",role,email,address,phone,loggedIn);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        String role = user.getRole();
        if (role.equals("1")) {
            recentSongs.add(new SongObject("User", "Profile User", ""));
            //recentSongs.add(new SongObject("Food", "Daftar Food", ""));
            //recentSongs.add(new SongObject("Makanan", "Daftar Makanan", ""));
        }else{
            recentSongs.add(new SongObject("Perusahaan", "Profile Perusahaan", ""));
        }

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

                    /*if (RecyclerViewItemPosition == 0) {
                        Intent foodIntent = new Intent(getActivity(), ProfilePershActivity.class);
                        foodIntent.putExtra("PROFILE_PERSH", objectToString);
                        getActivity().startActivity(foodIntent);
                    } else {
                        Intent foodIntent = new Intent(getActivity(), ProfileUserActivity.class);
                        foodIntent.putExtra("PROFILE_USER", objectToString);
                        getActivity().startActivity(foodIntent);
                    }*/
                    if ( item.getSongTitle().equals("User")) {
                        Intent foodIntent = new Intent(getActivity(), ProfileUserActivity.class);
                        foodIntent.putExtra("PROFILE_USER", objectToString);
                        getActivity().startActivity(foodIntent);
                    } else if (item.getSongTitle().equals("Perusahaan")) {
                        Intent foodIntent = new Intent(getActivity(), ProfilePershActivity.class);
                        foodIntent.putExtra("PROFILE_PERSH", objectToString);
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
