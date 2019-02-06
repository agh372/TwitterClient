package com.arjun.twitterapp.util;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by arjun on 19/5/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AndroidDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "Tweets";
    private static final String TAG = "DBAdapter";
    private static final String TID = "eid";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE + "( " + TID + " integer primary key);";
        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS Tweets");
        onCreate(db);

    }


    //---gets all favorite tweets which
    //    are stored in database
    public Cursor getAllTweets(SQLiteDatabase db) throws SQLException {
        return db.query(TABLE, new String[]{TID}, null, null, null, null, null);
    }


    /**
     * Adds the tweets to Database
     *
     * @param id The id of the particular tweet
     *           .@param db SQLiteDatabase where the tweet ids are added.
     */
    public long insertContact(String id, SQLiteDatabase db) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TID, id);
        return db.insert(TABLE, null, initialValues);
    }


    /**
     * Deletes the tweet from the Database
     *
     * @param rowId The id of the particular tweet
     *              .@param db SQLiteDatabase where the tweet ids are added.
     */
    public boolean deleteContact(long rowId, SQLiteDatabase db) {
        return db.delete(TABLE, TID + "=" + rowId, null) > 0;
    }


}


