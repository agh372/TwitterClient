package com.arjun.twitterapp.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.arjun.twitterapp.R;
import com.arjun.twitterapp.util.DBHelper;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.TweetViewFetchAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavouriteTweetActivity extends AppCompatActivity {
    private DBHelper mDBHelper;
    private TweetViewFetchAdapter mTweetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_tweet);
        mTweetAdapter = new TweetViewFetchAdapter<CompactTweetView>(FavouriteTweetActivity.this);
        ArrayList<Long> tweetlist = new ArrayList<Long>();
        mDBHelper = new DBHelper(this);
        tweetlist = setAdapterToList(tweetlist);
        callBackSetTweets(tweetlist);
    }



    private ArrayList<Long> setAdapterToList(ArrayList<Long> tweetlist) {
        ListView tweetList = (ListView) findViewById(R.id.list);
        tweetlist = getFavoriteTweets(tweetlist);
        tweetList.setAdapter(mTweetAdapter);
        return tweetlist;
    }



    private void callBackSetTweets(ArrayList<Long> tweetlist) {
        mTweetAdapter.setTweetIds(tweetlist, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                Toast.makeText(getApplicationContext(), "Favorites", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getApplicationContext(), "Try later", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private ArrayList<Long> getFavoriteTweets(ArrayList<Long> arraylist) {
        openDatabase();
        Cursor c = mDBHelper.getAllTweets(mDBHelper.getWritableDatabase());
        transferDatabaseToList4(arraylist, c);
        closeDatabase();
        return arraylist;
    }


    /**
     * Collecting tweet ids and storing in an arraylist
     *
     * @param tweetlist Arraylist which contains all tweet ids
     * @param  cursor  It is positioned for first entry, and keeps iterating
     */
    private void transferDatabaseToList4(ArrayList<Long> tweetlist, Cursor cursor) {
        if (cursor.moveToFirst()) {
            do {
                tweetlist.add(cursor.getLong(0));
            } while (cursor.moveToNext());
        }
    }



    private void closeDatabase() {
        mDBHelper.close();
    }

    private void openDatabase() {
        mDBHelper.getWritableDatabase();
    }
}
