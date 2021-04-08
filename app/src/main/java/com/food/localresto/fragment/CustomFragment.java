package com.food.localresto.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.food.localresto.R;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.MenuCategoryObject;
import com.food.localresto.entities.MenuItemObject;
import com.theartofdev.edmodo.cropper.CropImage;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.kolotnev.formattedittext.DecimalEditText;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class CustomFragment extends Fragment {
    private String mText = "";
    private MenuItemObject dataItem;

    private SqliteDatabase mDatabase;
    ImageView imageViewIcon;
    public static final String IMAGE_DIRECTORY = "ImageResto";
    private File file;
    private File sourceFile;
    private File destFile;

    private static MaterialBetterSpinner spinner;
    private static EditText nameField, kategoryField;
    //private static DecimalEditText priceField;
    private Switch Saklar;
    private static TextView Status;

    private SimpleDateFormat dateFormatter;

    public static CustomFragment createInstance(String txt, MenuItemObject menu)
    {
        CustomFragment fragment = new CustomFragment();
        fragment.mText = txt;
        fragment.dataItem=menu;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sample,container,false);
        //((TextView) v.findViewById(R.id.textView)).setText(mText);

        spinner = (MaterialBetterSpinner) v.findViewById(R.id.spinner2);
        nameField = (EditText) v.findViewById(R.id.enter_name);
        kategoryField = (EditText) v.findViewById(R.id.enter_description);
        imageViewIcon = (ImageView) v.findViewById(R.id.imageView);
        //priceField = (DecimalEditText) v.findViewById(R.id.food_price1);
        Saklar = (Switch) v.findViewById(R.id.saklar);
        Status = (TextView) v.findViewById(R.id.result);


        Saklar.setChecked(true);

        mDatabase = SqliteDatabase.getInstance(getActivity());
        List<MenuCategoryObject> categoryObject = mDatabase.listProducts();
        String[] str = new String[categoryObject.size()];
        for (int i = 0; i < categoryObject.size(); i++) {
            str[i] = categoryObject.get(i).getMenu_name();
        }
        Saklar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                //Mengecek Status, Apakah SwitchButton Dalam Keadaan Checked/Hidup atau Unchecked/Mati
                if(checked){
                    Status.setText("Activated");
                    //Toast.makeText(getActivity(),"Saklar Dihidupkan", Toast.LENGTH_SHORT).show();
                }else {
                    Status.setText("Non Activated");
                    //Toast.makeText(getActivity(),"Saklar Dimatikan", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //activated button



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, str);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        if (dataItem != null) {

            spinner.setText(dataItem.getMenu_name());
            nameField.setText(dataItem.getItem_name());
            kategoryField.setText(dataItem.getDescription());
            String filename = dataItem.getMenu_item_id() + ".png";

            if (dataItem.getItem_activate().equals("Y")){
                Saklar.setChecked(true);
                Status.setText("Activated");
            }else{
                Saklar.setChecked(false);
                Status.setText("Non Activated");
            }

            file = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_DIRECTORY);
            destFile = new File(file, filename);

            Glide.with(getActivity())
                    .load(destFile)
                    .signature(new ObjectKey(System.currentTimeMillis()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .override(150, 150)
                    .into(imageViewIcon);

            //String totalHarga = String.valueOf(dataItem.getItem_price());
            //DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
            //String numberAsString = decimalFormat.format(dataItem.getItem_price());
            //priceField.setText(numberAsString);
        }


        //in update dialog click image view to update image
        imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check external storage permission
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

        return v;
    }

    public static List<String> getDataTab1Array(){
        String h;
        if (Status.getText().toString().equals("Activated")) {
            h="Y";
        }else{
            h="N";
        }

        List<String> xxx = new ArrayList<String>();
        xxx.add(spinner.getText().toString());
        xxx.add(nameField.getText().toString());
        xxx.add(kategoryField.getText().toString());
        xxx.add(h);

        //xxx.add(priceField.getText().toString());

        return xxx;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 888) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //gallery intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 888);
            } else {
                Toast.makeText(getActivity(), "Don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getActivity().getSupportFragmentManager().getPrimaryNavigationFragment().getChildFragmentManager().getFragments())
        {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == 888 && resultCode == RESULT_OK) {
            /*Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON) //enable image guidlines
                    .setAspectRatio(1, 1)// image will be square
                    .start(this);*/

            Uri uriPhoto = data.getData();
            //Log.d(TAG + ".PICK_GALLERY_IMAGE", "Selected image uri path :" + uriPhoto.toString());

            //img.setImageURI(uriPhoto);
            //imgCompress.setImageURI(uriPhoto);
            imageViewIcon.setImageURI(uriPhoto);

            //sourceFile = new File(getPathFromGooglePhotosUri(uriPhoto));

            //destFile = new File(file, "img_" + dateFormatter.format(new Date()).toString() + ".png");
            //destFile = new File(file, "999" + ".png");

            Log.d(TAG, "Source File Path :" + sourceFile);

            if (destFile.exists()) {
                destFile.delete();
            }

/*
            try {
                copyFile(sourceFile, destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                //set image choosed from gallery to image view
                imageViewIcon.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

}

