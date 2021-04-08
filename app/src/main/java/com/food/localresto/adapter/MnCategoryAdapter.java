package com.food.localresto.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.food.localresto.R;
import com.food.localresto.entities.MenuItemObject;
import com.food.localresto.entities.MnCategory;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.android.volley.VolleyLog.TAG;
import static java.security.AccessController.getContext;

public class MnCategoryAdapter extends ExpandableRecyclerAdapter<MnCategoryViewHolder, MnCategoryAdapter.MnViewHolder> {

    public static Context context;
    private RecyclerViewClickListener onClickListener;
    private LayoutInflater mInflator;
    private double subTotal;

    private File file;
    private File destFile;
    public static final String IMAGE_DIRECTORY = "ImageResto";

    public class MnViewHolder extends ChildViewHolder {

        //private TextView mMoviesTextView;
        private TextView name;
        private ImageView menuImage;
        private TextView foodPrice,jenisMenu;
        private ImageButton Overflow;

        public MnViewHolder(View itemView) {
            super(itemView);
            //mMoviesTextView = (TextView) itemView.findViewById(R.id.tv_movies);

            menuImage = (ImageView) itemView.findViewById(R.id.menu_image);
            name = (TextView) itemView.findViewById(R.id.menu_name);
            foodPrice = (TextView) itemView.findViewById(R.id.food_price);
            jenisMenu = (TextView) itemView.findViewById(R.id.order_item_extra);
            Overflow = (ImageButton) itemView.findViewById(R.id.overflow);

            /*Overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClick(v, getAdapterPosition(),"test");
                }
            });*/




        }

       public void bind(final MenuItemObject movies) {
            //mMoviesTextView.setText(movies.getItem_name());
            name.setText(movies.getItem_name());

            String filename = movies.getImage();
            file = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_DIRECTORY);
            destFile = new File(file, filename);

            //Glide.with(context).load(imageUrls.get(i).getImageUrl()).into(viewHolder.img);
           /*if((Activity)context!=null){
               Glide.with((Activity)context)
                       .load(destFile)
                       .signature(new ObjectKey(System.currentTimeMillis()))
                       .diskCacheStrategy(DiskCacheStrategy.ALL)
                       .override(150, 150)
                       .centerCrop()
                       .into(menuImage);
           }*/

           Glide.with(itemView.getContext())
                   .load(destFile)
                   .signature(new ObjectKey(System.currentTimeMillis()))
                   .diskCacheStrategy(DiskCacheStrategy.ALL)
                   .fitCenter()
                   .override(150, 150)
                   .into(menuImage);

           //Glide.with(itemView.getContext()).load(destFile).into(menuImage);

            jenisMenu.setText(movies.getMenu_name());
            //holder.foodPrice.setText("Rp " + String.valueOf(singleProduct.getItem_price()) + "0");
            //holder.foodPrice.setText("Rp " + String.format("%1$,.0f", String.valueOf(singleProduct.getItem_price()) ) + "0");
            Log.d(TAG, "Nilai Harga Adapter: " + movies.getItem_price());

            subTotal = movies.getItem_price();
            Log.d(TAG, "Nilai Harga double: " + subTotal);

            foodPrice.setText(String.format("%1$,.0f", Float.valueOf((float) subTotal) ));
            final int pos = movies.getMenu_item_id();
            Overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClick(v, getAdapterPosition(),pos);
                }
            });

        }
    }



    public MnCategoryAdapter(Context context, List<? extends ParentListItem> parentItemList, RecyclerViewClickListener listener) {
        super(parentItemList);
        mInflator = LayoutInflater.from(context);
        this.onClickListener = listener;
    }

    @Override
    public MnCategoryViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View movieCategoryView = mInflator.inflate(R.layout.mn_category_view, parentViewGroup, false);
        return new MnCategoryViewHolder(movieCategoryView);
    }

    @Override
    public MnViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        //View moviesView = mInflator.inflate(R.layout.mn_view, childViewGroup, false);
        View moviesView = mInflator.inflate(R.layout.daftarmenu_list_layout, childViewGroup, false);
        return new MnViewHolder(moviesView);
    }

    @Override
    public void onBindParentViewHolder(MnCategoryViewHolder movieCategoryViewHolder, int position, ParentListItem parentListItem) {
        MnCategory movieCategory = (MnCategory) parentListItem;
        movieCategoryViewHolder.bind(movieCategory);
    }

    @Override
    public void onBindChildViewHolder(MnViewHolder moviesViewHolder, int position, Object childListItem) {
        MenuItemObject movies = (MenuItemObject) childListItem;

        moviesViewHolder.bind(movies);
    }

    public interface RecyclerViewClickListener {

        //void onClick(View v, int position);

        void OnClick(View v, int position, int test);

        //void daysOnClick(View v, int position);
    }

}