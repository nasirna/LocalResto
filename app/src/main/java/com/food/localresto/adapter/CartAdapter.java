package com.food.localresto.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.R;
import com.food.localresto.entities.CartObject;
import com.food.localresto.entities.EventMessage;
import com.food.localresto.util.CustomApplication;
import com.food.localresto.util.DrawCart;
import com.google.gson.Gson;
import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private static final String TAG = CartAdapter.class.getSimpleName();

    private Context context;
    private List<CartObject> cartList;
    private DrawCart drawCart;

    public CartAdapter(Context context, List<CartObject> cartList) {
        this.context = context;
        this.cartList = cartList;
        drawCart = new DrawCart(context);
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_order_layout, parent, false);
        return new CartViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, final int position) {
        final CartObject cartObject = cartList.get(position);

        holder.orderItemName.setText(cartObject.getOrderName());
        holder.orderItemExtra.setText(cartObject.getExtra());
        holder.orderQuantity.setText(String.valueOf(cartObject.getQuantity()));

        //nasir
        //holder.orderSubtotal.setText("$" + String.valueOf(cartObject.getPrice()) + "0");
        holder.orderSubtotal.setText(String.format("%1$,.0f", Float.valueOf(cartObject.getPrice()*cartObject.getQuantity())));

        holder.removeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartList.remove(position);
                notifyDataSetChanged();
                //update order in shared preference
                Gson mGson = ((CustomApplication)((Activity)context).getApplication()).getGsonObject();
                String updatedOrder = mGson.toJson(cartList);
                ((CustomApplication)((Activity)context).getApplication()).getShared().updateCartItems(updatedOrder);
                //update the order subtotal when an item quantity is increased or decreased
                if(cartList.size() <= 0){
                    Intent intent = ((Activity) context).getIntent();
                    ((Activity) context).finish();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    context.startActivity(intent);
                }else{
                    double currentSubtotal = drawCart.getSubtotalAmount(cartList);
                    EventBus.getDefault().post(new EventMessage(String.valueOf(currentSubtotal), String.valueOf(cartList.size())));
                }
            }
        });

        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartObject.getQuantity() <= 0){
                    return;
                }
                int quantity = cartObject.getQuantity() - 1;
                holder.orderQuantity.setText(String.valueOf(quantity));

                //nasir
                holder.orderSubtotal.setText(String.format("%1$,.0f", Float.valueOf(cartObject.getPrice()*quantity) ));

                cartObject.setQuantity(quantity);
                //update the order subtotal when an item quantity is increased or decreased
                double currentSubtotal = drawCart.getSubtotalAmount(cartList);
                EventBus.getDefault().post(new EventMessage(String.valueOf(currentSubtotal), String.valueOf(cartList.size())));
            }
        });

        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = cartObject.getQuantity() + 1;
                holder.orderQuantity.setText(String.valueOf(quantity));

                //nasir
                holder.orderSubtotal.setText(String.format("%1$,.0f", Float.valueOf(cartObject.getPrice()*quantity) ));

                cartObject.setQuantity(quantity);
                //update the order subtotal when an item quantity is increased or decreased
                double currentSubtotal = drawCart.getSubtotalAmount(cartList);
                EventBus.getDefault().post(new EventMessage(String.valueOf(currentSubtotal), String.valueOf(cartList.size())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
