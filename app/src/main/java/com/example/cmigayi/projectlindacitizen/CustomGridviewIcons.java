package com.example.cmigayi.projectlindacitizen;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cmigayi on 25-Jan-16.
 */
public class CustomGridviewIcons extends ArrayAdapter {
    Context context;
    ArrayList<HashMap<String,Bitmap>> icons;
    LayoutInflater layoutInflater;
    int[] imgID;
    String [] iconName;

    public CustomGridviewIcons(Context context, int[] imgID, String [] iconName) {
        super(context, R.layout.gridview_icons);
        this.context = context;
        this.imgID = imgID;
        this.iconName = iconName;
        this.layoutInflater = LayoutInflater.from(context);
    }

    static class ViewHolder{
        TextView textView;
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.gridview_icons,parent,false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.iconName);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.setImageResource(imgID[position]);
        viewHolder.textView.setText(iconName[position]);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getPosition(Object item) {
        return super.getPosition(item);
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
