package com.arjun.twitterapp.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.arjun.twitterapp.R;
import com.arjun.twitterapp.adapters.FollowerAdapter;
import com.arjun.twitterapp.models.Followers;
import com.arjun.twitterapp.network.MyTwitterApiClient;
import com.arjun.twitterapp.util.Constants;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by arjun on 18/5/16.
 */
public class FollowersFragment extends android.support.v4.app.Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragments_followers, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPref", 0);
        Long userId = Long.parseLong(preferences.getString(Constants.PREF_KEY, "0"));
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        getFollowersCallback(rootView, userId, session);
        return rootView;
    }



   //responds to retrieving followers by either success or failure
    private void getFollowersCallback(final View rootView, Long userId, TwitterSession session) {

        new MyTwitterApiClient(session).getCustomService().show(userId, null, true, true, 100, new Callback<Followers>() {
            @Override
            public void success(Result< Followers > result) {
            Log.i("Get success", "" + result.data.getUsers().size());
                ListView followerslistView = (ListView) rootView.findViewById(R.id.list);
                FollowerAdapter adapter = new FollowerAdapter(result);
                followerslistView.setAdapter(adapter);
        }

            @Override
            public void failure(TwitterException e) {
                Toast.makeText(getContext(), "Cant retrieve", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
