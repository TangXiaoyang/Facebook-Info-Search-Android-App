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

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ShawYoungTang on 4/10/2017.
 */

public class PostsAdapter extends BaseAdapter {

    String headPhotoUrls;
    ArrayList<String> timeList;
    String name;
    ArrayList<String> postContents;
    Activity activity;
    LayoutInflater inflater;

    public PostsAdapter(Activity a, ArrayList<String>contents, ArrayList<String> times, String h, String n) {
        activity = a;
        headPhotoUrls = h;
        timeList = times;
        postContents = contents;
        name = n;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return postContents.size();
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
        View view = inflater.inflate(R.layout.post_item, null);

        ImageView headPhoto = (ImageView) view.findViewById(R.id.head_photo);
        TextView nameView = (TextView) view.findViewById(R.id.posts_name);
        TextView createTime = (TextView) view.findViewById(R.id.posts_time);
        TextView content = (TextView) view.findViewById(R.id.posts_content);

        Picasso.with(activity).load(headPhotoUrls).into(headPhoto);
        nameView.setText(name);
        createTime.setText(timeList.get(position));
        content.setText(postContents.get(position));

        return view;
    }
}
