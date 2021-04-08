package com.food.localresto;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.food.localresto.adapter.DaftarMenuItemAdapter;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.MenuCategoryObject;
import com.food.localresto.entities.MenuItemObject;
import com.food.localresto.util.AlertDialogHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.ByteArrayOutputStream;
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

public class DaftarMenuItemActivity extends AppCompatActivity implements AlertDialogHelper.AlertDialogListener{
    private static final String TAG = DaftarMenuItemActivity.class.getSimpleName();
    private RecyclerView recyclerViewUsers;

    private SqliteDatabase mDatabase;
    //private Query mDatabase;
    private String[] str;

    ArrayList<MenuItemObject> user_list = new ArrayList<>();
    public ArrayList<MenuItemObject> temp_list = new ArrayList<>();
    public ArrayList<MenuItemObject> temp_list1 = new ArrayList<>();

    ImageView imageViewIcon;
    private DaftarMenuItemAdapter mAdapter;
    private List<MenuCategoryObject> listProducts;

    private int posisiNya, posisiNa, menuidNya, jumlahnya, menuidna;
    private String jenisna;
    public AlertDialogHelper alertDialogHelper;

    //nasir nb
    public static final String DATE_FORMAT = "yyyyMMdd_HHmmss";
    public static final String IMAGE_DIRECTORY = "ImageResto";

    private static final String SCHEME_FILE = "file";
    private static final String SCHEME_CONTENT = "content";

    private Uri imageCaptureUri;

