package kinomaxi.feature.favorites.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import kinomaxi.feature.movieList.model.FavoriteMovie

/**
 * База данных для работы с избранными фильмами
 */

@Database(
    entities = [FavoriteMovie::class],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 2, to = 3)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
