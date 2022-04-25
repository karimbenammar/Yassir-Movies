package com.yassir.movies.ui.details

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Callback
import com.yassir.movies.R
import com.yassir.movies.adapters.MoviesAdapter
import com.yassir.movies.data.models.Movie
import com.yassir.movies.databinding.ActivityDetailsBinding
import com.yassir.movies.util.MovieHelper
import com.yassir.movies.util.PicassoHelper
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var mMovie: Movie
    private lateinit var relatedMoviesList: MutableList<Movie>

    private val compositeDisposable = CompositeDisposable()
    private val viewModel: DetailsViewModel by viewModels()
    private val relatedMoviesAdapter = MoviesAdapter { movie -> movieItemClicked(movie) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        relatedMoviesList = mutableListOf()
        binding.recyclerRelatedMovies.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = relatedMoviesAdapter
        }

        initData()
    }

    private fun initData() {
        val movieId = intent.getParcelableExtra<Movie>(MOVIE_DETAILS)?.id

        val disposable = viewModel.fetchMovieDetails(movieId!!).subscribe(
            { result ->
                mMovie = result
                initViews()
            },
            { error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        )
        compositeDisposable.add(disposable)
    }

    private fun initViews() {
        val img = ImageView(this)
        PicassoHelper.loadImage(
            url = mMovie.poster_path,
            imageView = binding.moviePoster,
            placeholderId = R.drawable.item_movie_placeholder
        )
        PicassoHelper.loadImage(
            url = mMovie.backdrop_path,
            imageView = img,
            placeholderId = R.drawable.item_cover_placeholder,
            callback = object :
                Callback {
                override fun onSuccess() {
                    binding.movieCover.background = img.drawable
                }

                override fun onError(e: Exception?) {
                    println(e?.localizedMessage)
                }
            })

        binding.movieTitle.text = mMovie.title
        binding.movieYear.text = LocalDate.parse(mMovie.release_date).year.toString()
        binding.movieReleaseDate.text =
            LocalDate.parse(mMovie.release_date)
                .format(DateTimeFormatter.ofPattern(RELEASE_DATE_FORMAT))
        binding.movieRuntime.text =
            getString(R.string.runtime_placeholder, mMovie.runtime / 60, mMovie.runtime % 60)
        binding.movieOverview.text = mMovie.overview
        binding.movieStatus.text = mMovie.status
        binding.movieGenre.text = MovieHelper.appendGenresText(mMovie.genres)
        binding.movieCountry.text = MovieHelper.appendCountriesText(mMovie.production_countries)
        binding.movieOverview.post {
            binding.expand.visibility =
                if (binding.movieOverview.lineCount > COLLAPSED_OVERVIEW_MAX_LINES) VISIBLE else GONE
        }
        binding.expand.setOnClickListener {
            toggleTextViewExpansion(binding.movieOverview, it as Button)
        }

        updateRelatedMoviesList()
    }

    private fun updateRelatedMoviesList() {
        val relatedMoviesDisposable =
            viewModel.fetchRelatedMovies(mMovie.genres).subscribe({ result ->
                val list = result.results.filter { movie -> movie.id != mMovie.id }
                relatedMoviesList.addAll(list)
                relatedMoviesAdapter.submitList(relatedMoviesList)
            },
                { error ->
                    Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
                })
        compositeDisposable.add(relatedMoviesDisposable)
    }

    /**
     * Toggles a TextView (Expand/Collapse) and updates the toggle's text
     *
     * @param textView TextView to be expanded/collapsed
     * @param toggle Button used as toggle
     */
    private fun toggleTextViewExpansion(textView: TextView, toggle: Button) {
        val animation = ObjectAnimator.ofInt(
            textView, MAX_LINES_PROPERTY_NAME,
            if (textView.maxLines == COLLAPSED_OVERVIEW_MAX_LINES) textView.lineCount else COLLAPSED_OVERVIEW_MAX_LINES
        )
        animation.setDuration(50).start()
        toggle.text =
            getString(if (textView.maxLines == COLLAPSED_OVERVIEW_MAX_LINES) R.string.less else R.string.more)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun movieItemClicked(movie: Movie) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(MOVIE_DETAILS, movie)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.dispose()
    }

    companion object {
        const val MOVIE_DETAILS = "movie_details"
        const val MAX_LINES_PROPERTY_NAME = "maxLines"
        const val RELEASE_DATE_FORMAT = "dd MMMM yyyy"
        const val COLLAPSED_OVERVIEW_MAX_LINES = 3
    }
}