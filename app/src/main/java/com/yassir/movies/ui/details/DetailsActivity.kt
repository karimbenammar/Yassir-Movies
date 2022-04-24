package com.yassir.movies.ui.details

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.yassir.movies.R
import com.yassir.movies.data.models.Genre
import com.yassir.movies.data.models.Movie
import com.yassir.movies.databinding.ActivityDetailsBinding
import com.yassir.movies.util.ImageHelper
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import java.time.LocalDate

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var mMovie: Movie

    private val compositeDisposable = CompositeDisposable()
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.backButton.setOnClickListener { onBackPressed() }
        initData()
    }

    private fun initData() {
        val movieId = intent.getParcelableExtra<Movie>(MOVIE_DETAILS)?.id

        val disposable = viewModel.fetchMovieDetails(movieId!!).subscribe(
            { result ->
                mMovie = result
                initView()
            },
            { error ->
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        )
        compositeDisposable.add(disposable)
    }

    private fun initView() {
        val img = ImageView(this)

        var picassoRequest = if (mMovie.poster_path.isNullOrEmpty())
            Picasso.get().load(R.drawable.item_movie_placeholder)
        else
            Picasso.get().load(ImageHelper.generateImageUrl(mMovie.poster_path!!))
        picassoRequest.into(binding.moviePoster)

        picassoRequest = if (mMovie.backdrop_path.isNullOrEmpty())
            Picasso.get().load(R.drawable.item_cover_placeholder)
        else
            Picasso.get().load(ImageHelper.generateImageUrl(mMovie.backdrop_path!!))
        picassoRequest.into(img, object: Callback {
            override fun onSuccess() {
                binding.movieCover.background = img.drawable
            }
            override fun onError(e: Exception?) {
                println(e?.localizedMessage)
            }
        })

        binding.movieTitle.text = mMovie.title
        binding.movieYear.text = LocalDate.parse(mMovie.release_date).year.toString()
        binding.movieOverview.text = mMovie.overview
        binding.movieStatus.text = mMovie.status
        binding.movieGenre.text = appendGenres(mMovie.genres)
        binding.movieOverview.post {
            binding.expand.visibility = if (binding.movieOverview.lineCount > COLLAPSED_OVERVIEW_MAX_LINES) VISIBLE else INVISIBLE
        }
        binding.expand.setOnClickListener {
            toggleTextViewExpansion(binding.movieOverview, it as Button)
        }

    }

    private fun appendGenres(genres: List<Genre>): String {
        var result = ""
        for ((i, genre) in genres.withIndex()) {
            result += if (i == genres.size - 1) genre.name else genre.name + ", "
        }
        return result
    }

    /**
     * Toggles a TextView (Expand/Collapse) and updates the toggle's text
     *
     * @param textView TextView to be expanded/collapsed
     * @param toggle Button used as toggle
     */
    private fun toggleTextViewExpansion(textView: TextView, toggle: Button) {
        val animation = ObjectAnimator.ofInt(
            textView, "maxLines",
            if (textView.maxLines == COLLAPSED_OVERVIEW_MAX_LINES) textView.lineCount else COLLAPSED_OVERVIEW_MAX_LINES
        )
        animation.setDuration(50).start()
        toggle.text = getString(if (textView.maxLines == COLLAPSED_OVERVIEW_MAX_LINES) R.string.less else R.string.more)
    }

    companion object{
        const val MOVIE_DETAILS = "movie_details"
        const val COLLAPSED_OVERVIEW_MAX_LINES = 3
    }
}