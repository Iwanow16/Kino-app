package kinomaxi.feature.mainPage.view

/**
 * Возможные состояния главной страницы
 */
sealed class MainPageState {

    /**
     * Происходит загрузка данных
     */
    object Loading : MainPageState()

    /**
     * Произошла ошибка при загрузке данных
     */
    object Error : MainPageState()

    /**
     * Данные загружены
     *
     * @param data данные экрана
     */
    data class Success(
        val data: MainPageData,
    ) : MainPageState()
}
