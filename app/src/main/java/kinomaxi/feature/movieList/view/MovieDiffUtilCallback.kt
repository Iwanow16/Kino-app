package kinomaxi.feature.movieList.view
import androidx.recyclerview.widget.DiffUtil

class MovieDiffUtilCallback(oldList: List<MovieViewData>, newList: List<MovieViewData>) :
    DiffUtil.Callback() {
    private val oldList: List<MovieViewData>
    private val newList: List<MovieViewData>

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
        val oldMovies: MovieViewData = oldList[oldItemPosition]
        val newMovies: MovieViewData = newList[newItemPosition]
        return oldMovies.id == newMovies.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovies: MovieViewData = oldList[oldItemPosition]
        val newMovies: MovieViewData = newList[newItemPosition]
        return (oldMovies.posterUrl.equals(newMovies.posterUrl))
    }
}