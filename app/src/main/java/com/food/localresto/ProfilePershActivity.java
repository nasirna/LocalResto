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


import androidx.appcompat.app.AppCompatActivity;

import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.RestaurantObject;
import com.food.localresto.util.CustomApplication;
import com.food.localresto.util.CustomSharedPreference;
import com.food.localresto.util.Helper;

import java.util.List;

public class ProfilePershActivity extends AppCompatActivity {

    private static final String TAG = ProfilePershActivity.class.getSimpleName();

    private CustomSharedPreference shared;
    private String usernm,address,city,propinsi,phone,email,restaurant_description,restaurant_opening_hours;
    private RestaurantObject resto;
    //private Query mDatabase;
    private SqliteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_persh);

        setTitle("Profile Perusahaan");
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
        List<RestaurantObject> restoObject = mDatabase.namaResto("9999");
        if(restoObject.size() > 0) {
            for (int i = 0; i < restoObject.size(); i++) {

                usernm = restoObject.get(i).getName();
                address = restoObject.get(i).getAddress();
                city = restoObject.get(i).getCity();
                propinsi =restoObject.get(i).getProfinsi();
                phone=restoObject.get(i).getPhone();
                email=restoObject.get(i).getEmail();
                restaurant_description=restoObject.get(i).getDescription();
                restaurant_opening_hours=restoObject.get(i).getOpening_time();

                //String nameAddress = restoObject.get(i).getName() + ":" + restoObject.get(i).getAddress()+ ":" + restoObject.get(i).getCity()+ ":" + restoObject.get(i).getProfinsi()+":" + restoObject.get(i).getPhone()+":" + restoObject.get(i).getKode();
                //((CustomApplication)getActivity().getApplication()).getShared().saveRestaurantInformation(nameAddress);
                //List<RestaurantObject> restoObject2 = new ArrayList<RestaurantObject>();

                ///restoObject2.add(new RestaurantObject(restoObject.get(i).getId(),restoObject.get(i).getKode(),usernm,restoObject.get(i).getDescription(),address,city,propinsi,restoObject.get(i).getEmail(),phone,restoObject.get(i).getOpening_time()));

                resto = new RestaurantObject(restoObject.get(i).getId(),restoObject.get(i).getKode(),usernm,restoObject.get(i).getDescription(),address,city,propinsi,restoObject.get(i).getEmail(),phone,restoObject.get(i).getOpening_time());
            }

        }


        Button editProfile = (Button)findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent editIntent = new Intent(getActivity(), EditProfileActivity.class);
                //startActivity(editIntent);
                // add new quick task
                editTaskDialog(resto);

            }
        });

        ImageView profileImage = (ImageView)findViewById(R.id.profile_image);
        TextView profileName = (TextView) findViewById(R.id.profile_name);
        TextView profileAddress = (TextView)findViewById(R.id.profile_address);
        TextView profileCity = (TextView)findViewById(R.id.profile_city);
        TextView profilePropinsi = (TextView)findViewById(R.id.profile_propinsi);
        TextView profilePhone = (TextView)findViewById(R.id.profile_phone_number);
        TextView profileEmail = (TextView)findViewById(R.id.profile_email);
        TextView profileRestoDesc = (TextView)findViewById(R.id.profile_restaurant_description);
        TextView profileRestoOpen = (TextView)findViewById(R.id.profile_restaurant_opening_hours);

        //email ="admin@testing.com";
        //restaurant_description ="good menu delivered";
        //restaurant_opening_hours ="10:00 - 23:00";

        profileName.setText(usernm);
        profileAddress.setText(address);
        profileCity.setText(city);
        profilePropinsi.setText(propinsi);
        profilePhone.setText(phone);
        profileEmail.setText(email);
        profileRestoDesc.setText(restaurant_description);
        profileRestoOpen.setText(restaurant_opening_hours);


    }


    private void editTaskDialog(final RestaurantObject product){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_persh_layout, null);

        final EditText usernm = (EditText)subView.findViewById(R.id.username);
        final EditText address = (EditText)subView.findViewById(R.id.address);
        final EditText city = (EditText)subView.findViewById(R.id.city);
        final EditText propinsi = (EditText)subView.findViewById(R.id.propinsi);
        final EditText phone = (EditText)subView.findViewById(R.id.phone_number);
        final EditText email = (EditText)subView.findViewById(R.id.email);
        final EditText restaurant_description = (EditText)subView.findViewById(R.id.restaurant_description);
        final EditText restaurant_opening_hours = (EditText)subView.findViewById(R.id.restaurant_opening_hours);


        if(product != null){
            //spinner.setText(product.getMenu_jenis());
            //kodeField.setText(String.valueOf(product.getId()));
            //nameField.setText(product.getUsername());
            //quantityField.setText(String.valueOf(product.getQuantity()));

            usernm.setText(product.getName());
            address.setText(product.getAddress());
            city.setText(product.getCity());
            propinsi.setText(product.getProfinsi());
            phone.setText(product.getPhone());
            email.setText(product.getEmail());
            restaurant_description.setText(product.getDescription());
            restaurant_opening_hours.setText(product.getOpening_time());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Persh");
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

        builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //final String jenis = spinner.getText().toString();
                //final int kode = Integer.parseInt(kodeField.getText().toString());
                //final String name = nameField.getText().toString();
                //final int quantity = Integer.parseInt(quantityField.getText().toString());

                String enteredUsername = usernm.getText().toString().trim();
                String enteredAddress = address.getText().toString().trim();
                String enteredCity = city.getText().toString().trim();
                String enteredPropinsi = propinsi.getText().toString();
                String enteredPhone = phone.getText().toString();
                String enteredEmail = email.getText().toString();
                String enteredRestoDesc = restaurant_description.getText().toString();
                String enteredRestoOpenTime = restaurant_opening_hours.getText().toString();

                if(TextUtils.isEmpty(enteredUsername) || TextUtils.isEmpty(enteredAddress)){
                    Toast.makeText(ProfilePershActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    //mDatabase.updateUser(new LoginObject(product.getId(),enteredUsername,enteredPassword,enteredEmail,enteredAddress,enteredPhoneNumber,"1"));

                    //String userData = ((CustomApplication)getApplication()).getGsonObject().toJson(loginObject);
                    //((CustomApplication)getApplication()).getShared().setUserData(userData);

                    ((CustomApplication) getApplication()).getShared().saveRestaurantInformation("");

                    //startActivityForResult(getIntent(), 888);
                    //Intent orderIntent = new Intent(ProfilePershActivity.this, ProfilePershActivity.class);
                    //startActivity(orderIntent);

                    resto = new RestaurantObject(product.getId(),product.getKode(),enteredUsername,enteredRestoDesc,enteredAddress,enteredCity,enteredPropinsi,enteredEmail,enteredPhone,enteredRestoOpenTime);
                    //long result = mDatabase.addOrderItem(params);
                    long result = mDatabase.updateResto(resto);


                    if (result == -1) {
                        Helper.displayErrorMessage(ProfilePershActivity.this, "Failed to update User desc");
                    } else {
                        //String nameAddress = resto.getName() + ":" + resto.getAddress()+ ":" + resto.getCity()+ ":" + resto.getProfinsi()+":" + resto.getPhone();
                        //((CustomApplication) getApplication()).getShared().saveRestaurantInformation(nameAddress);
                        String restoData = ((CustomApplication)getApplication()).getGsonObject().toJson(resto);
                        ((CustomApplication)getApplication()).getShared().saveRestaurantInformation(restoData);
                    }

                    //Navigate to login page
                    //Intent loginPageIntent = new Intent(ProfilePershActivity.this, LoginActivity.class);
                    //startActivity(loginPageIntent);
                    //finish();
                    startActivity(getIntent());
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

