package com.example.shawyoungtang.facebooksearch;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shawyoungtang.facebooksearch.TabFragments.EventFragment;
import com.example.shawyoungtang.facebooksearch.TabFragments.GroupFragment;
import com.example.shawyoungtang.facebooksearch.TabFragments.PageFragment;
import com.example.shawyoungtang.facebooksearch.TabFragments.PlaceFragment;
import com.example.shawyoungtang.facebooksearch.TabFragments.UserFragment;
import com.example.shawyoungtang.facebooksearch.UserPOJO.Datum;
import com.example.shawyoungtang.facebooksearch.UserPOJO.Paging;
import com.example.shawyoungtang.facebooksearch.UserPOJO.Users;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ShawYoungTang on 4/8/2017.
 */

public class ResultActivity extends AppCompatActivity{


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdpter adpter;
    private boolean notFirstLoad = false;
    String rawData;
    String[] jsonStrings;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);

        toolbar = (Toolbar)findViewById(R.id.result_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewPager = (ViewPager) findViewById(R.id.result_container);
        adpter = new ViewPagerAdpter(getSupportFragmentManager());
        adpter.addFragment(new UserFragment(), R.mipmap.ic_users, "Users");
        adpter.addFragment(new PageFragment(), R.mipmap.ic_pages, "Pages");
        adpter.addFragment(new EventFragment(), R.mipmap.ic_events, "Events");
        adpter.addFragment(new PlaceFragment(), R.mipmap.ic_places, "Places");
        adpter.addFragment(new GroupFragment(), R.mipmap.ic_groups, "Groups");
        viewPager.setAdapter(adpter);


        tabLayout = (TabLayout)findViewById(R.id.result_tabs);
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < adpter.getCount(); i++){
            TextView tab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tab.setText(adpter.getPageTitle(i));
            tab.setCompoundDrawablesWithIntrinsicBounds(0, adpter.getId(i), 0, 0);
            tabLayout.getTabAt(i).setCustomView(tab);
        }

        // get url from MainActivity

        Intent intent = getIntent();
        if(intent != null && intent.getStringExtra("rawData") != null) {
            rawData = getIntent().getStringExtra("rawData");
        }else{
            rawData = savedInstanceState.getString("rawData");
        }
        jsonStrings = divideJsonString(rawData);
        // send data to each fragment
        for(int i = 0 ; i < adpter.getCount(); i++){
            Bundle bundle = new Bundle();
            bundle.putString("json", jsonStrings[i]);
            adpter.getItem(i).setArguments(bundle);
        }

    }


    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        finish();
        return null;
//        return super.getSupportParentActivityIntent();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("rawData", rawData);
        super.onSaveInstanceState(outState);
    }

    private class ViewPagerAdpter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<Integer> ids = new ArrayList<>();
        private final List<String> titleList = new ArrayList<>();

        public ViewPagerAdpter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, int id, String title){
            fragmentList.add(fragment);
            ids.add(id);
            titleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return titleList.get(position);
        }

        public int getId(int position) {
            return ids.get(position);
        }

    }
    // end of tabs creation


    private String[] divideJsonString(String response) {
        String[] jsons = response.split("---");
        return jsons;
    }


}
