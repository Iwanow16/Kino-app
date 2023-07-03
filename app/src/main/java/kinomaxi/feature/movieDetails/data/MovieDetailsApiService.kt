package kinomaxi.feature.movieDetails.data

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Интерфейс взаимодействия с REST API
 */
interface MovieDetailsApiService {

    /**
     * Получить подробную информацию о фильме с идентификатором [movieId]
     */
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Long): RestMovieDetails

    /**
     * Получить список изображений для фильма с идентификатором [movieId]
     */
    @GET("movie/{movie_id}/images?include_image_language=en,null")
    suspend fun getMovieImages(@Path("movie_id") movieId: Long): RestMovieImagesResponse
}
