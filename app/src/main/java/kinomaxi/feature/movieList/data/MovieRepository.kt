package kinomaxi.feature.movieList.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import kinomaxi.feature.backgroundWork.data.ConfDataStore
import kinomaxi.feature.movieList.domain.GetMoviePageUseCase
import kinomaxi.feature.movieList.model.Movie
import kinomaxi.feature.movieList.model.MoviesListType
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: MoviesListApiService,
    private val dataStore: ConfDataStore
) {

    fun getMovieListFlow(listType: MoviesListType): Pager<Int, Movie> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GetMoviePageUseCase(apiService, listType, dataStore) }
        )
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}