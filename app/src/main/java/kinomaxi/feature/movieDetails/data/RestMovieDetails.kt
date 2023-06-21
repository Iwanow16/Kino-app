package kinomaxi.feature.movieDetails.data

import com.google.gson.annotations.SerializedName

/**
 * REST-представление подробной информации о фильме
 */
data class RestMovieDetails(
    @SerializedName("id") val id: Long,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("genres") val genres: List<RestMovieGenre>,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("runtime") val lengthMinutes: Int,
    @SerializedName("vote_average") val rating: Float,
)

/**
 * REST-представление жанра фильма
 */
data class RestMovieGenre(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
)
