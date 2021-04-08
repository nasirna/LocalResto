package com.food.localresto.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.food.localresto.R;
import com.food.localresto.entities.MenuItemObject;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class CustomFragment3 extends Fragment {

    private String mText = "";
    private MenuItemObject dataItem;

    private static MaterialBetterSpinner spinner;

    public static CustomFragment3 createInstance(String txt, MenuItemObject menu)
    {
        CustomFragment3 fragment = new CustomFragment3();
        fragment.mText = txt;
        fragment.dataItem=menu;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_custom3, container, false);
        View v = inflater.inflate(R.layout.fragment_custom3,container,false);
        //((TextView) v.findViewById(R.id.textView)).setText(mText);

        spinner = (MaterialBetterSpinner) v.findViewById(R.id.spinnerDisc);


        //initialization after declaration
        String[] str = new String[4];
        str[0] = "NONE";
        str[1] = "DISC(%) 5";
        str[2] = "DISC(%) 10";
        str[3] = "DISC(%) 20";

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, str);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        //spinner.setText(str[0]);
        if (dataItem != null) {
            spinner.setText(dataItem.getItem_disc());
        }

            return v;
    }


    public static List<String> getDataTab3Array(){

        List<String> xxx = new ArrayList<String>();
        xxx.add(spinner.getText().toString());
        //xxx.add(nameField.getText().toString());
        //xxx.add(kategoryField.getText().toString());
        //xxx.add(priceField.getText().toString());

        return xxx;
    }
}