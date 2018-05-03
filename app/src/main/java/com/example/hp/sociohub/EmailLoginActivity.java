package com.example.hp.sociohub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EmailLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);
    }

    public void open_signup(View view) {

        Intent i= new Intent(EmailLoginActivity.this,CreateAccountActivity.class);
        startActivity(i);
    }

}
