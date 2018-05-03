package com.example.hp.sociohub;

import android.app.Application;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

/**
 * Created by charanghumman on 03/05/18.
 */

public class AppApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();


        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("cj9KlTDHKRucKGRnwatQGDPJr", "VPYXo07sCDYz5SG2Fumzbu1XL2vTZWzdyOnnZUctrZp2XhZiUi"))
                .debug(true)
                .build();
        Twitter.initialize(config);
    }
}
