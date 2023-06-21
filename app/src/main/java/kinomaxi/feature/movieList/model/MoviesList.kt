package kinomaxi.feature.movieList.model

/**
 * Сущность списка фильмов
 */
data class MoviesList(
    val type: MoviesListType,
    val movies: List<Movie>,
)
