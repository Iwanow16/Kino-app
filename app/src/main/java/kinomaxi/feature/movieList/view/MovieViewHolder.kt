package kinomaxi.feature.movieList.view

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kinomaxi.databinding.ItemMovieBinding

/**
 * [RecyclerView.ViewHolder] для отображения карточки фильма в списке фильмов
 */
class MovieViewHolder(
    viewBinding: ItemMovieBinding,
    private val onMovieClick: (movieId: Long) -> Unit,
) : RecyclerView.ViewHolder(viewBinding.root) {

    private val rootView = viewBinding.root
    private val imageView = viewBinding.moviePoster

    /**
     * Установить данные [data] для отображения
     */
    fun setData(data: MovieViewData) {
        Glide.with(imageView).load(data.posterUrl).into(imageView)
        rootView.setOnClickListener { onMovieClick(data.id) }
    }
}