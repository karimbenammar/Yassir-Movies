package com.yassir.movies.ui

import com.yassir.movies.data.models.Genre
import com.yassir.movies.data.models.Movie
import com.yassir.movies.data.models.MovieCollection

fun moviesDiscoverCollection() = MovieCollection.Result(
    1,
    moviesDiscoverList()
)

fun moviesLatestCollection() = MovieCollection.Result(
    1,
    moviesLatestList()
)

fun moviesUpcomingCollection() = MovieCollection.Result(
    1,
    moviesUpcomingList()
)

fun moviesRelatedCollection() = MovieCollection.Result(
    1,
    moviesRelatedList()
)

fun movieDetails(id: Int, title: String, genres: List<Genre> = listOf()) =
    Movie(id, title, "", "", "", "", "", 0, "", genres, listOf())

fun genre(id: Int, name: String) = Genre(id, name)

fun moviesDiscoverList() = listOf(
    movieDetails(1, "d1"),
    movieDetails(2, "d2"),
    movieDetails(3, "d3"),
)

fun moviesLatestList() = listOf(
    movieDetails(1, "l1"),
    movieDetails(2, "l2"),
    movieDetails(3, "l3"),
)

fun moviesUpcomingList() = listOf(
    movieDetails(1, "u1"),
    movieDetails(2, "u2"),
    movieDetails(3, "u3"),
)

fun moviesRelatedList() = listOf(
    movieDetails(1, "r1", genresList()),
    movieDetails(2, "r2", genresList()),
    movieDetails(3, "r3", genresList()),
)

fun genresList() = listOf(
    genre(1, "g1"),
    genre(2, "g2"),
    genre(3, "g3"),
)