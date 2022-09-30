package com.codepath.apps.restclienttemplate.models

import org.json.JSONObject
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONException

@Parcelize
class Entity(
    var mediaImageUrl: String = "",
) : Parcelable {

    companion object {
        fun fromJson(jsonObject: JSONObject) : Entity {
            val entity = Entity()
            try {
                entity.mediaImageUrl = jsonObject.getJSONArray("media")
                    .getJSONObject(0).getString("media_url_https")
            } catch (e: JSONException)
            {
            }

            return entity
        }
    }
}