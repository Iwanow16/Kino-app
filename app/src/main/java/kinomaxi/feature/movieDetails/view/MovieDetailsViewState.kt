package kinomaxi.feature.movieDetails.view

import kinomaxi.feature.movieDetails.model.MovieDetails
import kinomaxi.feature.movieDetails.model.MovieImage

/**
 * Возможные состояния экрана детальной информации о фильме
 */
sealed class MovieDetailsViewState {

    /**
     * Происходит загрузка данных
     */
    object Loading : MovieDetailsViewState()

    /**
     * Произошла ошибка при загрузке данных
     */
    object Error : MovieDetailsViewState()

    /**
     * Данные загружены
     *
     * @param movieDetails информация о фильме
     * @param movieImages список изображений фильма
     */
    data class Success(
        val movieDetails: MovieDetails,
        val movieImages: List<MovieImage>,
    ) : MovieDetailsViewState()

}
