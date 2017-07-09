package com.example.tzadmin.nfc_reader_writer.Adapters;

import  android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tzadmin.nfc_reader_writer.Fonts.SingletonFonts;
import com.example.tzadmin.nfc_reader_writer.Models.MoneyLogs;
import com.example.tzadmin.nfc_reader_writer.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by velor on 6/30/17.
 */

public class MoneyAdapter extends BaseAdapter {

    Object[] items;
    Context context;

    public MoneyAdapter(Context context, Collection<MoneyLogs> items) {
        this.items = items.toArray();
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return ((MoneyLogs)items[position]).id;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = (View) inflater.inflate(R.layout.money_item, null);
        }

        MoneyLogs l = (MoneyLogs) items[position];

        //ImageView operation = (ImageView) convertView.findViewById(R.id.action);
        TextView date = (TextView)convertView.findViewById(R.id.date);
        TextView money = (TextView)convertView.findViewById(R.id.money);
        TextView description = (TextView)convertView.findViewById(R.id.text);

        date.setTypeface(SingletonFonts.getInstanse(context).getKarlson());
        money.setTypeface(SingletonFonts.getInstanse(context).getKarlson());
        description.setTypeface(SingletonFonts.getInstanse(context).getKarlson());

        date.setTextColor(context.getResources().getColor(R.color.colorBtn));
        money.setTextColor(context.getResources().getColor(R.color.colorBtn));
        description.setTextColor(context.getResources().getColor(R.color.colorBtn));

        /*
        if (l.type.equals(MoneyLogs.Type.ADD_MONEY.toString())) {
            operation.setBackgroundResource(R.drawable.arrow_up);
        } else {
            operation.setBackgroundResource(R.drawable.arrow_down);
        }
        */

        l.date.replace(" ", "\\n");
        date.setText(l.date);
        money.setText(l.money.toString());
        description.setText(l.description);

        return convertView;
    }
}
