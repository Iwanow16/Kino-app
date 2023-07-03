package kinomaxi.feature.movieDetails.view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kinomaxi.feature.favorites.data.FavoriteMoviesRepository
import kinomaxi.feature.movieDetails.domain.GetMovieDetailsUseCase
import kinomaxi.feature.movieDetails.domain.GetMovieImagesUseCase
import kinomaxi.feature.movieDetails.domain.IsMovieFavoriteUseCase
import kinomaxi.feature.movieDetails.model.MovieDetails
import kinomaxi.feature.movieDetails.model.MovieDetailsViewData
import kinomaxi.feature.movieDetails.view.MovieDetailsFragment.Companion.MOVIE_ID_ARG_KEY
import kinomaxi.feature.movieList.model.Movie
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsById: GetMovieDetailsUseCase,
    private val getMovieImagesById: GetMovieImagesUseCase,
    isMovieFavoriteFlow: IsMovieFavoriteUseCase,
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
) : ViewModel() {

    private val movieId: Long = requireNotNull(savedStateHandle[MOVIE_ID_ARG_KEY])

    private val isFavoriteState: StateFlow<Boolean> = isMovieFavoriteFlow(movieId)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            false
        )

    private val _viewState = MutableStateFlow<MovieDetailsViewState>(MovieDetailsViewState.Loading)
    val viewState: Flow<MovieDetailsViewState> = combine(
        _viewState.asStateFlow(),
        isFavoriteState
    ) { viewState: MovieDetailsViewState, isFavorite: Boolean ->
        if (viewState is MovieDetailsViewState.Success) {
            viewState.copy(viewState.data.copy(isFavorite = isFavorite))
        } else {
            viewState
        }
    }.onStart { loadData() }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        _viewState.value
    )

    fun refreshData() {
        _viewState.value = MovieDetailsViewState.Loading
        loadData()
    }

    private fun loadData() {
        val getMovieDetails = viewModelScope.async { getMovieDetailsById(movieId) }
        val getMovieImages = viewModelScope.async { getMovieImagesById(movieId) }
        viewModelScope.launch {
            try {
                val movieDetails = getMovieDetails.await()
                val movieImages = getMovieImages.await()
                val data = MovieDetailsViewData(movieDetails, movieImages, isFavorite = false)
                _viewState.value = MovieDetailsViewState.Success(data)
            } catch (e: Exception) {
                _viewState.value = MovieDetailsViewState.Error
            }
        }
    }

    fun toggleFavorites() {
        val viewState = _viewState.value as? MovieDetailsViewState.Success ?: return
        val viewData = viewState.data
        viewModelScope.launch {
            if (isFavoriteState.value) {
                favoriteMoviesRepository.removeFromFavorites(viewData.movieDetails.toMovie())
            } else {
                favoriteMoviesRepository.addToFavorites(viewData.movieDetails.toMovie())
            }
        }
    }
}

private fun MovieDetails.toMovie() = Movie(
    id = id,
    title = title,
    posterUrl = posterImage?.previewUrl,
)
