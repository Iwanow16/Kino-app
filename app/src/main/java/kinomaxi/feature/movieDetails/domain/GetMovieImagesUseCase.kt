package kinomaxi.feature.movieDetails.domain

import kinomaxi.AppConfig
import kinomaxi.feature.movieDetails.data.MovieDetailsApiService
import kinomaxi.feature.movieDetails.data.RestMovieImagesResponse
import kinomaxi.feature.movieDetails.model.MovieImage

/**
 * Бизнес-сценарий получения списка изображений фильма
 */
class GetMovieImagesUseCase(
    private val apiService: MovieDetailsApiService,
) {

    /**
     * Получить список изображений фильма по идентификатору [movieId]
     */
    suspend operator fun invoke(
        movieId: Long,
    ): List<MovieImage> {
        return apiService.getMovieImages(movieId).toImages()
    }
}

private fun RestMovieImagesResponse.toImages() = backdrops.map { backdrop ->
    MovieImage(
        imageUrl = "${AppConfig.IMAGE_BASE_URL}original${backdrop.path}",
        previewUrl = "${AppConfig.IMAGE_BASE_URL}${AppConfig.BACKDROP_PREVIEW_SIZE}${backdrop.path}",
    )
}