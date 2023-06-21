package kinomaxi.feature.favorites.data

import kinomaxi.feature.movieList.model.Movie

/**
 * Репозиторий для работы с избранными фильмами
 */
class FavoriteMoviesRepository {

    private val favoriteMovies = mutableMapOf<Long, Movie>()

    /**
     * Добавить фильм [movie] в список избранных фильмов
     */
    fun addToFavorites(movie: Movie) {
        favoriteMovies[movie.id] = movie
    }

    /**
     * Удалить фильм с идентификатором [movieId] из списка избранных фильмов
     */
    fun removeFromFavorites(movieId: Long) {
        favoriteMovies.remove(movieId)
    }

    /**
     * Получить признак наличия фильма с идентификатором [movieId] в списке избранных фильмов
     */
    fun isFavorite(movieId: Long): Boolean {
        return favoriteMovies.contains(movieId)
    }

    /**
     * Получить список избранных фильмов
     */
    fun getFavoriteMovies(): List<Movie> {
        return favoriteMovies.values.toList()
    }

    companion object {

        val instance: FavoriteMoviesRepository by lazy {
            FavoriteMoviesRepository()
        }
    }
}
