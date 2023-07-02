package kinomaxi.feature.movieDetails.domain

import kinomaxi.AppConfig
import kinomaxi.feature.favorites.data.FavoriteMoviesRepository
import kinomaxi.feature.movieDetails.data.MovieDetailsApiService
import kinomaxi.feature.movieDetails.data.RestMovieDetails
import kinomaxi.feature.movieDetails.data.RestMovieGenre
import kinomaxi.feature.movieDetails.model.MovieDetails
import kinomaxi.feature.movieDetails.model.MovieImage
import java.time.LocalDate
import javax.inject.Inject

/**
 * Бизнес-сценарий получения детальной информации о фильме
 */
class GetMovieDetailsUseCase @Inject constructor(
    private val apiService: MovieDetailsApiService,
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
) {
    /**
     * Получить детальную информацию о фильме по идентификатору [movieId]
     */
    suspend operator fun invoke(
        movieId: Long,
    ): MovieDetails {
        val isFavorite: Boolean = favoriteMoviesRepository.isFavorite(movieId)
        return apiService.getMovieDetails(movieId).toEntity(isFavorite)
    }
}

private fun RestMovieDetails.toEntity(isFavorite: Boolean) = MovieDetails(
    id = id,
    imdbId = imdbId,
    title = title,
    originalTitle = originalTitle,
    posterImage = MovieImage(
        imageUrl = "${AppConfig.IMAGE_BASE_URL}original$posterPath",
        previewUrl = "${AppConfig.IMAGE_BASE_URL}${AppConfig.POSTER_PREVIEW_SIZE}$posterPath",
    ),
    overview = overview,
    tagline = tagline,
    genres = genres.map(RestMovieGenre::name),
    releaseDate = LocalDate.parse(releaseDate),
    lengthMinutes = lengthMinutes,
    rating = rating,
    isFavorite = isFavorite,
)
