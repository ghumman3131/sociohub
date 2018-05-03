package com.example.hp.sociohub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.linkedin.platform.AccessToken;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

public class LinkedinLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkedin_login);
    }

    public void signin(View view) {


        LISessionManager.getInstance(getApplicationContext()).init(LinkedinLoginActivity.this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                // Authentication was successful.  You can now do
                // other calls with the SDK.

                Toast.makeText(LinkedinLoginActivity.this , "logged in successfully" , Toast.LENGTH_SHORT).show();


                Log.i("Access token->", LISessionManager.getInstance(getApplicationContext()).getSession().getAccessToken().getValue());

                finish();
            }

            @Override
            public void onAuthError(LIAuthError error) {

                Toast.makeText(LinkedinLoginActivity.this , "error" , Toast.LENGTH_SHORT).show();

                Log.i("Access token error ->", String.valueOf(error));

                // Handle authentication errors
            }
        }, true);
    }


    // Build the list of member permissions our LinkedIn session requires
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Add this line to your existing onActivityResult() method
        LISessionManager.getInstance(LinkedinLoginActivity.this).onActivityResult(LinkedinLoginActivity.this, requestCode, resultCode, data);
       // Log.i("Access token->", LISessionManager.getInstance(getApplicationContext()).getSession().getAccessToken().getValue());

    }

}
