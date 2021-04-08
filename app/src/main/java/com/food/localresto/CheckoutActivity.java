package com.food.localresto;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.adapter.CheckoutAdapter;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.CartObject;
import com.food.localresto.entities.LoginObject;
import com.food.localresto.entities.PaymentResponseObject;
import com.food.localresto.util.CustomApplication;
import com.food.localresto.util.CustomSharedPreference;
import com.food.localresto.util.DrawCart;
import com.food.localresto.util.Helper;
import com.food.localresto.util.SimpleDividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.food.localresto.MainActivity.mSeat;
import static com.food.localresto.database.SqliteDatabase.getDateTime;

public class CheckoutActivity extends AppCompatActivity {

    private static final String TAG = CheckoutActivity.class.getSimpleName();

    private TextView orderItemCount, orderTotalAmount, orderVat, orderDisc, orderFullAmount, restaurantName,
            restaurantAddress, deliveryAddress, tableSeat;

    private CustomSharedPreference shared;

    private RadioGroup radioGroup;
    private String paymentMethod = "";
    private String item = "";
    private String buyerDeliveryAddress;

    private LoginObject user;
    private List<CartObject> checkoutOrder;
    private String finalList;

    private double subTotal;

    private static PayPalConfiguration config;
    private static final int REQUEST_PAYMENT_CODE = 99;

    private LinearLayout payId;
    private LinearLayout tableId;

    //private Query mDatabase;
    private SqliteDatabase mDatabase;
    private Button placeOrderButton;
    private String kodeOrder;

    public static final String SHARED_PREFS = "SHARED_PREFERENCE";
    public static final String PAJAK = "pajak";
    public static final String DISC = "disc";

    private String pajak, disc;
    private double pp, dd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        setTitle(getString(R.string.my_checkout));

