package kinomaxi.feature.movieDetails.view

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kinomaxi.databinding.ItemMovieImageBinding
import kinomaxi.feature.movieDetails.model.MovieImage

/**
 * Класс для отображения элемента списка изображений фильма
 */
class MovieImageViewHolder(
    binding: ItemMovieImageBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val imageView = binding.root

    /**
     * Установить данные [data] для отображения
     */
    fun setData(data: MovieImage) {
        Glide.with(imageView).load(data.previewUrl).into(imageView)
    }
}
