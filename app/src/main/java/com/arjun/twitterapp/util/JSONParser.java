package com.arjun.twitterapp.util;

/**
 * Created by arjun on 21/5/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.arjun.twitterapp.fragments.HeroesFragment;
import com.arjun.twitterapp.models.Heroes;
import com.arjun.twitterapp.util.BitmapConversion;
import com.arjun.twitterapp.util.ImageCache;


public class JSONParser {

    private JSONObject mHeroesJsonObject;
    ImageCache mImageCache;

    public JSONParser(JSONObject jsonObject,FragmentManager fm,Context context) {
        mHeroesJsonObject = jsonObject;
        mImageCache=ImageCache.getInstance(fm, (float) 0.7,context);
        HeroesFragment.sCount=0;

    }

    public ArrayList<Heroes> jsonParse() throws IOException, JSONException {

        ArrayList<Heroes> heroesArrayList = new ArrayList<>();
        JSONObject jsonObject = mHeroesJsonObject.getJSONObject("data");
        JSONArray jsonArray = jsonObject.getJSONArray("results");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject heroesObject = jsonArray.getJSONObject(i);
            final Heroes hero = new Heroes();
            hero.setName(heroesObject.getString("name"));
            hero.setDescription(heroesObject.getString("description"));
            hero.setModified(heroesObject.getString("modified"));
            final JSONObject thumbnailObject = heroesObject.getJSONObject("thumbnail");
            BitmapConversion convertBitmap = new BitmapConversion();
            hero.setImageUrl(thumbnailObject.getString("path") + "." + thumbnailObject.getString("extension"));
            convertBitmap.execute(thumbnailObject.getString("path") + "." + thumbnailObject.getString("extension"));
            final int finalI = i;
            //function gets triggered when bitmap is ready
            convertBitmap.setOnBitmapCompleteLister(new BitmapConversion.OnCompleteListener() {
                @Override
                public void onClickSelected(Bitmap bitmap) {
                    HeroesFragment.sCount++;
                    hero.setImageBitmap(bitmap);
                    mImageCache.addBitmapToCache("bitmap" + finalI, hero);

                }
            });

            heroesArrayList.add(hero);

        }

        return heroesArrayList;
    }


}