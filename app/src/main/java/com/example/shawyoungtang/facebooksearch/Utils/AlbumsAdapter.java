package com.example.shawyoungtang.facebooksearch.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shawyoungtang.facebooksearch.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ShawYoungTang on 4/10/2017.
 */

public class AlbumsAdapter extends BaseExpandableListAdapter {

    private ArrayList<HashMap<String, ArrayList<String>>> photoUrls;
    Activity activity;
    LayoutInflater inflater;

    public AlbumsAdapter(Activity a, ArrayList<HashMap<String, ArrayList<String>>> p){
        activity = a;
        photoUrls = p;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return photoUrls.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return photoUrls.get(groupPosition).entrySet().iterator().next().getValue().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition * 100 + childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View groupView = inflater.inflate(R.layout.albums_group_view, null);
        TextView groupTitle = (TextView) groupView.findViewById(R.id.group_title);
        Map.Entry<String, ArrayList<String>> e = photoUrls.get(groupPosition).entrySet().iterator().next();
        groupTitle.setText(e.getKey());

        return groupView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View childView = inflater.inflate(R.layout.albums_children_view, null);
        Map.Entry<String, ArrayList<String>> e = photoUrls.get(groupPosition).entrySet().iterator().next();
        ArrayList<String> tempList = e.getValue();
        ImageView image1 = (ImageView) childView.findViewById(R.id.image1);

        Picasso.with(activity).load(tempList.get(childPosition).trim()).into(image1);


        return childView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
