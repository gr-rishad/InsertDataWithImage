package com.example.rishad.imagesaving;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private int  inflater;
    private LayoutInflater layoutInflater;
    private ArrayList<InfoDetails> info=new ArrayList<>();



    public CustomAdapter(Context context, int inflater, ArrayList<InfoDetails> info) {
        this.context = context;
        this.inflater = inflater;
        this.info = info;
    }

    @Override
    public int getCount() {
        return info.size();
    }

    @Override
    public Object getItem(int position) {
        return info.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         //InfoDetails infoo= info.get(position);
        if (convertView== null){
            layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.sample_view,parent,false);

        }
        ImageView imageView = convertView.findViewById(R.id.imageViewId);
        TextView name= convertView.findViewById(R.id.nameId);
        TextView MPposition = convertView.findViewById(R.id.positionId);
        TextView rgseat = convertView.findViewById(R.id.regionalConstituenciesNoId);


        byte[] images= info.get(position).getImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(images,0,images.length);
        imageView.setImageBitmap(bitmap);
        name.setText(info.get(position).getName());
        MPposition.setText(info.get(position).getShortName());
        rgseat.setText(info.get(position).getPrice());

        return convertView;
    }
}
