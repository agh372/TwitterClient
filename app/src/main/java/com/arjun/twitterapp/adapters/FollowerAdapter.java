package com.arjun.twitterapp.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arjun.twitterapp.R;
import com.arjun.twitterapp.fragments.HeroesFragment;
import com.arjun.twitterapp.models.Followers;
import com.arjun.twitterapp.models.Heroes;
import com.arjun.twitterapp.util.BitmapConversion;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.internal.TwitterApiConstants;
import com.twitter.sdk.android.core.models.User;

/**
 * Created by arjun on 26/5/16.
 */
public class FollowerAdapter extends BaseAdapter {
    Result<Followers> mFollowersList;

    public FollowerAdapter(Result<Followers> result) {
        mFollowersList = result;

    }

    @Override
    public int getCount() {
        return mFollowersList.data.getUsers().size();
    }

    @Override
    public Object getItem(int position) {
        return mFollowersList.data.getUsers().get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyViewHolder holder;

        if (convertView == null) {
            // new, used to recycle views
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_followers, null);
            holder = new MyViewHolder();
            holder.mIconImageView = (ImageView) convertView.findViewById(R.id.tw__tweet_author_avatar);
            holder.mNameLabel = (TextView) convertView.findViewById(R.id.tw__tweet_author_full_name);
            holder.mDescriptionLabel = (TextView) convertView.findViewById(R.id.tw__tweet_text);
            holder.mModifiedLabel = (TextView) convertView.findViewById(R.id.tw__tweet_timestamp);
            holder.mScreenNameLabel = (TextView) convertView.findViewById(R.id.tw__tweet_author_screen_name);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        holder.mNameLabel.setText(mFollowersList.data.getUsers().get(position).name);
        holder.mDescriptionLabel.setText(mFollowersList.data.getUsers().get(position).description);
        holder.mModifiedLabel.setText(mFollowersList.data.getUsers().get(position).createdAt);
        holder.mScreenNameLabel.setText(mFollowersList.data.getUsers().get(position).screenName);
        BitmapConversion conversion = new BitmapConversion();
        conversion.execute(mFollowersList.data.getUsers().get(position).profileImageUrl);

        conversion.setOnBitmapCompleteLister(new BitmapConversion.OnCompleteListener() {
            @Override
            public void onClickSelected(Bitmap bitmap) {
                holder.mIconImageView.setImageBitmap(bitmap);
            }
        });
        return convertView;
    }

    private class MyViewHolder {

        private ImageView mIconImageView;
        private TextView mNameLabel;
        private TextView mScreenNameLabel;
        private TextView mDescriptionLabel;
        private TextView mModifiedLabel;

    }

}
