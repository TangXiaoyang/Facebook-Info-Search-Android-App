package com.example.shawyoungtang.facebooksearch.TabFragments;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shawyoungtang.facebooksearch.PlacePOJO.Datum;
import com.example.shawyoungtang.facebooksearch.PlacePOJO.Paging;
import com.example.shawyoungtang.facebooksearch.PlacePOJO.Places;
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

public class PlaceFragment extends Fragment {

    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> imageUrls = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> stars = new ArrayList<>();
    ArrayList<Integer> arrows = new ArrayList<>();
    View view;
    ListView listView;
    TextView textView;
    private boolean notFirstLoad = false;

    public PlaceFragment() {

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
        view =  inflater.inflate(R.layout.place_fragment, container, false);

        listView = (ListView) view.findViewById(R.id.place_list);

        textView = (TextView) view.findViewById(R.id.place_text);

        if (getArguments() != null) {
            update(getArguments().getString("json"));
        }

        return view;
    }


    class PlacePagingListener implements View.OnClickListener{

        String url;

        public PlacePagingListener(String url){
            this.url = url;
        }

        @Override
        public void onClick(View v) {
            PlaceAsyncTask placeAsyncTask = new PlaceAsyncTask();
            placeAsyncTask.execute(url);
        }
    }



    private void update(String jsonData){
        Gson gson = new Gson();
        Places places = gson.fromJson(jsonData, Places.class);
        Log.i("info:", String.valueOf((places == null)));
        List<Datum> data = places.getData();

        Paging paging = places.getPaging();

        ids = new ArrayList<>();
        imageUrls = new ArrayList<>();
        names = new ArrayList<>();
        stars = new ArrayList<>();
        arrows = new ArrayList<>();

        for (Datum d : data){
            ids.add(Cons.PLACE + d.getId());
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

        Button previous = (Button) view.findViewById(R.id.place_previous);
        Button next = (Button) view.findViewById(R.id.place_next);

        if (paging != null && paging.getPrevious() != null){
            previous.setEnabled(true);
            previous.setOnClickListener(new PlacePagingListener(paging.getPrevious()));
        }else
            previous.setEnabled(false);

        if (paging != null && paging.getNext() != null){
            next.setEnabled(true);
            next.setOnClickListener(new PlacePagingListener(paging.getNext()));
        }else
            next.setEnabled(false);

    }


    private void updateStars(){
        SharedPreferences preferences = PlaceFragment.this.getActivity().getSharedPreferences(Cons.FAVORITES, 0);
        for (int i = 0; i < ids.size(); i++){
            if(!preferences.getString(ids.get(i), Cons.NOID).equals(Cons.NOID))
                stars.set(i, R.mipmap.ic_favorites_on);
        }
    }



    class PlaceAsyncTask extends AsyncTask<String, String, String>{

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
