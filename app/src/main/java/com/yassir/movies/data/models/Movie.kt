package com.yassir.movies.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil

data class Movie(
    val id: Int,
    val original_title: String,
    val poster_path: String,
    val backdrop_path: String,
    val release_date: String,
    val overview: String,
    val title: String,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
    )

    class DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id &&
                   oldItem.original_title == newItem.original_title &&
                   oldItem.poster_path == newItem.poster_path &&
                   oldItem.backdrop_path == newItem.backdrop_path &&
                   oldItem.release_date == newItem.release_date &&
                   oldItem.title == newItem.title &&
                   oldItem.overview == newItem.overview
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(original_title)
        parcel.writeString(poster_path)
        parcel.writeString(backdrop_path)
        parcel.writeString(release_date)
        parcel.writeString(overview)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}