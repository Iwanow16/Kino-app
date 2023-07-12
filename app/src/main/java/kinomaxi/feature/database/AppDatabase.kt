package kinomaxi.feature.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kinomaxi.feature.favorites.data.FavoriteMovieDao
import kinomaxi.feature.favorites.data.RemoteKeysDao
import kinomaxi.feature.movieList.model.FavoriteMovie
import kinomaxi.feature.movieList.model.RemoteKeys

/**
 * База данных для работы с избранными фильмами
 */

@Database(
    entities = [FavoriteMovie::class, RemoteKeys::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
