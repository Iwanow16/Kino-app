package kinomaxi.feature.movieDetails.domain

import kinomaxi.feature.favorites.data.FavoriteMoviesRepository

class IsMovieFavoriteFlow(
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
) {
        operator fun invoke(movieId: Long) =
        favoriteMoviesRepository.isFavoriteDetail(movieId)
}