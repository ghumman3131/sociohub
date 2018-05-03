package com.example.hp.sociohub;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoginResult;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SubscribeLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_layout);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



    }

    public void login(View view) {

        EditText username = findViewById(R.id.username_et);

        EditText password = findViewById(R.id.password_et);



        loginToInstagram(username.getText().toString() , password.getText().toString() );

    }

    private void loginToInstagram(final String username, final String password) {

        Instagram4Android instagram = Instagram4Android.builder().username(username).password(password).build();

        attemptLogin(instagram)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(instagramLoginResult -> {

                    if(instagramLoginResult.getStatus().equals("")) {
                        System.out.println("sucessss");
                    } else {
                        System.out.println("faileddd");
                    }

                });

    }

    private Observable<InstagramLoginResult> attemptLogin(final Instagram4Android instagram) {

        Observable<InstagramLoginResult> observable = Observable.create(observableEmitter -> {

            instagram.setup();
            try {
                observableEmitter.onNext(instagram.login());
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        return observable;

    }
}
