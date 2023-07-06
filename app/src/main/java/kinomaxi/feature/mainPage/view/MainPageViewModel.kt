package kinomaxi.feature.mainPage.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kinomaxi.feature.movieList.domain.GetMoviesListUseCase
import kinomaxi.feature.movieList.domain.IsAuthenticatedUseCase
import kinomaxi.feature.movieList.model.MoviesListType
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
    private val getMoviesList: GetMoviesListUseCase,
    isAuthenticationUseCase: IsAuthenticatedUseCase
) : ViewModel() {

    val isUserAuthenticated: Flow<Boolean> = isAuthenticationUseCase()

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
        loadData()
    }

    private fun loadData() {
        val getTopRatedMovies =
            viewModelScope.async { getMoviesList(MoviesListType.TOP_RATED_MOVIES) }
        val getPopularMovies = viewModelScope.async { getMoviesList(MoviesListType.POPULAR_MOVIES) }
        val getUpcomingMovies =
            viewModelScope.async { getMoviesList(MoviesListType.UPCOMING_MOVIES) }
        viewModelScope.launch {
            try {
                val (topRatedMovies, popularMovies, upcomingMovies) = listOf(
                    getTopRatedMovies,
                    getPopularMovies,
                    getUpcomingMovies
                ).awaitAll()

                val viewData = MainPageData(
                    topRatedMoviesList = topRatedMovies,
                    topPopularMoviesList = popularMovies,
                    topUpcomingMoviesList = upcomingMovies,
                )
                _viewState.value = MainPageState.Success(viewData)
            } catch (e: Exception) {
                _viewState.value = MainPageState.Error
            }
        }
    }
}