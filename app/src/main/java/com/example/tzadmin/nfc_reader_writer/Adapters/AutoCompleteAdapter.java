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

import com.example.tzadmin.nfc_reader_writer.Database.Database;
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
    private ArrayList<User> usersAll;
    private ArrayList<User> suggestions;

    public AutoCompleteAdapter(Context context, ArrayList<User> usersList) {
        mContext = context;
        users = usersList;
        usersAll = (ArrayList<User>) users.clone();
        suggestions = new ArrayList<>();

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
//            AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
            ((TextView)view).setText(users.get(position).cFirstName + " " +
                    users.get(position).cLastName + " " +
                        users.get(position).cSurname);
        } else {
            view = convertView;
        }

        return view;
    }


    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            User u = (User) resultValue;
            String str = u.cFirstName + " " + u.cLastName + " " + u.cSurname;
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                usersAll = Database.selectUsers();
                for (User u : usersAll) {
                    if((u.cFirstName + " " + u.cLastName + " " + u.cSurname).toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(u);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<User> filteredList = (ArrayList<User>) results.values;
            if(results != null && results.count > 0) {
                users.clear();
                for (User c : filteredList) {
                    users.add(c);
                }
                notifyDataSetChanged();
            }
        }
    };


    @Override
    public Filter getFilter() {
        return nameFilter;
    }
}
