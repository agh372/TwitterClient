package com.arjun.twitterapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.arjun.twitterapp.R;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import java.util.ArrayList;

/**
 * Created by arjun on 19/5/16.
 */
public class CustomTweetTimelineListAdapter extends TweetTimelineListAdapter {

    private OnTweetSelectedListener mListener;


    public CustomTweetTimelineListAdapter(Context context, Timeline<Tweet> timeline) {
        super(context, timeline);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        RelativeLayout tweetLayout = (RelativeLayout)view.findViewById(R.id.tw__tweet_view);
        Button favbtn = addButtonToLayout(tweetLayout,parent);
        onFavoriteButtomClick(position, favbtn);
        return view;
    }



    private void onFavoriteButtomClick(final int position, final Button favbtn) {
        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onTweetSelected(getItemId(position) + "");

            }
        });
    }

    private Button addButtonToLayout(RelativeLayout tweetLayout,ViewGroup parent) {
        Button favbtn = new Button(tweetLayout.getContext());
        favbtn.setText(R.string.add_to_fav);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.bottom_separator);
        favbtn.setLayoutParams(params);
        favbtn.setBackgroundColor(parent.getResources().getColor(R.color.blue));
        favbtn.setTextColor(parent.getResources().getColor(R.color.white));

        tweetLayout.addView(favbtn);
        return favbtn;
    }


    public void setOnTweetClickLister(OnTweetSelectedListener listener) {
        this.mListener = listener;

    }

    //Creating an interface
    public interface OnTweetSelectedListener {
        void onTweetSelected(String number);
    }


}