package kinomaxi.feature.loginPage.view

sealed class LoginPageViewState {

    object Loading : LoginPageViewState()

    object Error : LoginPageViewState()

    data class Success(
        val data: String
    ) : LoginPageViewState()
}