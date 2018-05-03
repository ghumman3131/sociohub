package com.example.hp.sociohub;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import org.json.JSONException;
import org.json.JSONObject;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    private ImageView facebook_icon, twitter_icon, instagram_icon, linked_in_icon;

    private TextView facebook_logged_in_as, twitter_logged_in_as, instagram_logged_in_as, linkedin_logged_in_as;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        facebook_icon = findViewById(R.id.facebook_icon);
        twitter_icon = findViewById(R.id.twitter_icon);
        instagram_icon = findViewById(R.id.instagram_icon);
        linked_in_icon = findViewById(R.id.linkedin_icon);

        facebook_logged_in_as = findViewById(R.id.facebook_logged_in_as);
        twitter_logged_in_as = findViewById(R.id.twitter_logged_in_as);
        instagram_logged_in_as = findViewById(R.id.instagram_logged_in_as);
        linkedin_logged_in_as = findViewById(R.id.linkedin_logged_in_as);

        drawerLayout = findViewById(R.id.drawer_layout);

    }


    public void next_page(View view) {

    }


    public void open_drawer(View view) {

        drawerLayout.openDrawer(Gravity.START);
    }

    public void open_facebook(View view) {

        Intent i = new Intent(MainActivity.this, Home_page.class);

        startActivity(i);
    }

    public void open_twitter(View view) {

        Intent i = new Intent(MainActivity.this, TwitterHome.class);

        startActivity(i);
    }

    public void open_instagram(View view) {

        Intent i = new Intent(MainActivity.this, SubscribeLayout.class);

        startActivity(i);

    }

    public void open_linkedin(View view) {

        Intent i = new Intent(MainActivity.this, LinkedinHome.class);

        startActivity(i);
    }


    private void check_currently_logged_in() {
        final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                .getActiveSession();

        if (session != null) {


            twitter_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_twitter));

            twitter_logged_in_as.setText(session.getUserName());

        } else {

            twitter_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_twitter_disable));

            twitter_logged_in_as.setText("Not logged in");
        }


        if (LISessionManager.getInstance(this).getSession().getAccessToken() != null) {


            linked_in_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_linkedin));

            get_linkedin_username();

        } else {


            linked_in_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_linkedin_disable));

            linkedin_logged_in_as.setText("Not logged in");

        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        check_currently_logged_in();
    }

    private void get_linkedin_username() {
        LISessionManager.getInstance(getApplicationContext()).init(LISessionManager.getInstance(MainActivity.this).getSession().getAccessToken());

        String url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name)";

        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(this, url, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                // Success!

                Log.i("linkedin profile succe", String.valueOf(apiResponse));

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(apiResponse));

                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("responseData"));

                    linkedin_logged_in_as.setText(jsonObject1.getString("firstName")+"  "+jsonObject1.getString("lastName"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onApiError(LIApiError liApiError) {
                // Error making GET request!

                Log.i("linkedin profile  error", String.valueOf(liApiError));

            }
        });
    }

    public void make_all_post(View view) {

        startActivity( new Intent(MainActivity.this , AllAccountPostActivity.class));
    }
}

