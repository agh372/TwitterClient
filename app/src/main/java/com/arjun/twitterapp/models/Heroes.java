package com.arjun.twitterapp.models;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by arjun on 21/5/16.
 */
public class Heroes implements Serializable {
    private String mName;
    private Bitmap mImageBitmap;
    private String mImageUrl;
    private String mModified;
    private String mDescription;

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getModified() {
        return mModified;
    }

    public void setModified(String modified) {
        mModified = modified;
    }

    public Bitmap getImageBitmap() {
        return mImageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        mImageBitmap = imageBitmap;
    }
}
