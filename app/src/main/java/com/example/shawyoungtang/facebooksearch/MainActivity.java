package com.example.shawyoungtang.facebooksearch;

import android.content.Intent;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shawyoungtang.facebooksearch.UserPOJO.Datum;
import com.example.shawyoungtang.facebooksearch.UserPOJO.Users;
import com.example.shawyoungtang.facebooksearch.Utils.MyLocationListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    String AWSUrl = "http://lowcost-env.zw3r44jb2z.us-west-2.elasticbeanstalk.com/getInfo.php";
    Button searchButton;
    Button clearButton;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        // bind a listener to search button

        editText = (EditText)findViewById(R.id.search_text);
        searchButton = (Button)findViewById(R.id.search_button);
        clearButton = (Button)findViewById(R.id.clear_button);


        final MyLocationListener myLocationListener = new MyLocationListener();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAsyncTask ma = new MyAsyncTask();
                String keyword = editText.getText().toString();

                if(keyword.equals("")){
                    Toast.makeText(MainActivity.this, "Please enter a keyword!", Toast.LENGTH_LONG).show();
                    return;
                }

                String targetUrl = AWSUrl + "?q=" + keyword + "&lat=" + myLocationListener.getLat() +
                        "&log=" + myLocationListener.getLog();
                ma.execute(targetUrl);

            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.toggle_favorite) {
            return true;
        }else if (id == R.id.share){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
        } else if (id == R.id.favorites) {
            Intent intent = new Intent(MainActivity.this, FavoriteActiviry.class);
            startActivity(intent);
        } else if (id == R.id.about_me) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class MyAsyncTask extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... params) {

            String res = "";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(params[0]).build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                publishProgress("Error in call");
                e.printStackTrace();
            }

            // dispose the response
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


            startResult(res);

            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Toast.makeText(MainActivity.this, values[0], Toast.LENGTH_SHORT).show();
        }


        private void startResult(String data) {
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("rawData", data);
            startActivity(intent);
        }
    }
}
