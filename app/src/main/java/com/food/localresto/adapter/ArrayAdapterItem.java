package com.food.localresto.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.food.localresto.R;
import com.food.localresto.entities.ListItem;

import java.util.ArrayList;

public class ArrayAdapterItem extends ArrayAdapter<ListItem> {
    private final Context context;
    private final ArrayList<ListItem> listItemArrayList;

    public ArrayAdapterItem(Context context, ArrayList<ListItem> listItemArrayList) {

        super(context, R.layout.list_view_row_item, listItemArrayList);

        this.context = context;
        this.listItemArrayList = listItemArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*
         * The convertView argument is essentially a "ScrapView" as described is Lucas post
         * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
         * It will have a non-null value when ListView is asking you recycle the row layout.
         * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
         */
        /*if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        // object item based on the position

        // get the TextView and then set the text (item name) and tag (item ID) values
        TextView textViewItem = (TextView) convertView.findViewById(R.id.textViewItem);
        textViewItem.setText(objectItem.text);
        textViewItem.setTag(objectItem.level);*/
// 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater

        View rowView = null;
        if(!listItemArrayList.get(position).isGroupHeader()){
            rowView = inflater.inflate(R.layout.list_view_row_item, parent, false);

            // 3. Get icon,title & counter views from the rowView
            /*ImageView imgView = (ImageView) rowView.findViewById(R.id.item_icon);
            TextView titleView = (TextView) rowView.findViewById(R.id.item_title);
            TextView counterView = (TextView) rowView.findViewById(R.id.item_counter);

            // 4. Set the text for textView
            imgView.setImageResource(modelsArrayList.get(position).getIcon());
            titleView.setText(modelsArrayList.get(position).getTitle());
            counterView.setText(modelsArrayList.get(position).getCounter());*/

            TextView textViewItem = (TextView) rowView.findViewById(R.id.textViewItem);
            textViewItem.setText(listItemArrayList.get(position).text);
            textViewItem.setTag(listItemArrayList.get(position).level);
        }
        else{
            rowView = inflater.inflate(R.layout.group_header_item, parent, false);
            TextView titleView = (TextView) rowView.findViewById(R.id.header);
            titleView.setText(listItemArrayList.get(position).getText());

            titleView.setTextColor(Color.parseColor("#FF3E80F1"));

            // Set the item text style to bold
            titleView.setTypeface(titleView.getTypeface(), Typeface.BOLD);

            // Change the item text size
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);

        }

        // 5. retrn rowView
        return rowView;

    }
}
