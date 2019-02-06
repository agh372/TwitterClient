package com.arjun.twitterapp.models;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

/**
 * Created by arjun on 26/5/16.
 */
public class Followers {
    @SerializedName("users")
    private final List<User> mUsers;

    public List<User> getUsers() {
        return mUsers;
    }

    public Followers(List<User> users) {
        this.mUsers = users;
    }
}