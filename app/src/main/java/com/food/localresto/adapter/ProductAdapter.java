package com.food.localresto.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.R;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.MenuCategoryObject;
import com.food.localresto.util.Helper;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private Context context;
    private List<MenuCategoryObject> listProducts;
    private ClickListener recyclerViewItemClickListener;

    private SqliteDatabase mDatabase;
    //private Query mDatabase;
    private int oldId;
    private String oldCat;


    final int REQUEST_CODE_GALLERY = 999;

    public ProductAdapter(Context context, List<MenuCategoryObject> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
        mDatabase = new SqliteDatabase(context);
        //mDatabase = new Query(context);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final MenuCategoryObject singleProduct = listProducts.get(position);

        //byte[] recordImage = singleProduct.getImage();
        //Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage, 0, recordImage.length);
        //holder.foodImage.setImageBitmap(bitmap);

        holder.name.setText(singleProduct.getMenu_name());

        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(singleProduct);
            }
        });

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Confirm Delete?");
                builder.setCancelable(true);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //delete row from database

                        //check data ada tidak di menu item
                        long result = mDatabase.QountMenuItembyMenu(singleProduct.getMenu_name().toUpperCase());
                        if (result > 0) {
                            Helper.displayErrorMessage(context, "Failed to delete kategori menu");
                        } else {
                            //mDatabase.deleteProduct(singleProduct.getKode());
                            mDatabase.deleteProduct(singleProduct.getMenu_id());
                            Toast.makeText(context, "Delete Success!", Toast.LENGTH_SHORT).show();
                        }

                        //refresh the activity page.
                        ((Activity)context).finish();
                        context.startActivity(((Activity) context).getIntent());

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    //Set method of OnItemClickListener object
  /*  public void setOnItemClickListener(ClickListener recyclerViewItemClickListener){
        this.recyclerViewItemClickListener=recyclerViewItemClickListener;
    }
*/
    public void editTaskDialog(final MenuCategoryObject product){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_product_layout_local, null);

        final MaterialBetterSpinner spinner = (MaterialBetterSpinner)subView.findViewById(R.id.spinner1);
        final EditText kodeField = (EditText)subView.findViewById(R.id.kode_kategory);
        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        //final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);

        if(product != null){
            spinner.setText(product.getMenu_jenis());
            kodeField.setText(String.valueOf(product.getMenu_id()));
            nameField.setText(product.getMenu_name());
            //quantityField.setText(String.valueOf(product.getQuantity()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit product");
        builder.setView(subView);
        builder.create();

        // Spinner Drop down elements
        final List<String> categories = new ArrayList<String>();
        categories.add("Makanan");
        categories.add("Minuman");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter <String>(context, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        builder.setPositiveButton("EDIT PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String jenis = spinner.getText().toString();
                final int kode = Integer.parseInt(kodeField.getText().toString());
                final String name = nameField.getText().toString();
                //final int quantity = Integer.parseInt(quantityField.getText().toString());


                //if(TextUtils.isEmpty(name) || quantity <= 0){

                if(TextUtils.isEmpty(name) ){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{

                    int index = categories.indexOf(jenis)+1;

                    String menuid = "1001"+index;

                    //mDatabase.updateProduct(new MenuCategoryObject(product.getKode(), name, quantity, MenuItemLocalActivity.imageViewToByte(mImageView)));
                    MenuCategoryObject newProduct = new MenuCategoryObject(product.getId(),product.getKode(), Integer.parseInt(menuid), jenis,name,"");
                    //mDatabase.updateProduct(newProduct);
                    //mDatabase.UpdateMenuCat(newProduct,oldCat);

                    oldId = product.getMenu_id();
                    oldCat = product.getMenu_name();
                    long result = mDatabase.UpdateMenuCat(newProduct);
                    if (result > 0) {
                        //mDatabase.deleteProduct(singleProduct.getKode());
                        mDatabase.MenuItemUpdate(name,oldId,oldCat);
                    } else {

                        Helper.displayErrorMessage(context, "Nama kategori menu sudah ada!");
                    }

                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });


        builder.show();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView deleteProduct;
        public  ImageView editProduct;
        //public  ImageView foodImage;
        //public int position=0;

        public ProductViewHolder(View itemView) {
            super(itemView);
            //foodImage = (ImageView)itemView.findViewById(R.id.food_image);
            name = (TextView)itemView.findViewById(R.id.product_name);
            deleteProduct = (ImageView)itemView.findViewById(R.id.delete_product);
            editProduct = (ImageView)itemView.findViewById(R.id.edit_product);

        }
    }

}