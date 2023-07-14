package kinomaxi.feature.movieDetails.domain

import kinomaxi.feature.backgroundWork.data.ConfDataStore
import kinomaxi.feature.movieDetails.data.MovieDetailsApiService
import kinomaxi.feature.movieDetails.data.RestMovieDetails
import kinomaxi.feature.movieDetails.data.RestMovieGenre
import kinomaxi.feature.movieDetails.model.MovieDetails
import kinomaxi.feature.movieDetails.model.MovieImage
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

/**
 * Бизнес-сценарий получения детальной информации о фильме
 */
class GetMovieDetailsUseCase @Inject constructor(
    private val apiService: MovieDetailsApiService,
    private val dataStore: ConfDataStore
) {
    /**
     * Получить детальную информацию о фильме по идентификатору [movieId]
     */
    suspend operator fun invoke(
        movieId: Long,
    ): MovieDetails {
        val baseUrl: String? = dataStore.baseUrlConfigurationFlow.first()
        val posterPreviewSize: String? = dataStore.posterSizeConfigurationFlow.first()
        return apiService.getMovieDetails(movieId).toEntity(baseUrl, posterPreviewSize)
    }
}

private fun RestMovieDetails.toEntity(
    baseUrl: String?,
    posterPreviewSize: String?
) =
        MovieDetails(
            id = id,
            imdbId = imdbId,
            title = title,
            originalTitle = originalTitle,
            posterImage = MovieImage(
                imageUrl = "${baseUrl}original$posterPath",
                previewUrl = "${baseUrl}${posterPreviewSize}$posterPath",
            ),
            overview = overview,
            tagline = tagline,
            genres = genres.map(RestMovieGenre::name),
            releaseDate = LocalDate.parse(releaseDate),
            lengthMinutes = lengthMinutes,
            rating = rating,
    )
