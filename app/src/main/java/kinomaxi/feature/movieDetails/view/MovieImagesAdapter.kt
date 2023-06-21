package kinomaxi.feature.movieDetails.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kinomaxi.databinding.ItemMovieImageBinding
import kinomaxi.feature.movieDetails.model.MovieImage

/**
 * Адаптер для списка изображений фильма
 */
class MovieImagesAdapter : RecyclerView.Adapter<MovieImageViewHolder>() {

    private val items = mutableListOf<MovieImage>()

    fun setItems(items: List<MovieImage>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieImageBinding.inflate(layoutInflater)
        return MovieImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieImageViewHolder, position: Int) {
        holder.setData(items[position])
    }
}
