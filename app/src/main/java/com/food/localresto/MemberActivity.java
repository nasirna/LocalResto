package com.food.localresto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.adapter.UserAdapterServer;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.LoginObject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MemberActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;

    //private Query mDatabase;
    private SqliteDatabase mDatabase;


    //String[] SPINNERLIST = {"Makanan", "Minuman"};

    //private String item = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_member);

        setTitle(getString(R.string.member));

        recyclerView = (RecyclerView)findViewById(R.id.member_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);


        //String menuItem = getIntent().getExtras().getString("CATEGORY_NAME");
        //setTitle(menuItem);

        //int menuId = getIntent().getExtras().getInt("CATEGORY_ID");

        //addNewUserToRemoteServer();
        getMemberFromLocal();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new quick task
                addTaskDialog();
            }
        });
    }

    private void getMemberFromLocal(){
        //mDatabase = new Query(this);
        mDatabase = SqliteDatabase.getInstance(this);

        List<LoginObject> loginObject = mDatabase.listUser();
        if(loginObject.size() > 0) {

            //display restaurant menu information
            //List<LoginObject> usrObject = new ArrayList<>();
            //for(int i = 0; i < loginObject.size(); i++){
                //Log.d(TAG, "Menu name " + response[i].getUsername());
            //    usrObject.add(new LoginObject(usrObject.get(i).getId(), usrObject.get(i).getUsername(), usrObject.get(i).getUsername(),usrObject.get(i).getUsername(),usrObject.get(i).getUsername(),usrObject.get(i).getUsername(),usrObject.get(i).getUsername()));
            //}
            UserAdapterServer mAdapter = new UserAdapterServer(MemberActivity.this, loginObject);
            recyclerView.setAdapter(mAdapter);

        }else{
            Toast.makeText(MemberActivity.this, "User is empty", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_member_layout, null);

        final EditText username = (EditText)subView.findViewById(R.id.username);
        final EditText password = (EditText)subView.findViewById(R.id.password);
        final EditText email = (EditText)subView.findViewById(R.id.email);
        final EditText address = (EditText)subView.findViewById(R.id.address);
        final EditText phoneNumber = (EditText)subView.findViewById(R.id.phone_number);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New User");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ADD USER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String enteredUsername = username.getText().toString().trim();
                String enteredEmail = email.getText().toString().trim();
                String enteredPassword = password.getText().toString().trim();
                String enteredAddress = address.getText().toString();
                String enteredPhoneNumber = phoneNumber.getText().toString();

                /*if(TextUtils.isEmpty(enteredUsername) || TextUtils.isEmpty(enteredEmail) || TextUtils.isEmpty(enteredPassword)
                        || TextUtils.isEmpty(enteredAddress) || TextUtils.isEmpty(enteredPhoneNumber)){
                    Helper.displayErrorMessage(MemberActivity.this, getString(R.string.fill_all_fields));
                }

                if(!Helper.isValidEmail(enteredEmail)){
                    Helper.displayErrorMessage(MemberActivity.this, getString(R.string.invalid_email));
                }

                if(enteredUsername.length() < Helper.MINIMUM_LENGTH || enteredPassword.length() < Helper.MINIMUM_LENGTH){
                    Helper.displayErrorMessage(MemberActivity.this,getString(R.string.maximum_length));
                }*/

                Log.d(TAG, enteredUsername + enteredEmail + enteredPassword + enteredAddress + enteredPhoneNumber);
                //Add new user to the server

                //Gson mGson = ((CustomApplication)getApplication()).getGsonObject();
                //String storedUser = ((CustomApplication)getApplication()).getShared().getUserData();
                //LoginObject userObject = mGson.fromJson(storedUser, LoginObject.class);

                //String kode = userObject.getKodeuser().substring(0,4);
                String kode = "9999";

                SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
                //String currentDateandTime = String.format("%05d", menuitem.getMenu_id())+ sdf.format(new Date());
                String kodeUser = kode + sdf.format(new Date());

                LoginObject newUser = new LoginObject(0, enteredUsername, enteredPassword, "1",enteredEmail,  enteredAddress, enteredPhoneNumber,"1");

                mDatabase.addUser(newUser);

                finish();
                startActivity(getIntent());

                //addNewUserToRemoteServer(kodeUser, enteredUsername, enteredEmail, enteredPassword, enteredAddress, enteredPhoneNumber);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    /*
    private void addNewUserToRemoteServer(String kodeuser, String username, String email, String password, String address, String phoneNumber){
        Map<String, String> params = new HashMap<String,String>();
        params.put(Helper.KODEUSER, kodeuser);
        params.put(Helper.USERNAME, username);
        params.put(Helper.EMAIL, email);
        params.put(Helper.PASSWORD, password);
        params.put(Helper.ADDRESS, address);
        params.put(Helper.PHONE_NUMBER, phoneNumber);

        GsonRequest<LoginObject> serverRequest = new GsonRequest<LoginObject>(
                Request.Method.POST,
                Helper.PATH_TO_SERVER_REGISTRATION,
                LoginObject.class,
                params,
                createRequestSuccessListener2(),
                createRequestErrorListener2());

        serverRequest.setRetryPolicy(new DefaultRetryPolicy(
                Helper.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(MemberActivity.this).addToRequestQueue(serverRequest);
    }

    private Response.Listener<LoginObject> createRequestSuccessListener2() {
        return new Response.Listener<LoginObject>() {
            @Override
            public void onResponse(LoginObject response) {
                try {
                    Log.d(TAG, "Json Response " + response.getLoggedIn());
                    if(response.getLoggedIn().equals("1")){
                        //save login data to a shared preference
                        String userData = ((CustomApplication)getApplication()).getGsonObject().toJson(response);
                        ((CustomApplication)getApplication()).getShared().setUserData(userData);

                        // navigate to restaurant home
                        Intent loginIntent = new Intent(MemberActivity.this, MainActivity.class);
                        startActivity(loginIntent);
                    }else if(response.getLoggedIn().equals("0")){
                        Helper.displayErrorMessage(MemberActivity.this, "User registration failed - Email address already exist");
                    }else{
                        Toast.makeText(MemberActivity.this, R.string.failed_registration, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private Response.ErrorListener createRequestErrorListener2() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
    }

    private void getMemberFromRemoteServer(){
        GsonRequest<LoginObject[]> serverRequest = new GsonRequest<LoginObject[]>(
                Request.Method.GET,
                Helper.PATH_TO_MEMBER,
                LoginObject[].class,
                createRequestSuccessListener(),
                createRequestErrorListener());

        serverRequest.setRetryPolicy(new DefaultRetryPolicy(
                Helper.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstance(this).addToRequestQueue(serverRequest);
    }

    private Response.Listener<LoginObject[]> createRequestSuccessListener() {
        return new Response.Listener<LoginObject[]>() {
            @Override
            public void onResponse(LoginObject[] response) {
                try {
                    Log.d(TAG, "Json Response " + response.toString());
                    if(response.length > 0){
                        //display restaurant menu information
                        List<LoginObject> usrObject = new ArrayList<>();
                        for(int i = 0; i < response.length; i++){
                            Log.d(TAG, "Menu name " + response[i].getUsername());
                            usrObject.add(new LoginObject(response[i].getId(), response[i].getKodeuser(), response[i].getUsername(), response[i].getEmail(), response[i].getPassword(), response[i].getRole(),response[i].getAddress(),response[i].getPhone(),""));
                        }
                        UserAdapterServer mAdapter = new UserAdapterServer(MemberActivity.this, usrObject);
                        recyclerView.setAdapter(mAdapter);

                    }else{
                        Toast.makeText(MemberActivity.this, "Restaurant menu is empty", Toast.LENGTH_LONG).show();
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
    }*/
}

