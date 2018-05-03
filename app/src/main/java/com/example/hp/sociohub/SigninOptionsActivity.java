package com.example.hp.sociohub;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SigninOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_options);

       /* try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.hp.sociohub",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/
    }
    public void continue_with_mail(View v){
        Intent i=new Intent(SigninOptionsActivity.this,EmailLoginActivity.class);
        startActivity(i);
    }

    public void continue_with_facebook(View view) {

        Intent i = new Intent(SigninOptionsActivity.this , Home_page.class);

        startActivity(i);
    }
}
