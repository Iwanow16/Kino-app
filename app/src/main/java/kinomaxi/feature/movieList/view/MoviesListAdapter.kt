package kinomaxi.feature.movieList.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kinomaxi.R
import kinomaxi.databinding.ItemBannerBinding
import kinomaxi.databinding.ItemMovieBinding

/**
 * Адаптер для списка фильмов
 */

class MoviesListAdapter (
    private val onMovieClick: (movieId: Long) -> Unit,
    private val isFavoritesList: Boolean = false,
    ) : ListAdapter<MovieListItem, RecyclerView.ViewHolder>
    (AsyncDifferConfig.Builder(DiffCallback()).build()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            R.layout.item_banner -> BannerViewHolder(ItemBannerBinding.inflate(layoutInflater))
            else -> MovieViewHolder(ItemMovieBinding.inflate(layoutInflater), onMovieClick)
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is BannerViewHolder -> holder.setData(currentList[position] as MovieListItem.Banner)
            is MovieViewHolder -> holder.setData(currentList[position] as MovieListItem.Movie)
        }
    }

    override fun getItemViewType(position: Int) = when (currentList[position]) {
        is MovieListItem.Movie -> R.layout.item_movie
        is MovieListItem.Banner -> R.layout.item_banner
        else -> throw IllegalStateException("Unknown view")
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)

        if (!isFavoritesList) return
        if (holder !is MovieViewHolder) return

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

private class DiffCallback : DiffUtil.ItemCallback<MovieListItem>() {
    override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
        val isSameMovie = oldItem is MovieListItem.Movie
                && newItem is MovieListItem.Movie
                && oldItem.id == newItem.id

        val isSameBanner = oldItem is MovieListItem.Banner
                && newItem is MovieListItem.Banner
                && oldItem.text == newItem.text

        return isSameMovie || isSameBanner
    }

    override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem) =
        oldItem.equals(newItem)
}
