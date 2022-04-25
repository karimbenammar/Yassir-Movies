package com.yassir.movies.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateHelper {

    /**
     * Parses the release date string and returns the year only
     *
     * @param releaseDate release date to be parsed
     */
    fun getReleaseDateYear(releaseDate: String?): String{
        return if (releaseDate.isNullOrEmpty())
            "TBD"
        else LocalDate.parse(releaseDate).year.toString()
    }

    /**
     * Parses the release date string and returns the formatted date
     *
     * @param releaseDate release date to be parsed
     */
    fun getReleaseDateFormatted(releaseDate: String?): String{
        return if (releaseDate.isNullOrEmpty())
            "TBD"
        else LocalDate.parse(releaseDate)
            .format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
    }
}