package com.yassir.movies.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil

data class Movie(
    val id: Int,
    val original_title: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String,
    val overview: String,
    val title: String,
    val runtime: Int,
    val status: String,
    val genres: List<Genre>,
    val production_countries: List<Country>,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        arrayListOf<Genre>().apply {
            parcel.readList(this, Genre::class.java.classLoader)
        },
        arrayListOf<Country>().apply {
            parcel.readList(this, Country::class.java.classLoader)
        }
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
                   oldItem.runtime == newItem.runtime &&
                   oldItem.status == newItem.status &&
                   oldItem.genres == newItem.genres &&
                   oldItem.production_countries == newItem.production_countries &&
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
        parcel.writeInt(runtime)
        parcel.writeString(status)
        parcel.writeList(genres)
        parcel.writeList(production_countries)
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