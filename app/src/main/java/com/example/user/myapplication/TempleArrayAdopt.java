package com.example.user.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by caihongru on 2017/6/19.
 */

class TempleArrayAdopt extends ArrayAdapter<Temple> {
    Context context;

    public TempleArrayAdopt(Context context, List<Temple> items) {
        super(context, 0, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout itemlayout = null;
        if (convertView == null){

            itemlayout =(LinearLayout) inflater.inflate(R.layout.temple_list,null);
        }else {
            itemlayout =(LinearLayout) convertView;
        }
        Temple item = (Temple)getItem(position);
        TextView tvname = (TextView) itemlayout.findViewById(R.id.tempName);
        tvname.setText(item.getName());
        TextView tvKind = (TextView) itemlayout.findViewById(R.id.tempKind);
        tvKind.setText(item.getKind());
        TextView tvAddress= (TextView) itemlayout.findViewById(R.id.tempAddr);
        tvAddress.setText(item.getAddress());

        return itemlayout;
    }



}
