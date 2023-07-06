package kinomaxi.feature.accountDetails.view

sealed class AccountDetailsViewState {

    object Loading : AccountDetailsViewState()

    object Error : AccountDetailsViewState()

    data class Success(
        val data: String
    ) : AccountDetailsViewState()
}