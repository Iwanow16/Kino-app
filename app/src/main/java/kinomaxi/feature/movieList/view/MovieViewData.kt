package kinomaxi.feature.movieList.view

sealed interface MovieListItem {
    data class Movie(
        val id: Long,
        val posterUrl: String?
    ): MovieListItem
    data class Banner(
        val text: String?
    ): MovieListItem
}
