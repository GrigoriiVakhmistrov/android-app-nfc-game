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

import com.example.tzadmin.nfc_reader_writer.Database.User;
import com.example.tzadmin.nfc_reader_writer.Fonts.SingletonFonts;
import com.example.tzadmin.nfc_reader_writer.R;

import java.util.ArrayList;

/**
 * Created by forz on 13.06.17.
 */

public class AutoCompleteAdapter extends BaseAdapter implements Filterable {
    LayoutInflater inflater;
    Context context;
    private ArrayList<User> users;
    private ArrayList<User> userAll;
    private ArrayList<User> suggestions;

    public AutoCompleteAdapter(Context context, ArrayList<User> userList) {
        users = userList;
        if (users == null) users = new ArrayList<>();
        userAll = (ArrayList<User>) users.clone();
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.auto_completed_item, null);

        }

        ((TextView)convertView).setTypeface(SingletonFonts.getInstanse(context).getKarlson());
        ((TextView)convertView).setText(users.get(position).getLastname() + " " +
                users.get(position).getFirstname() + " " +
                users.get(position).getPatronymic());

        return convertView;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            User u = (User) resultValue;
            return u.getLastname() + " " + u.getFirstname() + " " + u.getPatronymic();
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                userAll = new User().selectAll();
                if(userAll == null) {
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                    return filterResults;
                }

                for (User u : userAll) {
                    /*
                    if((u.cFirstName + " " + u.cLastName + " " + u.cSurname).toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(u);
                    }
                    */
                    if((u.getFirstname()).toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(u);
                    }else if((u.getLastname()).toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(u);
                    }else if((u.getPatronymic()).toLowerCase().startsWith(constraint.toString().toLowerCase())){
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
