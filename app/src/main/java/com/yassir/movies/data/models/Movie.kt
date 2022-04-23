package com.yassir.movies.data.models

import androidx.recyclerview.widget.DiffUtil

data class Movie(
    val id: Int,
    val original_title: String,
    val poster_path: String,
    val backdrop_path: String,
    val release_date: String,
) : java.io.Serializable {
    class DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id &&
                   oldItem.original_title == newItem.original_title &&
                   oldItem.poster_path == newItem.poster_path &&
                   oldItem.backdrop_path == newItem.backdrop_path &&
                   oldItem.release_date == newItem.release_date
        }
    }
}