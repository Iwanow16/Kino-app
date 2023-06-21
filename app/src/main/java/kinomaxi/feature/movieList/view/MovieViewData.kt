package kinomaxi.feature.movieList.view

/**
 * Элемент списка фильмов
 */
data class MovieViewData(
    /**
     * Идентификатор фильма
     */
    val id: Long,
    /**
     * Ссылка на постер фильма
     */
    val posterUrl: String?
)
