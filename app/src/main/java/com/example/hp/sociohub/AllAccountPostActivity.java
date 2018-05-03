package com.example.hp.sociohub;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.internal.TwitterApi;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import retrofit2.Call;


public class AllAccountPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_account_post);

        post_for_twitter();
    }


    private void post_for_twitter()
    {


        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();





        StatusesService statusesService = twitterApiClient.getStatusesService();
        Call<Tweet> call = statusesService.update("Hellow" , null , null , null , null , null , null , null , null);

        call.enqueue(new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                //Do something with result

                Log.i("result *** " , "tweet posted");
            }

            public void failure(TwitterException exception) {
                //Do something on failure

                Log.i("result fail *** " , "tweet not posted" + exception);

            }
        });
    }
}
