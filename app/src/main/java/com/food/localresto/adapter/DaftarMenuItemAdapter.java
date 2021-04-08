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

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.food.localresto.R;
import com.food.localresto.database.SqliteDatabase;
import com.food.localresto.entities.MenuItemObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class DaftarMenuItemAdapter extends RecyclerView.Adapter<DaftarMenuItemAdapter.DaftarMenuItemViewHolder> {

    private RecyclerViewClickListener onClickListener;

    private Context context;
    private List<MenuItemObject> listMenus;

    private SqliteDatabase mDatabase;
    //private Query mDatabase;

    final int REQUEST_CODE_GALLERY = 999;

    private double subTotal;

    private ArrayList<String> names;

    ArrayList<MenuItemObject> mModel;

    ArrayList<MenuItemObject> user_list = new ArrayList<>();

    public static int menu_idnya;
    public static int posisinya;


    //nasir nb
    private File file;
    private File destFile;
    public static final String IMAGE_DIRECTORY = "ImageResto";

    public class DaftarMenuItemViewHolder extends RecyclerView.ViewHolder {


        public TextView name;
        public ImageView menuImage;
        public TextView foodPrice,jenisMenu;
        public ImageButton Overflow;
        //public ImageView deleteProduct;
        //public  ImageView editProduct;

        public DaftarMenuItemViewHolder(View itemView) {
            super(itemView);
            menuImage = (ImageView) itemView.findViewById(R.id.menu_image);
            name = (TextView) itemView.findViewById(R.id.menu_name);
            foodPrice = (TextView) itemView.findViewById(R.id.food_price);
            jenisMenu = (TextView) itemView.findViewById(R.id.order_item_extra);
            Overflow = (ImageButton) itemView.findViewById(R.id.overflow);
            //deleteProduct = (ImageView)itemView.findViewById(R.id.delete_product);
            //editProduct = (ImageView)itemView.findViewById(R.id.edit_product);

            Overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClick(v, getAdapterPosition());
                }
            });
        }
    }

    public DaftarMenuItemAdapter(Context context, List<MenuItemObject> listMenus, RecyclerViewClickListener  listener) {
        this.context = context;
        this.listMenus = listMenus;
        mDatabase = new SqliteDatabase(context);
        //mDatabase = new Query(context);
        this.onClickListener = listener;
    }

    @Override
    public DaftarMenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftarmenu_list_layout, parent, false);
        return new DaftarMenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DaftarMenuItemViewHolder holder, final int position) {
        final MenuItemObject singleProduct = listMenus.get(position);


        //byte[] recordImage = singleProduct.getImage();
        //Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage, 0, recordImage.length);
        //holder.menuImage.setImageBitmap(bitmap);

        String filename = singleProduct.getImage();
        //String imagePath = Environment.getExternalFilesDir() + "/" + filename;
        file = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_DIRECTORY);
        destFile = new File(file, filename);

        Glide.with((Activity)context)
                .load(destFile)
                .signature(new ObjectKey(System.currentTimeMillis()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(150, 150)
                .centerCrop()
                .into(holder.menuImage);
        //Glide.with(context).load(imageUrls.get(i).getImageUrl()).into(viewHolder.img);

        holder.name.setText(singleProduct.getItem_name());
        holder.jenisMenu.setText(singleProduct.getMenu_name());
        //holder.foodPrice.setText("Rp " + String.valueOf(singleProduct.getItem_price()) + "0");
        //holder.foodPrice.setText("Rp " + String.format("%1$,.0f", String.valueOf(singleProduct.getItem_price()) ) + "0");
        Log.d(TAG, "Nilai Harga Adapter: " + singleProduct.getItem_price());

        //subTotal = 777777.99;

        subTotal = singleProduct.getItem_price();
        Log.d(TAG, "Nilai Harga double: " + subTotal);


        holder.foodPrice.setText(String.format("%1$,.0f", Float.valueOf((float) subTotal) ));

        /*holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(singleProduct);
            }
        });

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteMenus(singleProduct.getId());

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });*/

        //nasir
        //String[] str = new String[categoryObject.size()];
        /*for (int i = 0; i < listMenus.size(); i++) {
            //names.set(i, listMenus.get(i).getMenu_name());
            names[i] = listMenus.get(i).getMenu_name();
        }*/
        //

        //Mengimplementasikan Menu Popup pada Overflow (ImageButton)



    }

    @Override
    public int getItemCount() {
        return listMenus.size();
    }

    public void setFilter(List<MenuItemObject> usersModel) {
        mModel = new ArrayList<>();
        mModel.addAll(usersModel);
        notifyDataSetChanged();
    }

    public void delMenuItemList(int position) {
        listMenus.remove(position);
        notifyItemRemoved(position);

        //if (listMenus.get(position).getMenu_item_id() == 0) {
        //    notifyItemRangeChanged(listMenus.get(position).getMenu_item_id(), listMenus.size());
        //}

        // notifyDataSetChanged();
        //update order in shared preference
        //Gson mGson = ((CustomApplication)((Activity)context).getApplication()).getGsonObject();
        //String updatedOrder = mGson.toJson(listMenus);
        //((CustomApplication)((Activity)context).getApplication()).getShared().updateListMenuItems(updatedOrder);
    }

    public void newMenuItemList(List<MenuItemObject> usersModel) {
        mModel = new ArrayList<>();
        mModel.addAll(usersModel);
        notifyDataSetChanged();
    }

    public void addMenuItemList(MenuItemObject item, int position) {
        mModel = new ArrayList<>();
        mModel.add(position, item);
        notifyItemInserted(position);
    }

    public void insertRowData(int position, MenuItemObject orderItem) {
        listMenus.add(position, orderItem);
        notifyItemInserted(position);
    }

    public void updateMenuItemList(int position, MenuItemObject orderItem) {
        //listMenus.add(position, orderItem);
        notifyItemChanged(position);
        notifyDataSetChanged();

    }

    /*@SuppressLint("StaticFieldLeak")
    public void getDataFromSQLiteNya() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listMenus.clear();
                List<MenuItemObject> allMenus = mDatabase.listMenuItemAll();
                listMenus.addAll(allMenus);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                notifyDataSetChanged();
            }
        }.execute();
    }*/

    /*private void showDialogDelete(final int idRecord, final int position) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder((Activity)context);
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure to delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    mDatabase.deleteMenuItem(idRecord);

                    //Menghapus Data pada List dari Posisi Tertentu
                    //String position2 = String.valueOf(NIM.indexOf(NIM));
                    listMenus.remove(position);
                    notifyItemRemoved(position);
                    //if (position2 == null) {
                    //    notifyItemRangeChanged(Integer.parseInt(position2), listMenus.size());
                    //}

                    Toast.makeText((Activity)context, "Delete successfully " + idRecord, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.e("error", e.getMessage());
                }

                //updateRecordList();
            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogDelete.show();
    }
*/
    public interface RecyclerViewClickListener {

        //void onClick(View v, int position);

        void OnClick(View v, int position);

        //void daysOnClick(View v, int position);
    }
}
