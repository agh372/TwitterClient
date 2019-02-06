package com.arjun.twitterapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arjun.twitterapp.R;
import com.arjun.twitterapp.activities.TweetActivity;
import com.arjun.twitterapp.adapters.HeroesAdapter;
import com.arjun.twitterapp.models.Heroes;
import com.arjun.twitterapp.network.AsyncResponse;
import com.arjun.twitterapp.util.JSONParser;
import com.arjun.twitterapp.network.Network;
import com.arjun.twitterapp.util.Constants;
import com.arjun.twitterapp.util.ImageCache;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;


import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by arjun on 21/5/16.
 */
public class HeroesFragment extends android.support.v4.app.Fragment implements AsyncResponse, TweetActivity.FragmentInterface {
    private ListView mHeroeslistView;
    private AsyncTaskRunner mGetData;
    private EditText mHeroEditText;
    private Button mSearchBtn;
    private ImageCache mImageCache;
    HeroesAdapter adapter;
    public static int sCount;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_heroes, container, false);


        mImageCache = ImageCache.getInstance(getFragmentManager(), (float) 0.7,getContext());
        initialize(rootView);
        searchButtonListener();
        displayCachedData();
        if (!isNetworkAvailable()) {
            enableButton(false);
        }
        return rootView;
    }

    //button click to add the tweet to favorites
    private void addTweetToDatabase(HeroesAdapter adapter) {
        //implementing listener callback
       // if(adapter != null) {
            adapter.setOnTweetClickLister(new HeroesAdapter.OnTweetSelectedListener() {
                @Override
                public void onTweetSelected(String name) {
                    openTweetCompose(name);
                }
            });
      //  }
    }
    private void openTweetCompose(String name) {
        //TODO : Launch the Twitter Application’s Tweet Composer
        Resources res = getResources();
        String text = String.format(res.getString(R.string.comic_tweet), name, name);
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        TweetComposer.Builder builder = new TweetComposer.Builder(getContext())
                .text(text);
        builder.show();
    }

    private void enableButton(boolean enabled) {
        mSearchBtn.setEnabled(enabled);
    }


    private void initialize(View rootView) {

        mHeroeslistView = (ListView) rootView.findViewById(R.id.list);
        mGetData = new AsyncTaskRunner();
        mGetData.mResponseListener = this;
        mHeroEditText = (EditText) rootView.findViewById(R.id.edit_search);
        mSearchBtn = (Button) rootView.findViewById(R.id.btn_search);
    }


    private void displayCachedData() {
        if (!isNetworkAvailable()) {
            /*if (getAsyncTaskStatus(mGetData))
                mGetData.execute();*/
            offlineGetList();
        }
    }


    //triggers an event when search button is clicked
    private void searchButtonListener() {

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageCache.clearCache();
                if (getAsyncTaskStatus(mGetData))
                    mGetData.execute(mHeroEditText.getText().toString());
            }
        });
    }

    private void offlineGetList() {
        ArrayList<Heroes> heroesList = new ArrayList<Heroes>();
        for (int i = 0; i < sCount; i++) {
            Heroes heroes = mImageCache.getBitmapFromMemCache("bitmap" + i);
            heroesList.add(heroes);
        }
        HeroesAdapter adapter = new HeroesAdapter(heroesList);
        mHeroeslistView.setAdapter(adapter);
        addTweetToDatabase(adapter);

        adapter.notifyDataSetChanged();
      //  mResponseListener.processFinish(heroesList);
    }
    @Override
    public void processFinish(ArrayList<Heroes> output) {
        HeroesAdapter adapter = new HeroesAdapter(output);
        mHeroeslistView.setAdapter(adapter);
        addTweetToDatabase(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void FragmentBecameVisible() {
        ArrayList<Heroes> heroesList = new ArrayList<Heroes>();
        for (int i = 0; i < sCount; i++) {
            Heroes heroes = mImageCache.getBitmapFromMemCache("bitmap" + i);
            heroesList.add(heroes);
        }
        adapter = new HeroesAdapter(heroesList);
        mHeroeslistView.setAdapter(adapter);
        addTweetToDatabase(adapter);
        adapter.notifyDataSetChanged();

    }



    public class AsyncTaskRunner extends AsyncTask<String,Void, JSONObject> implements AsyncResponse {
        private AsyncResponse mResponseListener = null;

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject heroesObject = new JSONObject();
            Network data = new Network(Constants.URL + params[0] + Constants.KEYS);
            try {
                heroesObject = data.getJsonObject(getContext());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return heroesObject;
        }


        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            JSONParser parser = new JSONParser(jsonObject, getFragmentManager(),getContext());
            if (jsonObject.length() == 0) {
              Toast.makeText(getContext(), R.string.no_data,Toast.LENGTH_SHORT).show();
            } else {
                onlineGetList(parser);
            }
            mGetData = new AsyncTaskRunner();
            mGetData.mResponseListener = this;
        }


        private void onlineGetList(JSONParser parser) {
            ArrayList<Heroes> heroesArrayList;
            try {
                heroesArrayList = parser.jsonParse();
                for (int i = 0; i < heroesArrayList.size(); i++)
                    mResponseListener.processFinish(heroesArrayList);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        @Override
        public void processFinish(ArrayList<Heroes> output) {
            HeroesAdapter adapter = new HeroesAdapter(output);
            mHeroeslistView.setAdapter(adapter);
            addTweetToDatabase(adapter);
            adapter.notifyDataSetChanged();

        }
    }

    //Checks the network availability
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

   //Checks if asynctask is running
    private boolean getAsyncTaskStatus(AsyncTaskRunner async) {
        if (async.getStatus() == AsyncTask.Status.RUNNING)
            return false;
        else
            return true;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

}