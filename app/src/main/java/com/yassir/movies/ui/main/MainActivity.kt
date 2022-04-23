package com.yassir.movies.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yassir.movies.adapters.MoviesAdapter
import com.yassir.movies.data.models.Movie
import com.yassir.movies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()
    private val moviesAdapter = MoviesAdapter()

    private lateinit var moviesList: MutableList<Movie>
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        moviesList = mutableListOf()

        binding.recyclerMovies.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = moviesAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        updateMoviesList()
    }

    private fun updateMoviesList() {
        val disposable = viewModel.fetchMoviesDiscover().subscribe(
            { result ->
                moviesList.addAll(result.results)
                moviesAdapter.submitList(moviesList)
            },
            { error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        )
        compositeDisposable.add(disposable)
    }
}