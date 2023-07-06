package kinomaxi.feature.accountDetails.view

import kinomaxi.feature.accountDetails.model.AccountDetails

sealed class AccountDetailsViewState {

    object Loading : AccountDetailsViewState()

    object Error : AccountDetailsViewState()

    data class Success(
        val accountDetails: AccountDetails
    ) : AccountDetailsViewState()
}