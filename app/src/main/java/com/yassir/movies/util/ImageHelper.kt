package com.yassir.movies.util

object ImageHelper {

    /**
     * Generates a full URL for the provided image path
     *
     * @param path Path to the image
     */
    fun generateImageUrl(path: String, isOriginalRes: Boolean = false): String {
        return (if (isOriginalRes) BASE_URL_ORIGINAL else BASE_URL_W500) + path
    }

    private const val BASE_URL_W500 = "https://image.tmdb.org/t/p/w500"
    private const val BASE_URL_ORIGINAL = "https://image.tmdb.org/t/p/original"
}