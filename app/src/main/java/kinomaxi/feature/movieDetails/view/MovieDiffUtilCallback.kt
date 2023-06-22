package kinomaxi.feature.movieDetails.view
import androidx.recyclerview.widget.DiffUtil
import kinomaxi.feature.movieDetails.model.MovieImage

class MovieDiffUtilCallback(oldList: List<MovieImage>, newList: List<MovieImage>) :
    DiffUtil.Callback() {
    private val oldList: List<MovieImage>
    private val newList: List<MovieImage>

    init {
        this.oldList = oldList
        this.newList = newList
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovies: MovieImage = oldList[oldItemPosition]
        val newMovies: MovieImage = newList[newItemPosition]
        return oldMovies.imageUrl == newMovies.imageUrl
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovies: MovieImage = oldList[oldItemPosition]
        val newMovies: MovieImage = newList[newItemPosition]
        return (oldMovies.previewUrl == newMovies.previewUrl &&
                oldMovies.imageUrl == newMovies.imageUrl)
    }
}