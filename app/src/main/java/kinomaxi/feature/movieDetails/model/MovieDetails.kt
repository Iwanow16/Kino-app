package kinomaxi.feature.movieDetails.model

import java.time.LocalDate

/**
 * Сущность детальной информации о фильме
 */
data class MovieDetails(
    val id: Long,
    val imdbId: String?,
    val title: String,
    val originalTitle: String,
    val posterImage: MovieImage?,
    val overview: String?,
    val tagline: String?,
    val genres: List<String>,
    val releaseDate: LocalDate,
    val lengthMinutes: Int,
    val rating: Float,
)
