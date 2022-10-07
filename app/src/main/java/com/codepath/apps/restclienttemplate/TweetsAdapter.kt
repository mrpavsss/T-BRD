package com.codepath.apps.restclienttemplate

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.codepath.apps.restclienttemplate.models.Tweet

const val TWEET_EXTRA = "TWEET_EXTRA"
private const val TAG = "TweetsAdapter"
class TweetsAdapter(private val context: Context,
                    private val tweets: ArrayList<Tweet>)
    : RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.item_tweet, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TweetsAdapter.ViewHolder, position: Int) {
        val tweet: Tweet = tweets.get(position)

        holder.tvUserName.text = tweet.user?.name
        holder.tvScreenName.text = "@${tweet.user?.screenName}"
        holder.tvTweetBody.text = tweet.body
        holder.tvTimestamp.text = TimeFormatter.getTimeDifference(tweet.createdAt)
        if (tweet.retweetCount == 0) {
            holder.tvRetweetCount.text = ""
        } else {
            holder.tvRetweetCount.text = tweet.retweetCount.toString()
        }
        if (tweet.likeCount == 0) {
            holder.tvLikeCount.text = ""
        } else {
            holder.tvLikeCount.text = tweet.likeCount.toString()
        }
        if (tweet.retweeted) {
            holder.ivRetweetBtn.setImageResource(R.drawable.ic_vector_retweet)
        }
        if (tweet.liked) {
            holder.ivLikeBtn.setImageResource(R.drawable.ic_vector_heart)
        }

        Glide.with(holder.itemView)
            .load(tweet.user?.publicImageUrl)
            .transform(CircleCrop())
            .into(holder.ivProfileImage)
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    fun clear() {
        tweets.clear()
        notifyDataSetChanged()
    }

    fun addAll(tweetList: List<Tweet>) {
        tweets.addAll(tweetList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        val ivProfileImage = itemView.findViewById<ImageView>(R.id.ivProfileImage)
        val tvUserName = itemView.findViewById<TextView>(R.id.tvUsername)
        val tvScreenName = itemView.findViewById<TextView>(R.id.tvScreenname)
        val tvTweetBody = itemView.findViewById<TextView>(R.id.tvTweetBody)
        val tvTimestamp = itemView.findViewById<TextView>(R.id.tvTimestamp)
        val tvRetweetCount = itemView.findViewById<TextView>(R.id.tvRetweetCount)
        val tvLikeCount = itemView.findViewById<TextView>(R.id.tvLikeCount)
        val ivRetweetBtn = itemView.findViewById<ImageView>(R.id.ivRetweetBtn)
        val ivLikeBtn = itemView.findViewById<ImageView>(R.id.ivLikeBtn)

        override fun onClick(p0: View?) {
            val tweet = tweets.get(absoluteAdapterPosition)
            Log.i(TAG, "onClick: $absoluteAdapterPosition")
//            Toast.makeText(context, tweet.user?.name, Toast.LENGTH_SHORT).show()
            try {
                val i = Intent(context, DetailActivity::class.java)
                i.putExtra(TWEET_EXTRA, tweet)

                context.startActivity(i)
            } catch (noActivity: ActivityNotFoundException) {
                noActivity.printStackTrace()
            }
        }
    }

}