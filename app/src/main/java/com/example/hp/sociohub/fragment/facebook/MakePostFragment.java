package com.example.hp.sociohub.fragment.facebook;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.hp.sociohub.R;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

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
        return inflater.inflate(R.layout.fragment_make_post, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                .getActiveSession();


        if (session != null) {


            TweetComposer.Builder builder = new TweetComposer.Builder(getActivity())
                    .text("just setting up my Twitter Kit.");
            builder.show();

        } else {


        }
    }


}