package com.arjun.twitterapp.manager;


import com.arjun.twitterapp.util.Constants;
import com.twitter.sdk.android.core.TwitterAuthConfig;

/**
 * Created by arjun on 18/5/16.
 */
public class Configuration {

    private static TwitterAuthConfig sAuthConfig ;
    private Configuration() {

    }
//returns one instance of TwitterAuthConfig
    public static TwitterAuthConfig getInstance() {
        if(sAuthConfig == null) {
            sAuthConfig = new TwitterAuthConfig(Constants.TWITTER_KEY,Constants.TWITTER_SECRET);
        }
        return sAuthConfig;
    }


}
