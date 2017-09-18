package com.example.shawyoungtang.facebooksearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.shawyoungtang.facebooksearch.FavoriteFragments.FavoriteEvents;
import com.example.shawyoungtang.facebooksearch.FavoriteFragments.FavoriteGroups;
import com.example.shawyoungtang.facebooksearch.FavoriteFragments.FavoritePages;
import com.example.shawyoungtang.facebooksearch.FavoriteFragments.FavoritePlaces;
import com.example.shawyoungtang.facebooksearch.FavoriteFragments.FavoriteUsers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShawYoungTang on 4/8/2017.
 */

public class FavoriteActiviry extends AppCompatActivity{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_layout);

        toolbar = (Toolbar)findViewById(R.id.favorite_toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.favorite_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavListener());
        navigationView.getMenu().getItem(1).setChecked(true);


        viewPager = (ViewPager) findViewById(R.id.favorite_container);
        ViewPagerAdpter adpter = new ViewPagerAdpter(getSupportFragmentManager());
        adpter.addFragment(new FavoriteUsers(), R.mipmap.ic_users, "Users");
        adpter.addFragment(new FavoritePages(), R.mipmap.ic_pages, "Pages");
        adpter.addFragment(new FavoriteEvents(), R.mipmap.ic_events, "Events");
        adpter.addFragment(new FavoritePlaces(), R.mipmap.ic_places, "Places");
        adpter.addFragment(new FavoriteGroups(), R.mipmap.ic_groups, "Groups");
        viewPager.setAdapter(adpter);


        tabLayout = (TabLayout)findViewById(R.id.favorite_tabs);
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < adpter.getCount(); i++){
            TextView tab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            tab.setText(adpter.getPageTitle(i));
            tab.setCompoundDrawablesWithIntrinsicBounds(0, adpter.getId(i), 0, 0);
            tabLayout.getTabAt(i).setCustomView(tab);
        }


    }

    private class ViewPagerAdpter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<Integer> IconIds = new ArrayList<>();
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
            IconIds.add(id);
            titleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return titleList.get(position);
        }

        public int getId(int position) {
            return IconIds.get(position);
        }
    }




    class MyNavListener implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.home) {
                Intent intent = new Intent(FavoriteActiviry.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else if (id == R.id.favorites) {

            } else if (id == R.id.about_me) {
                Intent intent = new Intent(FavoriteActiviry.this, AboutActivity.class);
                startActivity(intent);
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.favorite_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }

}
