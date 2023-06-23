package kinomaxi.feature.movieList.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kinomaxi.databinding.ItemMovieBinding

/**
 * Адаптер для списка фильмов
 */

class MoviesListAdapter (
    private val onMovieClick: (movieId: Long) -> Unit,
    private val isFavoritesList: Boolean = false,
    ) : ListAdapter<MovieViewData, MovieViewHolder>
    (AsyncDifferConfig.Builder(DiffCallback()).build()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemMovieBinding.inflate(layoutInflater)
        return MovieViewHolder(viewBinding, onMovieClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setData(currentList[position])
    }

    override fun onViewAttachedToWindow(holder: MovieViewHolder) {
        super.onViewAttachedToWindow(holder)

        if (!isFavoritesList) return

        with(ItemMovieBinding.bind(holder.itemView)) {
            root.updateLayoutParams {
                width = ViewGroup.LayoutParams.MATCH_PARENT
            }
            moviePoster.updateLayoutParams {
                width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<MovieViewData>() {

    override fun areItemsTheSame(oldItem: MovieViewData, newItem: MovieViewData) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieViewData, newItem: MovieViewData) =
        oldItem == newItem
}