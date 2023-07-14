package kinomaxi.feature.movieList.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kinomaxi.feature.backgroundWork.data.ConfDataStore
import kinomaxi.feature.movieList.data.MovieRepository.Companion.NETWORK_PAGE_SIZE
import kinomaxi.feature.movieList.data.MoviesListApiService
import kinomaxi.feature.movieList.data.RestMoviesListResponse
import kinomaxi.feature.movieList.model.Movie
import kinomaxi.feature.movieList.model.MoviesListType
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class GetMoviePageUseCase(
    private val apisService: MoviesListApiService,
    private val listType: MoviesListType,
    private val dataStore: ConfDataStore
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val baseUrl: String? = dataStore.baseUrlConfigurationFlow.first()
            val posterPreviewSize: String? = dataStore.posterSizeConfigurationFlow.first()
            val response = when (listType) {
                MoviesListType.TOP_RATED_MOVIES -> apisService.getTopRatedMovies(position)
                MoviesListType.POPULAR_MOVIES -> apisService.getPopularMovies(position)
                MoviesListType.UPCOMING_MOVIES -> apisService.getUpcomingMovies(position)
            }.toEntity(baseUrl, posterPreviewSize)
            val nextKey = if (response.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = response,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

private fun RestMoviesListResponse.toEntity(
    baseUrl: String?,
    posterPreviewSize: String?
): List<Movie> =
    movies.map { movie ->
        Movie(
            id = movie.id,
            title = movie.title,
            posterUrl = "${baseUrl}${posterPreviewSize}${movie.posterPath}"
        )
    }
