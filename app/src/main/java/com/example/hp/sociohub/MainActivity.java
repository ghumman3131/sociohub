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
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoginStatusCallback;
import com.facebook.login.LoginManager;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    private ImageView facebook_icon, twitter_icon, instagram_icon, linked_in_icon;

    private TextView facebook_logged_in_as, twitter_logged_in_as, instagram_logged_in_as, linkedin_logged_in_as;

    public static Boolean isFacebook , isTwitter , isIntagram , isLinkedin ;

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

        Intent i = new Intent(MainActivity.this, FacebookHome.class);

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


            isTwitter = true;

            twitter_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_twitter));

            twitter_logged_in_as.setText(session.getUserName());

        } else {

            isTwitter = false ;

            twitter_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_twitter_disable));

            twitter_logged_in_as.setText("Not logged in");
        }


        if (LISessionManager.getInstance(this).getSession().getAccessToken() != null) {


            isLinkedin = true ;

            linked_in_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_linkedin));

            get_linkedin_username();

        } else {

            isLinkedin = false ;

            linked_in_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_linkedin_disable));

            linkedin_logged_in_as.setText("Not logged in");

        }

        if( AccessToken.getCurrentAccessToken()  != null )
        {

            isFacebook = true ;
            facebook_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_facebook));

            get_facebook_username();

        }

        else {

            isFacebook = false ;

            facebook_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_facebook_disable));

            facebook_logged_in_as.setText("Not logged in");
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

        if(isFacebook || isLinkedin || isIntagram || isTwitter)
        {
            startActivity( new Intent(MainActivity.this , AllAccountPostActivity.class));


        }

        else {

            Toast.makeText(MainActivity.this , "you are not logged in to any account" , Toast.LENGTH_SHORT).show();
        }

    }

    private void get_facebook_username()
    {
        LoginManager.getInstance().retrieveLoginStatus( this, new LoginStatusCallback()
        {
            @Override
            public void onCompleted( AccessToken accessToken )
            {
                GraphRequest request = GraphRequest.newMeRequest( accessToken, new GraphRequest.GraphJSONObjectCallback()
                {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response )
                    {
                        Log.e( "user details **********", object.toString() );
                        Log.e( "graph response ********", response.toString() );

                        try
                        {
                            String  userId = object.getString( "id" );
                            URL profilePicture = new URL( "https://graph.facebook.com/" + userId + "/picture?width=500&height=500" );
                            Log.d( "PROFILE_URL", "url: " + profilePicture.toString() );
                            if ( object.has( "first_name" ) )
                            {
                                if ( object.has( "last_name" ) )
                                {
                                    String firstName = object.getString( "first_name" );

                                  String  lastName = object.getString( "last_name" );

                                  facebook_logged_in_as.setText(firstName+"  "+lastName);
                                }

                            }
                         /*   if ( object.has( "last_name" ) )
                            {
                                lastName = object.getString( "last_name" );
                            }
                            if ( object.has( "email" ) )
                            {
                                email = object.getString( "email" );
                            }
                            if ( object.has( "birthday" ) )
                            {
                                birthday = object.getString( "birthday" );
                            }
                            if ( object.has( "gender" ) )
                            {
                                gender = object.getString( "gender" );
                            }
*/

                        }
                        catch ( JSONException e )
                        {
                            e.printStackTrace();
                        }
                        catch ( MalformedURLException e )
                        {
                            e.printStackTrace();
                        }

                    }
                } );
                //Here we put the requested fields to be returned from the JSONObject
                Bundle parameters = new Bundle();
                parameters.putString( "fields", "id, first_name, last_name, email, birthday, gender" );
                request.setParameters( parameters );
                request.executeAsync();
            }

            @Override
            public void onFailure()
            {

            }

            @Override
            public void onError( Exception exception )
            {

            }
        } );
    }

    public void show_scheduled(View view) {

        startActivity(new Intent(MainActivity.this , AllScheduledPostsActivity.class));
    }

    public void get_trending(View view) {

        //startActivity(new Intent(MainActivity.this , TrendingActivity.class));
    }
}

