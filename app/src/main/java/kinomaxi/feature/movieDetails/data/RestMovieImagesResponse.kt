package kinomaxi.feature.movieDetails.data

import com.google.gson.annotations.SerializedName

/**
 * REST-представление ответа на запрос списка изображений фильма
 */
data class RestMovieImagesResponse(
    @SerializedName("backdrops") val backdrops: List<RestMovieImage>
)

/**
 * REST-представление изображения фильма
 */
data class RestMovieImage(
    @SerializedName("file_path") val path: String,
)
