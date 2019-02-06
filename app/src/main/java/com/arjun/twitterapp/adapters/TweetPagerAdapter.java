package com.arjun.twitterapp.adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.arjun.twitterapp.R;
import com.arjun.twitterapp.fragments.FollowersFragment;
import com.arjun.twitterapp.fragments.HeroesFragment;
import com.arjun.twitterapp.fragments.TweetFragment;

/**
 * Created by arjun on 18/5/16.
 */
public class TweetPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public  TweetPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        mContext = context;
    }



    @Override
    public Fragment getItem(int position) {

        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new TweetFragment();
                break;
            case 1:
                fragment = new FollowersFragment();
                break;
            case 2:
                fragment= new HeroesFragment();
                break;
            default:
                return null;

        }
        return fragment;
    }

    

    @Override
    public int getCount() {
        return 3;
    }



    @Override
    public CharSequence getPageTitle(int position) {
        String title = " ";
        switch (position) {
            case 0:
                title = mContext.getString(R.string.tweets);
                break;
            case 1:
                title = mContext.getString(R.string.followers);
                break;
            case 2:
                title ="heroes";
                break;

            default:
                return null;

        }

        return title;
    }
}