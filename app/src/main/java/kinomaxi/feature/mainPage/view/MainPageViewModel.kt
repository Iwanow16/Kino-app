package kinomaxi.feature.mainPage.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kinomaxi.feature.movieList.data.MoviesListApiService
import kinomaxi.feature.movieList.domain.GetMoviesListUseCase
import kinomaxi.feature.movieList.model.MoviesList
import kinomaxi.feature.movieList.model.MoviesListType

class MainPageViewModel(
    private val getMoviesList: GetMoviesListUseCase,
) : ViewModel() {

    private var topRatedMovies: MoviesList? = null
    private var popularMovies: MoviesList? = null
    private var upcomingMovies: MoviesList? = null

    private var viewState: MainPageState = MainPageState.Loading
        set(value) {
            field = value
            viewStateChangeListener(value)
        }

    private var viewStateChangeListener: (MainPageState) -> Unit = {}

    fun setViewStateChangeListener(listener: (MainPageState) -> Unit) {
        viewStateChangeListener = listener
        viewStateChangeListener(viewState)
    }

    init {
        loadData()
    }

    fun refreshData() {
        viewState = MainPageState.Loading
        loadData()
    }

    private fun loadData() {
        loadTopRatedMovies()
        loadPopularMovies()
        loadUpcomingMovies()
    }

    private fun loadTopRatedMovies() {
        getMoviesList(
            MoviesListType.TOP_RATED_MOVIES,
            onSuccess = { list ->
                topRatedMovies = list.takeUnless { viewState == MainPageState.Error }

                val popularMovies = popularMovies
                val upcomingMovies = upcomingMovies
                if (popularMovies != null && upcomingMovies != null) {
                    val viewData = MainPageData(
                        topRatedMoviesList = list,
                        topPopularMoviesList = popularMovies,
                        topUpcomingMoviesList = upcomingMovies,
                    )
                    viewState = MainPageState.Success(viewData)
                }
            },
            onFailure = {
                topRatedMovies = null
                viewState = MainPageState.Error
            }
        )
    }

    private fun loadPopularMovies() {
        getMoviesList(
            MoviesListType.POPULAR_MOVIES,
            onSuccess = { list ->
                popularMovies = list.takeUnless { viewState == MainPageState.Error }

                val topRatedMovies = topRatedMovies
                val upcomingMovies = upcomingMovies
                if (topRatedMovies != null && upcomingMovies != null) {
                    val viewData = MainPageData(
                        topRatedMoviesList = topRatedMovies,
                        topPopularMoviesList = list,
                        topUpcomingMoviesList = upcomingMovies,
                    )
                    viewState = MainPageState.Success(viewData)
                }
            },
            onFailure = {
                topRatedMovies = null
                viewState = MainPageState.Error
            }
        )
    }

    private fun loadUpcomingMovies() {
        getMoviesList(
            MoviesListType.UPCOMING_MOVIES,
            onSuccess = { list ->
                upcomingMovies = list.takeUnless { viewState == MainPageState.Error }

                val topRatedMovies = topRatedMovies
                val popularMovies = popularMovies
                if (topRatedMovies != null && popularMovies != null) {
                    val viewData = MainPageData(
                        topRatedMoviesList = topRatedMovies,
                        topPopularMoviesList = popularMovies,
                        topUpcomingMoviesList = list,
                    )
                    viewState = MainPageState.Success(viewData)
                }
            },
            onFailure = {
                topRatedMovies = null
                viewState = MainPageState.Error
            }
        )
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val getMoviesListUseCase = GetMoviesListUseCase(MoviesListApiService.instance)

                MainPageViewModel(
                    getMoviesList = getMoviesListUseCase,
                )
            }
        }
    }
}