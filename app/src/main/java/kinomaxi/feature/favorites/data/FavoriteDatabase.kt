package kinomaxi.feature.favorites.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kinomaxi.feature.movieList.model.MovieDB

/**
 * База данных для работы с избранными фильмами
 */
@Database(entities = [MovieDB::class], version = 2, exportSchema = false)
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