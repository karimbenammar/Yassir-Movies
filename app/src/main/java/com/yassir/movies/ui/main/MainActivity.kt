package com.yassir.movies.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yassir.movies.adapters.MoviesAdapter
import com.yassir.movies.data.models.Movie
import com.yassir.movies.databinding.ActivityMainBinding
import com.yassir.movies.ui.details.DetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()
    private val trendingMoviesAdapter = MoviesAdapter { movie -> movieItemClicked(movie) }
    private val latestMoviesAdapter = MoviesAdapter { movie -> movieItemClicked(movie) }
    private val upcomingMoviesAdapter = MoviesAdapter { movie -> movieItemClicked(movie) }

    private lateinit var trendingMoviesList: MutableList<Movie>
    private lateinit var latestMoviesList: MutableList<Movie>
    private lateinit var upcomingMoviesList: MutableList<Movie>
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        trendingMoviesList = mutableListOf()
        latestMoviesList = mutableListOf()
        upcomingMoviesList = mutableListOf()

        binding.recyclerTrendingMovies.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = trendingMoviesAdapter
        }
        binding.recyclerLatestMovies.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = latestMoviesAdapter
        }
        binding.recyclerUpcomingMovies.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingMoviesAdapter
        }

        updateMoviesList()
    }

    private fun updateMoviesList() {
        val trendingMoviesDisposable = viewModel.fetchMoviesDiscover().subscribe { result ->
            trendingMoviesList.addAll(result.results)
            trendingMoviesAdapter.submitList(trendingMoviesList)
        }
        val latestMoviesDisposable = viewModel.fetchMoviesLatest().subscribe { result ->
            latestMoviesList.addAll(result.results)
            latestMoviesAdapter.submitList(latestMoviesList)
        }
        val upcomingMoviesDisposable = viewModel.fetchMoviesUpcoming().subscribe { result ->
            upcomingMoviesList.addAll(result.results)
            upcomingMoviesAdapter.submitList(upcomingMoviesList)
        }
        compositeDisposable.add(trendingMoviesDisposable)
        compositeDisposable.add(latestMoviesDisposable)
        compositeDisposable.add(upcomingMoviesDisposable)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    private fun movieItemClicked(movie: Movie) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.MOVIE_DETAILS , movie)
        startActivity(intent)
    }
}