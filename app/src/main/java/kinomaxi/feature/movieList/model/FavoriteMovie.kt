package kinomaxi.feature.movieList.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Сущность фильма базы данных
 */
@Entity(tableName = "favorite_movies")
data class FavoriteMovie(
    @PrimaryKey val id: Long,
    val title: String,
    val posterUrl: String?,
)
