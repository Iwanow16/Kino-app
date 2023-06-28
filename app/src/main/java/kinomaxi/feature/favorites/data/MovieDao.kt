package kinomaxi.feature.favorites.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kinomaxi.feature.movieList.model.MovieDB
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    /**
     * Добавить фильм [movieDB] в список избранных фильмов
     */
    @Insert
    suspend fun addToFavorites(movie: MovieDB)

    /**
     * Удалить фильм с идентификатором [movieId] из списка избранных фильмов
     */
    @Delete
    suspend fun removeFromFavorites(movie: MovieDB)

    /**
     * Получить признак наличия фильма с идентификатором [movieId] в списке избранных фильмов
     */
    @Query("SELECT COUNT(*) <> 0 FROM movie_database WHERE id IN (:movieId)")
    suspend fun isFavorite(movieId: Long): Boolean

    /**
     * Получить признак наличия фильма с идентификатором [movieId] в списке избранных фильмов
     */
    @Query("SELECT COUNT(*) <> 0 FROM movie_database WHERE id IN (:movieId)")
    fun isFavoriteDetail(movieId: Long): Flow<Boolean>

    /**
     * Получить список избранных фильмов
     */
    @Query("SELECT * FROM movie_database")
    fun getFavoriteMovies(): Flow<List<MovieDB>>

}