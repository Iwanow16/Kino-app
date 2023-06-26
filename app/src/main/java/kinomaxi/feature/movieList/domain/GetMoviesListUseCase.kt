package kinomaxi.feature.movieList.domain

import kinomaxi.AppConfig
import kinomaxi.feature.movieList.data.MoviesListApiService
import kinomaxi.feature.movieList.data.RestMovie
import kinomaxi.feature.movieList.data.RestMoviesListResponse
import kinomaxi.feature.movieList.model.Movie
import kinomaxi.feature.movieList.model.MoviesList
import kinomaxi.feature.movieList.model.MoviesListType

/**
 * Бизнес-сценарий получения списка фильмов
 */
class GetMoviesListUseCase(
    private val apiService: MoviesListApiService,
) {

    /**
     * Получить список фильмов с типом [listType]
     */
    suspend operator fun invoke(
        listType: MoviesListType,
    ) : MoviesList {
        return when (listType) {
            MoviesListType.TOP_RATED_MOVIES -> apiService.getTopRatedMovies()
            MoviesListType.POPULAR_MOVIES -> apiService.getPopularMovies()
            MoviesListType.UPCOMING_MOVIES -> apiService.getUpcomingMovies()
        }::toEntity.invoke(listType)
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
