package com.arjun.twitterapp.util;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

/**
 * Created by arjun on 21/5/16.
 */


public class BitmapConversion extends AsyncTask<String, Void, Bitmap> {

    private OnCompleteListener mListener;

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            BitmapTask convert = new BitmapTask();
            bitmap = convert.getBitmap(params[0]);
            bitmap = convert.resize(bitmap, 200, 200);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mListener.onClickSelected(bitmap);
    }

    public void setOnBitmapCompleteLister(OnCompleteListener listener) {
        this.mListener = listener;

    }

    //Creating an interface
    public interface OnCompleteListener {
        void onClickSelected(Bitmap bitmap);
    }

}
