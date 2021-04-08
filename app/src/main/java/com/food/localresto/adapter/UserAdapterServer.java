package com.food.localresto.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.food.localresto.R;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.LoginObject;
import com.food.localresto.util.Helper;

import java.util.List;

public class UserAdapterServer extends RecyclerView.Adapter<UserViewHolder>{

    private Context context;
    private List<LoginObject> loginObject;
    //private Query mDatabase;
    private SqliteDatabase mDatabase;

    public UserAdapterServer(Context context, List<LoginObject> loginObject) {
        this.context = context;
        this.loginObject = loginObject;
        //mDatabase = new Query(context);
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_layout, parent, false);
        return new UserViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final LoginObject usrObject = loginObject.get(position);
        final int id = usrObject.getId();
        //holder.categoryName.setText(usrObject.getMenu_name()); nasir
        //holder.categoryImage.setText(usrObject.getMenu_name());

        // use Glide to download and display the category image.
        /* nasir
        String serverImagePath = Helper.PUBLIC_FOLDER + usrObject.getMenu_image();
        Glide.with(context).load(serverImagePath).diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter().override(300, 300).into(holder.categoryImage);
        */


        /*holder.categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //nasir
                String[] str = new String[loginObject.size()];
                for (int i = 0; i < loginObject.size(); i++) {
                    str[i] = loginObject.get(i).getMenu_name();
                }
                //

                Intent categoryIntent = new Intent(context, SingleMenuCategoryActivity.class);
                categoryIntent.putExtra("CATEGORY_NAME", usrObject.getMenu_name());
                categoryIntent.putExtra("CATEGORY_ID", id);

                categoryIntent.putExtra("myArr",str); //nasir

                context.startActivity(categoryIntent);
            }
        });*/
        Integer posisi = position +1;

        holder.userName.setText(posisi+". "+usrObject.getUsername());

        holder.editMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(usrObject);
            }
        });

        holder.deleteMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                //LoginObject item = recentSongs.get(Recyclerview.getChildAdapterPosition(ChildView));
                //Gson gson = ((CustomApplication) ((Activity) getContext()).getApplication()).getGsonObject();
                //final String objectToString = gson.toJson(item);

                mDatabase.deleteUser(usrObject.getId());

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return loginObject.size();
    }


    public static int getResourseId(Context context, String pVariableName, String pResourcename, String pPackageName) throws RuntimeException {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Resource ID.", e);
        }
    }

    private void editTaskDialog(final LoginObject product){
        LayoutInflater inflater = LayoutInflater.from(context);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    //mDatabase.updateProduct(new MenuCategoryObject(kode, jenis, name));
                    //refresh the activity

                    //postEditUserToRemoteServer(jenis, name, kode);

                    long result = mDatabase.updateUser(new LoginObject(product.getId(),enteredUsername,enteredPassword,"1",enteredEmail,enteredAddress,enteredPhoneNumber,"1"));

                    if (result == -1) {
                        Helper.displayErrorMessage(context, "Failed to update User desc");
                    } else {
                        ((Activity)context).finish();
                        context.startActivity(((Activity)context).getIntent());
                    }



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
