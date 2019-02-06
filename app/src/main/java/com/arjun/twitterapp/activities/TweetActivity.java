package com.arjun.twitterapp.activities;

/**
 * Created by arjun on 17/5/16.
 */

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arjun.twitterapp.R;
import com.arjun.twitterapp.adapters.TweetPagerAdapter;
import com.arjun.twitterapp.fragments.HeroesFragment;
import com.arjun.twitterapp.manager.Configuration;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;

public class TweetActivity extends AppCompatActivity {


    private ViewPager mTweetPager;
    private TabLayout mTabLayout;
    TweetPagerAdapter adapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authentize();
        setContentView(R.layout.activity_tweet);
        initialize();
        setSupportActionBar(mToolbar);
        setUpTabs();
mTweetPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        try {
            TweetActivity.FragmentInterface frag = (TweetActivity.FragmentInterface) adapter.instantiateItem(mTweetPager, position);
            if (frag != null) {
                frag.FragmentBecameVisible();
            }
        } catch (ClassCastException e) {
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.tweetMenuButton:
                openTweetCompose();
                break;
            case R.id.favMenuButton:
                Intent i = new Intent(TweetActivity.this, FavouriteTweetActivity.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    //authentize to use the twitter kit
    private void authentize() {
        TwitterAuthConfig authConfig = Configuration.getInstance();
        Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());
    }



    //represents the setup of tabs
    private void setUpTabs() {
        FragmentManager manager = getSupportFragmentManager();
        //object of PagerAdapter passing fragment manager object as a parameter of constructor of PagerAdapter class.
         adapter = new TweetPagerAdapter(manager,this);
        //set Adapter to view pager
        mTweetPager.setAdapter(adapter);
        //set tablayout with viewpager
        mTabLayout.setupWithViewPager(mTweetPager);
        //Setting tabs from adapater
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }



    //initializing View variables
    private void initialize() {

        mTweetPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
    }


    private void openTweetCompose() {
        //TODO : Launch the Twitter Application’s Tweet Composer
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        Intent intent = new ComposerActivity.Builder(TweetActivity.this).session(session).createIntent();
        startActivity(intent);
    }

    public interface FragmentInterface {
        void FragmentBecameVisible();
    }


}