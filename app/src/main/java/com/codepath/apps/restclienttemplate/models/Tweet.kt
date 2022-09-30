package com.codepath.apps.restclienttemplate.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.codepath.apps.restclienttemplate.TimeFormatter
import com.codepath.apps.restclienttemplate.max_id
import org.json.JSONArray
import org.json.JSONObject



@Parcelize
data class Tweet(
    var id: Long = -1,
    var body: String = "",
    var createdAt: String = "",
    var user: User? = null,
    var entities: Entity? = null
) : Parcelable {

    companion object {
        fun fromJson(jsonObject: JSONObject) : Tweet {
            val tweet = Tweet()
            tweet.id = jsonObject.getLong("id")
            tweet.body = jsonObject.getString("text")
            tweet.createdAt = jsonObject.getString("created_at")
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"))
            tweet.entities = Entity.fromJson(jsonObject.getJSONObject("entities"))

            if (max_id == (-1).toLong() || tweet.id < max_id) {
                max_id = tweet.id
            }
            return tweet
        }

        fun fromJsonArray(jsonArray: JSONArray) : List<Tweet> {
            val tweets = ArrayList<Tweet>()
            for (i in 0 until jsonArray.length()) {
                tweets.add(fromJson(jsonArray.getJSONObject(i)))
            }
            return tweets
        }
    }
}