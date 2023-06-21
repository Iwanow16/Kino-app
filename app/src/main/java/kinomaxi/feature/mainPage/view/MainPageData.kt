package kinomaxi.feature.mainPage.view

import kinomaxi.feature.movieList.model.MoviesList

/**
 * View-представление данных главной страницы
 *
 * @param topRatedMoviesList список фильмов с самым высоким рейтингом
 * @param topPopularMoviesList список текущих популярных фильмов
 * @param topUpcomingMoviesList список ещё не вышедших фильмов
 */
data class MainPageData(
    val topRatedMoviesList: MoviesList,
    val topPopularMoviesList: MoviesList,
    val topUpcomingMoviesList: MoviesList,
)
