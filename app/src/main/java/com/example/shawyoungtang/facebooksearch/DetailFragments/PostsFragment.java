package com.example.shawyoungtang.facebooksearch.DetailFragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shawyoungtang.facebooksearch.IDPOJO.Datum__;
import com.example.shawyoungtang.facebooksearch.IDPOJO.IDResults;
import com.example.shawyoungtang.facebooksearch.R;
import com.example.shawyoungtang.facebooksearch.Utils.LazyAdapter;
import com.example.shawyoungtang.facebooksearch.Utils.PostsAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShawYoungTang on 4/9/2017.
 */

public class PostsFragment extends Fragment {

    String headPhotoUrl;
    String name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.posts_layout, container, false);
        TextView textView = (TextView) view.findViewById(R.id.posts_text);

        Bundle bundle = getArguments();
        String detailResult = bundle.getString("data").trim();
        headPhotoUrl = bundle.getString("headPhotoUrl");
        name = bundle.getString("name");


        Gson gson = new Gson();
        IDResults results = gson.fromJson(detailResult, IDResults.class);

        if(results == null || results.getPosts() == null){
            textView.setText("No data found");
            textView.setVisibility(View.VISIBLE);
            return view;
        }
        List<Datum__> data = results.getPosts().getData();
        ArrayList<String> postsList = new ArrayList<>();
        ArrayList<String> timeList = new ArrayList<>();

        for (Datum__ d : data){
            timeList.add(extractTime(d.getCreatedTime()));
            if(d.getMessage() != null)
                postsList.add(d.getMessage());
            else
                postsList.add("None");
        }

        if (postsList.size() == 0){
            textView.setText("No data found");
            textView.setVisibility(View.VISIBLE);
            return view;
        }




        PostsAdapter adapter = new PostsAdapter(this.getActivity(), postsList, timeList, headPhotoUrl, name);
        ListView listView = (ListView) view.findViewById(R.id.posts_list);
        listView.setAdapter(adapter);


        return view;
    }


    private String extractTime(String s){
        return s.substring(0, 10) + " " + s.substring(11, 19);
    }


}
