package com.example.tzadmin.nfc_reader_writer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.tzadmin.nfc_reader_writer.Models.WinnerStatistic;
import com.example.tzadmin.nfc_reader_writer.R;
import java.util.ArrayList;

/**
 * Created by forz on 20.06.17.
 */

public class WinnerViewAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    ArrayList<WinnerStatistic> objects;


    public WinnerViewAdapter(Context ctx, ArrayList<WinnerStatistic> objs) {
        context = ctx;
        objects = objs;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.winner_item, parent, false);
        }

        WinnerStatistic info = getProduct(position);

        ((TextView) view.findViewById(R.id.tv_winner_place)).setText(info.place);
        ((TextView) view.findViewById(R.id.tv_winner_team)).setText(info.team);
        ((TextView) view.findViewById(R.id.tv_winner_team)).setText(info.allPoints);

        return view;
    }

    WinnerStatistic getProduct(int position) {
        return ((WinnerStatistic) getItem(position));
    }
}