package kinomaxi.feature.accountDetails.domain

import kinomaxi.feature.accountDetails.data.AccountDetailsApiServers
import kinomaxi.feature.accountDetails.data.RestAccountDetails
import kinomaxi.feature.accountDetails.model.AccountAvatar
import kinomaxi.feature.accountDetails.model.AccountDetails
import javax.inject.Inject

/**
 * Бизнес-сценарий получения детальной информации об аккаунте
 */

class GetAccountDetailsUseCase @Inject constructor(
    private val apiServers: AccountDetailsApiServers
) {
    suspend operator fun invoke(): AccountDetails =
        apiServers.getAccountDetails().toEntity()
}

private fun RestAccountDetails.toEntity(): AccountDetails =
    AccountDetails(
        id = id,
        language = language,
        country = country,
        name = name,
        includeAdult = includeAdult,
        username = username,
        avatar = AccountAvatar(
            hash = "",
            avatarPath = ""
        )
    )