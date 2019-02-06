package com.arjun.twitterapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by arjun on 23/5/16.
 */
class BitmapTask {
    /**
     * returns the particular bitmap from the url
     *
     * @param src The url in String format.
     */
    public Bitmap getBitmap(String src) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(src)
                .openConnection();
        connection.setDoInput(true);
        connection.connect();
        return BitmapFactory.decodeStream(connection.getInputStream());
    }

    /**
     * returns the particular bitmap from the url
     *
     * @param bitmap The bitmap in which modification is required
     * @param width The width to which it must be resized.
     * @param height The height to which it must be resized.
     */
    public Bitmap resize(Bitmap bitmap, int width, int height) {

        float scaleWidth = ((float) width) / bitmap.getWidth();
        float scaleHeight = ((float) height) /bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, false);
        return resizedBitmap;
    }
}