package kinomaxi.feature.movieList.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Сущность фильма базы данных
 */
@Entity(tableName = "movie_database")
data class MovieDB(
    @PrimaryKey val id: Long,
    val title: String,
    val posterUrl: String?,
)
