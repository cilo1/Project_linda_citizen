package com.example.cmigayi.projectlindacitizen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cmigayi on 22-Mar-16.
 */
public class CustomPatrolListview extends ArrayAdapter {
    ArrayList<HashMap<String, String>> arrayList;
    Context context;
    LayoutInflater layoutInflater;
    TextView tvName,tvID,tvDistance;

    public CustomPatrolListview(Context context, ArrayList<HashMap<String, String>> arrayList) {
        super(context, R.layout.patrol_custom_listview,arrayList);
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.patrol_custom_listview,parent,false);
        tvName = (TextView) convertView.findViewById(R.id.name_tv);
        tvID = (TextView) convertView.findViewById(R.id.id_tv);
        tvDistance = (TextView) convertView.findViewById(R.id.distance_tv);

        HashMap<String, String> map = new HashMap<String, String>();
        map = arrayList.get(position);
        String name = map.get("fname")+" "+map.get("lname")+" "+map.get("surname");
        Log.d("fname",name);
        tvName.setText(name);
        tvID.setText(map.get("officerID"));
        tvDistance.setText(map.get("distance")+" km away");
        return convertView;
    }
}
