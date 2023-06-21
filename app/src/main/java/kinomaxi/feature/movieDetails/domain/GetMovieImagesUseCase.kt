package kinomaxi.feature.movieDetails.domain

import kinomaxi.AppConfig
import kinomaxi.feature.movieDetails.data.MovieDetailsApiService
import kinomaxi.feature.movieDetails.data.RestMovieImagesResponse
import kinomaxi.feature.movieDetails.model.MovieImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Бизнес-сценарий получения списка изображений фильма
 */
class GetMovieImagesUseCase(
    private val apiService: MovieDetailsApiService,
) {

    /**
     * Получить список изображений фильма по идентификатору [movieId]
     */
    operator fun invoke(
        movieId: Long,
        onSuccess: (images: List<MovieImage>) -> Unit,
        onFailure: () -> Unit
    ) {
        apiService.getMovieImages(movieId).enqueue(object : Callback<RestMovieImagesResponse> {
            override fun onResponse(
                call: Call<RestMovieImagesResponse>,
                response: Response<RestMovieImagesResponse>
            ) {
                if (!response.isSuccessful) {
                    onFailure()
                    return
                }

                val images = response.body()?.toImages()
                if (images == null) {
                    onFailure()
                    return
                }

                onSuccess(images)
            }

            override fun onFailure(call: Call<RestMovieImagesResponse>, t: Throwable) {
                onFailure()
            }
        })
    }
}

private fun RestMovieImagesResponse.toImages() = backdrops.map { backdrop ->
    MovieImage(
        imageUrl = "${AppConfig.IMAGE_BASE_URL}original${backdrop.path}",
        previewUrl = "${AppConfig.IMAGE_BASE_URL}${AppConfig.BACKDROP_PREVIEW_SIZE}${backdrop.path}",
    )
}