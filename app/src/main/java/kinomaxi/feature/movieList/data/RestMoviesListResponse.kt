package kinomaxi.feature.movieList.data

import com.google.gson.annotations.SerializedName

/**
 * REST-представление ответа на запрос списка фильмов
 */
data class RestMoviesListResponse(
    @SerializedName("results") val movies: List<RestMovie>,
)
