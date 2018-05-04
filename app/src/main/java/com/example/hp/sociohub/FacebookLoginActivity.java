package com.example.hp.sociohub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoginStatusCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class FacebookLoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        Button logout_btn = findViewById(R.id.logout_btn);

        callbackManager = CallbackManager.Factory.create();


        String EMAIL = "email";



        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);



        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                Toast.makeText(FacebookLoginActivity.this, "login successfully", Toast.LENGTH_SHORT).show();

                finish();
            }

            @Override
            public void onCancel() {
                // App code

                Toast.makeText(FacebookLoginActivity.this, "canceled", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException exception) {
                // App code

                Toast.makeText(FacebookLoginActivity.this, "error", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        System.out.println( "access token is  :  "+AccessToken.getCurrentAccessToken());

    }

    public void finish_activity(View view) {
    }

    public void logout_btn(View view) {


        LoginManager.getInstance().logOut();
    }

    private void get_logged_in_user_info()
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
                          URL   profilePicture = new URL( "https://graph.facebook.com/" + userId + "/picture?width=500&height=500" );
                            Log.d( "PROFILE_URL", "url: " + profilePicture.toString() );
                           /* if ( object.has( "first_name" ) )
                            {
                                firstName = object.getString( "first_name" );
                            }
                            if ( object.has( "last_name" ) )
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
                Toast.makeText( FacebookLoginActivity.this, "Could not log in.", Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onError( Exception exception )
            {
                Toast.makeText( FacebookLoginActivity.this, "Could not log in.", Toast.LENGTH_SHORT ).show();
            }
        } );
    }
}
