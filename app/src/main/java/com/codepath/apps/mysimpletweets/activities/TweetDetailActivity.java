package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.utils.ParseDateHelper;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TweetDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private Tweet tweet;

    @Bind(R.id.tvUserName) TextView mtvUserName;
    @Bind(R.id.tvScreenName) TextView mtvScreenName;
    @Bind(R.id.tvBody) TextView mtvBody;
    @Bind(R.id.tvTimestamp) TextView mtvTimestamp;
    @Bind(R.id.vLine2) View mvLine2;
    @Bind(R.id.tvRetweetCount) TextView mtvRetweetCount;
    @Bind(R.id.tvFavoriteCount) TextView mtvFavoriteCount;
    @Bind(R.id.ivUserImage) ImageView mivUserImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tweet = getIntent().getParcelableExtra("tweet");
        mtvUserName.setText(tweet.user.name);
        mtvScreenName.setText(tweet.user.screenName);
        mtvBody.setText(tweet.text);
        mtvTimestamp.setText(ParseDateHelper.getPrettyTimeStamp(tweet.timeCreated));

        // default to invisible, only show if there are retweets and/or favorites
        mvLine2.setVisibility(View.INVISIBLE);

        if (tweet.retweetCount > 0) {
            String retweetCountHtml = "<b>" + String.valueOf(tweet.retweetCount) +
                    "</b> <font color='#74363636'>RETWEETS</font>";
            mtvRetweetCount.setText(Html.fromHtml(retweetCountHtml));
            mvLine2.setVisibility(View.VISIBLE);
        }

        if (tweet.favoriteCount > 0) {
            String favoriteCountHtml = "<b>" + String.valueOf(tweet.favoriteCount) +
                    "</b> <font color='#74363636'>FAVORITES</font>";
            mtvFavoriteCount.setText(Html.fromHtml(favoriteCountHtml));
            mvLine2.setVisibility(View.VISIBLE);
        }

        mivUserImage.setOnClickListener(this);
        Picasso.with(this)
                .load(tweet.user.profileImageUrl)
                .into(mivUserImage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweet_detail, menu);
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


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivUserImage) {
            Intent i = new Intent(this, UserProfileActivity.class);
            i.putExtra("user", tweet.user);
            startActivity(i);
        }
    }

}
