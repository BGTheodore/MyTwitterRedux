package com.codepath.apps.mysimpletweets.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.adapters.TweeterPagerAdapter;
import com.codepath.apps.mysimpletweets.adapters.TweetsAdapter;
import com.codepath.apps.mysimpletweets.dialogs.PostTweetDialog;
import com.codepath.apps.mysimpletweets.fragments.TweetListFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.apps.mysimpletweets.utils.Network;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.astuetz.PagerSlidingTabStrip;


import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TimelineActivity extends AppCompatActivity implements PostTweetDialog.PostTweetDialogListener, TweetListFragment.TweetListFragmentListener {
    private static final int POST_TWEET_REQUEST_CODE = 341;

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsAdapter aTweets;
    private ListView lvTweets;

    private TweetListFragment tweetListFragment;
    private User user;

    private boolean isFetching;

    private MenuItem miActionProgressItem;

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.viewpager) ViewPager mViewPager;
    @Bind(R.id.tabs) PagerSlidingTabStrip mTabsStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle("My Simple Tweets");
        mToolbar.setTitleTextColor(Color.WHITE);
        showProgressBar();

        client = TwitterApplication.getRestClient();
        getUserProfile();

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager.setAdapter(new TweeterPagerAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager and attach it to the tab strip
        mTabsStrip.setViewPager(mViewPager);
        hideProgressBar();
    }

    private void getUserProfile() {
        if (!Network.isNetworkAvailable(this)) {
            Toast.makeText(getApplicationContext(), "No internet! Try again later...", Toast.LENGTH_SHORT).show();
        }
        if (isFetching) {
            return;
        }

        client.getUserProfile(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                Log.d("DEBUG", "user: " + jsonObject.toString());
                user = new User(jsonObject);
            }


            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  Throwable throwable,
                                  JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_timeline, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                //  searchOptions.searchTerm = query;
                // submitQuery(true);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_tweet) {
            FragmentManager fm = getSupportFragmentManager();
            PostTweetDialog postTweetDialog = PostTweetDialog.newInstance(user, null);
            postTweetDialog.show(fm, "fragment_post_tweet");
        } else if (id == R.id.action_profile) {
            showUserProfile(user);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPost(Tweet tweet) {
        if (tweet != null) {
            // pass tweet to tweet list fragment
            tweetListFragment.insertTweet(tweet);
        }
    }

    @Override
    public void onReplyTweet(Tweet tweet) {
        FragmentManager fm = getSupportFragmentManager();
        PostTweetDialog postTweetDialog = PostTweetDialog.newInstance(user, tweet);
        postTweetDialog.show(fm, "fragment_reply_tweet");
    }

    @Override
    public void onViewUserProfile(User user) {
        if (user != null) {
            showUserProfile(user);
        }
    }

    private void showUserProfile(User user) {
        Intent userProfileIntent = new Intent(this, UserProfileActivity.class);
        userProfileIntent.putExtra("user", user);
        startActivity(userProfileIntent);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar() {
        // Show progress item
        if (miActionProgressItem != null) {
            miActionProgressItem.setVisible(true);;
        }
    }

    public void hideProgressBar() {
        // Hide progress item
        if(miActionProgressItem != null) {
            miActionProgressItem.setVisible(false);
        }
    }

}
