package com.yassir.movies.util

import com.yassir.movies.data.models.Country
import com.yassir.movies.data.models.Genre

object MovieHelper {

    /**
     * Appends genres text and returns a full string containing genres separated by commas
     *
     * @param genres list of genres
     */
    fun appendGenresText(genres: List<Genre>): String {
        var result = ""
        for ((i, genre) in genres.withIndex()) {
            result += if (i == genres.size - 1) genre.name else genre.name + ", "
        }
        return result
    }

    /**
     * Appends countries text and returns a full string containing countries separated by commas
     *
     * @param countries list of countries
     */
    fun appendCountriesText(countries: List<Country>): String {
        var result = ""
        for ((i, country) in countries.withIndex()) {
            result += if (i == countries.size - 1) country.name else country.name + ", "
        }
        return result
    }

    /**
     * Appends genres ids and returns a full string containing ids separated by commas
     *
     * @param genres list of genres
     */
    fun appendGenresIds(genres: List<Genre>): String {
        var result = ""
        for ((i, genre) in genres.withIndex()) {
            result += if (i == genres.size - 1) genre.id else genre.id.toString() + ","
        }
        return result
    }
}