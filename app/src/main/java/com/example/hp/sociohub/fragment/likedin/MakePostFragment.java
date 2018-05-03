package com.example.hp.sociohub.fragment.likedin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.sociohub.R;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import org.json.JSONException;
import org.json.JSONObject;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakePostFragment extends Fragment {


    public MakePostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_linkedin_make_post, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LISessionManager.getInstance(getApplicationContext()).init(LISessionManager.getInstance(getContext()).getSession().getAccessToken());


        String url = "https://api.linkedin.com/v1/people/~/shares";

        JSONObject body = null;
        try {
            body = new JSONObject("{" +
                    "\"comment\": \"Sample share\"," +
                    "\"visibility\": { \"code\": \"anyone\" }," +
                    "\"content\": { " +
                    "\"title\": \"Sample share\"," +
                    "\"description\": \"Testing the mobile SDK call wrapper!\"," +
                    "\"submitted-url\": \"http://www.example.com/\"," +
                    "\"submitted-image-url\": \"http://www.example.com/pic.jpg\"" +
                    "}" +
                    "}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        APIHelper apiHelper = APIHelper.getInstance(getActivity());
        apiHelper.postRequest(getContext(), url, body, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                // Success!

                System.out.println("data posted ************************");
            }

            @Override
            public void onApiError(LIApiError liApiError) {
                // Error making POST request!

                System.out.println("error ************************"+liApiError);

            }
        });


    }


}