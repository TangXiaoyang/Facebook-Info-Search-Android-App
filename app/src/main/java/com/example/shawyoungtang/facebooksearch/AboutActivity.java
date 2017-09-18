package com.example.shawyoungtang.facebooksearch;

import android.content.Intent;
import android.os.Bundle;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.R.anim;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

/**
 * Created by ShawYoungTang on 4/8/2017.
 */

public class AboutActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_me_layout);

        Toolbar toolbar = (Toolbar)findViewById(R.id.about_me_toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle(R.string.about_me_title);
        setSupportActionBar(toolbar);

//        toolbar.setNavigationIcon(R.mipmap.ic_keyboard_arrow_left_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(anim.fade_in, anim.slide_out_right);
    }
}
