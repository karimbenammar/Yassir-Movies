package com.yassir.movies.ui.details

import com.yassir.movies.TrampolineSchedulerRule
import com.yassir.movies.net.Api
import com.yassir.movies.ui.genresList
import com.yassir.movies.ui.movieDetails
import com.yassir.movies.ui.moviesRelatedCollection
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class DetailsViewModelTest {

    @get:Rule
    val rule = TrampolineSchedulerRule()

    private val api: Api = mock {
        on { fetchMoviesRelated(any(),any(),any(),any(),any()) } doReturn Observable.just(
            moviesRelatedCollection()
        )
        on { fetchMovieDetails(MOVIE_ID) } doReturn Observable.just(movieDetails(MOVIE_ID,
            "t$MOVIE_ID"
        ))
    }

    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        viewModel = getViewModel(api)
    }

    @Test
    fun `related movies fetched correctly`() {
        viewModel.fetchRelatedMovies(genresList()).test().assertValue(moviesRelatedCollection())
    }

    @Test
    fun `movie details fetched correctly`() {
        viewModel.fetchMovieDetails(MOVIE_ID).test().assertValue(movieDetails(MOVIE_ID, "t$MOVIE_ID"))
    }

    private fun getViewModel(api: Api) = DetailsViewModel(api)

    companion object {
        private const val MOVIE_ID = 1
    }
}