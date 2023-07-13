package kinomaxi.feature.mainPage.view

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.AndroidEntryPoint
import kinomaxi.R
import kinomaxi.Screens.DetailsScreen
import kinomaxi.databinding.FragmentMainPageBinding
import kinomaxi.feature.movieList.compose.ErrorViewCompose
import kinomaxi.feature.movieList.compose.LoaderViewCompose
import kinomaxi.feature.movieList.compose.MovieMainListsCompose
import kinomaxi.feature.movieList.model.Movie
import kinomaxi.feature.movieList.view.MovieListItem
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

        /*
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
        */

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
        with(viewBinding.composeView) {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                when (state) {
                    MainPageState.Loading -> {
                        LoaderViewCompose()
                    }

                    MainPageState.Error -> {
                        ErrorViewCompose()
                    }

                    is MainPageState.Success -> {
                        setTitle(getString(R.string.app_name))
                        setSubtitle(null)
                        MovieMainListsCompose(state.data)
                    }
                }
            }
        }
    }
}

private fun Movie.toViewData(): MovieListItem =
    MovieListItem.Movie(id, posterUrl)