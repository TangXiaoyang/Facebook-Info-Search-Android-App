package com.example.shawyoungtang.facebooksearch.Utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shawyoungtang.facebooksearch.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.internal.cache.CacheStrategy;


/**
 * Created by ShawYoungTang on 4/9/2017.
 */

public class LazyAdapter extends BaseAdapter{

    private ArrayList<String> ids;
    private ArrayList<String> imageUrls;
    private ArrayList<String> names;
    private ArrayList<Integer> stars;
    private ArrayList<Integer> arrows;
    private LayoutInflater inflater;
    private Activity activity;

    public LazyAdapter(Activity a, ArrayList<String> ids, ArrayList<String> imageUrls, ArrayList<String> names, ArrayList<Integer> stars, ArrayList<Integer> arrows) {
        this.ids = ids;
        this.imageUrls = imageUrls;
        this.names = names;
        this.stars = stars;
        this.arrows = arrows;
        activity = a;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ids.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        View v = convertView;
//        if (v == null)
        View v = inflater.inflate(R.layout.list_item, null);
        ImageView photo = (ImageView) v.findViewById(R.id.profile_photo);
        TextView nameView = (TextView) v.findViewById(R.id.name);
        ImageView star = (ImageView) v.findViewById(R.id.star);
        ImageView arrow = (ImageView) v.findViewById(R.id.arrow);

        Picasso.with(activity).load(imageUrls.get(position)).into(photo);
        nameView.setText(names.get(position));
        star.setImageResource(stars.get(position));
        arrow.setImageResource(arrows.get(position));

        return v;
    }
}
