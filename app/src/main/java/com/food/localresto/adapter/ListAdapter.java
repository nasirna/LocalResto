package com.food.localresto.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.food.localresto.R;
import com.food.localresto.entities.MenuItemObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<MenuItemObject> implements DoubleStickHeadersListAdapter{
    private static final int LEVEL0_HEADERS_NUMBER = 10;
    private static final int LEVEL1_HEADERS_NUMBER = 4;
    private static final int LEVEL2_HEADERS_NUMBER = 5;

    private static final int BG_COLOR_LEVEL_0 = android.R.color.holo_purple;
    private static final int BG_COLOR_LEVEL_1 = android.R.color.holo_green_light;
    private static final int BG_COLOR_LEVEL_2 = android.R.color.white;
    private int listItemLayout;

    //private LinearLayout catNm;
    private ListView listview;
    private ArrayList<MenuItemObject> dataSet;
    Context mContext;


    //nasir nb
    public File file;
    public File destFile;
    public static final String IMAGE_DIRECTORY = "ImageResto";


    public ListAdapter(Context context, int layoutId, List<MenuItemObject> itemList) {
        super(context, layoutId, itemList);
        listItemLayout = layoutId;
        //genListData();
    }

    /*public void genListData() {

        for (char i = 0; i < LEVEL0_HEADERS_NUMBER; i++) {
            ListItem level0Header = new ListItem(DoubleStickyHeaderListView.HEADER_LEVEL_0, String.valueOf((char)('A' + i)));
            add(level0Header);

            for (char j = 0; j < LEVEL1_HEADERS_NUMBER; j++) {
                ListItem level1Header = new ListItem(DoubleStickyHeaderListView.HEADER_LEVEL_1,
                        level0Header.text.toUpperCase(Locale.ENGLISH) + " - " + String.valueOf((char)('a' + j)));
                add(level1Header);

                for (int k = 0; k < LEVEL2_HEADERS_NUMBER; k++) {
                    ListItem level2Header = new ListItem(DoubleStickyHeaderListView.HEADER_LEVEL_2, level1Header.text + " - " + k);
                    add(level2Header);
                }
            }

        }
    }*/

/*
    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
*/


  /*  @Override
    public void onClick(AdapterView<?> parent, View v, int position, long id) {
        // TODO Auto-generated method stub
        //ListItem item  = (ListItem) getListView().getAdapter().getItem(position);
        MenuItemObject item = (MenuItemObject) parent.getAdapter().getItem(position);

        if (item != null) {
            Toast.makeText(v.getContext(), "Item " + position + ": level " + item.getMenu_id() + ", text: " + item.getItem_name(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(v.getContext(), "Item " + position + ": not exist", Toast.LENGTH_SHORT).show();
        }

        //Intent foodIntent = new Intent(getActivity(), FoodActivity.class);
        //foodIntent.putExtra("MENU_ITEM", objectToString);
        //getActivity().startActivity(foodIntent);
    }*/


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

/*        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTextColor(Color.BLACK);
        view.setTag("" + position);
        int level = getHeaderLevel(position);
        if (level == DoubleStickyHeaderListView.HEADER_LEVEL_0) {
            view.setBackgroundColor(parent.getResources().getColor(BG_COLOR_LEVEL_0));
        } else if (level == DoubleStickyHeaderListView.HEADER_LEVEL_1) {
            view.setBackgroundColor(parent.getResources().getColor(BG_COLOR_LEVEL_1));
        } else {
            view.setBackgroundColor(parent.getResources().getColor(BG_COLOR_LEVEL_2));
        }
        return view;*/
        // Get the data item for this position

       /* ListItem item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(listItemLayout, parent, false);
            viewHolder.item = (TextView) convertView.findViewById(R.id.food_name);
            convertView.setTag(viewHolder); // view lookup cache stored in tag
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.item.setText(item.text);
        viewHolder.item.setTextColor(Color.BLACK);
        //convertView.setTag("" + position);

        int level = getHeaderLevel(position);
        if (level == DoubleStickyHeaderListView.HEADER_LEVEL_0) {
            convertView.setBackgroundColor(parent.getResources().getColor(BG_COLOR_LEVEL_0));
        } else if (level == DoubleStickyHeaderListView.HEADER_LEVEL_1) {
            convertView.setBackgroundColor(parent.getResources().getColor(BG_COLOR_LEVEL_1));
        } else {
            convertView.setBackgroundColor(parent.getResources().getColor(BG_COLOR_LEVEL_2));
        }
        */


        // Get the data item for this position

        MenuItemObject dataModel = getItem(position);
        final int id = dataModel.getMenu_item_id();
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.view_list_item, parent, false);
            viewHolder.foodName = (TextView) convertView.findViewById(R.id.food_name);
            //viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            //viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            //viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            viewHolder.foodImage = (ImageView)convertView.findViewById(R.id.food_image);
            //foodName = (TextView)itemView.findViewById(R.id.food_name);
            viewHolder.foodPrice = (TextView)convertView.findViewById(R.id.food_price);
            viewHolder.foodDetails = (ImageView) convertView.findViewById(R.id.food_detail);

            viewHolder.foodCat = (TextView)convertView.findViewById(R.id.food_cat);

            viewHolder.catNm = (LinearLayout)convertView.findViewById(R.id.cat_nm);
            viewHolder.catNm.setVisibility(View.GONE);

            viewHolder.catNm2 = (LinearLayout)convertView.findViewById(R.id.cat_nm2);
            viewHolder.catNm2.setVisibility(View.GONE);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        // Populate the data into the template view using the data object
        viewHolder.foodCat.setText(dataModel.getItem_name());

        viewHolder.foodName.setText(dataModel.getItem_name());
        //viewHolder.foodPrice.setText("$" + String.valueOf(dataModel.getItem_price()) + "0");
        viewHolder.foodPrice.setText("Rp " + String.format("%1$,.0f", Float.valueOf(dataModel.getItem_price()) ));

        viewHolder.foodName.setTextColor(Color.BLACK);
        //convertView.setTag("" + position);

        //String serverImagePath = Helper.PUBLIC_FOLDER + dataModel.getItem_picture();

        String filename = dataModel.getImage();
        //String imagePath = Environment.getExternalFilesDir() + "/" + filename;
        file = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_DIRECTORY);
        destFile = new File(file, filename);

        //Glide.with(getContext()).load(destFile).diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter().override(150, 150).into(viewHolder.foodImage);
       /* Glide.with(getContext())
                .load(destFile)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .override(150, 150)
                .into(viewHolder.foodImage);*/
        Glide.with(getContext())
                .load(destFile)
                //.signature(new ObjectKey(System.currentTimeMillis()))
                //.skipMemoryCache(true)
                //.diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(150, 150)
                .circleCrop()
                .into(viewHolder.foodImage);

        /*Glide.with(getContext()) //1
                .load(destFile)
                //.placeholder(R.drawable.ic_profile_placeholder)
                //.error(R.drawable.ic_profile_placeholder)
                .skipMemoryCache(false) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .centerCrop()
                .into(viewHolder.foodImage);*/

        //byte[] recordImage = dataModel.getImage();
        //Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage, 0, recordImage.length);
        //viewHolder.foodImage.setImageBitmap(bitmap);

        viewHolder.foodCat.setText(dataModel.getItem_name());

        int level = getHeaderLevel(position);
        if (level == DoubleStickyHeaderListView.HEADER_LEVEL_0) {
            convertView.setBackgroundColor(parent.getResources().getColor(BG_COLOR_LEVEL_0));
            viewHolder.catNm.setVisibility(View.VISIBLE);
            viewHolder.catNm2.setVisibility(View.GONE);
        } else if (level == DoubleStickyHeaderListView.HEADER_LEVEL_1) {
            convertView.setBackgroundColor(parent.getResources().getColor(BG_COLOR_LEVEL_1));
            viewHolder.catNm.setVisibility(View.VISIBLE);
            viewHolder.catNm2.setVisibility(View.GONE);
        } else {
            convertView.setBackgroundColor(parent.getResources().getColor(BG_COLOR_LEVEL_2));
            viewHolder.catNm.setVisibility(View.GONE);
            viewHolder.catNm2.setVisibility(View.VISIBLE);
        }

        //Gson gson = ((CustomApplication)((Activity)getContext()).getApplication()).getGsonObject();
        //final String objectToString = gson.toJson(dataModel);


  /*      convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent foodIntent = new Intent(getContext(), FoodActivity.class);
                    foodIntent.putExtra("MENU_ITEM", objectToString);
                    getContext().startActivity(foodIntent);


            }
        });*/


        //listview = (ListView) convertView.findViewById(R.id.listView);

        /*listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuItemObject item = (MenuItemObject) parent.getAdapter().getItem(position);

                if (mTwoPane) {
                    if (item.getMenu_id() > 1) {
*//*                        if (item != null) {
                            Toast.makeText(v.getContext(), "Item " + position + ": level " + item.getMenu_id() + ", text: " + item.getItem_name(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), "Item " + position + ": not exist", Toast.LENGTH_SHORT).show();
                        }*//*

                        buttonListener.onButtonPressed(item.getItem_name());
                    }
                } else {
                    Intent foodIntent = new Intent(getContext(), FoodActivity.class);
                    foodIntent.putExtra("MENU_ITEM", objectToString);
                    getContext().startActivity(foodIntent);
                }
            }
        });*/

        //listview = (ListView)convertView.findViewById(R.id.listView);


        /*listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // TODO Auto-generated method stub
                //ListItem item  = (ListItem) getListView().getAdapter().getItem(position);
                MenuItemObject item = (MenuItemObject) parent.getAdapter().getItem(position);

*//*                if (item != null) {
                    Toast.makeText(v.getContext(), "Item " + position + ": level " + item.getMenu_id() + ", text: " + item.getItem_name(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "Item " + position + ": not exist", Toast.LENGTH_SHORT).show();
                }*//*

                if (mTwoPane) {
                    if (item.getMenu_id() > 1) {
                        if (item != null) {
                            Toast.makeText(v.getContext(), "Item " + position + ": level " + item.getMenu_id() + ", text: " + item.getItem_name(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), "Item " + position + ": not exist", Toast.LENGTH_SHORT).show();
                        }

                        buttonListener.onButtonPressed(item.getItem_name());
                    }
                } else {
                    Intent foodIntent = new Intent(getContext(), FoodActivity.class);
                    foodIntent.putExtra("MENU_ITEM", objectToString);
                    getContext().startActivity(foodIntent);
                }

            }
        });*/

        // Return the completed view to render on screen
        return convertView;

    }

    @Override
    public int getHeaderLevel(int position) {
        return getItem(position).getMenu_id();
    }


    // The ViewHolder, only one item for simplicity and demonstration purposes, you can put all the views inside a row of the list into this ViewHolder
    public static class ViewHolder {
        ImageView foodImage;
        TextView foodName;
        TextView foodPrice;
        ImageView foodDetails;
        LinearLayout catNm;
        TextView foodCat;
        LinearLayout catNm2;
    }

}

