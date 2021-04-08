package com.food.localresto.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.food.localresto.R;
import com.food.localresto.entities.MenuItemObject;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ru.kolotnev.formattedittext.DecimalEditText;

public class CustomFragment2 extends Fragment {

    private String mText = "";
    private MenuItemObject dataItem;

    private static DecimalEditText priceField1,priceField2,priceField3;

    public static CustomFragment2 createInstance(String txt, MenuItemObject menu)
    {
        CustomFragment2 fragment = new CustomFragment2();
        fragment.mText = txt;
        fragment.dataItem=menu;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_custom2, container, false);
        View v = inflater.inflate(R.layout.fragment_custom2,container,false);
        //((TextView) v.findViewById(R.id.textView)).setText(mText);

        priceField1 = (DecimalEditText) v.findViewById(R.id.food_price1);
        priceField2 = (DecimalEditText) v.findViewById(R.id.food_price2);
        priceField3 = (DecimalEditText) v.findViewById(R.id.food_price3);

        if (dataItem != null) {



            //String totalHarga = String.valueOf(dataItem.getItem_price());
            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
            String numberAsString = decimalFormat.format(dataItem.getItem_price());
            priceField1.setText(numberAsString);

            DecimalFormat decimalFormat2 = new DecimalFormat("#,###.##");
            String numberAsString2 = decimalFormat2.format(dataItem.getItem_price2());
            priceField2.setText(numberAsString2);

            DecimalFormat decimalFormat3 = new DecimalFormat("#,###.##");
            String numberAsString3 = decimalFormat3.format(dataItem.getItem_price3());
            priceField3.setText(numberAsString3);

        }
        return v;
    }

    public static List<String> getDataTab2Array(){
        //String xxx = spinner.getText().toString();
        List<String> xxx = new ArrayList<String>();

        xxx.add(String.valueOf(priceField1.getValue().doubleValue()));
        xxx.add(String.valueOf(priceField2.getValue().doubleValue()));
        xxx.add(String.valueOf(priceField3.getValue().doubleValue()));

        return xxx;
    }
}