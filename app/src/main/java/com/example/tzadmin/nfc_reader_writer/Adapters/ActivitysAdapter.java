package com.example.tzadmin.nfc_reader_writer.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.tzadmin.nfc_reader_writer.Models.Activitys;
import com.example.tzadmin.nfc_reader_writer.Models.Shop;

import java.util.ArrayList;

/**
 * Created by forz on 27.06.17.
 */

public class ActivitysAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Activitys> activityses;

    public  ActivitysAdapter (Context context, ArrayList<Activitys> activityses) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
