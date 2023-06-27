package kinomaxi.feature.mainPage.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kinomaxi.feature.movieList.data.MoviesListApiService
import kinomaxi.feature.movieList.domain.GetMoviesListUseCase
import kinomaxi.feature.movieList.model.MoviesListType
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainPageViewModel(
    private val getMoviesList: GetMoviesListUseCase,
) : ViewModel() {

    private var _viewState = MutableStateFlow<MainPageState>(MainPageState.Loading)
    val viewState: Flow<MainPageState> = _viewState.asStateFlow()
        .onSubscription { loadData() }
        .onStart {  }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            MainPageState.Loading
        )

    fun refreshData() {
        _viewState.value = MainPageState.Loading
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {

                val (topRatedMovies, popularMovies, upcomingMovies) = awaitAll(
                    async { getMoviesList(MoviesListType.TOP_RATED_MOVIES) },
                    async { getMoviesList(MoviesListType.POPULAR_MOVIES) },
                    async { getMoviesList(MoviesListType.UPCOMING_MOVIES) },
                )

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