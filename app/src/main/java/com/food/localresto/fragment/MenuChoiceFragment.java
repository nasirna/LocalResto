package com.food.localresto.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.CartActivity;
import com.food.localresto.FoodActivity;
import com.food.localresto.R;
import com.food.localresto.adapter.ArrayAdapterItem;
import com.food.localresto.adapter.DoubleStickyHeaderListView;
import com.food.localresto.adapter.ListAdapter;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.ListItem;
import com.food.localresto.entities.MenuCategoryObject;
import com.food.localresto.entities.MenuItemObject;
import com.food.localresto.util.CustomApplication;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MenuChoiceFragment extends Fragment {

    private static final String TAG = MenuChoiceFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private ListView listview;
    //List<MenuItemObject> rowItems;
    private ArrayList<ListItem> arrayStudent = new ArrayList<ListItem>();

    //nasir
    private FloatingActionButton fab;
    private RelativeLayout tv;
    private MoneyTextView moneyTextView;

    //OnButtonPressListener buttonListener;

    //private Query mDatabase;
    private SqliteDatabase mDatabase;

    //ImageView imageViewIcon;

    private ProgressBar progressBar;
    private ProgressDialog dlg;

    private int mCount;
    private float jmlmCount;
    private TextView checkOutAmount, itemCountTextView;

    private String[] str;

    public MenuChoiceFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getActivity().setTitle(getString(R.string.manu_categories));
        View view = inflater.inflate(R.layout.fragment_menu_choice, container, false);

        moneyTextView = (MoneyTextView) view.findViewById(R.id.jml_order);
        itemCountTextView = (TextView) view.findViewById(R.id.item_count);
        listview = (ListView) view.findViewById(R.id.listView);
        //imageViewIcon = (ImageView) view.findViewById(R.id.foodImage);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // TODO Auto-generated method stub
                //ListItem item  = (ListItem) getListView().getAdapter().getItem(position);
                MenuItemObject item = (MenuItemObject) parent.getAdapter().getItem(position);

               /* if (item != null) {
                    Toast.makeText(getActivity(), "Item " + position + ": level " + item.getMenu_id() + ", text: " + item.getItem_name(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Item " + position + ": not exist", Toast.LENGTH_SHORT).show();
                }*/


                //Gson gson = ((CustomApplication) ((Activity) getContext()).getApplication()).getGsonObject();
                //final String objectToString = gson.toJson(item);

                final Bundle extras = new Bundle();
                extras.putInt("MENU_ITEM_ID", item.getMenu_item_id());
                extras.putString("MENU_KODEITEM", item.getKodeitem());
                extras.putString("MENU_ITEM_NAME", item.getItem_name());
                extras.putString("MENU_ITEM_DESC", item.getDescription());
                extras.putFloat("MENU_ITEM_PRICE",item.getItem_price());
                extras.putString("MENU_ITEM_PICT",item.getImage());
                //extras.putByteArray("MENU_ITEM_PICT", item.getImage());

                //Intent foodIntent = new Intent(context, FoodActivity.class);
                //foodIntent.putExtras(extras);
                //context.startActivity(foodInte
                // nt);
                //extras.putString("MENU_ITEM", objectToString);

                if (item.getMenu_id() > 1) {
                //if (item.getMenu_item_id() > 0) {
                    Toast.makeText(getActivity(), "Item " + item.getMenu_id() + ": not exist", Toast.LENGTH_SHORT).show();
                    Intent foodIntent = new Intent(getActivity(), FoodActivity.class);
                    foodIntent.putExtras(extras);
                    getActivity().startActivity(foodIntent);
                }else{

                }

            }
        });


        //nasir
        fab = (FloatingActionButton) view.findViewById(R.id.fabSetting);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent menuIntent = new Intent(SingleMenuCategoryActivity.this, MainActivity.class);
                //startActivity(menuIntent);
                showCustomizeOrderDialog();

            }
        });

        RelativeLayout relative1 = (RelativeLayout) view.findViewById(R.id.checkout_item_root);
        relative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkoutIntent = new Intent(getActivity(), CartActivity.class);
                startActivity(checkoutIntent);
                //showCustomizeOrderDialog();
            }
        });

        //getMenuItemsInCategoryFromRemoteServer();

        //ProgressDialog dialog = ProgressDialog.show(this.getContext(), "Loading...", "Please wait...", true);

        //ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        List<MenuItemObject> itemObject = new ArrayList<>();
        List<ListItem> rowItems = new ArrayList<>();

        //mDatabase = new Query(getActivity());
        mDatabase = SqliteDatabase.getInstance(getActivity());
        List<MenuItemObject> allMenus = mDatabase.listMenuItemAllbyGroup();
        //str = new String[allMenus.size()];


        //mDatabase = new Query(getActivity());
        mDatabase = SqliteDatabase.getInstance(getActivity());
        List<MenuCategoryObject> allProducts = mDatabase.listMenuCategory();

        //String finalOrderList = mGson.toJson(allProducts);

        //String[] str = new String[allProducts.size()];
        str = new String[allProducts.size()];
        for (int i = 0; i < allProducts.size(); i++) {
            str[i] = allProducts.get(i).getMenu_name();
        }


        String menuIdJen = "AA";
        //int menuIdBay = 99;
        String menuNameBay = "ZZ";

        for (int i = 0; i < allMenus.size(); i++) {
            //str[i] = allMenus.get(i).getMenu_name();


            //MenuItemObject orderHistory = response[k]; //nasir
            //int menuId = allMenus.get(i).getMenu_id();

            String menuName = allMenus.get(i).getMenu_name();
            String JenName = allMenus.get(i).getMenu_jenis().toUpperCase();

            //String menuName = allMenus.get(i).getKodeitem().substring(6).trim();

            //String StrKode = allMenus.get(i).getKodeitem();
            //String JenName0 = StrKode.substring(4).trim();
            //String JenName1 = StrKode.substring(5).trim();
            //String JenName = JenName0.substring(0,1);
            //String menuName = JenName1.substring(0,1);

            //Log.d(TAG, "Nilai intent extra: " + allMenus.get(i).getMenu_jenis() +" - "+ allMenus.get(i).getMenu_name()+" - "+ allMenus.get(i).getItem_name());

            String Catname = allMenus.get(i).getMenu_name().toUpperCase();
            //String Itemname = allMenus.get(i).getItem_name();

            if (!JenName.equals(menuIdJen)) {

                //itemObject.add(new MenuItemObject(9,"", DoubleStickyHeaderListView.HEADER_LEVEL_0, "", JenName, "", "", 999, ""));
                //itemObject.add(new MenuItemObject(9, DoubleStickyHeaderListView.HEADER_LEVEL_0, JenName,JenName, JenName, "", allMenus.get(i).getImage(), 0));
                //itemObject.add(new MenuItemObject(9, DoubleStickyHeaderListView.HEADER_LEVEL_0, JenName,JenName, JenName, "", allMenus.get(i).getImage(), 0));
                itemObject.add(new MenuItemObject(9,allMenus.get(i).getKodeitem(), DoubleStickyHeaderListView.HEADER_LEVEL_0, JenName,JenName, JenName, "", allMenus.get(i).getImage(), 0));
                arrayStudent.add(new ListItem(JenName));  //ini group header

            }

            if (!menuName.equals(menuNameBay)) {
                //itemObject.add(new MenuItemObject("",9, DoubleStickyHeaderListView.HEADER_LEVEL_1, Catname, Catname,Catname, "", allMenus.get(i).getImage(), 0));
                //itemObject.add(new MenuItemObject(allMenus.get(i).getMenu_item_id(), 9, JenName,JenName, JenName, "", allMenus.get(i).getImage(), 0));
                itemObject.add(new MenuItemObject(9, allMenus.get(i).getKodeitem(),DoubleStickyHeaderListView.HEADER_LEVEL_1, menuName, menuName,menuName, "", allMenus.get(i).getImage(), 0));
                //str[Catname];
                //v = menuId-1;
                //str[menuId-1] = Catname;

                //mAnimals.add(Catname);

                ListItem level0Header = new ListItem(i, Catname);
                //add(level0Header);
                arrayStudent.add(level0Header);


            }


            itemObject.add(new MenuItemObject(9, allMenus.get(i).getKodeitem(), DoubleStickyHeaderListView.HEADER_LEVEL_2, allMenus.get(i).getMenu_jenis(),menuName, allMenus.get(i).getItem_name(), "", allMenus.get(i).getImage(), allMenus.get(i).getItem_price()));

            //menuIdBay = menuId;
            menuNameBay = menuName;
            menuIdJen = JenName;
        }


        ListAdapter adapter = new ListAdapter(getActivity(), R.layout.view_list_item, itemObject);
        listview.setAdapter(adapter);

        //dialog.dismiss();

        //updateJmlCart();

        return view;
    }

    /*
    private void getMenuItemsInCategoryFromRemoteServer() {
        //Map<String, String> params = new HashMap<String,String>();
        //params.put(Helper.MENU_ID, String.valueOf(id));

        GsonRequest<MenuItemObject[]> serverRequest = new GsonRequest<MenuItemObject[]>(
                Request.Method.POST,
                Helper.PATH_TO_ALL_MENU_ITEM,
                MenuItemObject[].class,
                createRequestSuccessListener(),
                createRequestErrorListener());

        serverRequest.setRetryPolicy(new DefaultRetryPolicy(
                Helper.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(serverRequest);
    }

    private Response.Listener<MenuItemObject[]> createRequestSuccessListener() {
        return new Response.Listener<MenuItemObject[]>() {
            @Override
            public void onResponse(MenuItemObject[] response) {
                try {
                    Log.d(TAG, "Json Response " + response.toString());


                    List<MenuItemObject> itemObject = new ArrayList<>();
                    List<ListItem> rowItems = new ArrayList<>();

                    if (response.length > 0) {

                        String menuIdJen = "AA";
                        int menuIdBay = 99;


                        for (int k = 0; k < response.length; k++) {

                            MenuItemObject orderHistory = response[k]; //nasir
                            int menuId = orderHistory.getMenu_id();

                            String JenName = "XXXXXXXXXXXX";//orderHistory.getMenu_jenis().toUpperCase();
                            String Catname = "YYYYYYYYYYY";//orderHistory.getMenu_cat().toUpperCase();
                            String Itemname = orderHistory.getItem_name();

                            if (!JenName.equals(menuIdJen)) {

                                //itemObject.add(new MenuItemObject(9,"", DoubleStickyHeaderListView.HEADER_LEVEL_0, "", JenName, "", "", 999, ""));
                                itemObject.add(new MenuItemObject(9,DoubleStickyHeaderListView.HEADER_LEVEL_0,"","","",imageViewToByte(imageViewIcon),0));
                                arrayStudent.add(new ListItem(JenName));  //ini group header

                            }

                            if (menuId != menuIdBay) {
                                itemObject.add(new MenuItemObject(9,DoubleStickyHeaderListView.HEADER_LEVEL_1,"","","",imageViewToByte(imageViewIcon),0));
                                //str[Catname];
                                //v = menuId-1;
                                //str[menuId-1] = Catname;

                                //mAnimals.add(Catname);

                                ListItem level0Header = new ListItem(k, Catname);
                                //add(level0Header);
                                arrayStudent.add(level0Header);


                            }


                            itemObject.add(new MenuItemObject(response[k].getMenu_item_id(), "", DoubleStickyHeaderListView.HEADER_LEVEL_2, response[k].getMenu_cat(), response[k].getItem_name(),
                                    response[k].getDescription(), "", response[k].getItem_price(), response[k].getItem_options()));


                            menuIdBay = menuId;
                            menuIdJen = JenName;
                        }

                        //}

                        //ArrayList<ListItem> itemList = new ArrayList<ListItem>();

                        ListAdapter adapter = new ListAdapter(getActivity(), R.layout.view_list_item, itemObject);
                        listview.setAdapter(adapter);


                    } else {
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

    //nasir
    private void showCustomizeOrderDialog() {

        //setContentView(R.layout.progressbar_layout);

        // Get Bundle object that contain the array
        //Bundle extras = getActivity().getIntent().getExtras();


        // Extract the array from the Bundle object
        //final String[] items = extras.getStringArray("myArr");

/*        final CharSequence[] items = {
                "Ikan", "Udang", "Kerang", "Minuman"
        };*/



 /*       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pilih Kategori menu :");

        builder.setItems(str, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                //mDoneButton.setText(items[item]);
                Toast.makeText(getActivity(), "Pilih Kategori menu :" + str[item], Toast.LENGTH_LONG).show();

               // Toast.makeText(getActivity(), "Item " + position + ": not exist", Toast.LENGTH_SHORT).show();

                loading =true;  //nasir
                //invalidateOptionsMenu();

                int menuId = item + 1;
                //setTitle(items[item]);

                //getMenuItemsInCategoryFromRemoteServer(menuId);



            }
        });
        AlertDialog alert = builder.create();
        alert.show();
*/

       /* List<String> mAnimals = new ArrayList<String>();
        mAnimals.add("Cat");
        mAnimals.add("Dog");
        mAnimals.add("Horse");
        mAnimals.add("Elephant");
        mAnimals.add("Rat");
        mAnimals.add("Lion");*/

        //Create sequence of items
        //final CharSequence[] Animals = arrayStudent.toArray(new String[arrayStudent.size()]);

