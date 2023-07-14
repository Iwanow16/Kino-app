package kinomaxi.feature.movieList.model

import androidx.paging.Pager

/**
 * Сущность списка фильмов
 */
data class MoviesList(
    val type: MoviesListType,
    val movies: Pager<Int, Movie>,
)
