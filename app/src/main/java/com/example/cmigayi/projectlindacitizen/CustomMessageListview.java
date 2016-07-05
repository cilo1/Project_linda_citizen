package com.example.cmigayi.projectlindacitizen;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cmigayi on 26-Mar-16.
 */
public class CustomMessageListview extends ArrayAdapter {
    ArrayList<HashMap<String, String>> arrayList;
    Context context;
    LayoutInflater layoutInflater;
    TextView subject,datetime,status;

    public CustomMessageListview(Context context, ArrayList<HashMap<String, String>> arrayList) {
        super(context, R.layout.message_custom_listview,arrayList);
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.message_custom_listview,parent,false);
        subject = (TextView) convertView.findViewById(R.id.subject);
        datetime = (TextView) convertView.findViewById(R.id.datetime);
        status = (TextView) convertView.findViewById(R.id.status);


        HashMap<String, String> map = new HashMap<String, String>();
        map = arrayList.get(position);
        Log.d("subject", map.get("subject"));
        subject.setText(map.get("subject"));
        datetime.setText("Posted: "+map.get("datetime"));
        if(map.get("status").equals("unread")){
            status.setText("UR");
            status.setBackgroundColor(Color.parseColor("#54bd64"));
        }else{
            status.setText("RE");
            status.setBackgroundColor(Color.parseColor("#fbff57"));
        }
        return convertView;
    }
}
