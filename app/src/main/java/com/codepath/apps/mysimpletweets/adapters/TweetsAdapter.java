package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.apps.mysimpletweets.utils.ParseDateHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by carlybaja on 2/17/16.
 */
public class TweetsAdapter extends ArrayAdapter<Tweet> implements View.OnClickListener{

    private TweetsAdapterListener listener;

    public interface TweetsAdapterListener {
        public void onReplyTweet(Tweet tweet);
        public void onRetweet(Tweet tweet);
        public void onFavoriteTweet(Tweet tweet);
        public void onViewUserProfile(User user);
    }

    static class ViewHolder {

        @Bind(R.id.ivUserImage) ImageView mivUserImage;
        @Bind(R.id.tvUserName) TextView mtvUserName;
        @Bind(R.id.tvUserHandle) TextView mtvUserHandle;
        @Bind(R.id.tvDescription) TextView mtvDescription;
        @Bind(R.id.tvTimestamp) TextView mtvTimestamp;
        @Bind(R.id.ibReply) ImageButton mibReply;
        @Bind(R.id.tvRetweetCount) TextView mtvRetweetCount;
        @Bind(R.id.tvFavoriteCount) TextView mtvFavoriteCount;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    public TweetsAdapter(Context context, List<Tweet> tweets, TweetsAdapterListener listener) {
        super(context, android.R.layout.simple_list_item_1, tweets);
        this.listener = listener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Tweet tweet = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }


        viewHolder.mtvUserName.setText(tweet.user.name);
        viewHolder.mtvUserHandle.setText(tweet.user.screenName);
        viewHolder.mtvDescription.setText(tweet.text);
        viewHolder.mtvTimestamp.setText(ParseDateHelper.getRelativeTimeAgo(tweet.timeCreated));
        viewHolder.mibReply.setTag(position);
        viewHolder.mibReply.setOnClickListener(this);
        viewHolder.mtvRetweetCount.setText(String.valueOf(tweet.retweetCount));
        viewHolder.mtvRetweetCount.setTag(position);
        viewHolder.mtvRetweetCount.setOnClickListener(this);
        viewHolder.mtvFavoriteCount.setText(String.valueOf(tweet.favoriteCount));
        viewHolder.mtvFavoriteCount.setTag(position);
        viewHolder.mtvFavoriteCount.setOnClickListener(this);
        Picasso.with(getContext())
                .load(tweet.user.profileImageUrl)
                .into(viewHolder.mivUserImage);
        viewHolder.mivUserImage.setTag(position);
        viewHolder.mivUserImage.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        if (listener == null) {
            return;
        }
        int position = (int)v.getTag();
        Tweet tweet = getItem(position);
        if (v.getId() == R.id.ibReply) {
            listener.onReplyTweet(tweet);
        } else if (v.getId() == R.id.tvRetweetCount) {
            listener.onRetweet(tweet);
        } else if (v.getId() == R.id.tvFavoriteCount) {
            listener.onFavoriteTweet(tweet);
        } else if (v.getId() == R.id.ivUserImage) {
            listener.onViewUserProfile(tweet.user);
        }
    }

    public void setFavoritedStatus(boolean favorited, int position) {

    }
}
