package com.food.localresto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.adapter.ProductAdapter;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.MenuCategoryObject;
import com.food.localresto.util.Helper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import com.food.localresto.adapter.ClickListener;
import com.food.localresto.adapter.RecyclerTouchListener;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.List;

public class DaftarKategoryActivity extends AppCompatActivity {

    private static final String TAG = DaftarKategoryActivity.class.getSimpleName();

    private SqliteDatabase mDatabase;
    //private Query mDatabase;
    private int getid;

    ImageView imageViewIcon;
    private ProductAdapter mAdapter;
    private List<MenuCategoryObject> listProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuitemlocal);

        setTitle(getString(R.string.menu_categories));

        FrameLayout fLayout = (FrameLayout) findViewById(R.id.activity_to_do);

        RecyclerView productView = (RecyclerView)findViewById(R.id.product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);

        //mDatabase = new SqliteDatabase(this);
        //final List<Product> allProducts = mDatabase.listProducts();
        //mDatabase = new Query(this);
        mDatabase = SqliteDatabase.getInstance(this);
        final List<MenuCategoryObject> allProducts = mDatabase.listProducts();
        //id = allProducts.size()+1;
        if(allProducts.size() > 0){
            productView.setVisibility(View.VISIBLE);
            ProductAdapter mAdapter = new ProductAdapter(this, allProducts);


            //productView.addOnItemTouchListener(new
            productView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), productView, new ClickListener() {


                @Override
                public void onClick(View view, int position) {
                    //Movie movie = movieList.get(position);
                    Toast.makeText(getApplicationContext(), " is selectedddd! "+ position, Toast.LENGTH_SHORT).show();
                }

                @Override
                public boolean onLongClick(View view, final int position) {
                    Toast.makeText(getApplicationContext(), " is longggg! " + position, Toast.LENGTH_SHORT).show();

                    final MenuCategoryObject product = allProducts.get(position);

                    //alert dialog to display options of update and delete
                    final CharSequence[] items = {"Update", "Delete"};

                    AlertDialog.Builder dialog = new AlertDialog.Builder(DaftarKategoryActivity.this);

                    dialog.setTitle("Choose an action");
                    dialog.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i == 0){

                                showDialogUpdate(product);

                            }
                            if (i==1){
                            }
                        }
                    });
                    dialog.show();
                    return true;

                }
            }));

            productView.setAdapter(mAdapter);

        }else {
            productView.setVisibility(View.GONE);
            Toast.makeText(this, "There is no product in the database. Start adding now", Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new quick task
                addTaskDialog();
            }
        });

        Log.d(TAG, "test TAGGGGG");

    }

    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_product_layout_local, null);

        final MaterialBetterSpinner spinner = (MaterialBetterSpinner)subView.findViewById(R.id.spinner1);
        //final Spinner spinner = (Spinner) subView.findViewById(R.id.spinner1);
        final EditText kodeField = (EditText)subView.findViewById(R.id.kode_kategory);
        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new product");
        builder.setView(subView);
        builder.create();

        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.spinner1);
            materialDesignSpinner.setAdapter(arrayAdapter);*/

        // Spinner element
        //Spinner spinner = (Spinner) findViewById(R.id.spinner1);

        // Spinner click listener
        //spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        final List<String> categories = new ArrayList<String>();
        categories.add("Makanan");
        categories.add("Minuman");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


       /* spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getid = parent.getSelectedItemPosition();   ///get the selected element place id
                //textView1.setText("Position of selected element: "+String.valueOf(getid));
                //String getvalue = String.valueOf(parent.getItemAtPosition(position));   // getting the selected element value
                //textView2.setText("Value of Selected Spinner : "+getvalue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        //in update dialog click image view to update image
        /*imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check external storage permission
                ActivityCompat.requestPermissions(
                        DaftarKategoryActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });*/



        builder.setPositiveButton("ADD PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //final String jenis = spinner.getText().toString();

                final String jenis = spinner.getText().toString();
                //final int kode = Integer.parseInt(kodeField.getText().toString());
                final String name = nameField.getText().toString();
                //final int quantity = Integer.parseInt(quantityField.getText().toString());


                //if(TextUtils.isEmpty(name) || quantity <= 0){
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(DaftarKategoryActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{

                    //List<String> list = Arrays.asList(new String[] {"First", "Second", "Third"});
                    int index = categories.indexOf(jenis)+1;

                    String menuid = "1001"+index;

                    MenuCategoryObject newProduct = new MenuCategoryObject(0,0,Integer.parseInt(menuid),jenis,name,"");


                    //check data ada tidak di menu item
                    long result = mDatabase.QountMenuCat(name.toUpperCase());
                    if (result > 0) {
                        Helper.displayErrorMessage(DaftarKategoryActivity.this, "Nama kategori menu sudah ada!");
                    } else {
                        mDatabase.addMenuCat(newProduct);
                        Toast.makeText(DaftarKategoryActivity.this, "Add Success!", Toast.LENGTH_SHORT).show();
                    }


                    //refresh the activity
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DaftarKategoryActivity.this, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }*/

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 888){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //gallery intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 888);
            }
            else {
                Toast.makeText(this, "Don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 888 && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON) //enable image guidlines
                    .setAspectRatio(1,1)// image will be square
                    .start(DaftarKategoryActivity.this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result =CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                //set image choosed from gallery to image view
                imageViewIcon.setImageURI(resultUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    ///////////////////////////////////

    private void showDialogUpdate(final MenuCategoryObject product){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_product_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        //final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);
        //imageViewIcon = (ImageView)subView.findViewById(R.id.imageView);

        if(product != null){
            nameField.setText(product.getMenu_name());
            //quantityField.setText(String.valueOf(product.getQuantity()));

            //byte[] recordImage = product.getImage();
            //Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage, 0, recordImage.length);
            //imageViewIcon.setImageBitmap(bitmap);

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit product");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                //final int quantity = Integer.parseInt(quantityField.getText().toString());

                //if(TextUtils.isEmpty(name) || quantity <= 0){
                if(TextUtils.isEmpty(name)){
                    Toast.makeText( DaftarKategoryActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{

                    MenuCategoryObject newProduct = new MenuCategoryObject(product.getId(),product.getKode(), product.getMenu_id(), product.getMenu_jenis(),product.getMenu_name(),"");

                    //mDatabase.updateProduct(new MenuCategoryObject(product.getKode(), product.getMenu_id(), product.getMenu_jenis(),product.getMenu_name()));
                    mDatabase.updateProduct(newProduct);

                    //refresh the activity
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });


        //in update dialog click image view to update image
        /*imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check external storage permission
                ActivityCompat.requestPermissions(
                        DaftarKategoryActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });*/

        builder.show();

    }
}
