package kinomaxi.feature.movieList.data

import retrofit2.http.GET

/**
 * Интерфейс взаимодействия с REST API для функционала списков фильмов
 */
interface MoviesListApiService {

    /**
     * Получить список текущих популярных фильмов
     */
    @GET("movie/popular")
    suspend fun getPopularMovies(): RestMoviesListResponse

    /**
     * Получить список фильмов с самым высоким рейтингом
     */
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): RestMoviesListResponse

    /**
     * Получить список ещё не вышедших фильмов
     */
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): RestMoviesListResponse
}
