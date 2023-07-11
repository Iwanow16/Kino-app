package kinomaxi.feature.favorites.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kinomaxi.feature.movieList.model.FavoriteMovie
import kinomaxi.feature.movieList.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {

    /**
     * Добавить фильм [FavoriteMovie] в список избранных фильмов
     */
    @Insert
    suspend fun addToFavorites(movie: FavoriteMovie)

    /**
     * Удалить фильм [movie] из списка избранных фильмов
     */
    @Delete
    suspend fun removeFromFavorites(movie: FavoriteMovie)

    /**
     * Получить признак наличия фильма с идентификатором [movieId] в списке избранных фильмов
     */
    @Query("SELECT COUNT(*) <> 0 FROM favorite_movies WHERE id IN (:movieId)")
    fun isFavoriteFlow(movieId: Long): Flow<Boolean>

    /**
     * Получить список избранных фильмов
     */
    @Query("SELECT * FROM favorite_movies")
    fun getFavoriteMovies(): PagingSource<Int, FavoriteMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<FavoriteMovie>)

    @Query("DELETE FROM favorite_movies")
    suspend fun clearRepos()

}