package com.arjun.twitterapp.network;

import com.arjun.twitterapp.models.Followers;
import com.twitter.sdk.android.core.Callback;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by arjun on 26/5/16.
 */
public interface CustomService {@GET("/1.1/followers/list.json")
                         void show(@Query("user_id") Long userId, @Query("screen_name") String
                var, @Query("skip_status") Boolean var1, @Query("include_user_entities") Boolean var2, @Query("count") Integer var3, Callback<Followers> cb);
}

