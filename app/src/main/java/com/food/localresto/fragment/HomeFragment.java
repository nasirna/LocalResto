package com.food.localresto.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.food.localresto.R;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.RestaurantObject;
import com.food.localresto.util.CustomApplication;

import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private TextView restaurantName, restaurantAddress, restaurantOpeningHour, restaurantDescription,
            address, restaurantEmail, restaurantPhone;

    private LinearLayout generalWrapper;

    //private Query mDatabase;
    private SqliteDatabase mDatabase;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.restaurant_home));
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        restaurantName = (TextView)view.findViewById(R.id.restaurant_name);
        restaurantAddress = (TextView)view.findViewById(R.id.restaurant_address);
        restaurantOpeningHour = (TextView)view.findViewById(R.id.restaurant_opening_hours);
        restaurantDescription = (TextView)view.findViewById(R.id.restaurant_description);
        address = (TextView)view.findViewById(R.id.address_info);
        restaurantEmail = (TextView)view.findViewById(R.id.restaurant_email_address);
        restaurantPhone = (TextView)view.findViewById(R.id.phone_number);

        generalWrapper = (LinearLayout)view.findViewById(R.id.general_wrapper);

        hideView();
        mDatabase = SqliteDatabase.getInstance(getActivity());

        getRestaurantInformationFromLocal();
        return view;
    }


    private void getRestaurantInformationFromLocal(){

        //mDatabase = new Query(getActivity());
        //List<RestaurantObject> restoObject = mDatabase.namaResto("9999");
        List<RestaurantObject> restoObject = mDatabase.namaResto("9999");
        if(restoObject.size() > 0) {

            for (int i = 0; i < restoObject.size(); i++) {
                showView();
                restaurantName.setText(restoObject.get(i).getName());
                restaurantAddress.setText(restoObject.get(i).getAddress());
                restaurantOpeningHour.setText(restoObject.get(i).getOpening_time());
                restaurantDescription.setText(restoObject.get(i).getDescription());
                address.setText(restoObject.get(i).getAddress());
                restaurantEmail.setText(restoObject.get(i).getEmail());
                restaurantPhone.setText(restoObject.get(i).getPhone());
                String nameAddress = restoObject.get(i).getName() + ":" + restoObject.get(i).getAddress()+ ":" + restoObject.get(i).getCity()+ ":" + restoObject.get(i).getProfinsi()+":" + restoObject.get(i).getPhone()+":" + restoObject.get(i).getKode();
                ((CustomApplication)getActivity().getApplication()).getShared().saveRestaurantInformation(nameAddress);
            }

        }
    }

    private void hideView(){
        generalWrapper.setVisibility(View.GONE);
    }

    private void showView(){
        generalWrapper.setVisibility(View.VISIBLE);
    }

}
