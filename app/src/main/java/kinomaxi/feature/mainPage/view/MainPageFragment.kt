package kinomaxi.feature.mainPage.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kinomaxi.R
import kinomaxi.databinding.FragmentMainPageBinding
import kinomaxi.databinding.LayoutErrorViewBinding
import kinomaxi.databinding.LayoutMoviesListBinding
import kinomaxi.feature.favorites.view.FavoritesFragment
import kinomaxi.feature.movieDetails.view.MovieDetailsFragment
import kinomaxi.feature.movieList.model.Movie
import kinomaxi.feature.movieList.model.MoviesList
import kinomaxi.feature.movieList.model.MoviesListType
import kinomaxi.feature.movieList.view.MovieViewData
import kinomaxi.feature.movieList.view.MoviesListAdapter
import kinomaxi.navigateTo
import kinomaxi.setSubtitle
import kinomaxi.setTitle

class MainPageFragment : Fragment() {

    private var _viewBinding: FragmentMainPageBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val viewModel by viewModels<MainPageViewModel>(
        factoryProducer = MainPageViewModel.Companion::Factory
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewBinding = FragmentMainPageBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            favoritesButton.setOnClickListener {
                navigateTo(FavoritesFragment())
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

        viewModel.setViewStateChangeListener(::showNewState)
    }

    private fun onMovieClick(movieId: Long) {
        navigateTo(MovieDetailsFragment.getInstance(movieId))
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
        (moviesListSlider.adapter as? MoviesListAdapter)?.submitList(
            moviesList.movies.map(Movie::toViewData)
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

private fun Movie.toViewData() = MovieViewData(
    id = id,
    posterUrl = posterUrl,
)