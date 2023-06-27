package kinomaxi.feature.movieList.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Сущность фильма
 */
@Entity
data class Movie(
    @PrimaryKey val id: Long,
    val title: String,
    val posterUrl: String?,
)
