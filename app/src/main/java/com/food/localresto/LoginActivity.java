package com.food.localresto;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.LoginObject;
import com.food.localresto.util.CustomApplication;
import com.food.localresto.util.Helper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private TextView errorDisplay;
    private TextView signInformation;
    private EditText emailInput;
    private EditText passwordInput;

    //private Query mDatabase;
    private SqliteDatabase mDatabase;

    private ArrayList<HashMap<String, String>> contactList;
    private String usernm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        if(null != actionBar){
            actionBar.hide();
        }

        isUserLoggedIn();

        errorDisplay = (TextView)findViewById(R.id.login_error);
        signInformation = (TextView)findViewById(R.id.sign_in_notice);
        signInformation.setText(Helper.NEW_ACCOUNT);

        emailInput = (EditText)findViewById(R.id.email);
        passwordInput = (EditText)findViewById(R.id.password);

        Button signUpButton = (Button)findViewById(R.id.sign_up);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent signInIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                //startActivity(signInIntent);
            }
        });

        Button loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enteredEmail = emailInput.getText().toString().trim();
                String enteredPassword = passwordInput.getText().toString().trim();

                if(TextUtils.isEmpty(enteredEmail) || TextUtils.isEmpty(enteredPassword)){
                    Helper.displayErrorMessage(LoginActivity.this, getString(R.string.fill_all_fields));
                }
                if(!Helper.isValidEmail(enteredEmail)){
                    Helper.displayErrorMessage(LoginActivity.this, getString(R.string.invalid_email));
                }

                //make server call for user authentication
                //authenticateUserInRemoteServer(enteredEmail, enteredPassword);
                authenticateUserLocal(enteredEmail, enteredPassword);
            }
        });

        contactList = new ArrayList<>();
    }

    private void authenticateUserLocal(String username, String password){
        Map<String, String> params = new HashMap<String,String>();
        params.put(Helper.USERNAME, username);
        params.put(Helper.PASSWORD, password);

        //mDatabase = new Query(this);
        mDatabase = SqliteDatabase.getInstance(this);
        List<LoginObject> loginObject = mDatabase.signin(username,password);

        //calculateOrder(orderHistory);
        //CheckoutAdapter mAdapter = new CheckoutAdapter(OrderListActivity.this, orderHistory);
        //orderRecyclerView.setAdapter(mAdapter);
        // looping through All Contacts
        if(loginObject.size() > 0) {
            String userData = ((CustomApplication)getApplication()).getGsonObject().toJson(loginObject);
            ((CustomApplication)getApplication()).getShared().setUserData(userData);

            // navigate to restaurant home
            Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(loginIntent);
        }

    }


    private void isUserLoggedIn(){
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

                Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intentMain);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

/*
        //String userData = loginObject.toString();
        try {
            JSONObject jsonObj = new JSONObject(storedUser);

            // Getting JSON Array node
            JSONArray contacts = jsonObj.getJSONArray("contacts");

            if(loginObject.size() > 0) {
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    int id = loginObject.get(i).getId();
                    String usernm = c.getString("username");
                    String email = c.getString("email");
                    String address = c.getString("address");
                    String phone = c.getString("phone");
                    String loggedIn = c.getString("loggedIn");

                    // tmp hash map for single contact
                    HashMap<String, String> contact = new HashMap<>();

                    // adding each child node to HashMap key => value
                    contact.put("id", String.valueOf(id));
                    contact.put("name", usernm);
                    contact.put("email", email);
                    contact.put("address", address);
                    contact.put("phone", phone);
                    contact.put("loggedIn", loggedIn);

                    // adding contact to contact list
                    contactList.add(contact);
                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }*/


    }
}
