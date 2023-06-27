package kinomaxi.feature.movieDetails.view

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kinomaxi.feature.favorites.data.FavoriteDatabase
import kinomaxi.feature.favorites.data.FavoriteMoviesRepository
import kinomaxi.feature.movieDetails.data.MovieDetailsApiService
import kinomaxi.feature.movieDetails.domain.GetMovieDetailsUseCase
import kinomaxi.feature.movieDetails.domain.GetMovieImagesUseCase
import kinomaxi.feature.movieDetails.model.MovieDetails
import kinomaxi.feature.movieDetails.model.MovieImage
import kinomaxi.feature.movieList.model.Movie
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieId: Long,
    private val getMovieDetailsById: GetMovieDetailsUseCase,
    private val getMovieImagesById: GetMovieImagesUseCase,
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
) : ViewModel() {

    private var movieDetails: MovieDetails? = null
    private var movieImages: List<MovieImage>? = null

    private var _viewState = MutableStateFlow<MovieDetailsViewState>(MovieDetailsViewState.Loading)
    val viewState: Flow<MovieDetailsViewState> = _viewState.asStateFlow()
        .onSubscription { loadData() }
        .onStart {  }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            MovieDetailsViewState.Loading
        )

    fun refreshData() {
        _viewState.value = MovieDetailsViewState.Loading
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                movieDetails = async { getMovieDetailsById(movieId) }.await()
                movieImages = async { getMovieImagesById(movieId) }.await()
                _viewState.value = MovieDetailsViewState.Success(movieDetails!!, movieImages!!)

            } catch (e: Exception) {
                _viewState.value = MovieDetailsViewState.Error
            }
        }
    }

    fun toggleFavorites() {
        val movieImages = movieImages ?: return
        val movieDetails = movieDetails?.let {
            it.copy(isFavorite = !it.isFavorite)
        } ?: return

            if (movieDetails.isFavorite) {
                viewModelScope.launch {
                    favoriteMoviesRepository.addToFavorites(movieDetails.toMovie())
                }
            } else {
                viewModelScope.launch {
                    favoriteMoviesRepository.removeFromFavorites(movieDetails.toMovie())
                }
            }

        this.movieDetails = movieDetails
        _viewState.value = MovieDetailsViewState.Success(movieDetails, movieImages)
    }

    companion object {

        fun createFactory(movieId: Long): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    val application = checkNotNull(this[APPLICATION_KEY])
                    val movieDao = FavoriteDatabase.getDatabase(application).movieDao()
                    val favoriteMoviesRepository = FavoriteMoviesRepository(movieDao)
                    val getMovieDetailsUseCase = GetMovieDetailsUseCase(
                        MovieDetailsApiService.instance,
                        favoriteMoviesRepository,
                    )
                    val getMovieImagesUseCase = GetMovieImagesUseCase(
                        MovieDetailsApiService.instance
                    )

                    MovieDetailsViewModel(
                        movieId,
                        getMovieDetailsById = getMovieDetailsUseCase,
                        getMovieImagesById = getMovieImagesUseCase,
                        favoriteMoviesRepository = favoriteMoviesRepository,
                    )
                }
            }
        }
    }
}

private fun MovieDetails.toMovie() = Movie(
    id = id,
    title = title,
    posterUrl = posterImage?.previewUrl,
)
