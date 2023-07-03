package kinomaxi.feature.movieDetails.model

data class MovieDetailsViewData(
    val movieDetails: MovieDetails,
    val movieImages: List<MovieImage>,
    val isFavorite: Boolean
)