package com.food.localresto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.LoginObject;
import com.food.localresto.util.CustomApplication;
import com.food.localresto.util.CustomSharedPreference;
import com.food.localresto.util.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileUserActivity extends AppCompatActivity {

    private static final String TAG = ProfileUserActivity.class.getSimpleName();

    private CustomSharedPreference shared;
    private String usernm,password,role,email,address,phone,loggedIn;
    private LoginObject user;
    //private Query mDatabase;
    private SqliteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_user);

        setTitle("Profile User");
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*ActionBar actionBar = getSupportActionBar();
        if(null != actionBar){
            actionBar.hide();
        }

        Button startButton = (Button)findViewById(R.id.start_app);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAppIntent = new Intent(IntroActivity.this, LoginOptionActivity.class);
                startActivity(startAppIntent);
            }
        });*/


        //mDatabase = new Query(this);
        mDatabase = SqliteDatabase.getInstance(this);

        Button editProfile = (Button)findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent editIntent = new Intent(getActivity(), EditProfileActivity.class);
                //startActivity(editIntent);
                // add new quick task
                editTaskDialog(user);

            }
        });

        shared = ((CustomApplication) getApplication()).getShared();
        //user = gson.fromJson(shared.getUserData(), LoginObject.class);

        //String storedUserNya = shared.getUserData();

        try {
            JSONArray kontak = new JSONArray(shared.getUserData());

            for (int i = 0; i < kontak.length(); i++){
                JSONObject c = kontak.getJSONObject(i);
                int idna = c.getInt("id");
                usernm = c.getString("username");
                password = c.getString("password");
                role = c.getString("role");
                email = c.getString("email");
                address = c.getString("address");
                phone = c.getString("phone");
                loggedIn = c.getString("loggedIn");

                user = new LoginObject(idna,usernm,password,role,email,address,phone,loggedIn);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        ImageView profileImage = (ImageView)findViewById(R.id.profile_image);
        TextView profileName = (TextView) findViewById(R.id.profile_name);
        TextView profileEmail = (TextView)findViewById(R.id.profile_email);
        TextView profileAddress = (TextView)findViewById(R.id.profile_address);
        TextView profilePhone = (TextView)findViewById(R.id.profile_phone_number);

        profileName.setText(usernm);
        //profilePassword.setText(password);
        profileEmail.setText(email);
        profileAddress.setText(address);
        profilePhone.setText(phone);

    }


    private void editTaskDialog(final LoginObject product){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_member_layout, null);

        final EditText username = (EditText)subView.findViewById(R.id.username);
        final EditText password = (EditText)subView.findViewById(R.id.password);
        final EditText email = (EditText)subView.findViewById(R.id.email);
        final EditText address = (EditText)subView.findViewById(R.id.address);
        final EditText phoneNumber = (EditText)subView.findViewById(R.id.phone_number);


        if(product != null){
            //spinner.setText(product.getMenu_jenis());
            //kodeField.setText(String.valueOf(product.getId()));
            //nameField.setText(product.getUsername());
            //quantityField.setText(String.valueOf(product.getQuantity()));

            username.setText(product.getUsername());
            email.setText(product.getEmail());
            password.setText(product.getPassword());
            address.setText(product.getAddress());
            phoneNumber.setText(product.getPhone());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Member");
        builder.setView(subView);
        builder.create();

        // Spinner Drop down elements
        /*List<String> categories = new ArrayList<String>();
        categories.add("Makanan");
        categories.add("Minuman");*/

        // Creating adapter for spinner
        /*ArrayAdapter<String> dataAdapter = new ArrayAdapter <String>(context, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);*/

        builder.setPositiveButton("EDIT USER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //final String jenis = spinner.getText().toString();
                //final int kode = Integer.parseInt(kodeField.getText().toString());
                //final String name = nameField.getText().toString();
                //final int quantity = Integer.parseInt(quantityField.getText().toString());

                String enteredUsername = username.getText().toString().trim();
                String enteredEmail = email.getText().toString().trim();
                String enteredPassword = password.getText().toString().trim();
                String enteredAddress = address.getText().toString();
                String enteredPhoneNumber = phoneNumber.getText().toString();

                if(TextUtils.isEmpty(enteredUsername) || TextUtils.isEmpty(enteredEmail)){
                    Toast.makeText(ProfileUserActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    //mDatabase.updateUser(new LoginObject(product.getId(),enteredUsername,enteredPassword,enteredEmail,enteredAddress,enteredPhoneNumber,"1"));

                    //String userData = ((CustomApplication)getApplication()).getGsonObject().toJson(loginObject);
                    //((CustomApplication)getApplication()).getShared().setUserData(userData);

                    ((CustomApplication) getApplication()).getShared().setUserData("");

                    //startActivityForResult(getIntent(), 888);
                    //Intent orderIntent = new Intent(ProfilePershActivity.this, ProfilePershActivity.class);
                    //startActivity(orderIntent);

                    //long result = mDatabase.addOrderItem(params);
                    long result = mDatabase.updateUser(new LoginObject(product.getId(),enteredUsername,enteredPassword,"1",enteredEmail,enteredAddress,enteredPhoneNumber,"1"));

                    if (result == -1) {
                        Helper.displayErrorMessage(ProfileUserActivity.this, "Failed to update User desc");
                    } else {
                        //String nameAddress = resto.getName() + ":" + resto.getAddress()+ ":" + resto.getCity()+ ":" + resto.getProfinsi()+":" + resto.getPhone();
                        //((CustomApplication) getApplication()).getShared().saveRestaurantInformation(nameAddress);
                        String userData = ((CustomApplication)getApplication()).getGsonObject().toJson(product);
                        ((CustomApplication)getApplication()).getShared().setUserData(userData);
                    }

                    //Navigate to login page
                    Intent loginPageIntent = new Intent(ProfileUserActivity.this, LoginActivity.class);
                    startActivity(loginPageIntent);
                    finish();

                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

}