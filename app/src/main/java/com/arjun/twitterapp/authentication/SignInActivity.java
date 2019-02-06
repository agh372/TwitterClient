package com.arjun.twitterapp.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.arjun.twitterapp.R;
import com.arjun.twitterapp.activities.TweetActivity;
import com.arjun.twitterapp.manager.Configuration;
import com.arjun.twitterapp.network.MyTwitterApiClient;
import com.arjun.twitterapp.util.Constants;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;
import retrofit.client.Response;

public class SignInActivity extends AppCompatActivity {
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private String mPrefName = Constants.PREF_NAME;
    private String mPrefKey = Constants.PREF_KEY;
    private TwitterLoginButton mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authentize();
        setContentView(R.layout.activity_sign_in);
        mPref = getSharedPreferences(mPrefName, Context.MODE_PRIVATE);
        initialize();
        getAccessToken();
    }



    //authentize to use the twitter kit
    private void authentize() {
        TwitterAuthConfig authConfig = Configuration.getInstance();
        Fabric.with(this, new Twitter(authConfig));
    }


    private void initialize() {
        mLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
    }


    private void getAccessToken() {
        //To process the result of the login attempt,a Callback is required.
        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            //if login attempt is successful
            public void success(Result<TwitterSession> result) {
                Log.d("token", result.data.getUserId() + "");
                addTokenToSharedPreference(result);
                Intent intent = new Intent(SignInActivity.this, TweetActivity.class);
                startActivity(intent);

            }
            @Override
            //if login attempt is failed
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
    }


    /**
     * Adds the token to SharedPreference
     *
     * @param result The access token to use to sign the request.
     */
    private void addTokenToSharedPreference(Result<TwitterSession> result) {
        mEditor = mPref.edit();
        mEditor.putString(mPrefKey, String.valueOf(result.data.getUserId())).commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginButton.onActivityResult(requestCode, resultCode, data);

    }

}





