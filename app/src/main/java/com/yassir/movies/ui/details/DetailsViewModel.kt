package com.yassir.movies.ui.details

import androidx.lifecycle.ViewModel
import com.yassir.movies.data.models.Movie
import com.yassir.movies.net.Api
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val api: Api,
): ViewModel() {
    fun fetchMovieDetails(movieId: Int): Observable<Movie> {
        println("TICHA " + movieId)
        return api.fetchMovieDetails(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}