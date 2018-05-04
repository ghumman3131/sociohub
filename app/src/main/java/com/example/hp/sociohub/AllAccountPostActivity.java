package com.example.hp.sociohub;

import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
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

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

import static com.facebook.FacebookSdk.getApplicationContext;


public class AllAccountPostActivity extends AppCompatActivity {


    private EditText title_et , description_et , url_et ;
    
    private Button post_btn ;
    
    private CheckBox facebook_check_btn , twitter_check_btn , linkedin_check_btn ;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_account_post);

        title_et = findViewById(R.id.title_et);
        description_et = findViewById(R.id.description_et);
        url_et = findViewById(R.id.url_et);

        post_btn = findViewById(R.id.post_btn);
        
        facebook_check_btn = findViewById(R.id.facebook_radio_btn);
        
        twitter_check_btn = findViewById(R.id.twitter_radio_btn);
        
        linkedin_check_btn = findViewById(R.id.linkedin_radio_btn);
        
        if(!MainActivity.isFacebook)
        {
            facebook_check_btn.setEnabled(false);

            facebook_check_btn.setTextColor(Color.DKGRAY);


        }
        
        if(!MainActivity.isTwitter)
        {
            twitter_check_btn.setEnabled(false);
        }
        
        if(!MainActivity.isLinkedin)
        {
            linkedin_check_btn.setEnabled(false);
        }

       
    }


    private void post_()
    {

        String title = title_et.getText().toString();

        String description = description_et.getText().toString();

        String share_url = url_et.getText().toString();

        if(title.equals(""))
        {
            Toast.makeText(AllAccountPostActivity.this , "please enter title " , Toast.LENGTH_SHORT).show();
            return;
        }

        if(linkedin_check_btn.isChecked()) {
            LISessionManager.getInstance(getApplicationContext()).init(LISessionManager.getInstance(AllAccountPostActivity.this).getSession().getAccessToken());


            String url = "https://api.linkedin.com/v1/people/~/shares";

            JSONObject body = null;
            try {
                body = new JSONObject("{" +
                        "\"comment\": \"" + title + "\"," +
                        "\"visibility\": { \"code\": \"anyone\" }," +
                        "\"content\": { " +
                        "\"title\": \"" + title + "\"," +
                        "\"description\":\"" + description + "\"," +
                        "\"submitted-url\":\"" + url + "\" ," +
                        "\"submitted-image-url\": \"http://www.linkedin.com/\"" +
                        "}" +
                        "}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            APIHelper apiHelper = APIHelper.getInstance(AllAccountPostActivity.this);
            apiHelper.postRequest(AllAccountPostActivity.this, url, body, new ApiListener() {
                @Override
                public void onApiSuccess(ApiResponse apiResponse) {
                    // Success!

                    System.out.println("data posted ************************");

                    Toast.makeText(AllAccountPostActivity.this, "successfully posted on linkedin", Toast.LENGTH_SHORT).show();

                    title_et.setText("");
                    description_et.setText("");
                    url_et.setText("");
                }

                @Override
                public void onApiError(LIApiError liApiError) {
                    // Error making POST request!

                    System.out.println("error ************************" + liApiError);

                    Toast.makeText(AllAccountPostActivity.this, "error posting on linkedin", Toast.LENGTH_SHORT).show();


                }
            });

        }

        if(twitter_check_btn.isChecked()) {


            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();


            StatusesService statusesService = twitterApiClient.getStatusesService();
            Call<Tweet> call = statusesService.update(title+"    "+description, null, true, null, null, null, null, null, null);

            call.enqueue(new Callback<Tweet>() {
                @Override
                public void success(Result<Tweet> result) {
                    //Do something with result

                    Toast.makeText(AllAccountPostActivity.this, "successfully posted on twitter", Toast.LENGTH_SHORT).show();

                    Log.i("result *** ", "tweet posted");
                }

                public void failure(TwitterException exception) {
                    //Do something on failure
                    Toast.makeText(AllAccountPostActivity.this, "error posting on twitter", Toast.LENGTH_SHORT).show();


                    Log.i("result fail *** ", "tweet not posted" + exception);

                }
            });

        }
    }

    public void post_now(View view) {
        

        post_();
    }

    public void schedule_post(View view) {

        String title = title_et.getText().toString();

        String description = description_et.getText().toString();

        String share_url = url_et.getText().toString();

        String facebook = "no";

        String instagram = "no";

        String twitter = "no";

        String linkedin = "no";

        if(title.equals(""))
        {
            Toast.makeText(AllAccountPostActivity.this , "please enter title " , Toast.LENGTH_SHORT).show();
            return;
        }

        if(linkedin_check_btn.isChecked()) {

            linkedin = "yes";
        }

        if(twitter_check_btn.isChecked())
        {
            twitter = "yes";
        }

        new SchedulePostDIalog(AllAccountPostActivity.this , R.style.Dialog , title , description , share_url , facebook , instagram , twitter , linkedin).show();
    }


}
