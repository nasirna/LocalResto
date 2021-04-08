package com.food.localresto;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.adapter.RecyclerViewClickListener;
import com.food.localresto.adapter.TableAdapter;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.HistoryObject;
import com.food.localresto.util.CustomApplication;
import com.food.localresto.util.DrawCart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.food.localresto.adapter.TableAdapter.checkAvailability;
import static com.food.localresto.database.SqliteDatabase.getDateTime;

public class TableActivity extends AppCompatActivity {

    private LinearLayout generalWrapper;
    private RecyclerView recyclerView;
    int posisi;

    // ArrayList for table names
    ArrayList tableNames = new ArrayList<>(Arrays.asList("Table 1", "Table 2", "Table 3", "Table 4", "Table 5", "Table 6", "Table 7", "Table 8", "Table 9", "Table 10"));


    //private Query mDatabase;
    private SqliteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        generalWrapper = (LinearLayout)findViewById(R.id.general_wrapper);

        //buka lagi kalo mau di buka

        recyclerView = (RecyclerView)findViewById(R.id.table_seat);
        GridLayoutManager mGrid = new GridLayoutManager(this.getApplication(), 2);
        recyclerView.setLayoutManager(mGrid);
        recyclerView.setHasFixedSize(true);

        //TableAdapter mAdapter = new TableAdapter(getApplication(), tableNames);
        //recyclerView.setAdapter(mAdapter);

        //mDatabase = new Query(this);
        mDatabase = SqliteDatabase.getInstance(this);
        final List<HistoryObject> orderHistory = mDatabase.listHistorybyAvailTableId();

        //for (int i = 0; i < response.length; i++){
            /*if(tableNames.contains("Table 11")) {
                Toast.makeText(getApplication(), "yes ada" , Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplication(), "tidak ada" , Toast.LENGTH_LONG).show();
            }*/


        TableAdapter mAdapter = new TableAdapter(getApplicationContext(), tableNames, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(getApplication(), "position" + position, Toast.LENGTH_LONG).show();
                //DrawCart dCart = new DrawCart(getApplication());
                //menuItem.setIcon(dCart.buildCounterDrawable(position, R.drawable.cart));
                posisi = position+1;
                MainActivity.mSeat=posisi;
                //((CustomApplication)getApplication()).getShared().saveTableInformation(String.valueOf(posisi));

                if (checkAvailability(orderHistory,posisi)){
                    //finish();
                    showDialogBayar(posisi);
                }else{
                    invalidateTable();
                    finish();
                }


            }
        });


        Button takeAwayButton = (Button) findViewById(R.id.takeaway_id);
        takeAwayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplication(), "take away", Toast.LENGTH_LONG).show();
                MainActivity.mSeat=0;
                invalidateTable();
                finish();
            }
        });


        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.tableresto);
        int mCount = ((CustomApplication)getApplication()).cartItemCount();
        //int mCount = ((CustomApplication)getApplication()).getTableSeat(6);
        DrawCart dCart = new DrawCart(this);
        menuItem.setIcon(dCart.buildCounterDrawable2(posisi, R.drawable.dining));

        menuItem = menu.findItem(R.id.action_shop);
        menuItem.setIcon(dCart.buildCounterDrawable(mCount, R.drawable.cart));
        menuItem.setVisible(false);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.tableresto) {
            Intent checkoutIntent = new Intent(getApplication(), TableActivity.class);
            startActivity(checkoutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void invalidateTable() {
        invalidateOptionsMenu();
    }

    private void showDialogBayar(int posisi){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.confirm_bayar_layout_local, null);

        //final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        //final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);
        final Button btnNambah =(Button)subView.findViewById(R.id.btn_nambah_menu);
        final Button btnBayar =(Button)subView.findViewById(R.id.btn_bayar_menu);
        //imageViewIcon = (ImageView)subView.findViewById(R.id.imageView);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Table "+posisi+" :");
        builder.setView(subView);
        builder.create();

        //in update dialog click image view to update image
        /*imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check external storage permission
                ActivityCompat.requestPermissions(
                        TableActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });*/

        btnNambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check external storage permission
                //Toast.makeText(TableActivity.this, "Nambah", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent sampleIntent = new Intent(TableActivity.this, OrderConfirmTableActivity.class);
                //startActivity(sampleIntent);

                final Bundle extras = new Bundle();
                extras.putString("NAMA", restaurantDetails(0));
                extras.putString("ALAMAT", restaurantDetails(1));
                extras.putString("KOTA", restaurantDetails(2));
                extras.putString("KODENA", "9999201020143424");
                extras.putString("DATENA", getDateTime());
                //extras.putString("ORDER_LIST", order_list);

                Intent foodIntent = new Intent(TableActivity.this, OrderConfirmTableActivity.class);
                foodIntent.putExtras(extras);
                startActivity(foodIntent);
            }
        });

        /*builder.setPositiveButton("ADD PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final int quantity = Integer.parseInt(quantityField.getText().toString());

                *//*if(TextUtils.isEmpty(name) || quantity <= 0){
                    Toast.makeText(TableActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    Product newProduct = new Product(name, quantity, imageViewToByte(imageViewIcon));
                    mDatabase.addProduct(newProduct);

                    //refresh the activity
                    finish();
                    startActivity(getIntent());
                }*//*
            }
        });*/

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(TableActivity.this, "Task cancelled", Toast.LENGTH_LONG).show();
                finish();
                MainActivity.mSeat=0;
            }
        });
        builder.show();
    }


    private String restaurantDetails(int index) {
        String restaurant = ((CustomApplication) getApplication()).getShared().getRestaurantInformation();
        String[] restaurantList = restaurant.split(":");
        if (restaurantList.length > 0) {
            return restaurantList[index];
        }
        return "";
    }
}
