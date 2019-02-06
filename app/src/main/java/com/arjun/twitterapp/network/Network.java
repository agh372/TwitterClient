package com.arjun.twitterapp.network;

/**
 * Created by arjun on 21/5/16.
 */

import android.content.Context;

import com.arjun.twitterapp.R;
import com.arjun.twitterapp.util.DataConversion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Network {

    private String mQuery;

    public Network(String query) {
        mQuery = query;
    }

    public JSONObject getJsonObject(Context context) throws IOException, JSONException {

        URL url = new URL(mQuery);
        JSONObject jsonObject;
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(context.getString(R.string.get_method));
        urlConnection.connect();
        InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
        String jsonString = DataConversion.ConvertString(inputStream);
        jsonObject = new JSONObject(jsonString);
        return jsonObject;
    }
}