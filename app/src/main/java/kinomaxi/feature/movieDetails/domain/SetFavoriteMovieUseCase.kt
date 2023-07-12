package kinomaxi.feature.movieDetails.domain

import kinomaxi.feature.movieDetails.data.FavoriteType
import kinomaxi.feature.movieDetails.data.MovieDetailsApiService
import javax.inject.Inject

class SetFavoriteMovieUseCase @Inject constructor(
    private val apiService: MovieDetailsApiService,
){
    suspend operator fun invoke(movieId: Long, favorite: Boolean) {
        apiService.favoriteMovie(FavoriteType("movie", movieId, favorite))
    }
}