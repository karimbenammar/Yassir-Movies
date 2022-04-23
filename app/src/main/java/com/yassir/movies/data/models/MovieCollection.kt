package com.yassir.movies.data.models

object MovieCollection {
    data class Result(
        val page: Int,
        val results: List<Movie>
    )
}