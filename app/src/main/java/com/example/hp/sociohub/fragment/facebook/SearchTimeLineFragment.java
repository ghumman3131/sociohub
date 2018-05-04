package com.example.hp.sociohub.fragment.facebook;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.hp.sociohub.R;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTimeLineFragment extends Fragment {


    private SearchView searchView ;

    public SearchTimeLineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_time_line, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        searchView = view.findViewById(R.id.search_view);


        final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                .getActiveSession();


        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        if(session != null) {


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    searchView.clearFocus();

                    final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                            .query("#"+query)
                            .maxItemsPerRequest(50)
                            .build();

                    final TweetTimelineRecyclerViewAdapter adapter =
                            new TweetTimelineRecyclerViewAdapter.Builder(getContext())
                                    .setTimeline(searchTimeline)
                                    .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                                    .build();

                    recyclerView.setAdapter(adapter);

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });




        }

        else {


        }
    }


}
