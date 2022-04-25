package com.yassir.movies.ui.details

import androidx.lifecycle.ViewModel
import com.yassir.movies.data.models.Genre
import com.yassir.movies.data.models.Movie
import com.yassir.movies.data.models.MovieCollection
import com.yassir.movies.net.Api
import com.yassir.movies.util.MovieHelper
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
        return api.fetchMovieDetails(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun fetchRelatedMovies(genres: List<Genre>): Observable<MovieCollection.Result> {
        return api.fetchMoviesRelated(with_genres = MovieHelper.appendGenresIds(genres))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}