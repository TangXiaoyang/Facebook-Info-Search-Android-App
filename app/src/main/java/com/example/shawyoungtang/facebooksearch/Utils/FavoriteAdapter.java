package com.example.shawyoungtang.facebooksearch.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shawyoungtang.facebooksearch.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by ShawYoungTang on 4/11/2017.
 */

public class FavoriteAdapter extends BaseAdapter {

    private ArrayList<String> headPhotoUrls;
    private ArrayList<String> names;
    private Activity activity;
    private LayoutInflater inflater;


    public FavoriteAdapter(Activity activity, ArrayList<String> headPhotoUrls, ArrayList<String> names){
        this.activity = activity;
        this.headPhotoUrls = headPhotoUrls;
        this.names = names;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item, null);

        ImageView photo = (ImageView) view.findViewById(R.id.profile_photo);
        TextView nameView = (TextView) view.findViewById(R.id.name);
        ImageView star = (ImageView) view.findViewById(R.id.star);
        ImageView arrow = (ImageView) view.findViewById(R.id.arrow);

        Picasso.with(activity).load(headPhotoUrls.get(position)).into(photo);
        nameView.setText(names.get(position));
        star.setImageResource(R.mipmap.ic_favorites_on);
        arrow.setImageResource(R.mipmap.ic_details);

        return view;
    }
}
