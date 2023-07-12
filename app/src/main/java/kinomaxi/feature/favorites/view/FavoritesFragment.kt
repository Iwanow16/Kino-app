package kinomaxi.feature.favorites.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.AndroidEntryPoint
import kinomaxi.R
import kinomaxi.Screens.DetailsScreen
import kinomaxi.databinding.FragmentFavoritesBinding
import kinomaxi.feature.movieList.model.Banner
import kinomaxi.feature.movieList.model.FavoriteMovie
import kinomaxi.feature.movieList.view.MovieListItem
import kinomaxi.feature.movieList.view.MoviesListAdapter
import kinomaxi.setSubtitle
import kinomaxi.setTitle
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    @Inject
    lateinit var router: Router

    private val viewBinding: FragmentFavoritesBinding by viewBinding(FragmentFavoritesBinding::bind)

    private val viewModel: FavoritesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(getString(R.string.favorite_movies_list_title))
        setSubtitle(null)

        with(viewBinding) {
            moviesListView.adapter = MoviesListAdapter(::onMovieClick, isFavoritesList = true)
            moviesListView.layoutManager = GridLayoutManager(requireContext(), 2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when ((moviesListView.adapter as MoviesListAdapter).getItemViewType(
                            position
                        )) {
                            R.layout.item_banner -> 2
                            else -> 1
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteMovies.collect {
                    showItems(it)
                }
            }
        }
    }

    private fun onMovieClick(movieId: Long) {
        router.navigateTo(DetailsScreen(movieId))
    }

    private fun showItems(favoriteMovies: PagingData<FavoriteMovie>) {
        with(viewBinding) {
            val items: PagingData<MovieListItem> = favoriteMovies.map(FavoriteMovie::toViewData)
            val pagingDataEmpty: Boolean = PagingData.empty<MovieListItem>() == items
            moviesListView.isVisible = !pagingDataEmpty
            emptyDataView.isVisible = pagingDataEmpty
            (moviesListView.adapter as? MoviesListAdapter)?.submitData(lifecycle, items)
        }
    }
}

private fun FavoriteMovie.toViewData() =
    MovieListItem.Movie(id, posterUrl)

private fun Banner.toViewData() =
    MovieListItem.Banner(text)