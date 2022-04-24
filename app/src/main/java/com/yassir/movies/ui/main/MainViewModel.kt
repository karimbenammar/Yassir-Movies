package com.yassir.movies.ui.main

import androidx.lifecycle.ViewModel
import com.yassir.movies.data.models.MovieCollection
import com.yassir.movies.net.Api
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: Api
): ViewModel() {
    fun fetchMoviesDiscover(): Observable<MovieCollection.Result> {
        return api.fetchMoviesDiscover()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchMoviesLatest(): Observable<MovieCollection.Result> {
        return api.fetchMoviesLatest(
            release_date_lte = LocalDateTime.now().toString()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchMoviesUpcoming(): Observable<MovieCollection.Result> {
        return api.fetchMoviesUpcoming(
            release_date_gte = LocalDateTime.now().toString()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}