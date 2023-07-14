package kinomaxi.feature.mainPage.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kinomaxi.feature.auth.IsAuthenticatedUseCase
import kinomaxi.feature.movieList.data.MovieRepository
import kinomaxi.feature.movieList.model.MoviesList
import kinomaxi.feature.movieList.model.MoviesListType
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    isAuthenticationUseCase: IsAuthenticatedUseCase,
    private val movieRepository: MovieRepository
) : ViewModel() {

    val isUserAuthenticated: Flow<Boolean> = isAuthenticationUseCase()

    private var job: Job? = null

    private var _viewState = MutableStateFlow<MainPageState>(MainPageState.Loading)
    val viewState: Flow<MainPageState> =
        _viewState.asStateFlow()
            .onStart { loadData() }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                _viewState.value
            )

    fun refreshData() {
        _viewState.value = MainPageState.Loading
        job?.cancel()
        loadData()
    }

    private fun loadData() {
        val getTopRatedMovies =
            viewModelScope.async { movieRepository.getMovieListFlow(MoviesListType.TOP_RATED_MOVIES) }
        val getPopularMovies =
            viewModelScope.async { movieRepository.getMovieListFlow(MoviesListType.POPULAR_MOVIES) }
        val getUpcomingMovies =
            viewModelScope.async { movieRepository.getMovieListFlow(MoviesListType.UPCOMING_MOVIES) }

        job = viewModelScope.launch {
            try {
                val (topRatedMovies, popularMovies, upcomingMovies) = listOf(
                    getTopRatedMovies,
                    getPopularMovies,
                    getUpcomingMovies
                ).awaitAll()

                val viewData = MainPageData(
                    topRatedMoviesList = MoviesList(
                        MoviesListType.TOP_RATED_MOVIES,
                        topRatedMovies
                    ),
                    topPopularMoviesList = MoviesList(
                        MoviesListType.POPULAR_MOVIES,
                        popularMovies
                    ),
                    topUpcomingMoviesList = MoviesList(
                        MoviesListType.UPCOMING_MOVIES,
                        upcomingMovies
                    ),
                )
                _viewState.value = MainPageState.Success(viewData)
            } catch (e: Exception) {
                _viewState.value = MainPageState.Error
            }
        }
    }
}