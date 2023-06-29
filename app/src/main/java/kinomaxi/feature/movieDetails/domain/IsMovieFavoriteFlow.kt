package kinomaxi.feature.movieDetails.domain

import kinomaxi.feature.favorites.data.FavoriteMoviesRepository
import kotlinx.coroutines.flow.Flow

class IsMovieFavoriteFlow(
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
) {
    operator fun invoke(movieId: Long): Flow<Boolean> =
        favoriteMoviesRepository.isFavoriteDetail(movieId)
}