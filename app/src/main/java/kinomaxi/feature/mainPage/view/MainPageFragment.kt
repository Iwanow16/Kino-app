package kinomaxi.feature.mainPage.view

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.map
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.AndroidEntryPoint
import kinomaxi.R
import kinomaxi.Screens.DetailsScreen
import kinomaxi.Screens.FavoriteScreen
import kinomaxi.databinding.FragmentMainPageBinding
import kinomaxi.databinding.LayoutErrorViewBinding
import kinomaxi.databinding.LayoutMoviesListBinding
import kinomaxi.feature.movieList.model.Movie
import kinomaxi.feature.movieList.model.MoviesList
import kinomaxi.feature.movieList.model.MoviesListType
import kinomaxi.feature.movieList.view.MovieListItem
import kinomaxi.feature.movieList.view.MoviesListAdapter
import kinomaxi.setSubtitle
import kinomaxi.setTitle
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainPageFragment : Fragment(R.layout.fragment_main_page) {

    @Inject
    lateinit var router: Router

    private val viewBinding: FragmentMainPageBinding by viewBinding(FragmentMainPageBinding::bind)

    private val viewModel: MainPageViewModel by viewModels()

    private val menuProvider by lazy { MainPageMenuProvider(router, childFragmentManager) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            favoritesButton.setOnClickListener {
                router.navigateTo(FavoriteScreen())
            }
            errorView.setOnInflateListener { _, inflated ->
                with(LayoutErrorViewBinding.bind(inflated)) {
                    errorActionButton.setOnClickListener {
                        viewModel.refreshData()
                    }
                }
            }

            topRatedMoviesList.moviesListSlider.adapter = MoviesListAdapter(::onMovieClick)
            topPopularMoviesList.moviesListSlider.adapter = MoviesListAdapter(::onMovieClick)
            topUpcomingMoviesList.moviesListSlider.adapter = MoviesListAdapter(::onMovieClick)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.viewState.collect(::showNewState) }
                launch { viewModel.isUserAuthenticated.collect(menuProvider::updateMenu) }
            }
        }

        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun onMovieClick(movieId: Long) {
        router.navigateTo(DetailsScreen(movieId))
    }

    private fun showNewState(state: MainPageState) {
        when (state) {
            MainPageState.Loading -> with(viewBinding) {
                contentView.isVisible = false
                loaderView.show()
                errorView.isVisible = false
            }

            MainPageState.Error -> with(viewBinding) {
                contentView.isVisible = false
                loaderView.hide()
                errorView.isVisible = true
            }

            is MainPageState.Success -> with(viewBinding) {
                contentView.isVisible = true
                loaderView.hide()
                errorView.isVisible = false
                showData(state.data)
            }
        }
    }

    private fun FragmentMainPageBinding.showData(data: MainPageData) {
        setTitle(getString(R.string.app_name))
        setSubtitle(null)

        topRatedMoviesList.showMoviesList(data.topRatedMoviesList)
        topPopularMoviesList.showMoviesList(data.topPopularMoviesList)
        topUpcomingMoviesList.showMoviesList(data.topUpcomingMoviesList)
    }

    private fun LayoutMoviesListBinding.showMoviesList(moviesList: MoviesList) {
        moviesListTitle.setText(moviesList.type.titleResId)
        (moviesListSlider.adapter as? MoviesListAdapter)?.submitData(lifecycle,
            moviesList.movies.map { movie: Movie -> movie.toViewData() }
        )
    }
}

private val MoviesListType.titleResId: Int
    @StringRes
    get() = when (this) {
        MoviesListType.TOP_RATED_MOVIES -> R.string.top_rated_title
        MoviesListType.POPULAR_MOVIES -> R.string.top_popular_title
        MoviesListType.UPCOMING_MOVIES -> R.string.top_upcoming_title
    }

private fun Movie.toViewData(): MovieListItem =
    MovieListItem.Movie(id, posterUrl)