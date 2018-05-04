package com.example.hp.sociohub;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;



//class extending the Broadcast Receiver
public class ScheduleAlarm extends BroadcastReceiver {

    //the method will be fired when the alarm is triggerred
    @Override
    public void onReceive(Context context, Intent intent) {

        //you can check the log that it is fired
        //Here we are actually not doing anything
        //but you can do any task here that you want to be done at a specific time everyday
        Log.d("POST SCHEDULED", "Alarm just fired ***************************" + intent.getStringExtra("time"));
        
        DatabaseHelper db = new DatabaseHelper(context);
        
        SchedulePostDataModel dataModel =  db.getPOST(intent.getStringExtra("time"));

        if(dataModel.linkedin.equals("yes")) {
            LISessionManager.getInstance(context).init(LISessionManager.getInstance(context).getSession().getAccessToken());


            String url = "https://api.linkedin.com/v1/people/~/shares";

            JSONObject body = null;
            try {
                body = new JSONObject("{" +
                        "\"comment\": \"" + dataModel.title + "\"," +
                        "\"visibility\": { \"code\": \"anyone\" }," +
                        "\"content\": { " +
                        "\"title\": \"" + dataModel.title + "\"," +
                        "\"description\":\"" + dataModel.description + "\"," +
                        "\"submitted-url\":\"" + url + "\" ," +
                        "\"submitted-image-url\": \"http://www.linkedin.com/\"" +
                        "}" +
                        "}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            APIHelper apiHelper = APIHelper.getInstance(context);
            apiHelper.postRequest(context, url, body, new ApiListener() {
                @Override
                public void onApiSuccess(ApiResponse apiResponse) {
                    // Success!

                    System.out.println("data posted ************************");

                    Toast.makeText(context, "successfully posted on linkedin", Toast.LENGTH_SHORT).show();


                }

                @Override
                public void onApiError(LIApiError liApiError) {
                    // Error making POST request!

                    System.out.println("error ************************" + liApiError);

                    Toast.makeText(context, "error posting on linkedin", Toast.LENGTH_SHORT).show();


                }
            });

        }

        if(dataModel.twitter.equals("yes")) {


            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();


            StatusesService statusesService = twitterApiClient.getStatusesService();
            Call<Tweet> call = statusesService.update(dataModel.title+"    "+dataModel.description, null, true, null, null, null, null, null, null);

            call.enqueue(new Callback<Tweet>() {
                @Override
                public void success(Result<Tweet> result) {
                    //Do something with result

                    Toast.makeText(context, "successfully posted on twitter", Toast.LENGTH_SHORT).show();

                    Log.i("result *** ", "tweet posted");
                }

                public void failure(TwitterException exception) {
                    //Do something on failure
                    Toast.makeText(context, "error posting on twitter", Toast.LENGTH_SHORT).show();


                    Log.i("result fail *** ", "tweet not posted" + exception);

                }
            });

        }
        
    }

}