        config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK).clientId(Helper.CLIENT_ID);

        finalList = getIntent().getExtras().getString("FINAL_ORDER");
        Log.d(TAG, "JSON FORMAT" + finalList);
        Gson gson = ((CustomApplication) getApplication()).getGsonObject();
        DrawCart cart = new DrawCart(this);
        checkoutOrder = cart.convertObjectArrayToListObject(gson.fromJson(finalList, CartObject[].class));

        shared = ((CustomApplication) getApplication()).getShared();
        //user = gson.fromJson(shared.getUserData(), LoginObject.class);

        //String storedUserNya = shared.getUserData();

        try {
            JSONArray kontak = new JSONArray(shared.getUserData());

            for (int i = 0; i < kontak.length(); i++){
                JSONObject c = kontak.getJSONObject(i);
                int idna = c.getInt("id");
                String usernm = c.getString("username");
                String role = c.getString("role");
                String email = c.getString("email");
                String address = c.getString("address");
                String phone = c.getString("phone");
                String loggedIn = c.getString("loggedIn");

                user = new LoginObject(idna,usernm,"",role,email,address,phone,loggedIn);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //List<LoginObject> user = new ArrayList<>();
        //user.add(new LoginObject(1,"x","y","z","h","1"));


        deliveryAddress = (TextView) findViewById(R.id.delivery_address);
        showDeliveryAddress();

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        selectPaymentMethod();

        restaurantName = (TextView) findViewById(R.id.restaurant_name);
        restaurantAddress = (TextView) findViewById(R.id.restaurant_address);
        restaurantName.setText(restaurantDetails(0));
        restaurantAddress.setText(restaurantDetails(1));

        orderItemCount = (TextView) findViewById(R.id.order_item_count);
        orderTotalAmount = (TextView) findViewById(R.id.order_total_amount);
        orderVat = (TextView) findViewById(R.id.order_vat);
        orderDisc = (TextView) findViewById(R.id.order_disc);
        orderFullAmount = (TextView) findViewById(R.id.order_full_amounts);

        //nb
        tableSeat = (TextView) findViewById(R.id.tableseat);
        tableSeat.setText(String.valueOf(mSeat));

        loadData();

        Gson mGson = ((CustomApplication)getApplication()).getGsonObject();
        String storedUser = ((CustomApplication)getApplication()).getShared().getUserData();
        //LoginObject userObject = mGson.fromJson(storedUser, LoginObject.class);

        //String kode = userObject.getKodeuser().substring(0,4);
        String kode = restaurantDetails(5);

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        //String currentDateandTime = String.format("%05d", menuitem.getMenu_id())+ sdf.format(new Date());

        kodeOrder = kode + sdf.format(new Date());//String.valueOf(menuidna) ;

        //Check History Table Ordered

        //mDatabase = new Query(this);
        mDatabase = SqliteDatabase.getInstance(this);
        if (mSeat == 0) {
            //Toast.makeText(CheckoutActivity.this,"Seat "+mSeat, Toast.LENGTH_LONG).show();
        }else {
            //mDatabase.CheckHistorybyTableOrder(mSeat);

            String result = mDatabase.CheckHistorybyTableOrder(mSeat);
            if (result.equals("0")) {
                Helper.displayErrorMessage(CheckoutActivity.this, "Failed to check ordered table");
            } else {
                kodeOrder = result;
            }
        }

        //Toast.makeText(CheckoutActivity.this,"Seat "+mSeat,Toast.LENGTH_LONG).show();

        orderItemCount.setText(String.valueOf(checkoutOrder.size()));
        DrawCart drawCart = new DrawCart(this);
        subTotal = drawCart.getSubtotalAmount(checkoutOrder);

        //nasir
        payId = (LinearLayout) findViewById(R.id.pay_id);
        payId.setVisibility(View.GONE);

        //orderTotalAmount.setText("$" + String.valueOf(subTotal) + "0");
        //orderTotalAmount.setText("Rp " + String.format("%1$,.0f", Float.valueOf((float) subTotal)));
        orderTotalAmount.setText(String.format("%1$,.0f", Float.valueOf((float) subTotal)));


        if(Double.parseDouble(pajak) > 0) {
            pp = subTotal * (Double.parseDouble(pajak) / 100)  ;
            //orderVat.setText("Rp " + String.format("%1$,.0f", Float.valueOf((float) pp)));
            orderVat.setText(String.format("%1$,.0f", Float.valueOf((float) pp)));

        }else{
            orderVat.setText("0");
        }


        if(Double.parseDouble(disc) > 0) {
            //dd = -1*(Total * (Integer.parseInt(disc) / 100));
            //totalVat=Double.parseDouble(Utility.doubleFormatter(totalBill*(Pajak/100)));
            //Double Tot = Total;
            dd= -1*(subTotal*(Double.parseDouble(disc) / 100));
            //orderDisc.setText("Rp " + String.format("%1$,.0f", Float.valueOf((float) dd)));
            orderDisc.setText(String.format("%1$,.0f", Float.valueOf((float) dd)));
        }else{
            orderDisc.setText("0");
        }

        Double Tot = Double.parseDouble(String.valueOf(subTotal));
        Tot=Tot+pp+dd;

        //orderVat.setText("0.00");

        //orderFullAmount.setText("$" + String.valueOf(subTotal) + "0");
        orderFullAmount.setText("Rp " + String.format("%1$,.0f", Tot));


        RecyclerView checkoutRecyclerView = (RecyclerView) findViewById(R.id.checkout_item);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        checkoutRecyclerView.setLayoutManager(linearLayoutManager);

        //checkoutRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        checkoutRecyclerView.setHasFixedSize(true);

        CheckoutAdapter mAdapter = new CheckoutAdapter(CheckoutActivity.this, checkoutOrder);
        checkoutRecyclerView.setAdapter(mAdapter);


        /*TextView addNewAddress = (TextView) findViewById(R.id.add_new_address);
        addNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newAddressIntent = new Intent(CheckoutActivity.this, NewAddressActivity.class);
                startActivity(newAddressIntent);
            }
        });

        TextView addNewCardPayment = (TextView) findViewById(R.id.add_payment_method);
        addNewCardPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newPaymentIntent = new Intent(CheckoutActivity.this, NewPaymentActivity.class);
                startActivity(newPaymentIntent);
            }
        });*/


        placeOrderButton = (Button) findViewById(R.id.place_an_order);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                if (radioGroup.getCheckedRadioButtonId() < 0) {
                    Helper.displayErrorMessage(CheckoutActivity.this, "Payment option must be selected before checkout");
                    return;
                }
/*
                if (TextUtils.isEmpty(buyerDeliveryAddress)) {
                    Helper.displayErrorMessage(CheckoutActivity.this, "You must provide a delivery address before checkout");
                    return;
                }
*/

                if (paymentMethod.equals("PAY PAL")) {
                    initializePayPalPayment();
                } else if (paymentMethod.equals("CREDIT CARD")) {

                } else {

                    if(Double.parseDouble(pajak) > 0) {
                        pp = subTotal * (Double.parseDouble(pajak) / 100)  ;
                    }


                    if(Double.parseDouble(disc) > 0) {
                        //dd = -1*(Total * (Integer.parseInt(disc) / 100));
                        //totalVat=Double.parseDouble(Utility.doubleFormatter(totalBill*(Pajak/100)));
                        //Double Tot = Total;
                        dd= -1*(subTotal*(Double.parseDouble(disc) / 100));
                    }
                    Double Tot = Double.parseDouble(String.valueOf(subTotal));
                    Tot=Tot+pp+dd;

                    //subTotal = Tot.intValue();

                    postCheckoutOrderToRemoteServer(kodeOrder,String.valueOf(user.getId()), String.valueOf(checkoutOrder.size()), String.valueOf(subTotal), getDateTime(),paymentMethod, String.valueOf(mSeat), pajak,disc,String.valueOf(Tot), finalList);
                    //postCheckoutOrderToRemoteServer(String.valueOf(user.getId()), String.valueOf(checkoutOrder.size()));
                }
            }
        });

        //nb
        tableId = (LinearLayout)findViewById(R.id.tableid);

        if (mSeat == 0) {
            tableId.setVisibility(View.GONE);
            placeOrderButton.setText("TAKE AWAY");
        } else {
            tableId.setVisibility(View.VISIBLE);
            placeOrderButton.setText("PLACE AN ORDER NOW");
        }

        // Spinner element
        //Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        //spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Table 1");
        categories.add("Table 2");
        categories.add("Table 3");
        categories.add("Table 4");
        categories.add("Table 5");
        categories.add("Table 6");
        categories.add("Table 7");
        categories.add("Table 8");
        categories.add("Table 9");
        categories.add("Table 10");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        //spinner.setAdapter(dataAdapter);

        // mengeset listener untuk mengetahui saat item dipilih
        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // memunculkan toast + value Spinner yang dipilih (diambil dari adapter)
                //Toast.makeText(TWOHLayoutSpinner.this, "Selected "+ adapter.getItem(i), Toast.LENGTH_SHORT).show();

                // On selecting a spinner item
                item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        pajak = sharedPreferences.getString(PAJAK, "");
        disc = sharedPreferences.getString(DISC, "");
        //footer_struk = sharedPreferences.getString(FOOTER, "");
    }

    private String restaurantDetails(int index) {
        String restaurant = ((CustomApplication) getApplication()).getShared().getRestaurantInformation();
        String[] restaurantList = restaurant.split(":");
        if (restaurantList.length > 0) {
            return restaurantList[index];
        }
        return "";
    }

    private void showDeliveryAddress() {
        if (!TextUtils.isEmpty(shared.getSavedDeliveryAddress())) {
            buyerDeliveryAddress = shared.getSavedDeliveryAddress();
            deliveryAddress.setText(shared.getSavedDeliveryAddress());
        } else {
            buyerDeliveryAddress = user.getAddress();
            deliveryAddress.setText(user.getAddress());
        }
    }

    private void selectPaymentMethod() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.pay_pal_payment:
                        paymentMethod = "PAY PAL";
                        break;
                    case R.id.credit_card_payment:
                        paymentMethod = "CREDIT CARD";
                        break;
                    case R.id.cash_on_delivery:
                        paymentMethod = "CASH ON DELIVERY";
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDeliveryAddress();

        //nb
        invalidateOptionsMenu();
        tableSeat.setText(String.valueOf(mSeat));
        if (mSeat == 0) {
            tableId.setVisibility(View.GONE);
            placeOrderButton.setText("TAKE AWAY");
        } else {
            tableId.setVisibility(View.VISIBLE);
            placeOrderButton.setText("PLACE AN ORDER NOW");
        }
    }


    private void postCheckoutOrderToRemoteServer(String kodeOrder, String userId, String quantity, String price, String datenya, String payment_method, String table, String pajak, String disc, String nettot, String order_list) {
        //private void postCheckoutOrderToRemoteServer(String userId, String quantity){
        Map<String, String> params = new HashMap<String, String>();
        params.put("ORDER_ID", kodeOrder);
        params.put("USER_ID", userId);
        params.put("QUANTITY", quantity);
        params.put("PRICE", price);
        params.put("DATE", datenya);
        params.put("PAYMENT", payment_method);
        params.put("TABLE", table);
        params.put("PAJAK", pajak);
        params.put("DISC", disc);
        params.put("NETTOT", nettot);
        params.put("ORDER_LIST", order_list);

        /*GsonRequest<SuccessObject> serverRequest = new GsonRequest<SuccessObject>(
                Request.Method.POST,
                Helper.PATH_TO_PLACE_ORDER,
                SuccessObject.class,
                params,
                createRequestSuccessListener(),
                createRequestErrorListener());

        serverRequest.setRetryPolicy(new DefaultRetryPolicy(
                Helper.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(CheckoutActivity.this).addToRequestQueue(serverRequest);*/

        //MenuItemObject newProduct = new MenuItemObject(menuidna, jenisna,menu, name, kategory, imageViewToByte(imageViewIcon), pricefloat);
        //mDatabase.addOrderItem(params);

        long result = mDatabase.addOrderItem(params);
        if (result == -1) {
            Helper.displayErrorMessage(CheckoutActivity.this, "Failed to upload order to server");
        } else {
            //delete paid other
            ((CustomApplication) getApplication()).getShared().updateCartItems("");
            //confirmation page.
            //Intent orderIntent = new Intent(CheckoutActivity.this, OrderComfirmationActivity.class);
            //startActivity(orderIntent);

            //Intent foodIntent = new Intent(CheckoutActivity.this, OrderComfirmationActivity.class);
            //foodIntent.putParcelable("ORDER_LIST", (Parcelable) params);
            //startActivity(foodIntent);

            //Intent intent = getIntent();
            //intent = new Intent();
            //Bundle bundle = new Bundle();
            //bundle.putParcelable("ORDER_LIST", (Parcelable) params);
            //bundle.putBoolean(Constants.PERSON_INTENT_EDIT, isEdit);
            //bundle.putInt(Constants.PERSON_INTENT_INDEX, index);
            //intent.putExtras(bundle);


            final Bundle extras = new Bundle();
            //extras.putInt("MENU_ITEM_ID", item.getMenu_item_id());
            //extras.putString("MENU_ITEM_NAME", item.getItem_name());
            //extras.putString("MENU_ITEM_DESC", item.getDescription());
            //extras.putFloat("MENU_ITEM_PRICE",item.getItem_price());

            extras.putString("NAMA_RESTO", restaurantDetails(0));
            extras.putString("ALAMAT_RESTO", restaurantDetails(1));
            extras.putString("KOTA_RESTO", restaurantDetails(2));
            extras.putString("KODE_ORDER", kodeOrder);
            extras.putString("DATENA", datenya);
            //extras.putString("NAMA_RESTO", "Adam Resto");
            //extras.putString("ALAMAT_RESTO", "JL Siliwangi 44 Kuningan");
            //extras.putString("KOTA_RESTO", "R001");
            extras.putString("ORDER_LIST", order_list);

            Intent foodIntent = new Intent(CheckoutActivity.this, OrderConfirmationActivity.class);
            foodIntent.putExtras(extras);
            startActivity(foodIntent);

        }
    }

    /*private Response.Listener<SuccessObject> createRequestSuccessListener() {
        return new Response.Listener<SuccessObject>() {
            @Override
            public void onResponse(SuccessObject response) {
                //Log.d(TAG,"JSON response " + response.toString());
                try {
                    Log.d(TAG, "Json Response " + response.getSuccess());
                    if(response.getSuccess() == 1){
                        //delete paid other
                        ((CustomApplication)getApplication()).getShared().updateCartItems("");
                        //confirmation page.
                        Intent orderIntent = new Intent(CheckoutActivity.this, OrderComfirmationActivity.class);
                        startActivity(orderIntent);
                    }else{
                        Helper.displayErrorMessage(CheckoutActivity.this, "Failed to upload order to server");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private Response.ErrorListener createRequestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
    }*/

    private void initializePayPalPayment() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(subTotal)), "USD", "Food Order", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, REQUEST_PAYMENT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_PAYMENT_CODE) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    String jsonPaymentResponse = confirm.toJSONObject().toString(4);
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    PaymentResponseObject responseObject = gson.fromJson(jsonPaymentResponse, PaymentResponseObject.class);
                    if (responseObject != null) {
                        String paymentId = responseObject.getResponse().getId();
                        String paymentState = responseObject.getResponse().getState();
                        Log.d(TAG, "Log payment id and state " + paymentId + " " + paymentState);



                        if(Double.parseDouble(pajak) > 0) {
                            pp = subTotal * (Double.parseDouble(pajak) / 100)  ;
                        }


                        if(Double.parseDouble(disc) > 0) {
                            //dd = -1*(Total * (Integer.parseInt(disc) / 100));
                            //totalVat=Double.parseDouble(Utility.doubleFormatter(totalBill*(Pajak/100)));
                            //Double Tot = Total;
                            dd= -1*(subTotal*(Double.parseDouble(disc) / 100));
                        }
                        Double Tot = Double.parseDouble(String.valueOf(subTotal));
                        Tot=Tot+pp+dd;

                        //subTotal = Tot.intValue();
                        //send order to server
                        postCheckoutOrderToRemoteServer(kodeOrder, String.valueOf(user.getId()), String.valueOf(checkoutOrder.size()), String.valueOf(subTotal), getDateTime(),paymentMethod, String.valueOf(mSeat), pajak,disc,String.valueOf(Tot),finalList);
                        //postCheckoutOrderToRemoteServer(String.valueOf(user.getId()), String.valueOf(checkoutOrder.size()));
                    }
                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

    //nb
    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.tableresto) {
            Intent checkoutIntent = new Intent(CheckoutActivity.this, TableActivity.class);
            startActivity(checkoutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.tableresto);
        DrawCart dCart = new DrawCart(this);
        menuItem.setIcon(dCart.buildCounterDrawable2(mSeat, R.drawable.dining));

        //menuItem.setVisible(false);

        menuItem = menu.findItem(R.id.action_shop);
        //menuItem.setIcon(dCart.buildCounterDrawable(mCount, R.drawable.cart));

        menuItem.setVisible(false);

        return true;
    }*/

}