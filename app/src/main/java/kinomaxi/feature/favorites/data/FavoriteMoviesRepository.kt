package kinomaxi.feature.favorites.data

import kinomaxi.feature.movieList.model.Movie
import kinomaxi.feature.movieList.model.MovieDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Репозиторий для работы с избранными фильмами
 */
class FavoriteMoviesRepository(private val movieDao: MovieDao) {

    /**
     * Cписок избранных фильмов
     */
    val favoriteMovies: Flow<List<Movie>> = movieDao.getFavoriteMovies().map {
        it.map { movieDB -> Movie(movieDB.id, movieDB.title, movieDB.posterUrl) }
    }

    /**
     * Получить признак наличия фильма с идентификатором [movieId] в списке избранных фильмов
     */
    suspend fun isFavorite(movieId: Long) = movieDao.isFavorite(movieId)

    /**
     * Получить признак наличия фильма с идентификатором [movieId] в списке избранных фильмов
     */
    fun isFavoriteDetail(movieId: Long): Flow<Boolean> = movieDao.isFavoriteDetail(movieId)

    /**
     * Добавить фильм [movie] в список избранных фильмов
     */
    suspend fun addToFavorites(movie: Movie) {
        movieDao.addToFavorites(movie.toEntity())
    }

    /**
     * Удалить фильм с идентификатором [movieId] из списка избранных фильмов
     */
    suspend fun removeFromFavorites(movie: Movie) {
        movieDao.removeFromFavorites(movie.toEntity())
    }
}

private fun Movie.toEntity() =
    MovieDB(id, title, posterUrl)