package kinomaxi.feature.movieList.data

import kinomaxi.AppConfig
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Интерфейс взаимодействия с REST API для функционала списков фильмов
 */
interface MoviesListApiService {

    /**
     * Получить список текущих популярных фильмов
     */
    @Headers("Authorization: Bearer ${AppConfig.BEARER_TOKEN}")
    @GET("movie/popular")
    suspend fun getPopularMovies(): RestMoviesListResponse

    /**
     * Получить список фильмов с самым высоким рейтингом
     */
    @Headers("Authorization: Bearer ${AppConfig.BEARER_TOKEN}")
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): RestMoviesListResponse

    /**
     * Получить список ещё не вышедших фильмов
     */
    @Headers("Authorization: Bearer ${AppConfig.BEARER_TOKEN}")
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): RestMoviesListResponse
}
