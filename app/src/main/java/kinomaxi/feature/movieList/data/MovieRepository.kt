package kinomaxi.feature.movieList.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kinomaxi.feature.movieList.domain.GetMoviePageUseCase
import kinomaxi.feature.movieList.model.Movie
import kinomaxi.feature.movieList.model.MoviesListType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: MoviesListApiService
) {

    fun getMovieListFlow(listType: MoviesListType): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GetMoviePageUseCase(apiService, listType) }
        ).flow
            .cachedIn(CoroutineScope(Dispatchers.IO))
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}