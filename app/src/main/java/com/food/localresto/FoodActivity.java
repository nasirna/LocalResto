package com.food.localresto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.CartObject;
import com.food.localresto.entities.MenuItemObject;
import com.food.localresto.util.CustomApplication;
import com.food.localresto.util.CustomSharedPreference;
import com.food.localresto.util.DrawCart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    private static final String TAG = FoodActivity.class.getSimpleName();

    private LinearLayout boxWrapper;

    private MenuItemObject singleMenuItem;

    private LinearLayout.LayoutParams params;

    private List<CheckBox> optionCheckBox = new ArrayList<CheckBox>();

    private Gson gson;
    private CustomSharedPreference shared;
    //private Query dbQuery;
    private SqliteDatabase mDatabase;

    private String addedOrderNote = "";
    private String addedOrderOptions = "";

    private Integer extra1;
    private String extra2,extra3,extra5, kodeitemnya;
    private Float extra4;

    private FloatingActionButton fab;
    int minteger = 1;

    //nasir nb
    private TextView menuItemExtra,menuItemNote;
    private File file;
    private File destFile;
    public static final String IMAGE_DIRECTORY = "ImageResto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        gson = ((CustomApplication)getApplication()).getGsonObject();
        shared = ((CustomApplication)getApplication()).getShared();
        //dbQuery = new Query(this);
        mDatabase = SqliteDatabase.getInstance(this);

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView menuItemImage = (ImageView)findViewById(R.id.food_item_image);
        TextView menuItemName = (TextView)findViewById(R.id.food_item_name);
        TextView menuItemPrice = (TextView)findViewById(R.id.food_item_price);
        //TextView menuItemDescription = (TextView)findViewById(R.id.food_item_description);
        final TextView menuItemTotal = (TextView)findViewById(R.id.food_item_total);

        menuItemExtra = (TextView)findViewById(R.id.order_item_extra);
        menuItemNote = (TextView)findViewById(R.id.order_item_note);

        /*ImageView favoriteButton = (ImageView)findViewById(R.id.favorite_icon);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.displayErrorMessage(FoodActivity.this, "Your favorite was successfully added");
                storeFavoriteMenuItem(singleMenuItem);
            }
        });*/

        Button customizeButton = (Button)findViewById(R.id.customize_button);
        customizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomizeOrderDialog();
            }
        });


        fab = (FloatingActionButton)findViewById(R.id.add_to_cart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //loading =true;  //nasir

                CartObject orderItem = new CartObject(extra1, kodeitemnya, extra2, minteger,
                        extra4, addedOrderOptions, addedOrderNote);
                addMenuItemToCart(orderItem);
                invalidateCart();

                onBackPressed();

            }
        });

        boxWrapper = (LinearLayout)findViewById(R.id.box_wrapper);
        boxWrapper.setVisibility(View.GONE);

        //Get any potential intent that was passed to the activity
        Intent intent = getIntent();

        if (intent != null) {
            kodeitemnya = getIntent().getExtras().getString("MENU_KODEITEM");
            extra1 = getIntent().getExtras().getInt("MENU_ITEM_ID");//intent.getStringExtra("MENU_ITEM_GOTO");
            extra2 = getIntent().getExtras().getString("MENU_ITEM_NAME");
            extra3 = getIntent().getExtras().getString("MENU_ITEM_DESC");
            extra4 = getIntent().getExtras().getFloat("MENU_ITEM_PRICE");
            extra5 = getIntent().getExtras().getString("MENU_ITEM_PICT");
            //byte[] recordImage = getIntent().getExtras().getByteArray("MENU_ITEM_PICT");

            boxWrapper.setVisibility(View.VISIBLE);

            setTitle(extra2);


            //Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage, 0, recordImage.length);
            //menuItemImage.setImageBitmap(bitmap);

            String filename = extra5;
            file = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_DIRECTORY);
            destFile = new File(file, filename);

            //String serverImagePath = Helper.PUBLIC_FOLDER + singleMenuItem.getItem_picture();
            Glide.with(FoodActivity.this)
                    .load(destFile)
                    .signature(new ObjectKey(System.currentTimeMillis()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .override(300, 300)
                    .into(menuItemImage);


            menuItemName.setText(extra2);

            float HargaMenu = extra4;
            menuItemPrice.setText("Rp " + String.format("%1$,.0f", Float.valueOf(extra4) ));
            //MoneyTextView moneyTextView = (MoneyTextView) findViewById(R.id.item_amount);
            //moneyTextView.setAmount(extra4);
            menuItemTotal.setText("Rp " + String.format("%1$,.0f", Float.valueOf(extra4) ));

            //}else {
            //Toast.makeText(FoodActivity.this, getString(R.string.no_information), Toast.LENGTH_LONG).show();
        }

        //nasir

        Button minusButton = (Button)findViewById(R.id.minus_button);
        minusButton.setOnClickListener(new View.OnClickListener() {
            double price = extra4;
            @Override
            public void onClick(View view) {


                if(minteger ==1){
                    return;
                }

                minteger = minteger - 1;
                display(minteger);
                //displayItem((int) (price * minteger));
                menuItemTotal.setText("Rp " + String.format("%1$,.0f", Float.valueOf((int) price*minteger) ));

            }
        });

        Button plusButton = (Button)findViewById(R.id.plus_button);
        plusButton.setOnClickListener(new View.OnClickListener() {
            double price = extra4;

            @Override
            public void onClick(View view) {
                //int price = (int) singleMenuItem.getItem_price();
                minteger = minteger + 1;
                display(minteger);
                //displayItem((int) (price*minteger));
                menuItemTotal.setText("Rp " + String.format("%1$,.0f", Float.valueOf((int) price*minteger) ));
            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_shop);
        int mCount = ((CustomApplication)getApplication()).cartItemCount();
        DrawCart dCart = new DrawCart(this);
        menuItem.setIcon(dCart.buildCounterDrawable(mCount, R.drawable.cart));
        menuItem.setVisible(false);
        MenuItem menuItem2 = menu.findItem(R.id.tableresto);
        menuItem2.setVisible(false);


        return true;
    }*/

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.tableresto);
        MenuItem menuItem2 = menu.findItem(R.id.menu_more);
        if(menuItem!=null || menuItem2!=null){
            menuItem.setVisible(false);
            menuItem2.setVisible(false);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_shop) {
            Intent checkoutIntent = new Intent(FoodActivity.this, CartActivity.class);
            startActivity(checkoutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void invalidateCart() {
        invalidateOptionsMenu();
    }

    private void showCustomizeOrderDialog(){
        LayoutInflater inflater = LayoutInflater.from(FoodActivity.this);
        View subView = inflater.inflate(R.layout.add_option_layout, null);

        LinearLayout wrapLayout = (LinearLayout)subView.findViewById(R.id.option_layout);
        final EditText mOrderNote = (EditText)subView.findViewById(R.id.order_note);

        //final String mOptions = singleMenuItem.getItem_options();
        final String mOptions ="Besar,Sedang,Kecil";
        if(TextUtils.isEmpty(mOptions)){
            TextView noticeTextView = new TextView(FoodActivity.this);
            noticeTextView.setText(R.string.no_option_item);
            noticeTextView.setLayoutParams(params);

            wrapLayout.removeAllViews();
            wrapLayout.addView(noticeTextView);
        }else{
            String[] allOptions = mOptions.split(",");
            optionCheckBox.clear();
            wrapLayout.removeAllViews();
            for(int i = 0; i < allOptions.length; i++){
                CheckBox oneBox = createDynamicCheckBox(i, allOptions[i]);
                optionCheckBox.add(oneBox);
                wrapLayout.addView(oneBox);
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Menu Item Option");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addedOrderNote = mOrderNote.getText().toString();
                addedOrderOptions =""; //nb
                for(int i = 0; i < optionCheckBox.size(); i++){
                    CheckBox mBox = optionCheckBox.get(i);
                    if(mBox.isChecked()){
                        addedOrderOptions += mBox.getText().toString() + " ";
                    }
                }

                menuItemExtra.setText(addedOrderOptions); //nb
                menuItemNote.setText(addedOrderNote); //nb
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(FoodActivity.this, "No order customization added", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    private CheckBox createDynamicCheckBox(int index, String textContent){
        CheckBox checkBox = new CheckBox(this);
        checkBox.setId(index);
        checkBox.setText(textContent);
        checkBox.setLayoutParams(params);
        return checkBox;
    }

    private void addMenuItemToCart(CartObject orderItem){
        if(shared.getCartItems().equals("")){
            List<CartObject> cartListItems = new ArrayList<CartObject>();
            cartListItems.add(orderItem);
            storeCartOrderList(cartListItems);
        }else{
            String storeOrderList = shared.getCartItems();
            CartObject[] cartItemsCollections = gson.fromJson(storeOrderList, CartObject[].class);
            DrawCart cart = new DrawCart(this);
            List<CartObject> allOrders = cart.convertObjectArrayToListObject(cartItemsCollections);
            allOrders.add(orderItem);
            storeCartOrderList(allOrders);
        }
    }

    private void storeCartOrderList(List<CartObject> orderList){
        String mOrders = gson.toJson(orderList);
        shared.updateCartItems(mOrders);
    }

    private void storeFavoriteMenuItem(MenuItemObject itemObject){
        int id = itemObject.getMenu_item_id();
        String name = itemObject.getItem_name();
        //String path = Helper.PUBLIC_FOLDER + itemObject.getItem_picture();
        float price = itemObject.getItem_price();
        //add to local database
        //dbQuery.addFavoriteMenuItem(name, path, price);
        //dbQuery.addFavoriteMenuItem(name, "", price);
    }

    //nasir
    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.order_quantity);
        displayInteger.setText("" + number);
    }


    /*private void displayItem(int number) {

        MoneyTextView itemAmount = (MoneyTextView) findViewById(R.id.item_amount);
        itemAmount.setAmount(number);
    }*/


}
