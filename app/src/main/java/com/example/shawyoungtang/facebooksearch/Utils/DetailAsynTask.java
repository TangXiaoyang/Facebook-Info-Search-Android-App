package com.example.shawyoungtang.facebooksearch.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.shawyoungtang.facebooksearch.DetailActivity;
import com.example.shawyoungtang.facebooksearch.MainActivity;
import com.example.shawyoungtang.facebooksearch.ResultActivity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ShawYoungTang on 4/9/2017.
 */

public class DetailAsynTask extends AsyncTask<String, String, String> {

    String AWSUrl = "http://lowcost-env.zw3r44jb2z.us-west-2.elasticbeanstalk.com/getInfo.php";
    String res = "";
    Context context;
    String headPhotoUrl;
    String name;
    String itemID;

    public DetailAsynTask(Context c, String h, String n){
        context = c;
        headPhotoUrl = h;
        name = n;
    }

    @Override
    protected String doInBackground(String... params) {
        String id = extractId(params[0]);
        itemID = params[0];

        String url = AWSUrl + "?id=" + id;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(response.isSuccessful()){
            try {
                res = response.body().string();
            } catch (IOException e) {
                publishProgress("Error in get result");
                e.printStackTrace();
            }
        }else{
            publishProgress("Failed to get data, error code: " + response.code());
        }
        return res;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("data", res);
        intent.putExtra("headPhotoUrl", headPhotoUrl);
        intent.putExtra("name", name);
        intent.putExtra("itemID", itemID);
        context.startActivity(intent);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Toast.makeText(context, values[0], Toast.LENGTH_SHORT).show();
    }


    private String extractId(String s){
        int i = 0;
        while(!Character.isDigit(s.charAt(i)))
            i++;
        return s.substring(i);
    }
}
