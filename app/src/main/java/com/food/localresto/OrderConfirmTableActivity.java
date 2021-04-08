package com.food.localresto;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.adapter.PaidAdapter;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.CartObject;
import com.food.localresto.entities.HistoryObject;
import com.food.localresto.entities.OrderObject;
import com.food.localresto.entities.SongObject;
import com.food.localresto.util.CustomApplication;
import com.food.localresto.util.Helper;
import com.food.localresto.util.PrintReceipt;
import com.food.localresto.util.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.kolotnev.formattedittext.DecimalEditText;

import static com.food.localresto.MainActivity.mSeat;

public class OrderConfirmTableActivity extends AppCompatActivity implements View.OnKeyListener {

    private BluetoothAdapter bAdapter;
    private String namaToPrint, alamatToPrint, kotaToPrint, Bayar, pajak, disc, footer_struk;
    private String finalListToPrint;
    private CartObject[] cartList;
    //private CartObject[] orderCart;
    private List<CartObject> orderCart = new ArrayList<>();

    private List<CartObject> orderCart2 = new ArrayList<CartObject>();

    private RecyclerView recyclerView;

    //nb
    private LinearLayout.LayoutParams params;
    private List<CheckBox> optionCheckBox = new ArrayList<CheckBox>();

    //

    EditText angka_pertama, emailCust;
    //Button tambah, kurang, kali, bagi, bersihkan;
    Button bt_10rb, bt_50rb, bt_100rb, bersihkan;
    TextView hasil;

    //private Query mDatabase;
    private SqliteDatabase mDatabase;

    private TextView tableSeat, jmlNota, SubtotalNota, totalNota, totalPajak, totalDisc;
    private int Total=0;
    private double angka1, angka2, result, pp, dd;
    private DecimalEditText angka_kedua;

    private List<HistoryObject> orderHistory = new ArrayList<HistoryObject>();

    String myEmailString, passString, sendToEmailString, subjectString, textString;

    private String orderId, orderDate, Tgl, Jam;
    //private DecimalEditText

    public static final String SHARED_PREFS = "SHARED_PREFERENCE";
    public static final String PAJAK = "pajak";
    public static final String DISC = "disc";
    public static final String FOOTER = "footer";


