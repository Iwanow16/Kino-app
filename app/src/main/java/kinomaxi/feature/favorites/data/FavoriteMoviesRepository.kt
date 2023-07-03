package kinomaxi.feature.favorites.data

import kinomaxi.feature.movieList.model.FavoriteMovie
import kinomaxi.feature.movieList.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Репозиторий для работы с избранными фильмами
 */

class FavoriteMoviesRepository @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) {

    /**
     * Cписок избранных фильмов
     */
    val favoriteMovies: Flow<List<Movie>> = favoriteMovieDao.getFavoriteMovies().map {
        it.map { FavoriteMovie ->
            Movie(
                FavoriteMovie.id,
                FavoriteMovie.title,
                FavoriteMovie.posterUrl
            )
        }
    }

    /**
     * Получить признак наличия фильма с идентификатором [movieId] в списке избранных фильмов
     */
    fun isFavoriteFlow(movieId: Long): Flow<Boolean> =
        favoriteMovieDao.isFavoriteFlow(movieId)

    /**
     * Добавить фильм [movie] в список избранных фильмов
     */
    suspend fun addToFavorites(movie: Movie) {
        favoriteMovieDao.addToFavorites(movie.toEntity())
    }

    /**
     * Удалить фильм с идентификатором [movie] из списка избранных фильмов
     */
    suspend fun removeFromFavorites(movie: Movie) {
        favoriteMovieDao.removeFromFavorites(movie.toEntity())
    }
}

private fun Movie.toEntity(): FavoriteMovie =
    FavoriteMovie(id, title, posterUrl)