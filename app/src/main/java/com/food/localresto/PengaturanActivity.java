package com.food.localresto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.RestaurantObject;
import com.food.localresto.util.CustomApplication;
import com.food.localresto.util.CustomSharedPreference;
import com.food.localresto.util.Helper;

import java.util.List;

/*
public class PengaturanActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pengaturan_activity);


        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new PengaturanFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class PengaturanFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}
*/

/*public class PengaturanActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private Button applyTextButton;
    private Button saveButton;
    private Switch switch1;

    public static final String SHARED_PREFS = "SHARED_PREFERENCE";
    public static final String TEXT = "text";
    public static final String SWITCH1 = "switch1";

    private String text;
    private boolean switchOnOff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pengaturan_activity);

        textView = (TextView) findViewById(R.id.textview);
        editText = (EditText) findViewById(R.id.edittext);
        applyTextButton = (Button) findViewById(R.id.apply_text_button);
        saveButton = (Button) findViewById(R.id.save_button);
        switch1 = (Switch) findViewById(R.id.switch1);

        applyTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(editText.getText().toString());
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        loadData();
        updateViews();
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, textView.getText().toString());
        editor.putBoolean(SWITCH1, switch1.isChecked());

        editor.apply();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "");
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);
    }

    public void updateViews() {
        textView.setText(text);
        switch1.setChecked(switchOnOff);
    }
}*/

public class PengaturanActivity extends AppCompatActivity {

    private static final String TAG = ProfilePershActivity.class.getSimpleName();

    public static final String SHARED_PREFS = "SHARED_PREFERENCE";
    public static final String PAJAK = "pajak";
    public static final String DISC = "disc";
    public static final String FOOTER = "footer";


    private TextView setPajak, setDisc, setFooter;
    private CustomSharedPreference shared;
    private String pajak,disc,footer_struk;
    private RestaurantObject resto;
    //private Query mDatabase;
    private SqliteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pengaturan_activity);

        setTitle("Setting Struk");

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

        //ImageView profileImage = (ImageView)findViewById(R.id.profile_image);
        setPajak = (TextView) findViewById(R.id.set_pajak);
        setDisc = (TextView)findViewById(R.id.set_disc);
        setFooter = (TextView)findViewById(R.id.set_footer);

        //email ="admin@testing.com";
        //restaurant_description ="good menu delivered";
        //restaurant_opening_hours ="10:00 - 23:00";

        loadData();
        updateViews();

        Button editSet = (Button)findViewById(R.id.edit_set);
        editSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(pajak, disc, footer_struk);

            }
        });

    }



    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        pajak = sharedPreferences.getString(PAJAK, "");
        disc = sharedPreferences.getString(DISC, "");
        footer_struk = sharedPreferences.getString(FOOTER, "");
    }

    public void updateViews() {
        setPajak.setText(pajak);
        setDisc.setText(disc);
        setFooter.setText(footer_struk);

    }

    private void editTaskDialog(String p, String d, String f){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_set_layout, null);

        final EditText pajak = (EditText)subView.findViewById(R.id.pajak);
        final EditText disc = (EditText)subView.findViewById(R.id.disc);
        final EditText footer = (EditText)subView.findViewById(R.id.footer_struk);

        pajak.setText(p);
        disc.setText(d);
        footer.setText(f);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Setting");
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

                String enteredPajak = pajak.getText().toString().trim();
                String enteredDisc = disc.getText().toString().trim();
                String enteredFooter = footer.getText().toString().trim();

                if(TextUtils.isEmpty(enteredPajak) || TextUtils.isEmpty(enteredDisc)){
                    Toast.makeText(PengaturanActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{

                    //((CustomApplication) getApplication()).getShared().saveRestaurantInformation("");

                    saveData(enteredPajak, enteredDisc, enteredFooter);

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


    public void saveData(String p, String d, String f) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PAJAK, p);
        editor.putString(DISC, d);
        editor.putString(FOOTER, f);

        editor.apply();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

}