    private LinearLayout wrapperSubTotal;
    private LinearLayout wrapperPajak;
    private LinearLayout wrapperDisc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tabel_confirm);

        //bAdapter = BluetoothAdapter.getDefaultAdapter();
        tableSeat = (TextView) findViewById(R.id.tableseat);
        jmlNota = (TextView) findViewById(R.id.jml_nota);
        totalNota = (TextView) findViewById(R.id.total_nota);
        SubtotalNota = (TextView) findViewById(R.id.subtotal_nota);
        totalPajak = (TextView) findViewById(R.id.total_pajak);
        totalDisc = (TextView) findViewById(R.id.total_disc);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//nb
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        tableSeat.setText(String.valueOf(mSeat));


        recyclerView = (RecyclerView)findViewById(R.id.checkout_table);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        bAdapter = BluetoothAdapter.getDefaultAdapter();


        loadData();

        wrapperSubTotal = (LinearLayout)findViewById(R.id.lay_subtotal);
        wrapperPajak = (LinearLayout)findViewById(R.id.lay_pajak);
        wrapperDisc = (LinearLayout)findViewById(R.id.lay_disc);

        if(Double.parseDouble(pajak) > 0) {
            wrapperPajak.setVisibility(View.VISIBLE);
        }else{
            wrapperPajak.setVisibility(View.GONE);
        }

        if(Double.parseDouble(disc) > 0) {
            wrapperDisc.setVisibility(View.VISIBLE);
        }else{
            wrapperDisc.setVisibility(View.GONE);
        }

        if(Double.parseDouble(pajak) > 0 || Double.parseDouble(disc) > 0) {
            wrapperSubTotal.setVisibility(View.VISIBLE);
        }else{
            wrapperSubTotal.setVisibility(View.GONE);
        }


        orderId = getIntent().getExtras().getString("KODENA");
        orderDate = getIntent().getExtras().getString("DATENA");
        namaToPrint = getIntent().getExtras().getString("NAMA");
        alamatToPrint = getIntent().getExtras().getString("ALAMAT");
        kotaToPrint = getIntent().getExtras().getString("KOTA");
        finalListToPrint = getIntent().getExtras().getString("ORDER_LIST");
        Tgl= Helper.dateFormatting2(getIntent().getExtras().getString("DATENA"));
        Jam= Helper.dateFormattingJam(getIntent().getExtras().getString("DATENA"));

        if (mSeat == 0) {
            getOrderTableID(orderId);

            Gson gson = ((CustomApplication) getApplication()).getGsonObject();
            cartList = gson.fromJson(finalListToPrint, CartObject[].class);

        } else {
            getOrderTable(mSeat);

            Gson gson = ((CustomApplication) getApplication()).getGsonObject();
            cartList = orderCart2.toArray(new CartObject[0]);//gson.fromJson(String.valueOf(orderCart), CartObject[].class);

        }



        //String orderId = getIntent().getExtras().getString("ORDER_ID");
        //String orderDate = getIntent().getExtras().getString("ORDER_DATE");
        //getOrderTableID(orderId);

       /* namaToPrint = getIntent().getExtras().getString("NAMA_RESTO");
        alamatToPrint = getIntent().getExtras().getString("ALAMAT_RESTO");
        kotaToPrint= getIntent().getExtras().getString("KOTA_RESTO");
        finalListToPrint = getIntent().getExtras().getString("ORDER_LIST");

        Gson gson = ((CustomApplication) getApplication()).getGsonObject();
        cartList = gson.fromJson(finalListToPrint, CartObject[].class);*/

        ActionBar actionBar = getSupportActionBar();
        if(null != actionBar){
            actionBar.hide();
        }


        //LinearLayout wrapLayout = (LinearLayout)findViewById(R.id.option_layout);
        //LinearLayout wrapLayout2 = (LinearLayout)findViewById(R.id.jml_belanja_layout);
        //final EditText mOrderNote = (EditText)findViewById(R.id.order_note);

        //final String mOptions = singleMenuItem.getItem_options();
        final String mOptions ="Panjang,Lebar";
        if(TextUtils.isEmpty(mOptions)){
            TextView noticeTextView = new TextView(OrderConfirmTableActivity.this);
            noticeTextView.setText(R.string.no_option_item);
            noticeTextView.setLayoutParams(params);

            //wrapLayout.removeAllViews();
            //wrapLayout.addView(noticeTextView);
        }else{
            String[] allOptions = mOptions.split(",");
            //optionCheckBox.clear();
            //wrapLayout.removeAllViews();
            for(int i = 0; i < allOptions.length; i++){
                DecimalEditText oneBox = createDynamicTextBox(i, allOptions[i]);
                //optionCheckBox.add(oneBox);
                //wrapLayout.addView(oneBox);

                /*TextView noticeTextView2 = new TextView(OrderComfirmTableActivity.this);
                noticeTextView2.setText("Test");
                noticeTextView2.setLayoutParams(params);
                wrapLayout.setGravity(Gravity.CENTER_VERTICAL | Gravity.TOP);
                wrapLayout.addView(noticeTextView2);*/

                TextView textView = new TextView(this);
                textView.setText("I am added");
                /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );*/
                //textView.setLayoutParams(params);
                //wrapLayout2.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
                //wrapLayout2.addView(textView);
            }
        }

        /*Button startTrackingButton = (Button)findViewById(R.id.start_tracking);
        startTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //isBluetoothHeadsetConnected();
                //final boolean isBluetoothKonek = isBluetoothHeadsetConnected();
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
                    Intent orderIntent = new Intent(OrderComfirmTableActivity.this, MainActivity.class);
                    startActivity(orderIntent);
                }else {
                    //if (isBluetoothKonek) {
                    mSeat = 0;

                    PrintReceipt.printBillFromOrder(OrderComfirmTableActivity.this, namaToPrint,alamatToPrint,kotaToPrint,cartList);
                    Intent orderIntent = new Intent(OrderComfirmTableActivity.this, MainActivity.class);
                    startActivity(orderIntent);


                }
                //}else{
                //    Toast.makeText(OrderComfirmationActivity.this, "Bluetooth False", Toast.LENGTH_LONG).show();
                //    Intent sampleIntent = new Intent(OrderComfirmationActivity.this, SettingActivity.class);
                //    startActivity(sampleIntent);
                //}

            }
        });*/


        //angka_kedua = (EditText) findViewById(R.id.angka_kedua);
        angka_kedua = findViewById(R.id.angka_kedua);
        //angka_kedua.setLocale(new Locale("in", "ID"));
        angka_kedua.setOnKeyListener(this);

        /*tambah = (Button)findViewById(R.id.tambah);
        kurang = (Button)findViewById(R.id.kurang);
        kali = (Button)findViewById(R.id.kali);
        bagi = (Button)findViewById(R.id.bagi);*/
        bersihkan = (Button) findViewById(R.id.bersihkan);
        bt_10rb = (Button)findViewById(R.id.bt_10rb);
        bt_50rb = (Button)findViewById(R.id.bt_50rb);
        bt_100rb = (Button)findViewById(R.id.bt_100rb);

        hasil = (TextView) findViewById(R.id.hasil);
        //emailCust = (EditText) findViewById(R.id.email_cust);

        /*tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((angka_pertama.getText().length()>0) && (angka_kedua.getText().length()>0))
                {
                    double angka1 = Double.parseDouble(angka_pertama.getText().toString());
                    double angka2 = Double.parseDouble(angka_kedua.getText().toString());
                    double result = angka1 + angka2;
                    hasil.setText(Double.toString(result));
                }
                else {
                    Toast toast = Toast.makeText(OrderComfirmTableActivity.this, "Mohon masukkan Angka pertama & Kedua", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });*/


        /*kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((angka_pertama.getText().length()>0) && (angka_kedua.getText().length()>0))
                {
                    double angka1 = Double.parseDouble(angka_pertama.getText().toString());
                    double angka2 = Double.parseDouble(angka_kedua.getText().toString());
                    double result = angka1 - angka2;
                    hasil.setText(Double.toString(result));
                }
                else {
                    Toast toast = Toast.makeText(OrderComfirmTableActivity.this, "Mohon masukkan Angka pertama & Kedua", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        kali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((angka_pertama.getText().length()>0) && (angka_kedua.getText().length()>0))
                {
                    double angka1 = Double.parseDouble(angka_pertama.getText().toString());
                    double angka2 = Double.parseDouble(angka_kedua.getText().toString());
                    double result = angka1 * angka2;
                    hasil.setText(Double.toString(result));
                }
                else {
                    Toast toast = Toast.makeText(OrderComfirmTableActivity.this, "Mohon masukkan Angka pertama & Kedua", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        bagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((angka_pertama.getText().length()>0) && (angka_kedua.getText().length()>0))
                {
                    double angka1 = Double.parseDouble(angka_pertama.getText().toString());
                    double angka2 = Double.parseDouble(angka_kedua.getText().toString());
                    double result = angka1 / angka2;
                    hasil.setText(Double.toString(result));
                }
                else {
                    Toast toast = Toast.makeText(OrderComfirmTableActivity.this, "Mohon masukkan Angka pertama & Kedua", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });*/

        Button startTrackingButton = (Button)findViewById(R.id.btn_print);
        //final SendEmailTask sendEmailTask = new SendEmailTask();
        startTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mDatabase.updateProduct(new Product(product.getId(), name, quantity,MenuItemLocalActivity.imageViewToByte(mImageView)));
                //mDatabase.UpdateHistoryForPaidbyTable(new HistoryObject(orderHistory.get(1).))

                if((totalNota.getText().length()>0) && (angka_kedua.getText().length()>0))
                {
                    for (int i = 0; i < orderHistory.size(); i++) {
                        //orderList.add(new OrderObject(orderHistory.get(i).getOrder_id(),orderHistory.get(i).getOrder_date(),orderHistory.get(i).getOrder_price(),orderHistory.get(i).getStatus()));
                        if (mSeat == 0) {
                            mDatabase.UpdateHistoryForPaidbyId(new HistoryObject(orderHistory.get(i).getOrder_id(),orderHistory.get(i).getOrder_date(),orderHistory.get(i).getOrder_quantity(),orderHistory.get(i).getOrder_price(),orderHistory.get(i).getStatus(),orderHistory.get(i).getPayment_method(),orderHistory.get(i).getTabel(),orderHistory.get(i).getPajak(),orderHistory.get(i).getDisc(),orderHistory.get(i).getJml(), (float) angka2));
                        }else{
                            mDatabase.UpdateHistoryForPaidbyTable(new HistoryObject(orderHistory.get(i).getOrder_id(),orderHistory.get(i).getOrder_date(),orderHistory.get(i).getOrder_quantity(),orderHistory.get(i).getOrder_price(),orderHistory.get(i).getStatus(),orderHistory.get(i).getPayment_method(),orderHistory.get(i).getTabel(),orderHistory.get(i).getPajak(),orderHistory.get(i).getDisc(),orderHistory.get(i).getJml(), (float) angka2));

                        }

                        subjectString = orderHistory.get(i).getOrder_id();
                    }

                    mSeat = 0;

                    //PrintReceipt.printBillFromOrder(OrderConfirmTableActivity.this, namaToPrint,alamatToPrint,kotaToPrint,cartList);
                    //Intent orderIntent = new Intent(OrderConfirmTableActivity.this, MainActivity.class);
                    //startActivity(orderIntent);

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
                        Intent orderIntent = new Intent(OrderConfirmTableActivity.this, MainActivity.class);
                        startActivity(orderIntent);
                    }else {
                        //if (isBluetoothKonek) {
                        mSeat = 0;

                        double angka1 = Double.parseDouble(String.valueOf(Total));
                        double d = 0;
                        d = angka_kedua.getValue().doubleValue();

                        //double result = d-angka1;


                        PrintReceipt.printBillFromOrder(OrderConfirmTableActivity.this, namaToPrint,alamatToPrint,kotaToPrint,orderId,Tgl,Jam,d,Double.parseDouble(pajak),Double.parseDouble(disc),footer_struk,cartList);
                        Intent orderIntent = new Intent(OrderConfirmTableActivity.this, MainActivity.class);
                        startActivity(orderIntent);

                    }


                }
                else {
                    Toast toast = Toast.makeText(OrderConfirmTableActivity.this, "Mohon masukan jumlah angka", Toast.LENGTH_LONG);
                    toast.show();
                }

                /*if(emailCust.getText().length()>0) {
                    myEmailString = "mangkubumi16@gmail.com";
                    passString = "n451r2020";
                    sendToEmailString = emailCust.getText().toString();
                    //subjectString = "test lunass";
                    //textString = "<i>Greetings!</i><br>";
                    //textString += "<b>Wish you a nice day!</b><br>";
                    //textString += "<font color=red>Duke</font>";
                    //textString = getString(R.string.about_meral);
                    textString = "Lunasss";
                    //sendEmailTask.execute();
                }*/
            }
        });

        bt_10rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if((angka_pertama.getText().length()>0) && (angka_kedua.getText().length()>0))
                if((totalNota.getText().length()>0) && (angka_kedua.getText().length()>0))
                //if (totalNota.getText().length()>0)
                {
                    angka1 = Double.parseDouble(String.valueOf(Total));

                    angka2 = angka_kedua.getValue().doubleValue();

                    angka2=angka2+10000;
                    result = angka2-angka1;
                    angka_kedua.setText(String.valueOf((int) angka2));
                    hasil.setText("Rp " + String.format("%1$,.0f", Float.valueOf((float) result) ));

                }
                else {
                    //Toast toast = Toast.makeText(OrderComfirmTableActivity.this, "Mohon masukkan Angka pertama & Kedua", Toast.LENGTH_LONG);
                    //toast.show();
                    angka_kedua.setText("10000");
                    angka1 = Double.parseDouble(String.valueOf(Total));
                    angka2 = 10000;
                    result = angka2-angka1;
                    hasil.setText("Rp " + String.format("%1$,.0f", Float.valueOf((float) result) ));
                }
            }
        });

        bt_50rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if((angka_pertama.getText().length()>0) && (angka_kedua.getText().length()>0))
                if((totalNota.getText().length()>0) && (angka_kedua.getText().length()>0))
                //if (totalNota.getText().length()>0)
                {
                    angka1 = Double.parseDouble(String.valueOf(Total));
                    //double angka2 = Double.parseDouble(angka_kedua.getText().toString());
                    //double angka2 = 0;
                    angka2 = angka_kedua.getValue().doubleValue();
                    angka2=angka2+50000;
                    result = angka2-angka1;
                    angka_kedua.setText(String.valueOf((int) angka2));
                    hasil.setText("Rp " + String.format("%1$,.0f", Float.valueOf((float) result) ));

                }
                else {
                    //Toast toast = Toast.makeText(OrderComfirmTableActivity.this, "Mohon masukkan Angka pertama & Kedua", Toast.LENGTH_LONG);
                    //toast.show();
                    angka_kedua.setText("50000");
                    angka1 = Double.parseDouble(String.valueOf(Total));
                    angka2 = 50000;
                    result = angka2-angka1;
                    hasil.setText("Rp " + String.format("%1$,.0f", Float.valueOf((float) result) ));
                }
            }
        });

        bt_100rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if((angka_pertama.getText().length()>0) && (angka_kedua.getText().length()>0))
                if((totalNota.getText().length()>0) && (angka_kedua.getText().length()>0))
                //if (totalNota.getText().length()>0)
                {
                    angka1 = Double.parseDouble(String.valueOf(Total));
                    //double angka2 = Double.parseDouble(angka_kedua.getText().toString());
                    //double angka2 = 0;
                    angka2 = angka_kedua.getValue().doubleValue();
                    angka2=angka2+100000;
                    result = angka2-angka1;
                    angka_kedua.setText(String.valueOf((int) angka2));
                    hasil.setText("Rp " + String.format("%1$,.0f", Float.valueOf((float) result) ));

                }
                else {
                    //Toast toast = Toast.makeText(OrderComfirmTableActivity.this, "Mohon masukkan Angka pertama & Kedua", Toast.LENGTH_LONG);
                    //toast.show();
                    angka_kedua.setText("100000");
                    angka1 = Double.parseDouble(String.valueOf(Total));
                    angka2 = 100000;
                    result = angka2-angka1;
                    hasil.setText("Rp " + String.format("%1$,.0f", Float.valueOf((float) result) ));
                }
            }
        });

        bersihkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //angka_pertama.setText("");
                angka_kedua.setText("");
                hasil.setText("0");
                angka_kedua.requestFocus();
            }
        });
    }


    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        pajak = sharedPreferences.getString(PAJAK, "");
        disc = sharedPreferences.getString(DISC, "");
        footer_struk = sharedPreferences.getString(FOOTER, "");
    }

   /* public static boolean isBluetoothHeadsetConnected() {

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()
                && mBluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) == BluetoothHeadset.STATE_CONNECTED;
    }*/

    @Override
    public void onBackPressed() {
        Intent confirmIntent = new Intent(OrderConfirmTableActivity.this, MainActivity.class);
        startActivity(confirmIntent);
    }

    //nb
    private CheckBox createDynamicCheckBox(int index, String textContent){
        CheckBox checkBox = new CheckBox(this);
        checkBox.setId(index);
        checkBox.setText(textContent);
        checkBox.setLayoutParams(params);
        return checkBox;
    }

    //nb
    private DecimalEditText createDynamicTextBox(int index, String textContent){
        DecimalEditText textBox = new DecimalEditText(this);
        textBox.setId(index);
        textBox.setText(textContent);
        textBox.setLayoutParams(params);
        return textBox;
    }

    private void getOrderTable(int tableId){
        Map<String, String> params = new HashMap<String,String>();
        //params.put(Helper.ID, tableId);

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
        orderHistory = mDatabase.listHistorybyForPaid(mSeat);
		/*PaidAdapter mAdapter = new PaidAdapter(this, orderHistory);
        recyclerView.setAdapter(mAdapter);*/

        List<OrderObject> orderList = new ArrayList<>();

        for (int i = 0; i < orderHistory.size(); i++) {
            orderList.add(new OrderObject(orderHistory.get(i).getOrder_id(),orderHistory.get(i).getOrder_date(),orderHistory.get(i).getOrder_price(),orderHistory.get(i).getStatus(),orderHistory.get(i).getTabel()));
            Total = (int) (Total+ orderHistory.get(i).getOrder_price());

            orderCart = mDatabase.listOrderHist2(orderHistory.get(i).getOrder_id());

            //public CartObject(int id, String kodeitem, String orderName, int quantity, float price, String extra, String note) {


            for (int j = 0; j < orderCart.size(); j++) {
                orderCart2.add(new CartObject(orderCart.get(j).getId(),orderCart.get(j).getKodeitem(),orderCart.get(j).getOrderName(),orderCart.get(j).getQuantity(),orderCart.get(j).getPrice(),orderCart.get(j).getExtra(),orderCart.get(j).getNote()));
            }

        }

        Collections.reverse(orderList);

        jmlNota.setText("Order No("+String.valueOf(orderList.size())+") :");
        //totalNota.setText("Rp " + String.format("%1$,.0f", Float.valueOf(Total) ));

        SubtotalNota.setText(String.format("%1$,.0f", Float.valueOf(Total) ));

        if(Double.parseDouble(pajak) > 0) {
            pp = Total * (Double.parseDouble(pajak) / 100)  ;
        }


        if(Double.parseDouble(disc) > 0) {
            //dd = -1*(Total * (Integer.parseInt(disc) / 100));
            //totalVat=Double.parseDouble(Utility.doubleFormatter(totalBill*(Pajak/100)));
            //Double Tot = Total;
            dd= -1*(Total*(Double.parseDouble(disc) / 100));
        }
         Double Tot = Double.parseDouble(String.valueOf(Total));
        Tot=Tot+pp+dd;

        Total = Tot.intValue();

        totalPajak.setText(String.format("%1$,.0f", pp ));
        totalDisc.setText(String.format("%1$,.0f", dd));
        totalNota.setText(String.format("%1$,.0f", Float.valueOf(Total) ));


        PaidAdapter mAdapter = new PaidAdapter(this, orderList);
        recyclerView.setAdapter(mAdapter);

    }

    private void getOrderTableID(String OrderId){
        Map<String, String> params = new HashMap<String,String>();
        //params.put(Helper.ID, tableId);

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
        //orderHistory = mDatabase.listHistorybyForPaid(mSeat);

        /*List<OrderObject> orderList = new ArrayList<>();

        for (int i = 0; i < orderHistory.size(); i++) {
            orderList.add(new OrderObject(orderHistory.get(i).getOrder_id(),orderHistory.get(i).getOrder_date(),orderHistory.get(i).getOrder_price(),orderHistory.get(i).getStatus(),orderHistory.get(i).getTabel()));
            Total = (int) (Total+ orderHistory.get(i).getOrder_price());
        }*/

        orderHistory = mDatabase.listHistorybyIDForPaid(OrderId);
        List<OrderObject> orderList = new ArrayList<>();

        for (int i = 0; i < orderHistory.size(); i++) {
            orderList.add(new OrderObject(orderHistory.get(i).getOrder_id(),orderHistory.get(i).getOrder_date(),orderHistory.get(i).getOrder_price(),orderHistory.get(i).getStatus(),orderHistory.get(i).getTabel()));

            if (orderHistory.get(i).getTabel()==0){
                Total = (int) (Total+ orderHistory.get(i).getOrder_price());
            }else{
                Total = (int) (Total+ (orderHistory.get(i).getOrder_price() -((orderHistory.get(i).getTabel()*0.01)*orderHistory.get(i).getOrder_price())));
            }
        }

        Collections.reverse(orderList);

        jmlNota.setText("Order No("+String.valueOf(orderList.size())+") :");
        //totalNota.setText("Rp " + String.format("%1$,.0f", Float.valueOf(Total) ));

        SubtotalNota.setText(String.format("%1$,.0f", Float.valueOf(Total) ));


        if(Double.parseDouble(pajak) > 0) {
            pp = Total * (Double.parseDouble(pajak) / 100)  ;
        }


        if(Double.parseDouble(disc) > 0) {
            //dd = -1*(Total * (Integer.parseInt(disc) / 100));
            //totalVat=Double.parseDouble(Utility.doubleFormatter(totalBill*(Pajak/100)));
            //Double Tot = Total;
            dd= -1*(Total*(Double.parseDouble(disc) / 100));
        }
        Double Tot = Double.parseDouble(String.valueOf(Total));
        Tot=Tot+pp+dd;

        Total = Tot.intValue();

        totalPajak.setText(String.format("%1$,.0f", pp ));
        totalDisc.setText(String.format("%1$,.0f", dd));
        totalNota.setText(String.format("%1$,.0f", Float.valueOf(Total) ));


        PaidAdapter mAdapter = new PaidAdapter(this, orderList);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        //return false;
        //TextView responseText = (TextView) findViewById(R.id.responseText); hasil
        //EditText myEditText = (EditText) view; angka_kedua
        DecimalEditText myEditText = (DecimalEditText) view;
        if ((event.getAction() == KeyEvent.ACTION_DOWN)
                && (keyCode == KeyEvent.KEYCODE_ENTER)) {

            if (!event.isShiftPressed()) {
                Log.v("AndroidEnterKeyActivity","Enter Key Pressed!");
                switch (view.getId()) {
                    case R.id.angka_kedua:
                        /*hasil
                                .setText("Just pressed the ENTER key, " +
                                        "focus was on Text Box1. " +
                                        "You typed:\n" + myEditText.getText());*/
                        double angka1 = Double.parseDouble(String.valueOf(Total));
                        double d = 0;
                        d = angka_kedua.getValue().doubleValue();
                        //double angka2 = Double.parseDouble(angka_kedua.getText().toString());


                        double result = d-angka1;
                        //hasil.setText(Double.toString(result));
                        hasil.setText("Rp " + String.format("%1$,.0f", Float.valueOf((float) result) ));
                        break;
                }
                return true;
            }

        }
        return false; // pass on to other listeners.

    }

    /*class SendEmailTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("Email sending", "sending start");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                GmailSender sender = new GmailSender(myEmailString, passString);
                //subject, body, sender, to
                sender.sendMail(subjectString,
                        textString,
                        myEmailString,
                        sendToEmailString);

                Log.i("Email sending", "send");
            } catch (Exception e) {
                Log.i("Email sending", "cannot send");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }*/
}
