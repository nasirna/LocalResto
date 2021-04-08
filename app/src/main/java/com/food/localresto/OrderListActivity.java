package com.food.localresto;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.adapter.CheckoutAdapter;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.CartObject;
import com.food.localresto.util.CustomApplication;
import com.food.localresto.util.DrawCart;
import com.food.localresto.util.Helper;
import com.food.localresto.util.PrintReceipt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.view.View.GONE;
import static com.food.localresto.MainActivity.mSeat;

public class OrderListActivity extends AppCompatActivity {

    private static final String TAG = OrderListActivity.class.getSimpleName();

    private TextView tableSeat, orderItemCount, orderTotalAmount, orderVat, orderDisc, orderFullAmount, restaurantName, restaurantAddress, orderNumber;
    private String namaToPrint;
    private String alamatToPrint;
    private String kotaToPrint;
    private String Bayar;
    private String pajak;
    private String disc;
    private String footer_struk;
    private RecyclerView orderRecyclerView;
    private CartObject[] cartList;
    //private Query mDatabase;

    private SqliteDatabase mDatabase;
    private String orderId, orderDate, Tgl, Jam, Ppn, Disc;
    private double pp, dd;

    private LinearLayout tableId;
    private Button print_list, rePrint;

    private BluetoothAdapter bAdapter;

    public static final String SHARED_PREFS = "SHARED_PREFERENCE";
    public static final String PAJAK = "pajak";
    public static final String DISC = "disc";
    public static final String FOOTER = "footer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        setTitle(getString(R.string.past_orders));

        loadData();

        //int orderId = getIntent().getExtras().getInt("ORDER_ID");
        orderId = getIntent().getExtras().getString("ORDER_ID");
        Ppn = getIntent().getExtras().getString("PAJAK");
        Disc = getIntent().getExtras().getString("DISC");
        Bayar = getIntent().getExtras().getString("BAYAR");
        Tgl= Helper.dateFormatting2(getIntent().getExtras().getString("DATENA"));
        Jam= Helper.dateFormattingJam(getIntent().getExtras().getString("DATENA"));


        restaurantName = (TextView)findViewById(R.id.restaurant_name);
        restaurantAddress = (TextView)findViewById(R.id.restaurant_address);
        restaurantName.setText(restaurantDetails(0));
        restaurantAddress.setText(restaurantDetails(1));

        namaToPrint =  restaurantDetails(0);
        alamatToPrint = restaurantDetails(1);
        kotaToPrint = restaurantDetails(2);

        //pajak = Ppn;
        //disc=Disc;
        //footer_struk = ;

        //restaurantName.setText("Bukan Urusan");
        //restaurantAddress.setText("Saya");
        tableId = (LinearLayout)findViewById(R.id.tableid);
        tableSeat = (TextView) findViewById(R.id.tableseat);
        //print_list = (Button) findViewById(R.id.btn_printList);
        rePrint = (Button) findViewById(R.id.btn_reprint);

        bAdapter = BluetoothAdapter.getDefaultAdapter();

        tableId.setVisibility(GONE);

        if (mSeat == 0) {
            tableId.setVisibility(View.GONE);
            //placeOrderButton.setText("TAKE AWAY");
        } else {
            tableId.setVisibility(View.VISIBLE);
            tableSeat.setText(String.valueOf(mSeat));
            //placeOrderButton.setText("PLACE AN ORDER NOW");
        }

        orderItemCount = (TextView)findViewById(R.id.order_item_count);
        orderTotalAmount =(TextView)findViewById(R.id.order_total_amount);
        orderVat = (TextView)findViewById(R.id.order_vat);
        orderDisc = (TextView)findViewById(R.id.order_disc);
        orderFullAmount = (TextView)findViewById(R.id.order_full_amounts);

        orderNumber = (TextView)findViewById(R.id.order_number);
        orderNumber.setText("Order number #"+ String.valueOf(orderId));


        orderRecyclerView = (RecyclerView)findViewById(R.id.checkout_item);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        orderRecyclerView.setLayoutManager(linearLayoutManager);
        orderRecyclerView.setHasFixedSize(true);


        // make network call
        getOrderHistoryByIdFromRemoteServer(orderId);

        //nb

        /*if (mSeat == 0) {
            tableId.setVisibility(View.GONE);
            //placeOrderButton.setText("TAKE AWAY");
            //print_list.setVisibility(View.GONE);
        } else {
            tableId.setVisibility(View.VISIBLE);
            //placeOrderButton.setText("PLACE AN ORDER NOW");
            //print_list.setVisibility(View.VISIBLE);
        }*/


        //placeOrderButton = (Button) findViewById(R.id.place_an_order);
        rePrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(OrderListActivity.this,"Reprint "+orderId, Toast.LENGTH_LONG).show();


