package kinomaxi.feature.movieList.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import kinomaxi.databinding.ItemMovieBinding

/**
 * Адаптер для списка фильмов
 */
class MoviesListAdapter(
    private val onMovieClick: (movieId: Long) -> Unit,
    private val isFavoritesList: Boolean = false,
) : RecyclerView.Adapter<MovieViewHolder>() {

    private val items = mutableListOf<MovieViewData>()

    fun setItems(items: List<MovieViewData>) {
        this.items.clear()
        this.items.addAll(items)

        val difCallback = MovieDiffUtilCallback(items, items)
        val diffMovie = DiffUtil.calculateDiff(difCallback)
        diffMovie.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemMovieBinding.inflate(layoutInflater)
        return MovieViewHolder(viewBinding, onMovieClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setData(items[position])
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
