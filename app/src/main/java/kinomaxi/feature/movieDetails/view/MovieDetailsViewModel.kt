package kinomaxi.feature.movieDetails.view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kinomaxi.feature.favorites.data.FavoriteMoviesRepository
import kinomaxi.feature.movieDetails.domain.GetMovieDetailsUseCase
import kinomaxi.feature.movieDetails.domain.GetMovieImagesUseCase
import kinomaxi.feature.movieDetails.domain.IsMovieFavoriteFlow
import kinomaxi.feature.movieDetails.model.MovieDetails
import kinomaxi.feature.movieDetails.model.MovieImage
import kinomaxi.feature.movieDetails.view.MovieDetailsFragment.Companion.MOVIE_ID_ARG_KEY
import kinomaxi.feature.movieList.model.Movie
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
    isMovieFavoriteFlow: IsMovieFavoriteFlow,
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
) : ViewModel() {
    private val movieId: Long = requireNotNull(savedStateHandle[MOVIE_ID_ARG_KEY])

    private val _viewState = MutableStateFlow<MovieDetailsViewState>(MovieDetailsViewState.Loading)
    private val isFavoriteState: StateFlow<Boolean> = isMovieFavoriteFlow(movieId)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            false
        )

    val viewState: Flow<MovieDetailsViewState> = combine(
        _viewState.asStateFlow(),
        isFavoriteState
    ) { viewState: MovieDetailsViewState, isFavorite: Boolean ->
        if (viewState is MovieDetailsViewState.Success) {
            viewState.copy(viewState.movieDetails.copy(isFavorite = isFavorite))
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
                val (movieDetails, movieImages) = listOf(
                    getMovieDetails,
                    getMovieImages,
                ).awaitAll()
                _viewState.value =
                    MovieDetailsViewState.Success(
                        movieDetails as MovieDetails,
                        movieImages as List<MovieImage>,
                    )
            } catch (e: Exception) {
                _viewState.value = MovieDetailsViewState.Error
            }
        }
    }

    fun toggleFavorites() {
        val viewState = _viewState.value as? MovieDetailsViewState.Success ?: return
        val movieDetails = viewState.movieDetails
        viewModelScope.launch {
            if (isFavoriteState.value) {
                favoriteMoviesRepository.removeFromFavorites(movieDetails.toMovie())
            } else {
                favoriteMoviesRepository.addToFavorites(movieDetails.toMovie())
            }
        }
    }
}

private fun MovieDetails.toMovie() = Movie(
    id = id,
    title = title,
    posterUrl = posterImage?.previewUrl,
)
