package com.example.tzadmin.nfc_reader_writer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tzadmin.nfc_reader_writer.Models.User;
import com.example.tzadmin.nfc_reader_writer.R;

import java.util.ArrayList;

/**
 * Created by forz on 13.06.17.
 */

public class AutoCompleteAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    LayoutInflater inflater;
    private ArrayList<User> users;

    public AutoCompleteAdapter(Context context, ArrayList<User> usersList) {
        mContext = context;
        users = usersList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, null);
            AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
            textView.setText(users.get(position).cFirstName +
                    users.get(position).cLastName +
                        users.get(position).cSurname);
        } else {
            view = convertView;
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
