package kinomaxi.feature.movieDetails.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kinomaxi.databinding.ItemMovieImageBinding
import kinomaxi.feature.movieDetails.model.MovieImage

/**
 * Адаптер для списка изображений фильма
 */
class MovieImagesAdapter : ListAdapter<MovieImage, MovieImageViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieImageBinding.inflate(layoutInflater)
        return MovieImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieImageViewHolder, position: Int) {
        holder.setData(currentList[position])
    }
}

private class DiffCallback : DiffUtil.ItemCallback<MovieImage>() {

    override fun areItemsTheSame(oldItem: MovieImage, newItem: MovieImage) =
        oldItem.imageUrl == newItem.imageUrl

    override fun areContentsTheSame(oldItem: MovieImage, newItem: MovieImage) =
        oldItem == newItem
}

