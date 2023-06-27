package kinomaxi.feature.favorites.data

import kinomaxi.feature.movieList.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для работы с избранными фильмами
 */
class FavoriteMoviesRepository(private val movieDao: MovieDao) {

    /**
     * Cписок избранных фильмов
     */
    val favoriteMovies: Flow<List<Movie>> = movieDao.getFavoriteMovies()

    /**
     * Получить признак наличия фильма с идентификатором [movieId] в списке избранных фильмов
     */
    suspend fun isFavorite(movieId: Long) = movieDao.isFavorite(movieId)

    /**
     * Добавить фильм [movie] в список избранных фильмов
     */
    suspend fun addToFavorites(movie: Movie) {
        movieDao.addToFavorites(movie)
    }

    /**
     * Удалить фильм с идентификатором [movieId] из списка избранных фильмов
     */
    suspend fun removeFromFavorites(movie: Movie) {
        movieDao.removeFromFavorites(movie)
    }
}
