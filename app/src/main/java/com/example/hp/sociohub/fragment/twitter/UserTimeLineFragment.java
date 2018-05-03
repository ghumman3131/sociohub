package com.example.hp.sociohub.fragment.twitter;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.sociohub.R;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserTimeLineFragment extends Fragment {

    TwitterLoginButton loginButton ;

    public UserTimeLineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_time_line, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginButton = (TwitterLoginButton) view.findViewById(R.id.login_button);


        final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                .getActiveSession();


        if(session != null) {


            final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            final UserTimeline userTimeline = new UserTimeline.Builder()
                    .screenName(session.getUserName())
                    .build();

            final TweetTimelineRecyclerViewAdapter adapter =
                    new TweetTimelineRecyclerViewAdapter.Builder(getContext())
                            .setTimeline(userTimeline)
                            .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                            .build();

            recyclerView.setAdapter(adapter);
        }


    }



    @Override
    public void onResume() {
        super.onResume();

    }
}