    private File file;
    private File sourceFile;
    private File destFile;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_menu_main);

        setTitle(getString(R.string.menu_items));

        //RecyclerView recyclerViewUsers = (RecyclerView) findViewById(R.id.menu_list);
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //recyclerViewUsers.setLayoutManager(mLayoutManager);
        //recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        //recyclerViewUsers.setHasFixedSize(true);
        //recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        //recyclerViewUsers.addItemDecoration(new ItemDividerDecoration(this, LinearLayoutManager.VERTICAL));

        alertDialogHelper = new AlertDialogHelper(this);

        recyclerViewUsers = (RecyclerView)findViewById(R.id.menu_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewUsers.setLayoutManager(linearLayoutManager);
        recyclerViewUsers.setHasFixedSize(true);

        //RecyclerView productView = (RecyclerView)findViewById(R.id.product_list);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //productView.setLayoutManager(linearLayoutManager);
        //productView.setHasFixedSize(true);

        //mDatabase = new SqliteDatabase(this);
        //final List<Product> allProducts = mDatabase.listProducts();


        //mDatabase = new Query(this);
        mDatabase = SqliteDatabase.getInstance(this);
        final List<MenuItemObject> allMenus = mDatabase.listMenuItemAll();

        /*if(allProducts.size() > 0){
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

                    AlertDialog.Builder dialog = new AlertDialog.Builder(DaftarMenuItemActivity.this);

                    dialog.setTitle("Choose an action");
                    dialog.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i == 0){

                                //showDialogUpdate(product);

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
        }*/

        mAdapter = new DaftarMenuItemAdapter(this, allMenus, new DaftarMenuItemAdapter.RecyclerViewClickListener() {
            @Override
            public void OnClick(View v, final int position) {
                //Toast.makeText(DaftarMenuItemActivity.this, "Position " + position, Toast.LENGTH_SHORT).show();

                menuidNya = allMenus.get(position).getMenu_item_id();
                posisiNya = position;

                final MenuItemObject menu = allMenus.get(position);

                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.inflate(R.menu.popup_menu);

                popupMenu.getMenu().findItem(R.id.view).setVisible(false);
                popupMenu.getMenu().findItem(R.id.reprint).setVisible(false);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:

                                //mAdapter.delMenuItemList(position);
                                //getDataFromSQLite();
                                posisiNa = menu.getMenu_item_id();

                                alertDialogHelper.showAlertDialog("", "Delete Item Menu", "DELETE", "CANCEL", 3, false);

                                break;

                            case R.id.update:

                                //alertDialogHelper.showAlertDialog("", "Update Item Menu", "UPDATE", "CANCEL", 2, false);
                                showDialogUpdate(menu);

                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }

            /*@Override
            public void onClick(View view, int position) {
                Toast.makeText(DaftarMenuItemActivity.this, "Position " + position, Toast.LENGTH_SHORT).show();
                //MenuCategoryObject item = menuObject.get(position);

                //Gson gson = ((CustomApplication)((Activity)getContext()).getApplication()).getGsonObject();
                //final String objectToString = gson.toJson(item);

                //if (mTwoPane) {

                    //PilihMenuFragment Obj = (PilihMenuFragment)getFragmentManager().findFragmentById(R.id.flMovieDetail);
                    //Obj.change(item.getMenu_id(), item.getMenu_name());

                //} else {
                    //Toast.makeText(getActivity(), "Item gak detail" + position + ": level " + item.getMenu_id() + ", text: " + item.getMenu_name(), Toast.LENGTH_SHORT).show();
                    //Intent foodIntent = new Intent(getActivity(), FoodActivity.class);
                    //foodIntent.putExtra("MENU_ITEM", objectToString);
                    //getActivity().startActivity(foodIntent);
                //}

            }*/
        });

        recyclerViewUsers.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new quick task
                addTaskDialog();
            }
        });

    }

    private void addTaskDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_menu_layout, null);

        final MaterialBetterSpinner spinner = (MaterialBetterSpinner) subView.findViewById(R.id.spinner2);

        final EditText nameField = (EditText) subView.findViewById(R.id.enter_name);
        //final EditText quantityField = (EditText)subView.findViewById(R.id.enter_kategory);
        final EditText kategoryField = (EditText) subView.findViewById(R.id.enter_description);
        imageViewIcon = (ImageView) subView.findViewById(R.id.imageView);

        final DecimalEditText priceField = (DecimalEditText) subView.findViewById(R.id.food_price);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new menu");
        builder.setView(subView);
        builder.create();

        //in update dialog click image view to update image
        imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check external storage permission
                ActivityCompat.requestPermissions(
                        DaftarMenuItemActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );

            }
        });

        final List<MenuCategoryObject> categoryObject = mDatabase.listProducts();
        str = new String[categoryObject.size()];
        for (int i = 0; i < categoryObject.size(); i++) {
            str[i] = categoryObject.get(i).getMenu_name();
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        builder.setPositiveButton("ADD MENU", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String menu = spinner.getText().toString();
                String name = nameField.getText().toString();
                //final int quantity = Integer.parseInt(quantityField.getText().toString());
                String kategory = kategoryField.getText().toString();

                double d = 0;
                d = priceField.getValue().doubleValue();

                Log.d(TAG, "Nilai Price edit string 1 : " + d);
                DecimalFormat df = new DecimalFormat("#########");
                String str = df.format(d);
                Log.d(TAG, "Nilai Price edit string 2 : " + str);


           /*     if (menu.trim().equals("Ikan")) {
                    menuidna = 1;
                    jenisna = "Makanan";
                } else if (menu.trim().equals("Udang")) {
                    menuidna = 2;
                    jenisna = "Makanan";
                } else if (menu.trim().equals("Kerang")) {
                    menuidna = 3;
                    jenisna = "Makanan";
                } else if (menu.trim().equals("Dingin")) {
                    menuidna = 4;
                    jenisna = "Minuman";
                } else if (menu.trim().equals("Panas")) {
                    menuidna = 5;
                    jenisna = "Minuman";
                } else if (menu.trim() == "Kemasan")) {
                    menuidna = 6;
                    jenisna = "Minuman";
                } else {
                    menuidna = 7;
                }*/

                //final ArrayList<MenuCategoryObject> filteredMemeberList = new ArrayList<>();
                for (MenuCategoryObject model : categoryObject) {
                    //Integer menuidna = model.getMenu_id();
                    //String jenisna = model.getMenu_jenis().toUpperCase();
                    String namamenu = model.getMenu_name();
                    if (namamenu.contains(menu)) {
                        //filteredMemeberList.add(model);
                        menuidna = model.getMenu_id();
                        jenisna = model.getMenu_jenis();
                    }
                }

                float pricefloat = Float.parseFloat(str);

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(kategory)) {
                    Toast.makeText(DaftarMenuItemActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
                    MenuItemObject newProduct = new MenuItemObject(0, String.valueOf(menuidna), menuidna, jenisna, menu, name, kategory, ".png", pricefloat);

                    mDatabase.addMenuItem(newProduct);


                    mAdapter.insertRowData(user_list.size(), newProduct);
                    mAdapter.notifyDataSetChanged();

                    getDataFromSQLite();

                    recyclerViewUsers.scrollToPosition(user_list.size());

/*                    Bundle extras = new Bundle();

                    //intent.putExtra("MENU_ITEM_GOTO", "GOTO_ADD");

                    extras.putInt("MENU_ITEM_GOTO", jumlahnya);
                    Intent intent = new Intent(DaftarMenuItemActivity.this, DaftarMenuItemActivity.class);
                    intent.putExtras(extras);
                    startActivity(intent);*/

                    //mAdapter.notifyDataSetChanged();

                    //productView.scrollToPosition(jmlRecord-1);
                    //((LinearLayoutManager)productView.getLayoutManager()).scrollToPositionWithOffset(jmlRecord,0);

                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(DaftarMenuActivity.this, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    private void showDialogUpdate(final MenuItemObject menu) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_menu_layout, null);

        final MaterialBetterSpinner spinner = (MaterialBetterSpinner) subView.findViewById(R.id.spinner2);
        final EditText nameField = (EditText) subView.findViewById(R.id.enter_name);
        //final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);
        //imageViewIcon = (ImageView)subView.findViewById(R.id.imageView);
        final EditText kategoryField = (EditText) subView.findViewById(R.id.enter_description);
        imageViewIcon = (ImageView) subView.findViewById(R.id.imageView);
        //final EditText priceField = (EditText)subView.findViewById(R.id.food_price);

        final DecimalEditText priceField = (DecimalEditText) subView.findViewById(R.id.food_price);

        // Spinner Drop down elements
        //List<MenuCategoryObject> categoryObject = new ArrayList<MenuCategoryObject>();
        List<MenuCategoryObject> categoryObject = mDatabase.listProducts();
        String[] str = new String[categoryObject.size()];
        for (int i = 0; i < categoryObject.size(); i++) {
            str[i] = categoryObject.get(i).getMenu_name();
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        if (menu != null) {

            spinner.setText(menu.getMenu_name());
            nameField.setText(menu.getItem_name());
            //quantityField.setText(String.valueOf(menu.getQuantity()));
            //kategoryField.getText(menu.getKategory());
            kategoryField.setText(menu.getDescription());

            //byte[] recordImage = menu.getImage();
            //Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage, 0, recordImage.length);
            //imageViewIcon.setImageBitmap(bitmap);
            //String filename = menu.getImage();
            String filename = menu.getMenu_item_id() + ".png";
            //String imagePath = Environment.getExternalFilesDir() + "/" + filename;
            file = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_DIRECTORY);
            destFile = new File(file, filename);

            Glide.with(getApplicationContext())
                    .load(destFile)
                    .signature(new ObjectKey(System.currentTimeMillis()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .override(150, 150)
                    .into(imageViewIcon);

            String totalHarga = String.valueOf(menu.getItem_price());

            Log.d(TAG, "Nilai Price update 1: " + totalHarga);


            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
            String numberAsString = decimalFormat.format(menu.getItem_price());

            //Log.d(TAG, "Nilai Price update 2: " + numberAsString);

            priceField.setText(numberAsString);


        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit product");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //final String menunya = spinner.getText().toString().toUpperCase();
                final String menunya = spinner.getText().toString();
                final String name = nameField.getText().toString();
                //final int quantity = Integer.parseInt(quantityField.getText().toString());
                final String kategory = kategoryField.getText().toString();

                double d = 0;
                //d = priceField.getCurrencyDouble();
                d = priceField.getValue().doubleValue();

                Log.d(TAG, "Nilai Price edit string 1 : " + d);
                DecimalFormat df = new DecimalFormat("#########");
                String str = df.format(d);
                Log.d(TAG, "Nilai Price edit string 2 : " + str);


                final List<MenuCategoryObject> categoryObject = mDatabase.listProducts();
                for (MenuCategoryObject model : categoryObject) {
                    //final Integer menuidna = model.getMenu_id();
                    //final String jenisna = model.getMenu_jenis().toUpperCase();
                    String namamenu = model.getMenu_name();
                    if (namamenu.contains(menunya)) {
                        //filteredMemeberList.add(model);
                        menuidna = model.getMenu_id();
                        jenisna = model.getMenu_jenis();
                    }
                }

                float pricefloat = Float.parseFloat(str);


                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(kategory)) {
                    Toast.makeText(DaftarMenuItemActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
                    mDatabase.updateMenuItem(new MenuItemObject(menu.getMenu_item_id(), String.valueOf(menuidna) + menu.getMenu_item_id(), menuidna, jenisna, menunya, name, kategory, menu.getMenu_item_id() + ".png", pricefloat));
                    //mDatabase.updateMenuItem(new MenuItemObject()
                    //refresh the activity
                    finish();
                    //startActivity(getIntent());

                    //Toast.makeText(getApplicationContext(), " is longgggxxxx! " + posisiNya, Toast.LENGTH_SHORT).show();

                    //mDatabase.addMenuItem(newProduct);

                    //getDataFromSQLite();

                    //mAdapter.insertRowData(user_list.size(), newProduct);
                    //mAdapter.notifyDataSetChanged();
                    //mAdapter.updateMenuItemList(posisiNya);
                    //getDataFromSQLite();


                    //recyclerViewUsers.scrollToPosition(posisiNya);

                    Bundle extras = new Bundle();

                    //intent.putExtra("MENU_ITEM_GOTO", "GOTO_ADD");

                    extras.putInt("MENU_ITEM_GOTO", posisiNya + 2);
                    Intent intent = new Intent(DaftarMenuItemActivity.this, DaftarMenuItemActivity.class);
                    intent.putExtras(extras);
                    startActivity(intent);

                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(DaftarMenuActivity.this, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });


        //in update dialog click image view to update image
        imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check external storage permission
                ActivityCompat.requestPermissions(
                        DaftarMenuItemActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

        builder.show();

    }

    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                user_list.clear();
                List<MenuItemObject> allMenus = mDatabase.listMenuItemAll();
                user_list.addAll(allMenus);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }*/

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    /*public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 888) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //gallery intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 888);
            } else {
                Toast.makeText(this, "Don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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

            sourceFile = new File(getPathFromGooglePhotosUri(uriPhoto));

            //destFile = new File(file, "img_" + dateFormatter.format(new Date()).toString() + ".png");
            //destFile = new File(file, "999" + ".png");

            Log.d(TAG, "Source File Path :" + sourceFile);

            if (destFile.exists()) {
                destFile.delete();
            }


            try {
                copyFile(sourceFile, destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

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

        super.onActivityResult(requestCode, resultCode, data);
    }

    //nasir nb
    private Bitmap decodeFile(File f) {
        Bitmap b = null;

        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int IMAGE_MAX_SIZE = 1024;
        int scale = 1;
        if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
            scale = (int) Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE /
                    (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
        }

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        try {
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Width :" + b.getWidth() + " Height :" + b.getHeight());

        destFile = new File(file, "img_"
                + dateFormatter.format(new Date()).toString() + ".png");
        try {
            FileOutputStream out = new FileOutputStream(destFile);
            b.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    public String getPathFromGooglePhotosUri(Uri uriPhoto) {
        if (uriPhoto == null)
            return null;

        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uriPhoto, "r");
            FileDescriptor fd = pfd.getFileDescriptor();
            input = new FileInputStream(fd);

            String tempFilename = getTempFilename(this);
            output = new FileOutputStream(tempFilename);

            int read;
            byte[] bytes = new byte[4096];
            while ((read = input.read(bytes)) != -1) {
                output.write(bytes, 0, read);
            }
            return tempFilename;
        } catch (IOException ignored) {
            // Nothing we can do
        } finally {
            closeSilently(input);
            closeSilently(output);
        }
        return null;
    }

    public static void closeSilently(Closeable c) {
        if (c == null)
            return;
        try {
            c.close();
        } catch (Throwable t) {
            // Do nothing
        }
    }

    private static String getTempFilename(Context context) throws IOException {
        File outputDir = context.getCacheDir();
        File outputFile = File.createTempFile("image", "tmp", outputDir);
        return outputFile.getAbsolutePath();
    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    @Override
    /*public void onPositiveClick(int from) {

    }*/
    public void onPositiveClick(int from) {
        /*if (from == 1) {
            if (multiselect_list.size() > 0) {
                for (int i = 0; i < multiselect_list.size(); i++) {


                    try {

                        //arm = userDatabaseHelper.deleteFact(multiselect_list.get(i));

                    } catch (Exception e) {
                        Log.e("error", e.toString());
                    }

                    user_list.remove(multiselect_list.get(i));

                    mAdapter.notifyDataSetChanged();


                }


                if (mActionMode != null) {
                    mActionMode.finish();
                }
            }
        }else*/ if (from == 3) {

            //mDatabase.deleteMenuItem(multiselect_list.get(0).getMenu_item_id());
            //Toast.makeText(getApplicationContext(), " is deleted menu_id : " + multiselect_list.get(0).getItem_name(), Toast.LENGTH_SHORT).show();

            //arm = mDatabase.deleteMenuItem(multiselect_list.get(0).getMenu_item_id());
            mDatabase.deleteMenuItem(posisiNa);
            finish();
            Toast.makeText(getApplicationContext(), " is deleted menu_id : " + posisiNa, Toast.LENGTH_SHORT).show();

            //user_list.remove(multiselect_list.get(0));

            mAdapter.notifyDataSetChanged();

            //if (mActionMode != null) {
            //    mActionMode.finish();
            //}
        }


        /*if (arm) {

            getDataFromSQLite();
            mAdapter.notifyDataSetChanged();


        } else if (from == 2) {
            if (mActionMode != null) {
                mActionMode.finish();
            }

            //MenuItemObject mSample = new MenuItemObject();
            //user_list.add(mSample);
            mAdapter.notifyDataSetChanged();

        }*/
    }


    @Override
    public void onNegativeClick(int from) {

    }

    @Override
    public void onNeutralClick(int from) {

    }
}
