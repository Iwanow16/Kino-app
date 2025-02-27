package kinomaxi.feature.favorites.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kinomaxi.feature.backgroundWork.data.ConfDataStore
import kinomaxi.feature.database.AppDatabase
import kinomaxi.feature.movieList.data.RestMoviesListResponse
import kinomaxi.feature.movieList.model.FavoriteMovie
import kinomaxi.feature.movieList.model.RemoteKeys
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class FavoriteMediator(
    private val apiService: FavoriteApiService,
    private val database: AppDatabase,
    private val dataStore: ConfDataStore
) : RemoteMediator<Int, FavoriteMovie>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FavoriteMovie>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
        try {
            val baseUrl: String? = dataStore.baseUrlConfigurationFlow.first()
            val posterPreviewSize: String? = dataStore.posterSizeConfigurationFlow.first()
            val response = apiService.getFavoriteList(page).toEntity(baseUrl, posterPreviewSize)

            val endOfPaginationReached = response.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.favoriteMovieDao().clearRepos()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = response.map {
                    RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                database.favoriteMovieDao().insertAll(response)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, FavoriteMovie>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                database.remoteKeysDao().remoteKeysMovieId(movie.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, FavoriteMovie>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                database.remoteKeysDao().remoteKeysMovieId(movie.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, FavoriteMovie>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                database.remoteKeysDao().remoteKeysMovieId(movieId)
            }
        }
    }
}

private fun RestMoviesListResponse.toEntity(
    baseUrl: String?,
    posterPreviewSize: String?
): List<FavoriteMovie> =
    movies.map { movie ->
        FavoriteMovie(
            id = movie.id,
            title = movie.title,
            posterUrl = "${baseUrl}${posterPreviewSize}${movie.posterPath}"
        )
    }