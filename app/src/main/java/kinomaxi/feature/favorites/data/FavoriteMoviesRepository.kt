package kinomaxi.feature.favorites.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kinomaxi.feature.database.AppDatabase
import kinomaxi.feature.movieList.data.MovieRepository
import kinomaxi.feature.movieList.domain.GetMoviePageUseCase
import kinomaxi.feature.movieList.model.FavoriteMovie
import kinomaxi.feature.movieList.model.Movie
import kinomaxi.feature.movieList.model.MoviesListType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Репозиторий для работы с избранными фильмами
 */

class FavoriteMoviesRepository @Inject constructor(
    private val database: AppDatabase,
    private val apiService: FavoriteApiService,
    private val favoriteMovieDao: FavoriteMovieDao
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getFavoriteMovies(): Flow<PagingData<FavoriteMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = FavoriteMediator(apiService, database),
            pagingSourceFactory = { favoriteMovieDao.getFavoriteMovies() }
        ).flow
            .cachedIn(CoroutineScope(Dispatchers.IO))
    }

    /**
     * Cписок избранных фильмов
     */
/*    val favoriteMovies: Flow<List<Movie>> = favoriteMovieDao.getFavoriteMovies().map {
        it.map { FavoriteMovie ->
            Movie(
                FavoriteMovie.id,
                FavoriteMovie.title,
                FavoriteMovie.posterUrl
            )
        }
    }*/

    /**
     * Получить признак наличия фильма с идентификатором [movieId] в списке избранных фильмов
     */
    fun isFavoriteFlow(movieId: Long): Flow<Boolean> =
        favoriteMovieDao.isFavoriteFlow(movieId)

    /**
     * Добавить фильм [movie] в список избранных фильмов
     */
    suspend fun addToFavorites(movie: Movie) {
        favoriteMovieDao.addToFavorites(movie.toEntity())
    }

    /**
     * Удалить фильм [movie] из списка избранных фильмов
     */
    suspend fun removeFromFavorites(movie: Movie) {
        favoriteMovieDao.removeFromFavorites(movie.toEntity())
    }
}

private fun Movie.toEntity(): FavoriteMovie =
    FavoriteMovie(id, title, posterUrl)