/*        for (ListItem student : arrayStudent) {

            System.out.println(student);

        }*/

       /* AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("Menu :");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = Animals[item].toString();  //Selected item in listview

                listview.setSelection(21);

            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();*/


        // add your items, this can be done programatically
        // your items can be from a database
       /* ObjectItem[] ObjectItemData = new ObjectItem[20];

        ObjectItemData[0] = new ObjectItem(91, "Mercury");
        ObjectItemData[1] = new ObjectItem(92, "Watson");
        ObjectItemData[2] = new ObjectItem(93, "Nissan");
        ObjectItemData[3] = new ObjectItem(94, "Puregold");
        ObjectItemData[4] = new ObjectItem(95, "SM");
        ObjectItemData[5] = new ObjectItem(96, "7 Eleven");
        ObjectItemData[6] = new ObjectItem(97, "Ministop");
        ObjectItemData[7] = new ObjectItem(98, "Fat Chicken");
        ObjectItemData[8] = new ObjectItem(99, "Master Siomai");
        ObjectItemData[9] = new ObjectItem(100, "Mang Inasal");
        ObjectItemData[10] = new ObjectItem(101, "Mercury 2");
        ObjectItemData[11] = new ObjectItem(102, "Watson 2");
        ObjectItemData[12] = new ObjectItem(103, "Nissan 2");
        ObjectItemData[13] = new ObjectItem(104, "Puregold 2");
        ObjectItemData[14] = new ObjectItem(105, "SM 2");
        ObjectItemData[15] = new ObjectItem(106, "7 Eleven 2");
        ObjectItemData[16] = new ObjectItem(107, "Ministop 2");
        ObjectItemData[17] = new ObjectItem(108, "Fat Chicken 2");
        ObjectItemData[18] = new ObjectItem(109, "Master Siomai 2");
        ObjectItemData[19] = new ObjectItem(110, "Mang Inasal 2");*/

        // our adapter instance
        ArrayAdapterItem adapter = new ArrayAdapterItem(getActivity(), arrayStudent);

        // create a new ListView, set the adapter and item click listener
        ListView listViewItems = new ListView(getActivity());
        listViewItems.setAdapter(adapter);

        //listViewItems.setOnItemClickListener(new OnItemClickListenerListViewItem());

        // put the ListView in the pop up
        //alertDialogStores = new AlertDialog.Builder(getActivity())
        //        .setView(listViewItems)
        //       .setTitle("Menu Category :")
        //        .show();

        //lv.setAdapter(adapter);

        AlertDialog.Builder alertDialogStores = new AlertDialog.Builder(getActivity());

        alertDialogStores.setView(listViewItems);
        alertDialogStores.setTitle("Menu Kategori :");
        //alertDialogStores.show();
        final AlertDialog ad = alertDialogStores.show();


        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // TODO Auto-generated method stub
                Context context = v.getContext();

                ListItem item = (ListItem) parent.getAdapter().getItem(position);
                TextView textViewItem = ((TextView) v.findViewById(R.id.textViewItem));


                // just toast it
                if (!((ListItem) parent.getAdapter().getItem(position)).isGroupHeader) {

                    // get the clicked item name
                    String listItemText = textViewItem.getText().toString();

                    // get the clicked item ID
                    String listItemId = textViewItem.getTag().toString();

                    if (item != null) {
                        // Toast.makeText(v.getContext(), "Item " + position + ": level " + item.level + ", text: " + item.text, Toast.LENGTH_SHORT).show();
                        listview.setSelection(item.level + (position));
                    } else {
                        Toast.makeText(v.getContext(), "Item " + position + ": not exist", Toast.LENGTH_SHORT).show();
                    }

                    //} else {
                    //Toast.makeText(v.getContext(), "Item " + position + ": not exist", Toast.LENGTH_SHORT).show();
                    //ad.dismiss();
                    //return;
                }

                //((MainActivity) context).alertDialogStores.cancel();
                getActivity().invalidateOptionsMenu();
                ad.dismiss();
            }
        });


    }


    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    //nasir
    private void updateJmlCart() {
        mCount = ((CustomApplication) getActivity().getApplication()).cartItemCount();
        jmlmCount = (float) ((CustomApplication) getActivity().getApplication()).getSubtotalAmount();

        //DrawCart dCart = new DrawCart(getActivity());
        //relative1.setIcon(dCart.buildCounterDrawable(mCount, R.drawable.cart));


        itemCountTextView.setSelected(true);
        itemCountTextView.setText(String.valueOf(mCount));


        //MoneyTextView moneyTextView = (MoneyTextView) view.findViewById(R.id.jml_order);
        moneyTextView.setAmount(jmlmCount);
    }


/*
    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_shop);
        DrawCart dCart = new DrawCart(getActivity());
        menuItem.setIcon(dCart.buildCounterDrawable(mCount, R.drawable.cart));

        menuItem.setVisible(true);

        return true;
    }
*/

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
        MenuItem menuItem = menu.findItem(R.id.action_shop);
        if(menuItem!=null){
            menuItem.setVisible(false);
        }
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        updateJmlCart();
        getActivity().invalidateOptionsMenu();
    }

}
