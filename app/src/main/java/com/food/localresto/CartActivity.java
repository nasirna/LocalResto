package com.food.localresto;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.adapter.CartAdapter;
import com.food.localresto.entities.CartObject;
import com.food.localresto.entities.EventMessage;
import com.food.localresto.util.CustomApplication;
import com.food.localresto.util.CustomSharedPreference;
import com.food.localresto.util.DrawCart;
import com.food.localresto.util.SimpleDividerItemDecoration;
import com.google.gson.Gson;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = CartActivity.class.getSimpleName();

    private LinearLayout wrapper;
    private LinearLayout downWrapper;
    private LinearLayout noOrder;

    private TextView orderTotalAmount;
    private TextView orderItemCount;

    private Gson mGson;
    private CustomSharedPreference pref;
    private List<CartObject> orderedItems;

    //nasir
    DrawCart drawCart = new DrawCart(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setTitle(getString(R.string.your_cart));

        mGson = ((CustomApplication)getApplication()).getGsonObject();
        pref = ((CustomApplication)getApplication()).getShared();

        wrapper = (LinearLayout)findViewById(R.id.activity_cart);
        downWrapper = (LinearLayout)findViewById(R.id.button_container);
        noOrder = (LinearLayout)findViewById(R.id.no_order);

        wrapper.setVisibility(View.GONE);
        downWrapper.setVisibility(View.GONE);
        noOrder.setVisibility(View.GONE);

        orderItemCount = (TextView) findViewById(R.id.order_item_count);
        orderTotalAmount = (TextView) findViewById(R.id.order_total_amount);
        RecyclerView cartRecyclerView = (RecyclerView) findViewById(R.id.cart_item);

        Button backToMenuButton = (Button)findViewById(R.id.back_to_menu_button);
        backToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuIntent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(menuIntent);
            }
        });

        Button checkoutButton = (Button)findViewById(R.id.check_out_button);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent checkoutIntent = new Intent(CartActivity.this, CheckoutActivity.class);
                if(orderedItems.size() > 0){
                    String finalOrderList = mGson.toJson(orderedItems);
                    checkoutIntent.putExtra("FINAL_ORDER", finalOrderList);
                    startActivity(checkoutIntent);
                }
            }
        });

        String itemInCart = pref.getCartItems();
        CartObject[] ordersInCart = mGson.fromJson(itemInCart, CartObject[].class);
        if(ordersInCart == null){
            noOrder.setVisibility(View.VISIBLE);
        }else{
            wrapper.setVisibility(View.VISIBLE);
            downWrapper.setVisibility(View.VISIBLE);
            DrawCart cart = new DrawCart(this);
            orderedItems = cart.convertObjectArrayToListObject(ordersInCart);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            cartRecyclerView.setLayoutManager(linearLayoutManager);
            cartRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
            cartRecyclerView.setHasFixedSize(true);

            CartAdapter mAdapter = new CartAdapter(CartActivity.this, orderedItems);
            cartRecyclerView.setAdapter(mAdapter);

/*  nasir
            orderItemCount.setText(String.valueOf(orderedItems.size()));
            DrawCart drawCart = new DrawCart(this);
            double subtotal = drawCart.getSubtotalAmount(orderedItems);
            orderTotalAmount.setText("$" + String.valueOf(subtotal));*/

            double subtotal = drawCart.getSubtotalAmount(orderedItems);
            orderTotalAmount.setText("Rp " + String.format("%1$,.0f", Float.valueOf((float) subtotal) ));

            int total = (int) drawCart.getSubtotalItem(orderedItems);
            orderItemCount.setText(String.valueOf(total));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventFromAdapter(EventMessage event){
/*     nasir
        orderTotalAmount.setText("$" + event.getSubtotal());
        orderItemCount.setText(event.getItemCount());*/

        orderTotalAmount.setText("Rp " + String.format("%1$,.0f", Float.valueOf(event.getSubtotal() )));

        int total = (int) drawCart.getSubtotalItem(orderedItems);
        orderItemCount.setText(String.valueOf(total));
    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

}
