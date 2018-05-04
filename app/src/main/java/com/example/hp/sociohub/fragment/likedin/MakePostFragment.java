package com.example.hp.sociohub.fragment.likedin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


    private EditText title_et , description_et , url_et ;

    private Button post_btn ;

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

        title_et = view.findViewById(R.id.title_et);
        description_et = view.findViewById(R.id.description_et);
        url_et = view.findViewById(R.id.url_et);

        post_btn = view.findViewById(R.id.post_btn);

        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String title = title_et.getText().toString();

                String description = description_et.getText().toString();

                String share_url = url_et.getText().toString();

                if(title.equals(""))
                {
                    Toast.makeText(getContext() , "please enter title " , Toast.LENGTH_SHORT).show();
                    return;
                }

                LISessionManager.getInstance(getApplicationContext()).init(LISessionManager.getInstance(getContext()).getSession().getAccessToken());


                String url = "https://api.linkedin.com/v1/people/~/shares";

                JSONObject body = null;
                try {
                    body = new JSONObject("{" +
                            "\"comment\": \""+title+"\"," +
                            "\"visibility\": { \"code\": \"anyone\" }," +
                            "\"content\": { " +
                            "\"title\": \""+title+"\"," +
                            "\"description\":\""+description+"\"," +
                            "\"submitted-url\":\""+url+"\" ," +
                            "\"submitted-image-url\": \"http://www.linkedin.com/\"" +
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

                        Toast.makeText(getActivity() , "successfully posted" , Toast.LENGTH_SHORT).show();

                        title_et.setText("");
                        description_et.setText("");
                        url_et.setText("");
                    }

                    @Override
                    public void onApiError(LIApiError liApiError) {
                        // Error making POST request!

                        System.out.println("error ************************"+liApiError);

                        Toast.makeText(getActivity() , "error" , Toast.LENGTH_SHORT).show();


                    }
                });


            }
        });




    }


}