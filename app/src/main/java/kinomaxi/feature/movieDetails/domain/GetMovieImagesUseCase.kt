package kinomaxi.feature.movieDetails.domain

import kinomaxi.feature.backgroundWork.data.ConfDataStore
import kinomaxi.feature.movieDetails.data.MovieDetailsApiService
import kinomaxi.feature.movieDetails.data.RestMovieImagesResponse
import kinomaxi.feature.movieDetails.model.MovieImage
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Бизнес-сценарий получения списка изображений фильма
 */
class GetMovieImagesUseCase @Inject constructor(
    private val apiService: MovieDetailsApiService,
    private val dataStore: ConfDataStore
) {

    /**
     * Получить список изображений фильма по идентификатору [movieId]
     */
    suspend operator fun invoke(
        movieId: Long,
    ): List<MovieImage> {
        val baseUrl: String? = dataStore.baseUrlConfigurationFlow.first()
        val backdropPreviewSize: String? = dataStore.backdropSizeConfigurationFlow.first()
        return apiService.getMovieImages(movieId).toImages(baseUrl, backdropPreviewSize)
    }
}

private fun RestMovieImagesResponse.toImages(
    baseUrl: String?,
    backdropPreviewSize: String?
) = backdrops.map { backdrop ->
    MovieImage(
        imageUrl = "${baseUrl}original${backdrop.path}",
        previewUrl = "${baseUrl}${backdropPreviewSize}${backdrop.path}",
    )
}