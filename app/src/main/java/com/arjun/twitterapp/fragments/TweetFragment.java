package com.arjun.twitterapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.arjun.twitterapp.adapters.CustomTweetTimelineListAdapter;
import com.arjun.twitterapp.manager.Configuration;
import com.arjun.twitterapp.util.DBHelper;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;


import com.arjun.twitterapp.R;
import com.twitter.sdk.android.tweetui.UserTimeline;

import io.fabric.sdk.android.Fabric;

/**
 * Created by arjun on 18/5/16.
 */
public class TweetFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tweets, container, false);
        TwitterAuthConfig authConfig = Configuration.getInstance();
        Fabric.with(getActivity(), new TwitterCore(authConfig), new TweetComposer());
        UserTimeline userTimeline = new UserTimeline.Builder().screenName("fabric").build();

        ListView tweetList = (ListView) view.findViewById(R.id.list);
        CustomTweetTimelineListAdapter adapter = new CustomTweetTimelineListAdapter(getActivity(), userTimeline);
        tweetList.setAdapter(adapter);
        DBHelper dBHelper = new DBHelper(getActivity());
        addTweetToDatabase(adapter,dBHelper);
        return view;
    }

    //button click to add the tweet to favorites
    private void addTweetToDatabase(CustomTweetTimelineListAdapter adapter, final DBHelper dbHelper) {
        //implementing listener callback
       adapter.setOnTweetClickLister(new CustomTweetTimelineListAdapter.OnTweetSelectedListener() {
            @Override
            public void onTweetSelected(String id) {
                AddTweet(id,dbHelper);
            }
        });
    }

    public void AddTweet(String id, DBHelper dbHelper) {
        //---add a Tweet---
        openDatabase(dbHelper);
        if (dbHelper.insertContact(id, dbHelper.getWritableDatabase()) >= 0) {
            Toast.makeText(getActivity(), "Add successful.", Toast.LENGTH_LONG).show();
        }
        closeDatabase(dbHelper);
    }

    private void closeDatabase(DBHelper dbHelper) {
        dbHelper.close();
    }

    private void openDatabase(DBHelper dbHelper) {
        dbHelper.getWritableDatabase();
    }
}
