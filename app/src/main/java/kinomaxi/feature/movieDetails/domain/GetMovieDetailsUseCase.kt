package kinomaxi.feature.movieDetails.domain

import kinomaxi.AppConfig
import kinomaxi.feature.favorites.data.FavoriteMoviesRepository
import kinomaxi.feature.movieDetails.data.MovieDetailsApiService
import kinomaxi.feature.movieDetails.data.RestMovieDetails
import kinomaxi.feature.movieDetails.data.RestMovieGenre
import kinomaxi.feature.movieDetails.model.MovieDetails
import kinomaxi.feature.movieDetails.model.MovieImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

/**
 * Бизнес-сценарий получения детальной информации о фильме
 */
class GetMovieDetailsUseCase(
    private val apiService: MovieDetailsApiService,
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
) {

    /**
     * Получить детальную информацию о фильме по идентификатору [movieId]
     */
    operator fun invoke(
        movieId: Long,
        onSuccess: (details: MovieDetails) -> Unit,
        onFailure: () -> Unit
    ) {
        apiService.getMovieDetails(movieId).enqueue(object : Callback<RestMovieDetails> {
            override fun onResponse(
                call: Call<RestMovieDetails>,
                response: Response<RestMovieDetails>
            ) {
                if (!response.isSuccessful) {
                    onFailure()
                    return
                }

                val isFavorite = favoriteMoviesRepository.isFavorite(movieId)
                val details = response.body()?.toEntity(isFavorite)
                if (details == null) {
                    onFailure()
                    return
                }

                onSuccess(details)
            }

            override fun onFailure(call: Call<RestMovieDetails>, t: Throwable) {
                onFailure()
            }
        })
    }
}

private fun RestMovieDetails.toEntity(isFavorite: Boolean) = MovieDetails(
    id = id,
    imdbId = imdbId,
    title = title,
    originalTitle = originalTitle,
    posterImage = MovieImage(
        imageUrl = "${AppConfig.IMAGE_BASE_URL}original$posterPath",
        previewUrl = "${AppConfig.IMAGE_BASE_URL}${AppConfig.POSTER_PREVIEW_SIZE}$posterPath",
    ),
    overview = overview,
    tagline = tagline,
    genres = genres.map(RestMovieGenre::name),
    releaseDate = LocalDate.parse(releaseDate),
    lengthMinutes = lengthMinutes,
    rating = rating,
    isFavorite = isFavorite,
)