                Set<BluetoothDevice> pairedDevices = bAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    // There are paired devices. Get the name and address of each paired device.
                    for (BluetoothDevice device : pairedDevices) {
                        String deviceName = device.getName();
                        String deviceHardwareAddress = device.getAddress(); // MAC address
                    }
                }

                if(pairedDevices.size() == 0)
                {
                    mSeat = 0;

                    Toast.makeText(getApplicationContext(),"Bluetooth Not paired",Toast.LENGTH_SHORT).show();
                    Intent orderIntent = new Intent(OrderListActivity.this, MainActivity.class);
                    startActivity(orderIntent);
                }else {
                    //if (isBluetoothKonek) {
                    mSeat = 0;

                    //double angka1 = Double.parseDouble(String.valueOf(Total));
                    double d = Double.parseDouble(Bayar);
                    //d = angka_kedua.getValue().doubleValue();


                    PrintReceipt.printBillFromOrder(OrderListActivity.this, namaToPrint,alamatToPrint,kotaToPrint,orderId,Tgl,Jam,d,Double.parseDouble(pajak),Double.parseDouble(disc),footer_struk,cartList);
                    Intent orderIntent = new Intent(OrderListActivity.this, MainActivity.class);
                    startActivity(orderIntent);

                }
            }
        });

    }

    private void getOrderHistoryByIdFromRemoteServer(String userId){
        Map<String, String> params = new HashMap<String,String>();
        params.put(Helper.ID, userId);

        /*GsonRequest<CartObject[]> serverRequest = new GsonRequest<CartObject[]>(
                Request.Method.POST,
                Helper.PATH_TO_ORDER_HISTORY,
                CartObject[].class,
                params,
                createRequestSuccessListener(),
                createRequestErrorListener());

        serverRequest.setRetryPolicy(new DefaultRetryPolicy(
                Helper.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(this).addToRequestQueue(serverRequest);*/

        //List<CartObject> orderHistory = new ArrayList<CartObject>();
        //Collections.addAll(orderHistory, response);

        //mDatabase = new Query(this);
        mDatabase = SqliteDatabase.getInstance(this);

        List<CartObject> orderHistory = mDatabase.listOrderHist1(userId);

        calculateOrder(orderHistory);
        CheckoutAdapter mAdapter = new CheckoutAdapter(OrderListActivity.this, orderHistory);
        orderRecyclerView.setAdapter(mAdapter);

        cartList = orderHistory.toArray(new CartObject[0]);

    }

    /*private Response.Listener<CartObject[]> createRequestSuccessListener() {
        return new Response.Listener<CartObject[]>() {
            @Override
            public void onResponse(CartObject[] response) {
                try {
                    Log.d(TAG, "Json Response " + response.length);
                    if(response.length > 0){
                        List<CartObject> orderHistory = new ArrayList<CartObject>();
                        Collections.addAll(orderHistory, response);
                        calculateOrder(orderHistory);
                        CheckoutAdapter mAdapter = new CheckoutAdapter(OrderListActivity.this, orderHistory);
                        orderRecyclerView.setAdapter(mAdapter);
                    }else{
                        Toast.makeText(OrderListActivity.this, R.string.failed_login, Toast.LENGTH_LONG).show();
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

    private void calculateOrder(List<CartObject> allOrder){
        orderItemCount.setText(String.valueOf(allOrder.size()));

        DrawCart drawCart = new DrawCart(this);
        double subTotal = drawCart.getSubtotalAmount(allOrder);

        /*orderTotalAmount.setText("$" + String.valueOf(subTotal));
        orderVat.setText("$0.00");
        orderFullAmount.setText("$" + String.valueOf(subTotal));*/
        //orderTotalAmount.setText("Rp " + String.format("%1$,.0f", Float.valueOf((float) subTotal)));
        orderTotalAmount.setText(String.format("%1$,.0f", Float.valueOf((float) subTotal)));

        if(Double.parseDouble(Ppn) > 0) {
            pp = subTotal * (Double.parseDouble(Ppn) / 100)  ;
            //orderVat.setText("Rp " + String.format("%1$,.0f", Float.valueOf((float) pp)));
            orderVat.setText(String.format("%1$,.0f", Float.valueOf((float) pp)));

        }else{
            orderVat.setText("0");
        }


        if(Double.parseDouble(Disc) > 0) {
            //dd = -1*(Total * (Integer.parseInt(disc) / 100));
            //totalVat=Double.parseDouble(Utility.doubleFormatter(totalBill*(Pajak/100)));
            //Double Tot = Total;
            dd= -1*(subTotal*(Double.parseDouble(Disc) / 100));
            //orderDisc.setText("Rp " + String.format("%1$,.0f", Float.valueOf((float) dd)));
            orderDisc.setText(String.format("%1$,.0f", Float.valueOf((float) dd)));
        }else{
            orderDisc.setText("0");
        }

        Double Tot = Double.parseDouble(String.valueOf(subTotal));
        Tot=Tot+pp+dd;

        orderFullAmount.setText("Rp " + String.format("%1$,.0f", Tot));
    }

    private String restaurantDetails(int index){
        String restaurant = ((CustomApplication)getApplication()).getShared().getRestaurantInformation();
        String[] restaurantList = restaurant.split(":");
        if(restaurantList.length > 0){
            return restaurantList[index];
        }
        return "";
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        pajak = sharedPreferences.getString(PAJAK, "");
        disc = sharedPreferences.getString(DISC, "");
        footer_struk = sharedPreferences.getString(FOOTER, "");
    }

}