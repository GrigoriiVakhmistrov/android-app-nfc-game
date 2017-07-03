package com.example.tzadmin.nfc_reader_writer.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.tzadmin.nfc_reader_writer.Fonts.SingletonFonts;
import com.example.tzadmin.nfc_reader_writer.Models.User;

import java.util.ArrayList;

/**
 * Created by forz on 13.06.17.
 */

public class AutoCompleteAdapter extends BaseAdapter implements Filterable {
    LayoutInflater inflater;
    Context context;
    private ArrayList<User> users;
    private ArrayList<User> usersAll;
    private ArrayList<User> suggestions;

    public AutoCompleteAdapter(Context context, ArrayList<User> usersList) {
        users = usersList;
        if (users == null) users = new ArrayList<>();
        usersAll = (ArrayList<User>) users.clone();
        suggestions = new ArrayList<>();

        this.context = context;
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
            ((TextView)view).setTypeface(SingletonFonts.getInstanse(context).getKarlson());
            ((TextView)view).setText(users.get(position).lastname + " " +
                    users.get(position).firstname + " " +
                        users.get(position).patronymic);
        } else {
            view = convertView;
        }

        return view;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            User u = (User) resultValue;
            return u.lastname + " " + u.firstname + " " + u.patronymic;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                usersAll = (ArrayList<User>)new User().selectAll();
                if(usersAll == null) {
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                    return filterResults;
                }

                for (User u : usersAll) {
                    /*
                    if((u.cFirstName + " " + u.cLastName + " " + u.cSurname).toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(u);
                    }
                    */
                    if((u.firstname).toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(u);
                    }else if((u.lastname).toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(u);
                    }else if((u.patronymic).toLowerCase().startsWith(constraint.toString().toLowerCase())){
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
        protected void publishResults(CharSequence constraint, @NonNull FilterResults results) {
            ArrayList<User> filteredList = (ArrayList<User>) results.values;
            if(results.count > 0) {
                users.clear();
                users.addAll(filteredList);
                notifyDataSetChanged();
            }
        }
    };

    @Override
    public Filter getFilter() {
        return nameFilter;
    }
}
