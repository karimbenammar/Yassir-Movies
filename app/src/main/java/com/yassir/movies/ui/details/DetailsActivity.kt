package com.yassir.movies.ui.details

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.yassir.movies.R
import com.yassir.movies.data.models.Movie
import com.yassir.movies.databinding.ActivityDetailsBinding
import com.yassir.movies.util.ImageHelper
import java.time.LocalDate


class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private var mMovie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initData()
        initView()
    }

    private fun initData() {
        mMovie = intent.getParcelableExtra(MOVIE_DETAILS)
    }

    private fun initView() {
        val img = ImageView(this)
        Picasso.get()
            .load(ImageHelper.generateImageUrl(mMovie?.backdrop_path.toString(), true))
            .placeholder(R.drawable.item_cover_placeholder)
            .into(img, object: Callback {
                override fun onSuccess() {
                    binding.movieCover.background = img.drawable
                }
                override fun onError(e: Exception?) {
                    println(e?.localizedMessage)
                }
            })
        Picasso.get()
            .load(ImageHelper.generateImageUrl(mMovie?.poster_path.toString()))
            .placeholder(R.drawable.item_movie_placeholder)
            .into(binding.moviePoster)
        binding.movieTitle.text = mMovie?.title
        // TODO: Add an alternative for older SDK versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.movieYear.text = LocalDate.parse(mMovie?.release_date).year.toString()
        }
        binding.movieOverview.text = mMovie?.overview
        binding.movieOverview.post {
            binding.expand.visibility = if (binding.movieOverview.lineCount > COLLAPSED_OVERVIEW_MAX_LINES) VISIBLE else INVISIBLE
        }
        binding.expand.setOnClickListener {
            toggleTextViewExpansion(binding.movieOverview, it as Button)
        }
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