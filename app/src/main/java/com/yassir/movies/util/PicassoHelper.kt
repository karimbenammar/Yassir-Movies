package com.yassir.movies.util

import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

object PicassoHelper {

    /**
     * Appends genres text and returns a full string containing genres separated by commas
     *
     * @param genres list of genres
     */
    fun loadImage(url: String?, imageView: ImageView, placeholderId: Int, callback: Callback? = null) {
        val picassoRequest = if (url.isNullOrEmpty())
            Picasso.get().load(placeholderId)
        else
            Picasso.get().load(ImageHelper.generateImageUrl(url))
        picassoRequest.into(imageView, callback)
    }
}