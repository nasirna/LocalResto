package com.food.localresto.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.food.localresto.R;
import com.food.localresto.SettingActivity;


public class SettingFragment extends Fragment {
    private static final String TAG = "SettingFragment.java";

    private String kode;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //Gson mGson = ((CustomApplication) ((Activity) getContext()).getApplication()).getGsonObject();
        //String storedUser = ((CustomApplication) ((Activity) getContext()).getApplication()).getShared().getUserData();
        //LoginObject userObject = mGson.fromJson(storedUser, LoginObject.class);
        //kode = userObject.getKodeuser().substring(0,4);

        //kode = "R001";
        //mDatabase = new Query(getActivity());
        //List<MenuItemObject> allMenuitem = mDatabase.listMenuItemAll();

        //mDatabase = new Query(getActivity());
        //List<HistoryObject> allOrderHist = mDatabase.listHistoryObject();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.my_history));
        View view = inflater.inflate(R.layout.fragment_setting, container, false);


        Button btnScan = (Button) view.findViewById(R.id.Scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sampleIntent = new Intent(getActivity(), SettingActivity.class);
                startActivity(sampleIntent);
            }
        });


        return view;
    }



}