package com.example.hp.sociohub;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.example.hp.sociohub.fragment.likedin.MakePostFragment;
import com.example.hp.sociohub.fragment.likedin.SearchTimeLineFragment;
import com.example.hp.sociohub.fragment.likedin.UserTimeLineFragment;
import com.linkedin.platform.LISession;
import com.linkedin.platform.LISessionManager;
import com.twitter.sdk.android.core.TwitterCore;

public class LinkedinHome extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkedin_home);

        if(LISessionManager.getInstance(this).getSession().getAccessToken()==null) {

            startActivity(new Intent(this, LinkedinLoginActivity.class));
        }

    }


    public void open_timeline(View view) {


        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.main_frame , new UserTimeLineFragment()).commit();

    }

    public void search_timeline(View view) {

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.main_frame , new SearchTimeLineFragment()).commit();

    }

    public void post_timeline(View view) {

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.main_frame , new MakePostFragment()).commit();

    }

    public void log_out_twitter(View view) {

        LISessionManager.getInstance(LinkedinHome.this).clearSession();


        finish();
    }


}
