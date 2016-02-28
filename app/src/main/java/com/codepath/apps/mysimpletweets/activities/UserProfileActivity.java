package com.codepath.apps.mysimpletweets.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;



import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.enums.TweetType;
import com.codepath.apps.mysimpletweets.fragments.TweetListFragment;
import com.codepath.apps.mysimpletweets.fragments.UserDetailFragment;
import com.codepath.apps.mysimpletweets.models.User;



public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        User user = getIntent().getExtras().getParcelable("user");
        String screenName = user.screenName;


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("@"+screenName);
        if (savedInstanceState == null) {
            UserDetailFragment userDetailFragment = UserDetailFragment.newInstance(user);
            TweetListFragment tweetListFragment = TweetListFragment.newInstance(TweetType.USER.ordinal(), user);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.flUserProfileDetailPlaceholder, userDetailFragment);
            fragmentTransaction.replace(R.id.flUserTweetsPlaceholder, tweetListFragment);

            fragmentTransaction.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
