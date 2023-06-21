package kinomaxi.feature.movieList.data

import com.google.gson.annotations.SerializedName

/**
 * REST-представление данных о фильме
 */
data class RestMovie(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String?,
)
