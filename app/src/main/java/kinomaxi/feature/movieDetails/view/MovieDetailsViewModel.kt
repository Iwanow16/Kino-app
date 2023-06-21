package kinomaxi.feature.movieDetails.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kinomaxi.feature.favorites.data.FavoriteMoviesRepository
import kinomaxi.feature.movieDetails.data.MovieDetailsApiService
import kinomaxi.feature.movieDetails.domain.GetMovieDetailsUseCase
import kinomaxi.feature.movieDetails.domain.GetMovieImagesUseCase
import kinomaxi.feature.movieDetails.model.MovieDetails
import kinomaxi.feature.movieDetails.model.MovieImage
import kinomaxi.feature.movieList.model.Movie

class MovieDetailsViewModel(
    private val movieId: Long,
    private val getMovieDetailsById: GetMovieDetailsUseCase,
    private val getMovieImagesById: GetMovieImagesUseCase,
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
) : ViewModel() {

    private var movieDetails: MovieDetails? = null
    private var movieImages: List<MovieImage>? = null

    private var viewState: MovieDetailsViewState = MovieDetailsViewState.Loading
        set(value) {
            field = value
            viewStateChangeListener(value)
        }

    private var viewStateChangeListener: (MovieDetailsViewState) -> Unit = {}

    fun setViewStateChangeListener(listener: (MovieDetailsViewState) -> Unit) {
        viewStateChangeListener = listener
        viewStateChangeListener(viewState)
    }

    init {
        loadData()
    }

    fun refreshData() {
        viewState = MovieDetailsViewState.Loading
        loadData()
    }

    private fun loadData() {
        loadMovieDetails()
        loadMovieImages()
    }

    private fun loadMovieDetails() {
        getMovieDetailsById(
            movieId,
            onSuccess = { details ->
                movieDetails = details.takeUnless { viewState == MovieDetailsViewState.Error }

                val images = movieImages
                if (images != null) {
                    viewState = MovieDetailsViewState.Success(details, images)
                }
            },
            onFailure = {
                movieDetails = null
                viewState = MovieDetailsViewState.Error
            }
        )
    }

    private fun loadMovieImages() {
        getMovieImagesById(
            movieId,
            onSuccess = { images ->
                movieImages = images.takeUnless { viewState == MovieDetailsViewState.Error }

                val details = movieDetails
                if (details != null) {
                    viewState = MovieDetailsViewState.Success(details, images)
                }
            },
            onFailure = {
                movieImages = null
                viewState = MovieDetailsViewState.Error
            }
        )
    }

    fun toggleFavorites() {
        val movieImages = movieImages ?: return
        val movieDetails = movieDetails?.let {
            it.copy(isFavorite = !it.isFavorite)
        } ?: return

        if (movieDetails.isFavorite) {
            favoriteMoviesRepository.addToFavorites(movieDetails.toMovie())
        } else {
            favoriteMoviesRepository.removeFromFavorites(movieId)
        }

        this.movieDetails = movieDetails
        viewState = MovieDetailsViewState.Success(movieDetails, movieImages)
    }

    companion object {

        fun createFactory(movieId: Long): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    val favoriteMoviesRepository = FavoriteMoviesRepository.instance
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
