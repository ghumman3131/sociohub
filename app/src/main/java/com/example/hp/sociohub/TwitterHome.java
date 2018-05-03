package com.example.hp.sociohub;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.hp.sociohub.fragment.twitter.MakePostFragment;
import com.example.hp.sociohub.fragment.twitter.SearchTimeLineFragment;
import com.example.hp.sociohub.fragment.twitter.UserTimeLineFragment;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;


public class TwitterHome extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_twitter_home);

        final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                .getActiveSession();

        if(session != null) {


            FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.main_frame, new UserTimeLineFragment()).commit();

        }

        else {

            startActivity(new Intent(TwitterHome.this , TwitterLoginActivity.class));
        }

    }

        @Override
        protected void onResume() {
            super.onResume();





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

        TwitterCore.getInstance().getSessionManager()
                .clearActiveSession();

        finish();
    }

}
