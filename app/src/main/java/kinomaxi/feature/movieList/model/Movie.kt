package kinomaxi.feature.movieList.model

/**
 * Сущность фильма
 */
data class Movie(
    val id: Long,
    val title: String,
    val posterUrl: String?,
)
