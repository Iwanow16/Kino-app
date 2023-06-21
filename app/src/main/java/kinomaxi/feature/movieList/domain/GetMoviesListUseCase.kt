package kinomaxi.feature.movieList.domain

import kinomaxi.AppConfig
import kinomaxi.feature.movieList.data.MoviesListApiService
import kinomaxi.feature.movieList.data.RestMovie
import kinomaxi.feature.movieList.data.RestMoviesListResponse
import kinomaxi.feature.movieList.model.Movie
import kinomaxi.feature.movieList.model.MoviesList
import kinomaxi.feature.movieList.model.MoviesListType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Бизнес-сценарий получения списка фильмов
 */
class GetMoviesListUseCase(
    private val apiService: MoviesListApiService,
) {

    /**
     * Получить список фильмов с типом [listType]
     */
    operator fun invoke(
        listType: MoviesListType,
        onSuccess: (list: MoviesList) -> Unit,
        onFailure: () -> Unit
    ) {
        val moviesListApiCall = when (listType) {
            MoviesListType.TOP_RATED_MOVIES -> apiService.getTopRatedMovies()
            MoviesListType.POPULAR_MOVIES -> apiService.getPopularMovies()
            MoviesListType.UPCOMING_MOVIES -> apiService.getUpcomingMovies()
        }

        moviesListApiCall.enqueue(object : Callback<RestMoviesListResponse> {
            override fun onResponse(
                call: Call<RestMoviesListResponse>,
                response: Response<RestMoviesListResponse>
            ) {
                if (!response.isSuccessful) {
                    onFailure()
                    return
                }

                val list = response.body()?.toEntity(listType)
                if (list == null) {
                    onFailure()
                    return
                }

                onSuccess(list)
            }

            override fun onFailure(call: Call<RestMoviesListResponse>, t: Throwable) {
                onFailure()
            }
        })
    }
}

private fun RestMoviesListResponse.toEntity(type: MoviesListType) = MoviesList(
    type = type,
    movies = movies.map(RestMovie::toEntity),
)

private fun RestMovie.toEntity() = Movie(
    id = id,
    title = title,
    posterUrl = "${AppConfig.IMAGE_BASE_URL}${AppConfig.POSTER_PREVIEW_SIZE}$posterPath",
)
