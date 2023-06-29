package kinomaxi.feature.movieDetails.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kinomaxi.feature.favorites.data.FavoriteMoviesRepository
import kinomaxi.feature.movieDetails.domain.GetMovieDetailsUseCase
import kinomaxi.feature.movieDetails.domain.GetMovieImagesUseCase
import kinomaxi.feature.movieDetails.domain.IsMovieFavoriteFlow
import kinomaxi.feature.movieDetails.model.MovieDetails
import kinomaxi.feature.movieDetails.model.MovieImage
import kinomaxi.feature.movieList.model.Movie
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieId: Long,
    private val getMovieDetailsById: GetMovieDetailsUseCase,
    private val getMovieImagesById: GetMovieImagesUseCase,
    private val isMovieFavoriteFlow: IsMovieFavoriteFlow,
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
) : ViewModel() {
    private val _viewState = MutableStateFlow<MovieDetailsViewState>(MovieDetailsViewState.Loading)
    val viewState: Flow<MovieDetailsViewState> = combine(
        _viewState.asStateFlow().onSubscription { loadData() }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            _viewState.value
        ),
        isMovieFavoriteFlow(movieId)
    ) { viewState: MovieDetailsViewState, isFavorite: Boolean ->
        if (viewState is MovieDetailsViewState.Success) {
            viewState.copy(viewState.movieDetails.copy(isFavorite = isFavorite))
        } else {
            viewState
        }
    }

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
            if (!movieDetails.isFavorite) {
                favoriteMoviesRepository.addToFavorites(movieDetails.toMovie())
            } else {
                favoriteMoviesRepository.removeFromFavorites(movieDetails.toMovie())
            }
        }
    }
}

private fun MovieDetails.toMovie() = Movie(
    id = id,
    title = title,
    posterUrl = posterImage?.previewUrl,
)
