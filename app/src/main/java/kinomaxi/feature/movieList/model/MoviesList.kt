package kinomaxi.feature.movieList.model

import androidx.paging.PagingData

/**
 * Сущность списка фильмов
 */
data class MoviesList(
    val type: MoviesListType,
    val movies: PagingData<Movie>,
)
