package kinomaxi.feature.movieDetails.domain

import kinomaxi.feature.favorites.data.FavoriteMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsMovieFavoriteFlow @Inject constructor(
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
) {
    operator fun invoke(movieId: Long): Flow<Boolean> =
        favoriteMoviesRepository.isFavoriteDetail(movieId)
}