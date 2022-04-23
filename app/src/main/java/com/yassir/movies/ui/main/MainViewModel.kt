package com.yassir.movies.ui.main

import androidx.lifecycle.ViewModel
import com.yassir.movies.data.models.MovieCollection
import com.yassir.movies.net.Api
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
}