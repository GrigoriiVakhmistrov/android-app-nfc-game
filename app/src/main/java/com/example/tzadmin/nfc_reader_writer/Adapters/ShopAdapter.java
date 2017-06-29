package com.example.tzadmin.nfc_reader_writer.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tzadmin.nfc_reader_writer.Models.Shop;
import com.example.tzadmin.nfc_reader_writer.R;

import java.util.ArrayList;

/**
 * Created by forz on 22.06.17.
 */

public class ShopAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Shop> items;

    public ShopAdapter(Context context, ArrayList<Shop> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View shop;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            shop = inflater.inflate(R.layout.shop_item, null);

            ImageView imageView = (ImageView)shop.findViewById(R.id.image_shop_item);
            TextView name = (TextView) shop.findViewById(R.id.tv_shop_item_name);
            TextView info = (TextView) shop.findViewById(R.id.tv_shop_item_info);
            //Button btn = (Button) shop.findViewById(R.id.btn_shop_item_add);

            imageView.setImageResource(items.get(position).pic);
            name.setText(items.get(position).name);
            info.setText(items.get(position).description);
            //btn.setTag(items.get(position).id);

        } else {
            shop = convertView;
        }

        return shop;
    }
}
