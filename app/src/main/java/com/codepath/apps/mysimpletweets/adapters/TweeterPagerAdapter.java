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
    final int PAGE_COUNT = 1;
    private String tabTitles[] = new String[]{"Home"};

    public TweeterPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        TweetType tweetType = TweetType.values()[position];
        return TweetListFragment.newInstance(tweetType.ordinal(), null);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
