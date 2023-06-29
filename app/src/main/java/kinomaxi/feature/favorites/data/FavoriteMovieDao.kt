package kinomaxi.feature.favorites.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kinomaxi.feature.movieList.model.FavoriteMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {

    /**
     * Добавить фильм [FavoriteMovie] в список избранных фильмов
     */
    @Insert
    suspend fun addToFavorites(movie: FavoriteMovie)

    /**
     * Удалить фильм с идентификатором [movieId] из списка избранных фильмов
     */
    @Delete
    suspend fun removeFromFavorites(movie: FavoriteMovie)

    /**
     * Получить признак наличия фильма с идентификатором [movieId] в списке избранных фильмов
     */
    @Query("SELECT COUNT(*) <> 0 FROM favorite_movies WHERE id IN (:movieId)")
    suspend fun isFavorite(movieId: Long): Boolean

    /**
     * Получить признак наличия фильма с идентификатором [movieId] в списке избранных фильмов
     */
    @Query("SELECT COUNT(*) <> 0 FROM favorite_movies WHERE id IN (:movieId)")
    fun isFavoriteFlow(movieId: Long): Flow<Boolean>

    /**
     * Получить список избранных фильмов
     */
    @Query("SELECT * FROM favorite_movies")
    fun getFavoriteMovies(): Flow<List<FavoriteMovie>>

}