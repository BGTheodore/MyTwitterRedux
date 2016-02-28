package com.codepath.apps.mysimpletweets.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.mysimpletweets.enums.TweetType;
import com.codepath.apps.mysimpletweets.fragments.TweetListFragment;

/**
 * Created by carlybaja on 2/21/16.
 */
public class TweeterPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"Home","Mentions"};

    // Adapter get the manager insert or remove fragment from the activity
    public TweeterPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // How many fragments there are to swip betwenn in this case it's two.
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    // The order and creation of fragments within the pager
    @Override
    public Fragment getItem(int position) {
        TweetType tweetType = TweetType.values()[position];
        return TweetListFragment.newInstance(tweetType.ordinal(), null);
    }

    // Return the tab title
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
