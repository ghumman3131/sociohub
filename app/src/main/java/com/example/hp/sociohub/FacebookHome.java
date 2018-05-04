package com.example.hp.sociohub;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.example.hp.sociohub.fragment.facebook.MakePostFragment;
import com.example.hp.sociohub.fragment.facebook.SearchTimeLineFragment;
import com.example.hp.sociohub.fragment.facebook.UserTimeLineFragment;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.linkedin.platform.LISessionManager;

public class FacebookHome extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_home);

       if( AccessToken.getCurrentAccessToken()  == null )
       {
           startActivity( new Intent(FacebookHome.this , FacebookLoginActivity.class));
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

    public void log_out_facebook(View view) {

        LoginManager.getInstance().logOut();


        finish();
    }

}
