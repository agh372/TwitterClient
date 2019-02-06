package com.arjun.twitterapp.adapters;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arjun.twitterapp.R;
import com.arjun.twitterapp.models.Heroes;
import com.arjun.twitterapp.util.BitmapConversion;

import java.util.ArrayList;

/**
 * Created by arjun on 21/5/16.
 */
public class HeroesAdapter extends BaseAdapter {
     private ArrayList<Heroes> mHeroesArrayList;
    private HeroesAdapter.OnTweetSelectedListener mListener;

    public HeroesAdapter(ArrayList<Heroes> heroesArrayList) {
        mHeroesArrayList = heroesArrayList;

    }

    @Override
    public int getCount() {
        return mHeroesArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mHeroesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyViewHolder holder;

        if (convertView == null) {
            // new, used to recycle views
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tweet, null);
            holder = new MyViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.image_hero);
            holder.tweetButton = (Button) convertView.findViewById(R.id.hero_tweet_btn);
            holder.nameLabel = (TextView) convertView.findViewById(R.id.text_name);
            holder.descriptionLabel = (TextView) convertView.findViewById(R.id.text_description);
            holder.modifiedLabel = (TextView) convertView.findViewById(R.id.text_modified);

            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        Heroes heroes = mHeroesArrayList.get(position);
        holder.nameLabel.setText(heroes.getName());
        holder.descriptionLabel.setText(heroes.getDescription());
        holder.modifiedLabel.setText(heroes.getModified());
        holder.iconImageView.setImageBitmap(heroes.getImageBitmap());
        onHeroTweetButtomClick(position, holder.tweetButton);
        return convertView;
    }

    private void onHeroTweetButtomClick(final int position, final Button tweetbtn) {
        tweetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onTweetSelected(mHeroesArrayList.get(position).getName() + "");

            }
        });
    }

    private static class MyViewHolder {
        ImageView iconImageView;
        TextView nameLabel;
        TextView descriptionLabel;
        TextView modifiedLabel;
        Button tweetButton;
    }

    public void setOnTweetClickLister(HeroesAdapter.OnTweetSelectedListener listener) {
        this.mListener = listener;
        Log.d("COOL", "setOnTweetClickLister: ");

    }

    //Creating an interface
    public interface OnTweetSelectedListener {
        void onTweetSelected(String hero);
    }

}
