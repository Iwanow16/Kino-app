package kinomaxi.feature.favorites.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kinomaxi.feature.movieList.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * База данных для работы с избранными фильмами
 */
@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        private var INSTANCE: FavoriteDatabase? = null

        fun getDatabase(context: Context): FavoriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "favorite_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Dao
interface MovieDao {

    /**
     * Добавить фильм [movie] в список избранных фильмов
     */
    @Insert
    suspend fun addToFavorites(movie: Movie)

    /**
     * Удалить фильм с идентификатором [movieId] из списка избранных фильмов
     */
    @Delete
    suspend fun removeFromFavorites(movie: Movie)

    /**
     * Получить признак наличия фильма с идентификатором [movieId] в списке избранных фильмов
     */
    @Query("SELECT COUNT(*) <> 0 FROM movie WHERE id IN (:movieId)")
    suspend fun isFavorite(movieId: Long): Boolean

    /**
     * Получить список избранных фильмов
     */
    @Query("SELECT * FROM movie")
    fun getFavoriteMovies(): Flow<List<Movie>>

}