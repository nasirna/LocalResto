package com.food.localresto.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.food.localresto.CartActivity;
import com.food.localresto.LoginActivity;
import com.food.localresto.MainActivity;
import com.food.localresto.OrderConfirmationActivity;
import com.food.localresto.OrderListActivity;
import com.food.localresto.R;
import com.food.localresto.adapter.HistoryAdapter;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.HistoryObject;
import com.food.localresto.entities.LoginObject;
import com.food.localresto.entities.OrderObject;
import com.food.localresto.util.CustomApplication;
import com.food.localresto.util.DrawCart;
import com.google.gson.Gson;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderHistoryFragment extends Fragment {

    private static final String TAG = OrderHistoryFragment.class.getSimpleName();
    private RecyclerView historyRecyclerView;
    //private Query mDatabase;
    private SqliteDatabase mDatabase;
    private LoginObject user;

    //nb
    private HistoryAdapter mAdapter;
    private List<OrderObject> orderList;
    private List<OrderObject> orderListDetil;
    private MaterialBetterSpinner spinner, spinner2;
    private LinearLayout wrapperTabel;

    public OrderHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);  //berguna bagi show/hide option menu
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.my_history));
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);

        spinner = (MaterialBetterSpinner) view.findViewById(R.id.spinner);
        spinner2 = (MaterialBetterSpinner) view.findViewById(R.id.spinner2);

        wrapperTabel = (LinearLayout) view.findViewById(R.id.tableid);

        mDatabase = SqliteDatabase.getInstance(getActivity());
        //mDatabase = new Query(getActivity());
        //List<HistoryObject> orderHistory = mDatabase.listHistoryObject();
        List<HistoryObject> orderHistory = mDatabase.listHistoryObjectGroupByTabel();

        historyRecyclerView = (RecyclerView)view.findViewById(R.id.order_history);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        historyRecyclerView.setLayoutManager(linearLayoutManager);
        historyRecyclerView.setHasFixedSize(true);
        Gson mGson = ((CustomApplication) ((Activity) getContext()).getApplication()).getGsonObject();
        String storedUser = ((CustomApplication) ((Activity) getContext()).getApplication()).getShared().getUserData();
        //LoginObject userObject = mGson.fromJson(storedUser, LoginObject.class);
        //String userObject1 = mGson.toJson(storedUser);
        //LoginObject userObject = mGson.fromJson(userObject1, LoginObject.class);


        try {
            JSONArray kontak = new JSONArray(storedUser);

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


        // get user id from shared preference
        //LoginObject loginUser = ((CustomApplication)getActivity().getApplication()).getLoginUser();
        //int id = loginUser.getId();
        int id =1;

        //getOrderHistoriesFromRemoteServer();

        orderList = new ArrayList<>();
        orderListDetil = new ArrayList<>();


        String IdOrder = "AAAAA";
        Double l_Tot=0.00;
        for (int i = 0; i < orderHistory.size(); i++) {
            //String idOrderna = orderHistory.get(i).getOrder_id().substring(0,16);

            //detil
            orderList.add(new OrderObject(orderHistory.get(i).getOrder_id(),orderHistory.get(i).getOrder_date(),orderHistory.get(i).getOrder_price(),orderHistory.get(i).getStatus(),orderHistory.get(i).getTabel(),orderHistory.get(i).getPajak(),orderHistory.get(i).getDisc(),0,orderHistory.get(i).getTotbayar(),orderHistory.get(i).getJml()));

        }

        mDatabase = SqliteDatabase.getInstance(getActivity());
        List<HistoryObject> orderHistoryDet = mDatabase.listHistoryObject();
        for (int i = 0; i < orderHistoryDet.size(); i++) {
            orderListDetil.add(new OrderObject(orderHistoryDet.get(i).getOrder_id(),orderHistoryDet.get(i).getOrder_date(),orderHistoryDet.get(i).getOrder_price(),orderHistoryDet.get(i).getStatus(),orderHistoryDet.get(i).getTabel(),orderHistoryDet.get(i).getPajak(),orderHistoryDet.get(i).getDisc(),0,0,orderHistoryDet.get(i).getJml()));

        }

        Collections.reverse(orderList);
        Collections.reverse(orderListDetil);

        //HistoryAdapter mAdapter = new HistoryAdapter(getActivity(), orderList);

        mAdapter = new HistoryAdapter(getActivity(), orderList, new HistoryAdapter.RecyclerViewClickListener(){
            @Override
            public void OnClick(View v, final int position) {
                //Toast.makeText(DaftarMenuItemActivity.this, "Position " + position, Toast.LENGTH_SHORT).show();

                //menuidNya = orderList.get(position).getId();
                //posisiNya =position;

                //final MenuItemObject menu = orderList.get(position);

                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.inflate(R.menu.popup_menu);
                String role = user.getRole();
                if (role.equals("1")) {
                    popupMenu.getMenu().findItem(R.id.update).setVisible(false);
                    popupMenu.getMenu().findItem(R.id.delete).setVisible(false);
                }else{
                    popupMenu.getMenu().findItem(R.id.update).setVisible(false);
                }



                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:

                                //alertDialogHelper.showAlertDialog("", "Delete Item Menu", "DELETE", "CANCEL", 3, false);
                                //Toast.makeText(getActivity(), "Position " + position, Toast.LENGTH_SHORT).show();

                                mDatabase.deleteOrder(orderList.get(position).getId());
                                mDatabase.deleteOrderItem(orderList.get(position).getId());

                                mAdapter.notifyDataSetChanged();


                                //refresh the activity
                                //(Activity) getContext().finish();
                                //startActivity(getIntent());
                                //getActivity().recreate();
                                getActivity().finish();
                                startActivity(getActivity().getIntent());

                                break;

                            case R.id.update:
                                Toast.makeText(getActivity(), "Position " + position, Toast.LENGTH_SHORT).show();

                                //showDialogUpdate(menu);

                                break;

                            case R.id.view:

                                String id = orderList.get(position).getId().substring(0,16);

                                //Toast.makeText(getActivity(), "Position " + id, Toast.LENGTH_SHORT).show();
                                if(orderList.get(position).getJml()>1){
                                    //List<OrderObject> dataFilter = new ArrayList<>();
                                    //for(OrderObject data : orderListDetil){
                                    //    String idna = data.getId();
                                    //    if(idna.contains(id)){
                                    //        dataFilter.add(data);
                                    //    }
                                    //}
                                    //mAdapter.setFilter(dataFilter);

                                    Intent historyIntent = new Intent(getActivity(), OrderListActivity.class);
                                    historyIntent.putExtra("ORDER_ID", orderList.get(position).getId());
                                    historyIntent.putExtra("JML_ORDER", orderList.get(position).getJml());
                                    historyIntent.putExtra("DATENA", orderList.get(position).getOrderDate());
                                    historyIntent.putExtra("PAJAK", String.valueOf(orderList.get(position).getPajak()));
                                    historyIntent.putExtra("BAYAR", String.valueOf(orderList.get(position).getTotalBayar()));
                                    historyIntent.putExtra("DISC", String.valueOf(orderList.get(position).getDisc()));
                                    startActivity(historyIntent);

                                }else{

                                    Intent historyIntent = new Intent(getActivity(), OrderListActivity.class);
                                    historyIntent.putExtra("ORDER_ID", orderList.get(position).getId());
                                    historyIntent.putExtra("DATENA", orderList.get(position).getOrderDate());
                                    historyIntent.putExtra("PAJAK", String.valueOf(orderList.get(position).getPajak()));
                                    historyIntent.putExtra("BAYAR", String.valueOf(orderList.get(position).getTotalBayar()));
                                    historyIntent.putExtra("DISC", String.valueOf(orderList.get(position).getDisc()));
                                    startActivity(historyIntent);

                                }

                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }

        });

        /*mAdapter = new HistoryAdapter(this, orderList, new OrderHistoryFragment.RecyclerViewClickListener() {
            @Override
            public void OnClick(View v, final int position) {
                //Toast.makeText(DaftarMenuItemActivity.this, "Position " + position, Toast.LENGTH_SHORT).show();

                menuidNya = orderList.get(position).getId();
                posisiNya =position;

                //final MenuItemObject menu = orderList.get(position);

                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:

                                alertDialogHelper.showAlertDialog("", "Delete Item Menu", "DELETE", "CANCEL", 3, false);

                                break;

                            case R.id.update:

                                //showDialogUpdate(menu);

                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }

        });
    */


/////
        String[] str = {"All", "Table", "Takeaway"};
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, str);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String spinnerValue=spinner.getText().toString();
                //Log.i("value", spinnerValue);

                if(spinnerValue.equals("Table")){
                    Toast.makeText(getActivity(), "Pilihan : " + spinnerValue, Toast.LENGTH_SHORT).show();

                    List<OrderObject> dataFilter = new ArrayList<>();
                    for(OrderObject data : orderList){
                        int tabel = data.getTabel();
                        if(tabel!=0){
                            dataFilter.add(data);
                        }
                    }
                    mAdapter.setFilter(dataFilter);
                    ViewSpinner2();
                    wrapperTabel.setVisibility(View.VISIBLE);

                }else if(spinnerValue.equals("Takeaway")){
                    Toast.makeText(getActivity(), "Pilihan : " + spinnerValue, Toast.LENGTH_SHORT).show();

                    List<OrderObject> dataFilter = new ArrayList<>();
                    for(OrderObject data : orderList){
                        int tabel = data.getTabel();
                        if(tabel==0){
                            dataFilter.add(data);
                        }
                    }
                    mAdapter.setFilter(dataFilter);
                    wrapperTabel.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(getActivity(), "Pilihan : " + spinnerValue, Toast.LENGTH_SHORT).show();
                    List<OrderObject> dataFilter = new ArrayList<>();
                    for(OrderObject data : orderList){
                        dataFilter.add(data);
                    }
                    mAdapter.setFilter(dataFilter);
                    wrapperTabel.setVisibility(View.INVISIBLE);
                }

            }
        });

