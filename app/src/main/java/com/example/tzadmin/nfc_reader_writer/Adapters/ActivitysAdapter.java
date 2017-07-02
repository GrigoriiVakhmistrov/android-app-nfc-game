package com.example.tzadmin.nfc_reader_writer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tzadmin.nfc_reader_writer.Fonts.SingletonFonts;
import com.example.tzadmin.nfc_reader_writer.Models.Event;
import com.example.tzadmin.nfc_reader_writer.R;

import java.util.ArrayList;

/**
 * Created by forz on 27.06.17.
 */

public class ActivitysAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Event> activityses;

    public  ActivitysAdapter (Context context, ArrayList<Event> activityses) {
        this.context = context;
        this.activityses = activityses;
    }

    @Override
    public int getCount() {
        return activityses.size();
    }

    @Override
    public Object getItem(int position) {
        return activityses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View activitys;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            activitys = inflater.inflate(R.layout.activity_item, null);

            TextView name = (TextView) activitys.findViewById(R.id.tv_activity_item_name);
            TextView price = (TextView) activitys.findViewById(R.id.tv_activity_item_price);

            name.setTypeface(SingletonFonts.getInstanse(context).getKarlson());
            price.setTypeface(SingletonFonts.getInstanse(context).getKarlson());

            name.setText(activityses.get(position).name);
            price.setText(activityses.get(position).price.toString());

        } else {
            activitys = convertView;
        }

        return activitys;
    }
}
