package com.example.shawyoungtang.facebooksearch.TabFragments;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.shawyoungtang.facebooksearch.PagePOJO.Datum;
import com.example.shawyoungtang.facebooksearch.PagePOJO.Pages;
import com.example.shawyoungtang.facebooksearch.PagePOJO.Paging;
import com.example.shawyoungtang.facebooksearch.R;
import com.example.shawyoungtang.facebooksearch.Utils.Cons;
import com.example.shawyoungtang.facebooksearch.Utils.DetailAsynTask;
import com.example.shawyoungtang.facebooksearch.Utils.LazyAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ShawYoungTang on 4/8/2017.
 */

public class PageFragment extends Fragment{

    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> imageUrls = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> stars = new ArrayList<>();
    ArrayList<Integer> arrows = new ArrayList<>();
    View view;
    ListView listView;
    TextView textView;
    private boolean notFirstLoad = false;

    public PageFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (notFirstLoad){
            updateStars();
            getFragmentManager().beginTransaction().detach(this).commit();
            notFirstLoad = false;
            getFragmentManager().beginTransaction().attach(this).commit();

        }else{
            notFirstLoad = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.page_fragment, container, false);

        listView = (ListView) view.findViewById(R.id.page_list);

        textView = (TextView) view.findViewById(R.id.page_text);

        if (getArguments() != null) {
            update(getArguments().getString("json"));
        }

        return view;
    }


    class PagePagingListener implements View.OnClickListener{

        String url;

        public PagePagingListener(String url){
            this.url = url;
        }

        @Override
        public void onClick(View v) {
            PageAsyncTask pageAsyncTask = new PageAsyncTask();
            pageAsyncTask.execute(url);
        }
    }




    private void update(String jsondata){
        Gson gson = new Gson();
        Pages pages = gson.fromJson(jsondata, Pages.class);
        List<Datum> data = pages.getData();
        Paging paging = pages.getPaging();


//        TextView textView = (TextView) view.findViewById(R.id.page_text);
//        textView.setText("Hello from code!");

        ids = new ArrayList<>();
        imageUrls = new ArrayList<>();
        names = new ArrayList<>();
        stars = new ArrayList<>();
        arrows = new ArrayList<>();

        for (Datum d : data){
            ids.add(Cons.PAGE + d.getId());
            names.add(d.getName());
            imageUrls.add(d.getPicture().getData().getUrl());
            stars.add(R.mipmap.ic_favorites_off);
            arrows.add(R.mipmap.ic_details);
        }

        updateStars();

        if (ids.size() == 0){
            textView.setVisibility(View.VISIBLE);
        }else{
            textView.setVisibility(View.GONE);
        }

        LazyAdapter adapter = new LazyAdapter(this.getActivity(), ids, imageUrls, names, stars, arrows);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailAsynTask da = new DetailAsynTask(view.getContext(), imageUrls.get(position), names.get(position));
                da.execute(ids.get(position));
            }
        });

        Button previous = (Button) view.findViewById(R.id.page_previous);
        Button next = (Button) view.findViewById(R.id.page_next);

        if (paging != null && paging.getPrevious() != null){
            previous.setEnabled(true);
            previous.setOnClickListener(new PagePagingListener(paging.getPrevious()));
        }else
            previous.setEnabled(false);

        if (paging != null && paging.getNext() != null){
            next.setEnabled(true);
            next.setOnClickListener(new PagePagingListener(paging.getNext()));
        }else
            next.setEnabled(false);
    }


    private void updateStars(){
        SharedPreferences preferences = PageFragment.this.getActivity().getSharedPreferences(Cons.FAVORITES, 0);
        for (int i = 0; i < ids.size(); i++){
            if(!preferences.getString(ids.get(i), Cons.NOID).equals(Cons.NOID))
                stars.set(i, R.mipmap.ic_favorites_on);
        }
    }


    class PageAsyncTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(params[0]).build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                publishProgress(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            update(values[0]);
        }
    }

}
