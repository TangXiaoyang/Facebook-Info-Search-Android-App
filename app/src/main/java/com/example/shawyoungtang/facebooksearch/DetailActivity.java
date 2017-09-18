package com.example.shawyoungtang.facebooksearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.BuddhistCalendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.health.SystemHealthManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shawyoungtang.facebooksearch.DetailFragments.AlbumsFragment;
import com.example.shawyoungtang.facebooksearch.DetailFragments.PostsFragment;
import com.example.shawyoungtang.facebooksearch.IDPOJO.Datum;
import com.example.shawyoungtang.facebooksearch.IDPOJO.Datum_;
import com.example.shawyoungtang.facebooksearch.IDPOJO.IDResults;
import com.example.shawyoungtang.facebooksearch.Utils.Cons;
import com.example.shawyoungtang.facebooksearch.Utils.MyLocationListener;
import com.facebook.*;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ShawYoungTang on 4/9/2017.
 */

public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String headPhotoUrl;
    private String name;
    ViewPagerAdapter adapter;
    private String unitID;
    private ShareDialog shareDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        headPhotoUrl = intent.getStringExtra("headPhotoUrl");
        name = intent.getStringExtra("name");
        unitID = intent.getStringExtra("itemID");

        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.detail_container);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.add(new AlbumsFragment(), "Albums", R.mipmap.ic_albums);
        adapter.add(new PostsFragment(), "Posts", R.mipmap.ic_posts);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.detail_tabs);
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < adapter.getCount(); i++){
            TextView tab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tab.setText(adapter.getPageTitle(i));
            tab.setCompoundDrawablesWithIntrinsicBounds(0, adapter.getIconId(i), 0, 0);
            tabLayout.getTabAt(i).setCustomView(tab);
        }

        Bundle albumsBundle = new Bundle();
        albumsBundle.putString("data", data);
        adapter.getItem(0).setArguments(albumsBundle);

        Bundle postsBundle = new Bundle();
        postsBundle.putString("data", data);
        postsBundle.putString("headPhotoUrl", headPhotoUrl);
        postsBundle.putString("name", name);
        adapter.getItem(1).setArguments(postsBundle);


        // facebook share dialog
        shareDialog = new ShareDialog(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        finish();
        return null;
//        return super.getSupportParentActivityIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.toggle_favorite);
        SharedPreferences sharedPreferences = getSharedPreferences(Cons.FAVORITES, 0);
        if (sharedPreferences.getString(unitID, Cons.NOID).equals(Cons.NOID))
            menuItem.setTitle("Add to Favorites");
        else
            menuItem.setTitle("Remove from Favorites");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.toggle_favorite){
            SharedPreferences preferences = DetailActivity.this.getSharedPreferences(Cons.FAVORITES, 0);
            SharedPreferences.Editor editor = preferences.edit();
            if(preferences.getString(unitID, Cons.NOID).equals(Cons.NOID)){
                // add to Favorites
                editor.putString(unitID, unitID);
                editor.putString(Cons.PHOTO_URL + unitID, headPhotoUrl);
                editor.putString(Cons.NAME + unitID, name);
                editor.putString(Cons.TIMESTAMP + unitID, System.currentTimeMillis() + "");
                editor.commit();
                Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
                item.setTitle("Remove from Favorites");
            }else{
                // remove from Favorites
                editor.remove(unitID);
                editor.remove(Cons.PHOTO_URL + unitID);
                editor.remove(Cons.NAME + unitID);
                editor.remove(Cons.TIMESTAMP + unitID);
                editor.commit();
                Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
                item.setTitle("Add to Favorites");
            }
        }else if (itemId == R.id.share){
            Toast.makeText(this, "Sharing " + name, Toast.LENGTH_SHORT).show();
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("http://facebook.com/" + Cons.extractID(unitID)))
                    .setImageUrl(Uri.parse(headPhotoUrl))
                    .setContentDescription("FB SEARCH FROM USC CSCI571")
                    .setContentTitle(name)
                    .build();
            shareDialog.show(content);
        }


        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> fragmentList = new ArrayList<>();
        private ArrayList<Integer> IconIds = new ArrayList<>();
        private ArrayList<String> titleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void add(Fragment f, String title, Integer iconId){
            fragmentList.add(f);
            titleList.add(title);
            IconIds.add(iconId);
        }

        public int getIconId(int position) {
            return IconIds.get(position);
        }

        public String getPageTitle(int position){
            return titleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }


}
