package com.food.localresto;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.food.localresto.entities.CartObject;
import com.food.localresto.util.CustomApplication;
import com.food.localresto.util.Helper;
import com.food.localresto.util.PrintReceipt;
import com.google.gson.Gson;

import java.util.Set;


import static com.food.localresto.MainActivity.mSeat;

public class OrderConfirmationActivity extends AppCompatActivity {
    private BluetoothAdapter bAdapter;
    private String alamatToPrint;
    private String namaToPrint;
    private String kotaToPrint;
    private String finalListToPrint;
    private CartObject[] cartList;

    private String idna,tgl,jam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        bAdapter = BluetoothAdapter.getDefaultAdapter();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        namaToPrint = getIntent().getExtras().getString("NAMA_RESTO");
        alamatToPrint = getIntent().getExtras().getString("ALAMAT_RESTO");
        kotaToPrint= getIntent().getExtras().getString("KOTA_RESTO");
        idna= getIntent().getExtras().getString("KODE_ORDER");
        tgl= Helper.dateFormatting2(getIntent().getExtras().getString("DATENA"));
        jam= Helper.dateFormattingJam(getIntent().getExtras().getString("DATENA"));

        finalListToPrint = getIntent().getExtras().getString("ORDER_LIST");

        Gson gson = ((CustomApplication) getApplication()).getGsonObject();
        cartList = gson.fromJson(finalListToPrint, CartObject[].class);

        ActionBar actionBar = getSupportActionBar();
        if(null != actionBar){
            actionBar.hide();
        }

        Button startTrackingButton = (Button)findViewById(R.id.start_tracking);
        if (mSeat == 0) {
            //tableId.setVisibility(View.GONE);
            startTrackingButton.setText("TAKE AWAY");
        } else {
            //tableId.setVisibility(View.VISIBLE);
            startTrackingButton.setText("CLOSE");
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
                    Intent orderIntent = new Intent(OrderConfirmationActivity.this, MainActivity.class);
                    startActivity(orderIntent);
                }else {
                    //if (isBluetoothKonek) {
                    mSeat = 0;

                    PrintReceipt.printBillFromOrder(OrderConfirmationActivity.this, namaToPrint,alamatToPrint,kotaToPrint,cartList);
                    Intent orderIntent = new Intent(OrderConfirmationActivity.this, MainActivity.class);
                    startActivity(orderIntent);


                }
                //}else{
                //    Toast.makeText(OrderComfirmationActivity.this, "Bluetooth False", Toast.LENGTH_LONG).show();
                //    Intent sampleIntent = new Intent(OrderComfirmationActivity.this, SettingActivity.class);
                //    startActivity(sampleIntent);
                //}

            }
        });*/

        //Button startTrackingButton = (Button)findViewById(R.id.start_tracking);
        startTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //    Toast.makeText(OrderComfirmationActivity.this, "Bluetooth False", Toast.LENGTH_LONG).show();
                if (mSeat == 0) {
                    Intent sampleIntent = new Intent(OrderConfirmationActivity.this, OrderConfirmTableActivity.class);
                    sampleIntent.putExtra("NAMA", namaToPrint);
                    sampleIntent.putExtra("ALAMAT", alamatToPrint);
                    sampleIntent.putExtra("KOTA", kotaToPrint);
                    sampleIntent.putExtra("KODENA", idna);
                    sampleIntent.putExtra("DATENA", getIntent().getExtras().getString("DATENA"));
                    sampleIntent.putExtra("ORDER_LIST", finalListToPrint);

                    startActivity(sampleIntent);

                } else {
                    mSeat = 0;
                    Intent sampleIntent = new Intent(OrderConfirmationActivity.this, MainActivity.class);
                    startActivity(sampleIntent);
                }

            }
        });
    }

    public static boolean isBluetoothHeadsetConnected() {

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()
                && mBluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) == BluetoothHeadset.STATE_CONNECTED;
    }

    @Override
    public void onBackPressed() {
        Intent confirmIntent = new Intent(OrderConfirmationActivity.this, MainActivity.class);
        startActivity(confirmIntent);
    }

}
