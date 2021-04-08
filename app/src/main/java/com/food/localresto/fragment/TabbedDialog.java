package com.food.localresto.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.food.localresto.CartActivity;
import com.food.localresto.DaftarMenuItemExpandActivityNew;
import com.food.localresto.MainActivity;
import com.food.localresto.R;
import com.food.localresto.SettingActivity;
import com.food.localresto.adapter.CustomAdapter;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.MenuCategoryObject;
import com.food.localresto.entities.MenuItemObject;
import com.food.localresto.util.Helper;
import com.google.android.material.tabs.TabLayout;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class TabbedDialog extends DialogFragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    private String IdMenu;
    private MenuItemObject itemData;
    private SqliteDatabase mDatabase;


    private int posisiNya, posisiNa, menuidNya, jumlahnya, menuidna;

    ImageView imageViewIcon;
    public static final String IMAGE_DIRECTORY = "ImageResto";
    private File file;
    private File sourceFile;
    private File destFile;

    private String jenisna;
    private float pricefloat, pricefloat2, pricefloat3;
    private List<String> str3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.dialog_sample,container,false);

        tabLayout = (TabLayout) rootview.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) rootview.findViewById(R.id.masterViewPager);
        imageViewIcon = (ImageView) rootview.findViewById(R.id.imageView);

        mDatabase = SqliteDatabase.getInstance(getActivity());

        CustomAdapter adapter = new CustomAdapter(getChildFragmentManager());
        adapter.addFragment("ITEM",CustomFragment.createInstance(IdMenu,itemData));
        adapter.addFragment("HARGA",CustomFragment2.createInstance(IdMenu,itemData));
        adapter.addFragment("DISCOUNT",CustomFragment3.createInstance(IdMenu,itemData));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        Button cancelButton = (Button) rootview.findViewById(R.id.cancel_txt);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Item  not exist" , Toast.LENGTH_SHORT).show();
                //Intent loginPageIntent = new Intent(getActivity(), DaftarMenuItemExpandActivityNew.class);
                //startActivity(loginPageIntent);
                //getActivity().finish();
                getActivity().onBackPressed();
            }
        });

        Button editButton = (Button) rootview.findViewById(R.id.edit_txt);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> str1 = CustomFragment.getDataTab1Array();
                List<String> str2 = CustomFragment2.getDataTab2Array();
                //List<String> str3 = CustomFragment3.getDataTab3Array();

                try {
                    str3 = CustomFragment3.getDataTab3Array();
                } catch (NullPointerException e) {
                    //System.err.println("Null pointer exception");
                    str3 = new ArrayList<String>();
                    str3.add(itemData.getItem_disc());

                }

                if (itemData != null) {

                    //spinner.setText(itemData.getMenu_name());
                    //nameField.setText(itemData.getItem_name());
                    //kategoryField.setText(itemData.getDescription());
                    double d = 0;
                    d = Double.parseDouble(str2.get(0));
                    DecimalFormat df = new DecimalFormat("#########");
                    String strku = df.format(d);


                    String filename = itemData.getMenu_item_id() + ".png";
                    file = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_DIRECTORY);
                    destFile = new File(file, filename);

                    /*Glide.with(getContext())
                            .load(destFile)
                            .signature(new ObjectKey(System.currentTimeMillis()))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .fitCenter()
                            .override(150, 150)
                            .into(imageViewIcon);*/

                    String totalHarga = String.valueOf(itemData.getItem_price());

                    DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
                    String numberAsString = decimalFormat.format(itemData.getItem_price());

                    pricefloat = Float.parseFloat(strku);


                    double d2 = 0;
                    d2 = Double.parseDouble(str2.get(1));
                    //DecimalFormat df2 = new DecimalFormat("#########");
                    String strku2 = df.format(d2);

                    pricefloat2 = Float.parseFloat(strku2);

                    double d3 = 0;
                    d3 = Double.parseDouble(str2.get(2));
                    //DecimalFormat df2 = new DecimalFormat("#########");
                    String strku3 = df.format(d3);

                    pricefloat3 = Float.parseFloat(strku3);

                }

                final List<MenuCategoryObject> categoryObject = mDatabase.listProducts();
                for (MenuCategoryObject model : categoryObject) {
                    //final Integer menuidna = model.getMenu_id();
                    //final String jenisna = model.getMenu_jenis().toUpperCase();
                    String namamenu = model.getMenu_name();
                    if (namamenu.contains(str1.get(0).toString())) {
                        //filteredMemeberList.add(model);
                        menuidna = model.getMenu_id();
                        jenisna = model.getMenu_jenis();
                    }
                }


                if (TextUtils.isEmpty(str1.toString())) {
                    Toast.makeText(getActivity(), "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {

                    //check data ada tidak di menu item

                    //long result = mDatabase.updateMenuItem(new MenuItemObject(itemData.getMenu_item_id(), String.valueOf(menuidna) + itemData.getMenu_item_id(), menuidna, jenisna, str1.get(0), str1.get(1), str1.get(2), itemData.getMenu_item_id() + ".png", pricefloat, pricefloat2, pricefloat3,'Y','DISCOUNT 100%'));
                    long result = mDatabase.updateMenuItem(new MenuItemObject(itemData.getMenu_item_id(), String.valueOf(menuidna) + itemData.getMenu_item_id(), menuidna, jenisna, str1.get(0), str1.get(1), str1.get(2), itemData.getMenu_item_id() + ".png", pricefloat, pricefloat2, pricefloat3,str1.get(3),str3.get(0)));

                    if (result > 0) {
                        Toast.makeText(getActivity(), "Update Success!", Toast.LENGTH_SHORT).show();
                    } else {
                        //mDatabase.updateMenuItem(new MenuItemObject(menu.getMenu_item_id(), String.valueOf(menuidna) + menu.getMenu_item_id(), menuidna, jenisna, menunya, name, kategory, menu.getMenu_item_id() + ".png", pricefloat));

                        Helper.displayErrorMessage(getActivity(), "Gagal Update!");

                    }

                    //refresh the activity
                    getActivity().onBackPressed();

                }
            }
        });


        return rootview;
    }

    public void setMessage(MenuItemObject menu) {

        itemData = menu;

    }


}
