package com.yassir.movies.util

import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

object PicassoHelper {

    /**
     * Loads an image from url into a view
     *
     * @param url url of the image to be loaded
     * @param imageView view that will contain the image
     * @param placeholderId resource id of the placeholder to display
     * @param callback callback to be executed after loading the image
     */
    fun loadImage(url: String?, imageView: ImageView, placeholderId: Int, callback: Callback? = null) {
        val picassoRequest = if (url.isNullOrEmpty())
            Picasso.get().load(placeholderId)
        else
            Picasso.get().load(ImageHelper.generateImageUrl(url))
        picassoRequest.into(imageView, callback)
    }
}