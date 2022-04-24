package com.yassir.movies.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yassir.movies.R
import com.yassir.movies.data.models.Movie
import com.yassir.movies.databinding.ItemMovieBinding
import com.yassir.movies.util.ImageHelper
import java.time.LocalDate

class MoviesAdapter(
    private val onItemClicked: (Movie) -> Unit
) : ListAdapter<Movie, MoviesAdapter.MoviesViewHolder>(Movie.DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemBinding, onItemClicked)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    class MoviesViewHolder(
        private val itemBinding: ItemMovieBinding,
        private val onItemClicked: (Movie) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movie: Movie) {
            itemBinding.run {
                // Set up movie item
                movieTitle.text = movie.title
                // TODO: Add an alternative for older SDK versions
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    movieYear.text = LocalDate.parse(movie.release_date).year.toString()
                }
                Picasso.get()
                    .load(ImageHelper.generateImageUrl(movie.poster_path))
                    .placeholder(R.drawable.item_movie_placeholder)
                    .into(moviePoster)
                root.setOnClickListener {
                    onItemClicked.invoke(movie)
                }
            }
        }
    }
}