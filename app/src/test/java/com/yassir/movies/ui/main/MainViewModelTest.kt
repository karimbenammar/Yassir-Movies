package com.yassir.movies.ui.main

import com.yassir.movies.TrampolineSchedulerRule
import com.yassir.movies.net.Api
import com.yassir.movies.ui.moviesDiscoverCollection
import com.yassir.movies.ui.moviesLatestCollection
import com.yassir.movies.ui.moviesUpcomingCollection
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class MainViewModelTest {

    @get:Rule
    val rule = TrampolineSchedulerRule()

    private val api: Api = mock {
        on { fetchMoviesDiscover() } doReturn Observable.just(moviesDiscoverCollection())
        on { fetchMoviesLatest(any(),any(),any(),any(),any(),any()) } doReturn Observable.just(
            moviesLatestCollection()
        )
        on { fetchMoviesUpcoming(any(),any(),any(),any(),any()) } doReturn Observable.just(
            moviesUpcomingCollection()
        )
    }

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = getViewModel(api)
    }

    @Test
    fun `discover movies fetched correctly`() {
        viewModel.fetchMoviesDiscover().test().assertValue(moviesDiscoverCollection())
    }

    @Test
    fun `latest movies fetched correctly`() {
        viewModel.fetchMoviesLatest().test().assertValue(moviesLatestCollection())
    }

    @Test
    fun `upcoming movies fetched correctly`() {
        viewModel.fetchMoviesUpcoming().test().assertValue(moviesUpcomingCollection())
    }

    private fun getViewModel(api: Api) = MainViewModel(api)
}