//////

        historyRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private void ViewSpinner2(){

        String[] str = {"1", "2", "3", "4", "5" ,"6" ,"7", "8", "9", "10"};
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, str);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter);

        spinner2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String spinnerValue=spinner2.getText().toString();
                //Log.i("value", spinnerValue);

                    Toast.makeText(getActivity(), "Pilihan : " + spinnerValue, Toast.LENGTH_SHORT).show();

                    List<OrderObject> dataFilter = new ArrayList<>();
                    for(OrderObject data : orderList){
                        int tabel = data.getTabel();
                        if(tabel==Integer.valueOf(spinnerValue)){
                            dataFilter.add(data);
                        }
                    }
                    mAdapter.setFilter(dataFilter);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_shop) {
            Intent checkoutIntent = new Intent(getActivity(), CartActivity.class);
            startActivity(checkoutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.tableresto);
        MenuItem menuItem2 = menu.findItem(R.id.menu_more);
        if(menuItem!=null || menuItem2!=null){
            menuItem.setVisible(false);
            menuItem2.setVisible(false);  //sebelumnya true
        }
    }

     /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem searchItem= menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) (searchItem.getActionView());
        searchView.setOnQueryTextListener(this);
    }*/
/*

    private void getOrderHistoriesFromRemoteServer(){
        GsonRequest<HistoryObject[]> serverRequest = new GsonRequest<HistoryObject[]>(
                Request.Method.GET,
                Helper.PATH_TO_ALL_ORDER,
                HistoryObject[].class,
                createRequestSuccessListener(),
                createRequestErrorListener());

        serverRequest.setRetryPolicy(new DefaultRetryPolicy(
                Helper.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(serverRequest);
    }

    private Response.Listener<HistoryObject[]> createRequestSuccessListener() {
        return new Response.Listener<HistoryObject[]>() {
            @Override
            public void onResponse(HistoryObject[] response) {
                try {
                    Log.d(TAG, "Json Response " + response.toString());
                    List<OrderObject> orderList = new ArrayList<>();
                    if(response.length > 0){
                        for(int i = 0; i < response.length; i++){
                            HistoryObject orderHistory = response[i];
                            int orderId = orderHistory.getOrder_id();
                            String orderDate = orderHistory.getOrder_date();
                            float orderPrice = orderHistory.getOrder_price();
                            String orderStatus = orderHistory.getStatus();
                            orderList.add(new OrderObject(orderId, orderDate, orderPrice, orderStatus));
                        }
                        HistoryAdapter mAdapter = new HistoryAdapter(getActivity(), orderList);
                        historyRecyclerView.setAdapter(mAdapter);

                    }else{
                        Toast.makeText(getActivity(), R.string.failed_login, Toast.LENGTH_LONG).show();
                        return;
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
    }
*/

    /*@Override
    public void onBackPressed() {
        Intent confirmIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(confirmIntent);
    }*/


}