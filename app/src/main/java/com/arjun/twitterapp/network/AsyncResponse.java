package com.arjun.twitterapp.network;

import android.content.Context;

import com.arjun.twitterapp.models.Heroes;

import java.util.ArrayList;

/**
 * Created by arjun on 21/5/16.
 */
public interface AsyncResponse {
    void processFinish(ArrayList<Heroes> output);
}