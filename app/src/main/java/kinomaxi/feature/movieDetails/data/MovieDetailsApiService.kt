package kinomaxi.feature.movieDetails.data

import kinomaxi.createApiService
import retrofit2.Call
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
    fun getMovieDetails(@Path("movie_id") movieId: Long): Call<RestMovieDetails>

    /**
     * Получить список изображений для фильма с идентификатором [movieId]
     */
    @GET("movie/{movie_id}/images?include_image_language=en,null")
    fun getMovieImages(@Path("movie_id") movieId: Long): Call<RestMovieImagesResponse>

    companion object {
        val instance by lazy { createApiService<MovieDetailsApiService>() }
    }
}
