package kinomaxi.feature.movieDetails.view

import kinomaxi.feature.movieDetails.model.MovieDetailsViewData

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
     * @param data информация о фильме
     */
    data class Success(
        val data: MovieDetailsViewData,
    ) : MovieDetailsViewState()
}
