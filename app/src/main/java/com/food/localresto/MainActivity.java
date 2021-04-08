package com.food.localresto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.LoginObject;
import com.food.localresto.entities.RestaurantObject;
import com.food.localresto.fragment.HomeFragment;
import com.food.localresto.fragment.LaporanFragment;
import com.food.localresto.fragment.MenuChoiceFragment;
import com.food.localresto.fragment.OrderHistoryFragment;
import com.food.localresto.fragment.SettingFragment;
import com.food.localresto.fragment.MasterFragment;
import com.food.localresto.fragment.ProfileFragment;
import com.food.localresto.fragment.SettingFragmentNew;
import com.food.localresto.util.CustomApplication;
import com.food.localresto.util.DrawCart;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/*
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}*/


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private FragmentManager fragmentManager;
    private Fragment fragment = null;
    private int mCount;
    public static int mSeat = 0;

    private NavigationView navigationView;
    private TextView nmUser, nmPersh;

    private SqliteDatabase mDatabase;
    private String usernm;

    public static final String SHARED_PREFS = "SHARED_PREFERENCE";
    public static final String PAJAK = "pajak";
    public static final String DISC = "disc";
    public static final String FOOTER = "footer";

    private String pajak, disc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = null;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mCount = ((CustomApplication)getApplication()).cartItemCount();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new HomeFragment();
        fragmentTransaction.replace(R.id.content_main, fragment);
        fragmentTransaction.commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    //fragment = new HomeFragment();
                    fragment = new MenuChoiceFragment();
                } /*else if (id == R.id.nav_menu_category) {
                    fragment = new MenuCategoryFragment();
                } */else if (id == R.id.nav_order_history) {
                    /*final ProgressDialog dlg = new ProgressDialog(MainActivity.this);
                    dlg.setMessage("Please wait...");
                    dlg.setCancelable(false);
                    dlg.show();

                    final int totalProgressTime = 4*1000;
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            dlg.dismiss();
                        }
                    }, totalProgressTime);*/

                    fragment = new OrderHistoryFragment();
                } else if (id == R.id.nav_setting) {
                    //fragment = new SettingFragment();
                    fragment = new SettingFragmentNew();
                } /*else if (id == R.id.nav_hot_deals) {
                    //Intent hotDealIntent = new Intent(MainActivity.this, HotDealActvity.class);
                    //startActivity(hotDealIntent);
                    //Intent sampleIntent = new Intent(MainActivity.this, SampleActivity.class);
                    Intent sampleIntent = new Intent(MainActivity.this, MenuItemParcelActivity.class);
                    startActivity(sampleIntent);
                }
                else if (id == R.id.nav_notification) {
                    Intent notificationIntent = new Intent(MainActivity.this, NotificationActivity.class);
                    startActivity(notificationIntent);
                }*/
                else if (id == R.id.nav_menu_laporan) {
                    fragment = new LaporanFragment();
                } else if (id == R.id.nav_profile) {
                    fragment = new ProfileFragment();
                }else if (id == R.id.nav_master) {
                    fragment = new MasterFragment();
                }/*else if (id == R.id.nav_master_data) {
                    fragment = new MasterDataFragment();
                }*/else if (id == R.id.nav_logout) {
                    //remove user data from shared preference
                    SharedPreferences mShared = ((CustomApplication)getApplication()).getShared().getInstanceOfSharedPreference();
                    mShared.edit().clear().apply();

                    //Navigate to login page
                    Intent loginPageIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(loginPageIntent);
                    finish();
                }

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                assert drawer != null;
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        //Gson mGson = ((CustomApplication)getApplication()).getGsonObject();
        //String storedUser = ((CustomApplication)getApplication()).getShared().getUserData();
        //LoginObject userObject = mGson.fromJson(storedUser, LoginObject.class);

        //String role = userObject.getRole();
        //if (role.equals("1")) {
        //    hidedrawermenu();
        //}

        View header=navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        nmUser = (TextView) header.findViewById(R.id.user);
        nmPersh = (TextView) header.findViewById(R.id.persh);

        //nmUser.setText(getIntent().getExtras().getString("USER_NM"));
        Gson mGson = ((CustomApplication)getApplication()).getGsonObject();
        String storedUser = ((CustomApplication)getApplication()).getShared().getUserData();
        /*LoginObject userObject = mGson.fromJson(storedUser, LoginObject.class);
        if(userObject != null){
            Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intentMain);
        }*/

        try {
            JSONArray contacts = new JSONArray(storedUser);

            if(contacts.length()  > 0){

                //Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                //startActivity(intentMain);
                for (int i = 0; i < contacts.length(); i++){
                    JSONObject c = contacts.getJSONObject(i);
                    //int idna = c.getInt("id");
                    usernm = c.getString("username");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        nmUser.setText(usernm.toUpperCase());
        //
        mDatabase = SqliteDatabase.getInstance(MainActivity.this);
        List<RestaurantObject> restoObject = mDatabase.namaResto("9999");
        if(restoObject.size() > 0) {

            for (int i = 0; i < restoObject.size(); i++) {
                nmPersh.setText(restoObject.get(i).getName());
            }

        }

        loadData();

        if(pajak.equals("")|| disc.equals("")  ) {
            saveDataPajak();
        }

    }


    private void hidedrawermenu(){
        Menu menu = navigationView.getMenu();
       // menu.findItem(R.id.nav_menu_category).setVisible(false);
       // menu.findItem(R.id.nav_menu_choice).setVisible(false);
        menu.findItem(R.id.nav_order_history).setVisible(false);
       // menu.findItem(R.id.nav_favorites).setVisible(false);
       // menu.findItem(R.id.bill_pay).setVisible(false);
       // menu.findItem(R.id.nav_landscape).setVisible(false);
        menu.findItem(R.id.nav_hot_deals).setVisible(false);
        menu.findItem(R.id.nav_notification).setVisible(false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Intent optionIntent = new Intent(MainActivity.this, LoginOptionActivity.class);
            startActivity(optionIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.tableresto);
        DrawCart dCart = new DrawCart(this);
        menuItem.setIcon(dCart.buildCounterDrawable2(mSeat, R.drawable.dining));

        //menuItem.setVisible(false);

        menuItem = menu.findItem(R.id.menu_more);
        //DrawCart dCart = new DrawCart(this);
        menuItem.setIcon(dCart.buildCounterDrawable(mCount, R.drawable.ic_more));

        menuItem.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*if (id == R.id.action_shop) {
            Intent checkoutIntent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(checkoutIntent);
            return true;
        }*/

        if (id == R.id.tableresto) {
            Intent checkoutIntent = new Intent(MainActivity.this, TableActivity.class);
            startActivity(checkoutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume() {
        mCount = ((CustomApplication)getApplication()).cartItemCount();
        invalidateOptionsMenu();
        super.onResume();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        pajak = sharedPreferences.getString(PAJAK, "");
        disc = sharedPreferences.getString(DISC, "");
        //footer_struk = sharedPreferences.getString(FOOTER, "");
    }

    public void saveDataPajak() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PAJAK, "0");
        editor.putString(DISC, "0");
        editor.putString(FOOTER, "");

        editor.apply();

    }

}
