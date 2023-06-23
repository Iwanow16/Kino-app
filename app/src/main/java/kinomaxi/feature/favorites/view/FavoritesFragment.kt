package kinomaxi.feature.favorites.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kinomaxi.R
import kinomaxi.databinding.FragmentFavoritesBinding
import kinomaxi.feature.movieDetails.view.MovieDetailsFragment
import kinomaxi.feature.movieList.model.Banner
import kinomaxi.feature.movieList.model.Movie
import kinomaxi.feature.movieList.view.MovieListItem
import kinomaxi.feature.movieList.view.MoviesListAdapter
import kinomaxi.navigateTo
import kinomaxi.setSubtitle
import kinomaxi.setTitle

class FavoritesFragment : Fragment() {

    private var _viewBinding: FragmentFavoritesBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val viewModel by viewModels<FavoritesViewModel>(
        factoryProducer = FavoritesViewModel.Companion::Factory
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewBinding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(getString(R.string.favorite_movies_list_title))
        setSubtitle(null)

        with(viewBinding) {
            moviesListView.adapter = MoviesListAdapter(::onMovieClick, isFavoritesList = true)
            moviesListView.layoutManager = GridLayoutManager(requireContext(), 2).apply {
//                spanSizeLookup =
            }
        }

        showItems(viewModel.favoriteMovies, viewModel.banners)
    }

    private fun onMovieClick(movieId: Long) {
        navigateTo(MovieDetailsFragment.getInstance(movieId))
    }

    private fun showItems(favoriteMovies: List<Movie>, banners: List<Banner>) {
        with(viewBinding) {
            val items: List<MovieListItem> =
                favoriteMovies.map(Movie::toViewData) + banners.map(Banner::toViewData)
            moviesListView.isVisible = favoriteMovies.isNotEmpty()
            emptyDataView.isVisible = favoriteMovies.isEmpty()
            (moviesListView.adapter as? MoviesListAdapter)?.submitList(items)
        }
    }
}

private fun Movie.toViewData() =
    MovieListItem.Movie(id, posterUrl)

private fun Banner.toViewData() =
    MovieListItem.Banner(text)








