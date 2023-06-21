package kinomaxi.feature.movieList.data

import kinomaxi.createApiService
import retrofit2.Call
import retrofit2.http.GET

/**
 * Интерфейс взаимодействия с REST API для функционала списков фильмов
 */
interface MoviesListApiService {

    /**
     * Получить список текущих популярных фильмов
     */
    @GET("movie/popular")
    fun getPopularMovies(): Call<RestMoviesListResponse>

    /**
     * Получить список фильмов с самым высоким рейтингом
     */
    @GET("movie/top_rated")
    fun getTopRatedMovies(): Call<RestMoviesListResponse>

    /**
     * Получить список ещё не вышедших фильмов
     */
    @GET("movie/upcoming")
    fun getUpcomingMovies(): Call<RestMoviesListResponse>

    companion object {
        val instance by lazy { createApiService<MoviesListApiService>() }
    }
}
