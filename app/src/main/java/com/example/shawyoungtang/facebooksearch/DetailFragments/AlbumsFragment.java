package com.example.shawyoungtang.facebooksearch.DetailFragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shawyoungtang.facebooksearch.IDPOJO.Datum;
import com.example.shawyoungtang.facebooksearch.IDPOJO.Datum_;
import com.example.shawyoungtang.facebooksearch.IDPOJO.IDResults;
import com.example.shawyoungtang.facebooksearch.R;
import com.example.shawyoungtang.facebooksearch.Utils.AlbumsAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ShawYoungTang on 4/9/2017.
 */

public class AlbumsFragment extends Fragment {

    String AWSUrl = "http://lowcost-env.zw3r44jb2z.us-west-2.elasticbeanstalk.com/getInfo.php";
    TextView textView;
    ArrayList<HashMap<String, ArrayList<String>>> pictureIds;
    ExpandableListView expandableListView;
    Activity activity;
    private int lastPosition = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.albums_layout, container, false);

        String detailResult = getArguments().getString("data");

        pictureIds = new ArrayList<>();
        textView = (TextView) view.findViewById(R.id.albums_text);
        MyAsyncTask ma = new MyAsyncTask();
        ma.execute(detailResult);


        activity = this.getActivity();
        expandableListView = (ExpandableListView) view.findViewById(R.id.albums_list);




        return view;
    }

    class MyAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            Gson gson = new Gson();
            IDResults results = gson.fromJson(params[0], IDResults.class);

            //get picture ids
            if (results == null || results.getAlbums() == null){
                publishProgress("No data found");
                return null;
            }
            List<Datum> data = results.getAlbums().getData();
            if(data == null || data.size() == 0){
                publishProgress("No data found");
                return null;
            }

            for (Datum row : data){
                if(row.getPhotos() != null) {
                    ArrayList<String> subList = new ArrayList<>();
                    String t = row.getName();
                    HashMap<String, ArrayList<String>> map = new HashMap<>();
                    for (Datum_ pic : row.getPhotos().getData())
                        subList.add(pic.getId());
                    if (subList.size() > 0) {
                        map.put(t, subList);
                        pictureIds.add(map);
                    }
                }
            }

            if (pictureIds.size() == 0){
                publishProgress("No data found");
                return null;
            }
            String IDsUrl = AWSUrl + "?";
            int count = 0;
            for (HashMap<String, ArrayList<String>> m : pictureIds){
                Map.Entry<String, ArrayList<String>> e = m.entrySet().iterator().next();
                for (String s : e.getValue()){
                    if(count == 0)
                        IDsUrl += "pic_ids[]=" + s;
                    else
                        IDsUrl += "&pic_ids[]=" + s;
                    count++;
                }
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(IDsUrl).build();
            Response response = null;

            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String res = null;

            if (response.isSuccessful()){
                try {
                    res = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                publishProgress("Something went wrong");
            }

            publishProgress(res);
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if (pictureIds.size() == 0){
                textView.setText(values[0]);
                textView.setVisibility(View.VISIBLE);
                return;
            }

            String[] urls = values[0].split("---");
            int count = 0;
            for (int i = 0; i < pictureIds.size(); i++){
                HashMap<String, ArrayList<String>> m = pictureIds.get(i);
                Map.Entry<String, ArrayList<String>> e = m.entrySet().iterator().next();
                ArrayList<String> subTemp = e.getValue();
                for(int j = 0; j < subTemp.size(); j++) {
                    subTemp.set(j, urls[count]);
                    count++;
                }
                m.put(e.getKey(), subTemp);
                pictureIds.set(i, m);
            }
            AlbumsAdapter albumsAdapter = new AlbumsAdapter(activity, pictureIds);
            expandableListView.setAdapter(albumsAdapter);
            expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    if (groupPosition != lastPosition)
                        expandableListView.collapseGroup(lastPosition);
                    lastPosition = groupPosition;
                }
            });
        }

    }
}
